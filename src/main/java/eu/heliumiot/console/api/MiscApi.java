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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.api.interfaces.*;
import eu.heliumiot.console.jpa.db.HeliumLogs;
import eu.heliumiot.console.jpa.db.HeliumParameter;
import eu.heliumiot.console.jpa.repository.HeliumLogsRepository;
import eu.heliumiot.console.service.ExitService;
import eu.heliumiot.console.service.HeliumParameterService;
import eu.heliumiot.console.service.PrometeusService;
import fr.ingeniousthings.tools.Now;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static eu.heliumiot.console.service.HeliumParameterService.PARAM_USER_COND_CURRENT;

@Tag( name = "misc api", description = "misc api" )
@CrossOrigin
@RequestMapping(value = "/console/1.0/misc")
@RestController
public class MiscApi {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ConsoleConfig consoleConfig;

    @Autowired
    protected PrometeusService prometeusService;

    @Operation(summary = "Backend Version",
            description = "Returns the backend version",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = ActionResult.class)))
            }
    )
    @RequestMapping(value="/version",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> requestApplicationVersion(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        ActionResult r = ActionResult.SUCESS();
        r.setMessage(consoleConfig.getVersion());
        prometeusService.addApiTotalTimeMs(startMs);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @Operation(summary = "Backend misc status",
            description = "Returns misc status information to display on site",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = ActionResult.class)))
            }
    )
    @RequestMapping(value="/status",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    public ResponseEntity<?> requestApplicationStatus(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        ConsoleStatusRespItf r = new ConsoleStatusRespItf();
        r.setOpenForRegistration(consoleConfig.isHeliumAllowsSignup());
        prometeusService.addApiTotalTimeMs(startMs);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @Operation(summary = "Data Rx status",
            description = "Returns status about data reception",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Ok", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "204", description= "No Content", content = @Content(schema = @Schema(implementation = ActionResult.class)))
            }
    )
    @RequestMapping(value="/status/data",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    public ResponseEntity<?> requestDataStatus(
            HttpServletRequest request
    ) {
        if ( prometeusService.isDataOk() ) {
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ActionResult.NODATA(), HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Router oui",
            description = "Returns the router oui",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = ActionResult.class)))
            }
    )
    @RequestMapping(value="/oui",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> requestRouterOui(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        ActionResult r = ActionResult.SUCESS();
        r.setMessage(""+consoleConfig.getHeliumRouteOui());
        prometeusService.addApiTotalTimeMs(startMs);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @Autowired
    protected HeliumLogsRepository heliumLogsRepository;

    @Operation(summary = "Record a log",
            description = "Record a log",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = ActionResult.class)))
            }
    )
    @RequestMapping(value="/logs",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    public ResponseEntity<?> recordConsoleLog(
            HttpServletRequest request,
            @RequestBody(required = true) String log
    ) {
        if ( ! consoleConfig.isStatReportEnable() ) {
            if (log.length() < 1500) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
                try {
                    ServerLogItf sl = mapper.readValue(log, ServerLogItf.class);
                    HeliumLogs hl = new HeliumLogs();
                    hl.setInsertTime(Now.NowUtcMs());
                    hl.setInfo(log);
                    //@Todo - protect against too many attempt
                    heliumLogsRepository.save(hl);
                } catch (Exception x) {
                }
            }
        }
        ActionResult r = ActionResult.SUCESS();
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    // =================================================
    // Manage condition version
    // =================================================

    @Autowired
    protected HeliumParameterService heliumParameterService;

    @Operation(summary = "Get Current Condition Version",
        description = "Returns the current user condition version",
        responses = {
            @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = ActionResult.class)))
        }
    )
    @RequestMapping(value="/condition",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> requestConditionVersion(
        HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        ActionResult r = ActionResult.SUCESS();
        HeliumParameter version = heliumParameterService.getParameter(PARAM_USER_COND_CURRENT);
        if ( version != null ) r.setMessage(version.getStrValue());
        prometeusService.addApiTotalTimeMs(startMs);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }


    @Operation(summary = "Change Current Condition Version",
        description = "Change the current user condition version",
        responses = {
            @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = ActionResult.class)))
        }
    )
    @RequestMapping(value="/condition",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> changeConditionVersion(
        HttpServletRequest request,
        @RequestBody(required = true) UserConditionVersionUpdateReqItf versionChange
    ) {
        long startMs= Now.NowUtcMs();
        ActionResult r = ActionResult.SUCESS();
        HeliumParameter version = heliumParameterService.getParameter(PARAM_USER_COND_CURRENT);
        if ( version != null ) version.setStrValue(versionChange.getConditionVersion());
        heliumParameterService.flushParameter(version);
        r.setMessage("Version Updated");
        prometeusService.addApiTotalTimeMs(startMs);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }


}

