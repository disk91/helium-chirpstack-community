package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_9__setup_join_billing_with_default extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {

        log.info("### Init helium_tenant_setup.dc_per_join_request values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenant_setup SET dc_per_join_request=-2 WHERE dc_per_join_request IS NULL" );
        }

        log.info("### Init helium_tenant_setup.max_join_request_dup values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenant_setup SET max_join_request_dup=-2 WHERE max_join_request_dup IS NULL" );
        }

        log.info("### Init helium_tenant_setup.dc_per_join_accept values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenant_setup SET dc_per_join_accept=-2 WHERE dc_per_join_accept IS NULL" );
        }
    }
}