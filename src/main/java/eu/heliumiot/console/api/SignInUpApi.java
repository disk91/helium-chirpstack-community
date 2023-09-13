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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag( name = "admin api", description = "admin api" )
@CrossOrigin
@RequestMapping(value = "/console/1.0/sign")
@RestController
public class SignInUpApi {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected UserService userService;

    @Autowired
    protected PrometeusService prometeusService;


    @Operation(summary = "Sign-in endpoint",
            description = "Authenticate on back-end and in chirpstack, get both token",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = LoginRespItf.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class)))
            }
    )
    @RequestMapping(value="/in",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    public ResponseEntity<?> requestUserSignIn(
            HttpServletRequest request,
            @RequestBody(required = true) LoginReqItf login
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Sign in for "+login.getUsername());
        try {
            LoginRespItf r = userService.verifyUserLogin(login);
            prometeusService.addUserTotalLogin();
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITNotFoundException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
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

    @Operation(summary = "Sign-up endpoint",
            description = "Create account on back-end and in chirpstack",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = UserSignUpRespItf.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = UserSignUpRespItf.class)))
            }
    )
    @RequestMapping(value="/up",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    public ResponseEntity<?> requestUserSignUp(
            HttpServletRequest request,
            @RequestBody(required = true) UserSignUpReqItf signup
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Sign up for "+signup.getUsername());
        try {
            UserSignUpRespItf r = userService.userSignup(signup,request.getRemoteAddr());
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITNotFoundException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            // manage errors
            UserSignUpRespItf r = new UserSignUpRespItf();
            r.setErrorMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

    @Operation(summary = "Sign-up confirmation endpoint",
            description = "Create account on back-end and in chirpstack",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = UserSignUpConfirmRespItf.class))),
                    @ApiResponse(responseCode = "403", description= "Forbidden", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = UserSignUpConfirmRespItf.class)))
            }
    )
    @RequestMapping(value="/confirm/{confirmationId}/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    public ResponseEntity<?> requestUserSignUpConfirm(
            HttpServletRequest request,
            @Parameter(required = true, name = "confirmationId", description = "confirmation code obtained by email")
            @PathVariable("confirmationId") String confirmationId

    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Sign up confirmation received");
        UserSignUpConfirmRespItf r = new UserSignUpConfirmRespItf();
        try {
            userService.userSignupConfirmation(confirmationId);
            r.setErrorMessage("success");
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITNotFoundException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            r.setErrorMessage(x.getMessage());
            return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }


    @Operation(summary = "Lost Password endpoint",
            description = "Request a way to change the user password, send an email with a key...",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = ActionResult.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = ActionResult.class)))
            }
    )
    @RequestMapping(value="/lost",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    public ResponseEntity<?> requestUserLostPassword(
            HttpServletRequest request,
            @RequestBody(required = true) UserLostPassReqItf lost
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Password lost for "+lost.getUsername());
        try {
            userService.userLostReq(lost);
            return new ResponseEntity<>(ActionResult.SUCESS(), HttpStatus.OK);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            return new ResponseEntity<>(ActionResult.BADREQUEST(), HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }


    @Operation(summary = "Password Change endpoint",
            description = "Change the user password after an email validation",
            responses = {
                    @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = UserPassChangeRespItf.class))),
                    @ApiResponse(responseCode = "400", description= "Bad Request", content = @Content(schema = @Schema(implementation = UserPassChangeRespItf.class)))
            }
    )
    @RequestMapping(value="/password",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    public ResponseEntity<?> requestUserPasswordChange(
            HttpServletRequest request,
            @RequestBody(required = true) UserPassResetReqItf change
    ) {
        long startMs= Now.NowUtcMs();
        log.debug("Password change with key "+change.getValidationKey());
        try {
            userService.userPasswordReset(change);
            UserPassChangeRespItf resp = new UserPassChangeRespItf();
            resp.setErrorMessage("success");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (ITParseException x) {
            prometeusService.addApiTotalError();
            UserPassChangeRespItf resp = new UserPassChangeRespItf();
            resp.setErrorMessage(x.getMessage());
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        } finally {
            prometeusService.addApiTotalTimeMs(startMs);
        }
    }

}
