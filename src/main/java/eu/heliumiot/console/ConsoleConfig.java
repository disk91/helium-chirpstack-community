/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2024.
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 *    and associated documentation files (the "Software"), to deal in the Software without restriction,
 *    including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *    sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies or
 *    substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package eu.heliumiot.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
@PropertySource(value = {"file:${config.file}"}, ignoreResourceNotFound = true)
public class ConsoleConfig {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // =====================================
    // Application Misc
    // =====================================
    @Value("${helium.version}")
    private String version;

    @Value("${config.file}")
    private String configFile;

    @Value ("${helium.allows.signup.default}")
    private String heliumAllowsSignupDefault;

    @Value ("${helium.allows.signup}")
    private String heliumAllowsSignupExternal;

    @Value ("${helium.allows.activation.default}")
    private String heliumAllowsActivationDefault;

    @Value ("${helium.allows.activation:}")     // if not existing (: = default value)
    private String heliumAllowsActivationExternal;

    @Value ("${helium.device.new.scanPeriod}")
    private long heliumDeviceNewScanPeriod;

    @Value ("${helium.device.deleted.scanPeriod}")
    private long heliumDeviceDeletedScanPeriod;

    @Value ("${helium.oui.data.server}")
    private String heliumOuiDataServer;

    @Value ("${helium.migration.graceful.session.period:60000}")
    private long heliumMigrationGracefulSessionPeriod;


    @Value ("${helium.zone.detection.enable.default}")
    private boolean heliumZoneDetectionEnableDefault;

    @Value ("${helium.zone.detection.enable:false}")
    private String heliumZoneDetectionEnable;

    @Value ("${ingeniousthings.email.filter:}")
    private String ingeniousthingsEmailFilter;

    public long getHeliumMigrationGracefulSessionPeriod() {
        return heliumMigrationGracefulSessionPeriod;
    }

    public boolean getHeliumZoneDetectionEnable() {
        if (!heliumZoneDetectionEnable.isEmpty()) {
            return ( heliumZoneDetectionEnable.compareToIgnoreCase("true") == 0 );
        }
        return heliumZoneDetectionEnableDefault;
    }

    public String getVersion() {
        return version;
    }

    public String getConfigFile() {
        return configFile;
    }

    public String getHeliumAllowsSignupDefault() {
        return heliumAllowsSignupDefault;
    }

    public String getHeliumAllowsSignupExternal() {
        return heliumAllowsSignupExternal;
    }

    public boolean isHeliumAllowsSignup() {
        if (!getHeliumAllowsSignupExternal().isEmpty()) return Boolean.parseBoolean(getHeliumAllowsSignupExternal());
        return Boolean.parseBoolean(getHeliumAllowsSignupDefault());
    }

    public String getHeliumAllowsActivationDefault() {
        return heliumAllowsActivationDefault;
    }

    public String getHeliumAllowsActivationExternal() {
        return heliumAllowsActivationExternal;
    }

    public boolean isHeliumAllowsActivation() {
        if (!getHeliumAllowsActivationExternal().isEmpty()) return Boolean.parseBoolean(getHeliumAllowsActivationExternal());
        return Boolean.parseBoolean(getHeliumAllowsActivationDefault());
    }


    public long getHeliumDeviceNewScanPeriod() {
        return heliumDeviceNewScanPeriod;
    }

    public long getHeliumDeviceDeletedScanPeriod() {
        return heliumDeviceDeletedScanPeriod;
    }

    public String getHeliumOuiDataServer() {
        return heliumOuiDataServer;
    }

    public String getIngeniousthingsEmailFilter() {
        return ingeniousthingsEmailFilter;
    }

    // =====================================
    // BILLING
    // =====================================
    @Value ("${helium.billing.dcBalanceStop.default}")
    private String heliumBillingDcBalanceStopDefault;

    @Value ("${helium.billing.dcBalanceStop}")
    private String heliumBillingDcBalanceStopExternal;

    @Value ("${helium.billing.freeTenantDc.default}")
    private String heliumBillingFreeTenantDcDefault;


