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