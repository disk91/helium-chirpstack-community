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

package eu.heliumiot.console.api;


import eu.heliumiot.console.api.interfaces.*;
import eu.heliumiot.console.service.HeliumTenantService;
import eu.heliumiot.console.service.HeliumTenantSetupService;
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

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag( name = "transaction api", description = "transaction management api" )
@CrossOrigin
@RequestMapping(value = "/console/1.0/transaction")
@RestController
public class TransactionApi {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected PrometeusService prometeusService;

    @Autowired
    protected TransactionService transactionService;


    @Operation(summary = "Create a stripe transaction intent",
            description = "Create a Stripe transaction intent for a customer order",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema =@Schema(implementation = TransactionStripeReqItf.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/intent",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> requestTransactionIntentCreation(
            HttpServletRequest request,
            @RequestBody(required = true) TransactionStripeReqItf txReq
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Create transaction for "+request.getUserPrincipal().getName());
        try {
            TransactionStripeRespItf r = transactionService.initStripeTransaction(
                    request.getUserPrincipal().getName(),
                    request.getHeader("x-real-ip"),
                    txReq
            );
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch ( ITRightException x ) {
            prometeusService.addApiTotalError();
            ActionResult a = ActionResult.FORBIDDEN();
            a.setMessage(x.getMessage());
            return new ResponseEntity<>(a, HttpStatus.FORBIDDEN);
        } catch ( ITParseException x ) {
            prometeusService.addApiTotalError();
            ActionResult a = ActionResult.BADREQUEST();
            a.setMessage(x.getMessage());
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }

    }

    @Operation(summary = "Request Update on a stripe transaction intent",
            description = "Force to update the stripe transaction intent, it's managed in background also",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema =@Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/intent/{txUUID}/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> updateTransactionIntentCreation(
            HttpServletRequest request,
            @Parameter(required = true, name = "txUUID", description = "transaction Id")
            @PathVariable String txUUID
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Refresh transaction for "+request.getUserPrincipal().getName());
        try {
            transactionService.updateStripeTransaction(
                    request.getUserPrincipal().getName(),
                    txUUID
            );
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        } catch ( ITRightException x ) {
            prometeusService.addApiTotalError();
            ActionResult a = ActionResult.FORBIDDEN();
            a.setMessage(x.getMessage());
            return new ResponseEntity<>(a, HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }


    // ----

    @Operation(summary = "Get transaction list",
            description = "Get the list of transaction for a given user, empty list when none, sort by creation date",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done",
                            content = @Content(array = @ArraySchema(schema = @Schema( implementation = TransactionListRespItf.class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> requestUserTransactionList(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get transaction list for "+request.getUserPrincipal().getName());
        List<TransactionListRespItf> r = transactionService.getTransactionHistory(request.getUserPrincipal().getName());
        prometeusService.addApiTotalTimeMs(startMs);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @Operation(summary = "Get transaction list for admin",
            description = "Get the list of completed transaction for all the user in the past 60 days",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done",
                            content = @Content(array = @ArraySchema(schema = @Schema( implementation = TransactionListRespItf.class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/completed",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> requestAdminTransactionList(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get admin transaction list for "+request.getUserPrincipal().getName());
        List<TransactionListRespItf> r = transactionService.getPastStripeTransactions(Now.NowUtcMs()-(60*Now.ONE_FULL_DAY));
        prometeusService.addApiTotalTimeMs(startMs);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @Operation(summary = "Get transaction setup",
            description = "get the transaction setup : authorization to use stripe of DC transfer",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done",
                            content = @Content(array = @ArraySchema(schema = @Schema( implementation = TransactionConfigRespItf.class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/setup",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> requestTransactionSetup(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get transaction setup for "+request.getUserPrincipal().getName());
        TransactionConfigRespItf r = transactionService.getTransactionSetup();
        prometeusService.addApiTotalTimeMs(startMs);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }



    /**
     * Get the invoice for a completed transaction
     * @param request
     * @return
     */
    @Operation(summary = "Get invoice",
            description = "Create a pdf from a completed transaction",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done",
                            content = @Content(array = @ArraySchema(schema = @Schema( implementation = byte[].class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/invoice/{txUUID}/",
            produces = MediaType.APPLICATION_PDF_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> requestInvoice(
            HttpServletRequest request,
            @Parameter(required = true, name = "txUUID", description = "transaction Id")
            @PathVariable String txUUID
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get invoice "+ txUUID +" setup for "+request.getUserPrincipal().getName());
        try {
            UUID utxuuid = UUID.fromString(txUUID);

            byte [] r = transactionService.getInvoice(request.getUserPrincipal().getName(), txUUID);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch ( ITRightException x ) {
            prometeusService.addApiTotalError();
            ActionResult a = ActionResult.FORBIDDEN();
            a.setMessage(x.getMessage());
            return new ResponseEntity<>(a, HttpStatus.FORBIDDEN);
        } catch ( ITParseException | IllegalArgumentException x ) {
            prometeusService.addApiTotalError();
            ActionResult a = ActionResult.BADREQUEST();
            a.setMessage(x.getMessage());
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }



}
