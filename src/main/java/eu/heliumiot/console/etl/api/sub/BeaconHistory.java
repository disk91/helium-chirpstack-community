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

public class BeaconHistory implements ClonnableObject<BeaconHistory>  {

    private long timeRef;           // corresponding to the hour of the history
    private int countBeacon;        // number of beacon during this period of time

    // --------

    public BeaconHistory clone() {
        BeaconHistory c = new BeaconHistory();
        c.setTimeRef(timeRef);
        c.setCountBeacon(countBeacon);
        return c;
    }

    // ---------


    public long getTimeRef() {
        return timeRef;
    }

    public void setTimeRef(long timeRef) {
        this.timeRef = timeRef;
    }

    public int getCountBeacon() {
        return countBeacon;
    }

    public void setCountBeacon(int countBeacon) {
        this.countBeacon = countBeacon;
    }
}
