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

@Tag(name = "User DC Transaction List", description = "List the transaction made by the user for transferring and acquiring DCs")
public class TransactionListRespItf {


    @Schema(
            description = "Transaction UUID",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String transactionUUID;


    @Schema(
            description = "Quantity of DCs really transferred",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long dcs;

    @Schema(
            description = "Type of the transaction 0 - transfer / 1 - stripe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected int type;

    @Schema(
            description = "Transaction time",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long createAt;

    @Schema(
            description = "Target tenant name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String tenantName;

    @Schema(
            description = "Cost in $ - for type 1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected double cost;

    @Schema(
            description = "Transaction status",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String status;




    // ------------------------------------


    public String getTransactionUUID() {
        return transactionUUID;
    }

    public void setTransactionUUID(String transactionUUID) {
        this.transactionUUID = transactionUUID;
    }

    public long getDcs() {
        return dcs;
    }

    public void setDcs(long dcs) {
        this.dcs = dcs;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
