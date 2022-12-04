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

@Tag(name = "Action Result", description = "common format for better front-end integration")
public class ActionResult {

    public static enum ACTION_RESULT  {
        SUCCESS,           // action complete with sucess
        FAILED,            // action failed
        NODATA,            // no Data
        NOTFOUND,          // not found
        FORBIDDEN,         // forbidden
        MALFORMED,         // maformed
        BADREQUEST,        // bad request
        UNKNOWDN           // Unknown
    }

    @Schema(
            description = "Result of a given action",
            example = "SUCCESS",
            allowableValues = "SUCCESS, FAILED, NODATA, NOTFOUND, FORBIDDEN, MALFORMED, BADREQUEST, UNKNOWN",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected ACTION_RESULT status;

    @Schema(
            description = "Associated custom message",
            example = "Invalid request, missing field xxx",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String message = "";


    // ------------------------------------

    public static ActionResult SUCESS(){
        ActionResult r = new ActionResult();
        r.setStatus(ACTION_RESULT.SUCCESS);
        return r;
    }

    public static ActionResult FAILED(){
        ActionResult r = new ActionResult();
        r.setStatus(ACTION_RESULT.FAILED);
        return r;
    }

    public static ActionResult NODATA(){
        ActionResult r = new ActionResult();
        r.setStatus(ACTION_RESULT.NODATA);
        return r;
    }

    public static ActionResult NOTFOUND(){
        ActionResult r = new ActionResult();
        r.setStatus(ACTION_RESULT.NOTFOUND);
        return r;
    }

    public static ActionResult FORBIDDEN(){
        ActionResult r = new ActionResult();
        r.setStatus(ACTION_RESULT.FORBIDDEN);
        return r;
    }

    public static ActionResult MALFORMED(){
        ActionResult r = new ActionResult();
        r.setStatus(ACTION_RESULT.MALFORMED);
        return r;
    }

    public static ActionResult BADREQUEST(){
        ActionResult r = new ActionResult();
        r.setStatus(ACTION_RESULT.BADREQUEST);
        return r;
    }

    // ------------------------------------

    public ACTION_RESULT getStatus() {
        return status;
    }

    public void setStatus(ACTION_RESULT status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
