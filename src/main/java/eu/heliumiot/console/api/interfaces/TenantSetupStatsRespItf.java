package eu.heliumiot.console.api.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Tenant stats per day and type of consumption", description = "Tenant stats par day and type of consumption, not DCs but activity")
public class TenantSetupStatsRespItf {

    @Schema(
        description = "Series of data to stack",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected List<TenantSetupStatsSerie> series;

    @Schema(
        description = "Corresponding data label for data in the serie",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    protected ArrayList<String> dateLabel;

    // ---


    public List<TenantSetupStatsSerie> getSeries() {
        return series;
    }

    public void setSeries(List<TenantSetupStatsSerie> series) {
        this.series = series;
    }

    public ArrayList<String> getDateLabel() {
        return dateLabel;
    }

    public void setDateLabel(ArrayList<String> dateLabel) {
        this.dateLabel = dateLabel;
    }
}
