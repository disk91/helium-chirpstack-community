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
