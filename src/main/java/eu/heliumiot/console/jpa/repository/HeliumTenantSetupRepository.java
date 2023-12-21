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

import eu.heliumiot.console.jpa.db.HeliumTenantSetup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HeliumTenantSetupRepository extends PagingAndSortingRepository<HeliumTenantSetup, UUID> {

    public HeliumTenantSetup findOneHeliumTenantSetupByTenantUUID(String id);

    public HeliumTenantSetup findOneHeliumTenantSetupById(UUID id);

    public List<HeliumTenantSetup> findHeliumTenantSetupByTemplate(boolean template);

    public List<HeliumTenantSetup> findHeliumTenantSetupByRouteId(String routeId);

    public List<HeliumTenantSetup> findAllByTemplate(boolean template, Pageable pageable);

    public Slice<HeliumTenantSetup> findHeliumTenantSetupByDcPerJoinRequest(
            int dcPerJoinRequest,
            Pageable pageable
    );

    // Search tenant_setup (and route) not associated to tenant
    @Query(value = "SELECT * FROM helium_tenant_setup LEFT JOIN tenant on UUID(helium_tenant_setup.tenantuuid) = tenant.id WHERE tenant.id IS NULL AND helium_tenant_setup.template IS FALSE", nativeQuery = true)
    public List<HeliumTenantSetup> findMissingTenantSetup();

}
