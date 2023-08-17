package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ticket list Interface", description = "list open ticket belonging to you")
public class TicketListRespItf {

    @Schema(
            description = "id of the ticket",
            example = "12452-2541562-...",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String ticketUUID;


    @Schema(
            description = "topic of the ticket",
            example = "I want to purchase DCs with a bank transfer",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String topic;

    @Schema(
            description = "detail of my request",
            example = "I would prefer to do a bank wire for purchasing a large amount of DCs...",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String details;

    @Schema(
            description = "Status of the ticket 1-Open 2-resp pending 3-close",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int status;

    @Schema(
            description = "Time of ticket creation",
            example = "1322426",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long createdAt;

    // ---

    public void setTopic(String topic) {
        if ( topic.length() > 254 ) topic = topic.substring(0,254);
        this.topic = topic;
    }

    // ---

    public String getTopic() {
        return topic;
    }


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTicketUUID() {
        return ticketUUID;
    }

    public void setTicketUUID(String ticketUUID) {
        this.ticketUUID = ticketUUID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
