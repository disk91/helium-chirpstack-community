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
@Immutable
@Table(name = "tenant")
public class Tenant {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private boolean can_have_gateways;
    private Timestamp created_at;
    private String description;
    private int max_device_count;
    private int max_gateway_count;
    // Name of the tenant, max 100 char
    private String name;
    private boolean private_gateways_up;
    private boolean private_gateways_down;
    private Timestamp updated_at;

    // ---


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isCan_have_gateways() {
        return can_have_gateways;
    }

    public void setCan_have_gateways(boolean can_have_gateways) {
        this.can_have_gateways = can_have_gateways;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMax_device_count() {
        return max_device_count;
    }

    public void setMax_device_count(int max_device_count) {
        this.max_device_count = max_device_count;
    }

    public int getMax_gateway_count() {
        return max_gateway_count;
    }

    public void setMax_gateway_count(int max_gateway_count) {
        this.max_gateway_count = max_gateway_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrivate_gateways_up() {
        return private_gateways_up;
    }

    public void setPrivate_gateways_up(boolean private_gateways_up) {
        this.private_gateways_up = private_gateways_up;
    }

    public boolean isPrivate_gateways_down() {
        return private_gateways_down;
    }

    public void setPrivate_gateways_down(boolean private_gateways_down) {
        this.private_gateways_down = private_gateways_down;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
