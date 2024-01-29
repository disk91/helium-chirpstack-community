package eu.heliumiot.console.etl.api.sub;

import fr.ingeniousthings.tools.ClonnableObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Hotspot Position", description = "Position and antenna information for a hotspot")
public class LatLng implements ClonnableObject<LatLng> {


    @Schema(
            description = "Timestamp UTC in ms where this position has been discovered"
    )
    private long lastDatePosition;  // timestamp where change have been detected

    @Schema(
            description = "Latitude - based on hex"
    )
    private double lat;

    @Schema(
            description = "Longitude - based on hex"
    )
    private double lng;

    @Schema(
            description = "Altitude - not filled"
    )
    private double alt;

    @Schema(
            description = "Antenna gain - not filled"
    )
    private double gain;

    // --------

    public LatLng clone() {
        LatLng c = new LatLng();
        c.setLat(lat);
        c.setLng(lng);
        c.setAlt(alt);
        c.setGain(gain);
        c.setLastDatePosition(lastDatePosition);
        return c;
    }


    // ---------


    public long getLastDatePosition() {
        return lastDatePosition;
    }

    public void setLastDatePosition(long lastDatePosition) {
        this.lastDatePosition = lastDatePosition;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }
}
