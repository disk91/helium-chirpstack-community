package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserConditionVersionUpdateReqItf {
    @Schema(
        description = "User Condition new Version name",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String conditionVersion;

    // ---


    public String getConditionVersion() {
        return conditionVersion;
    }

    public void setConditionVersion(String conditionVersion) {
        this.conditionVersion = conditionVersion;
    }
}
