package eu.heliumiot.console.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.BalanceTransaction;
import com.stripe.model.Charge;
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
import org.springframework.scheduling.annotation.Scheduled;
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
     *
     * @param userId
     * @return
     */
    public List<TransactionListRespItf> getTransactionHistory(String userId) {

        ArrayList<TransactionListRespItf> rs = new ArrayList<>();

        List<HeliumDcTransaction> ts = heliumDcTransactionRepository.findHeliumDcTransactionByUserUUIDOrderByCreatedAtDesc(userId);
        if (ts != null && ts.size() > 0) {
            for (HeliumDcTransaction t : ts) {
                TransactionListRespItf r = new TransactionListRespItf();
                r.setTransactionUUID(t.getId().toString());
                r.setType(t.getType());
                r.setDcs(t.getDcs());
                r.setCreateAt(t.getCreatedAt().getTime());
                if (t.getType() == 1) {
                    r.setCost(t.getDcs()*t.getDcsPrice());
                    r.setStatus(t.getStripeStatus());
                } else {
                    r.setCost(0.0);
                    r.setStatus("succeeded");
                }
                Tenant dst = heliumTenantService.getTenant(UUID.fromString(t.getTargetTenantUUID()));
                if (dst != null) {
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
     *
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
        if (user.heliumUser.getProfileStatus().compareToIgnoreCase(HUPROFILE_STATUS_COMPLETED) != 0)
            throw new ITRightException("strip_incomplete_profile");

        // check ownership
        UserTenant td = userTenantRepository.findOneUserByUserIdAndTenantId(UUID.fromString(userId), UUID.fromString(req.getTenantUUID()));
        if (td == null || td.isAdmin() == false) {
            log.warn("PurchaseDC - attempt to credit from not owned tenant by " + userId);
            throw new ITRightException("stripe_invalid_tenant");
        }

        // check amounts
        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(req.getTenantUUID());
        if (Math.round((req.getDcs() * ts.getDcPrice()) * 100.0) != Math.round(req.getCost() * 100.0)) {
            log.warn("PurchaseDC - attempt to send a wrong amount :" + (Math.round((req.getDcs() * ts.getDcPrice()) * 100.0)) + " vs " + (Math.round(req.getCost() * 100.0)));
            throw new ITParseException("stripe_invalid_amount");
        }
        if (req.getDcs() < ts.getDcMin()) {
            log.warn("PurchaseDC - attempt to send invalid DCs qty :" + (req.getDcs()) + " vs " + (ts.getDcMin()));
            throw new ITParseException("stripe_invalid_qty");
        }

        t.setType(HTRANSACTION_TYPE_STRIPE);
        t.setDcs(req.getDcs());
        t.setTargetTenantUUID(req.getTenantUUID());
        t.setUserUUID(userId);
        t.setCreatedAt(new Timestamp(Now.NowUtcMs()));
        try {
            if (userIp != null) {
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
        if (consoleConfig.getStripeCurrencyDefault().compareToIgnoreCase("usd") == 0) {
            amount = Math.round(req.getCost() * 100.0);
            if (amount < 50) throw new ITParseException("stripe_amount_too_low");
        } else if (consoleConfig.getStripeCurrencyDefault().compareToIgnoreCase("eur") == 0) {
            amount = Math.round(req.getCost() * 100.0);
            if (amount < 50) throw new ITParseException("stripe_amount_too_low");
        } else throw new ITParseException("stripe_invalid_back_currency");

        if (user.user.getEmail().compareToIgnoreCase("admin") == 0) {
            throw new ITRightException("stripe_invalid_email_admin");
        }

        Stripe.apiKey = consoleConfig.getStripeKeyPrivate();
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency(consoleConfig.getStripeCurrency())
                .setAmount(amount)
                // .setCustomer(user.heliumUser.getId().toString()) // @TODO need to be created ... let see that later
                .setReceiptEmail(user.user.getEmail())
                .addPaymentMethodType("card")
                .setStatementDescriptor("Helium DCs")
                .putMetadata("cust_id", user.heliumUser.getId().toString())
                .build();

        try {
            PaymentIntent paymentIntent = PaymentIntent.create(createParams);
            r.setStripeTxSecret(paymentIntent.getClientSecret());
            t.setStripeClientKey(paymentIntent.getClientSecret());
            t.setTransactionId(paymentIntent.getId());
        } catch (StripeException x) {
            log.error("Failed to create stripe intent " + x.getMessage());
            throw new ITParseException("stripe_failed_intent");
        }

        t.setStripeStatus("pending");
        t = heliumDcTransactionRepository.save(t);
        r.setTransactionUUID(t.getId().toString());
        r.setStripePublicKey(consoleConfig.getStripeKeyPublic());

        return r;
    }

    @Autowired
    HeliumParameterService heliumParameterService;

    protected HeliumDcTransaction updateTransaction(HeliumDcTransaction t)
    throws ITParseException {
        Stripe.apiKey = consoleConfig.getStripeKeyPrivate();

        if ( (Now.NowUtcMs() - t.getIntentTime()) > Now.ONE_HOUR ) {
            t.setCompleted(true);
            if ( t.getStripeStatus().compareToIgnoreCase("succeeded") != 0 ) {
                t.setStripeStatus("timeout");
            }
            return t;
        }

        boolean modified = false;
        try {
            PaymentIntent p = PaymentIntent.retrieve(t.getTransactionId());
            //ObjectMapper mapper = new ObjectMapper();
            //mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

            if ( p.getStatus() != null ) {
                if ( t.getStripeStatus().compareToIgnoreCase(p.getStatus()) != 0 ) {
                    t.setStripeStatus(p.getStatus());
                    modified = true;
                }
                if (p.getStatus().compareToIgnoreCase("succeeded") == 0) {

                    if ( ! heliumTenantService.processBalanceIncrease(t.getTargetTenantUUID(),t.getDcs()) ) {
                        log.error("!!! Impossible to increase Tenant balance even if stripe paid !!!");
                    }

                    // get Charged
                    if ( p.getLatestCharge() != null ) {

                        Charge c = Charge.retrieve(p.getLatestCharge());
                        //if ( ( c.getPaid() == true ) ) {
                        //    log.info("Paid transaction");
                        //}
                        if ( c.getCustomer() != null ) {
                            t.setStripeUser(c.getCustomer());
                        }

                        if ( c.getBalanceTransaction() != null ) {
                            BalanceTransaction tx = BalanceTransaction.retrieve(c.getBalanceTransaction());
                            t.setStripeCost(tx.getFee()); // no currency here need to look tate details, default account, in cent when euro / usd
                            t.setStripeCurrency(tx.getCurrency());
                            t.setStripeAmount(tx.getAmount()); // net is amount - fee and the unit is integer so for eur / usd must be devided by 100
                            t.setStripeCRate(tx.getExchangeRate().doubleValue());
                            HeliumParameter vat = heliumParameterService.getParameter(HeliumParameterService.PARAM_INVOICE_VAT);
                            t.setApplicableVAT(vat.getLongValue()/10_000.0);

                            // completed
                            t.setCompleted(true);
                            modified = true;

                        } else {
                            log.warn("No balance transaction for Payment Intent "+p.getId());
                        }

                    } else {
                        log.warn("Charge not found for Payment Intent "+p.getId());
                    }
                }
            }

        } catch (StripeException x) {
            log.error("Failed to refresh stripe intent " + x.getMessage());
            throw new ITParseException("stripe_failed_intent");
        //} catch (JsonProcessingException x) {
        //    log.error("Failed to process json " + x.getMessage());
        }

        return (modified)?t:null;
    }


    @Scheduled(fixedRateString = "${helium.transaction.intent.upd.scanPeriod}", initialDelay = 5_000)
    private void scanStripeTransactionJob() {
        List<HeliumDcTransaction> ts = heliumDcTransactionRepository.findHeliumDcTransactionByTypeAndIsCompletedAndIntentTimeGreaterThan(
                HTRANSACTION_TYPE_STRIPE,
                false,
                Now.NowUtcMs()-(20*60_000)-(24*3600_000)
        );
        for ( HeliumDcTransaction t : ts ) {
            try {
                synchronized (this) {
                    t = updateTransaction(t);
                    if (t != null) { // null when not modified
                        heliumDcTransactionRepository.save(t);
                    }
                }
            } catch ( ITParseException x ) {
            }
        }
    }


    /**
     * Force update the intent to have a better response time in the user experience
     * If not yet ready, the update batch will manage this
     * @param userId
     * @param transactionId
     * @throws ITRightException
     */
    public void updateStripeTransaction(String userId, String transactionId)
            throws ITRightException {

        // check the rights
        HeliumDcTransaction t = heliumDcTransactionRepository.findOneHeliumDcTransactionById(UUID.fromString(transactionId));
        if (t == null) throw new ITRightException();
        if (t.getUserUUID().compareToIgnoreCase(userId) != 0) {
            throw new ITRightException();
        }

        // Update the Intent
        synchronized (this) {
            try {
                t = updateTransaction(t);
                if (t != null) { // null when not modified
                    heliumDcTransactionRepository.save(t);
                }
            } catch ( ITParseException x ) {

            }
        }
    }

}