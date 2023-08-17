package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_7__change_ticket_column_format extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {
        log.info("### Change the ticket detail field format");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("ALTER TABLE helium_ticket ALTER COLUMN detail TYPE text" );
        }
        log.info("### Change the ticket_response detail field format");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("ALTER TABLE helium_ticket_response ALTER COLUMN content TYPE text" );
        }
    }
}