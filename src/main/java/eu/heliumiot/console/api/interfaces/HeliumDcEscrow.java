package eu.heliumiot.console.api.interfaces;

public class HeliumDcEscrow {
    /*
        {
            "pubKey": "HdUJ4CVUp2tDj22UKtFTbd5A4fNmnFvZmH5fkrjxdRdX"
        }
     */
    public String pubKey;

    // ---

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }
}
