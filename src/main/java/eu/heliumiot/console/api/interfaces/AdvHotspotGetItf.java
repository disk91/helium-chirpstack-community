/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2024.
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 *    and associated documentation files (the "Software"), to deal in the Software without restriction,
 *    including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *    sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies or
 *    substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package eu.heliumiot.console.api.interfaces;

import eu.heliumiot.console.etl.api.HotspotData;
import eu.heliumiot.console.etl.api.sub.RewardHistory;
import eu.heliumiot.console.etl.api.sub.WitnessHistory;
import eu.heliumiot.console.jpa.mongodb.DeviceFrames;
import eu.heliumiot.console.jpa.mongodb.Hotspots;
import eu.heliumiot.console.jpa.mongodb.sub.FrameEntry;
import eu.heliumiot.console.jpa.mongodb.sub.HotspotEntry;
import eu.heliumiot.console.jpa.mongodb.sub.HotspotHourlyUsage;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Device Frames History", description = "data related to last communication for a given device")
public class HotspotGetItf {

    @Schema(
        description = "hostpot ID",
        example = "JKHDKJHLA7HSANkldfjqsjdhj9PO",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String hotspotId;

    @Schema(
        description = "hotspot animal name",
        example = "dazzling-sun-solar",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String name;

    @Schema(
        description = "last activity from epoc in ms",
        example = "172485667000",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long lastSeen;

    @Schema(
        description = "packets seen",
        example = "4000",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long seen;


    @Schema(
        description = "last 72 hour seen traffic history (packets)",
        example = "",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected List<HotspotHourlyUsage> trafficHistory;

    @Schema(
        description = "last ETL data update timestamp",
        example = "172485667000",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long lastEtlUpdate;

    @Schema(
        description = "last beacon timestamp (ms)",
        example = "172485667000",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long lastBeaconMs;

    @Schema(
        description = "last witness timestamp (ms)",
        example = "172485667000",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long lastWitnessMs;

    @Schema(
        description = "last reward timestamp (ms)",
        example = "172485667000",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long lastRewardMs;

    @Schema(
        description = "Cumulated oT Rewards",
        example = "21351131531",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long sumOfIoTRewards;

    @Schema(
        description = "Number of HS around beaconned",
        example = "45",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected int beaconned;

    @Schema(
        description = "Max Transmission seen distance (m)",
        example = "45000",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long maxTxDistance;


    @Schema(
        description = "Number of HS witnessed",
        example = "60",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected int witnessed;    // number of hs around witnessed (TX)

    @Schema(
        description = "Max Reception seen distance (m)",
        example = "45000",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long maxRxDistance;

    @Schema(
        description = "Max seen budget link (dBm)",
        example = "140",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long maxRxBudgetLinkDB;

    @Schema(
        description = "Hotspot manufacturer",
        example = "140",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected HotspotData.HotspotBrand brand;     // Hs Brand


    @Schema(
        description = "last 48h witness history",
        example = "",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected List<WitnessHistory> witnessesHistory;

    @Schema(
        description = "last 5 days reward history",
        example = "",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected List<RewardHistory> rewardHistories;


    // ===

    public void initFromHotspots(Hotspots h) {
        this.hotspotId = h.getHotspotId();
        this.name = h.getName();
        this.lastSeen = h.getLastSeen();
        this.seen = h.getSeen();
        this.trafficHistory = h.getTrafficHistory();
        this.lastEtlUpdate = h.getLastEtlUpdate();
        this.lastBeaconMs = h.getLastBeaconMs();
        this.lastWitnessMs = h.getLastWitnessMs();
        this.lastRewardMs = h.getLastRewardMs();
        this.sumOfIoTRewards = h.getSumOfIoTRewards();
        this.beaconned = h.getBeaconned();
        this.maxTxDistance = h.getMaxTxDistance();
        this.witnessed = h.getWitnessed();
        this.maxRxDistance = h.getMaxRxDistance();
        this.maxRxBudgetLinkDB = h.getMaxRxBudgetLinkDB();
        this.brand = h.getBrand();
        this.witnessesHistory = h.getWitnessesHistory();
        this.rewardHistories = h.getRewardHistories();
    }

    // ===


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

    public long getMaxTxDistance() {
        return maxTxDistance;
    }

    public void setMaxTxDistance(long maxTxDistance) {
        this.maxTxDistance = maxTxDistance;
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

    public long getMaxRxBudgetLinkDB() {
        return maxRxBudgetLinkDB;
    }

    public void setMaxRxBudgetLinkDB(long maxRxBudgetLinkDB) {
        this.maxRxBudgetLinkDB = maxRxBudgetLinkDB;
    }

    public HotspotData.HotspotBrand getBrand() {
        return brand;
    }

    public void setBrand(HotspotData.HotspotBrand brand) {
        this.brand = brand;
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
}
