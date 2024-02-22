/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2024.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package eu.heliumiot.console.service;

import eu.heliumiot.console.ConsolePrivateConfig;
import eu.heliumiot.console.chirpstack.ChirpstackApiAccess;
import eu.heliumiot.console.jpa.db.Device;
import eu.heliumiot.console.jpa.db.HeliumDevice;
import eu.heliumiot.console.jpa.db.HeliumTenantSetup;
import eu.heliumiot.console.jpa.db.NovaDevice;
import eu.heliumiot.console.jpa.repository.DeviceRepository;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.Now;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HeliumEduService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private boolean enableEdu = false;

    @Autowired
    protected ConsolePrivateConfig consolePrivateConfig;

    @PostConstruct
    private void onHeliumEduServiceInit() {
        if ( consolePrivateConfig.getHeliumEduDeviceMaxlifeMs() > 0 ) {
            log.info("# Helium EDU is activated on this environment");
            enableEdu = true;
        }
    }

    // Make sure we do not stop in the middle of a running process
    public boolean stopService() {
        this.enableEdu=false;
        return (garbageRunning == false) && (cleanGarbageRunning == false);
    }

    // ------------------------------------------
    // Garbage the devices created for more than the
    // authorized time

    @Autowired
    protected DeviceRepository deviceRepository;

    @Autowired
    protected ChirpstackApiAccess chirpstackApiAccess;

    @Autowired
    protected NovaService novaService;

    @Autowired
    protected HeliumDeviceCacheService heliumDeviceCacheService;

    @Autowired
    protected HeliumTenantSetupService heliumTenantSetupService;

    private boolean garbageRunning = false;
    @Scheduled(fixedDelay = 300_000, initialDelay = 120_000) // default : 5 minutes
    private void deviceGarbageCollector() {
        if ( !this.enableEdu ) return;
        garbageRunning = true;
        ArrayList<NovaDevice> toDeactivate = new ArrayList<>();
        try {

            // Get devices
            List<Device> devices = deviceRepository.findDeviceByNApplicationIdAndCreatedAtBetweenOrderByCreatedAtAsc(
                UUID.fromString(consolePrivateConfig.getHeliumEduDeviceGarbageApplicationID()),
                new Timestamp(consolePrivateConfig.getHeliumEduDeviceExludeBefore()),
                new Timestamp(Now.NowUtcMs() - consolePrivateConfig.getHeliumEduDeviceMaxlifeMs())
            );

            for ( Device device : devices ) {
                String devEui = HexaConverters.byteToHexString(device.getDevEui());
                log.debug("deviceGarbageCollector - Removing device "+devEui);

                // get the Helium Device and mark it deleted
                HeliumDevice hdev = heliumDeviceCacheService.getHeliumDevice(devEui);
                if ( hdev != null ) {
                    // make as deleted so a new one can be created later
                    hdev.setState(HeliumDevice.DeviceState.DELETED);
                    hdev.setToUpdate(false);
                    hdev.setDeletedAt(Now.NowUtcMs());
                    heliumDeviceCacheService.flushHeliumDevice(hdev);
                    // clean the route
                    HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(hdev.getTenantUUID(),false);
                    if ( hts != null ) {
                        NovaDevice nd = new NovaDevice();
                        nd.devEui = devEui;
                        nd.appEui = hdev.getApplicationEui();
                        nd.routeId = hts.getRouteId();
                        toDeactivate.add(nd);
                    } else {
                        log.error("deviceGarbageCollector - Helium device without a valid Tenant Setup");
                    }
                    // delete the device
                    if (!chirpstackApiAccess.deleteOneDevice(devEui) ) {
                        log.error("deviceGarbageCollector - Chirpstack device "+devEui+" deletion failed");
                    } else {
                        // create a new device in garbage
                        if (consolePrivateConfig.getHeliumEduDeviceGarbageTenantID() != null && consolePrivateConfig.getHeliumEduDeviceGarbageTenantID().length() > 0) {
                            if (!chirpstackApiAccess.createOneOTAADevice(
                                devEui,
                                hdev.getApplicationEui(),
                                null,
                                consolePrivateConfig.getHeliumEduDeviceGarbageTenantID(),
                                consolePrivateConfig.getHeliumEduDeviceGarbageApplicationID(),
                                consolePrivateConfig.getHeliumEduDeviceGarbagedevprofileID(),
                                device.getDescription(),
                                device.getName(),
                                true,
                                true
                            )) {
                                log.error("deviceGarbageCollector - Chripstack device "+devEui+" recreation impossible");
                            }
                        }
                    }

                } else {
                    log.error("deviceGarbageCollector - Can remove device "+devEui+" not found in HeliumDevices");
                }
            }
        } catch (Exception x) {
            log.error("deviceGarbageCollector Exception "+x.getMessage());
            x.printStackTrace();
        } finally {
            // clear the Nova routes
            try {
                novaService.deactivateDevices(toDeactivate);
            } catch (Exception e) {
                log.error("deviceGarbageCollector Exception "+e.getMessage());
                e.printStackTrace();
            }
            garbageRunning = false;
        }
    }

    // ---------------------------------------------
    // Clear the Garbage tenant after the garbage
    // max duration

    private boolean cleanGarbageRunning = false;
    @Scheduled(fixedDelay = 600_000, initialDelay = 150_000) // default : 10 minutes
    private void deviceCleanGarbageCollector() {
        if ( !this.enableEdu ) return;
        cleanGarbageRunning = true;
        try {

            // Get devices
            List<Device> devices = deviceRepository.findDeviceByApplicationIdAndCreatedAtBetweenOrderByCreatedAtAsc(
                UUID.fromString(consolePrivateConfig.getHeliumEduDeviceGarbageApplicationID()),
                new Timestamp(consolePrivateConfig.getHeliumEduDeviceExludeBefore()),
                new Timestamp(Now.NowUtcMs() - consolePrivateConfig.getHeliumEduDeviceGarbagelifeMs())
            );

            for ( Device device : devices ) {
                String devEui = HexaConverters.byteToHexString(device.getDevEui());
                log.debug("deviceCleanGarbageCollector - Deleting garbaged device "+devEui);

                // get the Helium Device and mark it deleted
                HeliumDevice hdev = heliumDeviceCacheService.getHeliumDevice(devEui);
                if ( hdev != null ) {
                    // make as deleted so a new one can be created later
                    hdev.setState(HeliumDevice.DeviceState.DELETED);
                    hdev.setToUpdate(false);
                    hdev.setDeletedAt(Now.NowUtcMs());
                    heliumDeviceCacheService.flushHeliumDevice(hdev);
                    // delete the device
                    if (!chirpstackApiAccess.deleteOneDevice(devEui) ) {
                        log.error("deviceCleanGarbageCollector - Chirpstack device "+devEui+" deletion failed");
                    }
                } else {
                    log.error("deviceCleanGarbageCollector - Can remove device "+devEui+" not found in HeliumDevices");
                }
            }

        } catch (Exception x) {
            log.error("deviceCleanGarbageCollector Exception "+x.getMessage());
            x.printStackTrace();
        } finally {
            cleanGarbageRunning = false;
        }
    }

}
