package eu.heliumiot.console.api.interfaces;

public class HeliumDcEscrow {
    /*
        {
            "escrowKey": "HdUJ4CVUp2tDj22UKtFTbd5A4fNmnFvZmH5fkrjxdRdX"
        }
     */
    public String escrowKey;

    // ---

    public String getEscrowKey() {
        return escrowKey;
    }

    public void setEscrowKey(String escrowKey) {
        this.escrowKey = escrowKey;
    }
}
