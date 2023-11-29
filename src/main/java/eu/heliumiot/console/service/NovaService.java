package eu.heliumiot.console.service;

import com.google.protobuf.ByteString;
import eu.heliumiot.console.ConsoleApplication;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.jpa.db.HeliumDevice;
import eu.heliumiot.console.jpa.db.HeliumParameter;
import eu.heliumiot.console.jpa.db.HeliumTenantSetup;
import eu.heliumiot.console.jpa.db.NovaDevice;
import eu.heliumiot.console.jpa.repository.HeliumDeviceRepository;
import eu.heliumiot.console.jpa.repository.HeliumTenantSetupRepository;
import eu.heliumiot.console.redis.RedisDeviceRepository;
import fr.ingeniousthings.tools.*;
import io.chirpstack.api.internal.Internal;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.helium.grpc.*;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class NovaService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // @TODO - reverse this to 0
    protected static final int SKFS_MAX_COPIES = 0;
    @Autowired
    protected RedisDeviceRepository redisDeviceRepository;

    @Autowired
    protected HeliumTStoNovaProxyService heliumTStoNovaProxyService;

    @Autowired
    protected PrometeusService prometeusService;

    // ----------------------------------
    // MANAGE THE SESSIONS NwkSkey
    // ----------------------------------

    protected int runningJobs = 0;
    protected boolean serviceEnable = true; // false to stop the services

    // request to stop the service properly
    public void stopService() {
        if ( ! this.flushDelayedEuisUpdateRunning ) {
            this.flushDelayedEuisUpdate();
        }
        this.serviceEnable = false;
    }

    // return true when the service has stopped all the running jobs
    public boolean hasStopped() {
        return (!this.serviceEnable  && this.runningJobs == 0);
    }
    // -----

    // List of deviceEUI we want the associated devAddr to be refreshed
    protected final ArrayList<String> delayedSessionRefresh = new ArrayList<>();

    protected void addDelayedSessionRefresh(String devEUI) {
        synchronized (delayedSessionRefresh) {
            boolean found = false;
            for ( String s : this.delayedSessionRefresh ) {
                if ( s.compareToIgnoreCase(devEUI) == 0 ) { found = true; break; }
            }
            if ( !found )this.delayedSessionRefresh.add(devEUI);
        }
    }



    /**
     * On regular basis we update the sessions on Nova backend based on the movement made on the devices
     * deletion, creation, deactivation, reactivation ...
     * Start managing this after the first global refresh (avoid conflict if doing it in parallel)
     */
    @Scheduled(fixedDelayString = "${helium.nova.publish.delayed.scanPeriod}", initialDelay = 120_000)
    protected void flushDelayedSessionUpdate() {
        if (!this.serviceEnable || !this.initialSessionRefreshDone) return;
        if (!consoleConfig.isHeliumGrpcSkfEnable()) return;

        this.runningJobs++;
        long start = Now.NowUtcMs();
        try {

            ArrayList<String> toRefresh = new ArrayList<>();
            if (delayedSessionRefresh.size() > 0) {

                // get all the pending device to be updated
                synchronized (delayedSessionRefresh) {
                    toRefresh.addAll(delayedSessionRefresh);
                    delayedSessionRefresh.clear();
                }

                HashMap<String,String> routes = new HashMap<>();
                for (String devEUI : toRefresh) {
                    // get routeId to be refreshed
                    String routeId = heliumTStoNovaProxyService.getRouteIdFromEui(devEUI);
                    if ( routeId == null ) {
                        log.warn("flushDelayedSessionUpdate - the route is not existing for "+devEUI);
                        // process later
                        // @todo - after a certain time we should clear this, the route may be simply deleted
                        addDelayedSessionRefresh(devEUI);
                    } else {
                        routes.putIfAbsent(routeId,routeId);
                    }
                }

                for ( String route : routes.values() ) {
                    this.refreshOneRouteSkf(route);
                }

            }
        } finally {
            this.runningJobs--;
        }
        log.debug("End Running flushDelayedSessionUpdate - duration " + (Now.NowUtcMs() - start) + "ms");

    }

    /**
     * When starting the application, it's better to refresh all the sessions on Nova Backend
     * to avoid synchronization problems dur to chirpstack action executed when the console was down
     * @Todo - we could imagine to rerun this on every day to make a full resync
     *         not mandatory as we manage delta and delta refresh the whole devaddr
     *         but it could a interesting sometime. let see the real life
     */
    protected boolean initialSessionRefreshDone = false;

    @Autowired
    protected HeliumTenantSetupRepository heliumTenantSetupRepository;

    private boolean readyForSessionRefresh = false;
    public void setReadyForSessionRefresh(boolean readyForSessionRefresh) {
        this.readyForSessionRefresh = readyForSessionRefresh;
    }

    @Scheduled(fixedDelay = 120_000, initialDelay = 180_000)
    protected void initialRouteAndSessionRefresh() {
        // make sure the tenant refresh has been done
        if (!this.readyForSessionRefresh ) return;
        if (!initialSessionRefreshDone) {
            if (!this.serviceEnable) return;
            this.runningJobs++;
            long start = Now.NowUtcMs();
            try {
                log.info("Initial Route Refresh");

                // process all routes
                int _i = 0, _j = 0;
                List<HeliumTenantSetup> htss = null;
                do {
                    htss = heliumTenantSetupRepository.findAllByTemplate(false, PageRequest.of(_i,50));
                    for ( HeliumTenantSetup hts : htss ) {
                        if ( hts.getRouteId() != null && !hts.isTemplate() ) {
                            // process one route
                            log.info("["+_i+"/"+_j+"] Refreshing tenant "+hts.getTenantUUID()+ " route "+hts.getRouteId()); _j++;
                            // search the route
                            route_v1 r = grpcGetOneRoute(hts.getRouteId());
                            if ( r == null ) { log.error("A known route does not exist"); continue; }

                            // verify if route server are ok
                            boolean toBeUpdated = false;
                            if ( r.getServer().getHost().compareTo(consoleConfig.getHeliumRouteHost()) != 0
                                || r.getIgnoreEmptySkf() != consoleConfig.isHeliumRouteRejectEmptySKF()
                            ) {
                                toBeUpdated = true;
                            } else {
                                // check the region list
                                for (RegionSupported reg : regionsSupported) {
                                    toBeUpdated = true;
                                    for (protocol_gwmp_mapping_v1 gwp : r.getServer().getGwmp().getMappingList()) {
                                        if (gwp.getRegion() == reg.regionValue && gwp.getPort() == reg.port) {
                                            toBeUpdated = false;
                                            break;
                                        }
                                    }
                                    if ( toBeUpdated ) break;
                                }
                            }
                            if ( toBeUpdated ) {
                                log.info("Updating route definition "+hts.getRouteId());
                                grpcUpdateOneRoute(hts.getRouteId(),hts.getMaxCopy(),true);
                            }

                            // now check the sessions
                            if (!consoleConfig.isHeliumGrpcSkfEnable()) continue;
                            this.refreshOneRouteSkf(hts.getRouteId());
                        }
                    }
                    _i++;
                } while ( htss != null && htss.size() > 0 );

            } finally {
                this.runningJobs--;
            }
            log.debug("End Running initialNovaSessionRefresh - duration " + (Now.NowUtcMs() - start) + "ms");
        }
        this.initialSessionRefreshDone = true;
    }

    protected boolean initialRouteCheckDone = false;
    @Scheduled(fixedDelay = 120_000, initialDelay = 300_000)
    protected void initialRouteCheck() {
        // make sure the declared route have a corresponding tenant
        if (!this.initialSessionRefreshDone) return; // make sure we run it after other refresh
        if (this.initialRouteCheckDone) return; // not run twice

        route_list_res_v1 routes = this.grpcListRoutes();
        if ( routes != null ) {
            log.info("initialRouteCheck - found "+routes.getRoutesCount()+" routes registered ");
            int anotherServer=0;
            int found=0;
            int error=0;
            for (route_v1 r : routes.getRoutesList()) {
                if ( r.getServer().getHost().compareToIgnoreCase(consoleConfig.getHeliumRouteHost()) == 0 ){
                    // search if it has a tenant
                   List<HeliumTenantSetup> hts = heliumTenantSetupRepository.findHeliumTenantSetupByRouteId(r.getId());
                   if ( hts == null || hts.size() == 0 ) {
                       log.error("initialRouteCheck - route ("+r.getId()+") does not have tenant setup");
                       error++;
                   } else found++;
                } else {
                    anotherServer++;
                }
            }
            log.info("initialRouteCheck - end of process - total("+routes.getRoutesCount()+") found("+found+") error("+error+") external("+anotherServer+")");
            initialRouteCheckDone=true;
        } else {
            log.error("initialRouteCheck - problem in getting routes");
        }
    }


    @Autowired
    protected HeliumDeviceRepository heliumDeviceRepository;


    // Cache the skf by route & eui locally
    protected class SkfRoute {
        public String routeId;
        public HashMap<String,skf_v1> skfsByEui;
        public long refreshTime;        // Last full refresh
    }

    // cache skf by route
    private HashMap<String,SkfRoute> skfCache = new HashMap<>();


    /**
     * Optimize SKF route by considering an EUI update instead of a full route update
     * when it's possible to do it.
     * @param routeId
     * @param eui
     */
    public synchronized void refreshOneEuiSkf(String routeId, String eui) {
        eui = eui.toLowerCase();
        SkfRoute r = skfCache.get(routeId);
        if ( r != null && (Now.NowUtcMs() - r.refreshTime) < 2*Now.ONE_HOUR ) {
            // get new information about this EUI
            log.debug("refreshOneEuiSkf - use cache for "+eui);
            Internal.DeviceSession s = redisDeviceRepository.getDeviceDetails(eui);
            String ntwSEncKey = HexaConverters.byteToHexString(s.getNwkSEncKey().toByteArray());
            String devaddr = HexaConverters.byteToHexString(s.getDevAddr().toByteArray());
            int iDevAddr = Stuff.hexStrToInt(devaddr);

            LinkedList<SkfUpdate> skfToRem = new LinkedList<>();
            // find the previous one and remove it
            skf_v1 old = r.skfsByEui.get(eui);
            if ( old != null) {
                log.debug("Key "+old.getSessionKey()+" found for deletion");
                SkfUpdate su = new SkfUpdate();
                su.devAddr = old.getDevaddr();
                su.session = old.getSessionKey();
                skfToRem.add(su);
                // update the previous entry in cache
                r.skfsByEui.remove(eui);
            } else {
                // debug
                for ( String _eui : r.skfsByEui.keySet() ) {
                    log.debug("> "+_eui);
                }
            }

            LinkedList<SkfUpdate> skfToAdd = new LinkedList<>();
            SkfUpdate su = new SkfUpdate();
            su.devAddr = iDevAddr;
            su.session = ntwSEncKey;
            skfToAdd.add(su);
            log.debug("Key "+ntwSEncKey+" to be added");
            grpcUpdateSessions(skfToAdd,skfToRem,routeId);

            skf_v1 n = skf_v1.newBuilder()
                    .setDevaddr(iDevAddr)
                    .setRouteId(routeId)
                    .setSessionKey(ntwSEncKey)
                    .setMaxCopies(SKFS_MAX_COPIES)
                    .build();
            r.skfsByEui.put(eui,n);
        } else {
            this.refreshOneRouteSkf(routeId);
        }
    }


    public synchronized void refreshOneRouteSkf(String routeId) {

        // Trace
        log.debug("refreshOneRouteSkf - "+routeId);

        // get the route SKF entries
        List<skf_v1> inRouteSkfs =  grpcListSessionsByDevaddr(0,routeId);
        if ( inRouteSkfs == null ) inRouteSkfs = new ArrayList<>();

        // create cache entry for the next pass
        SkfRoute r = skfCache.get(routeId);
        if ( r != null ) skfCache.remove(routeId);
        r = new SkfRoute();
        r.refreshTime = Now.NowUtcMs();
        r.routeId = routeId;
        r.skfsByEui = new HashMap<>();

        ArrayList<skf_v1> toAdd = new ArrayList<>();
        ArrayList<skf_v1> toRem = new ArrayList<>();
        ArrayList<skf_v1> toKep = new ArrayList<>();

        // get the active sessions
        HeliumTenantSetup hts = heliumTStoNovaProxyService.getHTSByRouteId(routeId);
        if ( hts == null ) {
            log.error("Try to refresh a route but the route does not exists in database");
        }
        // get associated devices
        Slice<HeliumDevice> devices = heliumDeviceRepository.findHeliumDeviceByTenantUUID(hts.getTenantUUID(), PageRequest.of(0, 500));
        boolean quit = false;
        do {
            for ( HeliumDevice hd : devices.getContent() ) {
                switch (hd.getState()) {
                    case INSERTED:
                    case ACTIVE:
                    case INACTIVE:
                        Internal.DeviceSession s = redisDeviceRepository.getDeviceDetails(hd.getDeviceEui());
                        if ( s == null ) {
                            // no session yet for that device (just inserted)
                            log.debug("refreshOneRouteSkf - session not ready for "+hd.getDeviceEui());
                            continue;
                        }
                        String ntwSEncKey = HexaConverters.byteToHexString(s.getNwkSEncKey().toByteArray());
                        //log.debug("### Ntwks Key "+ntwSEncKey);
                        String devaddr = HexaConverters.byteToHexString(s.getDevAddr().toByteArray());
                        int iDevAddr = Stuff.hexStrToInt(devaddr);
                        boolean keep = false;
                        for (skf_v1 skf : inRouteSkfs) {
                            if (skf.getDevaddr() == iDevAddr && skf.getSessionKey().compareToIgnoreCase(ntwSEncKey) == 0) {
                                // found
                                toKep.add(skf);
                                r.skfsByEui.put(hd.getDeviceEui().toLowerCase(),skf);
                                keep = true;
                                break;
                            }
                        }
                        if (!keep) {
                            // missing entry
                            skf_v1 sa = skf_v1.newBuilder()
                                    .setDevaddr(iDevAddr)
                                    .setSessionKey(ntwSEncKey)
                                    .setRouteId(routeId)
                                    .setMaxCopies(SKFS_MAX_COPIES)
                                    .build();
                            toAdd.add(sa);
                            r.skfsByEui.put(hd.getDeviceEui().toLowerCase(),sa);
                        }
                        break;
                    default:
                    case DEACTIVATED:
                    case OUTOFDCS:
                    case DELETED:
                    case DISABLED:
                        log.debug("refreshOneRouteSkf - "+hd.getDeviceEui()+" not added due to state "+hd.getState());
                        break;
                }
            }
            if ( devices.hasNext() ) {
                devices = heliumDeviceRepository.findHeliumDeviceByTenantUUID(hts.getTenantUUID(), devices.nextPageable());
            } else {
                quit = true;
            }
        } while ( devices != null && !quit );

        // search for session to be removed
        boolean hasNonEmpty = false;            // search & make sure this route have the dummy session keys
                                                // @Todo - to manage devaddr increase, we should count the dummy skf
                                                //         verify the number with devaddr size and rebuild the dummy
                                                //          skf based on this.
                                                // @Todo - when SKF is disable we should remove all the existing SKFS
        int dummySkfs = 0;
        for ( skf_v1 skf : inRouteSkfs ) {
            boolean found = false;
            for ( skf_v1 keep : toKep ) {
                if ( skf == keep ) {
                    found = true;
                    break;
                }
            }
            if ( ! found ) {
                if ( !this.isRandomSkf(skf.getDevaddr(),skf.getSessionKey(),routeId) ) {
                    toRem.add(skf);
                } else {
                    hasNonEmpty = true;
                    dummySkfs++;
                }
            }
        }

        // store in cache the updated skfs
        skfCache.put(routeId,r);

        // package this for update function
        LinkedList<SkfUpdate> skfToAdd = new LinkedList<>();
        for ( skf_v1 s : toAdd ) {
            // check if exist
            if (skfToAdd.parallelStream().noneMatch(skf -> (skf.devAddr == s.getDevaddr() && skf.session.compareToIgnoreCase(s.getSessionKey()) == 0))) {
                SkfUpdate su = new SkfUpdate();
                su.devAddr = s.getDevaddr();
                su.session = s.getSessionKey();
                skfToAdd.add(su);
            } else {
                log.warn("The route "+routeId+" contains redundant add skf for devAddr "+s.getDevaddr()+ " key "+s.getSessionKey());
            }
        }
        LinkedList<SkfUpdate> skfToRem = new LinkedList<>();
        for ( skf_v1 s : toRem ) {
            if (skfToRem.parallelStream().noneMatch(skf -> (skf.devAddr == s.getDevaddr() && skf.session.compareToIgnoreCase(s.getSessionKey()) == 0))) {
                SkfUpdate su = new SkfUpdate();
                su.devAddr = s.getDevaddr();
                su.session = s.getSessionKey();
                skfToRem.add(su);
            } else {
                log.warn("The route "+routeId+" contains redundant del skf for devAddr "+s.getDevaddr()+ " key "+s.getSessionKey());
            }
        }
        grpcUpdateSessions(skfToAdd,skfToRem,routeId);

        // fix the route with non empty skf missing
        if ( ! hasNonEmpty && ! consoleConfig.isHeliumRouteRejectEmptySKF() ) {
            log.warn("We found a route without the non empty skf entry");
            this.grpcAddRandomSkf(routeId);
        }

    }


    // ---------------------------------------
    // MANAGE THE EUIs Route update
    // ---------------------------------------

    // Manage asynchronous interface with route
    protected final ArrayList<String> delayedRouteRemoval = new ArrayList<>();

    public void addDelayedRouteRemoval(String routeId) {
        synchronized (delayedRouteRemoval) {
            this.delayedRouteRemoval.add(routeId);
        }
    }

    public String immediateRouteCreation(String tenantId, int maxCopy) {
        route_v1 r = grpcCreateNewRoute(tenantId,maxCopy);
        if ( r != null ) return r.getId();
        return null;
    }

    // List of deviceEUI we want the add / remove in the list
    protected final ArrayList<NovaDevice> delayedEuisRefreshAddition = new ArrayList<>();
    protected final ArrayList<NovaDevice> delayedEuisRefreshRemoval = new ArrayList<>();

    protected void addDelayedEuisRefreshAddition(NovaDevice dev) {
        if (dev.routeId == null) return;
        synchronized (delayedEuisRefreshAddition) {
            boolean found = false;
            for ( NovaDevice d : this.delayedEuisRefreshAddition ) {
                if ( d.devEui.compareToIgnoreCase(dev.devEui) == 0 ) { found = true; break; }
            }
            if ( !found ) {
                log.debug("To be added " + dev.devEui + " (" + dev.appEui + ") " + dev.routeId);
                this.delayedEuisRefreshAddition.add(dev);
            }
        }
    }

    protected void addDelayedEuisRefreshRemoval(NovaDevice dev) {
        if (dev.routeId == null) return;
        synchronized (delayedEuisRefreshRemoval) {
            boolean found = false;
            for ( NovaDevice d : this.delayedEuisRefreshRemoval ) {
                if ( d.devEui.compareToIgnoreCase(dev.devEui) == 0 ) { found = true; break; }
            }
            if ( !found ) {
                log.debug("To be deleted " + dev.devEui + " (" + dev.appEui + ") " + dev.routeId);
                this.delayedEuisRefreshRemoval.add(dev);
            }
        }
    }

    private boolean flushDelayedEuisUpdateRunning = false;
    /**
     * On regular basis we update the route on Nova backend based on the movement made on the device
     * deletion, creation, deactivation, reactivation ...
     */
    @Scheduled(fixedRateString = "${helium.nova.publish.delayed.scanPeriod}", initialDelay = 15_000)
    protected void flushDelayedEuisUpdate() {
        if (!this.serviceEnable) return;
        this.flushDelayedEuisUpdateRunning = true;
        this.runningJobs++;
        long start = Now.NowUtcMs();
        try {

            // First delete the route mark as to be deleted
            ArrayList<String> routeRemoval = new ArrayList<>();
            synchronized (delayedRouteRemoval) {
                routeRemoval.addAll(delayedRouteRemoval);
                this.delayedRouteRemoval.clear();
            }
            for ( String routeId : routeRemoval ) {
                if ( ! grpcDeleteRoute(routeId) ) {
                    this.addDelayedRouteRemoval(routeId);   // in case of problem, retry later
                }
            }


            // Compute the real list (priority on removal)
            // Add = Add - Remove
            // Remove = Remove
            HashMap<String,ArrayList<NovaDevice>> toRemove = new HashMap<>();
            synchronized (delayedEuisRefreshRemoval) {
                for ( NovaDevice n : delayedEuisRefreshRemoval ) {
                    ArrayList<NovaDevice> trh = toRemove.get(n.routeId);
                    if ( trh == null ) {
                        trh = new ArrayList<>();
                        toRemove.put(n.routeId,trh);
                    }
                    trh.add(n);
                }
                this.delayedEuisRefreshRemoval.clear();
            }

            HashMap<String,ArrayList<NovaDevice>> toAdd = new HashMap<>();
            synchronized (delayedEuisRefreshAddition) {
                for ( NovaDevice n : delayedEuisRefreshAddition ) {
                    boolean found = false;
                    ArrayList<NovaDevice> _toremove = toRemove.get(n.routeId);
                    if ( _toremove != null ) {
                        for (NovaDevice r : _toremove) {
                            if (n.devEui.compareToIgnoreCase(r.devEui) == 0
                                    && n.appEui.compareToIgnoreCase(r.appEui) == 0) {
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        ArrayList<NovaDevice> _toAdd = toAdd.get(n.routeId);
                        if ( _toAdd == null ) {
                            _toAdd = new ArrayList<>();
                            toAdd.put(n.routeId,_toAdd);
                        }
                        _toAdd.add(n);
                    }
                }
                this.delayedEuisRefreshAddition.clear();
            }

            if ( toRemove.size() > 0 ) {
                for ( String routeId: toRemove.keySet() ) {
                    ArrayList<NovaDevice> _toRemove = toRemove.get(routeId);
                    if ( ! grpcAddRemoveInRoutes(_toRemove,routeId, false) ) {
                        // restore the removal for next pass
                        for ( NovaDevice d : _toRemove ) {
                            this.addDelayedEuisRefreshRemoval(d);
                        }
                    }
                }
            }
            if ( toAdd.size() > 0 ) {
                for ( String routeId: toAdd.keySet() ) {
                    ArrayList<NovaDevice> _toAdd = toAdd.get(routeId);
                    if (!grpcAddRemoveInRoutes(_toAdd, routeId,true)) {
                        // restore the removal for next pass
                        for (NovaDevice d : _toAdd) {
                            this.addDelayedEuisRefreshAddition(d);
                        }
                    }
                }
            }

        } finally {
            this.runningJobs--;
            this.flushDelayedEuisUpdateRunning = false;
        }
        log.debug("End Running flushDelayedEuisUpdate - duration " + (Now.NowUtcMs() - start) + "ms");

    }


    // ----------------------------
    // public handlers

    public boolean deactivateDevices(List<NovaDevice> devices) {
        for (NovaDevice d : devices) {
            this.addDelayedSessionRefresh(d.devEui);
            this.addDelayedEuisRefreshRemoval(d);
            log.debug("Deactivating device " + d.devEui);
        }
        return true;
    }

    public boolean activateDevices(List<NovaDevice> devices) {
        for (NovaDevice d : devices) {
            this.addDelayedSessionRefresh(d.devEui);
            this.addDelayedEuisRefreshAddition(d);
            log.debug("Activating device " + d.devEui);
        }
        return true;
    }


    // ===========================================================
    // GRPC Interface
    // ===========================================================
    @Autowired
    protected ConsoleConfig consoleConfig;

    protected static class RegionSupported {
        public String regionString;
        public region regionValue;
        public int port;
    }

    private byte[] privateKey;
    private ByteString owner;
    private Ed25519Signer signer;
    protected boolean grpcInitOk = false;

    private int netIdValue = 0;
    private ArrayList<RegionSupported> regionsSupported;

    @Autowired
    protected HeliumParameterService heliumParameterService;

    @PostConstruct
    private void loadKey() {
        log.info("Init Nova GRPC setup");

        // For some demo environments it can be good to not pollute with route update
        // like for testing the migrations of devices.
        if ( ! consoleConfig.isHeliumGrpcEnable() ) {
            log.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            log.warn("Nova GRPC service is disabled, no route will be created");
            log.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            this.grpcInitOk=false;
            return;
        }

        // Load Private key
        this.privateKey = new byte[64];
        try {
            InputStream inputStream = new FileInputStream(consoleConfig.getHeliumGrpcPrivateKeyfilePath());

            int b;
            int k = 0;
            while ((b = inputStream.read()) != -1) {
                // verifiy key header should be 1 for type of key
                if (k == 0 && b != 1) break;
                if (k > 65) break;
                if (k > 0 && k < 65) {
                    privateKey[k - 1] = (byte) b;
                }
                k++;
            }
            if (k != 65) {
                // error
                log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                log.error("Invalid private keyfile");
                log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                ConsoleApplication.requestingExitForStartupFailure = true;
                return;
            }

        } catch (IOException x) {
            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            log.error("Impossible to access private key file " + x.getMessage());
            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            ConsoleApplication.requestingExitForStartupFailure = true;
            return;
        }

        // Prepare owner information
        try {
            byte[] owner_b = Base58.decode(consoleConfig.getHeliumGprcPublicKey());
            if (owner_b.length == 38) {
                byte owner_b2[] = new byte[33];
                for (int i = 0; i < 33; i++) {
                    owner_b2[i] = owner_b[i + 1];
                }
                this.owner = ByteString.copyFrom(owner_b2);
            } else if (owner_b.length == 37) {
                // no leading  0
                byte owner_b2[] = new byte[33];
                for (int i = 0; i < 33; i++) {
                    owner_b2[i] = owner_b[i];
                }
                this.owner = ByteString.copyFrom(owner_b2);
            } else {
                log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                log.error("The public key size is not valid");
                log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                ConsoleApplication.requestingExitForStartupFailure = true;
                return;
            }
        } catch (ITParseException x) {
            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            log.error("Impossible to parse Public Key with Base58");
            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            ConsoleApplication.requestingExitForStartupFailure = true;
            return;
        }

        // Prepare the encryption elements
        Ed25519PrivateKeyParameters secretKeyParameters = new Ed25519PrivateKeyParameters(this.privateKey, 0);
        signer = new Ed25519Signer();
        signer.init(true, secretKeyParameters);

        // Verify the rest of the configuration
        String _netId = consoleConfig.getHeliumRouteNetid();
        if ( _netId != null && _netId.length() > 0 ) {
            netIdValue = Stuff.hexStrToInt(_netId);
        }

        regionsSupported = new ArrayList<>();
        if ( consoleConfig.getHeliumRouteRegions().length() > 0 ) {
            for ( String reg : consoleConfig.getHeliumRouteRegions().split(",") ) {
                RegionSupported r = new RegionSupported();
                String[] regionData = reg.split(":");
                if ( regionData.length == 2 ) {
                    r.regionString = regionData[0];
                    try {
                        r.port = Integer.parseInt(regionData[1]);
                    } catch ( NumberFormatException x) {
                        log.error("Invalid port format for region "+r.regionString);
                        r.port = 0;
                    }
                    //## accepted regions : US915,EU868,EU433,CN470,CN779,AU915,AS923_1,KR920,IN865,AS923_2,AS923_3,AS923_4,AS923_1B,
                    //##                    CD900_1A,RU864,EU868_A,EU868_B,EU868_C,EU868_D,EU868_E,EU868_F,AU915_SB1,AU915_SB2,
                    //##                    AS923_1A,AS923_1C,AS923_1D,AS923_1E,AS923_1F
                    switch (regionData[0]) {
                        case "US915": r.regionValue = region.US915; break;
                        case "EU868": r.regionValue = region.EU868; break;
                        case "CN470": r.regionValue = region.CN470; break;
                        case "AU915": r.regionValue = region.AU915; break;
                        case "AS923_1": r.regionValue = region.AS923_1; break;
                        case "KR920": r.regionValue = region.KR920; break;
                        case "IN865": r.regionValue = region.IN865; break;
                        case "AS923_2": r.regionValue = region.AS923_2; break;
                        case "AS923_3": r.regionValue = region.AS923_3; break;
                        case "AS923_4": r.regionValue = region.AS923_4; break;
                        case "AS923_1B": r.regionValue = region.AS923_1B; break;
                        case "CD900_1A": r.regionValue = region.CD900_1A; break;
                        case "RU864": r.regionValue = region.RU864; break;
                        case "EU868_A": r.regionValue = region.EU868_A; break;
                        case "EU868_B": r.regionValue = region.EU868_B; break;
                        case "EU868_C": r.regionValue = region.EU868_C; break;
                        case "EU868_D": r.regionValue = region.EU868_D; break;
                        case "EU868_E": r.regionValue = region.EU868_E; break;
                        case "EU868_F": r.regionValue = region.EU868_F; break;
                        case "AU915_SB1": r.regionValue = region.AU915_SB1; break;
                        case "AU915_SB2": r.regionValue = region.AU915_SB2; break;
                        case "AS923_1A": r.regionValue = region.AS923_1A; break;
                        case "AS923_1C": r.regionValue = region.AS923_1C; break;
                        case "AS923_1D": r.regionValue = region.AS923_1D; break;
                        case "AS923_1E": r.regionValue = region.AS923_1E; break;
                        case "AS923_1F": r.regionValue = region.AS923_1F; break;
                        default:
                            log.error("Unsupported Region "+r.regionString);
                            r.port = 0;
                            break;
                    }
                    if ( r.port != 0 ) regionsSupported.add(r);
                }
            }
        }

        if (   netIdValue == 0
            || consoleConfig.getHeliumRouteOui() == 0
            || consoleConfig.getHeliumRouteHost().length() == 0
            || regionsSupported.size() == 0
        ) {
            log.error("############################################################################");
            log.error("Impossible to start GRPC - properties file is not correctly setup");
            log.error("############################################################################");
            return;
        }

        // Verify if the OUI setup has changed, in this case we need to rebuild all the routes
        HeliumParameter oui = heliumParameterService.getParameter(HeliumParameterService.PARAM_HELIUM_OUI);
        if ( oui != null && oui.getLongValue() != consoleConfig.getHeliumRouteOui() ) {
            log.warn("############################################################################");
            log.warn("The Helium OUI has changed, we clear the existing route for creating new one");
            log.warn("############################################################################");
            Tools.sleep(20_000);

            int _i = 0, _j = 0;
            List<HeliumTenantSetup> htss = null;
            do {
                htss = heliumTenantSetupRepository.findAllByTemplate(false, PageRequest.of(_i, 100));
                for (HeliumTenantSetup hts : htss) {
                    if (hts.getRouteId() != null && !hts.isTemplate()) {
                        // process one route
                        hts.setRouteId(null);
                        heliumTenantSetupRepository.save(hts);
                    }
                }
                _i++;
            } while (htss != null && htss.size() > 0);

            oui.setLongValue(consoleConfig.getHeliumRouteOui());
            heliumParameterService.flushParameter(oui);
        }

        this.grpcInitOk = true;
    }

    private ArrayList<devaddr_constraint_v1> addresses = null;
    private ArrayList<devaddr_constraint_v1> getAddresses() {
        if ( addresses != null ) return addresses;

        long start = Now.NowUtcMs();
        log.debug("GRPC get addresses ");

        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            orgGrpc.orgBlockingStub stub = orgGrpc.newBlockingStub(channel);

            org_get_req_v1 getToSign = org_get_req_v1.newBuilder()
                    .setOui(consoleConfig.getHeliumRouteOui())
                    .build();

            org_res_v1 response = stub.get(getToSign);
            if ( response != null && response.getDevaddrConstraintsCount() > 0) {
                this.addresses = new ArrayList<>();
                addresses.addAll(response.getDevaddrConstraintsList());
                log.debug("GPRC found "+addresses.size()+"addresses range");
                log.debug("GPRC get addresses duration " + (Now.NowUtcMs() - start) + "ms");
                return addresses;
            }
        } catch ( Exception x ) {
            log.error("GRPC get addresses error with message "+x.getMessage());
            prometeusService.addHeliumTotalError();
        } finally {
            if ( channel != null ) channel.shutdown();
            prometeusService.addHeliumApiTotalTimeMs(start);
        }
        return null;
    }



    private boolean grpcDeleteRoute(String routeId) {
        if ( ! this.grpcInitOk ) return false;

        long start = Now.NowUtcMs();
        log.debug("GRPC delete route "+routeId);

        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);

            route_delete_req_v1 delToSign = route_delete_req_v1.newBuilder()
                    .setId(routeId)
                    .setTimestamp(start)
                    .setSigner(this.owner)
                    .clearSignature()
                    .build();
            byte[] requestToSignContent = delToSign.toByteArray();
            this.signer.update(requestToSignContent, 0, requestToSignContent.length);
            byte[] signature = signer.generateSignature();

            route_res_v1 response = stub.delete(route_delete_req_v1.newBuilder()
                    .setId(routeId)
                    .setTimestamp(start)
                    .setSigner(this.owner)
                    .setSignature(ByteString.copyFrom(signature))
                    .build());

            log.debug("GPRC route deletion duration " + (Now.NowUtcMs() - start) + "ms");
            log.debug("GRPC deletion " + response.getRoute().getId());
            return true;
        } catch ( Exception x ) {
            log.error("GRPC route deletion error for route "+routeId+" with message "+x.getMessage());
            prometeusService.addHeliumTotalError();
        } finally {
            if ( channel != null ) channel.shutdown();
            prometeusService.addHeliumApiTotalTimeMs(start);
        }
        return false;
    }


    private route_v1 grpcCreateNewRoute(String tenantId, int max_copy) {

        if ( ! this.grpcInitOk ) return null;


        StreamObserver<route_devaddr_ranges_res_v1> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(route_devaddr_ranges_res_v1 value) {
                log.debug("DevAddr Updated");
            }

            @Override
            public void onError(Throwable t) {
                log.warn("DevAddr update failed");
                log.error(t.getMessage());
                throw new RuntimeException();
            }

            @Override
            public void onCompleted() {
                log.debug("End of DevAddr updates");
            }

        };


        long start = Now.NowUtcMs();
        log.debug("GRPC Create route for tenant "+tenantId);
        ManagedChannel channel = null;

        List<devaddr_constraint_v1> addrs = this.getAddresses();
        if ( addrs == null ) {
            log.error("Cant create route due to problem on getting devaddr list");
            return null;
        }

        try {
            channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);
            routeGrpc.routeStub stubAdr = routeGrpc.newStub(channel);

            ArrayList<protocol_gwmp_mapping_v1> regions = new ArrayList<>();
            for ( RegionSupported r : this.regionsSupported ) {
                protocol_gwmp_mapping_v1 gwmpm = protocol_gwmp_mapping_v1.newBuilder()
                        .setRegion(r.regionValue)
                        .setPort(r.port)
                        .build();
                regions.add(gwmpm);
            }

            protocol_gwmp_v1 gwmp = protocol_gwmp_v1.newBuilder()
                    .addAllMapping(regions)
                    .build();

            server_v1 server = server_v1.newBuilder()
                    .setHost(consoleConfig.getHeliumRouteHost()) // https:://
                    .setPort(this.regionsSupported.get(0).port)
                    .setGwmp(gwmp)
                    .build();

            route_v1 route = route_v1.newBuilder()
                    .setNetId(netIdValue)
                    .setOui(consoleConfig.getHeliumRouteOui())
                    .setMaxCopies(max_copy)
                    .setActive(true) // something I can set true / false to lock an entire route
                    .setLocked(false) // defined by the router when our of DC
                    .setIgnoreEmptySkf(consoleConfig.isHeliumRouteRejectEmptySKF()) // when true empty devadd are blocked (avoid dummy skf) @todo
                    .setServer(server)
                    .build();

            route_create_req_v1 createToSign = route_create_req_v1.newBuilder()
                    .setOui(consoleConfig.getHeliumRouteOui())
                    .setTimestamp(start)
                    .setRoute(route)
                    .setSigner(this.owner)
                    .clearSignature()
                    .build();

            byte[] requestToSignContent = createToSign.toByteArray();
            this.signer.update(requestToSignContent, 0, requestToSignContent.length);
            byte[] signature = signer.generateSignature();

            route_res_v1 response = stub.create(route_create_req_v1.newBuilder()
                    .setOui(consoleConfig.getHeliumRouteOui())
                    .setTimestamp(start)
                    .setRoute(route)
                    .setSigner(this.owner)
                    .setSignature(ByteString.copyFrom(signature))
                    .build());

            if ( response != null ) {
                // now we need to add the devaddr to the route
                ArrayList<route_update_devaddr_ranges_req_v1> requests = new ArrayList<>();
                long now = Now.NowUtcMs();
                for( devaddr_constraint_v1 addr : addrs) {
                    devaddr_range_v1 range = devaddr_range_v1.newBuilder()
                            .setRouteId(response.getRoute().getId())
                            .setStartAddr(addr.getStartAddr())
                            .setEndAddr(addr.getEndAddr())
                            .build();

                    log.debug("Add devAddr Range : "+String.format("0x%08X",addr.getStartAddr())+" / "+String.format("0x%08X",addr.getEndAddr()));

                    route_update_devaddr_ranges_req_v1 addrToSign = route_update_devaddr_ranges_req_v1.newBuilder()
                            .setAction(action_v1.add)
                            .setTimestamp(now)
                            .setDevaddrRange(range)
                            .setSigner(this.owner)
                            .clearSignature()
                            .build();

                    byte[] addrToSignContent = addrToSign.toByteArray();
                    this.signer.update(addrToSignContent, 0, addrToSignContent.length);
                    byte[] sign = signer.generateSignature();

                    route_update_devaddr_ranges_req_v1 request = route_update_devaddr_ranges_req_v1.newBuilder()
                            .setAction(action_v1.add)
                            .setTimestamp(now)
                            .setDevaddrRange(range)
                            .setSigner(this.owner)
                            .setSignature(ByteString.copyFrom(sign))
                            .build();
                    requests.add(request);
                }

                StreamObserver<route_update_devaddr_ranges_req_v1> reqObserver = stubAdr.updateDevaddrRanges(responseObserver);
                try {
                    for (route_update_devaddr_ranges_req_v1 req : requests) {
                        reqObserver.onNext(req);
                    }
                    reqObserver.onCompleted();
                } catch ( RuntimeException x) {
                    // rollback route
                    grpcDeleteRoute(response.getRoute().getId());
                    prometeusService.addHeliumTotalError();
                    return null;
                }
            }
            // add a random skf for making sure we have skf enabled
            // we can identify it with the size : 30 chars
            this.grpcAddRandomSkf(response.getRoute().getId());

            log.debug("GPRC route creation duration " + (Now.NowUtcMs() - start) + "ms");
            log.debug("GRPC route " + response.getRoute().getId());
            return response.getRoute();
        } catch ( Exception x ) {
            log.warn("GRPC create route error "+x.getMessage());
            prometeusService.addHeliumTotalError();
        } finally {
            if ( channel != null ) channel.shutdown();
            prometeusService.addHeliumApiTotalTimeMs(start);
        }
        return null;

    }


    /**
     * Get a single route with all the deveuis inside it
     * @param routeId
     * @return
     */
    private route_v1 grpcGetOneRoute(String routeId) {
        if ( ! this.grpcInitOk ) return null;

        long start = Now.NowUtcMs();
        log.debug("GRPC GET route "+routeId);
        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);

            long now = Now.NowUtcMs();
            route_get_req_v1 requestToSign = route_get_req_v1.newBuilder()
                    .setId(routeId)
                    .setTimestamp(now)
                    .setSigner(this.owner)
                    .clearSignature()
                    .build();
            byte[] requestToSignContent = requestToSign.toByteArray();
            this.signer.update(requestToSignContent, 0, requestToSignContent.length);
            byte[] signature = signer.generateSignature();

            route_res_v1 response = stub.get(route_get_req_v1.newBuilder()
                    .setId(routeId)
                    .setTimestamp(now)
                    .setSigner(this.owner)
                    .setSignature(ByteString.copyFrom(signature))
                    .build());

            log.debug("GPRC GET route duration " + (Now.NowUtcMs() - start) + "ms");
            log.debug("GRPC route " + response.getRoute().getId());
            return response.getRoute();
        } catch ( Exception x ) {
            log.warn("GRPC GET route error "+x.getMessage());
            x.printStackTrace();
            prometeusService.addHeliumTotalError();
        } finally {
            if ( channel != null ) channel.shutdown();
            prometeusService.addHeliumApiTotalTimeMs(start);
        }
        return null;
    }


    public route_v1 grpcUpdateOneRoute(String routeId, int maxCopy) {
        return grpcUpdateOneRoute(routeId,maxCopy,false);
    }

    public route_v1 grpcUpdateOneRoute(String routeId, int maxCopy, boolean updServer) {
        if ( ! this.grpcInitOk ) return null;

        long start = Now.NowUtcMs();
        log.debug("GRPC UPDATE route (maxcopy)"+routeId);

        route_v1 oldRoute = grpcGetOneRoute(routeId);
        if ( oldRoute == null ) return null;

        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);

            server_v1 server = oldRoute.getServer();
            if ( updServer ) {
                ArrayList<protocol_gwmp_mapping_v1> regions = new ArrayList<>();
                for ( RegionSupported r : this.regionsSupported ) {
                    protocol_gwmp_mapping_v1 gwmpm = protocol_gwmp_mapping_v1.newBuilder()
                            .setRegion(r.regionValue)
                            .setPort(r.port)
                            .build();
                    regions.add(gwmpm);
                }
                protocol_gwmp_v1 gwmp = protocol_gwmp_v1.newBuilder()
                        .addAllMapping(regions)
                        .build();
                server = server_v1.newBuilder()
                        .setHost(consoleConfig.getHeliumRouteHost()) // https:://
                        .setPort(this.regionsSupported.get(0).port)
                        .setGwmp(gwmp)
                        .build();
                if ( maxCopy == -1 ) maxCopy = oldRoute.getMaxCopies();
            }

            long now = Now.NowUtcMs();
            route_v1 newRoute = route_v1.newBuilder()
                    .setId(oldRoute.getId())
                    .setNetId(oldRoute.getNetId())
                    .setOui(oldRoute.getOui())
                    .setServer(server)
                    .setMaxCopies(maxCopy)
                    .setActive(oldRoute.getActive())
                    .setLocked(oldRoute.getLocked())
                    .setIgnoreEmptySkf(consoleConfig.isHeliumRouteRejectEmptySKF()) // when true empty devadd are blocked (avoid dummy skf) @todo
                    .build();

            route_update_req_v1 requestToSign = route_update_req_v1.newBuilder()
                    .setRoute(newRoute)
                    .setTimestamp(now)
                    .clearSignature()
                    .setSigner(this.owner)
                    .build();
            byte[] requestToSignContent = requestToSign.toByteArray();
            this.signer.update(requestToSignContent, 0, requestToSignContent.length);
            byte[] signature = signer.generateSignature();

            route_res_v1 response = stub.update(route_update_req_v1.newBuilder()
                    .setRoute(newRoute)
                    .setTimestamp(now)
                    .setSignature(ByteString.copyFrom(signature))
                    .setSigner(this.owner)
                    .build());

            log.debug("GPRC UPDATE route duration " + (Now.NowUtcMs() - start) + "ms");
            return response.getRoute();
        } catch ( Exception x ) {
            log.warn("GRPC UPDATE route error "+x.getMessage());
            x.printStackTrace();
            prometeusService.addHeliumTotalError();
        } finally {
            if ( channel != null ) channel.shutdown();
            prometeusService.addHeliumApiTotalTimeMs(start);
        }
        return null;
    }


    private List<eui_pair_v1> grpcGetEuiFromRoute(String routeId) {
        if ( ! this.grpcInitOk ) return null;

        long start = Now.NowUtcMs();
        log.debug("GRPC GET route EUIs "+routeId);
        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);

            long now = Now.NowUtcMs();
            route_get_euis_req_v1 requestToSign = route_get_euis_req_v1.newBuilder()
                    .setRouteId(routeId)
                    .setTimestamp(now)
                    .setSigner(this.owner)
                    .clearSignature()
                    .build();
            byte[] requestToSignContent = requestToSign.toByteArray();
            this.signer.update(requestToSignContent, 0, requestToSignContent.length);
            byte[] signature = signer.generateSignature();

            Iterator<eui_pair_v1> response = stub.getEuis(route_get_euis_req_v1.newBuilder()
                    .setRouteId(routeId)
                    .setTimestamp(now)
                    .setSigner(this.owner)
                    .setSignature(ByteString.copyFrom(signature))
                    .build());
            ArrayList<eui_pair_v1> rl = new ArrayList<>();
            while (response != null && response.hasNext()) {
                rl.add(response.next());
            }

            log.debug("GPRC GET route EUIs duration " + (Now.NowUtcMs() - start) + "ms");
            log.debug("GRPC route " + routeId + " has " + rl.size() + " entries ");
            return rl;
        } catch ( Exception x ) {
            log.warn("GRPC GET route EUIs error "+x.getMessage());
            prometeusService.addHeliumTotalError();
            x.printStackTrace();
        } finally {
            if ( channel != null ) channel.shutdown();
            prometeusService.addHeliumApiTotalTimeMs(start);
        }
        return null;
    }



    private route_list_res_v1 grpcListRoutes() {
        if ( ! this.grpcInitOk ) return null;

        long start = Now.NowUtcMs();
        log.debug("GRPC List routes ");
        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);

            long now = Now.NowUtcMs();
            route_list_req_v1 requestToSign = route_list_req_v1.newBuilder()
                    .setOui(consoleConfig.getHeliumRouteOui())
                    .setTimestamp(now)
                    .setSigner(this.owner)
                    .clearSignature()
                    .build();
            byte[] requestToSignContent = requestToSign.toByteArray();
            this.signer.update(requestToSignContent, 0, requestToSignContent.length);
            byte[] signature = signer.generateSignature();

            route_list_res_v1 response = stub.list(route_list_req_v1.newBuilder()
                    .setOui(consoleConfig.getHeliumRouteOui())
                    .setTimestamp(now)
                    .setSigner(this.owner)
                    .setSignature(ByteString.copyFrom(signature))
                    .build());
            log.debug("GPRC list route duration " + (Now.NowUtcMs() - start) + "ms");
            log.debug("GRPC routes (" + response.getRoutesCount() + ")");
            /*
            for (route_v1 route : response.getRoutesList()) {
                log.debug("GRPC route id " + route.getId() + " with " + route.getEuisList().size() + " euis");
                for (Config.eui_v1 eui : route.getEuisList()) {
                    log.debug("GRPC contains route for " + Tools.EuiStringFromLong(eui.getDevEui()) + " / " + Tools.EuiStringFromLong(eui.getAppEui()));
                }
            }
            */
            return response;
        } catch ( StatusRuntimeException x ) {
            prometeusService.addHeliumTotalError();
            log.warn("Nova Backend not reachable");
            return null;
        } finally {
            if ( channel != null ) channel.shutdown();
            prometeusService.addHeliumApiTotalTimeMs(start);
        }
    }


    public List<NovaDevice> getAllKnownDevices(String routeId) {
        ArrayList<NovaDevice> ret = new ArrayList<>();
        List<eui_pair_v1> euis = grpcGetEuiFromRoute(routeId);
        if ( euis != null ) {
            for (eui_pair_v1 eui : euis) {
                NovaDevice n = new NovaDevice();
                n.devEui = Tools.EuiStringFromLong(eui.getDevEui());
                n.appEui = Tools.EuiStringFromLong(eui.getAppEui());
                n.routeId = eui.getRouteId();
                ret.add(n);
            }
            return ret;
        }
        return null;
    }



    /**
     * Update an existing route by adding or removing EUIs in this route
     * boolean to select add (true) or removal (false)
     * @param devices
     * @param add
     */
    public boolean grpcAddRemoveInRoutes(List<NovaDevice> devices, String routeId, boolean add) {

        StreamObserver<route_euis_res_v1> responseObserver = new StreamObserver<route_euis_res_v1>() {
            @Override
            public void onNext(route_euis_res_v1 value) {
                log.debug("Eui Updated");
            }

            @Override
            public void onError(Throwable t) {
                log.debug("Eui update failed "+t.getMessage());
            }

            @Override
            public void onCompleted() {
                log.debug("End of Eui updates");
            }
        };


        if ( ! this.grpcInitOk ) return false;
        long start = Now.NowUtcMs();
        if ( add ) {
            log.debug("GRPC Add routes ("+devices.size()+") in "+routeId);
        } else {
            log.debug("GRPC Remove routes ("+devices.size()+") in "+routeId);
        }

        ManagedChannel channel = ManagedChannelBuilder.forAddress(
                consoleConfig.getHeliumGrpcServer(),
                consoleConfig.getHeliumGrpcPort()
        ).usePlaintext().build();
        routeGrpc.routeStub stub = routeGrpc.newStub(channel);

        long now = Now.NowUtcMs();
        ArrayList<route_update_euis_req_v1> requests = new ArrayList<>();
        for( NovaDevice device : devices) {
            log.debug("  Process DevEUI "+device.devEui);
            eui_pair_v1 eui = eui_pair_v1.newBuilder()
                    .setDevEui(Tools.EuiStringToLong(device.devEui))
                    .setAppEui(Tools.EuiStringToLong(device.appEui))
                    .setRouteId(routeId)
                    .build();

            route_update_euis_req_v1 requestToSign = route_update_euis_req_v1.newBuilder()
                    .setTimestamp(now)
                    .setActionValue(((add)?action_v1.add_VALUE:action_v1.remove_VALUE))
                    .setEuiPair(eui)
                    .setSigner(this.owner)
                    .clearSignature()
                    .build();
            byte[] requestToSignContent = requestToSign.toByteArray();
            this.signer.update(requestToSignContent, 0, requestToSignContent.length);
            byte[] signature = signer.generateSignature();

            route_update_euis_req_v1 request = route_update_euis_req_v1.newBuilder()
                    .setTimestamp(now)
                    .setActionValue(((add)?action_v1.add_VALUE:action_v1.remove_VALUE))
                    .setEuiPair(eui)
                    .setSigner(this.owner)
                    .setSignature(ByteString.copyFrom(signature))
                    .build();

            requests.add(request);
        }

        long startNova = Now.NowUtcMs();
        StreamObserver<route_update_euis_req_v1> reqObserver = stub.updateEuis(responseObserver);
        try {
            for ( route_update_euis_req_v1 req : requests ) {
                reqObserver.onNext(req);
            }
            reqObserver.onCompleted();
        } catch ( RuntimeException x ) {
            reqObserver.onError(x);
            prometeusService.addHeliumTotalError();
            log.error("GRPC error during route update "+x.getMessage());
            x.printStackTrace();
            return false;
        } finally {
            if ( channel != null ) channel.shutdown();
            prometeusService.addHeliumApiTotalTimeMs(startNova);
        }
        return true;
    }

    // ==============================================
    // Manage devices sessions
    // ==============================================


    /**
     * The Session Key Filter is only activated when you have,
     * for a given route, at least one SKF registered for each devAddr
     * (sounds a bit complex to not say stupid but that is the current implementation)
     * To avoid having uplinks repeated for every empty tenant on a given devaddr and
     * avoid DCs drain, we need to secure this with one dummy session key on every
     * route / devaddr. and we need to maintain that entry when the data are consolidated
     * @param routeId
     * @param addr
     */
    public void grpcAddRandomSkf(String routeId) {
        // when emptySkf is rejected we don't need to add the dummy keys
        if ( consoleConfig.isHeliumRouteRejectEmptySKF() ) return;

        // RandomSkf pattern is
        // 91919192xxxxxxxxxxxxxxxxxxDEVADDR
        LinkedList<SkfUpdate> add = new LinkedList<>();
        LinkedList<SkfUpdate> del = new LinkedList<>();
        for ( devaddr_constraint_v1 addr : this.getAddresses() ) {
            for ( int a = addr.getStartAddr() ; a <= addr.getEndAddr() ; a++){
                SkfUpdate skf = new SkfUpdate();
                skf.devAddr = a;
                String random = RandomString.getRandomHexString(16);
                String devAddr = String.format("%08X", a);
                skf.session = "91919193" + random + "" + devAddr;
                add.add(skf);
            }
        }
        this.grpcUpdateSessions(add,del,routeId);
    }

    public boolean isRandomSkf(int addr, String session, String routeId) {
        // if emptySkf is rejected, we don't need the dummy skfs
        if ( consoleConfig.isHeliumRouteRejectEmptySKF() ) return false;
        // else we need to verify it.
        return (session.length() == 32 && session.startsWith("91919193") && session.endsWith(String.format("%08X",addr)));
    }


    private List<skf_v1> grpcListSessionsByDevaddr(int devAddr, String routeId) {

        if ( ! this.grpcInitOk ) return null;

        long start = Now.NowUtcMs();
        log.debug("GRPC List sessions for "+String.format("0x%08X",devAddr)+ " in route "+routeId);
        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);

            long now = Now.NowUtcMs();
            Iterator<skf_v1> response;
            if ( devAddr != 0 ) {
                // get the list of skf for a route + device

                route_skf_get_req_v1 requestToSign = route_skf_get_req_v1.newBuilder()
                        .setRouteId(routeId)
                        .setDevaddr(devAddr)
                        .setTimestamp(now)
                        .setSigner(this.owner)
                        .clearSignature()
                        .build();
                byte[] requestToSignContent = requestToSign.toByteArray();
                this.signer.update(requestToSignContent, 0, requestToSignContent.length);
                byte[] signature = signer.generateSignature();

                route_skf_get_req_v1 request = route_skf_get_req_v1.newBuilder()
                            .setRouteId(routeId)
                            .setDevaddr(devAddr)
                            .setTimestamp(now)
                            .setSigner(this.owner)
                            .setSignature(ByteString.copyFrom(signature))
                            .build();

                response = stub.getSkfs(request);

            } else {
                // get for a route (all devaddr)
                route_skf_list_req_v1 requestToSign = route_skf_list_req_v1.newBuilder()
                        .setRouteId(routeId)
                        .setTimestamp(now)
                        .setSigner(this.owner)
                        .clearSignature()
                        .build();

                byte[] requestToSignContent = requestToSign.toByteArray();
                this.signer.update(requestToSignContent, 0, requestToSignContent.length);
                byte[] signature = signer.generateSignature();

                route_skf_list_req_v1 request = route_skf_list_req_v1.newBuilder()
                        .setRouteId(routeId)
                        .setTimestamp(now)
                        .setSigner(this.owner)
                        .setSignature(ByteString.copyFrom(signature))
                        .build();

                response = stub.listSkfs(request);

            }
            ArrayList<skf_v1> ret = new ArrayList<>();
            while (response != null && response.hasNext()) {
                ret.add(response.next());
            }

            log.debug("GPRC skf list duration " + (Now.NowUtcMs() - start) + "ms");
            log.debug("GRPC skf found "+ret.size()+" entries");
            return ret;
        } catch ( StatusRuntimeException x ) {
            prometeusService.addHeliumTotalError();
            log.warn("Nova Backend not reachable");
            return null;
        } finally {
            if ( channel != null ) channel.shutdown();
            prometeusService.addHeliumApiTotalTimeMs(start);
        }

    }

    protected class SkfUpdate {
        public int devAddr;
        public String session;
    }


    private boolean grpcUpdateSessions(
            LinkedList<SkfUpdate> toAddSession,
            LinkedList<SkfUpdate> toRemoveSession,
            String routeId
    ) {

        if ( ! this.grpcInitOk ) return false;
        long start = Now.NowUtcMs();
        log.debug("GRPC Session Update Add:"+toAddSession.size()+" Del:"+toRemoveSession.size()+" for "+routeId);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(
                consoleConfig.getHeliumGrpcServer(),
                consoleConfig.getHeliumGrpcPort()
        ).usePlaintext().build();
        routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);
        try {

            long now = Now.NowUtcMs();
            int actions = 0; // max 50 actions at a time
            while (toAddSession.size() > 0 || toRemoveSession.size() > 0) {
                ArrayList<route_skf_update_req_v1.route_skf_update_v1> updates = new ArrayList<>();
                while (toAddSession.size() > 0 && actions < 50) {
                    SkfUpdate session = toAddSession.poll();

                    boolean inRange = false;
                    for ( devaddr_constraint_v1 addr : this.getAddresses() ) {
                        if ( session.devAddr >= addr.getStartAddr() && session.devAddr <= addr.getEndAddr() ) {
                            inRange = true;
                            break;
                        }
                    }

                    if ( inRange ) {
                        route_skf_update_req_v1.route_skf_update_v1 update = route_skf_update_req_v1.route_skf_update_v1.newBuilder()
                                .setAction(action_v1.add)
                                .setDevaddr(session.devAddr)
                                .setSessionKey(session.session)
                                .setMaxCopies(SKFS_MAX_COPIES)
                                .build();
                        updates.add(update);
                    }
                    actions++;
                }
                while (toRemoveSession.size() > 0 && actions < 50) {
                    SkfUpdate session = toRemoveSession.poll();

                    boolean inRange = false;
                    for ( devaddr_constraint_v1 addr : this.getAddresses() ) {
                        if ( session.devAddr >= addr.getStartAddr() && session.devAddr <= addr.getEndAddr() ) {
                            inRange = true;
                            break;
                        }
                    }

                    if ( inRange ) {
                        route_skf_update_req_v1.route_skf_update_v1 update = route_skf_update_req_v1.route_skf_update_v1.newBuilder()
                                .setAction(action_v1.remove)
                                .setDevaddr(session.devAddr)
                                .setSessionKey(session.session)
                                .setMaxCopies(SKFS_MAX_COPIES)
                                .build();
                        updates.add(update);
                        actions++;
                    }
                }
                // execute
                if ( updates.size() > 0 ) {
                    route_skf_update_req_v1 requestToSign = route_skf_update_req_v1.newBuilder()
                            .setRouteId(routeId)
                            .addAllUpdates(updates)
                            .setTimestamp(now)
                            .setSigner(this.owner)
                            .clearSignature()
                            .build();

                    byte[] requestToSignContent = requestToSign.toByteArray();
                    this.signer.update(requestToSignContent, 0, requestToSignContent.length);
                    byte[] signature = signer.generateSignature();

                    route_skf_update_req_v1 request = route_skf_update_req_v1.newBuilder()
                            .setRouteId(routeId)
                            .addAllUpdates(updates)
                            .setTimestamp(now)
                            .setSigner(this.owner)
                            .setSignature(ByteString.copyFrom(signature))
                            .build();

                    route_skf_update_res_v1 response = stub.updateSkfs(request);
                }
                actions = 0;
            }
            log.debug("GPRC skf update duration " + (Now.NowUtcMs() - start) + "ms");
            return true;
        } catch ( StatusRuntimeException x ) {
            prometeusService.addHeliumTotalError();
            log.warn("Skf Update Nova Backend not reachable "+x.getMessage());
            return false;
        } finally {
            if ( channel != null ) channel.shutdown();
            prometeusService.addHeliumApiTotalTimeMs(start);
        }
    }

}

