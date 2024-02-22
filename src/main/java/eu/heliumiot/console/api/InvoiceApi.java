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
