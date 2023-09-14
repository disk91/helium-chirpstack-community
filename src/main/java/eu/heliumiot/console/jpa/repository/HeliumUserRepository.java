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

import eu.heliumiot.console.jpa.db.HeliumUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HeliumUserRepository extends CrudRepository<HeliumUser, UUID> {

    public HeliumUser findOneHeliumUserById(UUID id);

    public HeliumUser findOneHeliumUserByUserid(String id);

    public HeliumUser findOneHeliumUserByUsername(String username);

    @Query(value = "SELECT * FROM helium_user WHERE username LIKE CONCAT('%',?1,'%') OR userid LIKE CONCAT('%',?1,'%') LIMIT 5", nativeQuery = true)
    public List<HeliumUser> findHeliumUsersBySearch(String search);

    @Query(value = "SELECT * FROM helium_user WHERE username LIKE CONCAT('%',?1,'%') OR userid LIKE CONCAT('%',?1,'%') ORDER BY registration_time DESC LIMIT 5", nativeQuery = true)
    public List<HeliumUser> findHeliumUsersBySearchOrderByRegistration(String search);

    public List<HeliumUser> findFirst20HeliumUsersByOrderByRegistrationTimeDesc();

    //public List<HeliumUser> findHeliumUsersByUsernameContainingOrUseridContaining(String search,String search2);


}
