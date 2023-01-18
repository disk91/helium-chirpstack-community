package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V2_0_4__reset_helium_message_field_userMess extends BaseJavaMigration {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void migrate(Context context) throws Exception {
        log.info("### Init helium_message.user_mess values");
        try (Statement update = context.getConnection().createStatement()) {
            update.execute("UPDATE helium_messages SET user_mess=true WHERE user_mess IS NULL" );
        }
    }
}