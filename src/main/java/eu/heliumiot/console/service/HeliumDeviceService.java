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
package eu.heliumiot.console.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.jpa.db.*;
import eu.heliumiot.console.jpa.repository.*;
import eu.heliumiot.console.mqtt.MqttSender;
import eu.heliumiot.console.mqtt.api.HeliumDeviceActDeactItf;
import eu.heliumiot.console.mqtt.api.HeliumDeviceStatItf;
import eu.heliumiot.console.service.interfaces.LogEntry;
import eu.heliumiot.console.service.interfaces.LogLevel;
import fr.ingeniousthings.tools.DateConverters;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.Now;
import fr.ingeniousthings.tools.Tools;
import io.chirpstack.internal.DeviceSession;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class HeliumDeviceService {

    @Autowired
    private LogService logService;

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
    protected HeliumTenantSetupService heliumTenantSetupService;

    @Autowired
    protected TenantRepository tenantRepository;

    @Autowired
    protected HeliumDeviceStatService heliumDeviceStatService;

    @Autowired
    protected MqttSender mqttSender;

    protected ObjectMapper mapper;
    protected int runningJobs;
    protected boolean serviceEnable; // false to stop the services

    @PostConstruct
    private void initHeliumDeviceService() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        runningJobs=0;
        serviceEnable=true;
    }


    // request to stop the service properly
    public void stopService() {
        this.serviceEnable = false;
    }

    // return true when the service has stopped all the running jobs
    public boolean hasStopped() {
        return (!this.serviceEnable && this.runningJobs == 0);
    }


    public void reportDeviceActivationOnMqtt(String devEui) {
        HeliumDevice hdev = this.heliumDeviceCacheService.getHeliumDevice(devEui);
        if ( hdev != null && ( Now.NowUtcMs() - hdev.getCreatedAt() ) > 30_000 ) {
            switch ( hdev.getState() ) {
                default:
                case INSERTED:
                case ACTIVE:
                case INACTIVE:
                    this.reportDeviceActivationOnMqtt(hdev);
                    break;

                case DEACTIVATED:
                case OUTOFDCS:
                case DELETED:
                case DISABLED:
                    break;
            }
        } else {
            // test if the device exist and it's age
            Device d = deviceRepository.findOneDeviceByDevEui(Tools.EuiStringToByteArray(devEui));
            if ( d == null ) {
                log.error("Got a device activation request for not existing device ({})", devEui);
            } else if ( d.getCreatedAt().getTime() > (Now.NowUtcMs() - 60_000) ) {
                log.warn("Got a device activation request for an existing device ({}) w/o helium dev created ? ({})", DateConverters.msToStringDate(d.getCreatedAt().getTime()), devEui);
            } // normal behavior for new devices ...
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
            mqttSender.publishMessage(
                    "helium/device/activate/" + i.getDeviceId(),
                    mapper.writeValueAsString(i),
                    2
            );
            this.prometeusService.addDelayedStatUpdate();
        } catch (Exception x) {
            this.prometeusService.addMqttConnectionLoss();
            log.error("Something went wrong with publishing on MQTT (2)");
            log.error(x.getMessage());
        }
    }

    @Autowired
    protected PrometeusService prometeusService;

    protected void reportDeviceDeactivationOnMqtt(LoRaWanCreds cred) {
        HeliumDeviceActDeactItf i = new HeliumDeviceActDeactItf();
        i.setDeviceId(cred.deveui);
        i.setTenantId(cred.tenantId);
        i.setAppEui(cred.appeui);
        i.setTime(Now.NowUtcMs());
        i.setActivateDevice(false);
        i.setDeactivateDevice(true);
        try {
            mqttSender.publishMessage(
                    "helium/device/deactivate/" + i.getDeviceId(),
                    mapper.writeValueAsString(i),
                    2
            );
            this.prometeusService.addDelayedStatUpdate();

        } catch (Exception x) {
            this.prometeusService.addMqttConnectionLoss();
            log.error("Something went wrong with publishing on MQTT (3)");
            log.error(x.getMessage());
        }
    }


    @Autowired
    protected ConsoleConfig consoleConfig;

    @Autowired
    protected DeviceService deviceService;

    /**
     * Search for new device added into the device table
     */
    @Scheduled(fixedRateString = "${helium.device.new.scanPeriod}", initialDelay = 5_000) // default : 30s
    private void scanNewDevicesJob() {
        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        long start = Now.NowUtcMs();
        try {
            log.debug("Running scanNewDevicesJob");
            HeliumParameter p = heliumParameterService.getParameter(HeliumParameterService.PARAM_DEVICE_LASTSCAN_TIME);
            List<Device> devs = deviceRepository.findDeviceByCreatedAtGreaterThanOrderByCreatedAtAsc(
                    new Timestamp(p.getLongValue()-(    // as we can have 2 loops of NewScan and a loop for deleted = max
                            consoleConfig.getHeliumDeviceNewScanPeriod()+
                            consoleConfig.getHeliumDeviceDeletedScanPeriod()+
                            consoleConfig.getHeliumMigrationGracefulSessionPeriod()+
                            10_000
                    )));
            long lastCreated = 0;
            for (Device dev : devs) {
                String devEui = HexaConverters.byteToHexString(dev.getDevEui());
                // verify
                HeliumDevice hdev = heliumDeviceRepository.findOneHeliumDeviceByDeviceEui(devEui);
                if ( hdev != null ) {
                    // this device already exist
                    if ( hdev.getState() != HeliumDevice.DeviceState.DELETED ) {
                        // make sure this device is not older than the one created
                        if ( (dev.getCreatedAt().getTime() - hdev.getCreatedAt() ) > 20_000 ) {
                            // Helium device has been created before Chirp device
                            // this is a device not yet removed... do it
                            synchronized (this) {
                                hdev.setState(HeliumDevice.DeviceState.DELETED);
                                hdev.setToUpdate(false);
                                hdev.setDeletedAt(Now.NowUtcMs());
                                LoRaWanCreds c = new LoRaWanCreds(
                                        hdev.getDeviceEui(),
                                        hdev.getApplicationEui(),
                                        hdev.getTenantUUID()
                                );
                                this.reportDeviceDeactivationOnMqtt(c);
                                // save this
                                heliumDeviceRepository.save(hdev);
                            }
                            log.debug("scanNewDevicesJob - Force Del {}", hdev.getDeviceEui());
                            prometeusService.addDeviceDeletion();
                        } else continue;
                    } else {
                        // this device is deleted, is that a reactivation ?
                        // make sure other cleaning activities are done
                        // let the cleaner job to pass
                        if ((Now.NowUtcMs() - hdev.getDeletedAt()) < consoleConfig.getHeliumDeviceDeletedScanPeriod())
                            continue;
                    }

                    // remove that previous device
                    hdev.setDeviceEui(hdev.getDeviceEui()+"_del_"+Now.NowUtcS());
                    heliumDeviceRepository.save(hdev);
                }

                // new devices but to make sure we had time to complete the
                // device creation on chirpstack, including the session
                // configuration, it's better to not process a device too fast
                // when it comes from migration process, lets make it for all now
                if ( dev.getVariables().contains("migrated\": \"true") ) {
                    DeviceSession s = deviceService.getDeviceSession(dev.getDevEui());
                    if ( s == null ) {
                        log.debug("scanNewDevicesJob - Session for {} not ready", devEui);
                        // wait a minute
                        if ( (start - dev.getCreatedAt().getTime()) < consoleConfig.getHeliumMigrationGracefulSessionPeriod() ) {
                            log.debug("scanNewDevicesJob - skip device {}", devEui);
                            continue;
                        }
                    } else {
                        log.debug("scanNewDevicesJob - Device {} is ready to process", devEui);
                    }
                }

                // new devices
                hdev = new HeliumDevice();
                hdev.setDeviceUUID(dev.getDevEui());
                String appEui = dev.getAppEui();
                hdev.setApplicationEui("");
                if ( appEui != null ) hdev.setApplicationEui(appEui);
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
                if (a != null) {
                    hdev.setTenantUUID(a.getTenantId().toString());
                } else {
                    log.error("Device creation failed : the application {} does not exists", dev.getApplicationId());
                    continue;
                }

                // save this
                heliumDeviceRepository.save(hdev);

                // need to process stats and invoicing
                HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(hdev.getTenantUUID(),false);
                if (ts != null) {
                    HeliumDeviceStatItf i = new HeliumDeviceStatItf();
                    i.setTenantId(hdev.getTenantUUID());
                    i.setDeviceId(hdev.getDeviceEui());
                    i.setRegistrationDc(ts.getDcPerDeviceInserted());
                    heliumTenantService.processDeviceInsertionActivityInactivity(i);
                }

                // Declare on Nova Lab Router asynchronously
                this.reportDeviceActivationOnMqtt(hdev);

                prometeusService.addDeviceCreation();
                log.debug("scanNewDevicesJob - Add {}", hdev.getDeviceEui());
            }
            if (lastCreated > 0) {
                p.setLongValue(lastCreated - 1); // 1 ms before to make sure
                heliumParameterService.flushParameter(p);
            }

            // Search for updated device to check the appEUI updates
            List<Device> udevs = deviceRepository.findDeviceByUpdatedAtGreaterThanOrderByUpdatedAtAsc(
                    new Timestamp(p.getLongValue()-(consoleConfig.getHeliumDeviceNewScanPeriod()+10_000)));
            for (Device udev : udevs) {
                if ( udev.isDisabled() ) continue; // do not update disabled devices

                String devEui = HexaConverters.byteToHexString(udev.getDevEui());
                // verify
                HeliumDevice hdev = heliumDeviceRepository.findOneHeliumDeviceByDeviceEui(devEui);
                if (hdev != null) {
                    String appEui = udev.getAppEui();
                    if ( appEui != null && hdev.getApplicationEui().compareToIgnoreCase(appEui) != 0 ) {
                        // appEUI updated
                        log.debug("Device {} have a new appEUI {}", udev.getName(), appEui);
                        HeliumTenantSetup ts = heliumTenantSetupService.getHeliumTenantSetup(hdev.getTenantUUID(),false);
                        if (ts == null) {
                            log.error("Find a device with invalid tenantUUID {} / {}", hdev.getDeviceEui(), hdev.getTenantUUID());
                            continue;
                        }

                        // update the route - remove previous one
                        NovaDevice nd = new NovaDevice();
                        nd.devEui = hdev.getDeviceEui();
                        nd.appEui = hdev.getApplicationEui();
                        nd.routeId = ts.getRouteId();
                        novaService.addDelayedEuisRefreshRemoval(nd);

                        // update the device information
                        hdev.setApplicationEui(appEui);
                        heliumDeviceRepository.save(hdev);
                        heliumDeviceCacheService.removeFromCache(hdev.getDeviceEui());

                        // update the route
                        NovaDevice na = new NovaDevice();
                        na.devEui = hdev.getDeviceEui();
                        na.appEui = hdev.getApplicationEui();
                        na.routeId = ts.getRouteId();
                        novaService.addDelayedEuisRefreshAddition(na);
                    }
                }
            }

            // Search for re-enabled devices
            synchronized (this) {
                List<HeliumDevice> hdevs = heliumDeviceRepository.findReactivatedDevices();
                for ( HeliumDevice hdev : hdevs ) {
                    HeliumTenant t = heliumTenantService.getHeliumTenant(hdev.getTenantUUID(),false);
                    if ( t != null ) {
                        if ( t.getState() == HeliumTenant.TenantState.NORMAL ) {
                            hdev.setState(HeliumDevice.DeviceState.INSERTED);
                            hdev.setToUpdate(true);
                            this.reportDeviceActivationOnMqtt(hdev);
                            // save this
                            heliumDeviceRepository.save(hdev);
                            log.debug("scanNewDevicesJob - Enabling device {}", hdev.getDeviceEui());
                        } else {
                            log.debug("scanNewDevicesJob - skip re-enable device when out of dcs {}", hdev.getDeviceEui());
                        }
                    } else {
                        log.error("Try to reactivate a device not in a tenant");
                    }
                }
            }

        } finally {
            this.runningJobs--;
        }
        log.debug("End Running scanNewDevicesJob - duration {}ms", Now.NowUtcMs() - start);
    }

    /**
      Scan for devices removed from the database
      Scan for deactivated devices also
     */
    @Scheduled(fixedRateString = "${helium.device.deleted.scanPeriod}", initialDelay = 30_000)
    private void scanDeletedDevicesJob() {
        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        long start = Now.NowUtcMs();
        try {

            // check if we have a difference (same table size, nothing new, nothing deleted)
            // as Chirpstack table remove the devices but helium glue table store the deleted
            // devices, this part of the code is not required any more.
            // long deviceEntrySize = deviceRepository.count();
            // long heliumDeviceEntrySize = heliumDeviceRepository.count();
            // if (heliumDeviceEntrySize <= deviceEntrySize) return;

            // search for devices not in deviceRepository
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
                    log.debug("scanDeletedDevicesJob - Del {}", hdev.getDeviceEui());
                    prometeusService.addDeviceDeletion();
                }

                // search for deactivated devices ... like a deleted but can be reactivated later
                hdevs = heliumDeviceRepository.findDeactivatedDevices();
                for ( HeliumDevice hdev : hdevs ) {
                    switch ( hdev.getState() ) {
                        case ACTIVE:
                        case INACTIVE:
                        case INSERTED:
                            hdev.setState(HeliumDevice.DeviceState.DISABLED);
                            hdev.setToUpdate(false); // for not being updated with activity / inactivity costs
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
                            log.debug("scanDeletedDevicesJob - Disable {}", hdev.getDeviceEui());
                            break;

                        case DELETED:
                        case OUTOFDCS:
                        case DEACTIVATED:
                        case DISABLED:
                            log.debug("scanDeletedDevicesJob - a device in a non active status has been disabled {}", hdev.getDeviceEui());
                            break;
                    }
                }
            }

            // Call Helium API for updating
            for (LoRaWanCreds c : toRemove) {
                // Declare on Nova Lab Router asynchronously
                this.reportDeviceDeactivationOnMqtt(c);
            }
        } finally {
            this.runningJobs--;
        }
        log.debug("End Running scanDeletedDevicesJob - duration {}ms", Now.NowUtcMs() - start);
    }

    // ===========================================================
    // Clean the routes
    // ===========================================================

    @Autowired
    protected HeliumDeviceCacheService heliumDeviceCacheService;

    /**
     * List the device that are in the Helium route but not in the database to remove them
     * @param commit
     * @return
     */
    public List<NovaDevice> clearInvalidRouteEuis(String routeId, boolean commit) {
        log.debug("clearInvalidRouteEuis - Processing route {}", routeId);
        long start = Now.NowUtcMs();
        ArrayList<NovaDevice> invalids = new ArrayList<>();
        List<NovaDevice> all = novaService.getAllKnownDevices(routeId);
        if ( all == null || all.size() ==  0 ) return invalids; // better do nothing that big mistake & nothing to remove

        for ( NovaDevice dev : all ) {

            HeliumDevice hd = this.heliumDeviceCacheService.getHeliumDevice(dev.devEui,false);
            if ( hd == null ) {
                // this device does not exist
                log.debug("Device {} / {} can be cleared - not exists",dev.devEui,dev.appEui);
                invalids.add(dev);
            } else  {
                if ( dev.appEui.compareToIgnoreCase(hd.getApplicationEui()) != 0 ) {
                    log.debug("Device {} exists but with wrong appEUI, removing it", dev.devEui);
                    dev.timeMs = Now.NowUtcMs();
                    invalids.add(dev);
                } else {
                    switch (hd.getState()) {
                        default:
                        case INSERTED:
                        case ACTIVE:
                        case INACTIVE:
                            break;

                        case DEACTIVATED:
                        case OUTOFDCS:
                        case DELETED:
                        case DISABLED:
                            log.debug("Device {} / {} can be cleared - disabled",dev.devEui,dev.appEui);
                            invalids.add(dev);
                            break;
                    }
                }
            }

        }
        if ( commit ) {
            novaService.deactivateDevices(invalids);
        }
        prometeusService.addRouteUpdate(start);
        return invalids;
    }

    /**
     * Resync the database devices with the helium route by searching all the device not in the helium route
     * and adding them
     * @param commit
     * @return
     */
    public List<NovaDevice> searchMissingRouteEuis(String tenantId, String routeId, boolean commit) {
        long start = Now.NowUtcMs();
        ArrayList<NovaDevice> missing = new ArrayList<>();

        // get the devices and store in hashmap for search
        List<NovaDevice> all = novaService.getAllKnownDevices(routeId);
        if ( all == null ) return missing; // better do nothing that big mistake

        log.debug("searchMissingRouteEuis - Processing route : {}", routeId);
        log.debug("searchMissingRouteEuis - Existing {} euis in the route", all.size());
        HashMap<String,NovaDevice> all_= new HashMap<>();
        for ( NovaDevice n : all ) {
            all_.put((""+n.devEui+n.appEui).toLowerCase(),n);
        }

        Slice<HeliumDevice> allDevices = heliumDeviceRepository.findHeliumDeviceByTenantUUID(tenantId, PageRequest.of(0, 100));
        boolean nextPage = false;
        if ( allDevices != null ) {
            do {
                for ( HeliumDevice d : allDevices ) {
                    switch ( d.getState() ) {
                        case INSERTED:
                        case ACTIVE:
                        case INACTIVE:
                            if ( all_.get((""+d.getDeviceEui()+d.getApplicationEui()).toLowerCase()) == null ) {
                                NovaDevice n = new NovaDevice();
                                n.devEui= d.getDeviceEui();
                                n.appEui= d.getApplicationEui();
                                n.routeId= routeId;
                                n.timeMs= Now.NowUtcMs();
                                missing.add(n);
                                log.debug("Device {} / {} should be added", n.devEui, n.appEui);
                            }
                            break;

                        default:
                        case DEACTIVATED:
                        case OUTOFDCS:
                        case DELETED:
                        case DISABLED:
                        break;
                    }

                }
                if ( allDevices.hasNext() ) {
                    allDevices = heliumDeviceRepository.findHeliumDeviceByTenantUUID(tenantId, allDevices.nextPageable());
                    nextPage = true;
                } else nextPage = false;
            } while (nextPage);
        }
        if (!missing.isEmpty()) log.info("Found {} devices to add in the route {}", missing.size(), routeId);
        if (commit) novaService.activateDevices(missing);
        prometeusService.addRouteUpdate(start);
        return missing;
    }

    // On Start Schedule a route cleaning process
    private boolean resynced = false;

    @Autowired
    private HeliumTenantSetupRepository heliumTenantSetupRepository;

    // resync the devices in the route
    @Scheduled(fixedRate = 3600_000, initialDelay = 55_000)
    private void resyncOnce() {
        if (resynced) return;
        long start = Now.NowUtcMs();
        log.info("resyncOnce - start db & helium route resync");

        long cDevices = deviceRepository.count();
        long pDevices = 0;
        long lastTrace = Now.NowUtcMs();

        // scan all Devices (to sync appEui when needed)
        Slice<Device> allDevices = deviceRepository.findDeviceBy(PageRequest.of(0, 100));
        boolean nextPage = false;
        if ( allDevices != null ) {
            do {
                for (Device d : allDevices) {
                    String appEui = d.getAppEui();
                    if ( appEui != null ) {
                        String devEui = HexaConverters.byteToHexString(d.getDevEui());
                        HeliumDevice hdev = heliumDeviceCacheService.getHeliumDevice(devEui,false);
                        if ( hdev != null ) {
                            if ( hdev.getApplicationEui().compareTo(appEui) != 0) {
                                log.debug("Found an appEui to update : {} ({})", devEui, appEui);
                                hdev.setApplicationEui(appEui);
                                heliumDeviceRepository.save(hdev);
                            }
                        }
                    }
                    pDevices++;
                    if ((Now.NowUtcMs() - lastTrace) > 30_000) {
                        lastTrace = Now.NowUtcMs();
                        log.info("resyncOnce - Phase 1 - {} / {} devices already processed", pDevices, cDevices);
                    }
                }
                if ( allDevices.hasNext() ) {
                    allDevices = deviceRepository.findDeviceBy(allDevices.nextPageable());
                    nextPage = true;
                } else nextPage = false;
            } while (nextPage);
            log.info("resyncOnce - Phase 1 - {} / {} devices processed", pDevices, cDevices);
        }

        // Scan all Helium tenants
        int i = 0;
        long cTemplate = heliumTenantSetupRepository.count();
        long pTemplate = 0;
        Page<HeliumTenantSetup> htss = null;
        do {
            htss = heliumTenantSetupRepository.findAllByTemplate(false,PageRequest.of(i,50));
            for ( HeliumTenantSetup hts : htss ) {
                if ( hts.getRouteId() != null && !hts.isTemplate() ) {
                    this.clearInvalidRouteEuis(hts.getRouteId(), true);
                    this.searchMissingRouteEuis(hts.getTenantUUID(),hts.getRouteId(),true);
                }
                pTemplate++;
                if ((Now.NowUtcMs() - lastTrace) > 30_000) {
                    lastTrace = Now.NowUtcMs();
                    log.info("resyncOnce - Phase 2 - {} / {} tenants already processed", pTemplate, cTemplate);
                }
            }
            i++;
        } while ( htss.hasNext() );
        log.info("resyncOnce - Phase 2 - {} / {} tenants processed", pTemplate, cTemplate);

        resynced = true;
        novaService.setReadyForSessionRefresh(true);
        log.info("resyncOnce - processed in {}ms", Now.NowUtcMs() - start);
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

    // Manage the device Activity
    @Scheduled(fixedRateString = "${helium.device.activation.scanPeriod}", initialDelay = 120_000)
    private void deviceActivityJob() {
        if ( ! this.serviceEnable ) return;
        this.runningJobs++;
        long start = Now.NowUtcMs();
        try {
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
                    // no need to reprocess always the same device
                    if ( (now - hdev.getLastSeen()) < 3600_000 ) continue;

                    boolean invoicingReport = false;    // reporting invoicing needed

                    HeliumDeviceStatItf i = new HeliumDeviceStatItf();
                    i.setDeviceId(hdev.getDeviceEui());
                    i.setTenantId(hdev.getTenantUUID());

                    Device dev = deviceRepository.findOneDeviceByDevEui(hdev.getDeviceUUID());
                    if (dev != null) {
                        long lastSeenDevice = 0;
                        if ( dev.getLastSeenAt() != null ) {
                            Timestamp t = dev.getLastSeenAt();
                            lastSeenDevice = t.getTime();
                        }
                        if (lastSeenDevice == 0) lastSeenDevice = dev.getCreatedAt().getTime();

                        HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(
                                hdev.getTenantUUID(), true,true, 100
                        );

                        if (hts.getInactivityBillingPeriodMs() > 0) {

                            // is inactive
                            if ((now - hts.getInactivityBillingPeriodMs()) > lastSeenDevice) {
                                // inactive device, now need to see if already invoiced for that period of time and
                                // count the number of periods since last calculation
                                long inactivityPeriod = (now - hdev.getLastInactivityInvoiced());
                                long inactivityPeriods = inactivityPeriod / hts.getInactivityBillingPeriodMs();
                                log.debug("Found {} periods of inactivity for device {}", inactivityPeriods, hdev.getDeviceEui());
                                if (inactivityPeriods > 0) {
                                    hdev.setLastInactivityInvoiced(hdev.getLastInactivityInvoiced() + inactivityPeriods * hts.getInactivityBillingPeriodMs());
                                    hdev.setLastActivityInvoiced(hdev.getLastActivityInvoiced() + inactivityPeriods * hts.getInactivityBillingPeriodMs());
                                    if (hts.getDcPerInactivityPeriod() > 0) {
                                        i.setInactivityDc((int) inactivityPeriods * hts.getDcPerInactivityPeriod());
                                        invoicingReport = true;
                                    }
                                    hdev.setState(HeliumDevice.DeviceState.INACTIVE);
                                }
                            } else if ( hts.getActivityBillingPeriodMs() == 0 ){
                                // we have been active and it will not be updated by ActivityBilling
                                hdev.setLastInactivityInvoiced(lastSeenDevice);
                                hdev.setState(HeliumDevice.DeviceState.ACTIVE);
                            }

                        }

                        if (hts.getActivityBillingPeriodMs() > 0) {

                            // is active (should not be possible to be active & inactive but setup can be made like this so...
                            if ((now - hts.getActivityBillingPeriodMs()) < lastSeenDevice) {
                                // active device within the period of time
                                long activityPeriod = (now - hdev.getLastActivityInvoiced());
                                long activityPeriods = activityPeriod / hts.getActivityBillingPeriodMs();
                                log.debug("Found {} periods of activity for device {}", activityPeriods, hdev.getDeviceEui());
                                if (activityPeriods > 0) {
                                    hdev.setLastActivityInvoiced(hdev.getLastActivityInvoiced() + activityPeriods * hts.getActivityBillingPeriodMs());
                                    hdev.setLastInactivityInvoiced(lastSeenDevice);
                                    if (hts.getDcPerActivityPeriod() > 0) {
                                        i.setActivityDc((int) activityPeriods * hts.getDcPerActivityPeriod());
                                        invoicingReport = true;
                                    }
                                    hdev.setState(HeliumDevice.DeviceState.ACTIVE);
                                }
                            } else if (hts.getInactivityBillingPeriodMs() == 0) {
                                if ( ( now - hdev.getLastActivityInvoiced() ) > hts.getActivityBillingPeriodMs() ) {
                                    // more than a period of activity has passed in inactivity
                                    // skip it
                                    hdev.setLastActivityInvoiced(hdev.getLastActivityInvoiced() + hts.getActivityBillingPeriodMs());
                                }
                                hdev.setState(HeliumDevice.DeviceState.INACTIVE);
                            }

                        }

                        // send stat and invoicing information
                        if (invoicingReport) {
                            heliumTenantService.processDeviceInsertionActivityInactivity(i);
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
                                    sumDcs += e.getJoinDc();
                                    sumDcs += e.getJoinAcceptDc();
                                    sumDcs += e.getPunishmentDc();
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
                                    sumDcs += c.getJoinDc();
                                    sumDcs += c.getJoinAcceptDc();
                                    sumDcs += c.getPunishmentDc();
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
                        if (    ( hdev.getState() == HeliumDevice.DeviceState.ACTIVE ||
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
                                    sumDcs += e.getJoinDc();
                                    sumDcs += e.getJoinAcceptDc();
                                    sumDcs += e.getPunishmentDc();
                                }
                                if (sumDcs > hts.getLimitDcRatePerDevice()) {
                                    log.debug("deviceActivityJob - deactivate device (limitDCs) {} consumed {}", hdev.getDeviceEui(), sumDcs);
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
                        hdev.setLastSeen(now);
                        heliumDeviceRepository.save(hdev);
                    }
                }
            }
        } finally {
            this.runningJobs--;
        }
        log.debug("deviceActivityJob - processed in {}ms", Now.NowUtcMs() - start);

    }

    @Autowired
    protected NovaService novaService;

    protected class DeactivationRequest {
        public String tenantID;
        public long lastRequest;
        public int retries;
    }
    protected HashMap<String,DeactivationRequest> lastDeactivation = new HashMap<>();

    /**
     * Search all devices of the tenant that are currently
     * @param tenantID
     *
     * @todo - this is called many time when DC OUT and high traffic
     *          we should filter redundancy on the previous layer...
     *
     */
    public void processTenantDeactivation(String tenantID) {
        log.debug("Start tenant deactivation for {}", tenantID);
        long start = Now.NowUtcMs();

        // Avoid to process the same tenant many times on high traffic
        DeactivationRequest r = lastDeactivation.get(tenantID);
        if ( r != null && (start - r.lastRequest) < 120_000 ) {
            // skip this one
            log.debug("Skip tenant deactivation request for {}", tenantID);
            return;
        }
        if ( r != null ) {
            r.lastRequest = start;
            r.retries++;
            if ( r.retries > 10 ) {
                log.error("More than 10 trials to deactivate a tenant is not normal for {}", tenantID);
            }
        } else {
            r = new DeactivationRequest();
            r.retries=0;
            r.lastRequest=start;
            r.tenantID=tenantID;
            lastDeactivation.put(tenantID,r);
        }

        String tName = "Unknown";
        HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(tenantID,false);
        if ( hts == null ) {
            log.error("Found a tenant w/o tenantSTemplate {}", tenantID);
        } else {
            Tenant t = tenantRepository.findOneTenantById(UUID.fromString(hts.getTenantUUID()));
            if ( t != null ) tName = t.getName();
        }
        ArrayList<NovaDevice> toDeactivate = new ArrayList<>();

        logService.log(new LogEntry(
                LogLevel.INFO,
                "TENANT",
                "Tenant deactivation request for "+tName+" ("+tenantID+")",
                Now.NowUtcMs()
        ));
        synchronized (this) {
            Slice<HeliumDevice> allDevices = heliumDeviceRepository.findHeliumDeviceByTenantUUID(tenantID, PageRequest.of(0, 200));
            boolean nextPage = false;
            if ( allDevices != null ) {
                do {
                    for (HeliumDevice d : allDevices) {

                        if (
                                d.getState() == HeliumDevice.DeviceState.ACTIVE ||
                                        d.getState() == HeliumDevice.DeviceState.INACTIVE ||
                                        d.getState() == HeliumDevice.DeviceState.INSERTED ||
                                        d.getState() == HeliumDevice.DeviceState.OUTOFDCS       // not normal but we have the case the device was still running event if ...
                        ) {

                            d.setState(HeliumDevice.DeviceState.OUTOFDCS);
                            d.setToUpdate(false);
                            heliumDeviceRepository.save(d);

                            if (hts != null) {
                                NovaDevice n = new NovaDevice();
                                n.devEui = d.getDeviceEui();
                                n.appEui = d.getApplicationEui();
                                n.routeId = hts.getRouteId();
                                n.timeMs = Now.NowUtcMs();
                                toDeactivate.add(n);
                            }

                        }
                    }
                    if (allDevices.hasNext()) {
                        allDevices = heliumDeviceRepository.findHeliumDeviceByTenantUUID(tenantID, allDevices.nextPageable());
                        nextPage = true;
                    } else nextPage = false;
                } while (nextPage);
            }
        }
        novaService.deactivateDevices(toDeactivate);
        heliumTenantService.commitTenantDeactivation(tenantID);
        log.info("tenantDeactivation ({})- processed in {}ms", tenantID, Now.NowUtcMs() - start);

        // clean the deactivation cache
        if ( lastDeactivation.size() > 5 ) {
            ArrayList<String> cleanup = new ArrayList<>();
            for ( DeactivationRequest d : lastDeactivation.values() ) {
                if ( d.lastRequest < (start - 600_000) ) {
                    // more than 10 minutes pending
                    cleanup.add(d.tenantID);
                }
            }
            for ( String s : cleanup ) {
                lastDeactivation.remove(s);
            }
        }

    }

    public void processTenantReactivation(String tenantID) {
        log.debug("Start tenant reactivation for {}", tenantID);
        long start = Now.NowUtcMs();

        String tName = "Unknown";
        HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(tenantID,false);
        if ( hts == null ) {
            log.error("Found a tenant w/o tenantSTemplate {}", tenantID);
            return;
        } else {
            Tenant t = tenantRepository.findOneTenantById(UUID.fromString(hts.getTenantUUID()));
            if ( t != null ) tName = t.getName();
        }
        ArrayList<NovaDevice> toReactivate = new ArrayList<>();

        logService.log(new LogEntry(
                LogLevel.INFO,
                "TENANT",
                "Tenant reactivation request for "+tName+" ("+tenantID+")",
                Now.NowUtcMs()
        ));
        synchronized (this) {

            Slice<HeliumDevice> allDevices = heliumDeviceRepository.findHeliumDeviceByTenantUUID(tenantID, PageRequest.of(0, 200));
            boolean nextPage = false;
            if ( allDevices != null ) {
                do {
                    for (HeliumDevice d : allDevices) {

                        if (
                                d.getState() == HeliumDevice.DeviceState.OUTOFDCS
                        ) {
                            d.setState(HeliumDevice.DeviceState.INSERTED);
                            d.setLastActivityInvoiced(start);
                            d.setLastInactivityInvoiced(start);
                            d.setToUpdate(true);
                            d.setLastSeen(start);
                            heliumDeviceRepository.save(d);

                            NovaDevice n = new NovaDevice();
                            n.devEui = d.getDeviceEui();
                            n.appEui = d.getApplicationEui();
                            n.routeId = hts.getRouteId();
                            n.timeMs = Now.NowUtcMs();
                            toReactivate.add(n);
                        }
                    }
                    if (allDevices.hasNext()) {
                        allDevices = heliumDeviceRepository.findHeliumDeviceByTenantUUID(tenantID, allDevices.nextPageable());
                        nextPage = true;
                    } else nextPage = false;
                } while (nextPage);
            }
        }
        novaService.activateDevices(toReactivate);
        heliumTenantService.commitTenantReactivation(tenantID);
        log.info("tenantReactivation ({})- processed in {}ms", tenantID, Now.NowUtcMs() - start);

    }


    public void processDeviceDeactivation(HeliumDeviceActDeactItf creds) {
        log.debug("Start device deactivation for {}", creds.getDeviceId());
        long start = Now.NowUtcMs();

        ArrayList<NovaDevice> toDeactivate = new ArrayList<>();
        HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(creds.getTenantId(),false);
        if ( hts == null ) {
            log.error("Found a tenant w/o tenantSTemplate {}", creds.getTenantId());
            return;
        }

        NovaDevice n = new NovaDevice();
        n.devEui = creds.getDeviceId();
        n.appEui = creds.getAppEui();
        n.routeId = hts.getRouteId();
        n.timeMs = Now.NowUtcMs();
        toDeactivate.add(n);

        novaService.deactivateDevices(toDeactivate);
    }

    public void processDeviceReactivation(HeliumDeviceActDeactItf creds) {
        log.debug("Start device (re)activation for {}", creds.getDeviceId());
        long start = Now.NowUtcMs();

        ArrayList<NovaDevice> toReactivate = new ArrayList<>();
        HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(creds.getTenantId(),false);
        if ( hts == null ) {
            log.error("Found a tenant w/o tenantSTemplate {}", creds.getTenantId());
            return;
        }

        NovaDevice n = new NovaDevice();
        n.devEui = creds.getDeviceId();
        n.appEui = creds.getAppEui();
        n.routeId = hts.getRouteId();
        n.timeMs = Now.NowUtcMs();
        toReactivate.add(n);

        novaService.activateDevices(toReactivate);
    }

    // =================================================
    // Helper
    // =================================================
    private HashMap<String, String> appNameCache = new HashMap<>();
    public String getApplicationNameFromId(String applicationId) {
        String n = appNameCache.get(applicationId);
        if ( n == null ) {
            Application app = applicationRepository.findOneApplicationById(UUID.fromString(applicationId));
            if ( app == null ) return null;
            n = app.getName();
            if ( appNameCache.size() >= 200 ) {
                // simple and small cache
                appNameCache = new HashMap<>();
            }
            appNameCache.put(applicationId,n);
        }
        return n;
    }

}
