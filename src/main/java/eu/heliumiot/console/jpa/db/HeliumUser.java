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
package eu.heliumiot.console.jpa.db;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "helium_user",
        indexes = {
                @Index(name="uniqueUserIndex", columnList = "userid", unique = true),
                @Index(name="usernameIndex", columnList = "username")
        }
)
public class HeliumUser {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // reference to the User table
    @Column(name = "username")
    private String username;
    @Column(name = "userid")
    private String userid;

    // other information
    @Column(name = "user_secret")
    private String userSecret;

    @Column(name = "company")
    private String company ="";

    @Column(name = "first_name")
    private String firstName ="";

    @Column(name = "last_name")
    private String lastName ="";

    @Column(name = "address")
    private String address="";

    @Column(name = "city_code")
    private String cityCode="";

    @Column(name = "city_name")
    private String cityName="";

    @Column(name = "country")
    private String country="";

    // completion status - see option in UserService
    @Column(name = "profile_status")
    private String profileStatus;

    // default offering for this user
    @Column(name = "default_offer")
    private String defaultOffer="default";

    @Column(name = "registration_time")
    private Timestamp registrationTime;

    @Column(name = "condition_validation")
    private boolean conditionValidation = false;

    @Column(name = "condition_version")
    private String conditionVersion;

    @Column(name = "condition_time")
    private Timestamp conditionTime;

    @Column(name = "last_seen")
    private long lastSeen = 0;      // last login

    @Column(name = "last_mess_seen")
    private long lastMessSeen = 0;  // last message seen


    // ---

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserSecret() {
        return userSecret;
    }

    public void setUserSecret(String userSecret) {
        this.userSecret = userSecret;
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

    public String getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
    }

    public String getDefaultOffer() {
        return defaultOffer;
    }

    public void setDefaultOffer(String defaultOffer) {
        this.defaultOffer = defaultOffer;
    }

    public Timestamp getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Timestamp registrationTime) {
        this.registrationTime = registrationTime;
    }

    public boolean isConditionValidation() {
        return conditionValidation;
    }

    public void setConditionValidation(boolean conditionValidation) {
        this.conditionValidation = conditionValidation;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public long getLastMessSeen() {
        return lastMessSeen;
    }

    public void setLastMessSeen(long lastMessSeen) {
        this.lastMessSeen = lastMessSeen;
    }

    public String getConditionVersion() {
        return conditionVersion;
    }

    public void setConditionVersion(String conditionVersion) {
        this.conditionVersion = conditionVersion;
    }

    public Timestamp getConditionTime() {
        return conditionTime;
    }

    public void setConditionTime(Timestamp conditionTime) {
        this.conditionTime = conditionTime;
    }
}
