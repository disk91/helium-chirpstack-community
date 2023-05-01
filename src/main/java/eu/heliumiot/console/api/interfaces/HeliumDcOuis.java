package eu.heliumiot.console.api.interfaces;

public class HeliumDcOuis {

    /*
        "ok": false,
        "message": "success",
        "error": null,
        "data": {
            "ouis": [
            ]
     */
    protected boolean ok;
    protected String message;
    protected String error;
    protected HeliumDcOuisData data;

    // ---


    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HeliumDcOuisData getData() {
        return data;
    }

    public void setData(HeliumDcOuisData data) {
        this.data = data;
    }
}
