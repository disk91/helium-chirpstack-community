package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.persistence.Column;

@Tag(name = "Tenant balance Interface", description = "tenant balance interface")
public class MessagePendingRespItf {

    @Schema(
            description = "type of message display 1 for modal (full screen) 2 for toaster (banner)",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int type;

    @Schema(
            description = "category of message for display 0 for info, 1 for warning, 2 or danger",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int category;

    @Schema(
            description = "message content to be printed",
            example = "Welcome message !",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String content;

    // ---


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
