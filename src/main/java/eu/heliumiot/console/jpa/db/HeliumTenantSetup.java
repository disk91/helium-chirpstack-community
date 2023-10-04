/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2020.
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
package eu.heliumiot.console.jpa.db;

import fr.ingeniousthings.tools.ClonnableObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "helium_tenantSetup",
        indexes = {
            @Index(name="bytenantuuidsearch", columnList = "tenantUUID")
        }
)
public class HeliumTenantSetup implements ClonnableObject<HeliumTenantSetup> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // tenant UUID
    private String tenantUUID;

    // Minimum accepted value for DC balance for this tenant
    private long dcBalanceStop;

    // Free DCs on new Tenant Creation
    private long freeTenantDc;

    // DCs paid per 24B messages for this tenant
    private int dcPer24BMessage;

    // DCs paid per 24B downlink
    private int dcPer24BDownlink;

    // DCs paid per 24B duplicated messages
    private int dcPer24BDuplicate;

    // DCs paid per device inserted
    private int dcPerDeviceInserted;

    // Tenant device inactivity billing period in ms
    private long inactivityBillingPeriodMs;
    // DCs paid per inactivity period
    private int dcPerInactivityPeriod;

    // ---
    // Tenant device activity billing period in ms
    private long activityBillingPeriodMs;
    // DCs paid per activity period
    private int dcPerActivityPeriod;

    // ---
    // Tenant max DCs per device before deactivation
    private int maxDcPerDevice;

    // ---
    // Tenant max DCs per device per period before dactivation
    private int limitDcRatePerDevice;
    private long limitDcRatePeriodMs;

    // ---
    // cost in $ for a single DC
    private double dcPrice;

    // minimum qty of DCs you can buy in a single order
    private long dcMin;

    // max tenant owened per user
    private int maxOwnedTenants;

    // max device per tenant
    private int maxDevices;

    private boolean signupAllowed;

    // true when this is a template
    private boolean template = false;

    @Column(name = "max_copy", columnDefinition = "int4 default 3")
    private int maxCopy = 3;

    @Column(name = "route_id")
    private String routeId;

    // Join Req / Acc invoicing
    @Column(name = "dc_per_join_request", columnDefinition = "int4")
    private int dcPerJoinRequest;
    @Column(name = "max_join_request_dup", columnDefinition = "int4")
    private int maxJoinRequestDup;
    @Column(name = "dc_per_join_accept", columnDefinition = "int4")
    private int dcPerJoinAccept;

    // ---
    public HeliumTenantSetup clone() {
        System.out.println("### HeliumTenantSetup clone not implemented");
        return null;
    }


    // ---

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getDcBalanceStop() {
        return dcBalanceStop;
    }

    public void setDcBalanceStop(long dcBalanceStop) {
        this.dcBalanceStop = dcBalanceStop;
    }

    public int getDcPer24BMessage() {
        return dcPer24BMessage;
    }

    public void setDcPer24BMessage(int dcPer24BMessage) {
        this.dcPer24BMessage = dcPer24BMessage;
    }

    public int getDcPerDeviceInserted() {
        return dcPerDeviceInserted;
    }

    public void setDcPerDeviceInserted(int dcPerDeviceInserted) {
        this.dcPerDeviceInserted = dcPerDeviceInserted;
    }

    public int getDcPerInactivityPeriod() {
        return dcPerInactivityPeriod;
    }

    public void setDcPerInactivityPeriod(int dcPerInactivityPeriod) {
        this.dcPerInactivityPeriod = dcPerInactivityPeriod;
    }

    public int getDcPer24BDuplicate() {
        return dcPer24BDuplicate;
    }

    public void setDcPer24BDuplicate(int dcPer24BDuplicate) {
        this.dcPer24BDuplicate = dcPer24BDuplicate;
    }

    public String getTenantUUID() {
        return tenantUUID;
    }

    public void setTenantUUID(String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }

    public int getDcPer24BDownlink() {
        return dcPer24BDownlink;
    }

    public void setDcPer24BDownlink(int dcPer24BDownlink) {
        this.dcPer24BDownlink = dcPer24BDownlink;
    }

    public long getInactivityBillingPeriodMs() {
        return inactivityBillingPeriodMs;
    }

    public void setInactivityBillingPeriodMs(long inactivityBillingPeriodMs) {
        this.inactivityBillingPeriodMs = inactivityBillingPeriodMs;
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

    public long getFreeTenantDc() {
        return freeTenantDc;
    }

    public void setFreeTenantDc(long freeTenantDc) {
        this.freeTenantDc = freeTenantDc;
    }

    public int getMaxDcPerDevice() {
        return maxDcPerDevice;
    }

    public void setMaxDcPerDevice(int maxDcPerDevice) {
        this.maxDcPerDevice = maxDcPerDevice;
    }

    public int getLimitDcRatePerDevice() {
        return limitDcRatePerDevice;
    }

    public void setLimitDcRatePerDevice(int limitDcRatePerDevice) {
        this.limitDcRatePerDevice = limitDcRatePerDevice;
    }

    public long getLimitDcRatePeriodMs() {
        return limitDcRatePeriodMs;
    }

    public void setLimitDcRatePeriodMs(long limitDcRatePeriodMs) {
        this.limitDcRatePeriodMs = limitDcRatePeriodMs;
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

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public int getMaxCopy() {
        return maxCopy;
    }

    public void setMaxCopy(int maxCopy) {
        this.maxCopy = maxCopy;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
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
