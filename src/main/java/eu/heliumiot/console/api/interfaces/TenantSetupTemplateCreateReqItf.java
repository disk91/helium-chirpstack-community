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

import java.util.UUID;

@Tag(name = "Tenant update request Interface", description = "existing tenant update request interface")
public class TenantSetupTemplateCreateReqItf {


    @Schema(
            description = "name of the tenant template",
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
            description = "Number of free DCs given to consumer on tenant creation",
            example = "1000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long freeTenantDc;

    @Schema(
            description = "Number of DCs paid for Uplink communication per 24 bytes of data",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int dcPer24BMessage;

    @Schema(
            description = "Number of DCs paid for Downlink communication per 24 bytes of data",
            example = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int dcPer24BDownlink;

    @Schema(
            description = "Number of DCs paid for Uplink replicates per 24 bytes of data",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int dcPer24BDuplicate;

    @Schema(
            description = "Number of DCs paid for adding a device on the platform",
            example = "100",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int dcPerDeviceInserted;

    @Schema(
            description = "Duration in Ms of an inactivity calculation period",
            example = "3600000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long inactivityBillingPeriodMs;

    @Schema(
            description = "Cost paid per inactivity period when a device does not emit",
            example = "10",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int dcPerInactivityPeriod;

    @Schema(
            description = "Duration in Ms of an activity calculation period",
            example = "3600000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long activityBillingPeriodMs;

    @Schema(
            description = "Cost paid per activity period when a device has emition",
            example = "10",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int dcPerActivityPeriod;

    @Schema(
            description = "Amount of DC a device is authorized to consume before being automatically deactivated",
            example = "100000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int maxDcPerDevice;

    @Schema(
            description = "DCs consumption rate limitation calculation period in ms",
            example = "3600000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long limitDcRatePeriodMs;

    @Schema(
            description = "DCs consumption authorized during the limitation calculation period",
            example = "10000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int limitDcRatePerDevice;

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

    @Schema(
            description = "Maximum number of Tenant a user can owned (he can be invited to much more)",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int maxOwnedTenants;

    @Schema(
            description = "Maximum number of device a user can declare in a tenant he owns, 0 for unlimited",
            example = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int maxDevices;

    @Schema(
            description = "User can signup and request for this template",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean signupAllowed;

    @Schema(
            description = "Max number of Frames purchased by router from different hotspot ",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int maxCopy;


    @Schema(
        description = "Number of DCs invoiced for a Join request and each of the duplicates up to the limit",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int dcPerJoinRequest;

    @Schema(
        description = "Maximum join request duplicates invoiced -1 for all. This is in preparation of future unlimited Join Req",
        example = "0",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int maxJoinRequestDup;

    @Schema(
        description = "Number of DCs invoices for a Join Accept - each join req group have a join accept",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int dcPerJoinAccept;

    // ---


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

    public long getFreeTenantDc() {
        return freeTenantDc;
    }

    public void setFreeTenantDc(long freeTenantDc) {
        this.freeTenantDc = freeTenantDc;
    }

    public int getDcPer24BMessage() {
        return dcPer24BMessage;
    }

    public void setDcPer24BMessage(int dcPer24BMessage) {
        this.dcPer24BMessage = dcPer24BMessage;
    }

    public int getDcPer24BDownlink() {
        return dcPer24BDownlink;
    }

    public void setDcPer24BDownlink(int dcPer24BDownlink) {
        this.dcPer24BDownlink = dcPer24BDownlink;
    }

    public int getDcPer24BDuplicate() {
        return dcPer24BDuplicate;
    }

    public void setDcPer24BDuplicate(int dcPer24BDuplicate) {
        this.dcPer24BDuplicate = dcPer24BDuplicate;
    }

    public int getDcPerDeviceInserted() {
        return dcPerDeviceInserted;
    }

    public void setDcPerDeviceInserted(int dcPerDeviceInserted) {
        this.dcPerDeviceInserted = dcPerDeviceInserted;
    }

    public long getInactivityBillingPeriodMs() {
        return inactivityBillingPeriodMs;
    }

    public void setInactivityBillingPeriodMs(long inactivityBillingPeriodMs) {
        this.inactivityBillingPeriodMs = inactivityBillingPeriodMs;
    }

    public int getDcPerInactivityPeriod() {
        return dcPerInactivityPeriod;
    }

    public void setDcPerInactivityPeriod(int dcPerInactivityPeriod) {
        this.dcPerInactivityPeriod = dcPerInactivityPeriod;
    }

    public long getActivityBillingPeriodMs() {
        return activityBillingPeriodMs;
    }

    public void setActivityBillingPeriodMs(long activityBillingPeriodMs) {
        this.activityBillingPeriodMs = activityBillingPeriodMs;
    }

    public int getDcPerActivityPeriod() {
        return dcPerActivityPeriod;
    }

    public void setDcPerActivityPeriod(int dcPerActivityPeriod) {
        this.dcPerActivityPeriod = dcPerActivityPeriod;
    }

    public int getMaxDcPerDevice() {
        return maxDcPerDevice;
    }

    public void setMaxDcPerDevice(int maxDcPerDevice) {
        this.maxDcPerDevice = maxDcPerDevice;
    }

    public long getLimitDcRatePeriodMs() {
        return limitDcRatePeriodMs;
    }

    public void setLimitDcRatePeriodMs(long limitDcRatePeriodMs) {
        this.limitDcRatePeriodMs = limitDcRatePeriodMs;
    }

    public int getLimitDcRatePerDevice() {
        return limitDcRatePerDevice;
    }

    public void setLimitDcRatePerDevice(int limitDcRatePerDevice) {
        this.limitDcRatePerDevice = limitDcRatePerDevice;
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

    public int getMaxOwnedTenants() {
        return maxOwnedTenants;
    }

    public void setMaxOwnedTenants(int maxOwnedTenants) {
        this.maxOwnedTenants = maxOwnedTenants;
    }

    public int getMaxDevices() {
        return maxDevices;
    }

    public void setMaxDevices(int maxDevices) {
        this.maxDevices = maxDevices;
    }

    public boolean isSignupAllowed() {
        return signupAllowed;
    }

    public void setSignupAllowed(boolean signupAllowed) {
        this.signupAllowed = signupAllowed;
    }

    public int getMaxCopy() {
        return maxCopy;
    }

    public void setMaxCopy(int maxCopy) {
        this.maxCopy = maxCopy;
    }

    public int getDcPerJoinRequest() {
        return dcPerJoinRequest;
    }

    public void setDcPerJoinRequest(int dcPerJoinRequest) {
        this.dcPerJoinRequest = dcPerJoinRequest;
    }

    public int getMaxJoinRequestDup() {
        return maxJoinRequestDup;
    }

    public void setMaxJoinRequestDup(int maxJoinRequestDup) {
        this.maxJoinRequestDup = maxJoinRequestDup;
    }

    public int getDcPerJoinAccept() {
        return dcPerJoinAccept;
    }

    public void setDcPerJoinAccept(int dcPerJoinAccept) {
        this.dcPerJoinAccept = dcPerJoinAccept;
    }
}
