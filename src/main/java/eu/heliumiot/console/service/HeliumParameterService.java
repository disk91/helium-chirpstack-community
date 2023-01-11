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
            invoiceVat.setLongValue(2000); // 20.00%
            invoiceVat.setStrValue("");
            heliumParameterRepository.save(invoiceVat);
        }

    }

    public HeliumParameter getParameter(String paramName) {
        return heliumParameterRepository.findOneHeliumParameterByParameterName(paramName);
    }

    public void flushParameter(HeliumParameter p) {
        heliumParameterRepository.save(p);
    }

}