package eu.heliumiot.console.etl.api.sub;

import fr.ingeniousthings.tools.ClonnableObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Witness Data", description = "Information related to a witnesser")
public class Witness implements ClonnableObject<Witness> {

    @Schema(
            description = "Hotspot concerned, public key, base58"
    )
    private String hs;

    @Schema(
            description = "Timestamp UTC, last time that hotspot has been seen"
    )
    private long lastSeen;

    @Schema(
            description = "Last RSSI for that hotspot"
    )
    private double lastRssi;

    @Schema(
            description = "Last SNR for that hotspot"
    )
    private double lastSnr;

    @Schema(
            description = "Sum of Rssi for averaging"
    )
    private double totRssi;

    @Schema(
            description = "Sum of SNR for averaging"
    )
    private double totSnr;

    @Schema(
            description = "Num of witnesses for averaging"
    )
    private double countWitnesses;

    @Schema(
            description = "Number of time this one has been selected"
    )
    private long totSelected;

    @Schema(
            description = "Number of time this one has not been selected"
    )
    private long totUnselected;

    @Schema(
            description = "Last known position (latitude) for that hotspot"
    )
    private double lat = 0.0;
    @Schema(
            description = "Last known position (longitude) for that hotspot"
    )
    private double lng = 0.0;

    // --------

    public Witness clone() {
        Witness c = new Witness();
        c.setLastSeen(lastSeen);
        c.setHs(hs);
        c.setLastRssi(lastRssi);
        c.setLastSnr(lastSnr);
        c.setTotRssi(totRssi);
        c.setTotSnr(totSnr);
        c.setCountWitnesses(countWitnesses);
        c.setLat(lat);
        c.setLng(lng);
        c.setTotSelected(totSelected);
        c.setTotUnselected(totUnselected);
        return c;
    }


    // -----

    public void init(String id) {
        this.hs = id;
        this.totRssi = 0;
        this.totSnr = 0;
        this.countWitnesses = 0;
        this.lat = 0.0;
        this.lng = 0.0;
        this.totSelected = 0;
        this.totUnselected = 0;
    }

    public void addWitness(long _lastSeen, double _rssi, double _snr, double _lat, double _lng, boolean selected ) {
        this.lastSeen = _lastSeen;
        this.lastRssi = _rssi;
        this.lastSnr = _snr;
        this.lat = _lat;
        this.lng = _lng;
        this.totRssi += _rssi;
        this.totSnr += _snr;
        this.countWitnesses++;
        if ( selected ) this.totSelected++;
        else this.totUnselected++;
    }

    // ---------

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getHs() {
        return hs;
    }

    public void setHs(String hs) {
        this.hs = hs;
    }

    public double getLastRssi() {
        return lastRssi;
    }

    public void setLastRssi(double lastRssi) {
        this.lastRssi = lastRssi;
    }

    public double getLastSnr() {
        return lastSnr;
    }

    public void setLastSnr(double lastSnr) {
        this.lastSnr = lastSnr;
    }

    public double getTotRssi() {
        return totRssi;
    }

    public void setTotRssi(double totRssi) {
        this.totRssi = totRssi;
    }

    public double getTotSnr() {
        return totSnr;
    }

    public void setTotSnr(double totSnr) {
        this.totSnr = totSnr;
    }

    public double getCountWitnesses() {
        return countWitnesses;
    }

    public void setCountWitnesses(double countWitnesses) {
        this.countWitnesses = countWitnesses;
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

    public long getTotSelected() {
        return totSelected;
    }

    public void setTotSelected(long totSelected) {
        this.totSelected = totSelected;
    }

    public long getTotUnselected() {
        return totUnselected;
    }

    public void setTotUnselected(long totUnselected) {
        this.totUnselected = totUnselected;
    }
}
