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

import eu.heliumiot.console.jpa.db.HeliumDeviceStat;
import eu.heliumiot.console.jpa.db.Tenant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HeliumDeviceStatsRepository extends CrudRepository<HeliumDeviceStat, UUID> {

    public HeliumDeviceStat findOneHeliumDeviceStatByDeviceUUIDAndDay(
            String id,
            long day
    );

    public List<HeliumDeviceStat> findHeliumDeviceStatByDeviceUUIDAndDayBetween(
            String deviceUUID,
            long start,
            long stop
    );


    @Query(value = "SELECT '00000000-0000-0000-0000-000000000000' as id, 0 as day, 0 as last_commit, " +
                          "'00000000-0000-0000-0000-000000000000' as deviceuuid, " +
                          "?1 as tenantuuid, " +
                          "SUM(activity_dc) as activity_dc, SUM(downlink) as downlink, SUM(downlink_dc) as downlink_dc, " +
                          "SUM(duplicate) as duplicate, SUM(duplicate_dc) as duplicate_dc, SUM(inactivity_dc) as inactivity_dc, SUM(join_req) as join_req, " +
                          "SUM(registration_dc) as registration_dc, SUM(uplink) as uplink, SUM(uplink_dc) as uplink_dc, " +
                          "SUM(join_dc) as join_dc, SUM(join_accept_dc) as join_accept_dc " +
                          "FROM helium_device_stats " +
                          "WHERE tenantuuid = ?1 AND day >= ?2 AND day < ?3", nativeQuery = true)
    public HeliumDeviceStat findSumStatForTenantBetween(String tenantUUID, long from, long to);

    // get stats by day for a given tenantUUID
    @Query(value = "SELECT uuid_generate_v4() as id, day, 0 as last_commit, " +
        "'00000000-0000-0000-0000-000000000000' as deviceuuid, " +
        "?1 as tenantuuid, " +
        "SUM(activity_dc) as activity_dc, SUM(downlink) as downlink, SUM(downlink_dc) as downlink_dc, " +
        "SUM(duplicate) as duplicate, SUM(duplicate_dc) as duplicate_dc, SUM(inactivity_dc) as inactivity_dc, SUM(join_req) as join_req, " +
        "SUM(registration_dc) as registration_dc, SUM(uplink) as uplink, SUM(uplink_dc) as uplink_dc, " +
        "SUM(join_dc) as join_dc, SUM(join_accept_dc) as join_accept_dc " +
        "FROM helium_device_stats " +
        "WHERE tenantuuid = ?1 AND day >= ?2 AND day < ?3 " +
        "GROUP BY day ORDER BY day ASC", nativeQuery = true)
    public List<HeliumDeviceStat> findSumStatForTenantByDayBetween(String tenantUUID, long from, long to);

    // get stats by devices for a given tenantUUID
    @Query(value = "SELECT uuid_generate_v4() as id, 0 as day, 0 as last_commit, " +
        "deviceuuid, " +
        "?1 as tenantuuid, " +
        "SUM(activity_dc) as activity_dc, SUM(downlink) as downlink, SUM(downlink_dc) as downlink_dc, " +
        "SUM(duplicate) as duplicate, SUM(duplicate_dc) as duplicate_dc, SUM(inactivity_dc) as inactivity_dc, SUM(join_req) as join_req, " +
        "SUM(uplink + join_req) as registration_dc, SUM(uplink) as uplink, SUM(uplink_dc) as uplink_dc, " +
        "SUM(join_dc) as join_dc, SUM(join_accept_dc) as join_accept_dc " +
        "FROM helium_device_stats " +
        "WHERE tenantuuid = ?1 AND day >= ?2 AND day < ?3 " +
        "GROUP BY deviceuuid ORDER BY registration_dc DESC LIMIT ?4", nativeQuery = true)
    public List<HeliumDeviceStat> findSumStatForTenantDeviceByDayBetween(String tenantUUID, long from, long to, int limit);

    // Get device with join loop but no uplinks for a given tenant
    @Query(value = "SELECT uuid_generate_v4() as id, 0 as day, 0 as last_commit, " +
        "deviceuuid, " +
        "?1 as tenantuuid, " +
        "SUM(activity_dc) as activity_dc, SUM(downlink) as downlink, SUM(downlink_dc) as downlink_dc, " +
        "SUM(duplicate) as duplicate, SUM(duplicate_dc) as duplicate_dc, SUM(inactivity_dc) as inactivity_dc, SUM(join_req) as join_req, " +
        "SUM(registration_dc) as registration_dc, SUM(uplink) as uplink, SUM(uplink_dc) as uplink_dc, " +
        "SUM(join_dc) as join_dc, SUM(join_accept_dc) as join_accept_dc " +
        "FROM helium_device_stats " +
        "WHERE tenantuuid = ?1 AND day >= ?2 AND day < ?3 AND ( uplink = 0 OR inactivity_dc > 0)" +
        "GROUP BY deviceuuid ORDER BY join_req DESC LIMIT ?4", nativeQuery = true)
    public List<HeliumDeviceStat> findSumStatForTenantInactiveDeviceByDayBetween(String tenantUUID, long from, long to, int limit);


    // get stats for top consuming tenant
    @Query(value = "SELECT uuid_generate_v4() as id, 0 as day, 0 as last_commit, " +
        "'00000000-0000-0000-0000-000000000000' as deviceuuid, " +
        "tenantuuid, " +
        "SUM(activity_dc+downlink_dc+duplicate_dc+inactivity_dc+registration_dc+uplink_dc+join_dc+join_accept_dc) as activity_dc, " +
        "SUM(downlink) as downlink, SUM(downlink_dc) as downlink_dc, " +
        "SUM(duplicate) as duplicate, SUM(duplicate_dc) as duplicate_dc, SUM(inactivity_dc) as inactivity_dc, SUM(join_req) as join_req, " +
        "SUM(registration_dc) as registration_dc, SUM(uplink) as uplink, SUM(uplink_dc) as uplink_dc, " +
        "SUM(join_dc) as join_dc, SUM(join_accept_dc) as join_accept_dc " +
        "FROM helium_device_stats " +
        "WHERE day >= ?1 AND day < ?2 " +
        "GROUP BY tenantuuid ORDER BY activity_dc DESC LIMIT ?3", nativeQuery = true)
    public List<HeliumDeviceStat> findSumStatByDayBetween(long from, long to, int limit);

}
