package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_14__reset_helium_tenant_field_alarmed extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {
        log.info("### Init helium_tenant.alarmed values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_tenant SET alarmed=0 WHERE alarmed IS NULL" );
        }
    }
}