package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_16__setup_punishement_dc_defaults extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {

        log.info("### Init helium_tenant_setup.dc_per_punishment values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenant_setup SET dc_per_punishment=100 WHERE dc_per_punishment IS NULL" );
        }

        log.info("### Init helium_device_stats.punishment_dc values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_device_stats SET punishment_dc=0 WHERE punishment_dc IS NULL" );
        }

    }
}