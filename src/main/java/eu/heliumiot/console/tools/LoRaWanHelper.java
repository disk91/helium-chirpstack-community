package eu.heliumiot.console.tools;

import fr.ingeniousthings.tools.HexaConverters;
import fr.ingeniousthings.tools.Stuff;
import jakarta.annotation.PostConstruct;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.macs.CMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoRaWanHelper {

    /** Test function for MIC computation
     * test frame source : https://github.com/sntcz/LoRa.Message/blob/master/LoRa.Message.UnitTest/CalculatedMICTest.cs
    @ PostConstruct
    protected void init() {
        log.info("LoRaWanHelper initialized");

        String hexPayload = "40F17DBE4900020001954378762B11FF0D";
        byte[] payload = HexaConverters.hexStringToByteArray(hexPayload);
        String hexNwkSkey = "44024241ED4CE9A68C6A8BC055233FD3";
        byte[] nwkSKey = HexaConverters.hexStringToByteArray(hexNwkSkey);
        byte [] r = computeMic(payload, nwkSKey, 0);
        int i = 0;
        while ( i < 4 ) {
            if ( r[i] != payload[i+payload.length-4]) {
                log.error("MIC KO");
                break;
            }
            i++;
        }
        if ( i == 4 ) log.info("MIC OK");

        hexPayload = "40F17DBE49000300012A3518AF";
        payload = HexaConverters.hexStringToByteArray(hexPayload);
        hexNwkSkey = "44024241ED4CE9A68C6A8BC055233FD3";
        nwkSKey = HexaConverters.hexStringToByteArray(hexNwkSkey);
        r = computeMic(payload, nwkSKey, 0);
        i = 0;
        while ( i < 4 ) {
            if ( r[i] != payload[i+payload.length-4]) {
                log.error("MIC KO");
                break;
            }
            i++;
        }
        if ( i == 4 ) log.info("MIC OK");

        hexPayload = "40F17DBE4900020001954378762B11FF0D";
        payload = HexaConverters.hexStringToByteArray(hexPayload);
        hexNwkSkey = "44024241ED4CE9A68C6A8BC055233FD3";
        nwkSKey = HexaConverters.hexStringToByteArray(hexNwkSkey);
        r = computeMic(payload, nwkSKey, 0);
        i = 0;
        while ( i < 4 ) {
            if ( r[i] != payload[i+payload.length-4]) {
                log.error("MIC KO");
                break;
            }
            i++;
        }
        if ( i == 4 ) log.info("MIC OK");

        hexPayload = "40531E012680664601457090ED25";
        payload = HexaConverters.hexStringToByteArray(hexPayload);
        hexNwkSkey = "7A47F143D7CEF033DFA0D4B75E04A316";
        nwkSKey = HexaConverters.hexStringToByteArray(hexNwkSkey);
        r = computeMic(payload, nwkSKey, (7 << 16));
        i = 0;
        while ( i < 4 ) {
            if ( r[i] != payload[i+payload.length-4]) {
                log.error("MIC KO");
                break;
            }
            i++;
        }
        if ( i == 4 ) log.info("MIC OK");

        // try 10.000 for time measurement
        // ref processing time on my computer : 17ms
        long start = System.currentTimeMillis();
        for (int j = 0; j < 10000; j++) {
        r = computeMic(payload, nwkSKey, (7 << 16));
        }
        long end = System.currentTimeMillis();
        log.info("10k MIC computation time : {}ms", end - start);
    }
    */

    // JOIN
    // 00 9A2E3DD7EFF98160 9861BFC396F98160 75AB   D683 EED2
    //       app eui (rev)   dev eui (rev)  nonce  MIC  CRC

    // UPLINK
    // 40 XX XX XX XX YY ZZ ZZ K..K FF P..P MM MM MM MM
    // MHDR 1B 0x40 here => type = 2 (up unconf) 4 = (up conf) 6 = RFU 7 = Prop 0 = join Req
    // where XX are DevAddr reversed byte order
    // YY FCtrl (last 4b = FOpts Sz)
    // ZZ FCnt
    // K..K FOpts 0..120b 0..15B
    // FF FPort 1B (only if Payload > 1B) else it's not here
    // P..P Payload
    // MM MIC : 4B

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public boolean isUplink(byte[] payload) {
        return ((payload[0] & 0xE0) == 0x40 || (payload[0] & 0xE0) == 0x80 );
    }

    public boolean isUplinkConfirmed(byte[] payload) {
        return ( (payload[0] & 0xE0) == 0x80 );
    }

    public boolean isJoin(byte[] payload) {
        return ((payload[0] & 0xE0) == 0x00 && payload.length == 23);
    }

    // ------------------------------------------------------------
    // uplink specific

    public String getDevAddr(byte[] payload) {
        return  HexaConverters.byteToHexString(payload[4]) +
                HexaConverters.byteToHexString(payload[3]) +
                HexaConverters.byteToHexString(payload[2]) +
                HexaConverters.byteToHexString(payload[1]);
    }

    public int getFcnt(byte[] payload) {
        return Stuff.getIntFromByte(payload[7]) * 256 + Stuff.getIntFromByte(payload[6]);
    }

    /**
     * Compute MIC signature based on payload and nwkSKey, the fCntExtends is used to extends
     * the fCnt to 32 bits if required, this value will be added to the payload fCnt.
     * This function has been made to compute MIC for a received uplink message ; this message
     * contains the MIC in the last 4 bytes of the payload.
     * This will not work for LoRaWan 1.1 as MIC is computed with 2 different keys and have a different size.
     * But currently, helium HPR doesn't support 1.1
     */
    public byte[] computeMic(byte[] payload, byte[] nwkSKey, int fCntExtends) {
        int fCnt = this.getFcnt(payload)+fCntExtends;

        // --------------------------
        // 1) Build bloc B0
        // --------------------------
        // LoRaWan specification
        //   B0[0]  = 0x49
        //   B0[1]  = 0x00
        //   B0[2]  = 0x00
        //   B0[3]  = 0x00
        //   B0[4]  = 0x00
        //   B0[5]  = direction (0 = uplink, 1 = downlink)
        //   B0[6..9]  = DevAddr as little-endian
        //   B0[10..13] = FCnt (4 bytes LSB)  little-endian
        //   B0[14] = 0x00
        //   B0[15] = Payload size (in bytes) w/o MIC part

        byte[] b0 = new byte[16];
        b0[0]  = 0x49;
        b0[1]  = 0x00;
        b0[2]  = 0x00;
        b0[3]  = 0x00;
        b0[4]  = 0x00;
        b0[5]  = 0x00;              // Direction 0 for uplink / 1 for downlink
        b0[6]  = payload[1];        // DevAddr
        b0[7]  = payload[2];
        b0[8]  = payload[3];
        b0[9]  = payload[4];
        // fCnt is 32 bits but only 16 in frame (little-endian)
        b0[10] = (byte) (fCnt & 0xFF);
        b0[11] = (byte) ((fCnt >> 8) & 0xFF);
        b0[12] = (byte) ((fCnt >> 16) & 0xFF);
        b0[13] = (byte) ((fCnt >> 24) & 0xFF);
        b0[14] = 0x00;
        b0[15] = (byte) ((payload.length -4) & 0xFF);  // Payload size (w/o MIC)

        // -------------------------------
        // 2) AES-CMAC on  (B0 | payload) ( minus the 4 last bytes = MIC)
        // -------------------------------
        byte[] message = new byte[b0.length + payload.length-4];
        System.arraycopy(b0, 0, message, 0, b0.length);
        System.arraycopy(payload, 0, message, b0.length, payload.length-4);

        CMac cmac = new CMac(AESEngine.newInstance());
        cmac.init(new KeyParameter(nwkSKey));
        cmac.update(message, 0, message.length);
        byte[] fullMac = new byte[16];
        cmac.doFinal(fullMac, 0);

        byte[] mic = new byte[4];
        System.arraycopy(fullMac, 0, mic, 0, 4);

        return mic;
    }

    /**
     *  Verify a given MIC with the payload and the nwkSKey, see computeMic for more details.
     *  Return true when the MIC match the payload MIC ; validating the nwkSKey.
     */
    public boolean checkMic(byte[] payload, byte[] nwkSKey, int fCntExtends) {
        byte[] mic = computeMic(payload, nwkSKey, fCntExtends);
        for (int i = 0; i < 4; i++) {
            if (mic[i] != payload[payload.length-4+i]) {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------------------------
    // join specific

    public byte[] getDeviceEui(byte[] payload) {
        byte[] devEui = new byte[8];
        for (int i = 0; i < 8; i++) {
            devEui[i] = payload[(9 + 8 - 1) - i];
        }
        return devEui;
    }

    public String getDeviceEuiString(byte[] payload) {
        return HexaConverters.byteToHexString(getDeviceEui(payload));
    }

}
