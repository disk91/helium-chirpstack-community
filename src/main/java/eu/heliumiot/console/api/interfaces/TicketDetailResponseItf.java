package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;

public class TicketDetailResponseItf {

    @Schema(
            description = "response content",
            example = "bla bla bla",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected String content;

    @Schema(
            description = "response creation date",
            example = "1342542",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected long createdAt;

    @Schema(
            description = "response comes from admin/user",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected boolean isAdminReponse;

    //

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAdminReponse() {
        return isAdminReponse;
    }

    public void setAdminReponse(boolean adminReponse) {
        isAdminReponse = adminReponse;
    }
}
