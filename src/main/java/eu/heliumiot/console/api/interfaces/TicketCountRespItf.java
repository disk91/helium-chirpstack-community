package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;

public class TicketCountRespItf {

    @Schema(
            description = "Number of ticket requiring action",
            example = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected int pending;

    //


    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }
}
