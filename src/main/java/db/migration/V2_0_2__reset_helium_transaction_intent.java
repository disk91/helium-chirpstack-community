package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_2__reset_helium_transaction_intent extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {
        log.info("### Init helium_dc_transaction isCompleted intentTime values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_dc_transaction SET intent_time=0 WHERE intent_time IS NULL" );
        }
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_dc_transaction SET is_completed=true WHERE is_completed IS NULL" );
        }
    }
}