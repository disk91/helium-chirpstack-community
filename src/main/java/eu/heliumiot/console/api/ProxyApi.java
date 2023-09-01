/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2020.
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

import eu.heliumiot.console.api.interfaces.ActionResult;
import eu.heliumiot.console.api.interfaces.ProxyDeactivateDeviceReqItf;
import eu.heliumiot.console.api.interfaces.ProxyGetReqItf;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

@Tag( name = "proxy api", description = "Helium console api proxy" )
@CrossOrigin
@RequestMapping(value = "/console/1.0/proxy")
@RestController
public class ProxyApi {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private HashMap<String,Integer> endpointState = new HashMap<>();

    private void updateState(Integer b, String domain, int v, String user) {
        if (b == null) {
            b = Integer.valueOf(v);
            endpointState.put(domain, b);
        } else {
            if ( v == 0 ) b = 0;
            else b += v;
            if ( b == 5 ) {
                log.warn("Locking domain "+domain+" by user "+user);
            }
            endpointState.replace(domain, b);
        }

    }

    private boolean verifyEndpoint(String endPoint,String user) {

        // @TODO - rate limiter could be implemented
        // validate the standard domains
        if ( endPoint.compareToIgnoreCase("https://console.helium-iot.eu/api") == 0
           || endPoint.compareToIgnoreCase("https://console-vip.helium.com/api/") == 0
           || endPoint.compareToIgnoreCase("https://console.helium.com/api/") == 0
        ) {
            return true;
        }

        // do it for the rest
        if (endPoint.matches("^[A-Za-z0-9_./:-]+$")
                && (endPoint.startsWith("http://") || endPoint.startsWith("https://"))
                && (!endPoint.matches("/v1/.*/v1/"))
        ) {
            // test url with a known endpoint
            String domain = endPoint.substring(0, endPoint.indexOf("/v1/"));
            Integer b = endpointState.get(domain);
            if (b == null || b < 5) {
                try {
                    // verify endpoint
                    HttpHeaders headers = new HttpHeaders();
                    ArrayList<MediaType> accept = new ArrayList<>();
                    accept.add(MediaType.APPLICATION_JSON);
                    headers.setAccept(accept);
                    headers.add(HttpHeaders.USER_AGENT, "helium_chirp/1.0");

                    RestTemplate restTemplate = new RestTemplate();
                    HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
                    ResponseEntity<String> responseEntity =
                            restTemplate.exchange(
                                    domain + "/v1/organization",
                                    HttpMethod.GET,
                                    requestEntity,
                                    String.class
                            );
                    updateState(b,domain,1,user);
                } catch ( HttpClientErrorException x ) {
                    if ( x.getMessage().contains("401 Unauthorized:") && x.getMessage().contains("message") && x.getMessage().contains("invalid_api_key") ) {
                        // assume a valid domain
                        updateState(b,domain,0,user);
                        return true;
                    } else {
                        updateState(b,domain,1,user);
                    }
                } catch ( HttpServerErrorException x ) {
                    // 5xx not impacting
                } catch ( Exception x ) {
                    updateState(b,domain,1,user);
                }
            }
        }
        return false;
    }


