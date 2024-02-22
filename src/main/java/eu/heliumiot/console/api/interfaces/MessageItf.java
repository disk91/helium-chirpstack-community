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
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tenant balance Interface", description = "tenant balance interface")
public class MessageItf {

    @Schema(
            description = "Internal uniq ID of the message",
            example = "123456-1234-123456-1231456-1234",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String id;

    @Schema(
            description = "Index of the message, corresponding to UTC time at creation",
            example = "1723456789000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long index;

    @Schema(
            description = "type of message display 1 for modal (full screen) 2 for toaster (banner)",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int type;

    @Schema(
            description = "category of message for display 0 for info, 1 for warning, 2 or danger",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int category;

    @Schema(
            description = "message content to be printed",
            example = "Welcome message !",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String content;


    @Schema(
            description = "message expiration date, when different than 0, the message is displayed until this UTC EPOC in ms date",
            example = "1752132456000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long until;

    @Schema(
            description = "The message is displayed only one time to user when until is set, or it will be repeated until the date",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean onlyone;


    // ---


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getUntil() {
        return until;
    }

    public void setUntil(long until) {
        this.until = until;
    }

    public boolean isOnlyone() {
        return onlyone;
    }

    public void setOnlyone(boolean onlyone) {
        this.onlyone = onlyone;
    }
}
