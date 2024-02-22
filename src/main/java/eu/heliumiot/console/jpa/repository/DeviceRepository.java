/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2024.
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
package eu.heliumiot.console.jpa.repository;

import eu.heliumiot.console.jpa.db.Device;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceRepository extends CrudRepository<Device, byte[]> {

    public Device findOneDeviceByDevEui(byte[] id);

    public List<Device> findDeviceByCreatedAtGreaterThanOrderByCreatedAtAsc(Timestamp from);

    @Query(value = "SELECT * FROM device WHERE application_id <> ?1 AND created_at BETWEEN ?2 AND ?3 ORDER BY created_at ASC", nativeQuery = true)
    public List<Device> findDeviceByNApplicationIdAndCreatedAtBetweenOrderByCreatedAtAsc(UUID appId, Timestamp from, Timestamp to);

    public List<Device> findDeviceByApplicationIdAndCreatedAtBetweenOrderByCreatedAtAsc(UUID appId, Timestamp from, Timestamp to);

    public List<Device> findDeviceByUpdatedAtGreaterThanOrderByUpdatedAtAsc(Timestamp from);

    public Slice<Device> findDeviceBy(Pageable pageable);

    @Query(value= "SELECT DISTINCT count(device.*) FROM device " +
        "JOIN helium_devices ON (device.dev_eui = helium_devices.deviceuuid) " +
        "WHERE helium_devices.tenantuuid = ?1 AND " +
        "device.last_seen_at < to_timestamp(?2 / 1000.0)", nativeQuery = true)
    public long countDeviceByTenantUUIDAndLastSeenLowerThan(
        String tenantId,
        long lastRef
    );

    @Query(value= "SELECT DISTINCT device.* FROM device " +
        "JOIN helium_devices ON (device.dev_eui = helium_devices.deviceuuid) " +
        "WHERE helium_devices.tenantuuid = ?1 AND " +
        "device.last_seen_at < to_timestamp(?2 / 1000.0) ORDER BY last_seen_at DESC " +
        "LIMIT ?3 OFFSET ?4", nativeQuery = true)
    public List<Device> findDeviceByTenantUUIDAndLastSeenLowerThan(
        String tenantId,
        long lastRef,
        int page,
        int limit
    );

}
