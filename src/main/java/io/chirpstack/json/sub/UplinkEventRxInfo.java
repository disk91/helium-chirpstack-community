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
public class UplinkEventRxInfo {

    /*
                {
                    "gatewayId":"3c408850a5b4f27c",
                    "uplinkId":29174,
                    // prior to 4.6
                    "time":"2023-05-29T19:50:10+00:00",
                    // from 4.6
                    "gwTime":"2024-01-07T11:05:31+00:00",
                    "nsTime":"2024-01-07T11:05:31.577525935+00:00
                    "rssi":-46,
                    "snr":15.2,
                    "context":"O7eu8g==",
                    "metadata":{
                        "region_common_name":"EU868",
                        "region_config_id":"eu868",
                        "gateway_h3index" : "61105...",
                        "gateway_lat" : "45.80...",
                        "gateway_long" : "3.09...",
                        "gateway_name" : "mythical-xxx..."
                    }
                }
     */

    protected String gatewayId;
    protected long uplinkId;
    private String gwTime;
    private String nsTime;
    private String time;    // retro compat
    protected int rssi;
    protected double snr;
    private String context;
    protected UplinkEventRxInfoMetadata metadata;
    private String crcStatus;

    // ---

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public long getUplinkId() {
        return uplinkId;
    }

    public void setUplinkId(long uplinkId) {
        this.uplinkId = uplinkId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public double getSnr() {
        return snr;
    }

    public void setSnr(double snr) {
        this.snr = snr;
    }

    public UplinkEventRxInfoMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(UplinkEventRxInfoMetadata metadata) {
        this.metadata = metadata;
    }

    public String getGwTime() {
        return gwTime;
    }

    public void setGwTime(String gwTime) {
        this.gwTime = gwTime;
    }

    public String getNsTime() {
        return nsTime;
    }

    public void setNsTime(String nsTime) {
        this.nsTime = nsTime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCrcStatus() {
        return crcStatus;
    }

    public void setCrcStatus(String crcStatus) {
        this.crcStatus = crcStatus;
    }
}
