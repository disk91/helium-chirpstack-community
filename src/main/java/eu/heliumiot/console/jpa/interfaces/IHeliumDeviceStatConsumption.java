package eu.heliumiot.console.jpa.interfaces;

public class IHeliumDeviceStatConsumption {

    // The name of the Tenant
    String tenant_name;

    // The uuid of the tenant
    String tenant_uuid;

    // The uuid of the device (devEUI)
    String device_uuid;

    // The number of DCs consumed since last stat commit
    int total_dc;

    // ---

    public IHeliumDeviceStatConsumption(String tenant_name, String tenant_uuid, String device_uuid, Long total_dc) {
        this.tenant_name = tenant_name;
        this.tenant_uuid = tenant_uuid;
        this.device_uuid = device_uuid;
        this.total_dc = total_dc.intValue();
    }

    // ---


    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getTenant_uuid() {
        return tenant_uuid;
    }

    public void setTenant_uuid(String tenant_uuid) {
        this.tenant_uuid = tenant_uuid;
    }

    public String getDevice_uuid() {
        return device_uuid;
    }

    public void setDevice_uuid(String device_uuid) {
        this.device_uuid = device_uuid;
    }

    public int getTotal_dc() {
        return total_dc;
    }

    public void setTotal_dc(int total_dc) {
        this.total_dc = total_dc;
    }
}
