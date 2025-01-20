package eu.heliumiot.console.jpa.interfaces;

public class IHeliumDeviceChange {

    // The name of the Tenant
    String tenant_name;

    // The uuid of the tenant
    String tenant_uuid;

    // The number of lines concerned by the query
    int device_count;

    // ---

    // ---


    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public int getDevice_count() {
        return device_count;
    }

    public void setDevice_count(int device_count) {
        this.device_count = device_count;
    }

    public String getTenant_uuid() {
        return tenant_uuid;
    }

    public void setTenant_uuid(String tenant_uuid) {
        this.tenant_uuid = tenant_uuid;
    }
}
