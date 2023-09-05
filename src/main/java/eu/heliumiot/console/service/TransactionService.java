package eu.heliumiot.console.service;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.BalanceTransaction;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.api.interfaces.*;
import eu.heliumiot.console.jpa.db.*;
import eu.heliumiot.console.jpa.repository.HeliumDcTransactionRepository;
import eu.heliumiot.console.jpa.repository.UserTenantRepository;
import eu.heliumiot.console.tools.EncryptionHelper;
import fr.ingeniousthings.tools.ITParseException;
import fr.ingeniousthings.tools.ITRightException;
import fr.ingeniousthings.tools.Now;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static eu.heliumiot.console.service.HeliumTenantService.HTRANSACTION_TYPE_STRIPE;
import static eu.heliumiot.console.service.UserService.HUPROFILE_STATUS_COMPLETED;

@Service
public class TransactionService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final Object lock = new Object();

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

    /**
     * Get the list of stripe transaction in success from a past date
     * @param from
     * @return
     */
    public List<TransactionListRespItf> getPastStripeTransactions(long from) {

        List<HeliumDcTransaction> ts = heliumDcTransactionRepository.findHeliumDcTransactionByTypeAndIsCompletedAndIntentTimeGreaterThanOrderByIntentTimeDesc(
                HTRANSACTION_TYPE_STRIPE,
                true,
                from
        );

        ArrayList<TransactionListRespItf> rs = new ArrayList<>();
        if ( ts != null && ts.size() > 0 ) {
            for ( HeliumDcTransaction t : ts ) {
                TransactionListRespItf r = new TransactionListRespItf();
                r.setTransactionUUID(t.getId().toString());
                r.setType(t.getType());
                r.setDcs(t.getDcs());
                r.setCreateAt(t.getCreatedAt().getTime());
                r.setCost(t.getDcs()*t.getDcsPrice());
                r.setStatus(t.getStripeStatus());
                r.setTenantName(
                        encryptionHelper.decryptStringWithServerKey(t.getCompany()) + " / " +
                        encryptionHelper.decryptStringWithServerKey(t.getLastName())
                );
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

        // check if stripe is authorized
        if ( ! consoleConfig.isStripeEnable() ) throw new ITRightException("stripe_disable");

        // checks
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();
        if (req.getDcs() <= 0) throw new ITRightException();
        if (user.heliumUser.getProfileStatus().compareToIgnoreCase(HUPROFILE_STATUS_COMPLETED) != 0)
            throw new ITRightException("stripe_incomplete_profile");

        // check ownership
        UserTenant td = userTenantRepository.findOneUserByUserIdAndTenantId(UUID.fromString(userId), UUID.fromString(req.getTenantUUID()));
        if (td == null || td.isAdmin() == false) {
            log.warn("PurchaseDC - attempt to credit from not owned tenant by " + userId);
            throw new ITRightException("stripe_invalid_tenant");
        }

        // check amounts
        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(req.getTenantUUID(),true);
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
        if ( req.getMemo() != null ) {
            t.setMemo(encryptionHelper.encryptStringWithServerKey(req.getMemo()));
        } else t.setMemo(encryptionHelper.encryptStringWithServerKey(""));

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

    synchronized protected HeliumDcTransaction updateTransaction(HeliumDcTransaction t)
    throws ITParseException {
        Stripe.apiKey = consoleConfig.getStripeKeyPrivate();

        if ( t.isCompleted() ) return null;

        if ( (Now.NowUtcMs() - t.getIntentTime()) > Now.ONE_HOUR ) {
            t.setCompleted(true);
            if ( t.getStripeStatus().compareToIgnoreCase("succeeded") != 0 ) {
                t.setStripeStatus("timeout");
                log.info("Expiring one transaction ("+t.getTransactionId()+")");
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
                    log.info("Updating transaction ("+t.getTransactionId()+") to status "+p.getStatus());
                    t.setStripeStatus(p.getStatus());
                    modified = true;
                }
                if (p.getStatus().compareToIgnoreCase("succeeded") == 0) {
                    log.info("Terminating transaction ("+t.getTransactionId()+") with success");

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
                            if (tx.getExchangeRate() != null) {
                                t.setStripeCRate(tx.getExchangeRate().doubleValue());
                            } else {
                                log.warn("No exChange Rate for transaction "+tx.getId()+" with currency "+tx.getCurrency());
                                t.setStripeCRate(1.0);
                            }
                            HeliumParameter vat = heliumParameterService.getParameter(HeliumParameterService.PARAM_INVOICE_VAT);
                            t.setApplicableVAT(vat.getLongValue()/10_000.0);

                            // completed
                            t.setCompleted(true);
                            modified = true;

                            // credit DCs
                            if ( ! heliumTenantService.processBalanceIncrease(t.getTargetTenantUUID(),t.getDcs()) ) {
                                log.error("!!! Impossible to increase Tenant balance even if stripe paid !!!");
                            }

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
        synchronized (lock) {

            List<HeliumDcTransaction> ts = heliumDcTransactionRepository.findHeliumDcTransactionByTypeAndIsCompletedAndIntentTimeGreaterThanOrderByIntentTimeDesc(
                HTRANSACTION_TYPE_STRIPE,
                false,
                Now.NowUtcMs()-(20*60_000)
            );
            for ( HeliumDcTransaction t : ts ) {
                try {
                    t = updateTransaction(t);
                    if (t != null) { // null when not modified
                        heliumDcTransactionRepository.save(t);
                    }
                } catch ( ITParseException x ) {
                    log.error("Failure in updateTransaction "+x.getMessage());
                }
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

        // check if stripe is authorized
        if ( ! consoleConfig.isStripeEnable() ) throw new ITRightException("stripe_disable");

        // get transaction UUid
        UUID uuid = null;
        try {
            uuid = UUID.fromString(transactionId);
        } catch (Exception x) {
            // @todo is the api used with the right Id ?
            // apparently the payment intent id (pi_xxxxx) is used, none the UUID of transaction
            log.error("Transaction format invalid "+transactionId);
            throw new ITRightException();
        }
        if (uuid != null ) {

            synchronized (lock) {

                // check the rights
                HeliumDcTransaction t = heliumDcTransactionRepository.findOneHeliumDcTransactionById(UUID.fromString(transactionId));
                if (t == null) throw new ITRightException();
                if (t.getUserUUID().compareToIgnoreCase(userId) != 0) {
                    throw new ITRightException();
                }

                // Update the Intent
                try {
                    t = updateTransaction(t);
                    if (t != null) { // null when not modified
                        heliumDcTransactionRepository.save(t);
                    }
                } catch (ITParseException x) {
                    log.error("Failure in updateTransaction "+x.getMessage());
                }
            }

        }
    }

    // ======================================================
    // INVOICING
    // ======================================================

    public InvoiceSetupGetRespItf getInvoiceSetup() {

        InvoiceSetupGetRespItf r = new InvoiceSetupGetRespItf();
        HeliumParameter p = heliumParameterService.getParameter(HeliumParameterService.PARAM_COMPANY_NAME);
        r.setCompanyName(p.getStrValue());
        p = heliumParameterService.getParameter(HeliumParameterService.PARAM_COMPANY_ADDRESS);
        r.setCompanyAddress(p.getStrValue());
        p = heliumParameterService.getParameter(HeliumParameterService.PARAM_COMPANY_VAT);
        r.setCompanyVAT(p.getStrValue());
        p = heliumParameterService.getParameter(HeliumParameterService.PARAM_COMPANY_REGISTER);
        r.setCompanyRegistration(p.getStrValue());

        return r;
    }

    public void updateInvoiceSetup(InvoiceSetupPutReqItf i) throws ITParseException {

        HeliumParameter p;
        if ( i.getCompanyName() != null ) {
            p = heliumParameterService.getParameter(HeliumParameterService.PARAM_COMPANY_NAME);
            p.setStrValue(i.getCompanyName());
            heliumParameterService.flushParameter(p);
        } else throw new ITParseException();

        if ( i.getCompanyAddress() != null ) {
            p = heliumParameterService.getParameter(HeliumParameterService.PARAM_COMPANY_ADDRESS);
            p.setStrValue(i.getCompanyAddress());
            heliumParameterService.flushParameter(p);
        } else throw new ITParseException();

        if ( i.getCompanyVAT() != null ) {
            p = heliumParameterService.getParameter(HeliumParameterService.PARAM_COMPANY_VAT);
            p.setStrValue(i.getCompanyVAT());
            heliumParameterService.flushParameter(p);
        } else throw new ITParseException();

        if ( i.getCompanyRegistration() != null ) {
            p = heliumParameterService.getParameter(HeliumParameterService.PARAM_COMPANY_REGISTER);
            p.setStrValue(i.getCompanyRegistration());
            heliumParameterService.flushParameter(p);
        } else throw new ITParseException();

    }


    /**
     * Return the configuration of the transaction for managing front configuration
     * of the visible buttons
     * @return
     */
    public TransactionConfigRespItf getTransactionSetup() {
        TransactionConfigRespItf r = new TransactionConfigRespItf();
        r.setStripeEnable(consoleConfig.isStripeEnable());
        r.setTransferEnable(consoleConfig.isTransferEnable());
        return r;
    }


    // ======================================================
    // Generate invoice pdf
    // ======================================================

    public byte [] getInvoice(String userId, String transactionId)
    throws ITRightException, ITParseException {

        // check the rights
        HeliumDcTransaction t = heliumDcTransactionRepository.findOneHeliumDcTransactionById(UUID.fromString(transactionId));
        if (t == null) throw new ITRightException();

        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();

        if ( ! user.user.isAdmin() ) {
            if (t.getUserUUID().compareToIgnoreCase(userId) != 0) {
                throw new ITRightException();
            }
        }

        if ( t.getType() != HTRANSACTION_TYPE_STRIPE ) throw new ITRightException("invalid_type");
        if ( ! t.isCompleted() ) throw new ITRightException("incomplete");

        // all good
        HeliumParameter p;
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // company name
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 14);
            contentStream.newLineAtOffset(10, 750);
            p = heliumParameterService.getParameter(HeliumParameterService.PARAM_COMPANY_NAME);
            contentStream.showText(p.getStrValue());
            contentStream.endText();

            // invoice Id
            contentStream.moveTo(0,0);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
            contentStream.newLineAtOffset(300, 750);
            contentStream.showText("Invoice ID: "+t.getId().toString());
            contentStream.endText();

            // customer Id
            contentStream.moveTo(0,0);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
            contentStream.newLineAtOffset(300, 734);
            contentStream.showText("Customer ID: "+t.getUserUUID());
            contentStream.endText();

            // Get tenant
            if ( t.getTargetTenantUUID() != null ) {
                Tenant tenant = heliumTenantService.getTenant(UUID.fromString(t.getTargetTenantUUID()));
                if (tenant != null) {
                    contentStream.moveTo(0, 0);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
                    contentStream.newLineAtOffset(300, 718);
                    contentStream.showText("Tenant NAME: " + tenant.getName());
                    contentStream.endText();
                }
            }

            // date
            contentStream.moveTo(0,0);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLineAtOffset(300, 702);
            Date d = new Date(t.getIntentTime());
            Locale locale = new Locale("en", "US");
            SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MMMM-dd",locale);
            contentStream.showText("Date: "+sdf.format(d));
            contentStream.endText();

            // company address
            contentStream.moveTo(0,0);
            contentStream.beginText();
            contentStream.newLineAtOffset(10, 734);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            p = heliumParameterService.getParameter(HeliumParameterService.PARAM_COMPANY_ADDRESS);
            String [] ss = p.getStrValue().split("\n");
            for ( String s : ss ) {
                // relative position
                contentStream.showText(s.trim());
                contentStream.newLineAtOffset(0, -16);
            }
            contentStream.endText();

            int offset = 680;

            // Customer company
            if ( t.getCompany() != null ) {
                String s = encryptionHelper.decryptStringWithServerKey(t.getCompany());
                if ( s.length() > 0 ) {
                    contentStream.moveTo(0, 0);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(300, offset);
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                    try {
                        contentStream.showText(s);
                    } catch ( java.lang.IllegalArgumentException x ) {
                        contentStream.showText("Only ASCII chars supported");
                    }
                    contentStream.endText();
                    offset -= 16;
                }
            }

            // Customer name
            if ( t.getFirstName() != null || t.getLastName() != null ) {
                String first = "";
                String last = "";
                if ( t.getFirstName() != null ) first = encryptionHelper.decryptStringWithServerKey(t.getFirstName());
                if ( t.getLastName() != null ) last = encryptionHelper.decryptStringWithServerKey(t.getLastName());
                contentStream.moveTo(0, 0);
                contentStream.beginText();
                contentStream.newLineAtOffset(300, offset);
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                try {
                    contentStream.showText(first+' '+last);
                } catch ( java.lang.IllegalArgumentException x ) {
                    contentStream.showText("Only ASCII chars supported");
                }
                contentStream.endText();
                offset -= 16;
            }

            // Customer address
            if ( t.getAddress() != null ) {
                String ad = encryptionHelper.decryptStringWithServerKey(t.getAddress());
                String [] as = ad.split("\n");
                contentStream.moveTo(0, 0);
                contentStream.beginText();
                contentStream.newLineAtOffset(300, offset);
                for ( String a : as ) {
                    // relative position
                    try {
                        contentStream.showText(a.trim());
                    } catch ( java.lang.IllegalArgumentException x ) {
                        contentStream.showText("Only ASCII chars supported");
                    }
                    contentStream.newLineAtOffset(0, -16);
                    offset -= 16;
                }
                contentStream.endText();
            }

            // Customer city and CP
            if ( t.getCityName() != null || t.getCityId() != null || t.getCountry() != null ) {
                String cn = ""; if ( t.getCityName() != null ) cn = t.getCityName();
                String ci = ""; if ( t.getCityId() != null ) ci = t.getCityId();
                String cc = ""; if ( t.getCountry() != null ) cc = t.getCountry();
                contentStream.moveTo(0, 0);
                contentStream.beginText();
                contentStream.newLineAtOffset(300, offset);
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                try {
                    contentStream.showText(cn+", "+ci);
                } catch ( java.lang.IllegalArgumentException x ) {
                    contentStream.showText("Only ASCII chars supported");
                }
                offset -= 16;
                contentStream.newLineAtOffset(0, -16);
                contentStream.showText(cc);
                contentStream.endText();
                offset -= 16;
            }

            // headers
            int qtyPos = 60;
            int descPos = 120;
            int pricePos = 380;
            int totalPos = 460;
            int totalSz  = 80;
            int tableStart = 500;
            int tableHeight = 200;
            int summaryHeight = 50;

            contentStream.moveTo(0,0);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 10);
            contentStream.newLineAtOffset(qtyPos, tableStart);
            contentStream.showText("Quantity");
            contentStream.newLineAtOffset(descPos-qtyPos, 0);
            contentStream.showText("Description");
            contentStream.newLineAtOffset(pricePos-descPos, 0);
            contentStream.showText("Unit price");
            contentStream.newLineAtOffset(totalPos-pricePos, 0);
            contentStream.showText("Total");
            contentStream.endText();

            contentStream.setStrokingColor(Color.BLACK);
            contentStream.setNonStrokingColor(10/255.0f);
            contentStream.addRect(qtyPos-5,tableStart+12,totalPos-qtyPos+totalSz,-18);
            contentStream.addRect(qtyPos-5,tableStart+12,totalPos-qtyPos+totalSz,-tableHeight);
            contentStream.addRect(descPos-5,tableStart+12,0,-tableHeight);
            contentStream.addRect(pricePos-5,tableStart+12,0,-tableHeight);
            contentStream.addRect(totalPos-5,tableStart+12,0,-tableHeight);
            contentStream.addRect(pricePos-5,tableStart+12-tableHeight,totalPos-pricePos+totalSz,-summaryHeight);
            contentStream.addRect(totalPos-5,tableStart+12-tableHeight,0,-summaryHeight);
            contentStream.stroke();

            // DCs line
            contentStream.moveTo(0,0);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            contentStream.newLineAtOffset(qtyPos, tableStart-20);
            contentStream.showText(""+String.format("%,d",t.getDcs()));
            contentStream.newLineAtOffset(descPos-qtyPos, 0);
            contentStream.showText("Data Credits for helium communication (with taxes)");
            contentStream.newLineAtOffset(pricePos-descPos, 0);
            contentStream.showText("$"+String.format("%,.5f",t.getDcsPrice()));
            contentStream.newLineAtOffset(totalPos-pricePos, 0);
            contentStream.showText("$"+String.format("%,.2f",t.getDcsPrice()*t.getDcs()));
            contentStream.endText();

            // Total in invoice currency w/o tax
            double ht;
            double vat;
            int cur;
            if ( t.getStripeCurrency().compareToIgnoreCase("eur") == 0 ) {
                ht = (t.getStripeAmount()/100.0) / (1.0 + t.getApplicableVAT());
                vat = ht * t.getApplicableVAT();
                cur = 1; //euro
            } else if ( t.getStripeCurrency().compareToIgnoreCase("usd") == 0 ) {
                ht = (t.getStripeAmount()/100.0) / (1.0 + t.getApplicableVAT());
                vat = ht * t.getApplicableVAT();
                cur = 2; //usd
            } else {
                ht = (t.getStripeAmount()/100.0) / (1.0 + t.getApplicableVAT());
                vat = (t.getStripeAmount()/100.0) * t.getApplicableVAT();
                cur = 0; //unknown
            }

            contentStream.moveTo(0,0);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 9);
            contentStream.newLineAtOffset(pricePos, tableStart-tableHeight);
            contentStream.showText("Total w/o taxes");
            contentStream.newLineAtOffset(totalPos-pricePos, 0);
            contentStream.setFont(PDType1Font.TIMES_BOLD, 9);
            switch (cur) {
                case 1: // euro
                    contentStream.showText(""+String.format("%,.2f",ht)+"€");
                    break;
                case 2: // usd
                    contentStream.showText("$"+String.format("%,.2f",ht));
                    break;
                default:
                    contentStream.showText("unsupported currency");
                    break;
            }
            contentStream.endText();

            // taxes
            contentStream.moveTo(0,0);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 9);
            contentStream.newLineAtOffset(pricePos, tableStart-tableHeight-12);
            contentStream.showText("Taxes ("+String.format("%,.2f",t.getApplicableVAT()*100)+"%)");
            contentStream.newLineAtOffset(totalPos-pricePos, 0);
            contentStream.setFont(PDType1Font.TIMES_BOLD, 9);
            switch (cur) {
                case 1: // euro
                    contentStream.showText(""+String.format("%,.2f",vat)+"€");
                    break;
                case 2: // usd
                    contentStream.showText("$"+String.format("%,.2f",vat));
                    break;
                default:
                    contentStream.showText("unsupported currency");
                    break;
            }
            contentStream.endText();

            // total with tax
            contentStream.moveTo(0,0);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 9);
            contentStream.newLineAtOffset(pricePos, tableStart-tableHeight-24);
            contentStream.showText("Total with taxes");
            contentStream.newLineAtOffset(totalPos-pricePos, 0);
            contentStream.setFont(PDType1Font.TIMES_BOLD, 9);
            switch (cur) {
                case 1: // euro
                    contentStream.showText(""+String.format("%,.2f",(t.getStripeAmount()/100.0))+"€");
                    break;
                case 2: // usd
                    contentStream.showText("$"+String.format("%,.2f",(t.getStripeAmount()/100.0)));
                    break;
                default:
                    contentStream.showText("unsupported currency");
                    break;
            }
            contentStream.endText();

            // Other information
            contentStream.moveTo(0,0);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 9);
            contentStream.newLineAtOffset(descPos, tableStart-tableHeight);
            switch (cur) {
                case 1: // euro
                    contentStream.showText("Stripe cost (included) "+String.format("%,.2f",(t.getStripeCost()/100.0))+"€");
                    break;
                case 2: // usd
                    contentStream.showText("Stripe cost (included) $"+String.format("%,.2f",(t.getStripeCost()/100.0)));
                    break;
                default:
                    contentStream.showText("unsupported currency");
                    break;
            }
            contentStream.endText();

            contentStream.moveTo(0,0);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 9);
            contentStream.newLineAtOffset(descPos, tableStart-tableHeight-12);
            contentStream.showText("Currency conversion rate applied by stripe: "+String.format("%,.4f",(t.getStripeCRate())));
            contentStream.endText();

            contentStream.moveTo(0,0);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 9);
            contentStream.newLineAtOffset(descPos, tableStart-tableHeight-24);
            contentStream.showText("Invoice paid online");
            contentStream.endText();

            if ( t.getMemo() != null ) {

                String memo = encryptionHelper.decryptStringWithServerKey(t.getMemo());
                if ( memo.length() > 0 ) {
                    contentStream.moveTo(0,0);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_BOLD, 9);
                    contentStream.newLineAtOffset(qtyPos, tableStart-tableHeight-64);
                    try {
                        contentStream.showText("Customer additional information : "+memo);
                    } catch ( java.lang.IllegalArgumentException x ) {
                        contentStream.showText("Only ASCII chars supported");
                    }
                    contentStream.endText();
                }
            }


            // Seller information
            contentStream.moveTo(0,0);
            contentStream.setStrokingColor(Color.BLACK);
            contentStream.addRect(10,30,580,1);
            contentStream.fill();
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            contentStream.newLineAtOffset(10, 10);
            p = heliumParameterService.getParameter(HeliumParameterService.PARAM_COMPANY_VAT);
            contentStream.showText("VAT # "+p.getStrValue());
            contentStream.newLineAtOffset(0, 10);
            p = heliumParameterService.getParameter(HeliumParameterService.PARAM_COMPANY_REGISTER);
            contentStream.showText(p.getStrValue());
            contentStream.endText();

            contentStream.close();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            document.close();
            return out.toByteArray();
        } catch (IOException x) {
            throw new ITParseException("pdf_failure");
        }

    }

}