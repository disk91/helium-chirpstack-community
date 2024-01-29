package eu.heliumiot.console.jpa.mongodb;

import eu.heliumiot.console.ConsolePrivateConfig;
import eu.heliumiot.console.jpa.mongodb.sub.HotspotHourlyUsage;
import fr.ingeniousthings.tools.ClonnableObject;
import fr.ingeniousthings.tools.Now;
import io.chirpstack.json.sub.UplinkEventRxInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Document(collection = "helium_hotspots")
@CompoundIndexes({
    @CompoundIndex(name = "hotspot_Idx", def = "{'hotspotId' : 'hashed' }")
})
public class Hotspots implements ClonnableObject<Hotspots> {

    @Transient
    protected static final int HS_HOURLY_USE_HOURS = 72;

    @Transient
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Id
    private String id = null;

    private String name = null;

    // Key entry
    protected String hotspotId = null;

    // Position
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    protected GeoJsonPoint hotspotPosition;

    // Last time we get traffic on that hotspot
    protected long lastSeen;

    // number of packet processed from this hs
    protected long seen;

    // last 72 hours traffic
    protected List<HotspotHourlyUsage> trafficHistory;

    // history from etl

    protected long lastEtlUpdate;  // last time this entry has been updated from etl


    // ======================================================================
    // Constructors
    // ======================================================================

    public Hotspots clone() {
        Hotspots h = new Hotspots();
        h.setId(this.id);
        h.setHotspotId(this.hotspotId);
        h.setName(this.name);
        h.setHotspotPosition(new GeoJsonPoint(this.hotspotPosition.getX(),this.hotspotPosition.getY()));
        h.setLastSeen(this.lastSeen);
        h.setSeen(this.seen);
        ArrayList<HotspotHourlyUsage> hhu = new ArrayList<>();
        this.trafficHistory.forEach( hu -> hhu.add(hu.clone()) );
        h.setTrafficHistory(hhu);
        h.setLastEtlUpdate(lastEtlUpdate);
        return h;
    }

    public void initFromRxInfo(UplinkEventRxInfo rx, ConsolePrivateConfig config) {
        if ( id == null ) {
            this.lastEtlUpdate = 0;
            this.hotspotId = rx.getMetadata().getGateway_id().toLowerCase();
            this.name = rx.getMetadata().getGateway_name().toLowerCase();
            this.hotspotPosition = new GeoJsonPoint(0,0);
            if (   rx.getMetadata().getGateway_lat() != null && rx.getMetadata().getGateway_lat().length() > 1
                && rx.getMetadata().getGateway_long() != null && rx.getMetadata().getGateway_long().length() > 1
            ) {
                // we have a pos, convert string to double
                try {
                    this.hotspotPosition = new GeoJsonPoint(
                                Double.parseDouble(rx.getMetadata().getGateway_long()),
                                Double.parseDouble(rx.getMetadata().getGateway_lat())
                    );
                } catch (NumberFormatException x) {
                    log.warn("Invalid Gateway location for "+rx.getMetadata().getGateway_name()+" (" + rx.getMetadata().getGateway_lat() + ", " + rx.getMetadata().getGateway_long() + ")");
                    this.hotspotPosition = new GeoJsonPoint(0,0);
                }
            } else { this.hotspotPosition = new GeoJsonPoint(0,0); }
            this.trafficHistory = new ArrayList<>();
        }
        long now = Now.NowUtcMs();
        this.lastSeen = now;
        this.seen++;

        long baseHour = Now.ThisHourUtc(now);
        if (!this.trafficHistory.isEmpty() && this.trafficHistory.getLast().getHourRef() == baseHour ) {
            HotspotHourlyUsage hhu = this.trafficHistory.getLast();
            hhu.setPacketsSeen(hhu.getPacketsSeen()+1);
        } else {
            // Create all the missing slots between two data to have 1 entry for the last 72 hours
            long start = baseHour - (HS_HOURLY_USE_HOURS-1)*Now.ONE_HOUR;
            if ( !this.trafficHistory.isEmpty() && this.trafficHistory.getLast().getHourRef() >= start ) {
                start = this.trafficHistory.getLast().getHourRef();
            }
            while ( start < baseHour ) {
                HotspotHourlyUsage hhu = new HotspotHourlyUsage();
                hhu.setHourRef(start);
                hhu.setPacketsSeen(0);
                this.trafficHistory.add(hhu);
                start += Now.ONE_HOUR;
            }
            HotspotHourlyUsage hhu = new HotspotHourlyUsage();
            hhu.setHourRef(baseHour);
            hhu.setPacketsSeen(1);
            this.trafficHistory.add(hhu);

            // delete the slot older than HS_HOURLY_USE_HOURS hours
            if ( this.trafficHistory.size() > HS_HOURLY_USE_HOURS ) {
                this.trafficHistory = this.trafficHistory.subList(this.trafficHistory.size()-HS_HOURLY_USE_HOURS,this.trafficHistory.size());
            }
        }

    }

    // ======================================================================
    // Getter & Setters
    // ======================================================================


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotspotId() {
        return hotspotId;
    }

    public void setHotspotId(String hotspotId) {
        this.hotspotId = hotspotId;
    }

    public GeoJsonPoint getHotspotPosition() {
        return hotspotPosition;
    }

    public void setHotspotPosition(GeoJsonPoint hotspotPosition) {
        this.hotspotPosition = hotspotPosition;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public long getSeen() {
        return seen;
    }

    public void setSeen(long seen) {
        this.seen = seen;
    }

    public List<HotspotHourlyUsage> getTrafficHistory() {
        return trafficHistory;
    }

    public void setTrafficHistory(List<HotspotHourlyUsage> trafficHistory) {
        this.trafficHistory = trafficHistory;
    }

    public long getLastEtlUpdate() {
        return lastEtlUpdate;
    }

    public void setLastEtlUpdate(long lastEtlUpdate) {
        this.lastEtlUpdate = lastEtlUpdate;
    }
}
