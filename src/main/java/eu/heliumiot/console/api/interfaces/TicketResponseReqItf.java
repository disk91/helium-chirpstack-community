package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ticket creation Interface", description = "create a new ticket")
public class TicketResponseReqItf {

    @Schema(
            description = "id of the ticket",
            example = "12452-2541562-...",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String ticketUUID;


    @Schema(
            description = "ticket response",
            example = "For doing this you can ...",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String content;

    @Schema(
            description = "when true, the ticket is closed with this response",
            example = "false",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean closing;

    // ---


    public String getTicketUUID() {
        return ticketUUID;
    }

    public void setTicketUUID(String ticketUUID) {
        this.ticketUUID = ticketUUID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isClosing() {
        return closing;
    }

    public void setClosing(boolean closing) {
        this.closing = closing;
    }
}
