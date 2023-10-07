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

import fr.ingeniousthings.tools.ClonnableObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tenant configuration & usage statistics", description = "Information about tenant configuration and usage statistics")
public class TenantBasicStatRespItf implements ClonnableObject<TenantBasicStatRespItf>  {


    @Schema(
            description = "Id of the tenant template",
            example = "default",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tenantUUID;

    @Schema(
            description = "name of the tenant template",
            example = "default",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tenantName;

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
            description = "Day corresponding to start of given statistics, time is UTC",
            example = "1671308118",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long day;

    @Schema(
            description = "Duration of the stat windows, data are agregated from the start date to the duration date",
            example = "360000000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long duration;

    @Schema(
            description = "Quantity of DCs consumed for device registration",
            example = "1671308118",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int registrationDc; // device creation costs

    @Schema(
            description = "Number of uplink",
            example = "1000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int uplink;

    @Schema(
            description = "Number of join Request",
            example = "1000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int joinReq;


    @Schema(
            description = "Quantity of DCs consumed for uplink communications",
            example = "1000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int uplinkDc;

    @Schema(
            description = "Quantity of duplicates",
            example = "1000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int duplicate;

    @Schema(
            description = "Quantity of DCs consumed for uplink duplicates",
            example = "1000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int duplicateDc;

    @Schema(
            description = "Number of downlink messages proceeded",
            example = "1000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int downlink;

    @Schema(
            description = "Quantity of DCs consumed for downlink",
            example = "1000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int downlinkDc;

    @Schema(
            description = "Quantity of DCs consumed for device inactivity",
            example = "1000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int inactivityDc;

    @Schema(
            description = "Quantity of DCs consumed for device activity",
            example = "1000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int activityDc;

    @Schema(
            description = "Maximum number of duplicates purchased by router",
            example = "15",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int maxCopy;

    @Schema(
        description = "Number of Dc to be paid for a Join Request",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int dcPerJoinRequest;

    @Schema(
        description = "Maximum Join Request duplicates invoiced at dcPerJoinRequest, -1 all",
        example = "-1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int maxJoinRequestDup;

    @Schema(
        description = "Number of Dc consumed for Join Request, including duplicates",
        example = "100",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int joinDc;

    @Schema(
        description = "Cost in DCs to send a Join Accept as a response to Join request",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int dcPerJoinAccept;

    @Schema(
        description = "Number of DCs consumed for Join Accept",
        example = "10",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int joinAcceptDc;


    // ----

    public TenantBasicStatRespItf clone() {
        System.out.println("### TenantBasicStatRespItf clone not implemented");
        return null;
    }



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

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getRegistrationDc() {
        return registrationDc;
    }

    public void setRegistrationDc(int registrationDc) {
        this.registrationDc = registrationDc;
    }

    public int getUplink() {
        return uplink;
    }

    public void setUplink(int uplink) {
        this.uplink = uplink;
    }

    public int getJoinReq() {
        return joinReq;
    }

    public void setJoinReq(int joinReq) {
        this.joinReq = joinReq;
    }

    public int getUplinkDc() {
        return uplinkDc;
    }

    public void setUplinkDc(int uplinkDc) {
        this.uplinkDc = uplinkDc;
    }

    public int getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(int duplicate) {
        this.duplicate = duplicate;
    }

    public int getDuplicateDc() {
        return duplicateDc;
    }

    public void setDuplicateDc(int duplicateDc) {
        this.duplicateDc = duplicateDc;
    }

    public int getDownlink() {
        return downlink;
    }

    public void setDownlink(int downlink) {
        this.downlink = downlink;
    }

    public int getDownlinkDc() {
        return downlinkDc;
    }

    public void setDownlinkDc(int downlinkDc) {
        this.downlinkDc = downlinkDc;
    }

    public int getInactivityDc() {
        return inactivityDc;
    }

    public void setInactivityDc(int inactivityDc) {
        this.inactivityDc = inactivityDc;
    }

    public int getActivityDc() {
        return activityDc;
    }

    public void setActivityDc(int activityDc) {
        this.activityDc = activityDc;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
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

    public int getJoinDc() {
        return joinDc;
    }

    public void setJoinDc(int joinDc) {
        this.joinDc = joinDc;
    }

    public int getDcPerJoinAccept() {
        return dcPerJoinAccept;
    }

    public void setDcPerJoinAccept(int dcPerJoinAccept) {
        this.dcPerJoinAccept = dcPerJoinAccept;
    }

    public int getJoinAcceptDc() {
        return joinAcceptDc;
    }

    public void setJoinAcceptDc(int joinAcceptDc) {
        this.joinAcceptDc = joinAcceptDc;
    }
}
