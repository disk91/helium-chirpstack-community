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

package eu.heliumiot.console.chirpstack;


import com.google.protobuf.InvalidProtocolBufferException;
import eu.heliumiot.console.ConsoleConfig;
import fr.ingeniousthings.tools.ITNotFoundException;
import fr.ingeniousthings.tools.ITParseException;
import fr.ingeniousthings.tools.ITRightException;
import io.chirpstack.api.*;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.util.Arrays;

@Service
public class ChirpstackApiAccess {

    // Api endpoints
    protected static final String API_DEVICE_DELETE = "/api.DeviceService/Delete";
    protected static final String API_DEVICE_GET = "/api.DeviceService/Get";
    protected static final String API_DEVICE_CREATE = "/api.DeviceService/Create";
    protected static final String API_DEVICE_CREATEKEY = "/api.DeviceService/CreateKeys";


    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ConsoleConfig consoleConfig;

    // ==================================================================
    //  Wrap useful actions

    public boolean deleteOneDevice(String deviceEui) {
        HttpHeaders heads = new HttpHeaders();
        heads.add("authorization", "Bearer "+consoleConfig.getChirpstackApiAdminKey());
        DeleteDeviceRequest dr = DeleteDeviceRequest.newBuilder()
            .setDevEui(deviceEui)
            .build();
        try {
            byte [] resp = this.execute(
                HttpMethod.POST,
                API_DEVICE_DELETE,
                null,
                heads,
                dr.toByteArray()
            );
            return true;
        } catch ( ITParseException x ) {
            log.error("Impossible to delete chirpstack device - parse");
        } catch ( ITRightException x ) {
            log.error("Impossible to delete chirpstack device - rights");
        } catch ( ITNotFoundException x ) {
            log.error("Impossible to delete chirpstack device - not found");
        }
        return false;
    }

    public Device getOneDevice( String devEui ) {
        HttpHeaders heads = new HttpHeaders();
        heads.add("authorization", "Bearer "+consoleConfig.getChirpstackApiAdminKey());

        GetDeviceRequest gdr = GetDeviceRequest.newBuilder()
            .setDevEui(devEui)
            .build();

        try {
            byte[] resp = this.execute(
                HttpMethod.POST,
                API_DEVICE_GET,
                null,
                heads,
                gdr.toByteArray()
            );
            GetDeviceResponse dev = GetDeviceResponse.parseFrom(resp);
            log.debug("Found device "+dev.getDevice().getDevEui());
            return dev.getDevice();
        } catch ( InvalidProtocolBufferException x ) {
            log.error("Impossible to get chirpstack device config - protobuf");
        } catch ( ITParseException x ) {
            log.error("Impossible to get chirpstack device - parse");
        } catch ( ITRightException x ) {
            log.error("Impossible to get chirpstack device - rights");
        } catch ( ITNotFoundException x ) {
            log.error("Impossible to get chirpstack device - not found");
        }
        return null;
    }


    public boolean createOneOTAADevice(
        String devEui,
        String joinEui,
        String appKey,
        String tenantID,
        String applicationID,
        String devprofileID,
        String description,
        String name,
        boolean isDisabled,
        boolean isSkipFcntCheck
    ) {
        HttpHeaders heads = new HttpHeaders();
        heads.add("authorization", "Bearer "+consoleConfig.getChirpstackApiAdminKey());

        Device d = Device.newBuilder()
            .setDevEui(devEui)
            .setJoinEui(joinEui)
            .setApplicationId(applicationID)
            .setDeviceProfileId(devprofileID)
            .setDescription(description)
            .setName(name)
            .setIsDisabled(isDisabled)
            .setSkipFcntCheck(isSkipFcntCheck)
            .putTags("app_eui",joinEui)
            .build();

        CreateDeviceRequest cdr = CreateDeviceRequest.newBuilder()
            .setDevice(d)
            .build();

        try {
            this.execute(
                HttpMethod.POST,
                API_DEVICE_CREATE,
                null,
                heads,
                cdr.toByteArray()
            );
            log.debug("Creation device "+devEui+" ok");

            if ( appKey != null ) {
                DeviceKeys dk = DeviceKeys.newBuilder()
                    .setDevEui(devEui)
                    .setAppKey(appKey)
                    .build();
                CreateDeviceKeysRequest cdkr = CreateDeviceKeysRequest.newBuilder()
                    .setDeviceKeys(dk)
                    .build();
                this.execute(
                    HttpMethod.POST,
                    API_DEVICE_CREATEKEY,
                    null,
                    heads,
                    cdkr.toByteArray()
                );
                log.debug("Assigned key to device "+devEui+" ok");
            }
            return true;
        } catch ( ITParseException x ) {
            log.error("Impossible to get chirpstack device - parse");
        } catch ( ITRightException x ) {
            log.error("Impossible to get chirpstack device - rights");
        } catch ( ITNotFoundException x ) {
            log.error("Impossible to get chirpstack device - not found");
        }
        return false;
    }


