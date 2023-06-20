package eu.heliumiot.console.service;

import com.google.protobuf.InvalidProtocolBufferException;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.chirpstack.ChirpstackApiAccess;
import eu.heliumiot.console.jpa.db.Device;
import eu.heliumiot.console.jpa.db.DeviceProfile;
import eu.heliumiot.console.jpa.repository.DeviceProfileRepository;
import eu.heliumiot.console.jpa.repository.DeviceRepository;
import fr.ingeniousthings.tools.ITNotFoundException;
import fr.ingeniousthings.tools.ITRightException;
import fr.ingeniousthings.tools.Now;
import io.chirpstack.api.GetDeviceRequest;
import io.chirpstack.api.GetDeviceResponse;
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

    @Async
    public void processJoinMessage(byte [] _devEui, String devEui, String region) {
        long start = Now.NowUtcMs();

        try {
            // get device
            Device d = deviceRepository.findOneDeviceByDevEui(_devEui);
            if (d == null || d.getDeviceProfileId() == null) {
                log.warn("Unknown device / deviceProfile");
                return;
            }

            // get the associated Device_Profile
            DeviceProfile dp = deviceProfileRepository.findOneDeviceProfileById(d.getDeviceProfileId());
            if (dp == null) {
                log.warn("Impossible to find device profile");
                return;
            }

            // check region, if valid, nothing more to be done
            if (dp.getRegion().compareToIgnoreCase(region) == 0) return;
            log.debug("Device "+devEui+" Join from a different zone ("+region+") than default ("+dp.getRegion()+")");

            // when different, find a compatible device template
            String templateName = dp.getName().replaceFirst("(.*)","").trim().toLowerCase();
            log.debug("Match device profile : "+templateName);
            List<DeviceProfile> dps = deviceProfileRepository.findDeviceProfileByTenantId(dp.getTenantId());
            boolean found = false;
            DeviceProfile target = null;
            for ( DeviceProfile _dp : dps ) {
                if ( _dp.getName().toLowerCase().endsWith(templateName) ) {
                    // same group
                    log.debug("Get Region " + _dp.getRegion());
                    if (_dp.getRegion().compareToIgnoreCase(region) == 0) {
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
                        "/api.DeviceService/Get", //@todo check
                        null,
                        heads,
                        gdr.toByteArray()
                );
                GetDeviceResponse dev = GetDeviceResponse.parseFrom(resp);

                // @todo .. update the device and push resonse

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
