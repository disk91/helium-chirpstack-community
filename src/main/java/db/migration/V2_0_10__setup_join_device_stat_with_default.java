package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_10__setup_join_device_stat_with_default extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {

        log.info("### Init helium_device_stats.join_dc values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_device_stats SET join_dc=0 WHERE join_dc IS NULL" );
        }

        log.info("### Init helium_device_stats.join_accept_dc values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_device_stats SET join_accept_dc=0 WHERE join_accept_dc IS NULL" );
        }

    }
}