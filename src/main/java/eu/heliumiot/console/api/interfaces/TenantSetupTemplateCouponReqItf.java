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

@Tag(name = "Tenant Setup Template Coupon Request", description = "Create a coupon to use a tenantSetup")
public class TenantSetupTemplateCouponReqItf {

    @Schema(
            description = "prefix to be added on coupon name xxx-yyyy...",
            example = "xxx",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String prefix;

    @Schema(
            description = "name of the tenant template",
            example = "default",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tenantUUID;

    @Schema(
            description = "Number of time this coupon can be use (0 for unlimited)",
            example = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int maxUse;

    @Schema(
            description = "start valid period time (0) for no limit",
            example = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long start;

    @Schema(
            description = "stop valid period time (0) for no limit",
            example = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long stop;


    @Schema(
            description = "number of coupon to be created",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int toCreate;

    @Schema(
        description = "Who's coupon for - login",
        example = "john.doe@foo.bar",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String couponFor;

    // ---


    public String getTenantUUID() {
        return tenantUUID;
    }

    public void setTenantUUID(String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }

    public int getMaxUse() {
        return maxUse;
    }

    public void setMaxUse(int maxUse) {
        this.maxUse = maxUse;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getStop() {
        return stop;
    }

    public void setStop(long stop) {
        this.stop = stop;
    }

    public int getToCreate() {
        return toCreate;
    }

    public void setToCreate(int toCreate) {
        this.toCreate = toCreate;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getCouponFor() {
        return couponFor;
    }

    public void setCouponFor(String couponFor) {
        this.couponFor = couponFor;
    }
}
