package eu.heliumiot.console.etl.api;

import eu.heliumiot.console.etl.api.sub.*;
import fr.ingeniousthings.tools.Now;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Hotspot Sate", description = "Information related to an hotspot (summarized)")
public class HotspotState {

    @Schema(
            description = "Hexstring Base58 of the Hotspot public key, aka Hs address",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String hotspotId;

    @Schema(
            description = "Animal name of the hostpot with - between words",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String animalName;

    @Schema(
            description = "Hotspot Owner address",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Owner owner;

    @Schema(
            description = "Hotspot position, lat / lng, can be 0,0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LatLng position;


    @Schema(
            description = "Timestamp ms / utc, First time a hotspot received rewards - all old initialized on April 19th 2023",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long firstSeen = 0;

    @Schema(
            description = "Timestamp ms / utc, Last Time this hotspot had a beacon",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long lastBeacon;

    @Schema(
            description = "Timestamp ms / utc, Last time this hotspot get a witness",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long lastWitness;

    @Schema(
            description = "Timestamp ms / utc, Last Time this hotspot get a reward (once a day)",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long lastReward;

    @Schema(
            description = "Total income since April 19th for beacon activities (in IoT Bones)",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long sumRewardBeacon;
    @Schema(
            description = "Total income since April 19th for witness activities (in IoT bones)",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long sumRewardWitness;

    @Schema(
            description = "Total income since April 19th for Dcs activities (in IoT bones)",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long sumRewardDc;

    @Schema(
            description = "Previous hostpot rewards (in HNT)",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long offsetReward;

    @Schema(
        description = "Number of beaconner for this hotspot",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int beaconned;

    @Schema(
        description = "Number of witnesser for this hotspot",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int witnessed;

    @Schema(
        description = "Max distance for RX seen",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long maxRxDistance;

    @Schema(
        description = "Max distance for TX seen",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long maxTxDistance;

    @Schema(
        description = "Max RX budget link seen",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long maxRxBudgetLinkDB;


    @Schema(
            description = "Witness history, quantitative, per hour, max 48h",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<WitnessHistory> witnessesHistory;


    @Schema(
            description = "Rewards history, quantitative, per hour, for real, once a day, 5 days",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RewardHistory> rewardHistories;

    @Schema(
            description = "Hotspot Brand",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private HotspotData.HotspotBrand brand;



    // ---------------------------------------------------------
    // clone



    // -------------------
    // Getter & Setters


    public String getHotspotId() {
        return hotspotId;
    }

    public void setHotspotId(String hotspotId) {
        this.hotspotId = hotspotId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public long getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(long firstSeen) {
        this.firstSeen = firstSeen;
    }

    public long getLastBeacon() {
        return lastBeacon;
    }

    public void setLastBeacon(long lastBeacon) {
        this.lastBeacon = lastBeacon;
    }

    public long getLastWitness() {
        return lastWitness;
    }

    public void setLastWitness(long lastWitness) {
        this.lastWitness = lastWitness;
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

    public long getSumRewardDc() {
        return sumRewardDc;
    }

    public void setSumRewardDc(long sumRewardDc) {
        this.sumRewardDc = sumRewardDc;
    }

    public long getOffsetReward() {
        return offsetReward;
    }

    public void setOffsetReward(long offsetReward) {
        this.offsetReward = offsetReward;
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

    public List<RewardHistory> getRewardHistories() {
        return rewardHistories;
    }

    public void setRewardHistories(List<RewardHistory> rewardHistories) {
        this.rewardHistories = rewardHistories;
    }

    public HotspotData.HotspotBrand getBrand() {
        return brand;
    }

    public void setBrand(HotspotData.HotspotBrand brand) {
        this.brand = brand;
    }
}
