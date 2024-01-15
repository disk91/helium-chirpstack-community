package eu.heliumiot.console.service;


import eu.heliumiot.console.jpa.mongodb.DeviceFrames;
import eu.heliumiot.console.jpa.repository.DeviceFramesMongoRepository;
import fr.ingeniousthings.tools.ObjectCache;
import io.chirpstack.json.UplinkEvent;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrivDeviceFramesService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ObjectCache<String, DeviceFrames> frameCache;

    @Autowired
    protected DeviceFramesMongoRepository deviceFramesMongoRepository;

    @PostConstruct
    private void initDeviceFrameService() {
        this.frameCache = new ObjectCache<String, DeviceFrames>(
            "DeviceFrameCache",
            1000,               // @todo param
            3600_000                    // @todo param
        ) {
            private ArrayList<DeviceFrames> _toSave = new ArrayList<>();
            @Override
            public void onCacheRemoval(String key, DeviceFrames obj, boolean batch, boolean last) {
                if ( batch ) {
                    if ( obj != null ) _toSave.add(obj);
                    if ( _toSave.size() > 5000 || last ) {
                        _toSave.parallelStream().forEach(deviceFramesMongoRepository::save);
                        _toSave.clear();
                    }
                } else {
                    deviceFramesMongoRepository.save(obj);
                }
            }

            @Override
            public void bulkCacheUpdate(List<DeviceFrames> objects) {
                deviceFramesMongoRepository.saveAll(objects);
            }
        };
    }

    // Returns the device entry or null if does not exists
    public DeviceFrames getDevice(String devEui) {
        DeviceFrames d = this.frameCache.get(devEui);
        if ( d == null ) {
            // search in db
            d = deviceFramesMongoRepository.findOneDeviceFramesByDevEui(devEui);
            if ( d != null ) {
                this.frameCache.put(d,d.getDevEui());
            }
        }
        return d;
    }

    public DeviceFrames updateDevice(DeviceFrames d) {
        if ( d.getId() == null ) {
            // new entry
            d = deviceFramesMongoRepository.save(d);
        }
        this.frameCache.put(d,d.getDevEui(),true);
        return d;
    }


}
