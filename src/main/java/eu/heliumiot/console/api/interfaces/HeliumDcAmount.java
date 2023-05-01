package eu.heliumiot.console.api.interfaces;

public class HeliumDcAmount {
    /*
        {
          "dcAmount": 14246163
        }
     */
    public long dcAmount;

    // ---


    public long getDcAmount() {
        return dcAmount;
    }

    public void setDcAmount(long dcAmount) {
        this.dcAmount = dcAmount;
    }
}
