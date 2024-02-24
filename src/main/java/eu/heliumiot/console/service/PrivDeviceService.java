/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2020.
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
import eu.heliumiot.console.api.interfaces.AdvDeviceInacGetItf;
import eu.heliumiot.console.api.interfaces.AdvDeviceInacSubItf;
import eu.heliumiot.console.api.interfaces.AdvDeviceSearchGetItf;
import eu.heliumiot.console.jpa.db.*;
import eu.heliumiot.console.jpa.mongoRep.DeviceFramesMongoRepository;
import eu.heliumiot.console.jpa.mongodb.DeviceFrames;
import eu.heliumiot.console.jpa.mongodb.sub.FrameEntry;
import eu.heliumiot.console.jpa.mongodb.sub.FrameHotspot;
import fr.ingeniousthings.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PrivDeviceService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ConsolePrivateConfig consolePrivateConfig;

    @Autowired
    protected DeviceFramesMongoRepository deviceFramesMongoRepository;

    // =============================================================
    // Cache Management
    // =============================================================

    protected boolean serviceEnable = true;
    protected int runningJobs = 0;


    public void stopService() {
        this.serviceEnable = false;
    }

    // return true when the service has stopped all the running jobs
    public boolean hasStopped() {
        return (!this.serviceEnable && this.runningJobs == 0);
    }



    // =============================================================
    // Inactiv Devices
    // =============================================================

    @Autowired
    protected UserCacheService userCacheService;

    @Autowired
    protected HeliumDeviceCacheService heliumDeviceCacheService;

    @Autowired
    protected HeliumDeviceService heliumDeviceService;

    @Autowired
    protected HeliumTenantService heliumTenantService;

    @Autowired
    protected HeliumTenantSetupService heliumTenantSetupService;

    @Autowired
    protected PrivDeviceFramesService privDeviceFramesService;

    @Autowired
    protected eu.heliumiot.console.jpa.repository.DeviceRepository deviceRepository;

    @Autowired
    protected NovaService novaService;

    public AdvDeviceInacGetItf getInactivDeviceByUser(String userId, String tenantId, int page, int hoursBefore)
    throws ITRightException, ITNotFoundException {
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();

        // verify ownership
        if (! heliumTenantService.isTenantOwnedBy(user,tenantId) ) throw new ITRightException();

        Tenant t = heliumTenantService.getTenant(tenantId);
        if ( t == null ) throw new ITNotFoundException();

        // inactivity since
        long since = Now.NowUtcMs() - ( hoursBefore * Now.ONE_HOUR );

        // get a page of inactive
        long c = deviceRepository.countDeviceByTenantUUIDAndLastSeenLowerThan(tenantId,since);
        if ( c < (page * 200) ) throw new ITNotFoundException();

        List<Device> ds = deviceRepository.findDeviceByTenantUUIDAndLastSeenLowerThan(
            tenantId,
            since,
            page,
            200
        );
        AdvDeviceInacGetItf r = new AdvDeviceInacGetItf();
        r.setInactivCount(c);
        r.setTenantUUID(tenantId);
        r.setTenantName(t.getName());
        r.setCurrentPage(page);
        r.setTotalPage(c / 200);
        ArrayList<AdvDeviceInacSubItf> ids = new ArrayList<>();

        if ( ! ds.isEmpty() ) {
            HeliumTenantSetup hts = heliumTenantSetupService.getHeliumTenantSetup(tenantId, false);
            List<com.helium.grpc.eui_pair_v1> euis = null;
            if ( hts != null && user.user.isAdmin() ) {
                euis = novaService.getEuiInARoute(hts.getRouteId());
            }

            for (Device d : ds) {
                AdvDeviceInacSubItf id = new AdvDeviceInacSubItf();
                id.setApplicationId(d.getApplicationId().toString());
                String aName = heliumDeviceService.getApplicationNameFromId(id.getApplicationId());
                id.setApplicationName(Objects.requireNonNullElse(aName, "Unknown"));
                id.setDevEui(HexaConverters.byteToHexString(d.getDevEui()));
                id.setDevName(d.getName());
                if (d.getCreatedAt() != null) id.setCreationDate(d.getCreatedAt().getTime());
                else id.setCreationDate(0);
                if (d.getLastSeenAt() != null) id.setLastSeenDate(d.getLastSeenAt().getTime());
                else id.setLastSeenDate(0);
                if ( d.getDevAddr() != null ) {
                    id.setDevAddr(HexaConverters.byteToHexString(d.getDevAddr()));
                } else id.setDevAddr("undefined");
                id.setDisabled(d.isDisabled());
                id.setNeverSeen(d.getLastSeenAt() == null || d.getLastSeenAt().getTime() == 0);
                id.setRouteEui(1);
                id.setRouteSkfs(1);

                // get the DeviceFrame
                DeviceFrames df = privDeviceFramesService.getDevice(id.getDevEui().toLowerCase());
                id.setCoverageRisk(1);
                id.setOnlyJoinReq(1);
                if (df != null && !df.getRecentFrames().isEmpty()) {
                    id.setRouteEui(2);
                    AtomicBoolean haveUplink = new AtomicBoolean(false);
                    AtomicBoolean haveJoin = new AtomicBoolean(false);
                    AtomicInteger badRadioCount = new AtomicInteger(0);
                    AtomicInteger distRadioCount = new AtomicInteger(0);
                    AtomicInteger badRadioTotal = new AtomicInteger(0);
                    df.getRecentFrames().parallelStream().forEach((f) -> {
                        if (f.getFrameType() == FrameEntry.FRAME_TYPE_UPLINK) {
                            haveUplink.set(true);
                            boolean bad = true;
                            boolean dist = true;
                            // analyse communications, if SNR is bad or RSSI really low, coverage can explain it
                            for (FrameHotspot fh : f.getHotspots()) {
                                if (   (f.getDr() < 2 && fh.getSnr() > -15.0)
                                    || (f.getDr() >= 2 && fh.getSnr() > -7)
                                ) bad = false;
                                if (fh.getRssi() > -132) dist = false;
                                if ( !bad && !dist ) break;
                            }
                            if (bad) badRadioCount.addAndGet(1);
                            if (dist) distRadioCount.addAndGet(1);
                            badRadioTotal.addAndGet(1);
                        } else if ( f.getFrameType() == FrameEntry.FRAME_TYPE_JOIN) {
                            haveJoin.set(true);
                        }
                    });
                    log.debug("total: "+badRadioTotal.get()+" badSnr: "+badRadioCount.get()+" badRssi: "+distRadioCount.get());
                    if ( haveJoin.get() && !haveUplink.get() ) id.setOnlyJoinReq(2);
                    if ( haveUplink.get() ) id.setOnlyJoinReq(0);
                    id.setCoverageRisk(1); // default unknown
                    if (badRadioTotal.get() > 0 ) {
                        double badRssiRatio = distRadioCount.get() / (double) badRadioTotal.get();
                        double badSnrRatio = badRadioCount.get() / (double) badRadioTotal.get();
                        log.debug("Rssi Ratio: "+badRssiRatio+" Snr Ratio: "+badSnrRatio);
                        if (badRssiRatio > 0.75 || badSnrRatio > 0.75) id.setCoverageRisk(2);
                        if (badRssiRatio < 0.25 && badSnrRatio < 0.25) id.setCoverageRisk(0);
                    }
                }

                // get potential skfs collisions
                if (hts != null) {
                    int col = novaService.countSkfsColisions(hts.getRouteId(), id.getDevEui());
                    if (col >= 1) id.setRouteSkfs(2);
                    if (col > 1) id.setSkfsCollision(2);
                    if (col == 0) id.setRouteSkfs(0);
                } else {
                    id.setSkfsCollision(1);
                }

                // admin deep check
                if (user.user.isAdmin() && hts != null) {
                    // verify route & skfs
                    int sk = novaService.existSkfs(hts.getRouteId(), id.getDevEui());
                    if (sk == 0) id.setRouteSkfs(0);
                    if (sk >= 1) id.setRouteSkfs(2);
                    if (sk > 1) id.setSkfsCollision(2);
                    // verify route eui
                    if ( euis != null ) {
                        AtomicBoolean euiSeen = new AtomicBoolean(false);
                        long ldevEui = Tools.EuiStringToLong(id.getDevEui());
                        long lappEui = Tools.EuiStringToLong(d.getAppEui());
                        euis.parallelStream().forEach( (e) -> {
                            if ( ldevEui == e.getDevEui() && lappEui == e.getAppEui() ) euiSeen.set(true);
                        });
                        if ( euiSeen.get() ) id.setRouteEui(2);
                        else id.setRouteEui(0);
                    }
                }
                ids.add(id);
            }
        }
        r.setInactives(ids);
        return r;
    }

    // =============================================================
    // Search for devices
    // =============================================================

    // ---
    // Search in a tenant devices based on full text search on name and deveui
    // when search empty, return first 50 devices for the list
    //
    public List<AdvDeviceSearchGetItf> searchFromDeviceByUser(String search, String tenantUUID, String userId)
    throws ITNotFoundException, ITRightException {

        if ( ! search.matches("^[a-zA-Z0-9\\-_ +]+$") ) throw new ITNotFoundException();
        if ( search.length() == 1 && search.charAt(0) == ' ' ) search = "";

        UserCacheService.UserCacheElement user = userCacheService.getUserById(userId);
        if (user == null) throw new ITRightException();
        if ( !user.user.isAdmin() ) {
            // check ownership
            if ( ! heliumDeviceCacheService.isUserLinkedToTenant(userId,tenantUUID) ) throw new ITRightException();
        }
        List<HeliumDevice> ds = heliumDeviceCacheService.searchDevices(search, tenantUUID, 50);
        if ( ds.isEmpty() ) throw new ITNotFoundException();

        ArrayList<AdvDeviceSearchGetItf> r = new ArrayList<>();
        ds.forEach( d -> {
            AdvDeviceSearchGetItf _d = new AdvDeviceSearchGetItf();
            _d.initFromHeliumDevice(d);
            r.add(_d);
        });
        return r;

    }

    // ============================================================
    // Clean old device frame
    // ============================================================
    @Scheduled(fixedRate = 86_400_000, initialDelay = 1_560_000) // every 24h / first after 26m
    protected void clearOldDeviceFrame() {
        long start = Now.NowUtcMs();
        try {
            long limit = start - ( consolePrivateConfig.getHeliumDevMaxFrameDays() * Now.ONE_FULL_DAY );
            deviceFramesMongoRepository.deleteDeviceFrameByAge(limit);
            log.info("clear_old_DeviceFrame - delete_helium_device_stats_history duration " + (Now.NowUtcMs() - start) / 1000 + "s");
        } catch ( Exception x ) {
            log.error("clear_old_DeviceFrame - error ("+x.getMessage()+") - after "+ (Now.NowUtcMs() - start) / 1000 + "s");
        }
    }


}
