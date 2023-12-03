package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_15__change_message_column_format extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {
        log.info("### Change the message detail field format");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("ALTER TABLE helium_messages ALTER COLUMN content TYPE text" );
        }
    }
}