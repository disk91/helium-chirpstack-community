package eu.heliumiot.console.api;


import eu.heliumiot.console.api.interfaces.*;
import eu.heliumiot.console.service.HeliumMessageService;
import eu.heliumiot.console.service.HeliumTenantService;
import eu.heliumiot.console.service.HeliumTenantSetupService;
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

@Tag( name = "message api", description = "Messages sent to users api" )
@CrossOrigin
@RequestMapping(value = "/console/1.0/message")
@RestController
public class MessageApi {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HeliumMessageService heliumMessageService;

    @Autowired
    protected PrometeusService prometeusService;


    @Operation(summary = "Get public Messages for front page",
            description = "Get public message for front page",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done",
                            content = @Content(array = @ArraySchema(schema = @Schema( implementation = MessagePendingRespItf.class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/public",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    public ResponseEntity<?> getPublicMessage(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        List<MessagePendingRespItf> r = heliumMessageService.getHeliumMessageFrontPage();
        prometeusService.addApiTotalTimeMs(startMs);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }


    @Operation(summary = "Get pending Messages",
            description = "Get pending messages to be displayed to user",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done",
                            content = @Content(array = @ArraySchema(schema = @Schema( implementation = MessagePendingRespItf.class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> getPendingMessage(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get pending message for "+request.getUserPrincipal().getName());
        try {
            List<MessagePendingRespItf> r = heliumMessageService.getHeliumMessageForUser(request.getUserPrincipal().getName());
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }


    @Operation(summary = "Ack pending Messages",
            description = "Ack pending messages to be displayed to user",
            responses = {
                    @ApiResponse(responseCode = "200", description= "OK", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> ackPendingMessage(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Ack pending message for "+request.getUserPrincipal().getName());
        try {
            heliumMessageService.ackHeliumMessageForUser(request.getUserPrincipal().getName());
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }


    // =================================================================
    // USER MESSAGE SETUP (ADMIN)
    // =================================================================

    @Operation(summary = "Get last user messages",
            description = "Get last 5 messages as a list",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done",
                                 content = @Content(array = @ArraySchema(schema = @Schema( implementation = MessageItf.class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/admin",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getMessages(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get list of messages by admin "+request.getUserPrincipal().getName());
        List<MessageItf> r = heliumMessageService.getLastMessages(5);
        prometeusService.addApiTotalTimeMs(startMs);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    // ########

    @Operation(summary = "Update one of the user message",
            description = "Update one of the user message",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Ok", content = @Content(schema = @Schema(implementation = MessageItf.class))),
                    @ApiResponse(responseCode = "400", description= "Bad request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/admin",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateMessage(
            HttpServletRequest request,
            @RequestBody(required = true) MessageItf message
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Update one message by admin "+request.getUserPrincipal().getName());
        try {
            MessageItf r = heliumMessageService.updateMessage(message);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            ActionResult r =  ActionResult.BADREQUEST();
            r.setMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    // ########

    @Operation(summary = "Create a new user message",
            description = "Create a new user message",
            responses = {
                    @ApiResponse(responseCode = "201", description= "Created", content = @Content(schema = @Schema(implementation = MessageItf.class))),
                    @ApiResponse(responseCode = "400", description= "Bad request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/admin",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> createMessage(
            HttpServletRequest request,
            @RequestBody(required = true) MessageItf message
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Create a message by admin "+request.getUserPrincipal().getName());
        try {
            MessageItf r = heliumMessageService.createMessage(message);
            return new ResponseEntity<>(r, HttpStatus.CREATED);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            ActionResult r =  ActionResult.BADREQUEST();
            r.setMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    // ########

    @Operation(summary = "Delete a user message",
            description = "Delete given user message",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/admin/{messId}/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteMessage(
            HttpServletRequest request,
            @Parameter(required = true, name = "messId", description = "message Id")
            @PathVariable String messId
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Delete message ("+messId+") by "+request.getUserPrincipal().getName());
        try {
            heliumMessageService.deleteMessage(messId);
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            ActionResult r =  ActionResult.BADREQUEST();
            r.setMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

}
