package db.migration;

import eu.heliumiot.console.ConsoleConfig;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Statement;

@Component
public class V2_0_8__setup_join_billing_with_default extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ConsoleConfig consoleConfig;

    public void migrate(Context context) throws Exception {

        log.info("### Init helium_tenantSetup.dc_per_join_request values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenantSetup SET dc_per_join_request="+consoleConfig.getHeliumBillingDcPerJoinRequest() +" WHERE dc_per_join_request IS NULL" );
        }

        log.info("### Init helium_tenantSetup.max_join_request_dup values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenantSetup SET max_join_request_dup="+consoleConfig.getHeliumBillingMaxJoinRequestDup() +" WHERE max_join_request_dup IS NULL" );
        }

        log.info("### Init helium_tenantSetup.dc_per_join_accept values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenantSetup SET dc_per_join_accept="+consoleConfig.getHeliumBillingDcPerJoinAccept() +" WHERE dc_per_join_accept IS NULL" );
        }
    }
}