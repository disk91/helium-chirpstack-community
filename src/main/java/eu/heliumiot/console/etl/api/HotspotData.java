package eu.heliumiot.console.etl.api;

import eu.heliumiot.console.etl.api.sub.*;
import fr.ingeniousthings.tools.ClonnableObject;
import fr.ingeniousthings.tools.Now;

import java.util.List;

public class HotspotData implements ClonnableObject<HotspotData> {

    private String hotspotId;
    private String animalName;

    private Owner owner;
    private List<Owner> ownerHistory;

    private LatLng position;

    private List<LatLng> posHistory;
    private int version;

    private long firstSeen = 0;

    private long lastBeacon;

    private long lastWitness;

    private long lastReward;

    private long sumRewardBeacon;
    private long sumRewardWitness;

    private long sumRewardDc;

    private long offsetReward;

    // List of hotspots receiving this hotspot
    private List<Witness> beaconned;

    // List of hotspot this one is receiving
    private List<Witness> witnesses;

    // Quantitiative history
    private List<WitnessHistory> witnessesHistory;

    // Quantitative history
    private List<BeaconHistory> beaconHistory;

    // Reward history (once a day)
    private List<RewardHistory> rewardHistories;

    public enum HotspotBrand {
        UNKNOWN, HELIUM, RAK, NEBRA, KERLINK, COTX, SENSECAP, SYNCROBIT, BOBCAT, LONGAP,
        SMARTMIMIC, CALCHIP, DEWI, PISCES, CLODPI, LINXDOT, CONTROLLINO, HELTEC, FREEDOMFI, PANTHERX,
        HUMMINGBIRD, RISINGHF, BROWAN, MILESIGHT, DEEPER, MIDAS, DRAGINO, PYCOM
    };
    protected HotspotBrand brand = HotspotBrand.UNKNOWN;


    public HotspotData clone() {
        return this;
    }

    // -------------------
    // helpers

    public long getTotalRewards() {
        return (this.getSumRewardDc()+this.getSumRewardWitness()+this.getSumRewardBeacon()) / 1_000_000;
    }

    public double getRewardBetween(long from, long to) {
        long rewards = 0;
        if ( this.rewardHistories == null ) return 0.0;
        for ( RewardHistory r : this.rewardHistories ) {
            if ( r.getTimeRef() >= from && r.getTimeRef() < to ) {
                rewards += r.getBeaconReward() + r.getDataReward() + r.getWitnessReward();
            }
        }
        return rewards / 1_000_000.0;
    }

    public double getBeaconRewardsBetween(long from, long to) {
        long rewards = 0;
        if ( this.rewardHistories == null ) return 0.0;
        for ( RewardHistory r : this.rewardHistories ) {
            if ( r.getTimeRef() >= from && r.getTimeRef() < to ) {
                rewards += r.getBeaconReward();
            }
        }
        return rewards / 1_000_000.0;
    }

    public double getWitnessRewardsBetween(long from, long to) {
        long rewards = 0;
        if ( this.rewardHistories == null ) return 0.0;
        for ( RewardHistory r : this.rewardHistories ) {
            if ( r.getTimeRef() >= from && r.getTimeRef() < to ) {
                rewards += r.getWitnessReward();
            }
        }
        return rewards / 1_000_000.0;
    }

    public int getWitnessBetween(long from, long to) {
        int w = 0;
        if ( this.witnessesHistory == null ) return 0;
        for ( WitnessHistory r : this.witnessesHistory ) {
            if ( r.getTimeRef() >= from && r.getTimeRef() < to ) {
                w += r.getCountWitnesses();
            }
        }
        return w;
    }

    public int getSelWitnessBetween(long from, long to) {
        int w = 0;
        if ( this.witnessesHistory == null ) return 0;
        for ( WitnessHistory r : this.witnessesHistory ) {
            if ( r.getTimeRef() >= from && r.getTimeRef() < to ) {
                w += r.getSeletedWitness();
            }
        }
        return w;
    }

    public int getBeaconBetween(long from, long to) {
        int w = 0;
        if ( this.beaconHistory == null ) return 0;
        for ( BeaconHistory r : this.beaconHistory ) {
            if ( r.getTimeRef() >= from && r.getTimeRef() < to ) {
                w += r.getCountBeacon();
            }
        }
        return w;
    }

