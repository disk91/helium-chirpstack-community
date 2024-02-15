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
package eu.heliumiot.console.api;

import eu.heliumiot.console.api.interfaces.ActionResult;
import eu.heliumiot.console.api.interfaces.DeviceFramesGetItf;
import eu.heliumiot.console.api.interfaces.HotspotGetItf;
import eu.heliumiot.console.etl.api.HotspotIdent;
import eu.heliumiot.console.service.*;
import fr.ingeniousthings.tools.ITNotFoundException;
import fr.ingeniousthings.tools.ITParseException;
import fr.ingeniousthings.tools.ITRightException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag( name = "advanced admin api", description = "advanced admin api" )
@CrossOrigin
@RequestMapping(value = "/console/1.0/adv")
@RestController
public class AdvancedApi {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected PrometeusService prometeusService;

    @Autowired
    protected NovaService novaService;

    // =========================================================================================
    // Services
    // =========================================================================================

    @Operation(summary = "Check skfs for given addr, eventually clear the extra session key",
            description = "Search for missing session and extra session on a given addr" +
                "result is printed in log file, execution is asynchronous after this request.",
            responses = {
                @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = ActionResult.class)))
            }
    )
    @RequestMapping(value="/checkaddr/{addr}/{clear}/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getCheckAddrAndClear(
        HttpServletRequest request,
        @Parameter(required = true, name = "addr", description = "address to be verified")
        @PathVariable String addr,
        @Parameter(required = true, name = "clear", description = "when true, the extra session are cleared ")
        @PathVariable String clear
    ) {
        log.info("Request to check the sessions");
        int iaddr = Integer.parseInt(addr,16);
        boolean bclear = clear.compareToIgnoreCase("true") == 0;
        novaService.verifySKFsForAGivenAddr(iaddr,bclear);
        return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
    }

    // =========================================================================================
    // Context
    // =========================================================================================

    @Autowired
    protected PrivDeviceFramesService privDeviceFramesService;

    @Operation(summary = "Get the frame history for a given device",
        description = "Get the frame history with hotspot used during communications",
        responses = {
            @ApiResponse(responseCode = "200", description= "Device Frames", content = @Content(schema = @Schema(implementation = DeviceFramesGetItf.class))),
            @ApiResponse(responseCode = "204", description= "No Device data", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            @ApiResponse(responseCode = "403", description= "Not allowed", content = @Content(schema = @Schema(implementation = ActionResult.class)))
        }
    )
    @RequestMapping(value="/device/{devEui}/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> getDeviceFrames(
        HttpServletRequest request,
        @Parameter(required = true, name = "devEui", description = "Device Eui")
        @PathVariable String devEui
    ) {
        log.debug("Get device frame history");
        try {
            DeviceFramesGetItf r = privDeviceFramesService.getDeviceByUser(devEui, request.getUserPrincipal().getName());
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITNotFoundException x) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } catch (ITRightException x) {
            return new ResponseEntity<>(ActionResult.NODATA(), HttpStatus.NO_CONTENT);
        }
    }

    @Autowired
    protected PrivHotspotService privHotspotService;

    @Operation(summary = "Get existing hotspot around",
        description = "Get the existing hotspot around",
        responses = {
            @ApiResponse(responseCode = "200", description= "Hotspot List",
                content = @Content(array = @ArraySchema(schema = @Schema( implementation = DeviceFramesGetItf.class)))),
            @ApiResponse(responseCode = "204", description= "No Hostpot data", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            @ApiResponse(responseCode = "503", description= "Unavailable", content = @Content(schema = @Schema(implementation = ActionResult.class)))
        }
    )
    @RequestMapping(value="/hotspots-around/{latN}/{latS}/{lonW}/{lonE}/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> getHotspotsAround(
        HttpServletRequest request,
        @Parameter(required = true, name = "latN", description = "North Latitude")
        @PathVariable double latN,
        @Parameter(required = true, name = "latS", description = "South Latitude")
        @PathVariable double latS,
        @Parameter(required = true, name = "lonW", description = "West Longitude")
        @PathVariable double lonW,
        @Parameter(required = true, name = "lonE", description = "East Longitude")
        @PathVariable double lonE
    ) {
        log.debug("Get hotspots around proxy");
        try {
            List<HotspotIdent> r = privHotspotService.getHostpotAround(latN,latS,lonW,lonE);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITNotFoundException x) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } catch (ITParseException x) {
            return new ResponseEntity<>(ActionResult.FAILED(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Operation(summary = "Get details on 1 specific hotspot",
        description = "Get details on a given hotspot including ETL data (asyn resfresh)",
        responses = {
            @ApiResponse(responseCode = "200", description= "Hotspot data", content = @Content(schema = @Schema(implementation = DeviceFramesGetItf.class))),
            @ApiResponse(responseCode = "204", description= "No hotspot data", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            @ApiResponse(responseCode = "403", description= "Not allowed", content = @Content(schema = @Schema(implementation = ActionResult.class)))
        }
    )
    @RequestMapping(value="/hotspot/{hotspotId}/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> getHotspotDetails(
        HttpServletRequest request,
        @Parameter(required = true, name = "hotspotId", description = "Hotspot Id")
        @PathVariable String hotspotId
    ) {
        log.debug("Get hotspot details");
        try {
            HotspotGetItf r = privHotspotService.getHotspotForUser(hotspotId, request.getUserPrincipal().getName());
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITNotFoundException x) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } catch (ITRightException x) {
            return new ResponseEntity<>(ActionResult.NODATA(), HttpStatus.NO_CONTENT);
        }
    }



}

