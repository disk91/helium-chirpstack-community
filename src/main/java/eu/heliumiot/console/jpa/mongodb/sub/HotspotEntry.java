package eu.heliumiot.console.jpa.mongodb.sub;

import fr.ingeniousthings.tools.ClonnableObject;

public class HotspotEntry  implements ClonnableObject<HotspotEntry> {

    // Last time this hotspot has been seen
    protected long lastSeen;
    // number of time this hotspot as received payload from this device
    protected long count;

    // Gateway information
    protected String gatewayId;
    protected double lat;
    protected double lng;

    // averaging link quality
    protected long sumOfRssi;
    protected double sumOfSnr;

    protected String region;

    // ---

    public HotspotEntry clone() {
        HotspotEntry h = new HotspotEntry();
        h.setLastSeen(this.lastSeen);
        h.setCount(this.count);
        h.setGatewayId(this.gatewayId);
        h.setLat(this.lat);
        h.setLng(this.lng);
        h.setSumOfRssi(this.sumOfRssi);
        h.setSumOfSnr(this.sumOfSnr);
        h.setRegion(this.region);
        return h;
    }

    // ---

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
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

    public long getSumOfRssi() {
        return sumOfRssi;
    }

    public void setSumOfRssi(long sumOfRssi) {
        this.sumOfRssi = sumOfRssi;
    }

    public double getSumOfSnr() {
        return sumOfSnr;
    }

    public void setSumOfSnr(double sumOfSnr) {
        this.sumOfSnr = sumOfSnr;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