    @Value ("${helium.billing.freeTenantDc}")
    private String heliumBillingFreeTenantDcExternal;

    @Value ("${helium.billing.dcPer24BytesMessage.default}")
    private String heliumBillingDcPer24BytesMessageDefault;

    @Value ("${helium.billing.dcPer24BytesMessage}")
    private String heliumBillingDcPer24BytesMessageExternal;

    @Value ("${helium.billing.dcPer24BDuplicate.default}")
    private String heliumBillingDcPer24BDuplicateDefault;

    @Value ("${helium.billing.dcPer24BDuplicate}")
    private String heliumBillingDcPer24BDuplicateExternal;

    @Value ("${helium.billing.dcPer24BDownlink.default}")
    private String heliumBillingDcPer24BDownlinkDefault;

    @Value ("${helium.billing.dcPer24BDownlink}")
    private String heliumBillingDcPer24BDownlinkExternal;

    @Value ("${helium.billing.dcPerDeviceInserted.default}")
    private String heliumBillingDcPerDeviceInsertedDefault;

    @Value ("${helium.billing.dcPerDeviceInserted}")
    private String heliumBillingDcPerDeviceInsertedExternal;

    @Value ("${helium.billing.inactivityBillingPeriodMs.default}")
    private String heliumBillingInactivityBillingPeriodMsDefault;

    @Value ("${helium.billing.inactivityBillingPeriodMs}")
    private String heliumBillingInactivityBillingPeriodMsExternal;

    @Value ("${helium.billing.dcPerInactivityPeriod.default}")
    private String heliumBillingDcPerInactivityPeriodDefault;

    @Value ("${helium.billing.dcPerInactivityPeriod}")
    private String heliumBillingDcPerInactivityPeriodExternal;

    @Value ("${helium.billing.activityBillingPeriod.default}")
    private String heliumBillingActivityBillingPeriodDefault;

    @Value ("${helium.billing.activityBillingPeriod}")
    private String heliumBillingActivityBillingPeriodExternal;

    @Value ("${helium.billing.dcPerActivityPeriod.default}")
    private String heliumBillingDcPerActivityPeriodDefault;

    @Value ("${helium.billing.dcPerActivityPeriod}")
    private String heliumBillingDcPerActivityPeriodExternal;

    @Value ("${helium.billing.maxDcPerDevice.default}")
    private String heliumBillingMaxDcPerDeviceDefault;

    @Value ("${helium.billing.maxDcPerDevice}")
    private String heliumBillingMaxDcPerDeviceExternal;

    @Value ("${helium.billing.limitDcRatePerDevice.default}")
    private String heliumBillingLimitDcRatePerDeviceDefault;

    @Value ("${helium.billing.limitDcRatePerDevice}")
    private String heliumBillingLimitDcRatePerDeviceExternal;

    @Value ("${helium.billing.limitDcRatePeriod.default}")
    private String heliumBillingLimitDcRatePeriodDefault;

    @Value ("${helium.billing.limitDcRatePeriod}")
    private String heliumBillingLimitDcRatePeriodExternal;


    @Value ("${helium.billing.dc.price.default}")
    private String heliumBillingDcPriceDefault;

    @Value ("${helium.billing.dc.price}")
    private String heliumBillingDcPriceExternal;

    @Value ("${helium.billing.dc.min.amount.default}")
    private String heliumBillingDcMinAmountDefault;

    @Value ("${helium.billing.dc.min.amount}")
    private String heliumBillingDcMinAmountExternal;

    @Value ("${helium.billing.max.tenant.default}")
    private String heliumBillingMaxTenantDefault;

    @Value ("${helium.billing.max.tenant}")
    private String heliumBillingMaxTenantExternal;

    @Value ("${helium.billing.max.devices.default}")
    private String heliumBillingMaxDevicesDefault;

    @Value ("${helium.billing.max.devices}")
    private String heliumBillingMaxDevicesExternal;

    @Value ("${helium.billing.vat:2000}")
    private int heliumBillingVat;

    @Value ("${helium.billing.dcPerJoinRequest:0}")
    private int heliumBillingDcPerJoinRequest;

