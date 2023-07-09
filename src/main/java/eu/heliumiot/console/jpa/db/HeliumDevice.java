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
@Table(name = "helium_devices",
        indexes = {
                @Index(name="uniqueDeviceIndex", columnList = "device_eui", unique = true),
                @Index(name="tenantIndex", columnList = "tenantUUID")
        }
)
public class HeliumDevice implements ClonnableObject<HeliumDevice> {

    // Warning, do not change the order - it will impact the BDD values
    public enum DeviceState { INSERTED, ACTIVE, INACTIVE, DEACTIVATED, OUTOFDCS, DELETED, DISABLED }
                            // 0        1       2           3           4        5          6

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // device UUID
    @Column(name = "device_eui")
    private String deviceEui;

    @Column(name = "application_eui")
    private String applicationEui; // usually empty with chirpstack w/o Join server
                                   // for later use

    private byte[] deviceUUID;

    private String applicationUUID;

    private String tenantUUID;

    private DeviceState state;

    // accelerator related to state to process in batch or not
    @Column(name = "to_update")
    private boolean toUpdate;

    @Column(name = "created_at")
    private long createdAt;

    @Column(name = "inserted_at")
    private long insertedAt;

    @Column(name = "deleted_at")
    private long deletedAt;

    // Last time the device has been processed by the device process batch
    @Column(name = "last_seen")
    private long lastSeen;

    // Activity is invoiced by period, take a note on the last check
    @Column(name = "last_activity_invoiced")
    private long lastActivityInvoiced;

    // Inactivity is invoiced by period, take a note on the last check
    @Column(name = "last_inactivity_invoiced")
    private long lastInactivityInvoiced;

    // Total number of DCs consumed by the device from creation in the past days
    @Column(name = "totaldcs")
    private long totalDCs;

    // At what is the hourly timestamp corresponding to this total
    @Column(name = "totaldcs_at")
    private long totalDCsAt;

    // DCs for current day (this value is not fix in the database and comes from cache for best value)
    @Column(name = "todaydcs")
    private long todayDCs;

    // ---

    public HeliumDevice clone() {
        System.out.println("### HeliumDevice clone not implemented");
        return null;
    }

    // ---

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDeviceEui() {
        return deviceEui;
    }

    public void setDeviceEui(String deviceEui) {
        this.deviceEui = deviceEui;
    }

    public byte[] getDeviceUUID() {
        return deviceUUID;
    }

    public void setDeviceUUID(byte[] deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public String getTenantUUID() {
        return tenantUUID;
    }

    public void setTenantUUID(String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }

    public DeviceState getState() {
        return state;
    }

    public void setState(DeviceState state) {
        this.state = state;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getInsertedAt() {
        return insertedAt;
    }

    public void setInsertedAt(long insertedAt) {
        this.insertedAt = insertedAt;
    }

    public long getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getApplicationUUID() {
        return applicationUUID;
    }

    public void setApplicationUUID(String applicationUUID) {
        this.applicationUUID = applicationUUID;
    }

    public String getApplicationEui() {
        if ( applicationEui.length() < 16 ) return "0000000000000000";
        return applicationEui;
    }

    public void setApplicationEui(String applicationEui) {
        this.applicationEui = applicationEui;
    }

    public long getLastActivityInvoiced() {
        return lastActivityInvoiced;
    }

    public void setLastActivityInvoiced(long lastActivityInvoiced) {
        this.lastActivityInvoiced = lastActivityInvoiced;
    }

    public long getLastInactivityInvoiced() {
        return lastInactivityInvoiced;
    }

    public void setLastInactivityInvoiced(long lastInactivityInvoiced) {
        this.lastInactivityInvoiced = lastInactivityInvoiced;
    }

    public long getTotalDCs() {
        return totalDCs;
    }

    public void setTotalDCs(long totalDCs) {
        this.totalDCs = totalDCs;
    }

    public long getTotalDCsAt() {
        return totalDCsAt;
    }

    public void setTotalDCsAt(long totalDCsAt) {
        this.totalDCsAt = totalDCsAt;
    }

    public boolean isToUpdate() {
        return toUpdate;
    }

    public void setToUpdate(boolean toUpdate) {
        this.toUpdate = toUpdate;
    }

    public long getTodayDCs() {
        return todayDCs;
    }

    public void setTodayDCs(long todayDCs) {
        this.todayDCs = todayDCs;
    }
}
