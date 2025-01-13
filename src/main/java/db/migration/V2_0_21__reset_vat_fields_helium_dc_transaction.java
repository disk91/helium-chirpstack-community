package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_21__reset_vat_fields_helium_dc_transaction extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {

        log.info("### Update helium_dc_transaction vat related additional columns");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_dc_transaction SET vat_message='', country_code='', tax_number='' WHERE vat_message IS NULL" );
        }

    }
}