    @Value ("${helium.billing.maxJoinRequestDup:-1}")
    private int heliumBillingMaxJoinRequestDup;

    @Value ("${helium.billing.dcPerJoinAccept:0}")
    private int heliumBillingDcPerJoinAccept;

    @Value ("${helium.billing.dcPunishment:100}")
    private int heliumBillingDcPunishment;

    @Value ("helium.billing.vat.country:")
    private String heliumBillingVatCountry;

    @Value ("helium.billing.vat.message1:")
    private String heliumBillingVatMessage1;

    @Value ("helium.billing.vat.message2:")
    private String heliumBillingVatMessage2;

    @Value ("helium.billing.vat.message3:")
    private String heliumBillingVatMessage3;

    @Value ("helium.billing.vat.message4:")
    private String heliumBillingVatMessage4;

    @Value ("helium.billing.vat.message5:")
    private String heliumBillingVatMessage5;

    @Value ("helium.billing.vat.message6:")
    private String heliumBillingVatMessage6;

    @Value ("helium.billing.vat.message7:")
    private String heliumBillingVatMessage7;

    @Value ("helium.billing.vat.message8:")
    private String heliumBillingVatMessage8;

    @Value ("helium.billing.vat.message9:")
    private String heliumBillingVatMessage9;


    public int getHeliumBillingDcPunishment() {
        return heliumBillingDcPunishment;
    }

    public int getHeliumBillingVat() {
        return heliumBillingVat;
    }

    public String getHeliumBillingDcBalanceStopDefault() {
        return heliumBillingDcBalanceStopDefault;
    }

    public String getHeliumBillingDcBalanceStopExternal() {
        return heliumBillingDcBalanceStopExternal;
    }

    public String getHeliumBillingDcPer24BytesMessageDefault() {
        return heliumBillingDcPer24BytesMessageDefault;
    }

    public String getHeliumBillingDcPer24BytesMessageExternal() {
        return heliumBillingDcPer24BytesMessageExternal;
    }

    public String getHeliumBillingDcPer24BDuplicateDefault() {
        return heliumBillingDcPer24BDuplicateDefault;
    }

    public String getHeliumBillingDcPer24BDuplicateExternal() {
        return heliumBillingDcPer24BDuplicateExternal;
    }

    public String getHeliumBillingDcPer24BDownlinkDefault() {
        return heliumBillingDcPer24BDownlinkDefault;
    }

    public String getHeliumBillingDcPer24BDownlinkExternal() {
        return heliumBillingDcPer24BDownlinkExternal;
    }

    public String getHeliumBillingDcPerDeviceInsertedDefault() {
        return heliumBillingDcPerDeviceInsertedDefault;
    }

    public String getHeliumBillingDcPerDeviceInsertedExternal() {
        return heliumBillingDcPerDeviceInsertedExternal;
    }

    public String getHeliumBillingInactivityBillingPeriodMsDefault() {
        return heliumBillingInactivityBillingPeriodMsDefault;
    }

    public String getHeliumBillingInactivityBillingPeriodMsExternal() {
        return heliumBillingInactivityBillingPeriodMsExternal;
    }

    public String getHeliumBillingDcPerInactivityPeriodDefault() {
        return heliumBillingDcPerInactivityPeriodDefault;
    }

    public String getHeliumBillingDcPerInactivityPeriodExternal() {
        return heliumBillingDcPerInactivityPeriodExternal;
    }

    public String getHeliumBillingActivityBillingPeriodDefault() {
        return heliumBillingActivityBillingPeriodDefault;
    }

    public String getHeliumBillingActivityBillingPeriodExternal() {
        return heliumBillingActivityBillingPeriodExternal;
    }

    public String getHeliumBillingDcPerActivityPeriodDefault() {
        return heliumBillingDcPerActivityPeriodDefault;
    }

    public String getHeliumBillingDcPerActivityPeriodExternal() {
        return heliumBillingDcPerActivityPeriodExternal;
    }

