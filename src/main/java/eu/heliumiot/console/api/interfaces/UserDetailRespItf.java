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

@Tag(name = "User details Interface", description = "user details interface")
public class UserDetailRespItf {

    @Schema(
            description = "User login name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String username;


    @Schema(
            description = "Billing company name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String company ="";

    @Schema(
            description = "User first name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String firstName ="";

    @Schema(
            description = "User last name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String lastName ="";

    @Schema(
            description = "User address",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String address="";

    @Schema(
            description = "User city code",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String cityCode="";

    @Schema(
            description = "User city name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String cityName="";

    @Schema(
            description = "User country",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String country="";


    // ------------------------------------


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
