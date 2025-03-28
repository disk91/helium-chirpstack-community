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

import com.google.protobuf.ByteString;
import eu.heliumiot.console.ConsoleApplication;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.jpa.db.*;
import eu.heliumiot.console.jpa.repository.HeliumDeviceRepository;
import eu.heliumiot.console.jpa.repository.HeliumTenantSetupRepository;
import eu.heliumiot.console.jpa.repository.TenantRepository;
import eu.heliumiot.console.service.interfaces.LogEntry;
import eu.heliumiot.console.service.interfaces.LogLevel;
import fr.ingeniousthings.tools.*;
import io.chirpstack.internal.DeviceSession;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.helium.grpc.*;

import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NovaService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected LogService logService;

    protected static final int SKFS_MAX_COPIES = 0;
    protected static final int GRPC_NOVA_API_TIMEOUT = 30;  // 30s timeout for GPRC to not be blocking but need a certain time for big updates
    @Autowired
    protected DeviceService deviceService;

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
    protected record SessionRefreshRequest(String devEUI, long time, long retry) {};
    protected final HashMap<String, SessionRefreshRequest> delayedSessionRefresh = new HashMap<>();
    protected final HashMap<String, String> delayedRouteRefresh = new HashMap<>();

    protected void addDelayedSessionRefresh(SessionRefreshRequest session) {
        synchronized (delayedSessionRefresh) {
            if ( ! this.delayedSessionRefresh.containsKey(session.devEUI) ) {
                this.delayedSessionRefresh.put(session.devEUI, session);
            }
        }
    }

    protected void addDelayedSessionRefresh(String devEUI) {
        addDelayedSessionRefresh(new SessionRefreshRequest(devEUI,Now.NowUtcMs(),0));
    }

    protected void addDelayedRouteRefresh(String tenantId) {
        synchronized (delayedRouteRefresh) {
            if ( ! this.delayedRouteRefresh.containsKey(tenantId) ) {
                this.delayedRouteRefresh.put(tenantId, tenantId);
            }
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

            HashMap<String,String> routes = new HashMap<>();
            if (!delayedSessionRefresh.isEmpty()) {
                ArrayList<SessionRefreshRequest> toRefresh;

                // get all the pending device to be updated to map the associated route
                synchronized (delayedSessionRefresh) {
                    toRefresh = new ArrayList<>(delayedSessionRefresh.values());
                    delayedSessionRefresh.clear();
                }

                for (SessionRefreshRequest session : toRefresh) {
                    // get routeId to be refreshed
                    String routeId = heliumTStoNovaProxyService.getRouteIdFromEui(session.devEUI);
                    if (routeId == null
                            && (session.time < (Now.NowUtcMs() - 15 * Now.ONE_MINUTE)
                            || session.retry < 3)
                    ) {
                        log.warn("flushDelayedSessionUpdate - the route is not existing for {}", session.devEUI);
                        // process later
                        addDelayedSessionRefresh(new SessionRefreshRequest(session.devEUI, session.time, session.retry + 1));
                    } else {
                        routes.putIfAbsent(routeId, routeId);
                    }
                }
            }
            if ( !delayedRouteRefresh.isEmpty() ) {
                log.warn("flushDelayedSessionUpdate - some session are still pending");
                ArrayList<String> tenantToRefresh;
                synchronized (delayedSessionRefresh) {
                    tenantToRefresh = new ArrayList<>(delayedRouteRefresh.values());
                    delayedRouteRefresh.clear();
                }
                for (String tenantId : tenantToRefresh) {
                    HeliumTenantSetup hts = heliumTenantSetupRepository.findOneHeliumTenantSetupByTenantUUID(tenantId);
                    if (hts != null) {
                        routes.putIfAbsent(hts.getRouteId(), hts.getRouteId());
                    } else {
                        log.error("flushDelayedSessionUpdate - tenant {} does not exist", tenantId);
                    }
                }
            }

            for ( String route : routes.values() ) {
                this.refreshOneRouteSkf(route);
            }

        } finally {
            this.runningJobs--;
        }
        log.debug("End Running flushDelayedSessionUpdate - duration {}ms", Now.NowUtcMs() - start);

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

    // Identify the extra session keys that are not correctly setup
    @Async
    public void verifySKFsForAGivenAddr(int addr, boolean clear) {
        String sAddr = Integer.toHexString(addr);
        long cTemplate = heliumTenantSetupRepository.count();
        int _i = 0, _j = 1;
        Page<HeliumTenantSetup> htss = null;
        do {
            htss = heliumTenantSetupRepository.findAllByTemplate(false, PageRequest.of(_i,50));
            for ( HeliumTenantSetup hts : htss ) {
                if ( hts.getRouteId() != null && !hts.isTemplate() ) {
                    Tenant t = tenantRepository.findOneTenantById(UUID.fromString(hts.getTenantUUID()));
                    String tName = "Unknown";
                    if ( t != null ) tName = t.getName();

                    log.info("[{}-{}/{}] Exploring tenant {} ({}) route {}", _i, _j, cTemplate, tName, hts.getTenantUUID(), hts.getRouteId());
                    // get all skfs for the current route and the devAddr
                    List<skf_v1> skfs =  grpcListSessionsByDevaddr(addr,hts.getRouteId());
                    HashMap<String,skf_v1> inRouteSkfs = new HashMap<>();
                    if ( skfs != null ) {
                        for (skf_v1 skf : skfs) {
                            inRouteSkfs.put(skf.getSessionKey().toLowerCase(),skf);
                        }
                    }
                    LinkedList<SkfUpdate> skfToRem = new LinkedList<>();

                    // search all devices and filter on that devAddr
                    List<HeliumDevice> devices = heliumDeviceRepository.findHeliumDeviceByTenantUuid(hts.getTenantUUID(),HeliumDeviceRepository.FIRST_DEVICE_EUI,500);
                    boolean quit = false;
                    do {
                        for ( HeliumDevice hd : devices ) {
                            DeviceSession s = deviceService.getDeviceSession(hd.getDeviceUUID());
                            if ( s == null ) continue;
                            String devaddr = HexaConverters.byteToHexString(s.getDevAddr().toByteArray());
                            //log.debug(">"+devaddr+"<>"+sAddr+"<");
                            if ( devaddr.compareToIgnoreCase(sAddr) == 0 ) {
                                // we found a device
                                String ntwSEncKey = HexaConverters.byteToHexString(s.getNwkSEncKey().toByteArray()).toLowerCase();
                                if ( inRouteSkfs.get(ntwSEncKey) != null) {
                                    if (  hd.getState() == HeliumDevice.DeviceState.INSERTED
                                        || hd.getState() == HeliumDevice.DeviceState.ACTIVE
                                        || hd.getState() == HeliumDevice.DeviceState.INACTIVE
                                    ) {
                                        // fks found
                                        inRouteSkfs.remove(ntwSEncKey);
                                    } else {
                                        // some error case
                                        log.info(">> Valid session exists for dead device: {} addr: {} nwks: {}", hd.getDeviceEui(), sAddr, ntwSEncKey);
                                    }
                                } else {
                                    // this device does not have a session registered, normal when inactive
                                    if (   hd.getState() == HeliumDevice.DeviceState.INSERTED
                                        || hd.getState() == HeliumDevice.DeviceState.ACTIVE
                                        || hd.getState() == HeliumDevice.DeviceState.INACTIVE
                                    ){
                                        log.info(">> Missing session for device: {} addr: {}", hd.getDeviceEui(), sAddr);
                                    }
                                }
                            }
                        }
                        if ( devices.size() == 500 ) {
                            devices = heliumDeviceRepository.findHeliumDeviceByTenantUuid(hts.getTenantUUID(), devices.getLast().getDeviceEui(),500);
                        } else {
                            quit = true;
                        }
                    } while ( devices != null && !quit );

                    // if some more session it's a problem ...
                    for ( String key : inRouteSkfs.keySet() ) {
                        log.info(">> Found extra sessions for addr: {} nwks: {}", sAddr, key);
                        if ( clear ) {
                            skf_v1 skf = inRouteSkfs.get(key);
                            SkfUpdate su = new SkfUpdate();
                            su.devAddr = skf.getDevaddr();
                            su.session = skf.getSessionKey();
                            skfToRem.add(su);
                        }
                    }
                    if ( clear && !skfToRem.isEmpty()) {
                        log.info(">> clear the extra skfs");
                        if ( !grpcUpdateSessions(new LinkedList<>(), skfToRem, hts.getRouteId()) ) {
                            log.error("verifySKFsForAGivenAddr - Failed to remove extra skfs");
                        }
                    }
                }
                _j++;
            }
            _i++;
        } while ( htss.hasNext() );
    }


    // Resync the route and session keys
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

                long cTemplate = heliumTenantSetupRepository.count();
                // process all routes
                int _i = 0, _j = 1;
                Page<HeliumTenantSetup> htss = null;
                do {
                    htss = heliumTenantSetupRepository.findAllByTemplate(false, PageRequest.of(_i,50));
                    for ( HeliumTenantSetup hts : htss ) {
                        if ( hts.getRouteId() != null && !hts.isTemplate() ) {
                            // process one route
                            log.info("[{}-{}/{}] Refreshing tenant {} route {}", _i, _j, cTemplate, hts.getTenantUUID(), hts.getRouteId());
                            // search the route
                            route_v1 r = grpcGetOneRoute(hts.getRouteId());
                            if ( r == null ) { log.error("A known route does not exist"); continue; }


                            // verify if route server are ok
                            boolean toBeUpdated = false;
                            boolean toBeRecreated = false;
                            if ( r.getNetId() != netIdValue ) toBeRecreated = true;
                            else {
                                if (r.getServer().getHost().compareTo(consoleConfig.getHeliumRouteHost()) != 0
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
                                        if (toBeUpdated) break;
                                    }
                                }
                            }
                            if ( toBeRecreated ) {
                                log.info("Recreate route definition {}", hts.getRouteId());
                                log.error("This is not implemented");
                                // @TODO

                                // addDelayedRouteRemoval(r.getId());
                                // delete route
                                // delete euis - cascade
                                // delete skfs - cascade

                                // create route
                                // create euis
                                // create skfs

                                // Other way => if tenantSetup is updated to have tennantUUID = "000000-0000-0000-0000-000000000000" route will be deleted on restart
                                // if helium_tenant corresponding entry is removed, the route will be recreated automatically
                                // Problem will be related to the DC balance lost and max_copy in this operation

                            } else if ( toBeUpdated ) {
                                log.info("Updating route definition {}", hts.getRouteId());
                                grpcUpdateOneRoute(hts.getRouteId(),hts.getMaxCopy(),true);
                            }

                            // now check the sessions
                            if (!consoleConfig.isHeliumGrpcSkfEnable()) continue;
                            this.refreshOneRouteSkf(hts.getRouteId());
                        }
                        _j++;
                    }
                    _i++;
                } while ( htss.hasNext() );

            } finally {
                this.runningJobs--;
            }
            log.debug("End Running initialNovaSessionRefresh - duration {}ms", Now.NowUtcMs() - start);
        }
        this.initialSessionRefreshDone = true;
    }

    @Autowired
    private TenantRepository tenantRepository;

    protected boolean initialRouteCheckDone = false;
    @Scheduled(fixedDelay = 120_000, initialDelay = 300_000)
    protected void initialRouteCheck() {
        // make sure the declared route have a corresponding tenant
        if (!this.initialSessionRefreshDone) return; // make sure we run it after other refresh
        if (this.initialRouteCheckDone) return; // not run twice

        route_list_res_v1 routes = this.grpcListRoutes();
        if ( routes != null ) {
            log.info("initialRouteCheck - found {} routes registered ", routes.getRoutesCount());
            int anotherServer=0;
            int found=0;
            int error=0;
            int toRemove=0;
            for (route_v1 r : routes.getRoutesList()) {
                if ( r.getServer().getHost().compareToIgnoreCase(consoleConfig.getHeliumRouteHost()) == 0 ){
                    // search if it has a tenant
                   List<HeliumTenantSetup> hts = heliumTenantSetupRepository.findHeliumTenantSetupByRouteId(r.getId());
                   if ( hts == null || hts.isEmpty()) {
                       log.error("initialRouteCheck - route ({}) does not have tenant setup", r.getId());
                       error++;
                   } else {
                       // when we have a tenant setup, check we have a tenant attached or delete all of this
                       if ( hts.size() > 1 ) log.warn("Multiple tenant setup for a single route");
                       Tenant t = tenantRepository.findOneTenantById(UUID.fromString(hts.get(0).getTenantUUID()));
                       if ( t == null ) {
                           log.error("initialRouteCheck - route ({}) does not have tenant", r.getId());
                           addDelayedRouteRemoval(r.getId());
                           // not the best as TenantSetup use a cache but on restart we should
                           // be in a situation where a deleted tenant is in cache
                           heliumTenantSetupRepository.delete(hts.getFirst());
                           toRemove++;
                       } else {
                           found++;
                       }
                   }
                } else {
                    anotherServer++;
                }
            }
            log.info("initialRouteCheck - end of process - total({}) found({}) removed({}) error({}) external({})", routes.getRoutesCount(), found, toRemove, error, anotherServer);
            initialRouteCheckDone=true;
        } else {
            log.error("initialRouteCheck - problem in getting routes");
        }
    }


    @Autowired
    protected HeliumDeviceRepository heliumDeviceRepository;


    private record DeviceRecord(int devaddr, String nkey, String eui){};
    private static final long collisionSkfsCacheTimeout = 20*Now.ONE_MINUTE;
    private static final long collisionSkfsCacheCleanTimeout = 10*Now.ONE_MINUTE;
    private static final int collisionSkfsCacheMaxRoute = 500;
    private static final int collisionSkfsMaxDevicePerRoute = 20_000;
    private final TinyCache<String,ArrayList<DeviceRecord>> collisionSkfsCache = new TinyCache<>(
        collisionSkfsCacheMaxRoute,
        collisionSkfsCacheTimeout,
        collisionSkfsCacheCleanTimeout
    );


    // search network collision in skfs cache for a given route
    // -1 if device non found
    public int countSkfsColisions(String tenantId, String eui) {

        ArrayList<DeviceRecord> l = collisionSkfsCache.get(tenantId);
        if ( l == null ) {
            l = new ArrayList<>();
            List<HeliumDevice> devices = heliumDeviceRepository.findHeliumDeviceByTenantUuid(tenantId,HeliumDeviceRepository.FIRST_DEVICE_EUI,500);
            boolean quit = false;
            int inRouteDev = 0;
            do {
                for ( HeliumDevice hd : devices ) {
                    switch (hd.getState()) {
                        case INSERTED:
                        case ACTIVE:
                        case INACTIVE:
                            DeviceSession s = deviceService.getDeviceSession(hd.getDeviceUUID());
                            if ( s == null ) {
                                // no session yet for that device (just inserted)
                                log.debug("countSkfsColisions - session not ready for {}", hd.getDeviceEui());
                                continue;
                            }
                            String ntwSEncKey = HexaConverters.byteToHexString(s.getNwkSEncKey().toByteArray());
                            //log.debug("### Ntwks Key "+ntwSEncKey);
                            String devaddr = HexaConverters.byteToHexString(s.getDevAddr().toByteArray());
                            int iDevAddr = Stuff.hexStrToInt(devaddr);
                            DeviceRecord d = new DeviceRecord(iDevAddr,ntwSEncKey,eui);
                            l.add(d);
                            inRouteDev++;
                            break;
                        default:
                        case DEACTIVATED:
                        case OUTOFDCS:
                        case DELETED:
                        case DISABLED:
                            break;
                    }
                }
                if ( devices.size() == 500 && inRouteDev < collisionSkfsMaxDevicePerRoute ) {
                    devices = heliumDeviceRepository.findHeliumDeviceByTenantUuid(tenantId, devices.getLast().getDeviceEui(),500);
                } else {
                    quit = true;
                }
            } while ( devices != null && !quit );
            collisionSkfsCache.put(tenantId,l);
        }

        DeviceSession s = deviceService.getDeviceSession(eui);
        if ( s == null ) return -1;

        String ntwSEncKey = HexaConverters.byteToHexString(s.getNwkSEncKey().toByteArray());
        String devaddr = HexaConverters.byteToHexString(s.getDevAddr().toByteArray());
        int iDevAddr = Stuff.hexStrToInt(devaddr);

        AtomicInteger c = new AtomicInteger(0);
        l.parallelStream().forEach( (sk) -> {
            if ( sk.devaddr == iDevAddr && sk.nkey.compareToIgnoreCase(ntwSEncKey) == 0 ) c.addAndGet(1);
        });

        return c.get();
    }

    // Check the Skfs and cache the values for 15 minutes to optimize access performance
    // and avoid spamming Nova

    private static final long routeSkfsCacheTimeout = 20*Now.ONE_MINUTE;
    private static final long routeSkfsCacheCleanTimeout = 10*Now.ONE_MINUTE;
    private static final int routeSkfsCacheMaxRoute = 500;
    private final TinyCache<String,List<skf_v1>> routeSkfsCache = new TinyCache<>(
        routeSkfsCacheMaxRoute,
        routeSkfsCacheTimeout,
        routeSkfsCacheCleanTimeout
    );

    synchronized public int existSkfs(String routeId, String eui) {
        DeviceSession s = deviceService.getDeviceSession(eui);
        if ( s == null ) return -1;

        String ntwSEncKey = HexaConverters.byteToHexString(s.getNwkSEncKey().toByteArray());
        String devaddr = HexaConverters.byteToHexString(s.getDevAddr().toByteArray());
        int iDevAddr = Stuff.hexStrToInt(devaddr);

        List<skf_v1> cskfs = this.routeSkfsCache.get(routeId);
        if ( cskfs == null ) {
            cskfs = this.grpcListSessionsByDevaddr(0, routeId);
            routeSkfsCache.put(routeId,cskfs);
        }

        if ( cskfs != null ) {
            AtomicInteger c = new AtomicInteger(0);
            cskfs.parallelStream().forEach( (sk) -> {
                if ( sk.getDevaddr() == iDevAddr && sk.getSessionKey().compareToIgnoreCase(ntwSEncKey) == 0 ) c.addAndGet(1);
            });
            return c.get();
        }
        return 0;
    }


    public List<eui_pair_v1> getEuiInARoute(String routeId) {
        return grpcGetEuiFromRoute(routeId);
    }


    // Cache the skf by route & eui locally
    protected static class SkfRoute {
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
            log.debug("refreshOneEuiSkf - use cache for {}", eui);
            DeviceSession s = deviceService.getDeviceSession(eui);
            String ntwSEncKey = HexaConverters.byteToHexString(s.getNwkSEncKey().toByteArray());
            String devaddr = HexaConverters.byteToHexString(s.getDevAddr().toByteArray());
            int iDevAddr = Stuff.hexStrToInt(devaddr);

            LinkedList<SkfUpdate> skfToRem = new LinkedList<>();
            // find the previous one and remove it
            skf_v1 old = r.skfsByEui.get(eui);
            if ( old != null) {
                log.debug("Key {} found for deletion", old.getSessionKey());
                SkfUpdate su = new SkfUpdate();
                su.devAddr = old.getDevaddr();
                su.session = old.getSessionKey();
                skfToRem.add(su);
            }
            /*
            else {
                // debug
                for ( String _eui : r.skfsByEui.keySet() ) {
                    log.debug("> {}", _eui);
                }
            }
            */

            LinkedList<SkfUpdate> skfToAdd = new LinkedList<>();
            SkfUpdate su = new SkfUpdate();
            su.devAddr = iDevAddr;
            su.session = ntwSEncKey;
            skfToAdd.add(su);
            log.debug("Key {} to be added", ntwSEncKey);
            if ( ! grpcUpdateSessions(skfToAdd,skfToRem,routeId) ) {
                log.warn("Filed to update skfs for {}", eui);
            } else {
                if ( old != null ) {
                    // update the previous entry in cache
                    r.skfsByEui.remove(eui);
                }
                // add the new entry in cache
                skf_v1 n = skf_v1.newBuilder()
                        .setDevaddr(iDevAddr)
                        .setRouteId(routeId)
                        .setSessionKey(ntwSEncKey)
                        .setMaxCopies(SKFS_MAX_COPIES)
                        .build();
                r.skfsByEui.put(eui,n);
            }

        } else {
            this.refreshOneRouteSkf(routeId);
        }
    }


    public synchronized void refreshOneRouteSkf(String routeId) {

        // Trace
        log.debug("refreshOneRouteSkf - {}", routeId);

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
        int processed = 0;
        List<HeliumDevice> devices = heliumDeviceRepository.findHeliumDeviceByTenantUuid(hts.getTenantUUID(), HeliumDeviceRepository.FIRST_DEVICE_EUI,200);
        boolean quit = false;
        if ( devices != null ) {
            do {
                for (HeliumDevice hd : devices) {
                    processed++;
                    switch (hd.getState()) {
                        case INSERTED:
                        case ACTIVE:
                        case INACTIVE:
                            DeviceSession s = deviceService.getDeviceSession(hd.getDeviceUUID());
                            if (s == null) {
                                // no session yet for that device (just inserted)
                                log.debug("refreshOneRouteSkf - session not ready for {}", hd.getDeviceEui());
                                continue;
                            }
                            String ntwSEncKey = HexaConverters.byteToHexString(s.getNwkSEncKey().toByteArray());
                            //log.debug("### Ntwks Key "+ntwSEncKey);
                            String devaddr = HexaConverters.byteToHexString(s.getDevAddr().toByteArray());
                            // trace to identify all device for a given devaddr
                            //if ( devaddr.compareToIgnoreCase("480009b1") == 0 ) {
                            //    log.info("### route: "+routeId+" dev: "+hd.getDeviceEui()+" devaddr: 0x"+devaddr);
                            // }
                            int iDevAddr = Stuff.hexStrToInt(devaddr);
                            boolean keep = false;
                            for (skf_v1 skf : inRouteSkfs) {
                                if (skf.getDevaddr() == iDevAddr && skf.getSessionKey().compareToIgnoreCase(ntwSEncKey) == 0) {
                                    // found
                                    toKep.add(skf);
                                    r.skfsByEui.put(hd.getDeviceEui().toLowerCase(), skf);
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
                                r.skfsByEui.put(hd.getDeviceEui().toLowerCase(), sa);
                            }
                            break;
                        default:
                        case DEACTIVATED:
                        case OUTOFDCS:
                        case DELETED:
                        case DISABLED:
                            log.debug("refreshOneRouteSkf - {} not added due to state {}", hd.getDeviceEui(), hd.getState());
                            break;
                    }
                }
                if (devices.size() == 200) {
                    devices = heliumDeviceRepository.findHeliumDeviceByTenantUuid(hts.getTenantUUID(), devices.getLast().getDeviceEui(), 200);
                } else {
                    quit = true;
                }
            } while (devices != null && !quit);
        }

        log.debug("refreshOneRouteSkf - scan route {} exists {} processed {} devices",inRouteSkfs.size(), routeId, processed);

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
                log.warn("The route {} contains redundant add skf for devAddr {} key {}", routeId, Integer.toHexString(s.getDevaddr()), s.getSessionKey());
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
                log.warn("The route {} contains redundant del skf for devAddr {} key {}", routeId, Integer.toHexString(s.getDevaddr()), s.getSessionKey());
            }
        }
        if ( ! grpcUpdateSessions(skfToAdd,skfToRem,routeId) ) {
            log.error("The route {} failed to update skfs", routeId);
        }

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
    protected final HashMap<String,NovaDevice> delayedEuisRefreshAddition = new HashMap<>();
    protected final HashMap<String,NovaDevice> delayedEuisRefreshRemoval = new HashMap<>();

    private final Object asyncEuisRefreshAddition = new Object();
    private final Object asyncEuisRefreshRemoval = new Object();

    // @TODO - le synchronized sur le hashmap ne doit pas marcher s'il en manque ... ?

    protected void addDelayedEuisRefreshAddition(NovaDevice dev) {
        if (dev.routeId == null) return;
        // make sure so we don't have to retest this later
        dev.devEui = dev.devEui.toLowerCase();
        dev.appEui = dev.appEui.toLowerCase();
        if ( dev.timeMs == 0 ) dev.timeMs = Now.NowUtcMs();
        synchronized (asyncEuisRefreshAddition) {
            boolean found = delayedEuisRefreshAddition.get(dev.devEui) != null;
            if ( !found ) {
                log.debug("To be added {} ({}) {}", dev.devEui, dev.appEui, dev.routeId);
                this.delayedEuisRefreshAddition.put(dev.devEui,dev);
            }
        }
    }

    protected void addDelayedEuisRefreshRemoval(NovaDevice dev) {
        if (dev.routeId == null) return;
        // make sure so we don't have to retest this later
        dev.devEui = dev.devEui.toLowerCase();
        dev.appEui = dev.appEui.toLowerCase();
        if ( dev.timeMs == 0 ) dev.timeMs = Now.NowUtcMs();

        synchronized (asyncEuisRefreshRemoval) {
            boolean found = delayedEuisRefreshRemoval.get(dev.devEui) != null;
            if ( !found ) {
                log.debug("To be deleted {} ({}) {}", dev.devEui, dev.appEui, dev.routeId);
                this.delayedEuisRefreshRemoval.put(dev.devEui,dev);
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
            ArrayList<String> routeRemoval;
            synchronized (delayedRouteRemoval) {
                routeRemoval = new ArrayList<>(delayedRouteRemoval);
                this.delayedRouteRemoval.clear();
            }
            for ( String routeId : routeRemoval ) {
                if ( ! grpcDeleteRoute(routeId) ) {
                    this.addDelayedRouteRemoval(routeId);   // in case of problem, retry later
                }
            }

            // Compute the real Euis list (priority on removal)
            // Add = Add - Remove
            // Remove = Remove
            HashMap<String,ArrayList<NovaDevice>> toRemove = new HashMap<>();
            synchronized (asyncEuisRefreshRemoval) {

                // scan the removal list and get the 2000 first elements
                // (the HPR API refuse around 5000 elements and also requires longer timeout)
                int inList = 0;
                ArrayList<String> keys = new ArrayList<>(delayedEuisRefreshRemoval.keySet());
                for ( String key : keys ) {
                    NovaDevice n = delayedEuisRefreshRemoval.get(key);
                    // list devices by routes, create the route entry if not exists
                    ArrayList<NovaDevice> trh = toRemove.computeIfAbsent(n.routeId, k -> new ArrayList<>());
                    // add the device in the route entry
                    trh.add(n);
                    // clear the device from the delayed list
                    delayedEuisRefreshRemoval.remove(key);
                    inList++;
                    if ( inList > 2000 ) break;
                }

            }

            HashMap<String,ArrayList<NovaDevice>> toAdd = new HashMap<>();
            synchronized (asyncEuisRefreshAddition) {

                // doing the same with the device addition
                // scan the addition list and get the 2000 first elements
                int inList = 0;
                ArrayList<String> keys = new ArrayList<>(delayedEuisRefreshAddition.keySet());
                for ( String key : keys) {
                    NovaDevice n = delayedEuisRefreshAddition.get(key);
                    boolean found = false;

                    // search in the pending removal list
                    NovaDevice ns = null;
                    synchronized (asyncEuisRefreshRemoval) {
                        ns = delayedEuisRefreshRemoval.get(n.devEui);
                    }
                    if ( ns != null && ns.appEui.compareTo(n.appEui) == 0  /* && ns.timeMs > n.timeMs */ ) {
                        found = true;
                    } else {
                        ArrayList<NovaDevice> _toremove = toRemove.get(n.routeId);
                        if (_toremove != null) {
                            for (NovaDevice r : _toremove) {
                                if (n.devEui.compareTo(r.devEui) == 0 && n.appEui.compareTo(r.appEui) == 0 /* && r.timeMs > n.timeMs */) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                    // when the device is to be removed, don't add it when removed after being created
                    // that would be the best but until making further test, best is to delete and not create.
                    if (!found) {
                        ArrayList<NovaDevice> _toAdd = toAdd.computeIfAbsent(n.routeId, k -> new ArrayList<>());
                        _toAdd.add(n);
                        inList++;
                    }

                    delayedEuisRefreshAddition.remove(key);
                    if ( inList > 2000 ) break;
                }
            }

            if (!toRemove.isEmpty()) {
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
            if (!toAdd.isEmpty()) {
                for ( String routeId: toAdd.keySet() ) {
                    ArrayList<NovaDevice> _toAdd = toAdd.get(routeId);
                    if ( ! grpcAddRemoveInRoutes(_toAdd, routeId,true)) {
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
        log.debug("End Running flushDelayedEuisUpdate - duration {}ms", Now.NowUtcMs() - start);

    }


    // ----------------------------
    // public handlers

    public boolean deactivateDevices(List<NovaDevice> devices) {
        for (NovaDevice d : devices) {
            this.addDelayedSessionRefresh(d.devEui);
            this.addDelayedEuisRefreshRemoval(d);
            log.debug("Deactivating device {}", d.devEui);
        }
        return true;
    }

    public boolean activateDevices(List<NovaDevice> devices) {
        for (NovaDevice d : devices) {
            this.addDelayedSessionRefresh(d.devEui);
            this.addDelayedEuisRefreshAddition(d);
            log.debug("Activating device {}", d.devEui);
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
        public Region.region regionValue;
        public int port;
    }

    private byte[] privateKey;
    private ByteString owner;
    private final Object asyncSignerAccess = new Object();
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
        // There is two key format, the first initial one was 65 bytes long, potentially composed by
        // 01 + 32 bytes of private key + 32 bytes of public key (but I'm not sure, this format is working as a bytestream to init the java class
        // The second one is 98 bytes long: 01 + 32 bytes of private key + 32 bytes of public key + 01 + 32 bytes of public key
        // This file format is a bit like a buggy format... but using the first 65 bytes as the previous format is working the same way ...
        this.privateKey = new byte[64];
        try {
            InputStream inputStream = new FileInputStream(consoleConfig.getHeliumGrpcPrivateKeyfilePath());

            int b;
            int k = 0;
            while ((b = inputStream.read()) != -1) {
                // verifiy key header should be 1 for type of key
                if (k == 0 && b != 1) break;
                if (k > 0 && k < 65) {
                    privateKey[k - 1] = (byte) b;
                }
                k++;
            }
            if (k != 65 && k != 98 ) {
                // error
                log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                log.error("Invalid private keyfile");
                log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                ConsoleApplication.requestingExitForStartupFailure = true;
                return;
            }

        } catch ( IOException x ) {
            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            log.error("Impossible to access private key file {}", x.getMessage());
            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            ConsoleApplication.requestingExitForStartupFailure = true;
            return;
        }

        // Prepare owner information
        try {
            byte[] owner_b = Base58.decode(consoleConfig.getHeliumGprcPublicKey(),false);
            if (owner_b.length == 38) {
                byte[] owner_b2 = new byte[33];
                System.arraycopy(owner_b, 1, owner_b2, 0, 33);
                this.owner = ByteString.copyFrom(owner_b2);
            } else if (owner_b.length == 37) {
                // no leading  0
                byte[] owner_b2 = new byte[33];
                System.arraycopy(owner_b, 0, owner_b2, 0, 33);
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

        // Verify the public key
        byte[] pubKeyBytes = secretKeyParameters.generatePublicKey().getEncoded();
        byte[] pubKeyWithHeader = new byte[pubKeyBytes.length+2];
        pubKeyWithHeader[0] = 0x00;
        pubKeyWithHeader[1] = 0x01;
        System.arraycopy(pubKeyBytes, 0, pubKeyWithHeader, 2, pubKeyBytes.length);
        String pubKeyHex = Base58.encode(pubKeyWithHeader,true);
        if ( pubKeyHex.compareToIgnoreCase(consoleConfig.getHeliumGprcPublicKey()) != 0) {
            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            log.error("The public key is not the same as the one in the properties file");
            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            ConsoleApplication.requestingExitForStartupFailure = true;
            return;
        }

        // Verify the rest of the configuration
        String _netId = consoleConfig.getHeliumRouteNetid();
        if ( _netId != null && !_netId.isEmpty()) {
            netIdValue = Stuff.hexStrToInt(_netId);
        }

        regionsSupported = new ArrayList<>();
        if (!consoleConfig.getHeliumRouteRegions().isEmpty()) {
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
                        case "US915": r.regionValue = Region.region.US915; break;
                        case "EU868": r.regionValue = Region.region.EU868; break;
                        case "CN470": r.regionValue = Region.region.CN470; break;
                        case "AU915": r.regionValue = Region.region.AU915; break;
                        case "AS923_1": r.regionValue = Region.region.AS923_1; break;
                        case "KR920": r.regionValue = Region.region.KR920; break;
                        case "IN865": r.regionValue = Region.region.IN865; break;
                        case "AS923_2": r.regionValue = Region.region.AS923_2; break;
                        case "AS923_3": r.regionValue = Region.region.AS923_3; break;
                        case "AS923_4": r.regionValue = Region.region.AS923_4; break;
                        case "AS923_1B": r.regionValue = Region.region.AS923_1B; break;
                        case "CD900_1A": r.regionValue = Region.region.CD900_1A; break;
                        case "RU864": r.regionValue = Region.region.RU864; break;
                        case "EU868_A": r.regionValue = Region.region.EU868_A; break;
                        case "EU868_B": r.regionValue = Region.region.EU868_B; break;
                        case "EU868_C": r.regionValue = Region.region.EU868_C; break;
                        case "EU868_D": r.regionValue = Region.region.EU868_D; break;
                        case "EU868_E": r.regionValue = Region.region.EU868_E; break;
                        case "EU868_F": r.regionValue = Region.region.EU868_F; break;
                        case "AU915_SB1": r.regionValue = Region.region.AU915_SB1; break;
                        case "AU915_SB2": r.regionValue = Region.region.AU915_SB2; break;
                        case "AS923_1A": r.regionValue = Region.region.AS923_1A; break;
                        case "AS923_1C": r.regionValue = Region.region.AS923_1C; break;
                        case "AS923_1D": r.regionValue = Region.region.AS923_1D; break;
                        case "AS923_1E": r.regionValue = Region.region.AS923_1E; break;
                        case "AS923_1F": r.regionValue = Region.region.AS923_1F; break;
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
            || consoleConfig.getHeliumRouteHost().isEmpty()
            || regionsSupported.isEmpty()
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
            Page<HeliumTenantSetup> htss = null;
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
            } while (htss.hasNext());

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
            stub = stub.withDeadlineAfter(GRPC_NOVA_API_TIMEOUT, TimeUnit.SECONDS);

            org_get_req_v1 getToSign = org_get_req_v1.newBuilder()
                    .setOui(consoleConfig.getHeliumRouteOui())
                    .build();

            org_res_v1 response = stub.get(getToSign);
            if ( response != null && response.getDevaddrConstraintsCount() > 0) {
                this.addresses = new ArrayList<>();
                addresses.addAll(response.getDevaddrConstraintsList());
                log.debug("GPRC found {} addresses range", addresses.size());
                log.debug("GPRC get addresses duration {}ms", Now.NowUtcMs() - start);
                return addresses;
            }
        } catch ( Exception x ) {
            log.error("GRPC get addresses error with message {}", x.getMessage());
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
        log.debug("GRPC delete route {}", routeId);

        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);
            stub = stub.withDeadlineAfter(GRPC_NOVA_API_TIMEOUT, TimeUnit.SECONDS);

            route_delete_req_v1 delToSign = route_delete_req_v1.newBuilder()
                    .setId(routeId)
                    .setTimestamp(start)
                    .setSigner(this.owner)
                    .clearSignature()
                    .build();
            byte[] requestToSignContent = delToSign.toByteArray();

            byte [] signature;
            synchronized (asyncSignerAccess) {
                this.signer.update(requestToSignContent, 0, requestToSignContent.length);
                signature = signer.generateSignature();
            }

            route_res_v1 response = stub.delete(route_delete_req_v1.newBuilder()
                    .setId(routeId)
                    .setTimestamp(start)
                    .setSigner(this.owner)
                    .setSignature(ByteString.copyFrom(signature))
                    .build());


            log.debug("GPRC route deletion duration {}ms", Now.NowUtcMs() - start);
            log.debug("GRPC deletion {}", response.getRoute().getId());
            return true;
        } catch ( Exception x ) {
            log.error("GRPC route deletion error for route {} with message {}", routeId, x.getMessage());
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
        log.debug("GRPC Create route for tenant {}", tenantId);
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
            stub = stub.withDeadlineAfter(GRPC_NOVA_API_TIMEOUT, TimeUnit.SECONDS);

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
                    .setPort(this.regionsSupported.getFirst().port)
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

            byte [] signature;
            synchronized (asyncSignerAccess) {
                this.signer.update(requestToSignContent, 0, requestToSignContent.length);
                signature = signer.generateSignature();
            }

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

                    log.debug("Add devAddr Range : {} / {}", String.format("0x%08X", addr.getStartAddr()), String.format("0x%08X", addr.getEndAddr()));

                    route_update_devaddr_ranges_req_v1 addrToSign = route_update_devaddr_ranges_req_v1.newBuilder()
                            .setAction(action_v1.add)
                            .setTimestamp(now)
                            .setDevaddrRange(range)
                            .setSigner(this.owner)
                            .clearSignature()
                            .build();

                    byte[] addrToSignContent = addrToSign.toByteArray();

                    byte[] sign;
                    synchronized (asyncSignerAccess) {
                        this.signer.update(addrToSignContent, 0, addrToSignContent.length);
                        sign = signer.generateSignature();
                    }

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

            log.debug("GPRC route creation duration {}ms", Now.NowUtcMs() - start);
            log.debug("GRPC route {}",response.getRoute().getId());
            return response.getRoute();
        } catch ( Exception x ) {
            log.warn("GRPC create route error {}", x.getMessage());
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
        log.debug("GRPC GET route {}",routeId);
        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);
            stub = stub.withDeadlineAfter(GRPC_NOVA_API_TIMEOUT, TimeUnit.SECONDS);

            long now = Now.NowUtcMs();
            route_get_req_v1 requestToSign = route_get_req_v1.newBuilder()
                    .setId(routeId)
                    .setTimestamp(now)
                    .setSigner(this.owner)
                    .clearSignature()
                    .build();
            byte[] requestToSignContent = requestToSign.toByteArray();

            byte[] signature;
            synchronized (asyncSignerAccess) {
                this.signer.update(requestToSignContent, 0, requestToSignContent.length);
                signature = signer.generateSignature();
            }

            route_res_v1 response = stub.get(route_get_req_v1.newBuilder()
                    .setId(routeId)
                    .setTimestamp(now)
                    .setSigner(this.owner)
                    .setSignature(ByteString.copyFrom(signature))
                    .build());

            log.debug("GPRC GET route duration {}ms", Now.NowUtcMs() - start);
            log.debug("GRPC route {}", response.getRoute().getId());
            return response.getRoute();
        } catch ( Exception x ) {
            log.warn("GRPC GET route error {}", x.getMessage());
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
        log.debug("GRPC UPDATE route (maxcopy) {}", routeId);

        route_v1 oldRoute = grpcGetOneRoute(routeId);
        if ( oldRoute == null ) return null;

        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);
            stub = stub.withDeadlineAfter(GRPC_NOVA_API_TIMEOUT, TimeUnit.SECONDS);

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
                        .setPort(this.regionsSupported.getFirst().port)
                        .setGwmp(gwmp)
                        .build();
                if ( maxCopy == -1 ) maxCopy = oldRoute.getMaxCopies();
            }

            long now = Now.NowUtcMs();
            route_v1 newRoute = route_v1.newBuilder()
                    .setId(oldRoute.getId())
                    .setNetId(oldRoute.getNetId())      // the netId can't be updated
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

            byte[] signature;
            synchronized (asyncSignerAccess) {
                this.signer.update(requestToSignContent, 0, requestToSignContent.length);
                signature = signer.generateSignature();
            }

            route_res_v1 response = stub.update(route_update_req_v1.newBuilder()
                    .setRoute(newRoute)
                    .setTimestamp(now)
                    .setSignature(ByteString.copyFrom(signature))
                    .setSigner(this.owner)
                    .build());

            log.debug("GPRC UPDATE route duration {}ms", Now.NowUtcMs() - start);
            return response.getRoute();
        } catch ( Exception x ) {
            log.warn("GRPC UPDATE route error {}", x.getMessage());
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
        log.debug("GRPC GET route EUIs {}", routeId);
        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);
            stub = stub.withDeadlineAfter(GRPC_NOVA_API_TIMEOUT, TimeUnit.SECONDS);

            long now = Now.NowUtcMs();
            route_get_euis_req_v1 requestToSign = route_get_euis_req_v1.newBuilder()
                    .setRouteId(routeId)
                    .setTimestamp(now)
                    .setSigner(this.owner)
                    .clearSignature()
                    .build();
            byte[] requestToSignContent = requestToSign.toByteArray();

            byte[] signature;
            synchronized (asyncSignerAccess) {
                this.signer.update(requestToSignContent, 0, requestToSignContent.length);
                signature = signer.generateSignature();
            }

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

            log.debug("GPRC GET route EUIs duration {}ms", Now.NowUtcMs() - start);
            log.debug("GRPC route {} has {} entries ", routeId, rl.size());
            return rl;
        } catch ( Exception x ) {
            log.warn("GRPC GET route EUIs error {}", x.getMessage());
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
            stub = stub.withDeadlineAfter(GRPC_NOVA_API_TIMEOUT, TimeUnit.SECONDS);

            long now = Now.NowUtcMs();
            route_list_req_v1 requestToSign = route_list_req_v1.newBuilder()
                    .setOui(consoleConfig.getHeliumRouteOui())
                    .setTimestamp(now)
                    .setSigner(this.owner)
                    .clearSignature()
                    .build();
            byte[] requestToSignContent = requestToSign.toByteArray();

            byte[] signature;
            synchronized (asyncSignerAccess) {
                this.signer.update(requestToSignContent, 0, requestToSignContent.length);
                signature = signer.generateSignature();
            }

            route_list_res_v1 response = stub.list(route_list_req_v1.newBuilder()
                    .setOui(consoleConfig.getHeliumRouteOui())
                    .setTimestamp(now)
                    .setSigner(this.owner)
                    .setSignature(ByteString.copyFrom(signature))
                    .build());
            log.debug("GPRC list route duration {}ms", Now.NowUtcMs() - start);
            log.debug("GRPC routes ({})", response.getRoutesCount());
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
            log.warn("GPRC list route - Nova Backend not reachable");
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
     * @TODO - Apparently when the number of updates is or XXk device is silently fails...
     * @param devices
     * @param add
     */
    public boolean grpcAddRemoveInRoutes(List<NovaDevice> devices, String routeId, boolean add) {

        final AtomicInteger failureCounter = new AtomicInteger(0);
        final AtomicBoolean isCompleted = new AtomicBoolean(false);
        StreamObserver<route_euis_res_v1> responseObserver = new StreamObserver<route_euis_res_v1>() {
            @Override
            public void onNext(route_euis_res_v1 value) {
                // Why this is never called ?
                log.debug("Eui Updated");
            }

            @Override
            public void onError(Throwable t) {
                log.error("Eui update failed {}", t.getMessage().substring(0,50));
                failureCounter.incrementAndGet();
            }

            @Override
            public void onCompleted() {
                isCompleted.set(true);
                log.debug("End of Eui updates");
            }
        };


        if ( ! this.grpcInitOk ) return false;
        long start = Now.NowUtcMs();
        if ( add ) {
            log.debug("GRPC Add routes ({}) in {}", devices.size(), routeId);
        } else {
            log.debug("GRPC Remove routes ({}) in {}", devices.size(), routeId);
        }

        ManagedChannel channel = ManagedChannelBuilder.forAddress(
                consoleConfig.getHeliumGrpcServer(),
                consoleConfig.getHeliumGrpcPort()
        ).usePlaintext().build();
        long startNova = Now.NowUtcMs();
        int retry = 0;
        int updated = 0;
        try {
            do {
                if ( retry > 0 ) log.warn("GRPC Route update Retry {} / 3", retry);
                routeGrpc.routeStub stub = routeGrpc.newStub(channel);

                long now = Now.NowUtcMs();
                ArrayList<route_update_euis_req_v1> requests = new ArrayList<>();
                for( NovaDevice device : devices) {
                    // skip devEui == 0
                    if ( device.devEui.compareTo("0000000000000000") == 0 ) continue;

                    log.debug("  Process DevEUI {}", device.devEui);
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

                    byte[] signature;
                    synchronized (asyncSignerAccess) {
                        this.signer.update(requestToSignContent, 0, requestToSignContent.length);
                        signature = signer.generateSignature();
                    }

                    route_update_euis_req_v1 request = route_update_euis_req_v1.newBuilder()
                            .setTimestamp(now)
                            .setActionValue(((add)?action_v1.add_VALUE:action_v1.remove_VALUE))
                            .setEuiPair(eui)
                            .setSigner(this.owner)
                            .setSignature(ByteString.copyFrom(signature))
                            .build();

                    requests.add(request);
                }

                updated = 0;
                failureCounter.set(0);
                StreamObserver<route_update_euis_req_v1> reqObserver = stub.updateEuis(responseObserver);
                try {
                    isCompleted.set(false);
                    for (route_update_euis_req_v1 req : requests) {
                        reqObserver.onNext(req);
                        updated++;
                    }
                    reqObserver.onCompleted();

                    // wait for the end of the update to get the eventual error with a timeout
                    long startWait = Now.NowUtcMs();
                    while (!isCompleted.get() && (Now.NowUtcMs() - startWait) < 20_000 && failureCounter.get() == 0 ) {
                        Tools.sleep(10);
                    }
                } catch (RuntimeException x) {
                    reqObserver.onError(x);
                    prometeusService.addHeliumTotalError();
                    log.error("GRPC error during route update {}", x.getMessage());
                }
            } while (failureCounter.get() > 0 && retry++ < 3);
            if ( retry >= 3) return false;
        } finally {
            log.debug(">> exit Eui update {} / {}", updated, devices.size());
            if (channel != null) channel.shutdown();
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
        grpcUpdateSessions(add,del,routeId);
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
        log.debug("GRPC List sessions for {} in route {}", String.format("0x%08X", devAddr), routeId);
        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);
            stub = stub.withDeadlineAfter(GRPC_NOVA_API_TIMEOUT, TimeUnit.SECONDS);

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

                byte[] signature;
                synchronized (asyncSignerAccess) {
                    this.signer.update(requestToSignContent, 0, requestToSignContent.length);
                    signature = signer.generateSignature();
                }

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

                byte[] signature;
                synchronized (asyncSignerAccess) {
                    this.signer.update(requestToSignContent, 0, requestToSignContent.length);
                    signature = signer.generateSignature();
                }

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

            log.debug("GPRC skf list duration {}ms", Now.NowUtcMs() - start);
            log.debug("GRPC skf found {} entries", ret.size());
            return ret;
        } catch ( StatusRuntimeException x ) {
            prometeusService.addHeliumTotalError();
            log.warn("GPRC skf list - Nova Backend not reachable");
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


    /**
     * Update the session keys for a given route with skfs to remove and to add
     * the whole list is given in one single time
     */
    private boolean grpcUpdateSessions(
            LinkedList<SkfUpdate> toAddSession,
            LinkedList<SkfUpdate> toRemoveSession,
            String routeId
    ) {

        if ( ! this.grpcInitOk ) return false;
        long start = Now.NowUtcMs();
        log.debug("GRPC Session Update Add:{} Del:{} for {}", toAddSession.size(), toRemoveSession.size(), routeId);
        int toProcess = toAddSession.size() + toRemoveSession.size();
        ManagedChannel channel = ManagedChannelBuilder.forAddress(
                consoleConfig.getHeliumGrpcServer(),
                consoleConfig.getHeliumGrpcPort()
        ).usePlaintext().build();

        try {

            long now = Now.NowUtcMs();
            long lastCall = now;
            int actions = 0; // max 100 actions at a time
            while (!toAddSession.isEmpty() || !toRemoveSession.isEmpty()) {
                ArrayList<route_skf_update_req_v1.route_skf_update_v1> updates = new ArrayList<>();
                while (!toAddSession.isEmpty() && actions < 100) {
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
                    } else {
                        log.debug("Request to add skf with out-of-range devaddr ({}) in route {}", Integer.toHexString(session.devAddr), routeId);
                    }
                    actions++;
                }
                while (!toRemoveSession.isEmpty() && actions < 100) {
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
                        log.debug("Remove SKFS {} with session {} in route {}", session.devAddr, Integer.toHexString(session.devAddr), routeId);
                        actions++;
                    } else {
                        log.debug("Request to remove skf with out-of-range devaddr ({}) in route {}", Integer.toHexString(session.devAddr), routeId);
                    }
                }
                // execute
                int retry = 0;
                boolean success = false;
                while ( !success && retry < 3 ) {
                    if ( (Now.NowUtcMs() - lastCall) < 100 ) Tools.sleep(100); // at least have 100 ms in between API calls to try reduce pressure
                    lastCall = Now.NowUtcMs();
                    try {
                        if (!updates.isEmpty()) {
                            route_skf_update_req_v1 requestToSign = route_skf_update_req_v1.newBuilder()
                                    .setRouteId(routeId)
                                    .addAllUpdates(updates)
                                    .setTimestamp(now)
                                    .setSigner(this.owner)
                                    .clearSignature()
                                    .build();

                            byte[] requestToSignContent = requestToSign.toByteArray();

                            byte[] signature;
                            synchronized (asyncSignerAccess) {
                                this.signer.update(requestToSignContent, 0, requestToSignContent.length);
                                signature = signer.generateSignature();
                            }

                            route_skf_update_req_v1 request = route_skf_update_req_v1.newBuilder()
                                    .setRouteId(routeId)
                                    .addAllUpdates(updates)
                                    .setTimestamp(now)
                                    .setSigner(this.owner)
                                    .setSignature(ByteString.copyFrom(signature))
                                    .build();

                            // try to generate a new stub on every trial to avoid the "channel shutdown" issue after timeout
                            routeGrpc.routeBlockingStub stub = routeGrpc.newBlockingStub(channel);
                            stub = stub.withDeadlineAfter(GRPC_NOVA_API_TIMEOUT, TimeUnit.SECONDS);
                            route_skf_update_res_v1 response = stub.updateSkfs(request);

                        }
                        toProcess -= actions;
                        actions = 0;
                        success = true;
                    } catch (StatusRuntimeException x) {
                        prometeusService.addHeliumTotalError();
                        log.warn("Skf Update Nova Backend not reachable {}", x.getMessage());
                        retry++;
                    }
                }
                if ( retry >= 3 ) {
                    logService.log(new LogEntry(
                            LogLevel.ERROR,
                            "HPRCONFIG",
                            "Skf Update failed, ("+toProcess+") sessions may not be up-to-date.",
                            Now.NowUtcMs()
                    ));
                    log.error("Skf Update failed after 3 retries, missing updates ({})", toProcess);
                    return false;
                }
            }
            log.debug("GPRC skf update duration {}ms", Now.NowUtcMs() - start);
            return true;
        } finally {
            if ( channel != null ) channel.shutdown();
            prometeusService.addHeliumApiTotalTimeMs(start);
        }
    }

}

