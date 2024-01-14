package eu.heliumiot.console.jpa.repository;

import eu.heliumiot.console.jpa.mongodb.DeviceFrames;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceFramesMongoRepository  extends MongoRepository<DeviceFrames,String> {

    public DeviceFrames findOneDeviceFramesByDevEui(String devEui);


}
