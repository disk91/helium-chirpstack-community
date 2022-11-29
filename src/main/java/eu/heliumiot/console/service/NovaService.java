package eu.heliumiot.console.service;

import com.google.protobuf.ByteString;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.jpa.db.HeliumDevice;
import eu.heliumiot.console.jpa.db.NovaDevice;
import eu.heliumiot.console.redis.RedisDeviceRepository;
import fr.ingeniousthings.tools.Base58;
import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.ITParseException;
import fr.ingeniousthings.tools.Now;
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

    // ------
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


    public boolean deactivateDevices(List<NovaDevice> devices) {
        for (NovaDevice d : devices) {
            this.addDelayedSessionRefresh(d.devEui);
            log.info("Deactivating device " + d.devEui);
        }
        return true;
    }

    public boolean activateDevices(List<NovaDevice> devices) {
        for (NovaDevice d : devices) {
            this.addDelayedSessionRefresh(d.devEui);
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

    //"8e10ad69-f791-4a2e-9d58-12b7104bb4d2"
    public Config.route_v1 grpcListOneRoute(String routeId) {
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
        log.debug("GRPC route "+response.getId().toString()+ " has "+response.getEuisList().size()+" entries ");

        return response;
    }

}

