/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2020.
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 *    and associated documentation files (the "Software"), to deal in the Software without restriction,
 *    including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *    sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies or
 *    substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eu.heliumiot.console.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.api.interfaces.*;
import eu.heliumiot.console.chirpstack.ChirpstackApiAccess;
import eu.heliumiot.console.jpa.db.*;
import eu.heliumiot.console.jpa.db.ApiKey;
import eu.heliumiot.console.jpa.repository.*;
import eu.heliumiot.console.tools.EncryptionHelper;
import eu.heliumiot.console.tools.ExecuteEmail;
import fr.ingeniousthings.tools.*;
import fr.ingeniousthings.tools.Claims;
import io.chirpstack.restapi.*;
import io.chirpstack.restapi.Tenant;
import io.chirpstack.restapi.UserTenant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.sql.Timestamp;
import java.util.*;

import static eu.heliumiot.console.service.HeliumParameterService.PARAM_USER_COND_CURRENT;
import static eu.heliumiot.console.service.HeliumTenantService.HELIUM_TENANT_SETUP_DEFAULT;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ConsoleConfig consoleConfig;

    @Autowired
    protected UserCacheService userCacheService;

    @Autowired
    protected HeliumUserRepository heliumUserRepository;

    @Autowired
    protected HeliumTenantService heliumTenantService;


    // ===================================================
    // Cache Management
    // ===================================================

    protected String bottomEmail_en;

    @PostConstruct
    private void initUserService() {

        // common email footer
        bottomEmail_en = "\n\n";
        bottomEmail_en+="This message has been sent automatically from the "+consoleConfig.getHeliumConsoleName()+" service and sent according to your request." +
                "If you don't think you have been involved, just ignore this message.";
        bottomEmail_en+="\n\n";
        bottomEmail_en+=""+consoleConfig.getHeliumConsoleUrl() +" - Helium Provider";
    }


    // ===================================================
    // Helium User Management
    // ===================================================

    public static final String HUPROFILE_STATUS_CREATED="created";
    public static final String HUPROFILE_STATUS_COMPLETED="completed";

    public static final int HPU_TYPE_CREATION = 1;
    public static final int HPU_TYPE_PLOSS = 2;



    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    // ===================================================
    // Login Management
    // ===================================================

    /**
     * Extract the information from the chirpstack bearer
     * to be able to use them into the console bearer
     * do this without knowing the chirpstack signature key
     * @param bearer
     * @return
     */
    private Claims getUntrustedInfoFromBearer(String bearer) {
        // get the 2rd element of bearer xxxx.XXXXXX.xxxxx
        String [] split = bearer.split("\\.");
        if ( split.length >= 3 ) {
            String decoded = new String(Base64.decode(split[1]));
            ObjectMapper mapper = new ObjectMapper();
            try {
                Claims claims = mapper.readValue(decoded, Claims.class);
                return claims;
            } catch (JsonProcessingException x) {
                x.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Generate a uniq ket based on user key and server key to be able to deprecate any of these keys
     * @param hu
     * @return
     */
    public Key generateKeyForUser(HeliumUser hu) {
        String srvSecret = consoleConfig.getJwtSignatureKey();
        String userSecret = hu.getUserSecret();
        byte [] secret = new byte[64];
        for ( int i = 0 ; i < 64 ; i++ ) {
            secret[i] = (byte) ((byte)(srvSecret.charAt(i)) ^ (2*(byte)(userSecret.charAt(i))));
        }
        return Keys.hmacShaKeyFor(secret);
    }

    @Autowired
    protected ChirpstackApiAccess chirpstackApiAccess;

    @Autowired
    protected HeliumPendingUserRepository heliumPendingUserRepository;

    @Autowired
    protected EncryptionHelper encryptionHelper;

    @Autowired
    protected ExecuteEmail executeEmail;

    @Autowired
    protected HeliumTenantSetupService heliumTenantSetupService;


    /**
     * Create a user account with email validation, the user creation in chirpstack is really created once the email validation
     * has been made
     * @param req
     * @return
     * @throws ITNotFoundException
     * @throws ITParseException
     */
    public UserSignUpRespItf userSignup(UserSignUpReqItf req, String adr)
    throws ITNotFoundException,ITParseException
    {
        // Make sure the property file allow signup as a global setting
        if ( ! consoleConfig.isHeliumAllowsSignup() ) throw new ITParseException("error_signupclose");

        // verify invitation code
        String profile = HELIUM_TENANT_SETUP_DEFAULT;
        if  (req.getInviteCode().length() > 0) {
            // process verification ...
            // set profile based on the invitation code verification
            String uuid = heliumTenantSetupService.acquiresCoupon(req.getInviteCode());
            if ( uuid != null ) {
                profile = uuid;
            } else {
                throw new ITParseException("error_signupclose");
            }
        }

        // is signup allowed
        HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(profile, false);
        if (hts == null || !hts.isSignupAllowed()) {
            throw new ITParseException("error_signupclose");
        }

        // don't mix upper  lower case in user name
        req.setUsername(req.getUsername().toLowerCase());

        UserSignUpRespItf r = new UserSignUpRespItf();
        // verify information
        //  is conditions accepted ?
        if ( ! req.isConditionsAccepted() ) {
            throw new ITParseException("error_conditions");
        }

        // verify email
        if ( ! Tools.isAcceptedEmailSyntax(req.getUsername(), consoleConfig.getIngeniousthingsEmailFilter()) ) {
            log.warn("Refused email registration for "+req.getUsername()+" from "+adr);
            r.setErrorMessage("success");
            Tools.sleep(8+((new Random().nextInt()) % 7));
            return r;
        }
        if ( ! Tools.isValidEmailSyntax(req.getUsername()) ) {
            log.warn("Rejected email registration for "+req.getUsername());
            throw new ITParseException("error_invalid_email");
        }

        /* Emil validator is obsolete not supporting new TLDS
        if ( ! EmailValidator.getInstance().isValid(req.getUsername()) ) {
            // bypass for helium.foundation not recognized by EmailValidator
            if ( ! req.getUsername().matches("^[A-Z0-9._%+-]+@helium.foundation") )
        }
        */


        // verify password
        if ( req.getPassword().length() < 12 ) {
            throw new ITParseException("error_password_size");
        }

        // verify tenantName
        if ( req.getTenantName().length() < 3 || req.getTenantName().length() > 32 ) {
            throw new ITParseException("error_tenant_size");
        }

        // verify email does not exists
        if ( userCacheService.getUserByUsername(req.getUsername()) != null ) {
            // return success but nothing will be made, the
            // email already exist but we do not want this infomrmation
            // to be publicly reported
            r.setErrorMessage("success");
            Tools.sleep(8+((new Random().nextInt()) % 7));
            return r;
        }

        // All test ok, prepare to the next phase : the user will only be
        // really created once we get the email confirmation
        // clean the previous PendingUser, should have a single one but we already seen multiple
        // encrypt passwords
        List<HeliumPendingUser> hpes = heliumPendingUserRepository.findHeliumPendingUserByUsernameAndType(
                req.getUsername(),
                HPU_TYPE_CREATION
                );
        int retry = 0;
        if ( hpes != null && hpes.size() > 0 ) {
            retry = hpes.get(0).getRetrial()+1;
            for (HeliumPendingUser hpe : hpes ) {
                heliumPendingUserRepository.delete(hpe);
            }
        }

        HeliumPendingUser hpe = new HeliumPendingUser();
        hpe.setUsername(req.getUsername());
        hpe.setInsertedAt(new Timestamp(Now.NowUtcMs()));
        hpe.setRetrial(retry);
        hpe.setTenantName(req.getTenantName());
        hpe.setOfferName(profile);
        hpe.setValidationCode(RandomString.getRandomAZString(80));
        hpe.setUserPassword(encryptionHelper.encryptStringWithServerKey(req.getPassword()));
        hpe.setType(HPU_TYPE_CREATION);
        HeliumParameter version = heliumParameterService.getParameter(PARAM_USER_COND_CURRENT);
        if ( version != null ) {
            hpe.setConditionVersion(version.getStrValue());
        } else {
            hpe.setConditionVersion("Default");
        }

        heliumPendingUserRepository.save(hpe);

        // Now we need to send an email
        String bodyEmail_en = "You just have requested to signup on "+consoleConfig.getHeliumConsoleName()+". This email is " +
                "a confirmation of your request. To validate your registration, you need to follow this link to terminate the process: \n\n" +
                ""+consoleConfig.getHeliumConsoleUrl()+"/front/signupValidation?key="+hpe.getValidationCode()+"\n\n" +
                "This link will be valid for the next 30 minutes.";

        executeEmail.execute(
                hpe.getUsername(),
                bodyEmail_en+this.bottomEmail_en,
                "["+consoleConfig.getHeliumConsoleName()+"] Email registration confirmation",
                consoleConfig.getHeliumMailFrom()
        );

        // add time delay to avoid analysis of the response time
        Tools.sleep(10+((new Random().nextInt()) % 10));
        r.setErrorMessage("success");
        return r;
    }


    @Autowired
    private HeliumTenantRepository heliumTenantRepository;

    @Autowired
    private HeliumParameterService heliumParameterService;

    /**
     * Get the registration confirmation code to terminate the user signup
     * @param registrationCode
     * @return
     * @throws ITNotFoundException
     * @throws ITParseException
     */
    public void userSignupConfirmation(String registrationCode)
            throws ITNotFoundException,ITParseException
    {

        // Make sure the property file allow signup as a global setting
        if ( ! consoleConfig.isHeliumAllowsSignup() ) throw new ITParseException("error_signupclose");

        // check input
        if ( registrationCode.length() != 80 ) {
            log.warn("Getting invalid registration code");
            throw new ITParseException("error_invalid");
        }

        HeliumPendingUser hpe = heliumPendingUserRepository.findOneHeliumPendingUserByValidationCode(
                registrationCode
        );
        if ( hpe == null ) {
            log.warn("Getting not existing validation code");
            throw new ITParseException("error_invalid");
        }

        if ( hpe.getType() != HPU_TYPE_CREATION ) {
            log.warn("Getting a code for the wrong type of action");
            throw new ITParseException("error_invalid");
        }

        if ( hpe.getRetrial() > 10 ) {
            log.error("User with email "+hpe.getUsername()+" has made too many retry");
            throw new ITParseException("error_invalid");
        }

        if( hpe.getInsertedAt().getTime() < (Now.NowUtcMs() - 30*60_000) ) {
            log.info("Validation code is outdated");
            throw new ITParseException("error_timeout");
        }

        HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(hpe.getOfferName(),false);
        if ( hts == null || ! hts.isSignupAllowed() ) {
            throw new ITParseException("error_signupclose");
        }

        HttpHeaders heads = new HttpHeaders();
        heads.add("authorization", "Bearer "+consoleConfig.getChirpstackApiAdminKey());

        // Lets create the tenant first
        Tenant tenant = Tenant.newBuilder()
                .setName(hpe.getTenantName())
                .setDescription("Default user tenant")
                .setCanHaveGateways(false)
                .setMaxDeviceCount(hts.getMaxDevices())
                .setMaxGatewayCount(0)
                .setPrivateGateways(false)
                .build();

        CreateTenantRequest tenantReq = CreateTenantRequest.newBuilder()
                .setTenant(tenant)
                .build();

        String tenantId = null;
        try {

            byte[] respB = chirpstackApiAccess.execute(
                    HttpMethod.POST,
                    "/api.TenantService/Create",
                    null,
                    heads,
                    tenantReq.toByteArray()
            );
            CreateTenantResponse resp = CreateTenantResponse.parseFrom(respB);

            if ( resp != null ) {
                tenantId = resp.getId();
            }

        } catch ( ITRightException x ) {
            log.error("Impossible to create new tenant - rights - "+x.getMessage());
        } catch ( ITNotFoundException x ) {
            log.error("Impossible to create new tenant - not found - "+x.getMessage());
        } catch ( InvalidProtocolBufferException x ) {
            log.error("Impossible to create new tenant - protobuf");
        }

        if ( tenantId == null ) throw new ITParseException("error_internal");

        // We got it, let's create the user and the tenant
        String userId = null;

        // Public signup with admin validation
        boolean activate = true;
        if ( hts.getTenantUUID().compareTo(HELIUM_TENANT_SETUP_DEFAULT) == 0 ) {
            if ( ! consoleConfig.isHeliumAllowsActivation() ) {
                activate = false;
            }
        }

        io.chirpstack.restapi.User user = io.chirpstack.restapi.User.newBuilder()
                .setEmail(hpe.getUsername())
                .setIsActive(activate)
                .setIsAdmin(false)
                .build();
        UserTenant ut = UserTenant.newBuilder()
                .setIsAdmin(true)
                .setIsDeviceAdmin(true)
                .setIsGatewayAdmin(false)
                .setTenantId(tenantId)
                .build();
        CreateUserRequest cur = CreateUserRequest.newBuilder()
                .setPassword(encryptionHelper.decryptStringWithServerKey(hpe.getUserPassword()))
                .setUser(user)
                .addTenants(ut)
                .build();
        try {

            byte[] respB = chirpstackApiAccess.execute(
                    HttpMethod.POST,
                    "/api.UserService/Create",
                    null,
                    heads,
                    cur.toByteArray()
            );
            CreateUserResponse resp = CreateUserResponse.parseFrom(respB);

            if ( resp != null && resp.getId() != null && resp.getId().length() > 5 ) {
                // get User to make sure
                userId = resp.getId();
                UserCacheService.UserCacheElement u = userCacheService.getUserById(resp.getId());
                if ( u != null ) {
                    u.heliumUser.setDefaultOffer(hpe.getOfferName());
                    u.heliumUser.setConditionValidation(true);
                    u.heliumUser.setRegistrationTime(hpe.getInsertedAt());
                    u.heliumUser.setConditionTime(hpe.getInsertedAt());
                    u.heliumUser.setConditionVersion(hpe.getConditionVersion());
                    heliumUserRepository.save(u.heliumUser);
                }
            }

        } catch ( ITRightException x ) {
            log.error("Impossible to create new user - rights - "+x.getMessage());
        } catch ( ITNotFoundException x ) {
            log.error("Impossible to create new user - not found - "+x.getMessage());
        } catch ( InvalidProtocolBufferException x ) {
            log.error("Impossible to create new user - protobuf");
        }

        if ( userId == null ) {
            // delete tenant
            DeleteTenantRequest dt = DeleteTenantRequest.newBuilder()
                    .setId(tenantId)
                    .build();
            try {

                byte[] respB = chirpstackApiAccess.execute(
                        HttpMethod.POST,
                        "/api.TenantService/Delete",
                        null,
                        heads,
                        dt.toByteArray()
                );

            } catch ( ITRightException x ) {
                log.error("Impossible to delete tenant - rights - "+x.getMessage());
            } catch (ITNotFoundException x) {
                log.error("Impossible to delete tenant - not found - "+x.getMessage());
            } catch ( ITParseException x) {
                log.error("Impossible to delete tenant - parse");
            }
            throw new ITParseException("error_internal");
        }

        // create the Helium Tenant Setup Instance
        heliumTenantSetupService.createAndSave(hts,tenantId);

        // create the Helium Tenant instance
        HeliumTenant t = new HeliumTenant();
        t.setTenantUUID(tenantId);
        t.setDcBalance(hts.getFreeTenantDc());
        t.setState(HeliumTenant.TenantState.NORMAL);
        t.setMaxCopy(hts.getMaxCopy());
        heliumTenantRepository.save(t);

        // delete validation code
        heliumPendingUserRepository.delete(hpe);

    }



    /**
     * Receives a login request, verify validity and returns the bearers for both chirpstack and api
     * @param request
     * @throws ITNotFoundException
     * @throws ITRightException
     */
    public LoginRespItf verifyUserLogin(LoginReqItf request)
    throws ITNotFoundException, ITRightException, ITParseException
    {
        // check params
        if ( request.getUsername() == null || request.getPassword() == null ) throw new ITParseException();
        request.setUsername(request.getUsername().toLowerCase());

        // check for user existence, no need to go further otherwise
        // @Todo check 2FA and check too many login attempt later
        UserCacheService.UserCacheElement u = userCacheService.getUserByUsername(request.getUsername());
        if ( u == null ) throw new ITNotFoundException();
        if ( ! u.user.isActive() ) throw new ITRightException();

        // @Todo potential verification of the email validation

        // try to login on chirpstack API
        LoginRequest lr = LoginRequest.newBuilder()
                .setEmail(request.getUsername())
                .setPassword(request.getPassword())
                .build();

        try {

            byte[] respB = chirpstackApiAccess.execute(
                    HttpMethod.POST,
                    "/api.InternalService/Login",
                    null,
                    null,
                    lr.toByteArray()
            );
            LoginResponse resp = LoginResponse.parseFrom(respB);

            LoginRespItf r = new LoginRespItf();
            r.setAdmin(u.user.isAdmin());

            if (resp != null && resp.getJwt() != null && resp.getJwt().length() > 10) {
                r.setChirpstackBearer(resp.getJwt());
            } else {
                throw new ITRightException();
            }

            // get expiration from chirpstack JWT
            String cJwt=r.getChirpstackBearer();
            Claims cclaims=this.getUntrustedInfoFromBearer(cJwt);
            long cExpiration=(cclaims!=null)?cclaims.getExp()*1000:Now.NowUtcMs()+Now.ONE_HOUR;

            // roles
            ArrayList<String> roles = new ArrayList<>();
            roles.add(ROLE_USER);
            if ( u.user.isAdmin() ) roles.add(ROLE_ADMIN);

            // generate the console jwt
            io.jsonwebtoken.Claims claims = Jwts.claims().setSubject(u.heliumUser.getUserid());
            claims.put("roles", roles);
            claims.setExpiration(new Date(cExpiration));

            String token = Jwts.builder()
                    .setHeaderParam("typ","JWT")
                    .setClaims(claims)
                    .setExpiration(new Date(cExpiration))
                    .signWith(this.generateKeyForUser(u.heliumUser), SignatureAlgorithm.HS512)
                    .compact();

            r.setConsoleBearer(token);

            // update User Last Seen
            u.heliumUser.setLastSeen(Now.NowUtcMs());
            userCacheService.updateHeliumUser(u);

            // check the user Condition version
            r.setUserConditionChanged(false);
            HeliumParameter version = heliumParameterService.getParameter(PARAM_USER_COND_CURRENT);
            if (  u.heliumUser.getConditionVersion() == null ||
                ( version != null && u.heliumUser.getConditionVersion().compareToIgnoreCase(version.getStrValue()) != 0 )
            ) {
               r.setUserConditionChanged(true);
            }
            return r;
        } catch ( ITNotFoundException x ) {
            throw new ITRightException();
        } catch ( ITParseException x ) {
            throw new ITParseException();
        } catch (Exception x) {
            x.printStackTrace();
            throw new ITParseException();
        }


    }


    /**
     * Get user information from it's name coming from the jwt token
     * on the upper layer
     * @param userid
     * @return
     */
    public UserLoginRespItf getUserLogin(String userid)
    throws ITNotFoundException, ITRightException {
        UserCacheService.UserCacheElement c =  userCacheService.getUserById(userid);
        if ( c == null ) throw new ITNotFoundException();
        UserLoginRespItf r = new UserLoginRespItf();
        r.setUsername(c.user.getEmail());
        r.setCompletion(c.heliumUser.getProfileStatus());
        return r;
    }


    /**
     * Get the user profile details
     * @param userid
     * @return
     * @throws ITNotFoundException
     * @throws ITRightException
     */
    public UserDetailRespItf getUserdetails(String userid)
            throws ITNotFoundException, ITRightException {
        UserCacheService.UserCacheElement c =  userCacheService.getUserById(userid);
        if ( c == null ) throw new ITNotFoundException();
        UserDetailRespItf r = new UserDetailRespItf();
        r.setUsername(c.user.getEmail());
        if ( c.heliumUser.getAddress() != null ) {
            r.setAddress(encryptionHelper.decryptStringWithServerKey(c.heliumUser.getAddress()));
        } else r.setAddress("");
        r.setCityCode(c.heliumUser.getCityCode());
        r.setCityName(c.heliumUser.getCityName());
        if ( c.heliumUser.getCompany() != null ) {
            r.setCompany(encryptionHelper.decryptStringWithServerKey(c.heliumUser.getCompany()));
        } else r.setCompany("");
        r.setCountry(c.heliumUser.getCountry());
        if ( c.heliumUser.getFirstName() != null ) {
            r.setFirstName(encryptionHelper.decryptStringWithServerKey(c.heliumUser.getFirstName()));
        } else r.setFirstName("");
        if ( c.heliumUser.getLastName() != null ) {
            r.setLastName(encryptionHelper.decryptStringWithServerKey(c.heliumUser.getLastName()));
        } else r.setLastName("");
        return r;
    }

    public void updateUserAgreement(String userid) throws ITNotFoundException, ITRightException {

        UserCacheService.UserCacheElement c =  userCacheService.getUserById(userid);
        if ( c == null ) throw new ITNotFoundException();

        HeliumParameter version = heliumParameterService.getParameter(PARAM_USER_COND_CURRENT);
        if ( version != null ) {
            c.heliumUser.setConditionVersion(version.getStrValue());
            c.heliumUser.setConditionTime(new Timestamp(Now.NowUtcMs()));
            c.heliumUser.setConditionValidation(true);
            userCacheService.updateHeliumUser(c);
        }

    }


    /**
     * Update the User profile
     * @param userid
     * @param u
     * @return
     * @throws ITNotFoundException
     * @throws ITRightException
     */
    public UserDetailRespItf updateUserdetails(String userid, UserDetailUpdateReqItf u)
            throws ITNotFoundException, ITRightException {
        UserCacheService.UserCacheElement c =  userCacheService.getUserById(userid);
        if ( c == null ) throw new ITNotFoundException();

        if ( u.getUsername().compareToIgnoreCase(c.user.getEmail()) != 0 ) throw new ITRightException();

        c.heliumUser.setFirstName(encryptionHelper.encryptStringWithServerKey(u.getFirstName()));
        c.heliumUser.setLastName(encryptionHelper.encryptStringWithServerKey(u.getLastName()));
        c.heliumUser.setCompany(encryptionHelper.encryptStringWithServerKey(u.getCompany()));
        c.heliumUser.setAddress(encryptionHelper.encryptStringWithServerKey(u.getAddress()));
        c.heliumUser.setCityCode(u.getCityCode());
        c.heliumUser.setCityName(u.getCityName());
        c.heliumUser.setCountry(u.getCountry());
        if ( u.getLastName().length() > 0 && u.getCityCode().length() >0 && u.getCityName().length() > 0 && u.getCountry().length() > 0 ) {
            c.heliumUser.setProfileStatus(HUPROFILE_STATUS_COMPLETED);
        } else {
            c.heliumUser.setProfileStatus(HUPROFILE_STATUS_CREATED);
        }
        userCacheService.updateHeliumUser(c);

        UserDetailRespItf r = new UserDetailRespItf();
        r.setUsername(c.user.getEmail());
        r.setAddress(u.getAddress());
        r.setCityCode(c.heliumUser.getCityCode());
        r.setCityName(c.heliumUser.getCityName());
        r.setCompany(u.getCompany());
        r.setCountry(c.heliumUser.getCountry());
        r.setFirstName(u.getFirstName());
        r.setLastName(u.getLastName());
        return r;
    }

    /**
     * Create a password lost request
     * @param req
     * @throws ITParseException
     */
    public void userLostReq(UserLostPassReqItf req)
    throws ITParseException {

        UserCacheService.UserCacheElement u = userCacheService.getUserByUsername(req.getUsername());
        if ( u == null ) {
            Tools.sleep((Math.abs(new Random().nextInt()) % 40));
            return;
        }

        // no reset on inactive users
        if ( ! u.user.isActive() ) {
            Tools.sleep((Math.abs(new Random().nextInt()) % 40));
            return;
        }

        HeliumPendingUser hpe = heliumPendingUserRepository.findOneHeliumPendingUserByUsernameAndType(
                u.heliumUser.getUserid(),
                HPU_TYPE_PLOSS
        );
        int retry = 0;
        if ( hpe != null ) {
            retry = hpe.getRetrial()+1;
            heliumPendingUserRepository.delete(hpe);
        }

        hpe = new HeliumPendingUser();
        hpe.setUsername(u.user.getId().toString());
        hpe.setInsertedAt(new Timestamp(Now.NowUtcMs()));
        hpe.setRetrial(retry);
        hpe.setValidationCode(RandomString.getRandomAZString(80));
        hpe.setType(HPU_TYPE_PLOSS);
        heliumPendingUserRepository.save(hpe);

        // Now we need to send an email

        String bodyEmail_en = "You just sent a request on "+consoleConfig.getHeliumConsoleName()+" to reset your password. This email is " +
                "a confirmation of your request. To change your password, you need to follow this link to terminate the process: \n\n" +
                ""+consoleConfig.getHeliumConsoleUrl()+"/front/passwordReset?key="+hpe.getValidationCode()+"\n\n" +
                "This link will be valid for the next 30 minutes.";

        executeEmail.execute(
                u.user.getEmail(),
                bodyEmail_en+this.bottomEmail_en,
                "["+consoleConfig.getHeliumConsoleName()+"] Password change request",
                consoleConfig.getHeliumMailFrom()
        );

        // add time delay to avoid analysis of the response time
        Tools.sleep((Math.abs(new Random().nextInt()) % 20));

    }

    public void userPasswordReset(UserPassResetReqItf req)
    throws ITParseException {

        String registrationCode = req.getValidationKey();

        // check input
        if ( registrationCode.length() != 80 ) {
            log.warn("Getting invalid validation code / ploss");
            throw new ITParseException("error_invalid");
        }

        HeliumPendingUser hpe = heliumPendingUserRepository.findOneHeliumPendingUserByValidationCode(
                registrationCode
        );
        if ( hpe == null ) {
            log.warn("Getting not existing validation code / ploss");
            throw new ITParseException("error_invalid");
        }

        if ( hpe.getType() != HPU_TYPE_PLOSS ) {
            log.warn("Getting a code for the wrong type of action / ploss");
            throw new ITParseException("error_invalid");
        }

        if ( hpe.getRetrial() > 10 ) {
            log.error("User with email "+hpe.getUsername()+" has made too many psloss retry");
            throw new ITParseException("error_invalid");
        }

        if( hpe.getInsertedAt().getTime() < (Now.NowUtcMs() - 30*60_000) ) {
            log.info("Validation code is outdated / ploss");
            throw new ITParseException("error_timeout");
        }

        if ( req.getPassword().length() < 12 ) {
            log.info("Password length too small");
            throw new ITParseException("error_password_size");
        }

        HttpHeaders heads = new HttpHeaders();
        heads.add("authorization", "Bearer "+consoleConfig.getChirpstackApiAdminKey());

        UpdateUserPasswordRequest upd = UpdateUserPasswordRequest.newBuilder()
                .setUserId(hpe.getUsername())
                .setPassword(req.getPassword())
                .build();
        try {

            byte[] respB = chirpstackApiAccess.execute(
                    HttpMethod.POST,
                    "/api.UserService/UpdatePassword",
                    null,
                    heads,
                    upd.toByteArray()
            );

        } catch ( ITRightException x ) {
            log.error("Impossible to change password - rights");
        } catch ( ITNotFoundException x ) {
            log.error("Impossible to change password - not found");
        }

        // delete validation code
        heliumPendingUserRepository.delete(hpe);
    }

    // ==================================================
    // alarm on Dc Balance
    // ==================================================

    @Autowired
    protected UserTenantRepository userTenantRepository;

    public void fireDcBalanceAlarm( HeliumTenant t ) {
        // Send an email to the Tenant Admin when the
        // Dc Balance is going under the defined level
        // warn & alarm message are the same.
        String bodyEmail_en = "Hello, \n\n" +
            "Your DC balance on "+consoleConfig.getHeliumConsoleName()+" is currently "+t.getDcBalance()+" for one of your tenants." +
            " This is above the alarm limit and why you get this email. Please make sure you will credit your tenant before reaching 0Dcs\n\n" +
            "Sincerely.";

        List<eu.heliumiot.console.jpa.db.UserTenant> us = userTenantRepository.findUserTenantByTenantIdAndIsAdmin(UUID.fromString(t.getTenantUUID()),true);
        if ( us.size() > 0 ) {
            us.forEach( u -> {
                UserCacheService.UserCacheElement uc = userCacheService.getUserById(u.getUserId().toString());
                executeEmail.execute(
                    uc.user.getEmail(),
                    bodyEmail_en+this.bottomEmail_en,
                    "["+consoleConfig.getHeliumConsoleName()+"] Low DC balance on one of your tenants",
                    consoleConfig.getHeliumMailFrom()
                );
            });
        } else {
            log.info("Try to fire Dc alarm on a tenant w/o admins");
        }
    }


    public void fireAdminDcBalanceAlarm( ) {
        // Send an email to the Tenant Admin when the
        // Dc Balance is going under the defined level
        // warn & alarm message are the same.
        String bodyEmail_en = "Hello, \n\n" +
            "The oui DC balance on "+consoleConfig.getHeliumConsoleName()+" is currently above the alarm limit." +
            " Make sure your burn more DCs into your wallet to ensure communications will continue\n\n" +
            "Sincerely.";

        executeEmail.execute(
            consoleConfig.getHeliumMailFrom(),
            bodyEmail_en+this.bottomEmail_en,
            "["+consoleConfig.getHeliumConsoleName()+"] Low OUI DC balance",
            consoleConfig.getHeliumMailFrom()
        );
    }




    // ==================================================
    // Admin task
    // ==================================================

    @Autowired
    protected TenantRepository tenantRepository;

    public List<UserListRespItf> getUserlist( String userId, String searchKey )
    throws ITParseException {
        ArrayList<UserListRespItf> resp = new ArrayList<>();
        if ( searchKey.length() < 3 || searchKey.length() > 25 ) return resp;

        // search user
        List<HeliumUser> u1;
        if ( searchKey.compareTo("***") == 0 ) {
            u1 = heliumUserRepository.findFirst20HeliumUsersByOrderByRegistrationTimeDesc();
        } else {
            u1 = heliumUserRepository.findHeliumUsersBySearchOrderByRegistration(searchKey);
        }
        if ( u1 != null ) {
            for (HeliumUser u : u1) {
                UserCacheService.UserCacheElement _u = userCacheService.getUserByUsername(u.getUsername());
                if ( _u != null ){
                    UserListRespItf r = new UserListRespItf();
                    r.setUserLogin(u.getUsername());
                    if ( u.getRegistrationTime() != null ) {
                        r.setRegistration(u.getRegistrationTime().getTime());
                    } else { r.setRegistration(0); }
                    r.setLastLogin(u.getLastSeen());
                    r.setDisable(!_u.user.isActive());
                    r.setTenants(new ArrayList<>());
                    List<eu.heliumiot.console.jpa.db.UserTenant> t1 = userTenantRepository.findUserTenantByUserId(UUID.fromString(u.getUserid()));
                    for (eu.heliumiot.console.jpa.db.UserTenant ut : t1) {
                        eu.heliumiot.console.jpa.db.Tenant t = tenantRepository.findOneTenantById(ut.getTenantId());
                        TenantEntry _t = new TenantEntry();
                        _t.setId(ut.getTenantId().toString());
                        _t.setAdmin(ut.isAdmin());
                        if ( t != null ) {
                            _t.setName(t.getName());
                        } else {
                            _t.setName("unknown");
                        }
                        r.getTenants().add(_t);
                    }
                    resp.add(r);
                } else {
                    log.warn("User "+u.getUsername()+" exist in helium_user but can't be find in cache");
                }
            }
            // make newer come first
            Collections.reverse(resp);
        }
        return resp;
    }


    @Autowired
    protected ApiKeyRepository apiKeyRepository;

    public void banUser( String adminId, String userName) throws ITNotFoundException, ITRightException {
        UserCacheService.UserCacheElement ad = userCacheService.getUserById(adminId);
        if (ad == null) {
            log.warn("Ban request from unknown admin "+adminId);
            throw new ITNotFoundException();
        }
        if (! ad.user.isAdmin()) {
            log.error("Ban request from a non admin user ("+ad.user.getEmail()+")");
            throw new ITRightException();
        }

        UserCacheService.UserCacheElement u = userCacheService.getUserByUsername(userName);
        if (u == null) {
            log.warn("Ban request for unknown user ("+userName+")");
            throw new ITNotFoundException();
        }
        log.info("Ban request for ("+u.user.getEmail()+")");

        // Ban the User steps
        // Reset Password and deactivate
        HttpHeaders heads = new HttpHeaders();
        heads.add("authorization", "Bearer "+consoleConfig.getChirpstackApiAdminKey());
        UpdateUserPasswordRequest upd = UpdateUserPasswordRequest.newBuilder()
            .setUserId(u.user.getId().toString())
            .setPassword(RandomString.getRandomAZString(25))
            .build();
        try {
            chirpstackApiAccess.execute(
                HttpMethod.POST,
                "/api.UserService/UpdatePassword",
                null,
                heads,
                upd.toByteArray()
            );
        } catch ( ITRightException x ) {
            log.error("Impossible to force reset password - rights");
        } catch ( ITNotFoundException | ITParseException x ) {
            log.error("Impossible to force reset password - not found/parse "+x.getMessage());
        }

        // Disable the user, then he won't be able to reset password
        io.chirpstack.restapi.User user = io.chirpstack.restapi.User.newBuilder()
            .setId(u.user.getId().toString())
            .setEmail(u.user.getEmail())
            .setIsActive(false)
            .setIsAdmin(false)
            .build();
        io.chirpstack.restapi.UpdateUserRequest cur = io.chirpstack.restapi.UpdateUserRequest.newBuilder()
            .setUser(user)
            .build();
        try {

            chirpstackApiAccess.execute(
                HttpMethod.POST,
                "/api.UserService/Update",
                null,
                heads,
                cur.toByteArray()
            );

        } catch ( ITRightException x ) {
            log.error("Impossible to update user in ban - rights");
        } catch ( ITNotFoundException x ) {
            log.error("Impossible to update user in ban - not found - "+x.getMessage());
        } catch ( ITParseException x ) {
            log.error("Parse error to update user in ban - parse");
        }

        // Get tenants where admin and purge the DCs
        List<eu.heliumiot.console.jpa.db.UserTenant> t1 = userTenantRepository.findUserTenantByUserId(u.user.getId());
        for (eu.heliumiot.console.jpa.db.UserTenant ut : t1) {
            if ( ut.isAdmin() ) {
                heliumTenantService.clearTenant(ut.getTenantId().toString());
                // clean the API keys
                // Rq : API keys can't be retrieved with an Admin API
                List<ApiKey> apis = apiKeyRepository.findApiKeyByTenantId(ut.getTenantId());
                if (apis != null) {
                    for (ApiKey api : apis) {
                        if (!api.isAdmin()) {
                            log.info("Ban - found API key to delete (" + api.getId() + ") for tenant " + ut.getTenantId().toString());
                            apiKeyRepository.delete(api);
                        } else {
                            log.error("************************************************");
                            log.error("!!! Why this user to ban have an admin api key ?");
                        }
                    }
                }
            } else {
                log.warn("*********************************");
                log.warn("A banned user is member of tenant ("+ut.getTenantId()+") but not admin");
            }
        }
    }

}
