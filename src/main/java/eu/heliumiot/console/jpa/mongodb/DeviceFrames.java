package eu.heliumiot.console.jpa.mongodb;


import fr.ingeniousthings.tools.ClonnableObject;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "etl_hotspots")
@CompoundIndexes({
    @CompoundIndex(name = "hotspotId", def = "{'hotspotId' : 1 }"),
    @CompoundIndex(name = "animalName", def = "{'animalName' : 'text'}"),
    @CompoundIndex(name = "hotspotId_Id", def = "{'hotspotId' : 1, 'id' : 1}")
})
public class DeviceFrames {


}
