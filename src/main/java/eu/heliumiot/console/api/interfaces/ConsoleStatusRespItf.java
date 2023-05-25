package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Console Status Interface", description = "different public status to be used on login page")
public class ConsoleStatusRespItf {

    @Schema(
            description = "true when user can sign up",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean openForRegistration;

    // ---

    public boolean isOpenForRegistration() {
        return openForRegistration;
    }

    public void setOpenForRegistration(boolean openForRegistration) {
        this.openForRegistration = openForRegistration;
    }
}
