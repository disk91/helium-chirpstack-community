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

@Tag(name = "Stripe transaction initialization response", description = "Init a stripe transaction response")
public class TransactionStripeRespItf {

    @Schema(
            description = "Stripe public key to be used",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String stripePublicKey;


    @Schema(
            description = "Transaction intent secret",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String stripeTxSecret;

    @Schema(
            description = "Transaction Id",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String transactionUUID;



    // ------------------------------------


    public String getStripePublicKey() {
        return stripePublicKey;
    }

    public void setStripePublicKey(String stripePublicKey) {
        this.stripePublicKey = stripePublicKey;
    }

    public String getStripeTxSecret() {
        return stripeTxSecret;
    }

    public void setStripeTxSecret(String stripeTxSecret) {
        this.stripeTxSecret = stripeTxSecret;
    }

    public String getTransactionUUID() {
        return transactionUUID;
    }

    public void setTransactionUUID(String transactionUUID) {
        this.transactionUUID = transactionUUID;
    }
}
