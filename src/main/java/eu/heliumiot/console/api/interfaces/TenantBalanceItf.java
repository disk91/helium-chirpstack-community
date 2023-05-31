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

@Tag(name = "Tenant balance Interface", description = "tenant balance interface")
public class TenantBalanceItf {

    @Schema(
            description = "Associated Tenant Name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String tenantName;


    @Schema(
            description = "Current DC balance",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long dcBalance;

    @Schema(
            description = "Minimum balance accepted",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long minBalance;

    @Schema(
            description = "When true, the balance margin is > 1000DCs",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected boolean balanceOk;

    @Schema(
            description = "When false, value forced to 0 due to ownership",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected boolean ownerMode;


    // ------------------------------------


    public long getDcBalance() {
        return dcBalance;
    }

    public void setDcBalance(long dcBalance) {
        this.dcBalance = dcBalance;
    }

    public long getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(long minBalance) {
        this.minBalance = minBalance;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public boolean isBalanceOk() {
        return balanceOk;
    }

    public void setBalanceOk(boolean balanceOk) {
        this.balanceOk = balanceOk;
    }

    public boolean isOwnerMode() {
        return ownerMode;
    }

    public void setOwnerMode(boolean ownerMode) {
        this.ownerMode = ownerMode;
    }
}
