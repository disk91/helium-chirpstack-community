package eu.heliumiot.console.jpa.mongoRep;

import eu.heliumiot.console.jpa.mongodb.Hotspots;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotspotsMongoRepository extends MongoRepository<Hotspots,String> {

    public Hotspots findOneHotspotByHotspotId(String hotspotId);

    @Query(value="{'lastSeen' : { $lt : $0 }}", delete = true)
    public void deleteHotspotByAge(long time);

}
