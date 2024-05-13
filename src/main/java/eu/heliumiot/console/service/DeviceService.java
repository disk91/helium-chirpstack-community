package eu.heliumiot.console.service;

import eu.heliumiot.console.jpa.db.Device;
import eu.heliumiot.console.jpa.repository.DeviceRepository;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.ITNotFoundException;
import io.chirpstack.internal.DeviceSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected DeviceRepository deviceRepository;

    /** From a given device, get the device session
     * including the session keys and devAddr
     * @param devEui
     * @return The DeviceSession or null if not found
     */
    public DeviceSession getDeviceSession(byte [] devEui) {
        Device d = deviceRepository.findOneDeviceByDevEui(devEui);
        if ( d != null ) {
            try {
                return d.getDeviceSessionDetails();
            } catch (ITNotFoundException x) {
                return null;
            }
        }
        return null;
    }

    /** From a given device, get the device session
     * including the session keys and devAddr
     * @param devEui as an Hex String
     * @return The DeviceSession or null if not found
     */
    public DeviceSession getDeviceSession(String devEui) {
        return getDeviceSession(HexaConverters.hexStringToByteArray(devEui));
    }


}
