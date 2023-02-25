package eu.heliumiot.console.api;


import eu.heliumiot.console.api.interfaces.*;
import eu.heliumiot.console.service.PrometeusService;
import eu.heliumiot.console.service.TransactionService;
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

@Tag( name = "invoice api", description = "invoice management api" )
@CrossOrigin
@RequestMapping(value = "/console/1.0/invoice")
@RestController
public class InvoiceApi {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected TransactionService transactionService;

    @Autowired
    protected PrometeusService prometeusService;

    // =================================
    // Invoice Setup
    // =================================


    @Operation(summary = "Get the invoice setup information",
            description = "Allow the administrator to get the invoice setup content",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema =@Schema(implementation = InvoiceSetupGetRespItf.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/setup",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> invoiceSetupRequest(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get invoice setup request "+request.getUserPrincipal().getName());
        InvoiceSetupGetRespItf r = transactionService.getInvoiceSetup();
        prometeusService.addApiTotalTimeMs(startMs);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @Operation(summary = "Update invoice setup information",
            description = "All the administrator to setup the invoice content",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema =@Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/setup",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> invoiceSetupUpdate(
            HttpServletRequest request,
            @RequestBody(required = true) InvoiceSetupPutReqItf update
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Put invoice setup request "+request.getUserPrincipal().getName());
        try {
            transactionService.updateInvoiceSetup(update);
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            ActionResult a = ActionResult.BADREQUEST();
            a.setMessage(x.getMessage());
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }




}
