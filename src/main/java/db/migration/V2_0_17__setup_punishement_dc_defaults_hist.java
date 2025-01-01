package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_17__setup_punishement_dc_defaults_hist extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {

        log.info("### Init helium_device_stats_history.punishment_dc values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_device_stats_history SET punishment_dc=0 WHERE punishment_dc IS NULL" );
        }

    }
}