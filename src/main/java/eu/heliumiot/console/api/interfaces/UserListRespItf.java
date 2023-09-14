/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2018.
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

package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "User list response Interface", description = "list of user for admin task")
public class UserListRespItf {


    @Schema(
            description = "User Login - email",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String userLogin;

    @Schema(
        description = "true when user is disabled",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected boolean disable;

    @Schema(
        description = "Registration date",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long registration;

    @Schema(
        description = "Last Login date",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long lastLogin;


    @Schema(
        description = "Tenant Names",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected List<TenantEntry> tenants;



    // ------------------------------------


    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public long getRegistration() {
        return registration;
    }

    public void setRegistration(long registration) {
        this.registration = registration;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<TenantEntry> getTenants() {
        return tenants;
    }

    public void setTenants(List<TenantEntry> tenants) {
        this.tenants = tenants;
    }
}
