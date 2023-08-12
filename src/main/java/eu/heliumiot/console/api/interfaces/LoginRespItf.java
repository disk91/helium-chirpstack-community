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

@Tag(name = "Login Interface", description = "login response interface")
public class LoginRespItf {

    @Schema(
            description = "Chirpstack bearer",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String chirpstackBearer;

    @Schema(
            description = "Console bearer",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String consoleBearer;

    @Schema(
            description = "True when the user is platform admin",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected boolean admin;

    @Schema(
        description = "True when user condition changed",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected boolean userConditionChanged;


    // ------------------------------------


    public String getChirpstackBearer() {
        return chirpstackBearer;
    }

    public void setChirpstackBearer(String chirpstackBearer) {
        this.chirpstackBearer = chirpstackBearer;
    }

    public String getConsoleBearer() {
        return consoleBearer;
    }

    public void setConsoleBearer(String consoleBearer) {
        this.consoleBearer = consoleBearer;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isUserConditionChanged() {
        return userConditionChanged;
    }

    public void setUserConditionChanged(boolean userConditionChanged) {
        this.userConditionChanged = userConditionChanged;
    }
}
