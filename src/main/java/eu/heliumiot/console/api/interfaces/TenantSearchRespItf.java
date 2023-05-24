/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2018.
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

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tenant search response Interface", description = "search tenant based on owner, id, name")
public class TenantSearchRespItf {


    @Schema(
            description = "Tenant UUID",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String tenantUUID;

    @Schema(
            description = "Route Id",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String routeId;


    @Schema(
            description = "Tenant owner or actor",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String ownerEmail;

    @Schema(
            description = "Tenant name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String tenantName;

    @Schema(
            description = "DC balance",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long dcBalance;


    // ------------------------------------


    public String getTenantUUID() {
        return tenantUUID;
    }

    public void setTenantUUID(String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public long getDcBalance() {
        return dcBalance;
    }

    public void setDcBalance(long dcBalance) {
        this.dcBalance = dcBalance;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
}
