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

@Tag(name = "Service provider company information", description = "Company informations that will be used for the invoice creation")
public class InvoiceSetupGetRespItf {

    @Schema(
            description = "Name of the company",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String companyName;


    @Schema(
            description = "Full Address of the company",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String companyAddress;

    @Schema(
            description = "VAT number",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String companyVAT;

    @Schema(
            description = "Company ID + registration sentence",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String companyRegistration;


    // ------------------------------------


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyVAT() {
        return companyVAT;
    }

    public void setCompanyVAT(String companyVAT) {
        this.companyVAT = companyVAT;
    }

    public String getCompanyRegistration() {
        return companyRegistration;
    }

    public void setCompanyRegistration(String companyRegistration) {
        this.companyRegistration = companyRegistration;
    }
}
