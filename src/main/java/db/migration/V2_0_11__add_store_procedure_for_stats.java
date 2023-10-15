package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_11__add_store_procedure_for_stats extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {

        log.info("### Add helium_device_stats_xxx stored procedure");
        try (Statement statement = context.getConnection().createStatement()) {
            statement.execute("CREATE OR REPLACE PROCEDURE move_helium_device_stats_history()\n" +
                "LANGUAGE plpgsql\n" +
                "AS $$\n" +
                "BEGIN\n" +
                "    BEGIN\n" +
                "        WITH moved_rows AS (\n" +
                "            DELETE FROM helium_device_stats a\n" +
                "            WHERE day < EXTRACT(EPOCH FROM (NOW() - INTERVAL '1 month')) * 1000\n" +
                "            RETURNING a.id, a.deviceuuid, a.tenantuuid, a.day, a.last_commit, a.registration_dc, " +
                             "a.uplink_dc, a.duplicate, a.duplicate_dc, a.downlink_dc, a.inactivity_dc, a.activity_dc, " +
                             "a.uplink, a.downlink, a.join_req, a.join_dc, a.join_accept_dc\n" +
                "        )\n" +
                "        INSERT INTO helium_device_stats_history ( id, deviceuuid, tenantuuid, day, last_commit, registration_dc, " +
                         "uplink_dc, duplicate, duplicate_dc, downlink_dc, inactivity_dc, activity_dc, uplink, downlink, join_req, " +
                         "join_dc, join_accept_dc )\n" +
                "        SELECT * FROM moved_rows;\n" +
                "        \n" +
                "    EXCEPTION\n" +
                "        WHEN OTHERS THEN\n" +
                "            ROLLBACK;\n" +
                "            RAISE;\n" +
                "    END;\n" +
                "END;\n" +
                "$$;\n" );
        }
        try (Statement statement = context.getConnection().createStatement()) {
            statement.execute("CREATE OR REPLACE PROCEDURE delete_helium_device_stats_history()\n" +
                "LANGUAGE plpgsql\n" +
                "AS $$\n" +
                "BEGIN\n" +
                "    BEGIN\n" +
                "        DELETE FROM helium_device_stats_history WHERE day < EXTRACT(EPOCH FROM (NOW() - INTERVAL '1 year')) * 1000;\n" +
                "    EXCEPTION\n" +
                "        WHEN OTHERS THEN\n" +
                "            ROLLBACK;\n" +
                "            RAISE;\n" +
                "    END;\n" +
                "END;\n" +
                "$$;\n");
        }

    }
}