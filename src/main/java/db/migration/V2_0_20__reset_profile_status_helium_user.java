package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_20__reset_profile_status_helium_user extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {

        log.info("### Update helium_user profile status column");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_user SET profile_status='created' WHERE profile_status='completed'" );
        }

    }
}