    public long getLastDataReceived() {
        long t = 0;
        for ( RewardHistory r : this.rewardHistories ) {
            if ( r.getTimeRef() > t && r.getDataReward() > 0 ) t = r.getTimeRef();
        }
        return t;
    }


    /**
     * Return the estimate for the already received beacon & witnesses for that day
     * @return
     */
    public double getTodayRewardProjection() {
        double days = 3.0;
        long now = Now.NowUtcMs();
        long todayStart = Now.TodayMidnightUtc();
        long pastStart = todayStart - ((long)days*Now.ONE_FULL_DAY);

        int b = this.getBeaconBetween(todayStart,now);
        int w = this.getWitnessBetween(todayStart,now);
        double b1 = this.getBeaconBetween(pastStart,todayStart)/days;
        double w1 = this.getWitnessBetween(pastStart,todayStart)/days;

        double rew = 0.0;
        if ( w1 > 0.0 || b1 > 0.0 ) {
            double wr1 = this.getWitnessRewardsBetween(pastStart,todayStart)/days;
            double br1 = this.getBeaconRewardsBetween(pastStart,todayStart)/days;
            if ( b1 > 0 ) rew += b * ( br1/b1 );
            if ( w1 > 0 ) rew += w * ( wr1/w1 );
        }
        return rew;
    }

    // -------------------
    // Getter & Setters

    public long getLastBeacon() {
        return lastBeacon;
    }

    public void setLastBeacon(long lastBeacon) {
        this.lastBeacon = lastBeacon;
    }

    public List<Witness> getWitnesses() {
        return witnesses;
    }

    public void setWitnesses(List<Witness> witnesses) {
        this.witnesses = witnesses;
    }

    public List<WitnessHistory> getWitnessesHistory() {
        return witnessesHistory;
    }

    public void setWitnessesHistory(List<WitnessHistory> witnessesHistory) {
        this.witnessesHistory = witnessesHistory;
    }

    public List<BeaconHistory> getBeaconHistory() {
        return beaconHistory;
    }

    public void setBeaconHistory(List<BeaconHistory> beaconHistory) {
        this.beaconHistory = beaconHistory;
    }

    public long getLastWitness() {
        return lastWitness;
    }

    public void setLastWitness(long lastWitness) {
        this.lastWitness = lastWitness;
    }

    public String getHotspotId() {
        return hotspotId;
    }

    public void setHotspotId(String hotspotId) {
        this.hotspotId = hotspotId;
    }

    public List<Witness> getBeaconned() {
        return beaconned;
    }

    public void setBeaconned(List<Witness> beaconned) {
        this.beaconned = beaconned;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public List<LatLng> getPosHistory() {
        return posHistory;
    }

    public void setPosHistory(List<LatLng> posHistory) {
        this.posHistory = posHistory;
    }

    public long getLastReward() {
        return lastReward;
    }

    public void setLastReward(long lastReward) {
        this.lastReward = lastReward;
    }

    public long getSumRewardBeacon() {
        return sumRewardBeacon;
    }

    public void setSumRewardBeacon(long sumRewardBeacon) {
        this.sumRewardBeacon = sumRewardBeacon;
    }

    public long getSumRewardWitness() {
        return sumRewardWitness;
    }

    public void setSumRewardWitness(long sumRewardWitness) {
        this.sumRewardWitness = sumRewardWitness;
    }

    public long getOffsetReward() {
        return offsetReward;
    }

    public void setOffsetReward(long offsetReward) {
        this.offsetReward = offsetReward;
    }

    public long getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(long firstSeen) {
        this.firstSeen = firstSeen;
    }

    public long getSumRewardDc() {
        return sumRewardDc;
    }

    public void setSumRewardDc(long sumRewardDc) {
        this.sumRewardDc = sumRewardDc;
    }

    public List<RewardHistory> getRewardHistories() {
        return rewardHistories;
    }

    public void setRewardHistories(List<RewardHistory> rewardHistories) {
        this.rewardHistories = rewardHistories;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Owner> getOwnerHistory() {
        return ownerHistory;
    }

    public void setOwnerHistory(List<Owner> ownerHistory) {
        this.ownerHistory = ownerHistory;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public HotspotBrand getBrand() {
        return brand;
    }

    public void setBrand(HotspotBrand brand) {
        this.brand = brand;
    }
}
