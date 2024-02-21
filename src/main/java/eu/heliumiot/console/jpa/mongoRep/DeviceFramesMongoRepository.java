package eu.heliumiot.console.jpa.mongoRep;

import eu.heliumiot.console.jpa.mongodb.DeviceFrames;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceFramesMongoRepository  extends MongoRepository<DeviceFrames,String> {

    public DeviceFrames findOneDeviceFramesByDevEui(String devEui);

    @Query(value="{'lastSeen' : { $lt : $0 }}", delete = true)
    public void deleteDeviceFrameByAge(long time);

}
