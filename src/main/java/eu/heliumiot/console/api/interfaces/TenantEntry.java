package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "One tenant entry", description = "One of the User tenants")
public class TenantEntry {

    @Schema(
        description = "Tenant uuid",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String id;
    @Schema(
        description = "Tenant name",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String name;
    @Schema(
        description = "User is admin of that tenant",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected boolean admin;


    //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
