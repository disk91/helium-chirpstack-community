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

import eu.heliumiot.console.api.interfaces.LoginReqItf;
import eu.heliumiot.console.api.interfaces.LoginRespItf;
import eu.heliumiot.console.chirpstack.ChirpstackApiAccess;
import fr.ingeniousthings.tools.ITNotFoundException;
import fr.ingeniousthings.tools.ITParseException;
import fr.ingeniousthings.tools.ITRightException;
import io.chirpstack.restapi.LoginRequest;
import io.chirpstack.restapi.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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

        // check for user existence, no need to go further otherwise
        // @Todo check user existance, use of a cache is welcome..
        // also a wy to block too many login attempt for security later
        // also a way to add a 2FA access

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

}
