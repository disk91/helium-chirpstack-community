package eu.heliumiot.console.etl.api.sub;

import fr.ingeniousthings.tools.ClonnableObject;
import io.swagger.v3.oas.annotations.media.Schema;

public class WitnessHistory implements ClonnableObject<WitnessHistory> {

    @Schema(
            description = "Time reference for this witness"
    )
    private long timeRef;           // corresponding to the hour of the history

    @Schema(
            description = "Total Witnesses during this period"
    )
    private int countWitnesses;     // number of witness during this period of time

    @Schema(
            description = "Total of Selected Witness during this period"
    )
    private int seletedWitness = 0;     // number of witness selected during this period of time

    // ---------

    public WitnessHistory clone() {
        WitnessHistory c = new WitnessHistory();
        c.setTimeRef(timeRef);
        c.setCountWitnesses(countWitnesses);
        c.setSeletedWitness(seletedWitness);
        return c;
    }


    // ---------


    public long getTimeRef() {
        return timeRef;
    }

    public void setTimeRef(long timeRef) {
        this.timeRef = timeRef;
    }

    public int getCountWitnesses() {
        return countWitnesses;
    }

    public void setCountWitnesses(int countWitnesses) {
        this.countWitnesses = countWitnesses;
    }

    public int getSeletedWitness() {
        return seletedWitness;
    }

    public void setSeletedWitness(int seletedWitness) {
        this.seletedWitness = seletedWitness;
    }
}
