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
                    "time":"2022-11-14T11:37:56+00:00",
                    "rssi":-46,
                    "snr":15.2,
                    "context":"O7eu8g==",
                    "metadata":{
                        "region_name":"eu868",
                        "region_common_name":"EU868"
                    }
                }
     */

    // @ApiModelProperty(notes = "Id of the gateway", required = false)
    protected String gatewayId;

    // @ApiModelProperty(notes = "uplinkId ", required = false)
    protected long uplinkId;

    // @ApiModelProperty(notes = "time of the reception", required = false)
    protected String time;

    // @ApiModelProperty(notes = "Rssi", required = false)
    protected int rssi;

    // @ApiModelProperty(notes = "Snr", required = false)
    protected double snr;

    //@ApiModelProperty(notes = "Associated metadat", required = false)
    protected UplinkEventRxInfoMetadata metadata;

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
}
