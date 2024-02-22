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

package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;

public class TicketDetailResponseItf {

    @Schema(
            description = "response content",
            example = "bla bla bla",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String content;

    @Schema(
            description = "response creation date",
            example = "1342542",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long createdAt;

    @Schema(
            description = "response comes from admin/user",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected boolean isAdminReponse;

    //

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAdminReponse() {
        return isAdminReponse;
    }

    public void setAdminReponse(boolean adminReponse) {
        isAdminReponse = adminReponse;
    }
}
