/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2020.
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
package eu.heliumiot.console.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eu.heliumiot.console.jpa.db.*;
import eu.heliumiot.console.mqtt.MqttListener;
import eu.heliumiot.console.jpa.repository.ApplicationRepository;
import eu.heliumiot.console.jpa.repository.DeviceRepository;
import eu.heliumiot.console.jpa.repository.HeliumDeviceRepository;
import eu.heliumiot.console.mqtt.api.HeliumDeviceActDeactItf;
import eu.heliumiot.console.mqtt.api.HeliumDeviceStatItf;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.Now;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeliumDeviceService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private class LoRaWanCreds {
        public String deveui;
        public String appeui;

        public String tenantId;

        //

        public LoRaWanCreds( String deveui, String appeui, String tenantId) {
            this.deveui = deveui;
            this.appeui = appeui;
            this.tenantId = tenantId;
        }
    }

    @Autowired
    protected HeliumParameterService heliumParameterService;

    @Autowired
    protected DeviceRepository deviceRepository;

    @Autowired
    protected ApplicationRepository applicationRepository;

    @Autowired
    protected HeliumDeviceRepository  heliumDeviceRepository;

    @Autowired
    protected HeliumTenantService heliumTenantService;

    @Autowired
    protected HeliumDeviceStatService heliumDeviceStatService;

    @Autowired
    protected MqttListener mqttListener;

    protected ObjectMapper mapper;

    @PostConstruct
    private void initHeliumDeviceService() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
    }

    protected void reportBillingOnMqtt(HeliumDeviceStatItf i) {
        // need to process invoicing
        heliumTenantService.processDeviceInsertionActivityInactivity(i);

        // need to update stats
        try {
            mqttListener.publishMessage(
                    "helium/device/stats/" + i.getDeviceId(),
                    mapper.writeValueAsString(i),
                    2
            );
        } catch (Exception x) {
            log.error("Something went wrong with publishing on MQTT");
            log.error(x.getMessage());
        }
    }

    protected void reportDeviceActivationOnMqtt(HeliumDevice hdev) {
        HeliumDeviceActDeactItf i = new HeliumDeviceActDeactItf();
        i.setDeviceId(hdev.getDeviceEui());
        i.setAppEui(hdev.getApplicationEui());
        i.setTenantId(hdev.getTenantUUID());
        i.setTime(Now.NowUtcMs());
        i.setActivateDevice(true);
        i.setDeactivateDevice(false);
        try {
            mqttListener.publishMessage(
                    "helium/device/activate/" + i.getDeviceId(),
                    mapper.writeValueAsString(i),
                    2
            );
        } catch (Exception x) {
            log.error("Something went wrong with publishing on MQTT (2)");
            log.error(x.getMessage());
        }
    }

    protected void reportDeviceDeactivationOnMqtt(LoRaWanCreds cred) {
        HeliumDeviceActDeactItf i = new HeliumDeviceActDeactItf();
        i.setDeviceId(cred.deveui);
        i.setTenantId(cred.tenantId);
        i.setAppEui(cred.appeui);
        i.setTime(Now.NowUtcMs());
        i.setActivateDevice(false);
        i.setDeactivateDevice(true);
        try {
            mqttListener.publishMessage(
                    "helium/device/deactivate/" + i.getDeviceId(),
                    mapper.writeValueAsString(i),
                    2
            );
        } catch (Exception x) {
            log.error("Something went wrong with publishing on MQTT (3)");
            log.error(x.getMessage());
        }
    }



    /**
     * Search for new device added into the device table
     */
    @Scheduled(fixedRateString = "${helium.device.new.scanPeriod}", initialDelay = 5_000)
    private void scanNewDevicesJob() {
        long start = Now.NowUtcMs();
        log.debug("Running scanNewDevicesJob");
        HeliumParameter p = heliumParameterService.getParameter(HeliumParameterService.PARAM_DEVICE_LASTSCAN_TIME);
        List<Device> devs = deviceRepository.findDeviceByCreatedAtGreaterThanOrderByCreatedAtAsc(new Timestamp(p.getLongValue()));
        long lastCreated = 0;
        for ( Device dev : devs ) {
            String devEui=HexaConverters.byteToHexString(dev.getDevEui());
            // verify
            HeliumDevice hdev = heliumDeviceRepository.findOneHeliumDeviceByDeviceEui(devEui);
            if ( hdev != null ) continue;

            // new devices
            hdev = new HeliumDevice();
            hdev.setDeviceUUID(dev.getDevEui());
            hdev.setApplicationEui("");
            hdev.setDeviceEui(devEui);
            lastCreated = dev.getCreatedAt().getTime();
            hdev.setCreatedAt(lastCreated);
            hdev.setInsertedAt(Now.NowUtcMs());
            hdev.setDeletedAt(0);
            // we will compute lastSeen separately based on  tenant configuration, no need here, it'a
            // all about billing
            // long lastSeen = dev.getLastSeenAt().getTime();
            hdev.setLastSeen(0);
            hdev.setLastActivityInvoiced(lastCreated);
            hdev.setLastInactivityInvoiced(lastCreated);
            hdev.setState(HeliumDevice.DeviceState.INSERTED);
            hdev.setToUpdate(true);
            hdev.setApplicationUUID(dev.getApplicationId().toString());
            hdev.setTotalDCs(0);
            hdev.setTotalDCsAt(Now.ThisDayMidnightUtc(lastCreated));
            hdev.setTodayDCs(0);

            // find corresponding tenant
            Application a = applicationRepository.findOneApplicationById(dev.getApplicationId());
            if ( a != null ) {
                hdev.setTenantUUID(a.getTenantId().toString());
            } else {
                log.error("Device creation failed : the application "+dev.getApplicationId()+" does not exists");
                continue;
            }

            // save this
            heliumDeviceRepository.save(hdev);

            // need to process stats and invoicing
            HeliumTenantSetup ts = heliumTenantService.getHeliumTenantSetup(hdev.getTenantUUID());
            if ( ts != null ) {
                HeliumDeviceStatItf i = new HeliumDeviceStatItf();
                i.setTenantId(hdev.getTenantUUID());
                i.setDeviceId(hdev.getDeviceEui());
                i.setEmpty(false);
                i.setRegistrationDc(ts.getDcPerDeviceInserted());
                this.reportBillingOnMqtt(i);
            }

            // Declare on Nova Lab Router asynchronously
            this.reportDeviceActivationOnMqtt(hdev);

            log.info("scanNewDevicesJob - Add "+ hdev.getDeviceEui());
        }
        if ( lastCreated > 0 ) {
            p.setLongValue(lastCreated-1); // 1 ms before to make sure
            heliumParameterService.flushParameter(p);
        }
        log.debug("End Running scanNewDevicesJob - duration "+(Now.NowUtcMs()-start)+"ms");
    }

    /**
      Scan for devices removed from the database
     */
    @Scheduled(fixedRateString = "${helium.device.deleted.scanPeriod}", initialDelay = 30_000)
    private void scanDeletedDevicesJob() {
        // check if we have a difference (same table size, nothing new, nothing deleted)
        long deviceEntrySize = deviceRepository.count();
        long heliumDeviceEntrySize = heliumDeviceRepository.count();
        if ( heliumDeviceEntrySize <=  deviceEntrySize ) return;

        // search for devices not in deviceRepository
        long start = Now.NowUtcMs();
        log.debug("Running scanDeletedDevicesJob");
        ArrayList<LoRaWanCreds> toRemove = new ArrayList<>();
        synchronized (this) {
            List<HeliumDevice> hdevs = heliumDeviceRepository.findDeletedDevices();
            for (HeliumDevice hdev : hdevs) {
                hdev.setState(HeliumDevice.DeviceState.DELETED);
                hdev.setToUpdate(false);
                hdev.setDeletedAt(Now.NowUtcMs());
                // need to destroy the route on Nova router
                // due to synchronized, it seems better to delay the action
                LoRaWanCreds c = new LoRaWanCreds(
                        hdev.getDeviceEui(),
                        hdev.getApplicationEui(),
                        hdev.getTenantUUID()
                );
                toRemove.add(c);
                // save this
                heliumDeviceRepository.save(hdev);
                log.info("scanDeletedDevicesJob - Del " + hdev.getDeviceEui());
            }
        }
        // Call Helium API for updating
        for ( LoRaWanCreds c : toRemove ) {
            // Declare on Nova Lab Router asynchronously
            this.reportDeviceDeactivationOnMqtt(c);
        }
        log.debug("End Running scanDeletedDevicesJob - duration "+(Now.NowUtcMs()-start)+"ms");
    }

    // process par groupe de 100 devices dont le last state est ancien, par contre il va faloir etre blocking
    // Invoicing Inactive devices
    // search for devices w/o activity in the last 2 days
    // for each, get tenant config and process
    // process per group of 100 devices based on the lastInactivityCheck

    // Invoicing Active devices
    // search for all devices ... du coup autant traiter inactive et active en meme temps

    @Value("${helium.device.activation.scanPeriod}")
    private long activationScanPeriod;

    @Scheduled(fixedRateString = "${helium.device.activation.scanPeriod}", initialDelay = 120_000)
    private void deviceActivityJob() {
        long start = Now.NowUtcMs();
        long now = start;
        log.debug("Running deviceActivityJob");

        // make sure we process all the devices within 2 days
        long devices = heliumDeviceRepository.count();
        long toRead = devices / (2 * Now.ONE_FULL_DAY / this.activationScanPeriod);
        // get a minimum
        if (toRead < 50) toRead = 50;
        // monitor a maximum
        if (toRead > 500) {
            log.warn("deviceActivityJob - more than 500 devices to process by period of scan");
        }

        synchronized (this) {
            // search for device to check.
            List<HeliumDevice> toProceedDevices = heliumDeviceRepository.findHeliumDeviceToProcess((int) toRead);
            for (HeliumDevice hdev : toProceedDevices) {
                boolean invoicingReport = false;    // reporting invoicing needed

                HeliumDeviceStatItf i = new HeliumDeviceStatItf();
                i.setDeviceId(hdev.getDeviceEui());
                i.setTenantId(hdev.getTenantUUID());

                Device dev = deviceRepository.findOneDeviceByDevEui(hdev.getDeviceUUID());
                if (dev != null) {
                    Timestamp t = dev.getLastSeenAt();
                    long lastSeenDevice = t.getTime();
                    if (lastSeenDevice == 0) lastSeenDevice = dev.getCreatedAt().getTime();

                    HeliumTenantSetup hts = heliumTenantService.getHeliumTenantSetup(
                            hdev.getTenantUUID(), true, 50
                    );

                    if (hts.getInactivityBillingPeriodMs() > 0) {

                        // is inactive
                        if ((now - hts.getInactivityBillingPeriodMs()) > lastSeenDevice) {
                            // inactive device, now need to see if already invoiced for that period of time and
                            // count the number of periods since last calculation
                            long inactivityPeriod = (now - hdev.getLastInactivityInvoiced());
                            long inactivityPeriods = inactivityPeriod / hts.getInactivityBillingPeriodMs();
                            log.debug("Found " + inactivityPeriods + " periods of inactivity for device " + hdev.getDeviceEui());
                            if (inactivityPeriods > 0) {
                                hdev.setLastInactivityInvoiced(hdev.getLastActivityInvoiced() + inactivityPeriods * hts.getInactivityBillingPeriodMs());
                                hdev.setLastActivityInvoiced(hdev.getLastInactivityInvoiced() + inactivityPeriods * hts.getDcPerInactivityPeriod());
                                if (hts.getDcPerActivityPeriod() > 0) {
                                    i.setInactivityDc((int) inactivityPeriods * hts.getDcPerInactivityPeriod());
                                    invoicingReport = true;
                                }
                                hdev.setState(HeliumDevice.DeviceState.INACTIVE);
                            }
                        }

                    }

                    if (hts.getActivityBillingPeriodMs() > 0) {

                        // is active (should not be possible to be active & inactive but setup can be made like this so...
                        if ((now - hts.getActivityBillingPeriodMs()) < lastSeenDevice) {
                            // active device within the period of time
                            long activityPeriod = (now - hdev.getLastActivityInvoiced());
                            long activityPeriods = activityPeriod / hts.getActivityBillingPeriodMs();
                            log.debug("Found " + activityPeriods + " periods of activity for device " + hdev.getDeviceEui());
                            if (activityPeriods > 0) {
                                hdev.setLastActivityInvoiced(hdev.getLastActivityInvoiced() + activityPeriods * hts.getActivityBillingPeriodMs());
                                hdev.setLastInactivityInvoiced(lastSeenDevice);
                                if (hts.getDcPerActivityPeriod() > 0) {
                                    i.setActivityDc((int) activityPeriods * hts.getDcPerActivityPeriod());
                                    invoicingReport = true;
                                }
                                hdev.setState(HeliumDevice.DeviceState.ACTIVE);
                            }
                        }

                    }

                    // send stat and invoicing information
                    if (invoicingReport) {
                        i.setEmpty(false);
                        this.reportBillingOnMqtt(i);
                    }


                    // Update the total DCs ( device cache lifetime is 2h so better not including this)
                    // Data per days
                    if (hts.getMaxDcPerDevice() > 0) {

                        long startSearch = hdev.getTotalDCsAt() + Now.ONE_FULL_DAY;
                        long stopSearch = Now.TodayMidnightUtc() - Now.ONE_FULL_DAY;
                        if (startSearch <= stopSearch) {
                            List<HeliumDeviceStat> hds = heliumDeviceStatService.getDeviceStatsUnsafe(
                                    hdev.getDeviceEui(),
                                    hdev.getTenantUUID(),
                                    startSearch,
                                    stopSearch
                            );
                            // make sum
                            HeliumDeviceStat s = new HeliumDeviceStat();
                            long sumDcs = 0;
                            for (HeliumDeviceStat e : hds) {
                                sumDcs += e.getUplinkDc();
                                sumDcs += e.getDuplicateDc();
                                sumDcs += e.getDownlinkDc();
                                sumDcs += e.getInactivityDc();
                                sumDcs += e.getActivityDc();
                                sumDcs += e.getRegistrationDc();
                            }
                            hdev.setTotalDCs(hdev.getTotalDCs() + sumDcs);
                            hdev.setTotalDCsAt(stopSearch);

                            // Add current
                            HeliumDeviceStat c = heliumDeviceStatService.getCurrentDeviceStat(
                                    hdev.getDeviceEui(),
                                    hdev.getTenantUUID(),
                                    false
                            );
                            if (c != null) {
                                sumDcs = 0;
                                sumDcs += c.getUplinkDc();
                                sumDcs += c.getDuplicateDc();
                                sumDcs += c.getDownlinkDc();
                                sumDcs += c.getInactivityDc();
                                sumDcs += c.getActivityDc();
                                sumDcs += c.getRegistrationDc();
                                hdev.setTodayDCs(sumDcs);
                            }
                            // check if we need to deactivate the device
                            if ((hdev.getTotalDCs() + hdev.getTodayDCs()) > hts.getMaxDcPerDevice()) {
                                log.debug("deviceActivityJob - deactivate device (totalDCs) " + hdev.getDeviceEui());
                                hdev.setState(HeliumDevice.DeviceState.DEACTIVATED);
                                hdev.setToUpdate(false);
                                // Call Nova Api to deactivate the device asynchronously
                                this.reportDeviceDeactivationOnMqtt(
                                        new LoRaWanCreds(
                                                hdev.getDeviceEui(),
                                                hdev.getApplicationEui(),
                                                hdev.getTenantUUID()
                                        )
                                );
                            }

                        }
                    }
                    // Update the device DC limitation per period
                    if ((hdev.getState() == HeliumDevice.DeviceState.ACTIVE ||
                            hdev.getState() == HeliumDevice.DeviceState.INSERTED
                    ) &&
                            hts.getLimitDcRatePeriodMs() > 0 && hts.getLimitDcRatePerDevice() > 0
                    ) {

                        // calculate how many DCs stat we need for computing this
                        long stopSearch = Now.TodayMidnightUtc();
                        long startSearch = stopSearch - hts.getLimitDcRatePeriodMs();
                        startSearch = Now.ThisDayMidnightUtc(startSearch);
                        if (startSearch <= stopSearch) {
                            List<HeliumDeviceStat> hds = heliumDeviceStatService.getDeviceStatsUnsafe(
                                    hdev.getDeviceEui(),
                                    hdev.getTenantUUID(),
                                    startSearch,
                                    stopSearch
                            );
                            // make sum
                            HeliumDeviceStat s = new HeliumDeviceStat();
                            long sumDcs = 0;
                            for (HeliumDeviceStat e : hds) {
                                sumDcs += e.getUplinkDc();
                                sumDcs += e.getDuplicateDc();
                                sumDcs += e.getDownlinkDc();
                                sumDcs += e.getInactivityDc();
                                sumDcs += e.getActivityDc();
                                sumDcs += e.getRegistrationDc();
                            }
                            if (sumDcs > hts.getLimitDcRatePerDevice()) {
                                log.debug("deviceActivityJob - deactivate device (limitDCs) " + hdev.getDeviceEui());
                                hdev.setState(HeliumDevice.DeviceState.DEACTIVATED);
                                hdev.setToUpdate(false);
                                // Call Nova Api to deactivate the device asynchronously
                                this.reportDeviceDeactivationOnMqtt(
                                        new LoRaWanCreds(
                                                hdev.getDeviceEui(),
                                                hdev.getApplicationEui(),
                                                hdev.getTenantUUID()
                                        )
                                );
                            }
                        }
                        hdev.setLastSeen(now);
                        heliumDeviceRepository.save(hdev);
                    }
                }
            }
        }
        log.info("deviceActivityJob - processed in " + (Now.NowUtcMs() - start) + "ms");

    }


    public void processTenantDeactivation(String tenantID) {
        // @TODO - gestion de la desactivation des devices d'un tenant
        //  search devices for a tenant
        // deactivate all devices
        // state devient OUTOFDCs, update passe à false
        // pour les device actif seulement, inactif ou inserted uniquement




    }

    public void processTenantReactivation(String tenantID) {

        // @TODO - gestion de la reactivation des devices d'un tenant
        //  search devices for a tenant
        // reactive all devices
        // state devient INSERTED, update passe à true
        // il faut juste les outofDCS
        // il faut reseted les dates de calcul des actif / inactif en meme temps



    }

}
