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
import io.chirpstack.json.sub.UplinkEventRxInfo;
import io.chirpstack.json.sub.UplinkEventTxInfo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UplinkEvent {

    /*
    {
        "deduplicationId":"5ca8c259-2751-4307-8c06-671e931c6a47",
        "time":"2022-11-14T11:37:56+00:00",
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
        "devAddr":"fc014c36",
        "dr":4,
        "fCnt":37613,
        "fPort":2,
        "data":"AQIDBA==",
        "rxInfo":[
                {
                    "gatewayId":"3c408850a5b4f27c",
                    "uplinkId":29174,
                    "time":"2022-11-14T11:37:56+00:00",
                    "rssi":-46,
                    "snr":15.2,
                    "context":"O7eu8g==",
                    "metadata":{
                        "region_name":"eu868",
                        "region_common_name":"EU868"
                    }
                }
        ],
        "txInfo":{
            "frequency":867700000,
            "modulation":{
            "lora":{
                "bandwidth":125000,
                "spreadingFactor":8,
                "codeRate":"CR_4_5"
                }
            }
        }
      }
     */

    // @ApiModelProperty(notes = "message uniq identifier ", required = false)
    protected String deduplicationId;

    // @ApiModelProperty(notes = "time ", required = false)
    protected String time;

    // @ApiModelProperty(notes = "device address", required = false)
    protected String devAddr;

    // @ApiModelProperty(notes = "data rate", required = false)
    protected int dr;

    // @ApiModelProperty(notes = "frame counter ", required = false)
    protected int fCnt;

    // @ApiModelProperty(notes = "frame port ", required = false)
    protected int fPort;

    // @ApiModelProperty(notes = "data string base 64", required = false)
    protected String data;

    // @ApiModelProperty(notes = "device & context detailed information", required = false)
    protected UplinkEventDeviceInfo deviceInfo;

    // @ApiModelProperty(notes = "Information about receivers", required = false)
    protected List<UplinkEventRxInfo> rxInfo;

    // @ApiModelProperty(notes = "RF information", required = false)
    protected UplinkEventTxInfo txInfo;

    // ---


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

    public int getDr() {
        return dr;
    }

    public void setDr(int dr) {
        this.dr = dr;
    }

    public int getfCnt() {
        return fCnt;
    }

    public void setfCnt(int fCnt) {
        this.fCnt = fCnt;
    }

    public int getfPort() {
        return fPort;
    }

    public void setfPort(int fPort) {
        this.fPort = fPort;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public UplinkEventDeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(UplinkEventDeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public List<UplinkEventRxInfo> getRxInfo() {
        return rxInfo;
    }

    public void setRxInfo(List<UplinkEventRxInfo> rxInfo) {
        this.rxInfo = rxInfo;
    }

    public UplinkEventTxInfo getTxInfo() {
        return txInfo;
    }

    public void setTxInfo(UplinkEventTxInfo txInfo) {
        this.txInfo = txInfo;
    }
}
