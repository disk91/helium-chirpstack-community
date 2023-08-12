package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_6__init_condition_version extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {
        log.info("### Init helium_user.condition_version values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_user SET condition_version='Initial' WHERE condition_version IS NULL OR condition_version = ''" );
        }
        log.info("### Init helium_user.condition_time values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_user SET condition_time=registration_time WHERE condition_time IS NULL" );
        }
    }
}