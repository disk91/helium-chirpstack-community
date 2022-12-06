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
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.api.interfaces.LoginReqItf;
import eu.heliumiot.console.api.interfaces.LoginRespItf;
import eu.heliumiot.console.api.interfaces.UserDetailRespItf;
import eu.heliumiot.console.chirpstack.ChirpstackApiAccess;
import eu.heliumiot.console.jpa.db.HeliumUser;
import eu.heliumiot.console.jpa.db.User;
import eu.heliumiot.console.jpa.repository.HeliumUserRepository;
import eu.heliumiot.console.jpa.repository.UserRepository;
import fr.ingeniousthings.tools.*;
import fr.ingeniousthings.tools.Claims;
import io.chirpstack.restapi.LoginRequest;
import io.chirpstack.restapi.LoginResponse;
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
import java.util.ArrayList;
import java.util.Date;
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
                h = createHeliumUserFromUser(u);
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

    protected HeliumUser createHeliumUserFromUser(User u) {
        HeliumUser h = new HeliumUser();
        h.setUserid(u.getId().toString());
        h.setUsername(u.getEmail());
        h.setProfileStatus(HUPROFILE_STATUS_CREATED);
        h.setUserSecret(RandomString.getRandomAZString(64));
        h.setDefaultOffer("default");
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
