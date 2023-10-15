package eu.heliumiot.console.api;


import eu.heliumiot.console.api.interfaces.*;
import eu.heliumiot.console.service.HeliumTenantService;
import eu.heliumiot.console.service.HeliumTenantSetupService;
import eu.heliumiot.console.service.PrometeusService;
import eu.heliumiot.console.service.UserService;
import fr.ingeniousthings.tools.ITNotFoundException;
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

    @Autowired
    protected PrometeusService prometeusService;


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
        long startMs= Now.NowUtcMs();
        log.debug("Get tenant ID dc balance for "+request.getUserPrincipal().getName());
        try {
            TenantBalanceItf r = heliumTenantService.getTenantDcBalance(request.getUserPrincipal().getName(), tenantId);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiDcBalanceTimeMs(startMs);
            prometeusService.addApiTotalTimeMs(startMs);
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
        long startMs= Now.NowUtcMs();
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
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
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
        long startMs= Now.NowUtcMs();
        log.debug("Get tenant setup for "+request.getUserPrincipal().getName());
        try {
            TenantSetupRespItf r = heliumTenantService.getTenantSetup(request.getUserPrincipal().getName(), tenantId);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
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
        long startMs= Now.NowUtcMs();
        log.debug("Create tenant for "+request.getUserPrincipal().getName());
        try {
            heliumTenantService.addNewTenant(request.getUserPrincipal().getName(), tenantInfo);
            TenantCreateRespItf r = new TenantCreateRespItf();
            r.setErrorMessage("success");
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            TenantCreateRespItf r = new TenantCreateRespItf();
            r.setErrorMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            TenantCreateRespItf r = new TenantCreateRespItf();
            r.setErrorMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
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
        long startMs= Now.NowUtcMs();
        log.debug("Get list of tenant setup template "+request.getUserPrincipal().getName());
        try {
            List<TenantSetupTemplateListRespItf> r = heliumTenantSetupService.getTenantSetupTemplates(request.getUserPrincipal().getName());
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    @Operation(summary = "Get One tenant setup",
        description = "Get one tenant template",
        responses = {
            @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = TenantSetupTemplateListRespItf.class))),
            @ApiResponse(responseCode = "400", description= "Parse Error - not found", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
        }
    )
    @RequestMapping(value="/setup/detail/{tenantId}/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> requestOneTenantSetup(
        HttpServletRequest request,
        @Parameter(required = true, name = "tenantId", description = "tenant Id")
        @PathVariable String tenantId
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get one tenant setup "+request.getUserPrincipal().getName());
        try {
            TenantSetupTemplateListRespItf r = heliumTenantSetupService.getOneTenantSetup(request.getUserPrincipal().getName(), tenantId);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } catch (ITNotFoundException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.BADREQUEST(), HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
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
        long startMs= Now.NowUtcMs();
        log.debug("Update one tenant setup template "+request.getUserPrincipal().getName());
        try {
            heliumTenantSetupService.updateTenantSetupTemplates(request.getUserPrincipal().getName(), template);
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
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
        long startMs= Now.NowUtcMs();
        log.debug("Create one tenant setup template "+request.getUserPrincipal().getName());
        try {
            heliumTenantSetupService.createTenantSetupTemplates(request.getUserPrincipal().getName(), template);
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.CREATED);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
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
        long startMs= Now.NowUtcMs();
        log.debug("Delete tenant setup ("+tenantId+") by "+request.getUserPrincipal().getName());
        try {
            heliumTenantSetupService.deleteTenantSetupTemplate(request.getUserPrincipal().getName(), tenantId);
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    // #####################

    @Operation(summary = "Create coupon codes",
            description = "Create coupon codes for using a specific registration profile",
            responses = {
                    @ApiResponse(responseCode = "201", description= "Created",
                            content = @Content(array = @ArraySchema(schema = @Schema( implementation = TenantSetupTemplateCouponRespItf.class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/coupon",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> createCouponCode(
            HttpServletRequest request,
            @RequestBody(required = true) TenantSetupTemplateCouponReqItf couponReq
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Create coupon for "+request.getUserPrincipal().getName());
        try {
            List<TenantSetupTemplateCouponRespItf> r = heliumTenantSetupService.createTenantSetupTemplatesCoupon(request.getUserPrincipal().getName(), couponReq);
            return new ResponseEntity<>(r, HttpStatus.CREATED);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.BADREQUEST(), HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    @Operation(summary = "List coupon codes",
            description = "List coupon codes for using a specific registration profile",
            responses = {
                    @ApiResponse(responseCode = "200", description= "OK",
                            content = @Content(array = @ArraySchema(schema = @Schema( implementation = CouponListRespItf.class)))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/coupon/{active}/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> listAllCouponCode(
            HttpServletRequest request,
            @Parameter(required = true, name = "active", description = "1 to get only the active coupons")
            @PathVariable int active
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("List coupon for "+request.getUserPrincipal().getName());
        try {
            boolean filter = (active == 1);
            List<CouponListRespItf> r = heliumTenantSetupService.listTenantSetupTemplatesCoupon(request.getUserPrincipal().getName(), filter);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
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
        long startMs= Now.NowUtcMs();
        log.debug("Search list of tenant "+request.getUserPrincipal().getName()+" with search key "+keyword);
        try {
            List<TenantSearchRespItf> r = heliumTenantService.searchTenants(keyword);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
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
        long startMs= Now.NowUtcMs();
        log.debug("Update one tenant dc balance "+request.getUserPrincipal().getName());
        try {
            if (heliumTenantService.processBalanceIncrease(balance.getTenantUUID(), balance.getDcs())) {
                return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
            } else {
                prometeusService.addApiTotalError();
                return new ResponseEntity<>(ActionResult.BADREQUEST(), HttpStatus.BAD_REQUEST);
            }
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
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
        long startMs= Now.NowUtcMs();
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
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
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
        long startMs= Now.NowUtcMs();
        log.debug("Get tenant basic stats "+request.getUserPrincipal().getName()+" for tenant "+tenantId);
        try {
            TenantBasicStatRespItf r = heliumTenantService.getTenantBasicStat(request.getUserPrincipal().getName(),tenantId);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.BADREQUEST(), HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    @Operation(summary = "Get tenant last 30 days activity stats",
        description = "Get tenant 30 days per day activity information, formatted for frontend bar chart",
        responses = {
            @ApiResponse(responseCode = "200", description= "Ok", content = @Content(schema = @Schema(implementation = TenantSetupStatsRespItf.class))),
            @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
        }
    )
    @RequestMapping(value="/{tenantId}/activity",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> getTenantActivityStat(
        HttpServletRequest request,
        @Parameter(required = true, name = "tenantId", description = "tenant UUID")
        @PathVariable String tenantId
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get tenant activity stats "+request.getUserPrincipal().getName()+" for tenant "+tenantId);
        try {
            TenantSetupStatsRespItf r = heliumTenantService.getTenantActivityStat(request.getUserPrincipal().getName(),tenantId);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.BADREQUEST(), HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    @Operation(summary = "Get 10 most consuming uplink devices in a tenant for last 2 days activity stats",
        description = "Get 10 most uplink + join req consuming devices in a given tenant, formatted for frontend bar chart",
        responses = {
            @ApiResponse(responseCode = "200", description= "Ok", content = @Content(schema = @Schema(implementation = TenantSetupStatsRespItf.class))),
            @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
        }
    )
    @RequestMapping(value="/{tenantId}/topdevices",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> getTenantDeviceActivityStat(
        HttpServletRequest request,
        @Parameter(required = true, name = "tenantId", description = "tenant UUID")
        @PathVariable String tenantId
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get tenant device activity stats "+request.getUserPrincipal().getName()+" for tenant "+tenantId);
        try {
            TenantSetupStatsRespItf r = heliumTenantService.getTenantDeviceActivityStat(request.getUserPrincipal().getName(),tenantId);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.BADREQUEST(), HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    @Operation(summary = "Get 10 devices with no uplink activity but other with 24h",
        description = "Get 10 devices w/o activity for uplink within last 24h, formatted for frontend bar chart",
        responses = {
            @ApiResponse(responseCode = "200", description= "Ok", content = @Content(schema = @Schema(implementation = TenantSetupStatsRespItf.class))),
            @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
        }
    )
    @RequestMapping(value="/{tenantId}/inactive",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> getTenantDeviceInactivityStat(
        HttpServletRequest request,
        @Parameter(required = true, name = "tenantId", description = "tenant UUID")
        @PathVariable String tenantId
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get tenant device inactivity stats "+request.getUserPrincipal().getName()+" for tenant "+tenantId);
        try {
            TenantSetupStatsRespItf r = heliumTenantService.getTenantDeviceInactivityStat(request.getUserPrincipal().getName(),tenantId);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.BADREQUEST(), HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    @Operation(summary = "Get 10 devices with no uplink activity but other with 24h",
        description = "Get 10 devices w/o activity for uplink within last 24h, formatted for frontend bar chart",
        responses = {
            @ApiResponse(responseCode = "200", description= "Ok", content = @Content(schema = @Schema(implementation = TenantSetupStatsRespItf.class))),
            @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
        }
    )
    @RequestMapping(value="/topactive",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getTopTenantActivityStat(
        HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get top tenant activity stats "+request.getUserPrincipal().getName()+" for all tenant ");
        try {
            TenantSetupStatsRespItf r = heliumTenantService.getTopTenantActivityStat(request.getUserPrincipal().getName());
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.BADREQUEST(), HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    // ==========================================
    // Manage Tenant Api keys
    // ==========================================

    @Operation(summary = "Create an API key for migrating devices",
            description = "Create an API key for a migration, front will be able to automate the operations",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema( implementation = TenantApiKeyRespItf.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/key/{tenantId}/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> requestTenantApiKey(
            HttpServletRequest request,
            @Parameter(required = true, name = "tenantId", description = "tenant Id")
            @PathVariable String tenantId

    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Create API key for "+request.getUserPrincipal().getName());
        try {
            if ( request.getHeader("X-Chripstack-Bearer") == null || request.getHeader("X-Chripstack-Bearer").length() < 10  ) {
                throw new ITRightException("invalid_bearer");
            }
            String bearer = request.getHeader("X-Chripstack-Bearer");
            TenantApiKeyRespItf r = heliumTenantService.getTenantApiKey(request.getUserPrincipal().getName(), tenantId,bearer);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITRightException x) {
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

    @Operation(summary = "Delete API keys created for migrating devices",
            description = "Delete all the API key created for the migration",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema( implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/key/{tenantId}/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> deleteTenantApiKey(
            HttpServletRequest request,
            @Parameter(required = true, name = "tenantId", description = "tenant Id")
            @PathVariable String tenantId

    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Delete API key for "+request.getUserPrincipal().getName());
        try {
            if ( request.getHeader("X-Chripstack-Bearer") == null && request.getHeader("X-Chripstack-Bearer").length() > 10  ) {
                throw new ITRightException("invalid_bearer");
            }
            String bearer = request.getHeader("X-Chripstack-Bearer");

            heliumTenantService.clearMigrationApiKey(request.getUserPrincipal().getName(), tenantId, bearer);
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        } catch (ITRightException x) {
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

    // --------------------------------------
    // Update Max Copy


    @Operation(summary = "Update Max Copy for a tenant ",
            description = "Update Max Copy for a tenant, update the associted route param",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Ok", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/maxcopy",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> updateMaxCopy(
            HttpServletRequest request,
            @RequestBody(required = true) TenantUpdateMaxCopyReqItf maxCopyItf
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Update Max copy for tenants "+request.getUserPrincipal().getName());

        try {
            heliumTenantService.updateMaxCopyValue(
                    request.getUserPrincipal().getName(),
                    maxCopyItf
            );
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        } catch ( ITRightException x ) {
            ActionResult err = ActionResult.FORBIDDEN();
            err.setMessage(x.getMessage());
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
        } catch ( ITParseException x ) {
            ActionResult err = ActionResult.FAILED();
            err.setMessage(x.getMessage());
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

}
