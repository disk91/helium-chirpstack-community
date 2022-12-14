package eu.heliumiot.console.api;


import eu.heliumiot.console.api.interfaces.*;
import eu.heliumiot.console.service.HeliumTenantService;
import eu.heliumiot.console.service.UserService;
import fr.ingeniousthings.tools.ITNotFoundException;
import fr.ingeniousthings.tools.ITParseException;
import fr.ingeniousthings.tools.ITRightException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag( name = "tenant api", description = "Information about tenant api" )
@CrossOrigin
@RequestMapping(value = "/console/1.0/tenant")
@RestController
public class TenantApi {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HeliumTenantService heliumTenantService;

    @Operation(summary = "Get tenant balance",
            description = "Get balance for a given tenant",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = TenantBalanceItf.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/{tenantId}/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> requestTenantBalanceDetail(
            HttpServletRequest request,
            @Parameter(required = true, name = "tenantId", description = "tenant UUID")
            @PathVariable String tenantId
    ) {
        log.debug("Get tenant ID dc balance for "+request.getUserPrincipal().getName());
        try {
            TenantBalanceItf r = heliumTenantService.getTenantDcBalance(request.getUserPrincipal().getName(), tenantId);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITRightException x) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        }
    }


    @Operation(summary = "create tenant",
            description = "Crate a new tenant for an existing user",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = TenantCreateRespItf.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = TenantCreateRespItf.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = TenantCreateRespItf.class))),
            }
    )
    @RequestMapping(value="/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> requestTenantCreation(
            HttpServletRequest request,
            @RequestBody(required = true) TenantCreateReqItf tenantInfo
    ) {
        log.debug("Create tenant for "+request.getUserPrincipal().getName());
        try {
            heliumTenantService.addNewTenant(request.getUserPrincipal().getName(), tenantInfo);
            TenantCreateRespItf r = new TenantCreateRespItf();
            r.setErrorMessage("success");
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITParseException x) {
            TenantCreateRespItf r = new TenantCreateRespItf();
            r.setErrorMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            TenantCreateRespItf r = new TenantCreateRespItf();
            r.setErrorMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.FORBIDDEN);
        }
    }

}
