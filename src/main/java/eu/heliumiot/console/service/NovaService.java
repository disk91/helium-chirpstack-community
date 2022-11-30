package eu.heliumiot.console.service;

import com.google.protobuf.ByteString;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.jpa.db.HeliumDevice;
import eu.heliumiot.console.jpa.db.NovaDevice;
import eu.heliumiot.console.redis.RedisDeviceRepository;
import fr.ingeniousthings.tools.*;
import io.chirpstack.api.internal.Internal;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.nova.grpc.Config;
import xyz.nova.grpc.RouteGrpc;

import javax.annotation.PostConstruct;
import javax.tools.Tool;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    // ----------------------------------
    // MANAGE THE SESSIONS NwkSkey
    // ----------------------------------


    public boolean refreshDevAddrList(String devaddr) {
        // Get the list of device using this devaddr
        List<String> deviceEUIs = redisDeviceRepository.getDevEuiByDevAddr(devaddr);
        log.debug("Refresh devAddr network keys for " + devaddr + " found " + deviceEUIs.size() + " devices");
        for (String devEUI : deviceEUIs) {
            // verify state from cache, is that an active device...
            HeliumDevice hd = heliumDeviceCacheService.getHeliumDevice(devEUI);
            switch (hd.getState()) {
                case INSERTED:
                case ACTIVE:
                case INACTIVE:
                    // get all NwkSKey
                    Internal.DeviceSession ds = redisDeviceRepository.getDeviceDetails(devEUI);
                    if (ds != null && ds.getNwkSEncKey() != null && ds.getNwkSEncKey().size() > 4) {

                        // add to the list later call Nova API
                        // @Todo call Nova Api
                        String nwkSKey = HexaConverters.byteToHexString(ds.getNwkSEncKey().toByteArray());
                        log.debug("Add Network encrypted Key " + nwkSKey);
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


    protected int runningJobs = 0;
    protected boolean serviceEnable = true; // false to stop the services

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
        if (!this.serviceEnable || !this.initialRefreshDone) return;
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
                for (String devAd : devAddr.values()) {
                    this.refreshDevAddrList(devAd);
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
     */
    protected boolean initialRefreshDone = false;

    @Scheduled(fixedDelay = 3600_000, initialDelay = 180_000)
    protected void initialNovaSessionRefresh() {
        if (!initialRefreshDone) {
            if (!this.serviceEnable) return;
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
            log.debug("End Running initialNovaSessionRefresh - duration " + (Now.NowUtcMs() - start) + "ms");
        }
        this.initialRefreshDone = true;
    }

    // ---------------------------------------
    // MANAGE THE EUIs Route update
    // ---------------------------------------


    // List of deviceEUI we want the add / remove in the list
    protected ArrayList<NovaDevice> delayedEuisRefreshAddition = new ArrayList<>();
    protected ArrayList<NovaDevice> delayedEuisRefreshRemoval = new ArrayList<>();

    protected void addDelayedEuisRefreshAddition(NovaDevice dev) {
        synchronized (delayedEuisRefreshAddition) {
            this.delayedEuisRefreshAddition.add(dev);
        }
    }

    protected void addDelayedEuisRefreshRemoval(NovaDevice dev) {
        synchronized (delayedEuisRefreshRemoval) {
            this.delayedEuisRefreshRemoval.add(dev);
        }
    }

    /**
     * On regular basis we update the route on Nova backend based on the movement made on the devices
     * deletion, creation, deactivation, reactivation ...
     */
    @Scheduled(fixedRateString = "${helium.nova.publish.delayed.scanPeriod}", initialDelay = 15_000)
    protected void flushDelayedEuisUpdate() {
        if (!this.serviceEnable) return;
        this.runningJobs++;
        long start = Now.NowUtcMs();
        try {

            // Compute the real list (priority on removal)
            // Add = Add - Remove
            // Remove = Remove
            ArrayList<NovaDevice> toRemove = new ArrayList<>();
            synchronized (delayedEuisRefreshRemoval) {
                for ( NovaDevice n : delayedEuisRefreshRemoval ) {
                    toRemove.add(n);
                }
                this.delayedEuisRefreshRemoval.clear();
            }

            ArrayList<NovaDevice> toAdd = new ArrayList<>();
            synchronized (delayedEuisRefreshAddition) {
                for ( NovaDevice n : delayedEuisRefreshAddition ) {
                    boolean found = false;
                    for ( NovaDevice r : toRemove ) {
                        if ( n.devEui.compareToIgnoreCase(r.devEui) == 0
                        && n.appEui.compareToIgnoreCase(r.appEui) == 0 ) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        toAdd.add(n);
                    }
                }
                this.delayedEuisRefreshAddition.clear();
            }

            if ( toRemove.size() > 0 ) {
                if ( ! grpcAddRemoveInRoutes(toRemove, false) ) {
                    // restore the removal for next pass
                    for ( NovaDevice d : toRemove ) {
                        this.addDelayedEuisRefreshRemoval(d);
                    }
                }
            }
            if ( toAdd.size() > 0 ) {
                if ( ! grpcAddRemoveInRoutes(toAdd, true) ) {
                    // restore the removal for next pass
                    for ( NovaDevice d : toAdd ) {
                        this.addDelayedEuisRefreshAddition(d);
                    }
                }
            }

        } finally {
            this.runningJobs--;
        }
        log.debug("End Running flushDelayedSessionUpdate - duration " + (Now.NowUtcMs() - start) + "ms");

    }


    // ----------------------------
    // public handlers


    public boolean deactivateDevices(List<NovaDevice> devices) {
        for (NovaDevice d : devices) {
            this.addDelayedSessionRefresh(d.devEui);
            this.addDelayedEuisRefreshRemoval(d);
            log.info("Deactivating device " + d.devEui);
        }
        return true;
    }

    public boolean activateDevices(List<NovaDevice> devices) {
        for (NovaDevice d : devices) {
            this.addDelayedSessionRefresh(d.devEui);
            this.addDelayedEuisRefreshAddition(d);
            log.info("Activating device " + d.devEui);
        }
        return true;
    }

    // ===========================================================
    // GRPC Interface
    // ===========================================================
    @Autowired
    protected ConsoleConfig consoleConfig;

    private byte[] privateKey;
    private ByteString owner;
    private Ed25519Signer signer;
    protected boolean grpcInitOk = false;

    @PostConstruct
    private void loadKey() {
        log.info("Init Nova GRPC setup");
        // Load Private key
        this.privateKey = new byte[64];
        try {
            InputStream inputStream = new FileInputStream(consoleConfig.getHeliumGrpcPrivateKeyfilePath());

            int b = -1;
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
                log.error("Invalid private keyfile");
                return;
            }

        } catch (IOException x) {
            log.error("Impossible to access private key file " + x.getMessage());
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
                log.error("The public key size is not valid");
                return;
            }
        } catch (ITParseException x) {
            log.error("Impossible to parse Public Key with Base58");
            return;
        }

        // Prepare the encryption elements
        Ed25519PrivateKeyParameters secretKeyParameters = new Ed25519PrivateKeyParameters(this.privateKey, 0);
        signer = new Ed25519Signer();
        signer.init(true, secretKeyParameters);
        this.grpcInitOk = true;
    }

    /**
     * Get a single route with all the deveuis inside it
     * @param routeId
     * @return
     */
    public Config.route_v1 grpcGetOneRoute(String routeId) {
        if ( ! this.grpcInitOk ) return null;

        long start = Now.NowUtcMs();
        log.debug("GRPC Get route "+routeId);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(
                consoleConfig.getHeliumGrpcServer(),
                consoleConfig.getHeliumGrpcPort()
        ).usePlaintext().build();
        RouteGrpc.routeBlockingStub stub = RouteGrpc.newBlockingStub(channel);

        long now = Now.NowUtcMs();
        Config.route_get_req_v1 requestToSign = Config.route_get_req_v1.newBuilder()
                .setOwner(this.owner)
                .setId(ByteString.copyFromUtf8(routeId))
                .setTimestamp(now)
                .clearSignature()
                .build();
        byte[] requestToSignContent = requestToSign.toByteArray();
        this.signer.update(requestToSignContent, 0, requestToSignContent.length);
        byte[] signature = signer.generateSignature();

        Config.route_v1 response = stub.get(Config.route_get_req_v1.newBuilder()
                .setOwner(this.owner)
                .setId(ByteString.copyFromUtf8(routeId))
                .setTimestamp(now)
                .setSignature(ByteString.copyFrom(signature))
                .build());
        channel.shutdown();
        log.debug("GPRC get route duration "+(Now.NowUtcMs()-start)+"ms");
        log.debug("GRPC route "+response.getId().toStringUtf8()+ " has "+response.getEuisList().size()+" entries ");

        return response;
    }


    public Config.route_list_res_v1 grpcListRoutes() {
        if ( ! this.grpcInitOk ) return null;

        long start = Now.NowUtcMs();
        log.debug("GRPC List routes ");
        ManagedChannel channel = ManagedChannelBuilder.forAddress(
                consoleConfig.getHeliumGrpcServer(),
                consoleConfig.getHeliumGrpcPort()
        ).usePlaintext().build();
        RouteGrpc.routeBlockingStub stub = RouteGrpc.newBlockingStub(channel);

        long now = Now.NowUtcMs();
        Config.route_list_req_v1 requestToSign = Config.route_list_req_v1.newBuilder()
                .setOwner(this.owner)
                .setOui(consoleConfig.getHeliumGprcOui())
                .setTimestamp(now)
                .clearSignature()
                .build();
        byte[] requestToSignContent = requestToSign.toByteArray();
        this.signer.update(requestToSignContent, 0, requestToSignContent.length);
        byte[] signature = signer.generateSignature();

        Config.route_list_res_v1 response = stub.list(Config.route_list_req_v1.newBuilder()
                .setOwner(this.owner)
                .setOui(consoleConfig.getHeliumGprcOui())
                .setTimestamp(now)
                .setSignature(ByteString.copyFrom(signature))
                .build());
        channel.shutdown();
        log.debug("GPRC list route duration "+(Now.NowUtcMs()-start)+"ms");
        log.debug("GRPC routes ("+response.getRoutesCount()+ ")");
        for ( Config.route_v1 route : response.getRoutesList() ) {
            log.debug("GRPC route id "+route.getId().toStringUtf8()+ " with "+route.getEuisList().size()+" euis");
        }

        return response;
    }


    /**
     * Update an existing route by adding or removing EUIs in this route
     * boolean to select add (true) or removal (false)
     * @param devices
     * @param add
     */
    public boolean grpcAddRemoveInRoutes(List<NovaDevice> devices, boolean add) {
        if ( ! this.grpcInitOk ) return false;
        long start = Now.NowUtcMs();
        if ( add ) {
            log.debug("GRPC Add routes");
        } else {
            log.debug("GRPC Remove routes");
        }

        Config.route_list_res_v1 routes = this.grpcListRoutes();
        if( routes == null || routes.getRoutesCount() == 0 || routes.getRoutesCount() > 1) {
            // @TODO keep it simple currently, only one route is supported
            // means the route must be initialized previously
            // and no more than one is supported
            // we will later manage multi-route solution
            // route can be saved in the config
            // @TODO also manage route cache in memory to reduce the read impact ..
            if ( routes != null ) {
                log.error("GRPC currently supporting configuration with one route created, not more/less");
            } else {
                log.error("GRPC impossible to get the routes");
            }
            return false;
        }
        Config.route_v1 route = routes.getRoutes(0);

        HashMap<String, NovaDevice> allDevices = new HashMap<>();
        // add existing devices
        for ( Config.eui_v1 euis : route.getEuisList() ) {
            if ( add ) {
                NovaDevice nd = new NovaDevice();
                nd.devEui = Tools.EuiStringFromLong(euis.getDevEui());
                nd.appEui = Tools.EuiStringFromLong(euis.getAppEui());
                allDevices.put(nd.devEui + nd.appEui, nd);
            } else {
                String _devEui = Tools.EuiStringFromLong(euis.getDevEui());
                String _appEui = Tools.EuiStringFromLong(euis.getAppEui());
                boolean found = false;
                for ( NovaDevice device : devices ) {
                    if (  device.devEui.compareToIgnoreCase(_devEui) == 0
                       && device.appEui.compareToIgnoreCase(_appEui) == 0
                    ) {
                        found = true;
                        break;
                    }
                }
                if ( !found ) {
                    NovaDevice nd = new NovaDevice();
                    nd.devEui = Tools.EuiStringFromLong(euis.getDevEui());
                    nd.appEui = Tools.EuiStringFromLong(euis.getAppEui());
                    allDevices.put(nd.devEui + nd.appEui, nd);
                }
            }
        }

        // find devices to be added and do this
        if ( add ) {
            for (NovaDevice device : devices) {
                if (allDevices.get(device.devEui + device.appEui) == null) {
                    allDevices.put(device.devEui + device.appEui, device);
                }
            }
        }


        // Update list of route
        ArrayList<Config.eui_v1> updatedList = new ArrayList<>();
        for ( NovaDevice device : allDevices.values() ) {
            Config.eui_v1 n = Config.eui_v1.newBuilder()
                    .setDevEui(Tools.EuiStringToLong(device.devEui))
                    .setAppEui(Tools.EuiStringToLong(device.appEui))
                    .build();

            updatedList.add(n);
        }

        // Clone the route
        Config.route_v1 newRoute = Config.route_v1.newBuilder()
                .setId(route.getId())
                .setServer(route.getServer())
                .setOui(route.getOui())
                .setNetId(route.getNetId())
                .setMaxCopies(route.getMaxCopies())
                .addAllDevaddrRanges(route.getDevaddrRangesList())
                .addAllEuis(updatedList)
                .setNonce(route.getNonce())
                .build();

        try {
            // push the new version
            ManagedChannel channel = ManagedChannelBuilder.forAddress(
                    consoleConfig.getHeliumGrpcServer(),
                    consoleConfig.getHeliumGrpcPort()
            ).usePlaintext().build();
            RouteGrpc.routeBlockingStub stub = RouteGrpc.newBlockingStub(channel);

            long now = Now.NowUtcMs();
            Config.route_update_req_v1 requestToSign = Config.route_update_req_v1.newBuilder()
                    .setOwner(this.owner)
                    .setRoute(newRoute)
                    .setTimestamp(now)
                    .clearSignature()
                    .build();
            byte[] requestToSignContent = requestToSign.toByteArray();
            this.signer.update(requestToSignContent, 0, requestToSignContent.length);
            byte[] signature = signer.generateSignature();

            Config.route_v1 response = stub.update(Config.route_update_req_v1.newBuilder()
                    .setOwner(this.owner)
                    .setRoute(newRoute)
                    .setTimestamp(now)
                    .setSignature(ByteString.copyFrom(signature))
                    .build());
            channel.shutdown();
        } catch (Exception x) {
            log.error("GRPC error during route update "+x.getMessage());
            x.printStackTrace();
            return false;
        }
        log.debug("GPRC list update duration "+(Now.NowUtcMs()-start)+"ms");
        return true;
    }


}

