package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ticket creation Interface", description = "create a new ticket")
public class TicketCreationReqItf {

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

    // ---

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
