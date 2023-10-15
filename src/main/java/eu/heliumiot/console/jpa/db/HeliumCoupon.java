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
@Table(name = "helium_coupon",
        indexes = {
                @Index(name="uniqueCouponId", columnList = "coupon_id", unique = true)
        }
)
public class HeliumCoupon {

    public enum CouponState { ACTIVE, CLEARED }
    //                           0        1
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // coupon UUID
    @Column(name = "coupon_id")
    private String couponID;

    // tenant associated tenantTemplate
    @Column(name = "tenant_uuid")
    private String tenantUUID;

    // who can access that coupon
    @Column(name = "coupon_for")
    private String couponFor;

    // coupon state
    @Column(name = "coupon_state")
    private CouponState CouponState;

    // usage
    @Column(name = "max_use")
    private int maxUse;

    @Column(name = "in_use")
    private int inUse;

    @Column(name = "start")
    private long start;

    @Column(name = "stop")
    private long stop;


    // ---


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCouponID() {
        return couponID;
    }

    public void setCouponID(String couponID) {
        this.couponID = couponID;
    }

    public String getTenantUUID() {
        return tenantUUID;
    }

    public void setTenantUUID(String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }

    public HeliumCoupon.CouponState getCouponState() {
        return CouponState;
    }

    public void setCouponState(HeliumCoupon.CouponState couponState) {
        CouponState = couponState;
    }

    public int getMaxUse() {
        return maxUse;
    }

    public void setMaxUse(int maxUse) {
        this.maxUse = maxUse;
    }

    public int getInUse() {
        return inUse;
    }

    public void setInUse(int inUse) {
        this.inUse = inUse;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getStop() {
        return stop;
    }

    public void setStop(long stop) {
        this.stop = stop;
    }

    public String getCouponFor() {
        return couponFor;
    }

    public void setCouponFor(String couponFor) {
        this.couponFor = couponFor;
    }
}
