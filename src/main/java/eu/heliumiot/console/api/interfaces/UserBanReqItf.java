package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserBanReqItf {

    @Schema(
        description = "User login name",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String username;

    // ---


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
