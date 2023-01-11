package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_3__reset_helium_transaction_stripe extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {
        log.info("### Init helium_dc_transaction stripe_amount stripe_crate values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_dc_transaction SET stripe_amount=0 WHERE stripe_amount IS NULL" );
        }
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_dc_transaction SET stripe_crate=0 WHERE stripe_crate IS NULL" );
        }
    }
}