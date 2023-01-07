package eu.heliumiot.console.api;


import eu.heliumiot.console.api.interfaces.*;
import eu.heliumiot.console.service.HeliumTenantService;
import eu.heliumiot.console.service.HeliumTenantSetupService;
import eu.heliumiot.console.service.UserService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Tag( name = "tenant api", description = "Information about tenant api" )
@CrossOrigin
@RequestMapping(value = "/console/1.0/tenant")
@RestController
public class TenantApi {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HeliumTenantService heliumTenantService;

    @Autowired
    protected HeliumTenantSetupService heliumTenantSetupService;

    @Operation(summary = "Get tenant balance",
            description = "Get balance for a given tenant",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = TenantBalanceItf.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/balance/{tenantId}/",
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

    @Operation(summary = "Get all tenant balances, not owned balances are unknown",
            description = "Get balances for a given user",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done",
                            content = @Content(array = @ArraySchema(schema = @Schema( implementation = TenantBalancesItf.class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/balance",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> requestUserTenantBalancesDetail(
            HttpServletRequest request,
            @Parameter(required = false, name = "notOwned", description = "get tenant including not owned, in this case balance returned will be 0")
            @RequestParam("notOwned") Optional<String> notOwned
    ) {
        log.debug("Get user tenants balances for "+request.getUserPrincipal().getName());
        try {
            List<TenantBalancesItf> r;
            if ( ! notOwned.isEmpty() && notOwned.get().compareToIgnoreCase("true") == 0 ) {
                r = heliumTenantService.getAllTenantDcBalances(request.getUserPrincipal().getName());
            } else {
                r = heliumTenantService.getTenantDcBalances(request.getUserPrincipal().getName());
            }
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITRightException x) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        }
    }


    @Operation(summary = "Get tenant setup public configuration",
            description = "Get the tenant setup public configuration",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done",
                            content = @Content(array = @ArraySchema(schema = @Schema( implementation = TenantSetupRespItf.class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/setup/{tenantId}/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> requestTenantSetupPublic(
            HttpServletRequest request,
            @Parameter(required = true, name = "tenantId", description = "tenant Id")
            @PathVariable String tenantId

    ) {
        log.debug("Get tenant setup for "+request.getUserPrincipal().getName());
        try {
            TenantSetupRespItf r = heliumTenantService.getTenantSetup(request.getUserPrincipal().getName(), tenantId);
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

    // =================================================================
    // TENANT SETUP (ADMIN)
    // =================================================================

    @Operation(summary = "Get tenant setup templates",
            description = "Get the tenant setup template list",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done",
                                 content = @Content(array = @ArraySchema(schema = @Schema( implementation = TenantSetupTemplateListRespItf.class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/setup/templates",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> requestTenantSetupTemplate(
            HttpServletRequest request
    ) {
        log.debug("Get list of tenant setup template "+request.getUserPrincipal().getName());
        try {
            List<TenantSetupTemplateListRespItf> r = heliumTenantSetupService.getTenantSetupTemplates(request.getUserPrincipal().getName());
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITRightException x) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        }
    }

    // ########

    @Operation(summary = "Update tenant setup templates",
            description = "Update the tenant setup template",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Ok", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/setup",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateTenantSetupTemplate(
            HttpServletRequest request,
            @RequestBody(required = true) TenantSetupTemplateUpdateReqItf template
    ) {
        log.debug("Update one tenant setup template "+request.getUserPrincipal().getName());
        try {
            heliumTenantSetupService.updateTenantSetupTemplates(request.getUserPrincipal().getName(), template);
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        } catch (ITRightException x) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        }
    }

    // ########

    @Operation(summary = "Create new tenant setup templates",
            description = "Create new the tenant setup template",
            responses = {
                    @ApiResponse(responseCode = "201", description= "Created", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/setup",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> createTenantSetupTemplate(
            HttpServletRequest request,
            @RequestBody(required = true) TenantSetupTemplateCreateReqItf template
    ) {
        log.debug("Create one tenant setup template "+request.getUserPrincipal().getName());
        try {
            heliumTenantSetupService.createTenantSetupTemplates(request.getUserPrincipal().getName(), template);
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.CREATED);
        } catch (ITRightException x) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        }
    }

    // ########

    @Operation(summary = "Delete tenant setup",
            description = "Delete  given tenant setup. Default can't be deleted",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/setup/{tenantId}/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTenantSetup(
            HttpServletRequest request,
            @Parameter(required = true, name = "tenantId", description = "tenant Id")
            @PathVariable String tenantId
    ) {
        log.debug("Delete tenant setup ("+tenantId+") by "+request.getUserPrincipal().getName());
        try {
            heliumTenantSetupService.deleteTenantSetupTemplate(request.getUserPrincipal().getName(), tenantId);
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        } catch (ITRightException x) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        }
    }

    // #####################

    @Operation(summary = "Search tenants based on keyword",
            description = "get about 10-20 tenants based on keyword. Look at UUIDs, Name and email." +
                    "The search key is any string from 3 to 15 chars.",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done",
                            content = @Content(array = @ArraySchema(schema = @Schema( implementation = TenantSearchRespItf.class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/search/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> searchTenant(
            HttpServletRequest request,
            @Parameter(required = true, name = "keyword", description = "search key")
            @RequestParam("keyword") String keyword
    ) {
        log.debug("Search list of tenant "+request.getUserPrincipal().getName()+" with search key "+keyword);
        try {
            List<TenantSearchRespItf> r = heliumTenantService.searchTenants(keyword);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITParseException x) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        }
    }

    // #########################

    @Operation(summary = "Update tenant DC balance",
            description = "Update a tenant DC balance",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Ok", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/balance",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateTenantDCBalance(
            HttpServletRequest request,
            @RequestBody(required = true) TenantDcBalanceReqItf balance
    ) {
        log.debug("Update one tenant dc balance "+request.getUserPrincipal().getName());
        if ( heliumTenantService.processBalanceIncrease(balance.getTenantUUID(), balance.getDcs()) ){
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ActionResult.BADREQUEST(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Transfer tenant DC ",
            description = "Transfer DCs between tenant you have access",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Ok", content = @Content(schema = @Schema(implementation = TenantDcTransferRespItf.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/dc/transfer",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> transferTenantDC(
            HttpServletRequest request,
            @RequestBody(required = true) TenantDcTransferReqItf transferDcs
    ) {
        log.debug("Transfer DCs between tenants "+request.getUserPrincipal().getName());

        /*
        Iterator<String> hs = request.getHeaderNames().asIterator();
        while (  hs.hasNext() ) {
            String h = hs.next();
            log.info("### header : "+h+" value"+request.getHeader(h));
        }
         */

        try {
            TenantDcTransferRespItf r = heliumTenantService.transferDcBetweenTenant(
                    request.getUserPrincipal().getName(),
                    request.getHeader("x-real-ip"),
                    transferDcs
            );
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch ( ITRightException x ) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        }
    }


    // ######################

    @Operation(summary = "Get tenant information",
            description = "Get tenant basic consumption information with tenant configuration and " +
                    "last day consumption",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Ok", content = @Content(schema = @Schema(implementation = TenantBasicStatRespItf.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/{tenantId}/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> getTenantBasicStat(
            HttpServletRequest request,
            @Parameter(required = true, name = "tenantId", description = "tenant UUID")
            @PathVariable String tenantId
    ) {
        log.debug("Get tenant basic stats "+request.getUserPrincipal().getName()+" for tenant "+tenantId);
        try {
            TenantBasicStatRespItf r = heliumTenantService.getTenantBasicStat(request.getUserPrincipal().getName(),tenantId);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITParseException x) {
            return new ResponseEntity<>(ActionResult.BADREQUEST(), HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        }
    }


}
