package eu.heliumiot.console.jpa.db;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
@DependsOn("entityManagerFactory")
public class FlywayConfiguration {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // Manually start flyway to have it starting once JPA job is finished
    @Autowired
    public FlywayConfiguration(DataSource dataSource) {
        log.info("Running Flaywaydb ...");
        try {
            Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
