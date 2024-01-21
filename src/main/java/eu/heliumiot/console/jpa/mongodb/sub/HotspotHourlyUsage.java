package eu.heliumiot.console.jpa.mongodb.sub;

import fr.ingeniousthings.tools.ClonnableObject;

public class HotspotHourlyUsage implements ClonnableObject<HotspotHourlyUsage> {
    protected long hourRef;
    protected int packetsSeen;

    // ---

    public HotspotHourlyUsage clone() {
        HotspotHourlyUsage h = new HotspotHourlyUsage();
        h.setHourRef(this.hourRef);
        h.setPacketsSeen(this.packetsSeen);
        return h;
    }

    // ---


    public long getHourRef() {
        return hourRef;
    }

    public void setHourRef(long hourRef) {
        this.hourRef = hourRef;
    }

    public int getPacketsSeen() {
        return packetsSeen;
    }

    public void setPacketsSeen(int packetsSeen) {
        this.packetsSeen = packetsSeen;
    }
}
