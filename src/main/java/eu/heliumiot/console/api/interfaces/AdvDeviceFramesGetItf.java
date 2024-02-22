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

package eu.heliumiot.console.api.interfaces;

import eu.heliumiot.console.jpa.mongodb.DeviceFrames;
import eu.heliumiot.console.jpa.mongodb.sub.FrameEntry;
import eu.heliumiot.console.jpa.mongodb.sub.HotspotEntry;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Device Frames History", description = "data related to last communication for a given device")
public class DeviceFramesGetItf {

    @Schema(
        description = "device EUI",
        example = "0c2542789601",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String devEui;

    @Schema(
        description = "device name",
        example = "myDevice",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String devName;

    @Schema(
        description = "tenant ID",
        example = "0000156-11515353-121321-221131",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String tenantID;

    @Schema(
        description = "tenant Name",
        example = "myTenant",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String tenantName;


    @Schema(
        description = "last time (since epoc Ms) the device has been seen",
        example = "172485667000",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long lastSeen;

    @Schema(
        description = "total number of frame seen eceived by this device",
        example = "255",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long frameSeen;

    // estimated position
    @Schema(
        description = "Estimated device position (Longitude)",
        example = "3.42",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected double estimatedLon;

    @Schema(
        description = "Estimated device position (Latitude)",
        example = "45.2",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected double estimatedLat;


    @Schema(
        description = "True when the device is moving",
        example = "false",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected boolean mobile;

    @Schema(
        description = "Last seen communications",
        example = "...",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected List<FrameEntry> recentFrames;

    @Schema(
        description = "Hotspot around involved in communication with this device",
        example = "...",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected List<HotspotEntry> hotspotAround;

    // ===

    public void initFromDeviceFrames(DeviceFrames d) {
        this.devEui = d.getDevEui();
        this.lastSeen = d.getLastSeen();
        this.frameSeen = d.getFrameSeen();
        this.estimatedLon = d.getEstimatedLon();
        this.estimatedLat = d.getEstimatedLat();
        this.mobile = d.isMobile();
        this.recentFrames = d.getRecentFrames();
        this.hotspotAround = d.getHotspotAround();
        this.devName = "";
        this.tenantID = "";
        this.tenantName ="";
    }

    // ===

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

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}