    public String getHeliumBillingFreeTenantDcDefault() {
        return heliumBillingFreeTenantDcDefault;
    }

    public String getHeliumBillingFreeTenantDcExternal() {
        return heliumBillingFreeTenantDcExternal;
    }

    public String getHeliumBillingMaxDcPerDeviceDefault() {
        return heliumBillingMaxDcPerDeviceDefault;
    }

    public String getHeliumBillingLimitDcRatePerDeviceDefault() {
        return heliumBillingLimitDcRatePerDeviceDefault;
    }

    public String getHeliumBillingLimitDcRatePeriodDefault() {
        return heliumBillingLimitDcRatePeriodDefault;
    }

    public String getHeliumBillingMaxDcPerDeviceExternal() {
        return heliumBillingMaxDcPerDeviceExternal;
    }

    public String getHeliumBillingLimitDcRatePerDeviceExternal() {
        return heliumBillingLimitDcRatePerDeviceExternal;
    }

    public String getHeliumBillingLimitDcRatePeriodExternal() {
        return heliumBillingLimitDcRatePeriodExternal;
    }

    public boolean isHeliumZoneDetectionEnableDefault() {
        return heliumZoneDetectionEnableDefault;
    }

    public int getHeliumBillingDcPerJoinRequest() {
        return heliumBillingDcPerJoinRequest;
    }

    public int getHeliumBillingMaxJoinRequestDup() {
        return heliumBillingMaxJoinRequestDup;
    }

    public int getHeliumBillingDcPerJoinAccept() {
        return heliumBillingDcPerJoinAccept;
    }

    public boolean isStatReportEnableDefault() {
        return statReportEnableDefault;
    }

    public String getStatReportEnable() {
        return statReportEnable;
    }

    public int getHeliumBillingMaxDcPerDevice() {
        if (!getHeliumBillingMaxDcPerDeviceExternal().isEmpty()) return Integer.parseInt(getHeliumBillingMaxDcPerDeviceExternal());
        return Integer.parseInt(getHeliumBillingMaxDcPerDeviceDefault());
    }

    public int getHeliumBillingLimitDcRatePerDevice() {
        if (!getHeliumBillingLimitDcRatePerDeviceExternal().isEmpty()) return Integer.parseInt(getHeliumBillingLimitDcRatePerDeviceExternal());
        return Integer.parseInt(getHeliumBillingLimitDcRatePerDeviceDefault());
    }

    public long getHeliumBillingLimitDcRatePeriod() {
        if (!getHeliumBillingLimitDcRatePeriodExternal().isEmpty()) return Integer.parseInt(getHeliumBillingLimitDcRatePeriodExternal());
        return Long.parseLong(getHeliumBillingLimitDcRatePeriodDefault());
    }

    public int getHeliumBillingFreeTenantDc() {
        if (!getHeliumBillingFreeTenantDcExternal().isEmpty()) return Integer.parseInt(getHeliumBillingFreeTenantDcExternal());
        return Integer.parseInt(getHeliumBillingFreeTenantDcDefault());
    }

    public int getHeliumBillingDcBalanceStop() {
        if (!getHeliumBillingDcBalanceStopExternal().isEmpty()) return Integer.parseInt(getHeliumBillingDcBalanceStopExternal());
        return Integer.parseInt(getHeliumBillingDcBalanceStopDefault());
    }

    public int getHeliumBillingDcPer24BytesMessage() {
        if (!getHeliumBillingDcPer24BytesMessageExternal().isEmpty()) return Integer.parseInt(getHeliumBillingDcPer24BytesMessageExternal());
        return Integer.parseInt(getHeliumBillingDcPer24BytesMessageDefault());
    }

    public int getHeliumBillingDcPer24BDuplicate() {
        if (!getHeliumBillingDcPer24BDuplicateExternal().isEmpty()) return Integer.parseInt(getHeliumBillingDcPer24BDuplicateExternal());
        return Integer.parseInt(getHeliumBillingDcPer24BDuplicateDefault());
    }

