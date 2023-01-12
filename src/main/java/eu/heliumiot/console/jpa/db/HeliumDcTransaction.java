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

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "helium_dc_transaction")
public class HeliumDcTransaction {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // type of transaction 0 - DC transfer / 1 - stripe purchase
    private int type;

    // DCs quantity
    private long dcs;

    // DCs destination tenant UUID
    private String targetTenantUUID;

    // User owning the transaction
    private String userUUID;

    // Date of the transaction
    private Timestamp createdAt;

    // Ip of the transaction
    private String userIP;

    // Customer custom message associated to the operation.
    private String memo;

    // ----------------------
    // Type 0 information

    // number of DCs requested (can differ with executed)
    private long dcsRequested;

    // DCs source tenant UUID ( for type 0
    private String sourceTenantUUID;


    // ----------------------
    // Type 1 information

    // DCs price for type 1
    private double dcsPrice;

    // Name of the owner
    private String firstName;
    private String lastName;
    private String company;
    private String address;
    private String cityId;
    private String cityName;
    private String country;

    // stripe cost (fee)
    private double stripeCost;

    // stripe currency
    @Column(name="stripe_currency")
    private String stripeCurrency;

    // what stripe gives in the currency after conv rate
    @Column(name="stripe_amount")
    private double stripeAmount;

    // what is currency conv rate
    @Column(name="stripe_crate")
    private double stripeCRate;

    private String transactionId;
    private String stripeUser;
    private double applicableVAT;

    // Stripe info
    @Column(name="stripe_client_key")
    private String stripeClientKey = null;

    @Column(name="stripe_status")
    private String stripeStatus;

    @Column(name="is_completed")
    private boolean isCompleted = true;

    @Column(name="intentTime")
    private long intentTime = 0;


    // ##############################


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDcs() {
        return dcs;
    }

    public void setDcs(long dcs) {
        this.dcs = dcs;
    }

    public String getTargetTenantUUID() {
        return targetTenantUUID;
    }

    public void setTargetTenantUUID(String targetTenantUUID) {
        this.targetTenantUUID = targetTenantUUID;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public long getDcsRequested() {
        return dcsRequested;
    }

    public void setDcsRequested(long dcsRequested) {
        this.dcsRequested = dcsRequested;
    }

    public String getSourceTenantUUID() {
        return sourceTenantUUID;
    }

    public void setSourceTenantUUID(String sourceTenantUUID) {
        this.sourceTenantUUID = sourceTenantUUID;
    }

    public double getDcsPrice() {
        return dcsPrice;
    }

    public void setDcsPrice(double dcsPrice) {
        this.dcsPrice = dcsPrice;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public double getStripeCost() {
        return stripeCost;
    }

    public void setStripeCost(double stripeCost) {
        this.stripeCost = stripeCost;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStripeUser() {
        return stripeUser;
    }

    public void setStripeUser(String stripeUser) {
        this.stripeUser = stripeUser;
    }

    public double getApplicableVAT() {
        return applicableVAT;
    }

    public void setApplicableVAT(double applicableVAT) {
        this.applicableVAT = applicableVAT;
    }

    public String getStripeClientKey() {
        return stripeClientKey;
    }

    public void setStripeClientKey(String stripeClientKey) {
        this.stripeClientKey = stripeClientKey;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public long getIntentTime() {
        return intentTime;
    }

    public void setIntentTime(long intentTime) {
        this.intentTime = intentTime;
    }

    public String getStripeStatus() {
        return stripeStatus;
    }

    public void setStripeStatus(String stripeStatus) {
        this.stripeStatus = stripeStatus;
    }

    public String getStripeCurrency() {
        return stripeCurrency;
    }

    public void setStripeCurrency(String stripeCurrency) {
        this.stripeCurrency = stripeCurrency;
    }

    public double getStripeAmount() {
        return stripeAmount;
    }

    public void setStripeAmount(double stripeAmount) {
        this.stripeAmount = stripeAmount;
    }

    public double getStripeCRate() {
        return stripeCRate;
    }

    public void setStripeCRate(double stripeCRate) {
        this.stripeCRate = stripeCRate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
