package eu.heliumiot.console.api;


import eu.heliumiot.console.api.interfaces.*;
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
import java.util.List;

@Tag( name = "user api", description = "user api" )
@CrossOrigin
@RequestMapping(value = "/console/1.0/user")
@RestController
public class UserApi {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected UserService userService;

    @Autowired
    protected PrometeusService prometeusService;

    @Operation(summary = "Get user login",
            description = "Get login for user executing the request",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = UserLoginRespItf.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class)))
            }
    )
    @RequestMapping(value="/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> requestUserLogin(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get user login for "+request.getUserPrincipal().getName());
        try {
            UserLoginRespItf r = userService.getUserLogin(request.getUserPrincipal().getName());
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITNotFoundException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }


    @Operation(summary = "Get user details",
            description = "Get details on the user executing the request",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = UserDetailRespItf.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class)))
            }
    )
    @RequestMapping(value="/details",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> requestUserDetail(
            HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get user details for "+request.getUserPrincipal().getName());
        try {
            UserDetailRespItf r = userService.getUserdetails(request.getUserPrincipal().getName());
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITNotFoundException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    @Operation(summary = "Update user details",
        description = "Update details on the user executing the request",
        responses = {
            @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = UserDetailRespItf.class))),
            @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class)))
        }
    )
    @RequestMapping(value="/details",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> updateUserDetail(
        HttpServletRequest request,
        @RequestBody(required = true) UserDetailUpdateReqItf userInfo
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Update user details for "+request.getUserPrincipal().getName());
        try {
            UserDetailRespItf r = userService.updateUserdetails(request.getUserPrincipal().getName(),userInfo);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITNotFoundException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }


    @Operation(summary = "Update user agreement",
        description = "Update the user condition agreement",
        responses = {
            @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class)))
        }
    )
    @RequestMapping(value="/condition",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> updateUserCondition(
        HttpServletRequest request
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Revalidate User Agreement for "+request.getUserPrincipal().getName());
        try {
            userService.updateUserAgreement(request.getUserPrincipal().getName());
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        } catch (ITNotFoundException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.BAD_REQUEST);
        } catch (ITRightException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    // ---
    // Admin features
    // ---
    @Operation(summary = "Get user list",
        description = "Get user list by registration, based on query",
        responses = {
            @ApiResponse(responseCode = "200", description= "Done",
                content = @Content(array = @ArraySchema(schema = @Schema( implementation = UserListRespItf.class)))),
            @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class)))
        }
    )
    @RequestMapping(value="/list",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> requestUserList(
        HttpServletRequest request,
        @Parameter(required = true, name = "keyword", description = "search key")
        @RequestParam("keyword") String keyword
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Get user list for key "+keyword);
        try {
            List<UserListRespItf> r = userService.getUserlist(request.getUserPrincipal().getName(),keyword);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.BADREQUEST(), HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    @Operation(summary = "Ban one user",
        description = "Ban a given user, reset password, deactivate & clean tenants where admin",
        responses = {
            @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class)))
        }
    )
    @RequestMapping(value="/ban",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> requestUserBan(
        HttpServletRequest request,
        @RequestBody(required = true) UserBanReqItf userInfo
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Ban User "+userInfo.getUsername());
        try {
            userService.banUser(request.getUserPrincipal().getName(),userInfo.getUsername());
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
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


}
