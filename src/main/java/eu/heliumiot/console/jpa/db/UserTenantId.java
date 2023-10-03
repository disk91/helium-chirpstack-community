package eu.heliumiot.console.jpa.db;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserTenantId implements Serializable {

    private static final long serialVersionUID = 0L;
    private UUID userId;

    private UUID tenantId;

    // ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTenantId ut = (UserTenantId) o;
        return userId.equals(ut.getUserId()) && tenantId.equals(ut.getTenantId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, tenantId);
    }

    // ---

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }
}
