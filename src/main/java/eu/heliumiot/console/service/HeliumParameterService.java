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

import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.jpa.db.HeliumParameter;
import eu.heliumiot.console.jpa.repository.HeliumParameterRepository;
import fr.ingeniousthings.tools.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class HeliumParameterService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String PARAM_DEVICE_LASTSCAN_TIME = "deviceLastScanTime";
    public static final String PARAM_INVOICE_VAT = "invoiceVat";

    public static final String PARAM_COMPANY_NAME = "companyName";
    public static final String PARAM_COMPANY_ADDRESS = "companyAddress";
    public static final String PARAM_COMPANY_VAT = "companyVat";
    public static final String PARAM_COMPANY_REGISTER = "companyRegistration";

    public static final String PARAM_HELIUM_OUI = "previousOui";
    public static final String PARAM_HELIUM_NETID = "previousNetId";

    public static final String PARAM_MQTT_CLIENT_ID = "mqttClientId";
    public static final String PARAM_MQTT_HELIUM_CLIENT_ID = "mqttHeliumClientId";

    public static final String PARAM_USER_COND_CURRENT = "userConditionCurrentVersion";


    @Autowired
    protected ConsoleConfig consoleConfig;

    @Autowired
    protected HeliumParameterRepository heliumParameterRepository;

    @PostConstruct
    private void initHeliumParameterService() {
        log.info("Setup the non existing parameters with default value");
        HeliumParameter deviceScanTime = heliumParameterRepository.findOneHeliumParameterByParameterName(PARAM_DEVICE_LASTSCAN_TIME);
        if ( deviceScanTime == null ) {
            deviceScanTime = new HeliumParameter();
            deviceScanTime.setParameterName(PARAM_DEVICE_LASTSCAN_TIME);
            deviceScanTime.setLongValue(0);
            deviceScanTime.setStrValue("");
            heliumParameterRepository.save(deviceScanTime);
        }
        HeliumParameter invoiceVat = heliumParameterRepository.findOneHeliumParameterByParameterName(PARAM_INVOICE_VAT);
        if ( invoiceVat == null ) {
            invoiceVat = new HeliumParameter();
            invoiceVat.setParameterName(PARAM_INVOICE_VAT);
            invoiceVat.setLongValue(consoleConfig.getHeliumBillingVat()); // 20.00% default
            invoiceVat.setStrValue("");
            heliumParameterRepository.save(invoiceVat);
        } else {
            // refresh the VAT value when needed
            if ( invoiceVat.getLongValue() != consoleConfig.getHeliumBillingVat()) {
                invoiceVat.setLongValue(consoleConfig.getHeliumBillingVat());
                heliumParameterRepository.save(invoiceVat);
            }
        }
        HeliumParameter companyName = heliumParameterRepository.findOneHeliumParameterByParameterName(PARAM_COMPANY_NAME);
        if ( companyName == null ) {
            companyName = new HeliumParameter();
            companyName.setParameterName(PARAM_COMPANY_NAME);
            companyName.setLongValue(0);
            companyName.setStrValue("Acme");
            heliumParameterRepository.save(companyName);
        }
        HeliumParameter companyAddress = heliumParameterRepository.findOneHeliumParameterByParameterName(PARAM_COMPANY_ADDRESS);
        if ( companyAddress == null ) {
            companyAddress = new HeliumParameter();
            companyAddress.setParameterName(PARAM_COMPANY_ADDRESS);
            companyAddress.setLongValue(0);
            companyAddress.setStrValue("1 Bd Helium Rd \n San Francisco, CA \n U.S.A. \n Phone : 001 xx xx xx\n");
            heliumParameterRepository.save(companyAddress);
        }
        HeliumParameter companyVat = heliumParameterRepository.findOneHeliumParameterByParameterName(PARAM_COMPANY_VAT);
        if ( companyVat == null ) {
            companyVat = new HeliumParameter();
            companyVat.setParameterName(PARAM_COMPANY_VAT);
            companyVat.setLongValue(0);
            companyVat.setStrValue("FR123445678901234");
            heliumParameterRepository.save(companyVat);
        }
        HeliumParameter companyRegister = heliumParameterRepository.findOneHeliumParameterByParameterName(PARAM_COMPANY_REGISTER);
        if ( companyRegister == null ) {
            companyRegister = new HeliumParameter();
            companyRegister.setParameterName(PARAM_COMPANY_REGISTER);
            companyRegister.setLongValue(0);
            companyRegister.setStrValue("Acme is a company registered at xxx with company number 1235432516");
            heliumParameterRepository.save(companyRegister);
        }

        HeliumParameter heliumOui = heliumParameterRepository.findOneHeliumParameterByParameterName(PARAM_HELIUM_OUI);
        if ( heliumOui == null ) {
            heliumOui = new HeliumParameter();
            heliumOui.setParameterName(PARAM_HELIUM_OUI);
            heliumOui.setLongValue(consoleConfig.getHeliumRouteOui());
            heliumOui.setStrValue("");
            heliumParameterRepository.save(heliumOui);
        }

        HeliumParameter heliumNetId = heliumParameterRepository.findOneHeliumParameterByParameterName(PARAM_HELIUM_NETID);
        if ( heliumNetId == null ) {
            heliumNetId = new HeliumParameter();
            heliumNetId.setParameterName(PARAM_HELIUM_NETID);
            heliumNetId.setLongValue(0);
            heliumNetId.setStrValue(consoleConfig.getHeliumRouteNetid());
            heliumParameterRepository.save(heliumNetId);
        }

        HeliumParameter mqttClientId = heliumParameterRepository.findOneHeliumParameterByParameterName(PARAM_MQTT_CLIENT_ID);
        if ( mqttClientId == null ) {
            mqttClientId = new HeliumParameter();
            mqttClientId.setParameterName(PARAM_MQTT_CLIENT_ID);
            mqttClientId.setLongValue(0);
            mqttClientId.setStrValue(RandomString.getRandomString(6));
            heliumParameterRepository.save(mqttClientId);
        }

        HeliumParameter mqttheliumClientId = heliumParameterRepository.findOneHeliumParameterByParameterName(PARAM_MQTT_HELIUM_CLIENT_ID);
        if ( mqttheliumClientId == null ) {
            mqttheliumClientId = new HeliumParameter();
            mqttheliumClientId.setParameterName(PARAM_MQTT_HELIUM_CLIENT_ID);
            mqttheliumClientId.setLongValue(0);
            mqttheliumClientId.setStrValue(RandomString.getRandomString(6));
            heliumParameterRepository.save(mqttheliumClientId);
        }

        HeliumParameter userConditions = heliumParameterRepository.findOneHeliumParameterByParameterName(PARAM_USER_COND_CURRENT);
        if ( userConditions == null ) {
            userConditions = new HeliumParameter();
            userConditions.setParameterName(PARAM_USER_COND_CURRENT);
            userConditions.setLongValue(0);
            userConditions.setStrValue("Initial");
            heliumParameterRepository.save(userConditions);
        }

    }

    public HeliumParameter getParameter(String paramName) {
        return heliumParameterRepository.findOneHeliumParameterByParameterName(paramName);
    }

    public void flushParameter(HeliumParameter p) {
        heliumParameterRepository.save(p);
    }

}