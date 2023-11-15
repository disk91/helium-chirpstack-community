package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_13__remove_null_user_reg_date extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {

        log.info("### Make a default user registration date one 1/1/2023 instead of null for old time users");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_user SET registration_time='2023-01-01 00:00:00' WHERE registration_time IS NULL" );
        }
    }
}