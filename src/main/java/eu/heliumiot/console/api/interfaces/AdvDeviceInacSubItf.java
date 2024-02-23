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

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Inactiv Device Details", description = "Each of the inactive device details")
public class AdvDeviceInacSubItf {

    @Schema(
        description = "Dev EUI",
        example = "10203040506078",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String devEui;

    @Schema(
        description = "Dev Name",
        example = "my device",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String devName;

    @Schema(
        description = "Application Name",
        example = "myApplication",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String applicationName;

    @Schema(
        description = "Application UUID",
        example = "12312-513213-51321-2131-3131",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String applicationId;


    @Schema(
        description = "Device Creation Date (Ms since epoc)",
        example = "16213156121",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long creationDate;

    @Schema(
        description = "Device Last Seen Date (Ms since epoc) 0 = never",
        example = "16213156121",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long lastSeenDate;

    @Schema(
        description = "Device addr or empty",
        example = "48C231",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String devAddr;

    @Schema(
        description = "is Disable",
        example = "true",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean disabled;

    @Schema(
        description = "is routing eui ok",
        example = "0(false)/1(unknown)/2(true)",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int routeEui;

    @Schema(
        description = "is routing skfs ok",
        example = "0(false)/1(unknown)/2(true)",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int routeSkfs;

    @Schema(
        description = "is skfs collision",
        example = "0(false)/1(unknown)/2(true)",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int skfsCollision;

    @Schema(
        description = "is never seen",
        example = "true",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean neverSeen;

    @Schema(
        description = "is only Join Req",
        example = "0(false)/1(unknown)/2(true)",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int onlyJoinReq;

    @Schema(
        description = "is coverage risky",
        example = "0(false)/1(unknown)/2(true)",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int coverageRisk;


    // ---

    // ---


    public String getDevEui() {
        return devEui;
    }

    public void setDevEui(String devEui) {
        this.devEui = devEui;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public long getLastSeenDate() {
        return lastSeenDate;
    }

    public void setLastSeenDate(long lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
    }

    public String getDevAddr() {
        return devAddr;
    }

    public void setDevAddr(String devAddr) {
        this.devAddr = devAddr;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getRouteEui() {
        return routeEui;
    }

    public void setRouteEui(int routeEui) {
        this.routeEui = routeEui;
    }

    public int getRouteSkfs() {
        return routeSkfs;
    }

    public void setRouteSkfs(int routeSkfs) {
        this.routeSkfs = routeSkfs;
    }

    public int getSkfsCollision() {
        return skfsCollision;
    }

    public void setSkfsCollision(int skfsCollision) {
        this.skfsCollision = skfsCollision;
    }

    public boolean isNeverSeen() {
        return neverSeen;
    }

    public void setNeverSeen(boolean neverSeen) {
        this.neverSeen = neverSeen;
    }

    public int getOnlyJoinReq() {
        return onlyJoinReq;
    }

    public void setOnlyJoinReq(int onlyJoinReq) {
        this.onlyJoinReq = onlyJoinReq;
    }

    public int getCoverageRisk() {
        return coverageRisk;
    }

    public void setCoverageRisk(int coverageRisk) {
        this.coverageRisk = coverageRisk;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }
}
