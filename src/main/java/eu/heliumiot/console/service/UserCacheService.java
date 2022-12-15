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

import eu.heliumiot.console.jpa.db.User;
import eu.heliumiot.console.jpa.db.*;
import eu.heliumiot.console.jpa.repository.HeliumUserRepository;
import eu.heliumiot.console.jpa.repository.UserRepository;
import fr.ingeniousthings.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

import static eu.heliumiot.console.service.UserService.HUPROFILE_STATUS_CREATED;

@Service
public class UserCacheService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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

    protected String bottomEmail_en;

    private ObjectCache<String, UserCacheElement> userCache;
    @PostConstruct
    private void initUserCacheService() {
        log.info("initUserCacheService initialization");
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
}
