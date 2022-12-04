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
package io.chirpstack.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.chirpstack.json.sub.UplinkEventDeviceInfo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JoinEvent {

    /*
    {
       "deduplicationId":"45301df2-adf9-460b-9405-cd356714df6e",
       "time":"2022-11-15T21:55:03+00:00",
       "deviceInfo":{
          "tenantId":"52f14cd4-c6f1-4fbd-8f87-4025e1d49242",
          "tenantName":"ChirpStack",
          "applicationId":"997b3f5d-6f2d-4b51-b4eb-b50009055bf3",
          "applicationName":"test application",
          "deviceProfileId":"8c4deef2-54d5-4b0f-8c6e-a360c6e56ef7",
          "deviceProfileName":"Default profile",
          "deviceName":"test device",
          "devEui":"4cf9093a608cecdb"
       },
       "devAddr":"fc014c58"
     }
     */

    // @ApiModelProperty(notes = "message uniq identifier ", required = false)
    protected String deduplicationId;

    // @ApiModelProperty(notes = "time ", required = false)
    protected String time;

    // @ApiModelProperty(notes = "device address", required = false)
    protected String devAddr;

    // @ApiModelProperty(notes = "device & context detailed information", required = false)
    protected UplinkEventDeviceInfo deviceInfo;

    public String getDeduplicationId() {
        return deduplicationId;
    }

    public void setDeduplicationId(String deduplicationId) {
        this.deduplicationId = deduplicationId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDevAddr() {
        return devAddr;
    }

    public void setDevAddr(String devAddr) {
        this.devAddr = devAddr;
    }

    public UplinkEventDeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(UplinkEventDeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
