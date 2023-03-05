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
import eu.heliumiot.console.jpa.db.HeliumTenant;
import eu.heliumiot.console.jpa.db.SumResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HeliumTenantRepository extends CrudRepository<HeliumTenant, UUID> {

    public HeliumTenant findOneHeliumTenantByTenantUUID(String id);

    public List<HeliumTenant> findHeliumTenantByState(HeliumTenant.TenantState state);

    @Query(value = "SELECT SUM(dc_balance) FROM helium_tenant",nativeQuery = true)
    public Long selectSumOfDcs();

    @Query(value = "SELECT * FROM helium_tenant LEFT JOIN tenant ON tenant.id = uuid(helium_tenant.tenantuuid) WHERE tenant.id is null AND helium_tenant.state <> 3", nativeQuery = true)
    public List<HeliumTenant> findDeletedTenant();


}
