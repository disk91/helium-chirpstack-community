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

@Tag(name = "Tenant configuration", description = "Tenant information limited to what is needed for purchasing DCs")
public class TenantSetupRespItf {


    @Schema(
            description = "Id of the tenant template",
            example = "default",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tenantUUID;

    @Schema(
            description = "Minimum authorized DC balance for a tenant before deactivation of devices. Can be negative",
            example = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long dcBalanceStop;


    @Schema(
            description = "Price of a single DC in $",
            example = "0.00001",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private double dcPrice;

    @Schema(
            description = "Minimum amount of DCs a user can buy inside the platform",
            example = "100000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long dcMin;

    // ------------------------------------

    public String getTenantUUID() {
        return tenantUUID;
    }

    public void setTenantUUID(String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }

    public long getDcBalanceStop() {
        return dcBalanceStop;
    }

    public void setDcBalanceStop(long dcBalanceStop) {
        this.dcBalanceStop = dcBalanceStop;
    }

    public double getDcPrice() {
        return dcPrice;
    }

    public void setDcPrice(double dcPrice) {
        this.dcPrice = dcPrice;
    }

    public long getDcMin() {
        return dcMin;
    }

    public void setDcMin(long dcMin) {
        this.dcMin = dcMin;
    }
}
