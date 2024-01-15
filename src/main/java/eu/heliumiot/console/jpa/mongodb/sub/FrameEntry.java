package eu.heliumiot.console.jpa.mongodb.sub;

import fr.ingeniousthings.tools.ClonnableObject;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.List;

public class FrameEntry implements ClonnableObject<FrameEntry> {

    @Transient
    public final static int FRAME_TYPE_UPLINK = 0;
    @Transient
    public final static int FRAME_TYPE_JOIN = 1;


    // Frame reception time
    protected long rxTimeMs;

    // Frame information
    protected int fCnt;
    protected int dr;
    protected int dataSize;
    protected int frameType;

    // Hotspot Related
    protected List<FrameHotspot> hotspots;

    // ---

    public FrameEntry clone() {
        FrameEntry f = new FrameEntry();
        f.setRxTimeMs(this.rxTimeMs);
        f.setfCnt(this.fCnt);
        f.setDr(this.dr);
        f.setDataSize(this.dataSize);
        f.setFrameType(this.frameType);
        f.setHotspots(new ArrayList<>());
        for ( FrameHotspot h : this.hotspots ) {
            f.getHotspots().add(h.clone());
        }
        return f;
    }


    // ---

    public long getRxTimeMs() {
        return rxTimeMs;
    }

    public void setRxTimeMs(long rxTimeMs) {
        this.rxTimeMs = rxTimeMs;
    }

    public int getfCnt() {
        return fCnt;
    }

    public void setfCnt(int fCnt) {
        this.fCnt = fCnt;
    }

    public int getDr() {
        return dr;
    }

    public void setDr(int dr) {
        this.dr = dr;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getFrameType() {
        return frameType;
    }

    public void setFrameType(int frameType) {
        this.frameType = frameType;
    }

    public List<FrameHotspot> getHotspots() {
        return hotspots;
    }

    public void setHotspots(List<FrameHotspot> hotspots) {
        this.hotspots = hotspots;
    }
}
