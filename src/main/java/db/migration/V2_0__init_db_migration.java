package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V2_0__init_db_migration extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {
        // nothing to do really but register that the schema version is corresponding to this level
        log.info("### Init database migration");

            /*
            try (ResultSet rows = select.executeQuery("SELECT id FROM person ORDER BY id")) {
                while (rows.next()) {
                    int id = rows.getInt(1);
                    String anonymizedName = "Anonymous" + id;
                    try (Statement update = context.getConnection().createStatement()) {
                        update.execute("UPDATE person SET name='" + anonymizedName + "' WHERE id=" + id);
                    }
                }
            }
             */
    }
}