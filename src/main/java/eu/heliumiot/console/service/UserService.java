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
import eu.heliumiot.console.jpa.db.HeliumPendingUser;
import eu.heliumiot.console.jpa.db.HeliumUser;
import eu.heliumiot.console.jpa.db.User;
import eu.heliumiot.console.jpa.repository.HeliumPendingUserRepository;
import eu.heliumiot.console.jpa.repository.HeliumUserRepository;
import eu.heliumiot.console.jpa.repository.UserRepository;
import eu.heliumiot.console.tools.EncryptionHelper;
import eu.heliumiot.console.tools.ExecuteEmail;
import fr.ingeniousthings.tools.*;
import fr.ingeniousthings.tools.Claims;
import io.chirpstack.restapi.*;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.validator.routines.EmailValidator;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Email;
import java.security.Key;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ConsoleConfig consoleConfig;
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected HeliumUserRepository heliumUserRepository;


    // ===================================================
    // Cache Management
    // ===================================================
    public class UserCacheElement {
        public User user;
        public HeliumUser heliumUser;
    }

    private ObjectCache<String, UserCacheElement> userCache;
    @PostConstruct
    private void initUserService() {
        log.info("initUserService initialization");
        this.userCache = new ObjectCache<String, UserCacheElement>("UserCache", 2000) {
            @Override
            public void onCacheRemoval(String key, UserCacheElement obj) {
                // nothing to do, readOnly
            }
        };
    }

    public UserCacheElement getUserByUsername(String userName) {
        User u = userRepository.findOneUserByEmail(userName);
        if ( u == null ) return null;
        return getUserById(u.getId().toString());
    }

    // get user if exist from cache, then from DB
    public UserCacheElement getUserById(String userId) {
        UserCacheElement r = this.userCache.get(userId);
        if ( r == null ) {
            // search in db
            User u = userRepository.findOneUserById(UUID.fromString(userId));
            if ( u == null ) return null;

            // search the heliumUser
            HeliumUser h = heliumUserRepository.findOneHeliumUserByUserid(u.getId().toString());
            if ( h == null ) {
                // happen on initialization ...
                h = createHeliumUserFromUser(u,"default");
            }

            r = new UserCacheElement();
            r.user = u;
            r.heliumUser = h;
            this.userCache.put(r,userId);
        }
        return r;
    }

    // ===================================================
    // Helium User Management
    // ===================================================

    public static final String HUPROFILE_STATUS_CREATED="created";
    public static final String HUPROFILE_STATUS_COMPLETED="completed";


    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    protected HeliumUser createHeliumUserFromUser(User u, String offer) {
        HeliumUser h = new HeliumUser();
        h.setUserid(u.getId().toString());
        h.setUsername(u.getEmail());
        h.setProfileStatus(HUPROFILE_STATUS_CREATED);
        h.setUserSecret(RandomString.getRandomAZString(64));
        h.setDefaultOffer(offer);
        h = heliumUserRepository.save(h);
        return h;
    }

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

    /**
     * Create a user account with email validation, the user creation in chirpstack is really created once the email validation
     * has been made
     * @param req
     * @return
     * @throws ITNotFoundException
     * @throws ITParseException
     */
    public UserSignUpRespItf userSignup(UserSignUpReqItf req)
    throws ITNotFoundException,ITParseException
    {
        UserSignUpRespItf r = new UserSignUpRespItf();
        // verify informations
        //  is conditions accepted ?
        if ( ! req.isConditionsAccepted() ) {
            throw new ITParseException("error_conditions");
        }

        // verify email
        if ( ! EmailValidator.getInstance().isValid(req.getUsername()) ) {
            // bypass for helium.foundation not recognized by EmailValidator
            if ( ! req.getUsername().matches("^[A-Z0-9._%+-]+@helium.foundation") )
               throw new ITParseException("error_invalid_email");
        }

        // verify password
        if ( req.getPassword().length() < 12 ) {
            throw new ITParseException("error_password_size");
        }

        // verify tenantName
        if ( req.getTenantName().length() < 3 ) {
            throw new ITParseException("error_tenant_size");
        }

        // verify email does not exists
        if ( this.getUserByUsername(req.getUsername()) != null ) {
            // return success but nothing will be made, the
            // email already exist but we do not want this infomration
            // to be publicly reported
            r.setErrorMessage("success");
            try {
                Thread.sleep(8+((new Random().nextInt()) % 7));
            }catch (InterruptedException x){};
            return r;
        }

        // @todo verify the invitation code
        String profile = "default";
        if  (req.getInviteCode().length() > 0) {
            // process verification ...
            // set profile based on the invitation code verification
        }

        // All test ok, prepare to the next phase : the user will only be
        // really created once we get the email confirmation
        // @todo chercher s'il n'existe pas deja, sinon faire le menage
        // chiffre les password dans la transition
        //HeliumPendingUser ....
        HeliumPendingUser hpe = heliumPendingUserRepository.findOneHeliumPendingUserByUsername(req.getUsername());
        int retry = 0;
        if ( hpe != null ) {
            retry = hpe.getRetrial()+1;
            heliumPendingUserRepository.delete(hpe);
        }

        hpe = new HeliumPendingUser();
        hpe.setUsername(req.getUsername());
        hpe.setInsertedAt(new Timestamp(Now.NowUtcMs()));
        hpe.setRetrial(retry);
        hpe.setTenantName(req.getTenantName());
        hpe.setOfferName(profile);
        hpe.setValidationCode(RandomString.getRandomAZString(80));
        hpe.setUserPassword(encryptionHelper.encryptStringWithServerKey(req.getPassword()));
        heliumPendingUserRepository.save(hpe);

        // Now we need to send an email
        String bottomEmail_en = "\n\n";
        bottomEmail_en+="This message has been sent automatically from the "+consoleConfig.getHeliumConsoleName()+" service and sent according to your request." +
                "If you don't think you have been involved, just ignore this message.";
        bottomEmail_en+="\n\n";
        bottomEmail_en+=""+consoleConfig.getHeliumConsoleUrl() +" - Helium Provider";

        String bodyEmail_en = "You just have requested to signup on "+consoleConfig.getHeliumConsoleName()+". This email is " +
                "a confirmation of your request. To validate your registration, you need to follow this link to terminate the process: \n\n" +
                ""+consoleConfig.getHeliumConsoleUrl()+"/front/signupValidation?key="+hpe.getValidationCode()+"\n\n" +
                "This link will be valid for the next 15 minutes.";

        executeEmail.execute(
                hpe.getUsername(),
                bodyEmail_en+bottomEmail_en,
                "["+consoleConfig.getHeliumConsoleName()+"] Email registration confirmation",
                consoleConfig.getHeliumMailFrom()
        );

        // add time delay to avoid analysis of the response time
        try {
            Thread.sleep(10+((new Random().nextInt()) % 10));
        }catch (InterruptedException x){};
        r.setErrorMessage("success");
        return r;
    }

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

        if ( hpe.getRetrial() > 10 ) {
            log.error("User with email "+hpe.getUsername()+" has made too many retry");
            throw new ITParseException("error_invalid");
        }

        if( hpe.getInsertedAt().getTime() < (Now.NowUtcMs() - 30*60_000) ) {
            log.info("Validation code is outdated");
            throw new ITParseException("error_timeout");
        }

        HttpHeaders heads = new HttpHeaders();
        heads.add("authorization", "Bearer "+consoleConfig.getChirpstackApiAdminKey());

        // Lets create the tenant first
        Tenant tenant = Tenant.newBuilder()
                .setName(hpe.getTenantName())
                .setDescription("Default user tenant")
                .setCanHaveGateways(false)
                .setMaxDeviceCount(0)
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
            log.error("Impossible to create new tenant - rights");
        } catch ( InvalidProtocolBufferException x ) {
            log.error("Impossible to create new tenant - protobuf");
        }

        if ( tenantId == null ) throw new ITParseException("error_internal");



        // We got it, let's create the user and the tenant
        String userId = null;

        io.chirpstack.restapi.User user = io.chirpstack.restapi.User.newBuilder()
                .setEmail(hpe.getUsername())
                .setIsActive(true)
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
                UserCacheElement u = this.getUserById(resp.getId());
                if ( u != null ) {
                    u.heliumUser.setDefaultOffer(hpe.getOfferName());
                    u.heliumUser.setConditionValidation(true);
                    u.heliumUser.setRegistrationTime(hpe.getInsertedAt());
                    heliumUserRepository.save(u.heliumUser);
                }
            }

        } catch ( ITRightException x ) {
            log.error("Impossible to create new user - rights");
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
                log.error("Impossible to delete tenant - rights");
            }
            throw new ITParseException("error_internal");
        }

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
        UserCacheElement u = this.getUserByUsername(request.getUsername());
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
    public UserDetailRespItf getUserdetails(String userid)
    throws ITNotFoundException, ITRightException {
        UserCacheElement c =  this.getUserById(userid);
        if ( c == null ) throw new ITNotFoundException();
        UserDetailRespItf r = new UserDetailRespItf();
        r.setUsername(c.user.getEmail());
        return r;
    }

}
