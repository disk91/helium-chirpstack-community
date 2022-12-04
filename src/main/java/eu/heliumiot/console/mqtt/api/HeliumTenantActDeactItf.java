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

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeliumTenantActDeactItf {

    // @ApiModelProperty(notes = "tenantId", required = false)
    protected String tenantId;

    // @ApiModelProperty(notes = "time of the request", required = false)
    protected long time = 0;

    // @ApiModelProperty(notes = "true to request tenant's device deactivation", required = false)
    protected boolean deactivateTenant;

    // @ApiModelProperty(notes = "true to request tenant's device activation", required = false)
    protected boolean activateTenant;

    // ---


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isDeactivateTenant() {
        return deactivateTenant;
    }

    public void setDeactivateTenant(boolean deactivateTenant) {
        this.deactivateTenant = deactivateTenant;
    }

    public boolean isActivateTenant() {
        return activateTenant;
    }

    public void setActivateTenant(boolean activateTenant) {
        this.activateTenant = activateTenant;
    }
}
