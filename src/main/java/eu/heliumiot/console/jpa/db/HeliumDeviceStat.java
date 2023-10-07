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

import fr.ingeniousthings.tools.ClonnableObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "helium_device_stats",
        indexes = {
                @Index(name="uniqueDailyDeviceIndex", columnList = "day,deviceUUID", unique = true),
                @Index(name="dailyTenantIndex", columnList = "day,tenantUUID")
        }
)
public class HeliumDeviceStat implements ClonnableObject<HeliumDeviceStat> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // device UUID
    private String deviceUUID;

    private String tenantUUID;

    // date reference (day, 0) in Ms
    private long day;

    // timestamp for last commit Ms
    private long lastCommit;

    // --- type of cost
    private int registrationDc; // device creation costs
    private int uplinkDc;

    private int duplicate;
    private int duplicateDc;
    private int downlinkDc;
    private int inactivityDc;
    private int activityDc;

    private int uplink;

    private int downlink;

    private int joinReq;

    @Column(name = "join_dc", columnDefinition = "int4")
    private int joinDc;

    @Column(name = "join_accept_dc", columnDefinition = "int4")
    private int joinAcceptDc;

    // ---

    public HeliumDeviceStat clone() {
        System.out.println("### UserCacheElement clone not implemented");
        return null;
    }

    // ---

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDeviceUUID() {
        return deviceUUID.toUpperCase();
    }

    public void setDeviceUUID(String deviceUUID) {
        this.deviceUUID = deviceUUID.toUpperCase();
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public int getRegistrationDc() {
        return registrationDc;
    }

    public void setRegistrationDc(int registrationDc) {
        this.registrationDc = registrationDc;
    }

    public int getUplinkDc() {
        return uplinkDc;
    }

    public void setUplinkDc(int uplinkDc) {
        this.uplinkDc = uplinkDc;
    }

    public int getDuplicateDc() {
        return duplicateDc;
    }

    public void setDuplicateDc(int duplicateDc) {
        this.duplicateDc = duplicateDc;
    }

    public int getDownlinkDc() {
        return downlinkDc;
    }

    public void setDownlinkDc(int downlinkDc) {
        this.downlinkDc = downlinkDc;
    }

    public int getInactivityDc() {
        return inactivityDc;
    }

    public void setInactivityDc(int inactivityDc) {
        this.inactivityDc = inactivityDc;
    }

    public int getActivityDc() {
        return activityDc;
    }

    public void setActivityDc(int activityDc) {
        this.activityDc = activityDc;
    }

    public long getLastCommit() {
        return lastCommit;
    }

    public void setLastCommit(long lastCommit) {
        this.lastCommit = lastCommit;
    }

    public int getUplink() {
        return uplink;
    }

    public void setUplink(int uplink) {
        this.uplink = uplink;
    }

    public int getDownlink() {
        return downlink;
    }

    public void setDownlink(int downlink) {
        this.downlink = downlink;
    }

    public int getJoinReq() {
        return joinReq;
    }

    public void setJoinReq(int joinReq) {
        this.joinReq = joinReq;
    }

    public String getTenantUUID() {
        return tenantUUID;
    }

    public void setTenantUUID(String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }

    public int getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(int duplicate) {
        this.duplicate = duplicate;
    }

    public int getJoinDc() {
        return joinDc;
    }

    public void setJoinDc(int joinDc) {
        this.joinDc = joinDc;
    }

    public int getJoinAcceptDc() {
        return joinAcceptDc;
    }

    public void setJoinAcceptDc(int joinAcceptDc) {
        this.joinAcceptDc = joinAcceptDc;
    }
}
