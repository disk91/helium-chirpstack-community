package eu.heliumiot.console.api;


import eu.heliumiot.console.api.interfaces.*;
import eu.heliumiot.console.service.HeliumMessageService;
import eu.heliumiot.console.service.HeliumTicketService;
import eu.heliumiot.console.service.PrometeusService;
import fr.ingeniousthings.tools.ITParseException;
import fr.ingeniousthings.tools.ITRightException;
import fr.ingeniousthings.tools.Now;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import java.util.List;

@Tag( name = "ticket api", description = "Ticket api" )
@CrossOrigin
@RequestMapping(value = "/console/1.0/ticket")
@RestController
public class TicketApi {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HeliumTicketService heliumTicketService;

    @Autowired
    protected PrometeusService prometeusService;

    @Operation(summary = "Get ticket list",
            description = "Get my tickets or user tickets when admin, list all pending",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done",
                            content = @Content(array = @ArraySchema(schema = @Schema( implementation = TicketListRespItf.class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> getTickets(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get tickets for "+request.getUserPrincipal().getName());
        try {
            List<TicketListRespItf> r = heliumTicketService.getUserTickets(request.getUserPrincipal().getName());
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            ActionResult r = ActionResult.FORBIDDEN();
            r.setMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    @Operation(summary = "Count Pending Tickets",
        description = "Count my pending tickets or user tickets when admin",
        responses = {
            @ApiResponse(responseCode = "200", description= "Done",
                content = @Content(array = @ArraySchema(schema = @Schema( implementation = TicketCountRespItf.class)))),
            @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
        }
    )
    @RequestMapping(value="/count",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> countTickets(
        HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Count tickets for "+request.getUserPrincipal().getName());
        try {
            TicketCountRespItf r = heliumTicketService.countUserTickets(request.getUserPrincipal().getName());
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            ActionResult r = ActionResult.FORBIDDEN();
            r.setMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    @Operation(summary = "Get ticket detail",
            description = "Get details of one given ticket",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema( implementation = TicketDetailRespItf.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/{ticketId}/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> getTicketDetails(
            HttpServletRequest request,
            @Parameter(required = true, name = "messId", description = "ticket Id")
            @PathVariable String ticketId

    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get ticket details for "+request.getUserPrincipal().getName()+", id "+ticketId);
        try {
            TicketDetailRespItf r = heliumTicketService.getTicketDetails(request.getUserPrincipal().getName(), ticketId);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            ActionResult r = ActionResult.FORBIDDEN();
            r.setMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }


    @Operation(summary = "Create a new user ticket",
            description = "Create a new user ticket",
            responses = {
                    @ApiResponse(responseCode = "201", description= "Created", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> createTicket(
            HttpServletRequest request,
            @RequestBody(required = true) TicketCreationReqItf ticket
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Create a ticket by "+request.getUserPrincipal().getName());
        try {
            heliumTicketService.createTicket(request.getUserPrincipal().getName(),ticket);
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.CREATED);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            ActionResult r =  ActionResult.BADREQUEST();
            r.setMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            ActionResult r =  ActionResult.FORBIDDEN();
            r.setMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    @Operation(summary = "Add a response to a ticket",
            description = "Add a response to a ticket",
            responses = {
                    @ApiResponse(responseCode = "201", description= "Updated", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> updateTicket(
            HttpServletRequest request,
            @RequestBody(required = true) TicketResponseReqItf ticketResponse
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Add ticket response by "+request.getUserPrincipal().getName());
        try {
            heliumTicketService.addResponse(request.getUserPrincipal().getName(),ticketResponse);
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.CREATED);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            ActionResult r =  ActionResult.BADREQUEST();
            r.setMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            ActionResult r =  ActionResult.FORBIDDEN();
            r.setMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

}
