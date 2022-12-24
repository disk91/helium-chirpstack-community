package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_1__reset_helium_user_xseen extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {
        log.info("### Init helium_user lastSeen and lastMessSeen values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_user SET last_seen=0");
        }
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_user SET last_mess_seen=0");
        }
    }
}