package eu.heliumiot.console.jpa.db;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "helium_device_stats_history",
    indexes = {
        @Index(name="uniqueDailyDeviceHistoryIndex", columnList = "day,deviceUUID", unique = true),
        @Index(name="dailyTenantHistoryIndex", columnList = "day,tenantUUID")
    }
)
public class HeliumDeviceStatHistory {
    @Id
    private UUID id;
    private String deviceUUID;
    private String tenantUUID;
    private long day;
    private long lastCommit;
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


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDeviceUUID() {
        return deviceUUID;
    }

    public void setDeviceUUID(String deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public String getTenantUUID() {
        return tenantUUID;
    }

    public void setTenantUUID(String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getLastCommit() {
        return lastCommit;
    }

    public void setLastCommit(long lastCommit) {
        this.lastCommit = lastCommit;
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

    public int getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(int duplicate) {
        this.duplicate = duplicate;
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
