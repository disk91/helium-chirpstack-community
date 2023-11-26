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
import java.util.UUID;

@Entity
@Table(name = "helium_tenant",
        indexes = {
                @Index(name="uniqueTenantIndex", columnList = "tenantUUID", unique = true)
        }
)
public class HeliumTenant {

    public enum TenantState { NORMAL, REQUESTDEACTIVATION, DEACTIVATED, DELETED, REQUESTREACTIVATION }
    //                           0        1                    2           3             4
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // tenant UUID
    private String tenantUUID;


    // tenant DCs balance
    private long dcBalance;

    @Column(name="alarmed")
    private int alarmed = 0;    // 0 = No alarm, never armed / 1 = No alarm but armed / 2 : Warn / 3: Alarm

    // tenant state
    private TenantState state;

    @Column(name = "max_copy")
    private int maxCopy = 0;    // max number of DC purchased by message for this tenant

    // ---


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public long getDcBalance() {
        return dcBalance;
    }

    public void setDcBalance(long dcBalance) {
        this.dcBalance = dcBalance;
    }

    public String getTenantUUID() {
        return tenantUUID;
    }

    public void setTenantUUID(String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }

    public TenantState getState() {
        return state;
    }

    public void setState(TenantState state) {
        this.state = state;
    }

    public int getMaxCopy() {
        return maxCopy;
    }

    public void setMaxCopy(int maxCopy) {
        this.maxCopy = maxCopy;
    }

    public int getAlarmed() {
        return alarmed;
    }

    public void setAlarmed(int alarmed) {
        this.alarmed = alarmed;
    }
}