    // ==================================================================
    // Execute request

    public byte[] execute(
            HttpMethod httpMethod,
            String apiPath,
            String queryString,
            HttpHeaders headers,
            byte[] request
    )  throws ITParseException, ITNotFoundException, ITRightException {

        String bodyStr = "";
        if ( request != null ) {
            // The format of the payload is the byte stream from the serialized object, passed with
            // request content with in front of it a 5 byte array containing the size of the message
            // plus a 0 type on the first byte
            byte[] header = new byte[5];
            header[0] = 0;
            header[1] = (byte) ((request.length >> 24) & 0xFF);
            header[2] = (byte) ((request.length >> 16) & 0xFF);
            header[3] = (byte) ((request.length >> 8) & 0xFF);
            header[4] = (byte) ((request.length) & 0xFF);

            byte [] body = Arrays.copyOf(header, header.length + request.length);
            System.arraycopy(request, 0, body, 5, request.length);
            bodyStr = Base64.toBase64String(body);
        }

        String url = consoleConfig.getChirpstackApiBase() + apiPath;

        try {
            RestTemplate restTemplate = new RestTemplate();

            if ( headers == null ) {
                headers = new HttpHeaders();
            }
            headers.add(HttpHeaders.ACCEPT, "application/grpc-web-text");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/grpc-web-text");
            headers.add("X-User-Agent","grpc-web-javascript/0.1");
            headers.add("X-Grpc-Web","grpc-web-javascript/0.1");


            log.debug("Connect to "+url);
            log.debug("With body "+bodyStr);


            HttpEntity<String> he;
            if (    httpMethod == HttpMethod.GET
                 || httpMethod == HttpMethod.DELETE
            ) {
                he = new HttpEntity<String>(headers);
            } else {
                he = new HttpEntity<String>(bodyStr, headers);
            }

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    httpMethod,
                    he,
                    String.class);

            if (       response.getStatusCode() == HttpStatus.OK
                    || response.getStatusCode() != HttpStatus.NO_CONTENT
                    || response.getStatusCode() != HttpStatus.CREATED
            ) {
                String respStr = response.getBody();
                if ( respStr == null ) {
                    // case of error ... the backend loves to send 200 when you have an
                    // invalid password as an example !
                    // message in header "gprc-message"
                    String v = response.getHeaders().getFirst("grpc-message");
                    if ( v != null ) log.debug("Problem : "+v);
                    throw new ITNotFoundException(v);
                }
                log.debug("Response from the API :"+respStr);

                // The response comes with 2 Base64 string concatenated, so the decoder don't like this
                // the second one is a grpc status, we can remove it from the response
                if ( respStr.indexOf("==") > 0 && respStr.indexOf("==") < respStr.length() - 2 ) {
                    // multiple responses
                    respStr = respStr.substring(0,respStr.indexOf("==")+2);
                } else if ( respStr.indexOf("=") > 0 && respStr.indexOf("=") < respStr.length() - 1 ) {
                    respStr = respStr.substring(0,respStr.indexOf("=")+1);
                } else if ( respStr.substring(5).indexOf("AAAAA") > 0 ) {
                    // when we have no "="   between the Base64 strings
                    // keep the first of the A
                    respStr = respStr.substring(0,respStr.substring(5).indexOf("AAAAA")+4);
                }
                log.debug("Response after processing :"+respStr);
                byte[] fullResp = Base64.decode(respStr);
                // the response also contains a header with the size of the response
                // we can remove it assuming we have the right quantity fo data into the response
                return Arrays.copyOfRange(fullResp,5,fullResp.length);

            } else {
                log.error("Response code from Chirpstack is" + response.getStatusCode());
            }
        } catch ( HttpClientErrorException x ) {
            log.error("Error accessing the chirstack api");
            if ( x.getStatusCode() == HttpStatus.BAD_REQUEST ) {
                log.error("Invalid body content "+x.getMessage());
            } else {
                x.printStackTrace();
            }
        } catch ( HttpServerErrorException x ) {
            x.printStackTrace();
        } catch ( ResourceAccessException x ) {
            log.error("Impossible to connect to Chirpstack");
        }
        throw new ITParseException("Parse, see above");
    }



}