    public int getHeliumBillingDcPer24BDownlink() {
        if (!getHeliumBillingDcPer24BDownlinkExternal().isEmpty()) return Integer.parseInt(getHeliumBillingDcPer24BDownlinkExternal());
        return Integer.parseInt(getHeliumBillingDcPer24BDownlinkDefault());
    }


    public int getHeliumBillingDcPerDeviceInserted() {
        if (!getHeliumBillingDcPerDeviceInsertedExternal().isEmpty()) return Integer.parseInt(getHeliumBillingDcPerDeviceInsertedExternal());
        return Integer.parseInt(getHeliumBillingDcPerDeviceInsertedDefault());
    }


    public long getHeliumBillingInactivityBillingPeriodMs() {
        if (!getHeliumBillingInactivityBillingPeriodMsExternal().isEmpty()) return Long.parseLong(getHeliumBillingInactivityBillingPeriodMsExternal());
        return Long.parseLong(getHeliumBillingInactivityBillingPeriodMsDefault());
    }


    public int getHeliumBillingDcPerInactivityPeriod() {
        if (!getHeliumBillingDcPerInactivityPeriodExternal().isEmpty()) return Integer.parseInt(getHeliumBillingDcPerInactivityPeriodExternal());
        return Integer.parseInt(getHeliumBillingDcPerInactivityPeriodDefault());
    }


    public long getHeliumBillingActivityBillingPeriod() {
        if (!getHeliumBillingActivityBillingPeriodExternal().isEmpty()) return Long.parseLong(getHeliumBillingActivityBillingPeriodExternal());
        return Long.parseLong(getHeliumBillingActivityBillingPeriodDefault());
    }


    public int getHeliumBillingDcPerActivityPeriod() {
        if (!getHeliumBillingDcPerActivityPeriodExternal().isEmpty()) return Integer.parseInt(getHeliumBillingDcPerActivityPeriodExternal());
        return Integer.parseInt(getHeliumBillingDcPerActivityPeriodDefault());
    }

    public String getHeliumBillingDcPriceDefault() {
        return heliumBillingDcPriceDefault;
    }

    public String getHeliumBillingDcPriceExternal() {
        return heliumBillingDcPriceExternal;
    }

    public String getHeliumBillingDcMinAmountDefault() {
        return heliumBillingDcMinAmountDefault;
    }

    public String getHeliumBillingDcMinAmountExternal() {
        return heliumBillingDcMinAmountExternal;
    }

    public String getHeliumBillingMaxTenantDefault() {
        return heliumBillingMaxTenantDefault;
    }

    public String getHeliumBillingMaxTenantExternal() {
        return heliumBillingMaxTenantExternal;
    }

    public String getHeliumBillingMaxDevicesDefault() {
        return heliumBillingMaxDevicesDefault;
    }

    public String getHeliumBillingMaxDevicesExternal() {
        return heliumBillingMaxDevicesExternal;
    }

    public double getHeliumBillingDcPrice() {
        if (!getHeliumBillingDcPriceExternal().isEmpty()) return Double.parseDouble(getHeliumBillingDcPriceExternal());
        return Double.parseDouble(getHeliumBillingDcPriceDefault());
    }

    public long getHeliumBillingDcMinAmount() {
        if (!getHeliumBillingDcMinAmountExternal().isEmpty()) return Long.parseLong(getHeliumBillingDcMinAmountExternal());
        return Long.parseLong(getHeliumBillingDcMinAmountDefault());
    }

    public int getHeliumBillingMaxTenant() {
        if (!getHeliumBillingMaxTenantExternal().isEmpty()) return Integer.parseInt(getHeliumBillingMaxTenantExternal());
        return Integer.parseInt(getHeliumBillingMaxTenantDefault());
    }

    public int getHeliumBillingMaxDevices() {
        if (!getHeliumBillingMaxDevicesExternal().isEmpty()) return Integer.parseInt(getHeliumBillingMaxDevicesExternal());
        return Integer.parseInt(getHeliumBillingMaxDevicesDefault());
    }

    public String getHeliumBillingVatCountry() {
        return heliumBillingVatCountry;
    }

    public String getHeliumBillingVatMessage1() {
        return heliumBillingVatMessage1;
    }