    @Operation(summary = "Proxy Getter",
            description = "Perform a Get from a helium console API and retrieve result as a json string",
            responses = {
                    @ApiResponse(responseCode = "200", description= "OK", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description= "BAD REQUEST", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/getter",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> proxyGetter(
            HttpServletRequest request,
            @RequestBody(required = true) ProxyGetReqItf reqItf
    ) {

        ActionResult r = ActionResult.MALFORMED();
        try {
            // check the requested url pattern
            URL u = new URL(reqItf.getEndpoint());
            URI uri = u.toURI();
            log.debug(uri.getHost()); // force to use uri.

            if ( reqItf.getKey() != null && reqItf.getKey().length() > 0 ) {
                // test authorized URL
                if ( ! verifyEndpoint(reqItf.getEndpoint(), request.getUserPrincipal().getName() ) ) {
                    r.setMessage("console_response");
                    return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
                }

                if ( reqItf.getEndpoint().endsWith("/v1/labels")
                        || reqItf.getEndpoint().endsWith("/v1/devices")
                        || reqItf.getEndpoint().endsWith("/v1/functions")
                        || reqItf.getEndpoint().endsWith("/v1/flows")
                        || reqItf.getEndpoint().endsWith("/v1/integrations")
                ) {
                    // Accepted URL
                    HttpHeaders headers = new HttpHeaders();
                    ArrayList<MediaType> accept = new ArrayList<>();
                    accept.add(MediaType.APPLICATION_JSON);
                    headers.setAccept(accept);
                    headers.add(HttpHeaders.USER_AGENT, "helium_chirp/1.0");
                    headers.add("KEY", reqItf.getKey());

                    RestTemplate restTemplate = new RestTemplate();
                    HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
                    ResponseEntity<String> responseEntity =
                            restTemplate.exchange(
                                    reqItf.getEndpoint(),
                                    HttpMethod.GET,
                                    requestEntity,
                                    String.class
                            );
                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
                    } else {
                        r.setMessage("console_response");
                    }
                } else r.setMessage("unsupported_endpoint");
            } else {
                r.setMessage("invalid_key");
            }
        } catch ( MalformedURLException x ) {
            r.setMessage("malformed_endpoint");
        } catch ( URISyntaxException x ) {
            r.setMessage("malformed_endpoint");
        } catch ( HttpClientErrorException x ) {
            r.setMessage("client_error");
        } catch ( HttpServerErrorException x ) {
            r.setMessage("server_error");
        }
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }


    @Operation(summary = "Proxy Deactivater",
            description = "Perform a Put from a helium console API and deactivate a device",
            responses = {
                    @ApiResponse(responseCode = "200", description= "OK", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description= "BAD REQUEST", content = @Content(schema = @Schema(implementation = ActionResult.class))),
            }
    )
    @RequestMapping(value="/deactivate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> proxyDeactivater(
            HttpServletRequest request,
            @RequestBody(required = true) ProxyDeactivateDeviceReqItf reqItf
    ) {

        // check the requested url pattern
        ActionResult r = ActionResult.MALFORMED();
        try {
            URL u = new URL(reqItf.getEndpoint());
            URI uri = u.toURI();
            log.debug(uri.getHost()); // force to use uri.

            if ( reqItf.getKey() != null && reqItf.getKey().length() > 0 ) {
                // test authorized URL
                if ( ! verifyEndpoint(reqItf.getEndpoint(),request.getUserPrincipal().getName()) ) {
                    r.setMessage("console_response");
                    return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
                }
                if ( reqItf.getEndpoint().endsWith("/v1/devices")) {
                    // Accepted URL
                    HttpHeaders headers = new HttpHeaders();
                    ArrayList<MediaType> accept = new ArrayList<>();
                    accept.add(MediaType.APPLICATION_JSON);
                    headers.setAccept(accept);
                    headers.add(HttpHeaders.USER_AGENT, "helium_chirp/1.0");
                    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                    headers.add("KEY", reqItf.getKey());

                    RestTemplate restTemplate = new RestTemplate();
                    String body = (reqItf.isDeactivate())?"{ \"active\":false }":"{ \"active\":true }";
                    HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
                    ResponseEntity<String> responseEntity =
                            restTemplate.exchange(
                                    reqItf.getEndpoint()+'/'+reqItf.getDeviceId(),
                                    HttpMethod.PUT,
                                    requestEntity,
                                    String.class
                            );

                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
                    } else {
                        r.setMessage("console_response");
                    }
                } else r.setMessage("unsupported_endpoint");
            } else {
                r.setMessage("invalid_key");
            }
        } catch ( MalformedURLException x ) {
            r.setMessage("malformed_endpoint");
        } catch ( URISyntaxException x ) {
            r.setMessage("malformed_endpoint");
        } catch ( HttpClientErrorException x ) {
            r.setMessage("client_error");
        } catch ( HttpServerErrorException x ) {
            r.setMessage("server_error");
        }
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }



}

