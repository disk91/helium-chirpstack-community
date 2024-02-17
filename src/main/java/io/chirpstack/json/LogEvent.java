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
