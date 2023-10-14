package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;

@Tag(name = "Tenant stats per day and type of consumption", description = "Tenant stats par day and type of consumption, not DCs but activity")
public class TenantSetupStatsSerie {

    @Schema(
        description = "name of the activity",
        example = "default",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String name;

    @Schema(
        description = "last 30 days values",
        example = "default",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected ArrayList<Integer> data;

    // ---


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getData() {
        return data;
    }

    public void setData(ArrayList<Integer> data) {
        this.data = data;
    }
}