    public String getHeliumBillingVatMessage2() {
        return heliumBillingVatMessage2;
    }

    public String getHeliumBillingVatMessage3() {
        return heliumBillingVatMessage3;
    }

    public String getHeliumBillingVatMessage4() {
        return heliumBillingVatMessage4;
    }

    public String getHeliumBillingVatMessage5() {
        return heliumBillingVatMessage5;
    }

    public String getHeliumBillingVatMessage6() {
        return heliumBillingVatMessage6;
    }

    public String getHeliumBillingVatMessage7() {
        return heliumBillingVatMessage7;
    }

    public String getHeliumBillingVatMessage8() {
        return heliumBillingVatMessage8;
    }

    public String getHeliumBillingVatMessage9() {
        return heliumBillingVatMessage9;
    }

    // =====================================
    // MQTT
    // =====================================
    @Value("${mqtt.server}")
    private String mqttServerExternal;
    protected String getMqttServerExternal() {
        return mqttServerExternal;
    }

    @Value("${mqtt.login}")
    private String mqttLoginExternal;
    protected String getMqttLoginExternal() {
        return mqttLoginExternal;
    }

    @Value("${mqtt.password}")
    private String mqttPasswordExternal;
    protected String getMqttPasswordExternal() {
        return mqttPasswordExternal;
    }

    @Value("${mqtt.id}")
    private String mqttIdExternal;
    protected String getMqttIdExternal() {
        return mqttIdExternal;
    }

    @Value("${mqtt.server.default}")
    private String mqttServerDefault;
    protected String getMqttServerDefault() {
        return mqttServerDefault;
    }

    @Value("${mqtt.login.default}")
    private String mqttLoginDefault;
    public String getMqttLoginDefault() {
        return mqttLoginDefault;
    }

    @Value("${mqtt.password.default}")
    private String mqttPasswordDefault;
    public String getMqttPasswordDefault() {
        return mqttPasswordDefault;
    }

    @Value("${mqtt.id.default}")
    private String mqttIdDefault;
    public String getMqttIdDefault() {
        return mqttIdDefault;
    }

    public String getMqttServer() {
        if (!this.getMqttServerExternal().isEmpty()) return this.getMqttServerExternal();
        return this.getMqttServerDefault();
    }
    public String getMqttId() {
        if (!this.getMqttIdExternal().isEmpty()) return this.getMqttIdExternal();
        return this.getMqttIdDefault();
    }
    public String getMqttLogin() {
        if (!this.getMqttLoginExternal().isEmpty()) return this.getMqttLoginExternal();
        return this.getMqttLoginDefault();
    }
    public String getMqttPassword() {
        if (!this.getMqttPasswordExternal().isEmpty()) return this.getMqttPasswordExternal();
        return this.getMqttPasswordDefault();
    }


    // =============================
    // GPRC / Nova API
    // =============================


    @Value ("${helium.grpc.enable}")
    private boolean heliumGrpcEnable;

    @Value ("${helium.grpc.private.keyfile.path}")
    private String heliumGrpcPrivateKeyfilePath;

    @Value ("${helium.gprc.server}")
    private String heliumGrpcServerExternal;

    @Value ("${helium.gprc.server.default}")
    private String heliumGrpcServerDefault;

    @Value ("${helium.grpc.port}")
    private String heliumGrpcPortExternal;

    @Value ("${helium.grpc.port.default}")
    private String heliumGrpcPortDefault;

    @Value ("${helium.grpc.public.key}")
    private String heliumGprcPublicKey;

    @Value ("${helium.route.oui}")
    private int heliumRouteOui;

    @Value ("${helium.route.netid}")
    private String heliumRouteNetid;

    @Value ("${helium.route.host}")
    private String heliumRouteHost;

    @Value ("${helium.route.regions}")
    private String heliumRouteRegions;

    @Value ("${helium.route.copy.max}")
    private int heliumRouteMaxCopy;

    @Value ("${helium.route.copy.default}")
    private int heliumRouteDefaultCopy;

