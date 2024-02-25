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

import java.util.List;

@Tag(name = "Inactiv Device Search Response", description = "Information about inactive devices")
public class AdvDeviceInacGetItf {

    @Schema(
        description = "Tenant UUID",
        example = "5464616-56151651-51556-15161",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tenantUUID;

    @Schema(
        description = "Tenant Name",
        example = "myTenant",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tenantName;

    @Schema(
        description = "Total inactive devices",
        example = "15",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long inactivCount;

    @Schema(
        description = "Current page",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long currentPage;

    @Schema(
        description = "Last page number 0 .. X",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long totalPage;

    @Schema(
        description = "max entries per page",
        example = "50",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long perPage;

    @Schema(
        description = "List of inactiv devices",
        example = "",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<AdvDeviceInacSubItf> inactives;


    // ---

    // ---


    public String getTenantUUID() {
        return tenantUUID;
    }

    public void setTenantUUID(String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public long getInactivCount() {
        return inactivCount;
    }

    public void setInactivCount(long inactivCount) {
        this.inactivCount = inactivCount;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public List<AdvDeviceInacSubItf> getInactives() {
        return inactives;
    }

    public void setInactives(List<AdvDeviceInacSubItf> inactives) {
        this.inactives = inactives;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getPerPage() {
        return perPage;
    }

    public void setPerPage(long perPage) {
        this.perPage = perPage;
    }
}
