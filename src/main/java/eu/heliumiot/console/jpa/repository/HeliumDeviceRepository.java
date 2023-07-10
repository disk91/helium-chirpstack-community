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
package eu.heliumiot.console.jpa.repository;

import eu.heliumiot.console.jpa.db.HeliumDevice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HeliumDeviceRepository extends PagingAndSortingRepository<HeliumDevice, UUID> {

    public HeliumDevice findOneHeliumDeviceByDeviceEui(String deviceId);

    @Query(value = "SELECT * FROM helium_devices LEFT JOIN device on device.dev_eui = helium_devices.deviceuuid WHERE device.dev_eui is null AND helium_devices.state <> 5", nativeQuery = true)
    public List<HeliumDevice> findDeletedDevices();

    @Query(value = "SELECT * FROM helium_devices LEFT JOIN device on device.dev_eui = helium_devices.deviceuuid WHERE device.is_disabled = true AND helium_devices.state <> 5 AND helium_devices.state <> 6", nativeQuery = true)
    public List<HeliumDevice> findDeactivatedDevices();

    @Query(value = "SELECT * FROM helium_devices LEFT JOIN device on device.dev_eui = helium_devices.deviceuuid WHERE device.is_disabled = false AND helium_devices.state = 6", nativeQuery = true)
    public List<HeliumDevice> findReactivatedDevices();



    @Query(value = "SELECT * FROM helium_devices WHERE to_update IS TRUE AND helium_devices.state <> 5 ORDER BY last_seen ASC LIMIT ?1", nativeQuery = true)
    public List<HeliumDevice> findHeliumDeviceToProcess(int limit);

    public List<HeliumDevice> findHeliumDeviceByTenantUUID(
            String tenantUUID
    );

    public Slice<HeliumDevice> findHeliumDeviceByTenantUUID(
            String tenantUUID,
            Pageable pageable
    );


}

