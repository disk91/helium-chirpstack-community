package eu.heliumiot.console.api;


import eu.heliumiot.console.api.interfaces.ActionResult;
import eu.heliumiot.console.api.interfaces.LoginReqItf;
import eu.heliumiot.console.api.interfaces.LoginRespItf;
import eu.heliumiot.console.service.UserService;
import fr.ingeniousthings.tools.ITNotFoundException;
import fr.ingeniousthings.tools.ITParseException;
import fr.ingeniousthings.tools.ITRightException;
import io.swagger.v3.oas.annotations.Operation;
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
        log.debug("Sign in for "+login.getUsername());
        try {
            LoginRespItf r = userService.verifyUserLogin(login);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch (ITNotFoundException x) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } catch (ITRightException x) {
            return new ResponseEntity<>(ActionResult.FORBIDDEN(), HttpStatus.FORBIDDEN);
        } catch (ITParseException x) {
            return new ResponseEntity<>(ActionResult.BADREQUEST(), HttpStatus.BAD_REQUEST);
        }
    }

}
