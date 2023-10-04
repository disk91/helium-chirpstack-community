package db.migration;

import eu.heliumiot.console.ConsoleConfig;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Statement;

@Component
public class V2_0_9__setup_join_billing_with_default extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${helium.billing.dcPerJoinRequest:-5}")
    private int dcPerJoinRequest;

    @Value("${helium.billing.maxJoinRequestDup:-5}")
    private int maxJoinRequestDup;

    @Value("${helium.billing.dcPerJoinAccept:-5}")
    private int dcPerJoinAccept;

    public void migrate(Context context) throws Exception {

        log.info("### Init helium_tenant_setup.dc_per_join_request values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenant_setup SET dc_per_join_request="+dcPerJoinRequest+" WHERE dc_per_join_request IS NULL" );
        }

        log.info("### Init helium_tenant_setup.max_join_request_dup values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenant_setup SET max_join_request_dup="+maxJoinRequestDup+" WHERE max_join_request_dup IS NULL" );
        }

        log.info("### Init helium_tenant_setup.dc_per_join_accept values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenant_setup SET dc_per_join_accept="+dcPerJoinAccept+" WHERE dc_per_join_accept IS NULL" );
        }
    }
}