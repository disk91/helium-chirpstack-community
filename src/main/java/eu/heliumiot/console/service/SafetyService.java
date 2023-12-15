package eu.heliumiot.console.service;

import eu.heliumiot.console.api.interfaces.TransactionStripeReqItf;
import eu.heliumiot.console.api.interfaces.UserSignUpReqItf;
import eu.heliumiot.console.jpa.db.HeliumDevice;
import eu.heliumiot.console.jpa.db.HeliumPendingUser;
import eu.heliumiot.console.jpa.repository.HeliumDeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SafetyService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ArrayList<String> torIps;
    // manually refuse some addresses
    private final String [] ipsToBan = { "1.1.1.1", };
    private final String [] untrustedDomains = { "hotmail", "outlook", "gmail" };

    @PostConstruct
    private void initSafetyService() {
        // get the ToR exit server list from project
        // https://check.torproject.org/torbulkexitlist
        torIps = new ArrayList<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            ArrayList<MediaType> accept = new ArrayList<>();
            accept.add(MediaType.TEXT_HTML);
            headers.setAccept(accept);
            headers.add(HttpHeaders.USER_AGENT,"disk91_agent/1.0");
            HttpEntity<String> he = new HttpEntity<String>(headers);

            RestTemplate restTemplate = new RestTemplate();
            String url = "https://check.torproject.org/torbulkexitlist";
            ResponseEntity<String> responseEntity =
                restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    he,
                    String.class
                );
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                if (responseEntity.getBody() != null) {
                    String sIps = responseEntity.getBody();
                    String[] ips = sIps.split("\n");
                    Pattern pattern = Pattern.compile("^([0-9]{1,3}\\.){3}[0-9]{1,3}$");
                    int i = 0;
                    for ( String ip : ips ) {
                        if ( ip != null && ip.length() > 4 ) {
                            Matcher matcher = pattern.matcher(ip);
                            if ( matcher.matches() ) {
                                i++;
                                torIps.add(ip);
                            }
                        }
                    }
                    log.info("* "+i+" tor exit IP loaded");
                    Collections.addAll(torIps, ipsToBan);
                }
            }
        } catch (Exception e) {
            log.warn("Failed to load the tor exit servers");
        }

    }


    public boolean trustUserSignUp(UserSignUpReqItf req, String adr) {
        return true;
    }

    public boolean trustUserSignUpConfirmation(HeliumPendingUser hpe) {
        return true;
    }

    public boolean trustUserLogin(UserCacheService.UserCacheElement u, HttpServletRequest req) {
        if (isBanedIp(req.getHeader("x-real-ip"))) {
            log.warn(">>> The user "+u.user.getEmail()+" use a Tor of Banned IP");
        }
        return true;
    }


    @Autowired
    protected HeliumDeviceRepository heliumDeviceRepository;

    public boolean trustStripeTransaction(UserCacheService.UserCacheElement u, String userIp, TransactionStripeReqItf req ) {
        if ( this.isBanedIp(userIp) ) {
            log.warn(">>> Blocked strip transaction for user "+u.user.getEmail()+" request from ToR or banned IP");
            return false;
        }
        if ( this.isUntrustedEmail(u.user.getEmail()) ) {
            // classical pattern... an email like hotmail or gmail with a zero devices owned
            Slice<HeliumDevice> devices = heliumDeviceRepository.findHeliumDeviceByTenantUUID(req.getTenantUUID(), PageRequest.of(0, 5));
            if ( devices.getSize() == 0 ) {
                log.warn(">>> Blocked stripe transaction for user "+u.user.getEmail()+" no devices");
                return false;
            }
        }
        return true;
    }

    // ==========================================================
    // Helpers

    protected boolean isUntrustedEmail(String email) {
        for ( String domain: untrustedDomains) {
            if ( email.toLowerCase().matches(".*"+domain+".*") ) return true;
        }
        return false;
    }

    protected boolean isBanedIp(String ip) {
        for ( String _ip : torIps) {
            if ( ip.compareTo(_ip) == 0 ) return true;
        }
        return false;
    }

}
