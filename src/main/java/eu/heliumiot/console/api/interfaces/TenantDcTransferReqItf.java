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

@Tag(name = "Transfer DC between Tenant Request", description = "transfer DCs between a tenant owned by the user and a tenant with access")
public class TenantDcTransferReqItf {


    @Schema(
            description = "Source Tenant UUID",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String tenantSrcUUID;

    @Schema(
            description = "Destination Tenant UUID",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String tenantDestUUID;


    @Schema(
            description = "Quantity of DCs to be transferred",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long dcs;


    // ------------------------------------


    public String getTenantSrcUUID() {
        return tenantSrcUUID;
    }

    public void setTenantSrcUUID(String tenantSrcUUID) {
        this.tenantSrcUUID = tenantSrcUUID;
    }

    public String getTenantDestUUID() {
        return tenantDestUUID;
    }

    public void setTenantDestUUID(String tenantDestUUID) {
        this.tenantDestUUID = tenantDestUUID;
    }

    public long getDcs() {
        return dcs;
    }

    public void setDcs(long dcs) {
        this.dcs = dcs;
    }
}
