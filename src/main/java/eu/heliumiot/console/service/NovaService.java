package eu.heliumiot.console.service;

import eu.heliumiot.console.jpa.db.HeliumDevice;
import eu.heliumiot.console.jpa.db.NovaDevice;
import eu.heliumiot.console.redis.RedisDeviceRepository;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.Now;
import io.chirpstack.api.internal.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NovaService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected RedisDeviceRepository redisDeviceRepository;

    @Autowired
    protected HeliumDeviceCacheService heliumDeviceCacheService;

    public boolean refreshDevAddrList(String devaddr) {
        // Get the list of device using this devaddr
        List<String> deviceEUIs = redisDeviceRepository.getDevEuiByDevAddr(devaddr);
        log.debug("Refresh devAddr network keys for "+devaddr+" found "+deviceEUIs.size()+" devices");
        for ( String devEUI : deviceEUIs ) {
            // verify state from cache, is that an active device...
            HeliumDevice hd = heliumDeviceCacheService.getHeliumDevice(devEUI);
            switch ( hd.getState() ) {
                case INSERTED:
                case ACTIVE:
                case INACTIVE:
                    // get all NwkSKey
                    Internal.DeviceSession ds = redisDeviceRepository.getDeviceDetails(devEUI);
                    if ( ds != null && ds.getNwkSEncKey() != null && ds.getNwkSEncKey().size() > 4) {

                        // add to the list later call Nova API
                        // @Todo call Nova Api
                        String nwkSKey = HexaConverters.byteToHexString(ds.getNwkSEncKey().toByteArray());
                        log.debug("Add Network encrypted Key " +nwkSKey);
                    }
                    break;
                default:
                case DEACTIVATED:
                case OUTOFDCS:
                case DELETED:
                    break;
            }

        }
        return true;
    }

    // ------
    protected int runningJobs=0;
    protected boolean serviceEnable=true; // false to stop the services
    // request to stop the service properly
    public void stopService() {
        this.serviceEnable = false;
    }
    // return true when the service has stopped all the running jobs
    public boolean hasStopped() {
        return (this.serviceEnable == false && this.runningJobs == 0);
    }
    // -----

    // List of deviceEUI we want the associated devAddr to be refreshed
    protected ArrayList<String> delayedSessionRefresh = new ArrayList<>();
    protected void addDelayedSessionRefresh(String devEUI) {
        synchronized (delayedSessionRefresh) {
            this.delayedSessionRefresh.add(devEUI);
        }
    }

    /**
     * On regular basis we update the sessions on Nova backend based on the movement made on the devices
     * deletion, creation, deactivation, reactivation ...
     * Start managing this after the first global refresh (avoid conflict if doing it in parallel)
     */
    @Scheduled(fixedRateString = "${helium.nova.publish.delayed.scanPeriod}", initialDelay = 120_000)
    protected void flushDelayedSessionUpdate() {
        if ( ! this.serviceEnable || ! this.initialRefreshDone ) return;
        this.runningJobs++;
        long start = Now.NowUtcMs();
        try {

            ArrayList<String> toRefresh = new ArrayList<>();
            if (delayedSessionRefresh.size() > 0) {

                // get all the pending device to be updated
                synchronized (delayedSessionRefresh) {
                    for (String devEUI : delayedSessionRefresh) {
                        toRefresh.add(devEUI);
                    }
                    delayedSessionRefresh.clear();
                }

                // get the list of devaddr associated to these devices
                HashMap<String, String> devAddr = new HashMap<>();
                for (String devEUI : toRefresh) {
                    Internal.DeviceSession ds = redisDeviceRepository.getDeviceDetails(devEUI);
                    String devad = HexaConverters.byteToHexString(ds.getDevAddr().toByteArray());
                    if (devAddr.get(devad) == null) {
                        devAddr.put(devad, devad);
                    }
                }

                // Update the devAddr
                for ( String devAd : devAddr.values() ) {
                    this.refreshDevAddrList(devAd);
                }

            }
        } finally {
            this.runningJobs--;
        }
        log.debug("End Running flushDelayedSessionUpdate - duration "+(Now.NowUtcMs()-start)+"ms");

    }

    /**
     * When starting the application, it's better to refresh all the sessions on Nova Backend
     * to avoid synchronization problems dur to chirpstack action executed when the console was down
     */
    protected boolean initialRefreshDone = false;
    @Scheduled(fixedDelay = 3600_000, initialDelay = 180_000)
    protected void initialNovaSessionRefresh() {
        if ( !initialRefreshDone ) {
            if ( ! this.serviceEnable ) return;
            this.runningJobs++;
            long start = Now.NowUtcMs();
            try {
                log.info("initialRefresh - refresh all the sessions");
                List<String> devAddrs = redisDeviceRepository.getAllDevAddr();
                for (String devAddr : devAddrs) {
                    log.debug("initialRefresh - refresh devAddr " + devAddr);
                    this.refreshDevAddrList(devAddr);
                }
            } finally {
                this.runningJobs--;
            }
            log.debug("End Running initialNovaSessionRefresh - duration "+(Now.NowUtcMs()-start)+"ms");
        }
        this.initialRefreshDone=true;
    }


    public boolean deactivateDevices(List<NovaDevice> devices) {
        for ( NovaDevice d : devices ) {
            this.addDelayedSessionRefresh(d.devEui);
            log.info("Deactivating device "+d.devEui);
        }
        return true;
    }

    public boolean activateDevices(List<NovaDevice> devices) {
        for ( NovaDevice d : devices ) {
            this.addDelayedSessionRefresh(d.devEui);
            log.info("Activating device "+d.devEui);
        }
        return true;
    }


}
