package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_19__update_country_code_helium_user extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {

        log.info("### Update helium_user country_code column");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_user SET country_code='' WHERE country_code IS NULL" );
        }

    }
}