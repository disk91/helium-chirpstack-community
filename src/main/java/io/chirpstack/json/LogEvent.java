/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2024.
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
import io.chirpstack.json.sub.LogEventContext;
import io.chirpstack.json.sub.UplinkEventDeviceInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogEvent {
        /*
        {
            "time": "2024-02-16T21:11:29+00:00",
            "deviceInfo": {
                "tenantId": "7315....bd08",
                "tenantName": "xxxx",
                "applicationId": "fb02...fa5",
                "applicationName": "xxxx",
                "deviceProfileId": "945...7",
                "deviceProfileName": "us915",
                "deviceName": "xxxx",
                "devEui": "7...c",
                "deviceClassEnabled": "CLASS_A",
                "tags": {}
            },
            "level": "WARNING",
            "code": "UPLINK_F_CNT_RESET",
            "description": "Frame-counter reset or rollover detected",
            "context": {
                "deduplication_id": "dceb5eb8-...-f04834eea493"
            }
        }
         */

    protected String time;
    protected String level;
    protected String code;
    protected String description;
    protected UplinkEventDeviceInfo deviceInfo;
    protected LogEventContext context;

    // ---


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UplinkEventDeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(UplinkEventDeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public LogEventContext getContext() {
        return context;
    }

    public void setContext(LogEventContext context) {
        this.context = context;
    }
}
