package io.chirpstack.json.sub;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogEventContext {
    /*
        "context": {
                "deduplication_id": "dceb5eb8-...-f04834eea493"
        }
     */

    protected String deduplication_id;

    // ---


    public String getDeduplication_id() {
        return deduplication_id;
    }

    public void setDeduplication_id(String deduplication_id) {
        this.deduplication_id = deduplication_id;
    }
}
