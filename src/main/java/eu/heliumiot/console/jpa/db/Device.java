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

import fr.ingeniousthings.tools.HexaConverters;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Immutable
@Table(name = "device")
public class Device {
    @Id
    @Column(name = "dev_eui")
    private byte[] devEui;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "last_seen_at")
    private Timestamp lastSeenAt;
    @Column(name = "is_disabled")
    private boolean isDisabled;
    private String name;
    @Column(name = "dev_addr")
    private byte[] devAddr;
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @Column(name = "application_id")
    private UUID applicationId;

    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @Column(name = "device_profile_id")
    private UUID deviceProfileId;

    private String description;

    @Column(name = "variables")
    private String variables;       // contains tags

    @Column(name = "join_eui")
    private byte[] join_eui;


    // --
    public String getAppEui() {
        if ( this.variables != null ) {
            String v = this.variables.strip();
            if ( v.startsWith("{") && v.endsWith("}") ) {
                v = v.substring(1,v.length()-1);
                String vs [] =  v.split(",");
                for ( String _v : vs ) {
                    _v = _v.strip();
                    if ( _v.startsWith("\"app_eui\":") ) {
                        String ae = _v.split(":")[1];
                        ae = ae.strip();
                        ae = ae.substring(1,ae.length()-1);
                        if ( ae.length() == 16 && HexaConverters.isHexString(ae) ) {
                            return ae;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        if ( this.join_eui != null ) {
            String joinEui = HexaConverters.byteToHexString(this.join_eui);
            if ( joinEui.length() == 16 && joinEui.compareToIgnoreCase("0000000000000000") != 0 ) {
                // we have a joinEui setup
                return joinEui;
            }
        }
        return null;
    }


    // ---


    public byte[] getDevEui() {
        return devEui;
    }

    public void setDevEui(byte[] devEui) {
        this.devEui = devEui;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getLastSeenAt() {
        return lastSeenAt;
    }

    public void setLastSeenAt(Timestamp lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getDevAddr() {
        return devAddr;
    }

    public void setDevAddr(byte[] devAddr) {
        this.devAddr = devAddr;
    }

    public UUID getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(UUID applicationId) {
        this.applicationId = applicationId;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public UUID getDeviceProfileId() {
        return deviceProfileId;
    }

    public void setDeviceProfileId(UUID deviceProfileId) {
        this.deviceProfileId = deviceProfileId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getJoin_eui() {
        return join_eui;
    }

    public void setJoin_eui(byte[] join_eui) {
        this.join_eui = join_eui;
    }
}
