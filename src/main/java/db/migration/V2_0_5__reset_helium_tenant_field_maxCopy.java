package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_5__reset_helium_tenant_field_maxCopy extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {
        log.info("### Init helium_tenant.max_copy values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenant SET max_copy=3 WHERE max_copy IS NULL" );
        }
        log.info("### Init helium_tenant_setup.max_copy values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenant_setup SET max_copy=3 WHERE max_copy IS NULL" );
        }
        log.info("### Init helium_tenant_setup.route_id values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenant_setup SET route_id='N/A' WHERE template" );
        }
    }
}