/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2020.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package eu.heliumiot.console.service;

import eu.heliumiot.console.api.interfaces.TransactionStripeReqItf;
import eu.heliumiot.console.api.interfaces.UserSignUpReqItf;
import eu.heliumiot.console.jpa.db.HeliumPendingUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SafetyService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public boolean trustUserSignUp(UserSignUpReqItf req, String adr) {
        return true;
    }

    public boolean trustUserSignUpConfirmation(HeliumPendingUser hpe) {
        return true;
    }

    public boolean trustUserLogin(UserCacheService.UserCacheElement u, HttpServletRequest req) {
        return true;
    }

    public boolean trustStripeTransaction(UserCacheService.UserCacheElement u, String userIp, TransactionStripeReqItf req ) {
        return true;
    }
}
