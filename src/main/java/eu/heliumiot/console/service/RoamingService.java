package eu.heliumiot.console.service;

import com.google.protobuf.InvalidProtocolBufferException;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.chirpstack.ChirpstackApiAccess;
import eu.heliumiot.console.jpa.db.Device;
import eu.heliumiot.console.jpa.db.DeviceProfile;
import eu.heliumiot.console.jpa.repository.DeviceProfileRepository;
import eu.heliumiot.console.jpa.repository.DeviceRepository;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.ITNotFoundException;
import fr.ingeniousthings.tools.ITRightException;
import fr.ingeniousthings.tools.Now;
import io.chirpstack.api.GetDeviceRequest;
import io.chirpstack.api.GetDeviceResponse;
import io.chirpstack.api.UpdateDeviceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoamingService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected PrometeusService prometeusService;

    @Autowired
    protected DeviceRepository deviceRepository;

    @Autowired
    protected DeviceProfileRepository deviceProfileRepository;

    @Autowired
    protected ConsoleConfig consoleConfig;
    @Autowired
    protected ChirpstackApiAccess chirpstackApiAccess;


    protected String convertRegion(String topicRegion) {
        topicRegion = topicRegion.toUpperCase();
        if ( topicRegion.contains("US915") ) return "US915";
        if ( topicRegion.contains("EU869") ) return "EU868";
        if ( topicRegion.contains("AS923") ) return "AS923";
        if ( topicRegion.contains("AU915") ) return "AU915";
        if ( topicRegion.contains("KR920") ) return "KR920";
        if ( topicRegion.contains("IN865") ) return "IN865";
        return topicRegion;
    }

    @Async
    public void processJoinMessage(byte [] _devEui, String devEui, String region) {
        long start = Now.NowUtcMs();

        try {
            // get device
            Device d = deviceRepository.findOneDeviceByDevEui(_devEui);
            if (d == null || d.getDeviceProfileId() == null) {
                log.warn("Unknown device / deviceProfile for "+devEui+"("+ HexaConverters.byteToHexString(_devEui)+")");
                return;
            }

            // get the associated Device_Profile
            DeviceProfile dp = deviceProfileRepository.findOneDeviceProfileById(d.getDeviceProfileId());
            if (dp == null) {
                log.warn("Impossible to find device profile");
                return;
            }

            region = convertRegion(region);
            // check region, if valid, nothing more to be done
            if (dp.getRegion().compareToIgnoreCase(region) == 0) return;
            log.debug("Device "+devEui+" Join from a different zone ("+region+") than default ("+dp.getRegion()+")");

            // when different, find a compatible device template
            String templateName = dp.getName().replaceFirst("[(].*[)]","").trim().toLowerCase();
            log.debug("Match device profile : "+templateName);
            List<DeviceProfile> dps = deviceProfileRepository.findDeviceProfileByTenantId(dp.getTenantId());
            boolean found = false;
            DeviceProfile target = null;
            region = region.toUpperCase();
            for ( DeviceProfile _dp : dps ) {
                if ( _dp.getName().toLowerCase().endsWith(templateName) ) {
                    // same group
                    log.debug("Get Region " + _dp.getRegion());
                    if ( region.compareTo(_dp.getRegion()) == 0 ) {
                        target = _dp;
                        found = true;
                    }
                }
            }
            if ( !found ) return;

            // when found, update the device template
            HttpHeaders heads = new HttpHeaders();
            heads.add("authorization", "Bearer "+consoleConfig.getChirpstackApiAdminKey());

            GetDeviceRequest gdr = GetDeviceRequest.newBuilder()
                    .setDevEui(devEui)
                    .build();
            try {
                byte[] resp = chirpstackApiAccess.execute(
                        HttpMethod.POST,
                        "/api.DeviceService/Get",
                        null,
                        heads,
                        gdr.toByteArray()
                );
                GetDeviceResponse dev = GetDeviceResponse.parseFrom(resp);
                log.debug("Found device "+dev.getDevice().getDevEui());

                io.chirpstack.api.Device ndev = dev.getDevice();
                ndev = ndev.toBuilder()
                        .setDeviceProfileId(target.getId().toString())
                        .build();

                UpdateDeviceRequest udr = UpdateDeviceRequest.newBuilder()
                        .setDevice(ndev)
                        .build();

                chirpstackApiAccess.execute(
                        HttpMethod.POST,
                        "/api.DeviceService/Update",
                        null,
                        heads,
                        udr.toByteArray()
                );
                log.debug("Updated device "+ndev.getDevEui());

            } catch ( ITRightException x ) {
                log.error("Impossible to get device config - rights");
            } catch ( ITNotFoundException x ) {
                log.error("Impossible to get device config - not found");
            } catch ( InvalidProtocolBufferException x ) {
                log.error("Impossible to get device config - protobuf");
            }

            // then
            prometeusService.roamingAddOne();

        } catch (Exception x) {
            log.error("Exception happened "+x.getMessage());
        } finally {
            prometeusService.roamingAddDuration(Now.NowUtcMs()-start);
        }

    }

}
