package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_12__add_uuid_ossp extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {

        log.info("### Add uuid-ossp extension");
        try (Statement statement = context.getConnection().createStatement()) {
            statement.execute("CREATE EXTENSION IF NOT EXISTS \"uuid-ossp\"" );
        }
    }
}