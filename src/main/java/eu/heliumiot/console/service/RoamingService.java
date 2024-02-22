/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2024.
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 *    and associated documentation files (the "Software"), to deal in the Software without restriction,
 *    including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *    sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies or
 *    substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package eu.heliumiot.console.service;

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

                io.chirpstack.api.Device ndev = chirpstackApiAccess.getOneDevice(devEui);
                if ( ndev != null ) {
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
                    log.debug("Updated device " + ndev.getDevEui());
                } else {
                    log.error("Failed to change device config, device get error");
                }

            } catch ( ITRightException x ) {
                log.error("Impossible to get device config - rights");
            } catch ( ITNotFoundException x ) {
                log.error("Impossible to get device config - not found");
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
