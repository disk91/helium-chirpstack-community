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

import eu.heliumiot.console.jpa.db.HeliumDevice;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Device Search Response", description = "Basic data related to device as a result of a search")
public class AdvDeviceSearchGetItf {

    @Schema(
        description = "device EUI",
        example = "0c2542789601",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String deviceEui;

    @Schema(
        description = "Application UUID",
        example = "5464616-56151651-51556-15161",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String applicationUUID;

    @Schema(
        description = "Tenant UUID",
        example = "5464616-56151651-51556-15161",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tenantUUID;

    // ---

    public void initFromHeliumDevice(HeliumDevice d) {
        this.deviceEui = d.getDeviceEui();
        this.applicationUUID = d.getApplicationUUID();
        this.tenantUUID = d.getTenantUUID();
    }

    // ---

    public String getDeviceEui() {
        return deviceEui;
    }

    public void setDeviceEui(String deviceEui) {
        this.deviceEui = deviceEui;
    }

    public String getApplicationUUID() {
        return applicationUUID;
    }

    public void setApplicationUUID(String applicationUUID) {
        this.applicationUUID = applicationUUID;
    }

    public String getTenantUUID() {
        return tenantUUID;
    }

    public void setTenantUUID(String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }
}
