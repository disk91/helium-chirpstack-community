package eu.heliumiot.console.etl.api;

import eu.heliumiot.console.etl.api.sub.LatLng;
import eu.heliumiot.console.etl.api.sub.Owner;
import io.swagger.v3.oas.annotations.media.Schema;

public class HotspotIdent {

    @Schema(
        description = "Hexstring Base58 of the Hotspot public key, aka Hs address",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String hotspotId;

    @Schema(
        description = "Animal name of the hostpot with - between words",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String animalName;

    @Schema(
        description = "Hotspot Owner address",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Owner owner;

    @Schema(
        description = "Hotspot position, lat / lng, can be 0,0",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LatLng position;

    // ---

    // ---


    public String getHotspotId() {
        return hotspotId;
    }

    public void setHotspotId(String hotspotId) {
        this.hotspotId = hotspotId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}
