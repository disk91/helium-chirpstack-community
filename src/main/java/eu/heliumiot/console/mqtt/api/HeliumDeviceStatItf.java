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
package eu.heliumiot.console.mqtt.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeliumDeviceStatItf {

    @ApiModelProperty(notes = "deviceId", required = false)
    protected String deviceId;

    @ApiModelProperty(notes = "tenantId", required = false)
    protected String tenantId;

    @ApiModelProperty(notes = "time of the update", required = false)
    protected long time = 0;
    protected int uplinkDc = 0;
    protected int duplicateDc = 0;
    protected int downlinkDc = 0;
    protected int inactivityDc = 0;
    protected int activityDc = 0;
    protected int registrationDc = 0;
    protected int uplink = 0;
    protected int downlink = 0;
    protected int join=0;

    protected boolean empty = true;

    // ---

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUplinkDc() {
        return uplinkDc;
    }

    public void setUplinkDc(int uplinkDc) {
        this.uplinkDc = uplinkDc;
    }

    public int getDuplicateDc() {
        return duplicateDc;
    }

    public void setDuplicateDc(int duplicateDc) {
        this.duplicateDc = duplicateDc;
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

    public int getDownlink() {
        return downlink;
    }

    public void setDownlink(int downlink) {
        this.downlink = downlink;
    }

    public int getJoin() {
        return join;
    }

    public void setJoin(int join) {
        this.join = join;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
