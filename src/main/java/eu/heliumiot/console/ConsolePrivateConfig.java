package eu.heliumiot.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "eu.heliumiot.console.jpa.mongoRep")
@PropertySource("application.properties")
@PropertySource(value = {"file:${config.file}"}, ignoreResourceNotFound = true)
public class ConsolePrivateConfig {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // =====================================
    // Console Dev features
    // =====================================

    @Value("${helium.dev.max.frame.hist:200}")
    private int heliumDevMaxFrameHist;

    @Value("${helium.dev.max.hotspot.hist:200}")
    private int heliumDevMaxHotspotHist;

    public int getHeliumDevMaxFrameHist() {
        return heliumDevMaxFrameHist;
    }

    public int getHeliumDevMaxHotspotHist() {
        return heliumDevMaxHotspotHist;
    }

    // =====================================
    // Console for EDU environment
    // =====================================

    // After this duration a device is moved to the garbage tenant and disabled
    @Value("${helium.edu.device.maxlife.ms:0}")  // default disable
    private long heliumEduDeviceMaxlifeMs;

    // After this duration a device is removed from garbage (deleted) so it can be recreated
    @Value("${helium.edu.device.garbagelife.ms:0}") // default disable
    private long heliumEduDeviceGarbagelifeMs;

    // This is where we move the device for garbaging
    @Value("${helium.edu.device.garbage.tenantID:}")
    private String heliumEduDeviceGarbageTenantID;

    @Value("${helium.edu.device.garbage.applicationID:}")
    private String heliumEduDeviceGarbageApplicationID;

    @Value("${helium.edu.device.garbage.devprofileID:}")
    private String heliumEduDeviceGarbagedevprofileID;

    // For the test phase in an env where there are standard devices this allows to exclude the
    // other devices. Basically a device create prior to this date (epoc ms) will be skipped
    @Value("${helium.edu.device.exclude.before:0}") // default exclude none
    private long heliumEduDeviceExludeBefore;


    public long getHeliumEduDeviceMaxlifeMs() {
        return heliumEduDeviceMaxlifeMs;
    }

    public long getHeliumEduDeviceGarbagelifeMs() {
        return heliumEduDeviceGarbagelifeMs;
    }

    public String getHeliumEduDeviceGarbageTenantID() {
        return heliumEduDeviceGarbageTenantID;
    }

    public String getHeliumEduDeviceGarbageApplicationID() {
        return heliumEduDeviceGarbageApplicationID;
    }

    public String getHeliumEduDeviceGarbagedevprofileID() {
        return heliumEduDeviceGarbagedevprofileID;
    }

    public long getHeliumEduDeviceExludeBefore() {
        return heliumEduDeviceExludeBefore;
    }
}
