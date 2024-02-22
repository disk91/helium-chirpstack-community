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

import eu.heliumiot.console.jpa.db.HeliumTenantSetup;
import eu.heliumiot.console.jpa.repository.HeliumTenantSetupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeliumTStoNovaProxyService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // just a stupid service to avoir service loops

    @Autowired
    protected HeliumTenantSetupRepository heliumTenantSetupRepository;


    public HeliumTenantSetup getHTSByRouteId(String routeId) {
        List<HeliumTenantSetup> tss = heliumTenantSetupRepository.findHeliumTenantSetupByRouteId(routeId);
        if ( tss == null || tss.size() == 0 ) return null;
        else return tss.get(0);
    }

    @Autowired
    protected HeliumDeviceCacheService heliumDeviceCacheService;
    public String getRouteIdFromEui(String eui) {
        String tenantId = heliumDeviceCacheService.getTenantId(eui);
        if ( tenantId != null ) {
            HeliumTenantSetup ht = heliumTenantSetupRepository.findOneHeliumTenantSetupByTenantUUID(tenantId);
            if (ht != null) {
                return ht.getRouteId();
            } else {
                log.error("Tenant with id " + tenantId + " does not exist");
            }
        }
        return null;
    }


}