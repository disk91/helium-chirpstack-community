package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_22__reset_company_tax_helium_user extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {

        log.info("### Update helium_user company_tax column");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_user SET company_tax='' WHERE company_tax IS NULL" );
        }

    }
}