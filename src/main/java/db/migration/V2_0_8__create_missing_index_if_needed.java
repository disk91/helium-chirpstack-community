package db.migration;

import eu.heliumiot.console.ConsoleConfig;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Statement;

public class V2_0_8__create_missing_index_if_needed extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {

        log.info("### Create index for helium_ticket when not created automatically");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("CREATE INDEX IF NOT EXISTS byusersearch ON helium_ticket (user_uuid)");
        }

        log.info("### Create index for helium_ticket_response when not created automatically");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("CREATE INDEX IF NOT EXISTS byticketuuid ON helium_ticket_response (ticket_uuid)");
        }
    }
}