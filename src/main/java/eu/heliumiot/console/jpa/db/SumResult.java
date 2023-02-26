package eu.heliumiot.console.jpa.db;

public class SumResult {
    Float sum;
    public SumResult(Float v) {
        this.sum = v;
    }

    public int getSum() {
        return this.sum.intValue();
    }
}
