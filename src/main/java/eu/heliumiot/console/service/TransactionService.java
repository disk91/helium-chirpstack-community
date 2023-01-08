package eu.heliumiot.console.service;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.api.interfaces.TransactionListRespItf;
import eu.heliumiot.console.api.interfaces.TransactionStripeReqItf;
import eu.heliumiot.console.api.interfaces.TransactionStripeRespItf;
import eu.heliumiot.console.jpa.db.*;
import eu.heliumiot.console.jpa.repository.HeliumDcTransactionRepository;
import eu.heliumiot.console.jpa.repository.UserTenantRepository;
import eu.heliumiot.console.tools.EncryptionHelper;
import fr.ingeniousthings.tools.ITParseException;
import fr.ingeniousthings.tools.ITRightException;
import fr.ingeniousthings.tools.Now;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static eu.heliumiot.console.service.HeliumTenantService.HTRANSACTION_TYPE_STRIPE;
import static eu.heliumiot.console.service.UserService.HUPROFILE_STATUS_COMPLETED;

@Service
public class TransactionService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    HeliumDcTransactionRepository heliumDcTransactionRepository;

    @Autowired
    HeliumTenantService heliumTenantService;

    /**
     * Get the User transaction history
     * @param userId
     * @return
     */
    public List<TransactionListRespItf> getTransactionHistory(String userId) {

        ArrayList<TransactionListRespItf> rs = new ArrayList<>();

        List<HeliumDcTransaction> ts = heliumDcTransactionRepository.findHeliumDcTransactionByUserUUIDOrderByCreatedAtDesc(userId);
        if ( ts != null && ts.size() > 0 ) {
            for ( HeliumDcTransaction t : ts ) {
                TransactionListRespItf r = new TransactionListRespItf();
                r.setTransactionUUID(t.getId().toString());
                r.setType(t.getType());
                r.setDcs(t.getDcs());
                r.setCreateAt(t.getCreatedAt().getTime());
                if ( t.getType() == 1 ) {
                    r.setCost(t.getStripeCost());
                } else r.setCost(0.0);
                Tenant dst = heliumTenantService.getTenant(UUID.fromString(t.getTargetTenantUUID()));
                if ( dst != null ) {
                    r.setTenantName(dst.getName());
                } else r.setTenantName("unknown");
                rs.add(r);
            }
        }
        return rs;
    }



    @Autowired
    private ConsoleConfig consoleConfig;

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private UserTenantRepository userTenantRepository;

    @Autowired
    private HeliumTenantSetupService heliumTenantSetupService;

    @Autowired
    protected EncryptionHelper encryptionHelper;

    private static Gson gson = new Gson();

    /**
     * Init a CB transaction request with stripe API
     * @param userId
     * @param req
     * @return
     * @throws ITRightException
     */
    public TransactionStripeRespItf initStripeTransaction(String userId, String userIp, TransactionStripeReqItf req)
    throws ITRightException, ITParseException {
        TransactionStripeRespItf r = new TransactionStripeRespItf();
        HeliumDcTransaction t = new HeliumDcTransaction();

        // checks
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();
        if (req.getDcs() <= 0) throw new ITRightException();
        if (user.heliumUser.getProfileStatus().compareToIgnoreCase(HUPROFILE_STATUS_COMPLETED) != 0) throw new ITRightException("strip_incomplete_profile");

        // check ownership
        UserTenant td = userTenantRepository.findOneUserByUserIdAndTenantId(UUID.fromString(userId), UUID.fromString(req.getTenantUUID()));
        if ( td == null || td.isAdmin() == false ) {
            log.warn("PurchaseDC - attempt to credit from not owned tenant by " + userId);
            throw new ITRightException("stripe_invalid_tenant");
        }

        // check amounts
        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(req.getTenantUUID());
        if ( Math.round((req.getDcs() * ts.getDcPrice())*100.0) != Math.round(req.getCost()*100.0) ) {
            log.warn("PurchaseDC - attempt to send a wrong amount :"+(Math.round((req.getDcs() * ts.getDcPrice())*100.0))+" vs "+(Math.round(req.getCost()*100.0)) );
            throw new ITParseException("stripe_invalid_amount");
        }
        if ( req.getDcs() < ts.getDcMin() ) {
            log.warn("PurchaseDC - attempt to send invalid DCs qty :"+(req.getDcs())+" vs "+(ts.getDcMin()) );
            throw new ITParseException("stripe_invalid_qty");
        }

        t.setType(HTRANSACTION_TYPE_STRIPE);
        t.setDcs(req.getDcs());
        t.setTargetTenantUUID(req.getTenantUUID());
        t.setUserUUID(userId);
        t.setCreatedAt(new Timestamp(Now.NowUtcMs()));
        try {
            if ( userIp != null ) {
                String eIp = encryptionHelper.encryptStringWithServerKey(userIp);
                t.setUserIP(eIp);
            } else t.setUserIP("");
        } catch (Exception e) {
            t.setUserIP("");
        }
        t.setDcsPrice(ts.getDcPrice());
        // encrypted fields
        t.setFirstName(user.heliumUser.getFirstName());
        t.setLastName(user.heliumUser.getLastName());
        t.setCompany(user.heliumUser.getCompany());
        t.setAddress(user.heliumUser.getAddress());
        t.setCityId(user.heliumUser.getCityCode());
        t.setCityName(user.heliumUser.getCityName());
        t.setCountry(user.heliumUser.getCountry());

        t.setIntentTime(Now.NowUtcMs());
        t.setCompleted(false);

        // Amount calculation
        // Stripe uses integer amount, it depends on the currency
        // for USD it is cents
        long amount = 0;
        if ( consoleConfig.getStripeCurrencyDefault().compareToIgnoreCase("usd") == 0) {
            amount = Math.round(req.getCost()*100.0);
            if ( amount < 50 ) throw new ITParseException("stripe_amount_too_low");
        } else if ( consoleConfig.getStripeCurrencyDefault().compareToIgnoreCase("eur") == 0) {
            amount = Math.round(req.getCost()*100.0);
            if ( amount < 50 ) throw new ITParseException("stripe_amount_too_low");
        } else throw new ITParseException("stripe_invalid_back_currency");

        if ( user.user.getEmail().compareToIgnoreCase("admin") == 0 ) {
            throw new ITRightException("stripe_invalid_email_admin");
        }

        Stripe.apiKey = consoleConfig.getStripeKeyPrivate();
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency(consoleConfig.getStripeCurrency())
                .setAmount(amount)
               // .setCustomer(user.heliumUser.getId().toString()) // @TODO need to be created ... let see than later
                .setReceiptEmail(user.user.getEmail())
                .addPaymentMethodType("card")
                .build();

        try {
            PaymentIntent paymentIntent = PaymentIntent.create(createParams);
            r.setStripeTxSecret(paymentIntent.getClientSecret());
            t.setStripeClientKey(paymentIntent.getClientSecret());
            t.setTransactionId(paymentIntent.getId());
        } catch ( StripeException x ) {
            log.error("Failed to create stripe intent "+x.getMessage());
            throw new ITParseException("stripe_failed_intent");
        }

        t = heliumDcTransactionRepository.save(t);
        r.setTransactionUUID(t.getId().toString());
        r.setStripePublicKey(consoleConfig.getStripeKeyPublic());

        return r;
    }


}
