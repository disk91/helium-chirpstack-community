package eu.heliumiot.console.jpa.db;

import java.io.Serializable;
import java.util.UUID;

public class UserTenantId implements Serializable {

    private static final long serialVersionUID = 0L;
    private UUID userId;

    private UUID tenantId;

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
