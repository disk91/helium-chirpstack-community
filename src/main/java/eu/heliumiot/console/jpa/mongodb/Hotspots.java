/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2024.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package eu.heliumiot.console.jpa.mongodb;

import eu.heliumiot.console.ConsolePrivateConfig;
import eu.heliumiot.console.etl.api.HotspotData;
import eu.heliumiot.console.etl.api.sub.RewardHistory;
import eu.heliumiot.console.etl.api.sub.WitnessHistory;
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
    protected int failure;          // number of failure since last update in success
    protected long lastBeaconMs;    // last Beacon timestamp
    protected long lastWitnessMs;   // last Witness timestamp
    protected long lastRewardMs;    // last Reward timestamp
    protected long sumOfIoTRewards; // total IoT Rewards
    protected int beaconned;    // number of hs around beaconned (RX)
    protected int witnessed;    // number of hs around witnessed (TX)
    protected long maxRxDistance;   // Rx distance in meter
    protected long maxTxDistance; // Tx distance in meter
    protected long maxRxBudgetLinkDB; // max seen budget link
    protected List<WitnessHistory> witnessesHistory;    // last 48h witnesses per hour
    protected HotspotData.HotspotBrand brand;     // Hs Brand
    protected List<RewardHistory> rewardHistories;  // lst 5 day rewards


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
        h.setLastWitnessMs(lastWitnessMs);
        h.setLastBeaconMs(lastBeaconMs);
        h.setLastRewardMs(lastRewardMs);
        h.setSumOfIoTRewards(sumOfIoTRewards);
        h.setBeaconned(beaconned);
        h.setWitnessed(witnessed);
        h.setMaxRxDistance(maxRxDistance);
        h.setMaxTxDistance(maxTxDistance);
        h.setMaxRxBudgetLinkDB(maxRxBudgetLinkDB);
        h.setBrand(brand);
        h.setFailure(failure);
        List<RewardHistory> rhs = new ArrayList<>();
        if ( h.getRewardHistories() != null ) {
            for (RewardHistory bh : h.getRewardHistories()) {
                rhs.add(bh.clone());
            }
        }
        h.setRewardHistories(rhs);
        List<WitnessHistory> whs = new ArrayList<>();
        if ( h.getWitnessesHistory() != null ) {
            for (WitnessHistory wh : h.getWitnessesHistory()) {
                whs.add(wh.clone());
            }
        }
        h.setWitnessesHistory(whs);
        return h;
    }

    public void initFromRxInfo(UplinkEventRxInfo rx, ConsolePrivateConfig config) {
        if ( id == null ) {
            this.lastEtlUpdate = 0;
            this.failure = 0;
            this.hotspotId = rx.getMetadata().getGateway_id();
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
            this.lastWitnessMs=0;
            this.lastBeaconMs=0;
            this.lastRewardMs=0;
            this.sumOfIoTRewards=0;
            this.beaconned=0;
            this.witnessed=0;
            this.maxRxDistance=0;
            this.maxTxDistance=0;
            this.maxRxBudgetLinkDB=0;
            this.brand= HotspotData.HotspotBrand.UNKNOWN;
            this.rewardHistories=new ArrayList<>();
            this.witnessesHistory=new ArrayList<>();
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

    public long getLastBeaconMs() {
        return lastBeaconMs;
    }

    public void setLastBeaconMs(long lastBeaconMs) {
        this.lastBeaconMs = lastBeaconMs;
    }

    public long getLastWitnessMs() {
        return lastWitnessMs;
    }

    public void setLastWitnessMs(long lastWitnessMs) {
        this.lastWitnessMs = lastWitnessMs;
    }

    public long getLastRewardMs() {
        return lastRewardMs;
    }

    public void setLastRewardMs(long lastRewardMs) {
        this.lastRewardMs = lastRewardMs;
    }

    public long getSumOfIoTRewards() {
        return sumOfIoTRewards;
    }

    public void setSumOfIoTRewards(long sumOfIoTRewards) {
        this.sumOfIoTRewards = sumOfIoTRewards;
    }

    public int getBeaconned() {
        return beaconned;
    }

    public void setBeaconned(int beaconned) {
        this.beaconned = beaconned;
    }

    public int getWitnessed() {
        return witnessed;
    }

    public void setWitnessed(int witnessed) {
        this.witnessed = witnessed;
    }

    public long getMaxRxDistance() {
        return maxRxDistance;
    }

    public void setMaxRxDistance(long maxRxDistance) {
        this.maxRxDistance = maxRxDistance;
    }

    public long getMaxTxDistance() {
        return maxTxDistance;
    }

    public void setMaxTxDistance(long maxTxDistance) {
        this.maxTxDistance = maxTxDistance;
    }

    public long getMaxRxBudgetLinkDB() {
        return maxRxBudgetLinkDB;
    }

    public void setMaxRxBudgetLinkDB(long maxRxBudgetLinkDB) {
        this.maxRxBudgetLinkDB = maxRxBudgetLinkDB;
    }

    public List<WitnessHistory> getWitnessesHistory() {
        return witnessesHistory;
    }

    public void setWitnessesHistory(List<WitnessHistory> witnessesHistory) {
        this.witnessesHistory = witnessesHistory;
    }

    public HotspotData.HotspotBrand getBrand() {
        return brand;
    }

    public void setBrand(HotspotData.HotspotBrand brand) {
        this.brand = brand;
    }

    public List<RewardHistory> getRewardHistories() {
        return rewardHistories;
    }

    public void setRewardHistories(List<RewardHistory> rewardHistories) {
        this.rewardHistories = rewardHistories;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }
}
