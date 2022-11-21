package eu.heliumiot.console.service;

import eu.heliumiot.console.jpa.db.NovaDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NovaService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
