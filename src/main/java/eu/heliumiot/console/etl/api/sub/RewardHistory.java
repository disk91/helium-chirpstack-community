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

package eu.heliumiot.console.etl.api.sub;

import fr.ingeniousthings.tools.ClonnableObject;

public class RewardHistory implements ClonnableObject<RewardHistory> {

    private long timeRef;           // corresponding to the hour of the history
    private long witnessReward;
    private long beaconReward;
    private long dataReward;

    // ---------

    public RewardHistory clone() {
        RewardHistory c = new RewardHistory();
        c.setTimeRef(timeRef);
        c.setWitnessReward(witnessReward);
        c.setBeaconReward(beaconReward);
        c.setDataReward(dataReward);
        return c;
    }


    // ---------


    public long getTimeRef() {
        return timeRef;
    }

    public void setTimeRef(long timeRef) {
        this.timeRef = timeRef;
    }


    public long getWitnessReward() {
        return witnessReward;
    }

    public void setWitnessReward(long witnessReward) {
        this.witnessReward = witnessReward;
    }

    public long getBeaconReward() {
        return beaconReward;
    }

    public void setBeaconReward(long beaconReward) {
        this.beaconReward = beaconReward;
    }

    public long getDataReward() {
        return dataReward;
    }

    public void setDataReward(long dataReward) {
        this.dataReward = dataReward;
    }
}
