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

@Tag(name = "Transaction configuration Interface", description = "return the stripe & transfer status interface")
public class TransactionConfigRespItf {

    @Schema(
            description = "True when stripe DC purchase is enable",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected boolean stripeEnable;

    @Schema(
            description = "True when the DC transfer between tenant is enable",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected boolean transferEnable;


    // ------------------------------------


    public boolean isStripeEnable() {
        return stripeEnable;
    }

    public void setStripeEnable(boolean stripeEnable) {
        this.stripeEnable = stripeEnable;
    }

    public boolean isTransferEnable() {
        return transferEnable;
    }

    public void setTransferEnable(boolean transferEnable) {
        this.transferEnable = transferEnable;
    }
}
