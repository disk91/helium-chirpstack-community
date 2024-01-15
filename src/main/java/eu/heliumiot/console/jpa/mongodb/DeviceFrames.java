package eu.heliumiot.console.jpa.mongodb;


import eu.heliumiot.console.ConsolePrivateConfig;
import eu.heliumiot.console.jpa.mongodb.sub.FrameEntry;
import eu.heliumiot.console.jpa.mongodb.sub.FrameHotspot;
import eu.heliumiot.console.jpa.mongodb.sub.HotspotEntry;
import fr.ingeniousthings.tools.ClonnableObject;
import fr.ingeniousthings.tools.DateConverters;
import fr.ingeniousthings.tools.Now;
import io.chirpstack.json.UplinkEvent;
import io.chirpstack.json.sub.UplinkEventRxInfo;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "etl_hotspots")
@CompoundIndexes({
    @CompoundIndex(name = "devEui", def = "{'devEui' : 'text' }"),
})
public class DeviceFrames implements ClonnableObject<DeviceFrames>{

    @Transient
    @Autowired
    private ConsolePrivateConfig consolePrivateConfig;

    @Transient
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Id
    private String id = null;

    // Key entry
    protected String devEui = null;
    // last frame time stamp
    protected long lastSeen;
    // last cache push
    protected long lastSaved;
    // total frame seen
    protected long frameSeen;

    // estimated position
    protected double estimatedLon;
    protected double estimatedLat;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint mongoPosition;


    // is mobile device - behavior is different for mobile device to not track
    protected boolean mobile;

    // recent frame with  a limit
    protected List<FrameEntry> recentFrames;

    // hotspot information
    protected List<HotspotEntry> hotspotAround;

    // ======================================================================
    // Constructors
    // ======================================================================

    public void initFromUplinkEvent(UplinkEvent e) {
        if ( this.devEui == null ) {
            // new entry
            this.devEui = e.getDeviceInfo().getDevEui();
            this.frameSeen = 0;
            this.lastSaved = 0;
            this.recentFrames = new ArrayList<>();
            this.hotspotAround = new ArrayList<>();
        } else {
            // update entry

            // check entryFrame size
            if ( this.recentFrames.size() > consolePrivateConfig.getHeliumDevMaxFrameHist() ) {
                // keep the last N-5 entries
                this.recentFrames = this.recentFrames.subList(this.recentFrames.size() - (consolePrivateConfig.getHeliumDevMaxFrameHist()-5), this.recentFrames.size());
            }

        }
        // all
        this.lastSeen = Now.NowUtcMs();
        this.frameSeen++;

        // new FrameEntry
        FrameEntry fe = new FrameEntry();
        fe.setDr(e.getDr());
        fe.setfCnt(e.getfCnt());
        fe.setDataSize(Base64.decode(e.getData()).length);
        fe.setFrameType(FrameEntry.FRAME_TYPE_UPLINK);
        fe.setRxTimeMs(DateConverters.StringDateToMs(e.getTime()));
        ArrayList<FrameHotspot> hss = new ArrayList<>();
        for (UplinkEventRxInfo r : e.getRxInfo()) {
            FrameHotspot fh = new FrameHotspot();
            fh.setHotspotId(r.getMetadata().getGateway_id());
            fh.setRssi(r.getRssi());
            fh.setSnr(r.getSnr());
            hss.add(fh);

            // Update the gateway information
            boolean found = false;
            for (HotspotEntry he : this.hotspotAround) {
                if ( he.getGatewayId().compareToIgnoreCase(fh.getHotspotId()) == 0 ) {
                    // exists
                    found = true;
                    he.setCount(he.getCount()+1);
                    he.setLastSeen(DateConverters.StringDateToMs(r.getGwTime()));
                    he.setSumOfRssi(he.getSumOfRssi()+r.getRssi());
                    he.setSumOfSnr(he.getSumOfSnr()+r.getSnr());
                    break;
                }
            }
            if ( !found ) {
                // new hotspot found
                HotspotEntry he = new HotspotEntry();
                he.setGatewayId(fh.getHotspotId());
                he.setSumOfSnr(r.getSnr());
                he.setSumOfRssi(r.getRssi());
                he.setCount(1);
                he.setLastSeen(DateConverters.StringDateToMs(r.getGwTime()));
                he.setLat(0.0);
                he.setLng(0.0);
                if (   r.getMetadata().getGateway_lat() != null && r.getMetadata().getGateway_lat().length() > 1
                    && r.getMetadata().getGateway_long() != null && r.getMetadata().getGateway_long().length() > 1
                ) {
                    // we have a pos, convert string to double
                    try {
                        he.setLng(Double.parseDouble(r.getMetadata().getGateway_long()));
                        he.setLat(Double.parseDouble(r.getMetadata().getGateway_lat()));
                    } catch (NumberFormatException x) {
                        log.warn("Invalid Gateway location for "+r.getMetadata().getGateway_name()+" (" + r.getMetadata().getGateway_lat() + ", " + r.getMetadata().getGateway_long() + ")");
                    }
                }
                this.hotspotAround.add(he);
            }
        }
        fe.setHotspots(hss);
        this.recentFrames.add(fe);

        // evaluate position / is the device moving ?

        // clean the hotspot list

    }

    public DeviceFrames clone() {
        DeviceFrames d = new DeviceFrames();
        d.setDevEui(this.devEui);
        d.setLastSeen(this.lastSeen);
        d.setLastSaved(this.lastSaved);
        d.setFrameSeen(this.frameSeen);
        d.setEstimatedLat(this.estimatedLat);
        d.setEstimatedLon(this.estimatedLon);
        d.setMongoPosition(this.mongoPosition);
        d.setMobile(this.mobile);
        d.setRecentFrames(new ArrayList<>());
        for (FrameEntry f : this.recentFrames) {
            d.getRecentFrames().add(f.clone());
        }
        d.setHotspotAround(new ArrayList<>());
        for (HotspotEntry h : this.hotspotAround) {
            d.getHotspotAround().add(h.clone());
        }
        return d;
    }


    // ======================================================================
    // Getter & Setters
    // ======================================================================

    public String getDevEui() {
        return devEui;
    }

    public void setDevEui(String devEui) {
        this.devEui = devEui;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public long getLastSaved() {
        return lastSaved;
    }

    public void setLastSaved(long lastSaved) {
        this.lastSaved = lastSaved;
    }

    public long getFrameSeen() {
        return frameSeen;
    }

    public void setFrameSeen(long frameSeen) {
        this.frameSeen = frameSeen;
    }

    public double getEstimatedLon() {
        return estimatedLon;
    }

    public void setEstimatedLon(double estimatedLon) {
        this.estimatedLon = estimatedLon;
    }

    public double getEstimatedLat() {
        return estimatedLat;
    }

    public void setEstimatedLat(double estimatedLat) {
        this.estimatedLat = estimatedLat;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public List<FrameEntry> getRecentFrames() {
        return recentFrames;
    }

    public void setRecentFrames(List<FrameEntry> recentFrames) {
        this.recentFrames = recentFrames;
    }

    public List<HotspotEntry> getHotspotAround() {
        return hotspotAround;
    }

    public void setHotspotAround(List<HotspotEntry> hotspotAround) {
        this.hotspotAround = hotspotAround;
    }

    public GeoJsonPoint getMongoPosition() {
        return mongoPosition;
    }

    public void setMongoPosition(GeoJsonPoint mongoPosition) {
        this.mongoPosition = mongoPosition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
