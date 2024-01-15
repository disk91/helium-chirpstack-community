package eu.heliumiot.console.jpa.mongodb.sub;

import fr.ingeniousthings.tools.ClonnableObject;

public class FrameHotspot implements ClonnableObject<FrameHotspot> {

    protected String hotspotId;
    protected int rssi;
    protected double snr;

    // ---

    public FrameHotspot clone() {
        FrameHotspot f =  new FrameHotspot();
        f.setHotspotId(this.hotspotId);
        f.setRssi(this.rssi);
        f.setSnr(this.snr);
        return f;
    }

    // ---

    public String getHotspotId() {
        return hotspotId;
    }

    public void setHotspotId(String hotspotId) {
        this.hotspotId = hotspotId;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public double getSnr() {
        return snr;
    }

    public void setSnr(double snr) {
        this.snr = snr;
    }
}
