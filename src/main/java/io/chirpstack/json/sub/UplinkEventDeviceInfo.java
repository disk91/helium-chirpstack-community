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
package io.chirpstack.json.sub;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UplinkEventDeviceInfo {

    /*
        "deviceInfo":{
            "tenantId":"52f14cd4-c6f1-4fbd-8f87-4025e1d49242",
            "tenantName":"ChirpStack",
            "applicationId":"997b3f5d-6f2d-4b51-b4eb-b50009055bf3",
            "applicationName":"test application",
            "deviceProfileId":"5f9e8dd6-0f37-4d74-8e9b-4fbefeb22131",
            "deviceProfileName":"ABP Profile",
            "deviceName":"test 2 mkr1300",
            "devEui":"d0a98e24b24d93dc"
        },
     */

    // @ApiModelProperty(notes = "tenant ID", required = false)
    protected String tenantId;

    //@ApiModelProperty(notes = "tenant name ", required = false)
    protected String tenantName;

    // @ApiModelProperty(notes = "application ID", required = false)
    protected String applicationId;

    // @ApiModelProperty(notes = "application Name", required = false)
    protected String applicationName;

    // @ApiModelProperty(notes = "device profile ID", required = false)
    protected String deviceProfileId;

    // @ApiModelProperty(notes = "device profile name", required = false)
    protected String deviceProfileName;

    // @ApiModelProperty(notes = "device given name", required = false)
    protected String deviceName;

    // @ApiModelProperty(notes = "device EUI", required = false)
    protected String devEui;

    //  ---


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getDeviceProfileId() {
        return deviceProfileId;
    }

    public void setDeviceProfileId(String deviceProfileId) {
        this.deviceProfileId = deviceProfileId;
    }

    public String getDeviceProfileName() {
        return deviceProfileName;
    }

    public void setDeviceProfileName(String deviceProfileName) {
        this.deviceProfileName = deviceProfileName;
    }

    public String getDevEui() {
        return devEui;
    }

    public void setDevEui(String devEui) {
        this.devEui = devEui;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