    @Value ("${helium.grpc.skf.enable}")
    private boolean heliumGrpcSkfEnable;

    @Value ("${helium.route.reject.empty.skf:false}")
    private boolean heliumRouteRejectEmptySKF;

    public boolean isHeliumRouteRejectEmptySKF() {
        return heliumRouteRejectEmptySKF;
    }

    public boolean isHeliumGrpcSkfEnable() {
        return heliumGrpcSkfEnable;
    }

    public boolean isHeliumGrpcEnable() {
        return heliumGrpcEnable;
    }

    public int getHeliumRouteMaxCopy() {
        return heliumRouteMaxCopy;
    }

    public int getHeliumRouteDefaultCopy() {
        return heliumRouteDefaultCopy;
    }

    public String getHeliumRouteRegions() {
        return heliumRouteRegions;
    }

    public String getHeliumRouteNetid() {
        return heliumRouteNetid;
    }

    public String getHeliumRouteHost() {
        return heliumRouteHost;
    }

    public int getHeliumRouteOui() {
        return heliumRouteOui;
    }

    public String getHeliumGprcPublicKey() {
        return heliumGprcPublicKey;
    }

    public String getHeliumGrpcPrivateKeyfilePath() {
        return heliumGrpcPrivateKeyfilePath;
    }

    public String getHeliumGrpcServerExternal() {
        return heliumGrpcServerExternal;
    }

    public String getHeliumGrpcServerDefault() {
        return heliumGrpcServerDefault;
    }

    public String getHeliumGrpcPortExternal() {
        return heliumGrpcPortExternal;
    }

    public String getHeliumGrpcPortDefault() {
        return heliumGrpcPortDefault;
    }

    public String getHeliumGrpcServer() {
        if (!this.getHeliumGrpcServerExternal().isEmpty()) return this.getHeliumGrpcServerExternal();
        return this.getHeliumGrpcServerDefault();
    }

    public int getHeliumGrpcPort() {
        if (!this.getHeliumGrpcPortExternal().isEmpty()) return Integer.parseInt(this.getHeliumGrpcPortExternal());
        return Integer.parseInt(this.getHeliumGrpcPortDefault());
    }

    // =========================================
    // EMAIL CONFIG
    // =========================================
    @Value ("${helium.mail.from}")
    private String heliumMailFrom;

    @Value ("${helium.console.url}")
    private String heliumConsoleUrl;

    @Value ("${helium.console.name}")
    private String heliumConsoleName;


    public String getHeliumMailFrom() {
        return heliumMailFrom;
    }

    public String getHeliumConsoleUrl() {
        return heliumConsoleUrl;
    }

    public String getHeliumConsoleName() {
        return heliumConsoleName;
    }

    // =========================================
    // Chirpstack / Console API
    // =========================================
    @Value ("${chirpstack.api.base}")
    private String chirpstackApiBaseExternal;

    @Value ("${chirpstack.api.base.default}")
    private String chirpstackApiBaseDefault;

    @Value ("${chirpstack.api.admin.key}")
    private String chirpstackApiAdminKey;

    @Value ("${chirpstack.dedup.delay.ms:350}")
    private int chirpstackDedupDelayMs;

    @Value ("${helium.jwt.signature.key.default}")
    private String jwtSignatureKeyDefault;

    @Value ("${helium.jwt.signature.key}")
    private String jwtSignatureKeyExternal;

    public String getChirpstackApiAdminKey() {
        return chirpstackApiAdminKey;
    }

    public String getChirpstackApiBase() {
        if (!this.getChirpstackApiBaseExternal().isEmpty()) return this.getChirpstackApiBaseExternal();
        return getChirpstackApiBaseDefault();
    }

    public String getChirpstackApiBaseExternal() {
        return chirpstackApiBaseExternal;
    }

    public String getChirpstackApiBaseDefault() {
        return chirpstackApiBaseDefault;
    }

