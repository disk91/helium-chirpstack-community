package eu.heliumiot.console.api.interfaces;

import java.util.ArrayList;

public class HeliumDcOuisData {

    protected ArrayList<HeliumDcOuisDataOuis> ouis;

    // ---


    public ArrayList<HeliumDcOuisDataOuis> getOuis() {
        return ouis;
    }

    public void setOuis(ArrayList<HeliumDcOuisDataOuis> ouis) {
        this.ouis = ouis;
    }
}
