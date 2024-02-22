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
