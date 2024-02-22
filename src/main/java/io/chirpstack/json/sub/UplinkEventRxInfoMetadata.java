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
package io.chirpstack.json.sub;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UplinkEventRxInfoMetadata {

    /*
                "metadata":{
                    gateway_id:"11o8R9inbpcanSdVpdQxx5G2DGtVs9UqaxxNSYMeqLG6LYf13XA"
                    gateway_name:"mythical-misty-finch"
                    "region_common_name":"EU868",
                    "region_config_id":"eu868",
                    "gateway_h3index" : "61105...",
                    "gateway_lat" : "45.80...",
                    "gateway_long" : "3.09...",
                }
            }
     */

    private String gateway_id;
    private String gateway_name;
    private String region_common_name;
    private String region_config_id;
    private String gateway_lat;
    private String gateway_long;
    private String gateway_h3index;

    // ---


    public String getGateway_id() {
        return gateway_id;
    }

    public void setGateway_id(String gateway_id) {
        this.gateway_id = gateway_id;
    }

    public String getGateway_name() {
        return gateway_name;
    }

    public void setGateway_name(String gateway_name) {
        this.gateway_name = gateway_name;
    }

    public String getRegion_common_name() {
        return region_common_name;
    }

    public void setRegion_common_name(String region_common_name) {
        this.region_common_name = region_common_name;
    }

    public String getRegion_config_id() {
        return region_config_id;
    }

    public void setRegion_config_id(String region_config_id) {
        this.region_config_id = region_config_id;
    }

    public String getGateway_lat() {
        return gateway_lat;
    }

    public void setGateway_lat(String gateway_lat) {
        this.gateway_lat = gateway_lat;
    }

    public String getGateway_long() {
        return gateway_long;
    }

    public void setGateway_long(String gateway_long) {
        this.gateway_long = gateway_long;
    }

    public String getGateway_h3index() {
        return gateway_h3index;
    }

    public void setGateway_h3index(String gateway_h3index) {
        this.gateway_h3index = gateway_h3index;
    }
}
