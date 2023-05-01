package eu.heliumiot.console.api.interfaces;

import java.util.ArrayList;

public class HeliumDcOuisDataOuis {

    /*
        "oui": 1,
        "owner": "13tyMLKRFYURNBQqLSqNJg9k41maP1A7Bh8QYxR13oWv7EnFooc",
        "payer": "112qB3YaH5bZkCnKA5uRH7tBtGNv2Y5B4smv1jsmvGUzgKT71QpE",
        "delegateKeys": [
            "112qB3YaH5bZkCnKA5uRH7tBtGNv2Y5B4smv1jsmvGUzgKT71QpE"
        ]
     */

    protected int oui;
    protected String owner;
    protected String payer;
    protected ArrayList<String> delegateKeys;

    // ---


    public int getOui() {
        return oui;
    }

    public void setOui(int oui) {
        this.oui = oui;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public ArrayList<String> getDelegateKeys() {
        return delegateKeys;
    }

    public void setDelegateKeys(ArrayList<String> delegateKeys) {
        this.delegateKeys = delegateKeys;
    }
}
