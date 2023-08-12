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
@Table(name = "helium_pending_user",
        indexes = {
                @Index(name="hpeUsernameIndex", columnList = "username")
        }
)
public class HeliumPendingUser {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // type of entry, user creation / password loss see UserService
    @Column(name = "type")
    private int type;

    // reference to the User table
    @Column(name = "username")
    private String username;

    // other information
    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "tenant_name")
    private String tenantName;

    @Column(name = "offer_name")
    private String offerName;

    @Column(name = "validation_code")
    private String validationCode;

    @Column(name = "inserted_at")
    private Timestamp insertedAt;

    @Column(name = "retrial")
    private int retrial;

    @Column(name = "condition_version")
    private String conditionVersion;

    // ---


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    public Timestamp getInsertedAt() {
        return insertedAt;
    }

    public void setInsertedAt(Timestamp insertedAt) {
        this.insertedAt = insertedAt;
    }

    public int getRetrial() {
        return retrial;
    }

    public void setRetrial(int retrial) {
        this.retrial = retrial;
    }

    public String getConditionVersion() {
        return conditionVersion;
    }

    public void setConditionVersion(String conditionVersion) {
        this.conditionVersion = conditionVersion;
    }
}
