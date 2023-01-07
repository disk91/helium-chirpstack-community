package eu.heliumiot.console.service;

import eu.heliumiot.console.api.interfaces.TransactionListRespItf;
import eu.heliumiot.console.jpa.db.HeliumDcTransaction;
import eu.heliumiot.console.jpa.db.HeliumTenant;
import eu.heliumiot.console.jpa.db.Tenant;
import eu.heliumiot.console.jpa.repository.HeliumDcTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    HeliumDcTransactionRepository heliumDcTransactionRepository;

    @Autowired
    HeliumTenantService heliumTenantService;


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
}