    public String getJwtSignatureKey() {
        if (!this.getJwtSignatureKeyExternal().isEmpty() && this.getJwtSignatureKeyExternal().length() != 64) {
            log.error("helium.jwt.signature.key format is invalid, must be 64 char. Back to default");
        }
        if (this.getJwtSignatureKeyExternal().length() == 64) return this.getJwtSignatureKeyExternal();
        return getJwtSignatureKeyDefault();
    }

    public String getJwtSignatureKeyDefault() {
        return jwtSignatureKeyDefault;
    }

    public String getJwtSignatureKeyExternal() {
        return jwtSignatureKeyExternal;
    }

    public int getChirpstackDedupDelayMs() {
        return chirpstackDedupDelayMs;
    }

    // ==========================================
    // Stripe
    // ==========================================

    @Value ("${helium.stripe.key.private}")
    private String stripeKeyPrivateExternal;

    @Value ("${helium.stripe.key.private.default}")
    private String stripeKeyPrivateDefault;

    @Value ("${helium.stripe.key.public}")
    private String stripeKeyPublicExternal;

    @Value ("${helium.stripe.key.public.default}")
    private String stripeKeyPublicDefault;

    @Value ("${helium.stripe.currency.default}")
    private String stripeCurrencyDefault;

    @Value ("${helium.stripe.enable}")
    private boolean stripeEnable;

    @Value ("${helium.transfer.enable}")
    private boolean transferEnable;

    public boolean isStripeEnable() {
        return stripeEnable;
    }

    public boolean isTransferEnable() {
        return transferEnable;
    }

    public String getStripeKeyPrivateExternal() {
        return stripeKeyPrivateExternal;
    }

    public String getStripeKeyPrivateDefault() {
        return stripeKeyPrivateDefault;
    }

    public String getStripeKeyPublicExternal() {
        return stripeKeyPublicExternal;
    }

    public String getStripeKeyPublicDefault() {
        return stripeKeyPublicDefault;
    }

    public String getStripeCurrencyDefault() {
        return stripeCurrencyDefault;
    }

    public String getStripeKeyPrivate() {
        if (!this.getStripeKeyPrivateExternal().isEmpty()) return this.getStripeKeyPrivateExternal();
        return getStripeKeyPrivateDefault();
    }

    public String getStripeKeyPublic() {
        if (!this.getStripeKeyPublicExternal().isEmpty()) return this.getStripeKeyPublicExternal();
        return getStripeKeyPublicDefault();
    }

    public String getStripeCurrency() {
        // Until we make it modifiable for each instance, but it need to change many things in the Front also
        return getStripeCurrencyDefault();
    }

    // ==============================
    // Monitoring
    // ==============================

    @Value ("${helium.testdevice.eui}")
    private String testdeviceEui;

    @Value ("${helium.testdevice.eui.default}")
    private String testdeviceEuiDefault;

    @Value ("${helium.stats.report.enable.default}")
    private boolean statReportEnableDefault;

    @Value ("${helium.stats.report.enable:}")
    private String statReportEnable;

    @Value ("${helium.oui.dc.alarm:4000000}")
    private int heliumOuiDcAlarm;

    @Value ("${helium.tenant.dc.warn:30000}")
    private int heliumTenantDcWarn;

    @Value ("${helium.tenant.dc.alarm:10000}")
    private int heliumTenantDcAlarm;


    public boolean isStatReportEnable() {
        if (!statReportEnable.isEmpty()) {
            if ( statReportEnable.compareToIgnoreCase("true") == 0 ) return true;
            return false;
        } else return statReportEnableDefault;
    }

    public String getTestdeviceEuiExternal() {
        return testdeviceEui;
    }

    public String getTestdeviceEuiDefault() {
        return testdeviceEuiDefault;
    }

    public String getTestdeviceEui() {
        if (!this.getTestdeviceEuiExternal().isEmpty()) return this.getTestdeviceEuiExternal();
        return getTestdeviceEuiDefault();
    }

    public int getHeliumOuiDcAlarm() {
        return heliumOuiDcAlarm;
    }

    public int getHeliumTenantDcWarn() {
        return heliumTenantDcWarn;
    }

    public int getHeliumTenantDcAlarm() {
        return heliumTenantDcAlarm;
    }
}
