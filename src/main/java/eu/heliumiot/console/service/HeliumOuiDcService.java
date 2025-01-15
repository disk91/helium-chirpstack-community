/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2024.
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

import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.api.interfaces.HeliumDcAmount;
import eu.heliumiot.console.api.interfaces.HeliumDcEscrow;
import eu.heliumiot.console.api.interfaces.HeliumDcOuis;
import eu.heliumiot.console.api.interfaces.HeliumDcOuisDataOuis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeliumOuiDcService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private String payer = null;
    private String escrowAccount = null;
    private String ouiAccount = null;

    private long currentDcAmount;
    public long getCurrentDcAmount() {
        return currentDcAmount;
    }

    @Autowired
    protected ConsoleConfig consoleConfig;

    @Autowired
    protected PrometeusService prometeusService;

    @PostConstruct
    private void initHeliumOuiDcService() {
        log.info("Init initHeliumOuiDcService");
        this.getInformations();

    }



    protected HttpEntity<String> createHeaders(boolean withAuth){
        HttpHeaders headers = new HttpHeaders();
        ArrayList<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON);
        headers.setAccept(accept);
        headers.add(HttpHeaders.USER_AGENT,"disk91_stats/1.0");
        HttpEntity<String> he = new HttpEntity<String>(headers);
        return he;
    }


    private void getInformations() {
        String url;
        RestTemplate restTemplate = new RestTemplate();
        if (this.payer == null) {
            try {
                HttpEntity<String> he = createHeaders(false);
                url = consoleConfig.getHeliumOuiDataServer() + "/ouis";
                ResponseEntity<List<HeliumDcOuisDataOuis>> responseEntity =
                        restTemplate.exchange(
                                url,
                                HttpMethod.GET,
                                he,
                                new ParameterizedTypeReference<List<HeliumDcOuisDataOuis>>() {}
                        );
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    if (responseEntity.getBody() != null) {
                        List<HeliumDcOuisDataOuis> h = responseEntity.getBody();
                        for (HeliumDcOuisDataOuis oui : h) {
                            if (oui.getOui() == consoleConfig.getHeliumRouteOui()) {
                                this.payer = oui.getPayer();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                this.payer = null;
                log.warn("Failed to contact service for DC balance");
            }
        }

        if (this.escrowAccount == null) {
            try {
                HttpEntity<String> he = createHeaders(false);
                url = consoleConfig.getHeliumOuiDataServer() + "/routerKeyToEscrowAccount/" + this.payer;
                ResponseEntity<HeliumDcEscrow> responseEntity =
                        restTemplate.exchange(
                                url,
                                HttpMethod.GET,
                                he,
                                HeliumDcEscrow.class
                        );
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    if (responseEntity.getBody() != null) {
                        HeliumDcEscrow h = responseEntity.getBody();
                        this.escrowAccount = h.escrowKey;
                        log.info("EscrowAccount is "+this.escrowAccount);
                    }
                }
            } catch (Exception e) {
                this.escrowAccount = null;
                log.warn("Failed to get EscrowAccount");
            }
        }
    }

    @Autowired
    protected UserService userService;

    private boolean alarmFired = false;
    @Scheduled(fixedDelay = 900_000, initialDelay = 5_000)
    protected void updateDcBalance() {
        String url;
        RestTemplate restTemplate = new RestTemplate();

        if ( this.escrowAccount != null ) {
            try {
                HttpEntity<String> he = createHeaders(false);
                url = consoleConfig.getHeliumOuiDataServer() + "/escrowAccountToDcAmount/" + this.escrowAccount;
                ResponseEntity<HeliumDcAmount> responseEntity =
                        restTemplate.exchange(
                                url,
                                HttpMethod.GET,
                                he,
                                HeliumDcAmount.class
                        );
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    if (responseEntity.getBody() != null) {
                        HeliumDcAmount h = responseEntity.getBody();
                        this.currentDcAmount = h.dcAmount;
                        prometeusService.setDcAmount(this.currentDcAmount);
                        log.debug("Dc balance "+h.dcAmount);
                        if ( consoleConfig.getHeliumOuiDcAlarm() > 0 && h.dcAmount < consoleConfig.getHeliumOuiDcAlarm() ) {
                            if( !alarmFired ) {
                                this.alarmFired = true;
                                userService.fireAdminDcBalanceAlarm();
                            }
                        } else {
                            // rearmed
                            this.alarmFired = false;
                        }
                    }
                }
            } catch (Exception e) {
                log.debug("Failed to get Dc balance");
            }
        } else {
            getInformations();
        }
    }

}
