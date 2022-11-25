package eu.heliumiot.console.service;

import eu.heliumiot.console.jpa.db.NovaDevice;
import eu.heliumiot.console.redis.RedisDeviceStreamListener;
import io.chirpstack.api.internal.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NovaService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected RedisDeviceStreamListener redisDeviceStreamListener;

    public boolean refreshDevAddrList(String devaddr) {
        // Get the list of device using this devaddr
        List<String> deviceEUIs = redisDeviceStreamListener.getDevEuiByDevAddr(devaddr);
        log.debug("Refresh devAddr network keys for "+devaddr+" found "+deviceEUIs.size()+" devices");
        for ( String devEUI : deviceEUIs ) {
            // get all NetwksKey
            Internal.DeviceSession ds = redisDeviceStreamListener.getDeviceDetails(devEUI);
            if ( ds != null ) {

                // verify state from cache, is that an active device...
                // @todo

                // add to the list
                log.debug("Add Network encrypted Key " +ds.getNwkSEncKey());
            }

        }

        return true;
    }

    public boolean deactivateDevices(List<NovaDevice> devices) {
        for ( NovaDevice d : devices ) {
            log.info("Deactivating device "+d.devEui);
        }
        return true;
    }

    public boolean activateDevices(List<NovaDevice> devices) {
        for ( NovaDevice d : devices ) {
            log.info("Activating device "+d.devEui);
        }
        return true;
    }


}
