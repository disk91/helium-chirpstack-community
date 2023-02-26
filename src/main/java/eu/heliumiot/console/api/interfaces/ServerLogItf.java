package eu.heliumiot.console.api.interfaces;

public class ServerLogItf {

    protected String serverName;
    protected String serverUrl;
    protected String serverVersion;
    protected long tenantNumber;
    protected long deviceNumber;
    protected long userNumber;

    // ---

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public long getTenantNumber() {
        return tenantNumber;
    }

    public void setTenantNumber(long tenantNumber) {
        this.tenantNumber = tenantNumber;
    }

    public long getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(long deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public long getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(long userNumber) {
        this.userNumber = userNumber;
    }
}
