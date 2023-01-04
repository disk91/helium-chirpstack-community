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

package eu.heliumiot.console.tools;

import eu.heliumiot.console.ConsoleConfig;
import fr.ingeniousthings.tools.Stuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class EncryptionHelper {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ConsoleConfig consoleConfig;

    private String __iv = "4fee88822bce7d331d6db0d69d978492";

    /**
     * Encryt the given string with the configuration key store in the configuration file
     * @param tobeEncrypted
     * @return
     */
    public String encryptStringWithServerKey(String tobeEncrypted) {
        return encrypt(tobeEncrypted, __iv, consoleConfig.getJwtSignatureKey());
    }


    public String decryptStringWithServerKey(String tobeDecrypted) {
        return decrypt(tobeDecrypted, __iv, consoleConfig.getJwtSignatureKey());
    }


    /**
     * Encrypt a given String with IV and Key
     * The String have " " padding to be 16Byte modulo long.
     * The space will be stripped on decrytion. do not send string with heading or leading space you will
     * lose them.
     * The encrypted String is base64 encoded
     * @param tobeEncrypted
     * @param iv
     * @param encKey
     * @return
     */
    public String encrypt(String tobeEncrypted, String iv, String encKey ) {

        byte [] byteToEncrytp;
        try {
            byte [] byteToEncrytpLen = tobeEncrypted.getBytes("UTF-8");
            int pad = (16 - (byteToEncrytpLen.length & 0x0F) );
            if ( pad == 16 ) pad = 0;
            pad += byteToEncrytpLen.length;
            byteToEncrytp = new byte[pad];
            for ( int i = 0 ; i < byteToEncrytpLen.length ; i++ ) {
                byteToEncrytp[i] = byteToEncrytpLen[i];
            }
            for ( int i = byteToEncrytpLen.length ; i < pad ; i ++ ) {
                byteToEncrytp[i] = 0;
            }
        } catch ( UnsupportedEncodingException e ) {
            log.error(e.getLocalizedMessage());
            return null;
        }

        byte[] _iv = Stuff.getBytesFromInt(Stuff.getDataArray(iv));

        try {
            IvParameterSpec iv_ = new IvParameterSpec(_iv);
            SecretKeySpec skeySpec = new SecretKeySpec(
                    Stuff.getBytesFromInt(
                            Stuff.getDataArray(
                                    encKey
                            )), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv_);
            String result = Base64.getEncoder().encodeToString(cipher.doFinal(byteToEncrytp));
            return result;
        } catch (NoSuchPaddingException e) {
            log.error("Error initializing the AES encryption library 1");
        } catch (NoSuchAlgorithmException e) {
            log.error("Error initializing the AES encryption library 2");
        } catch (InvalidKeyException e) {
            log.error("Error initializing the AES encryption library 3");
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            log.error("Error initializing the AES encryption library 4");
            log.error(e.getLocalizedMessage());
        } catch (IllegalBlockSizeException e) {
            log.error(e.getLocalizedMessage());
        } catch (BadPaddingException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;

    }

    /**
     * Decrypt the given Base64 encoded string to the initial String.
     * Remove heading and leading spaces.
     * @param tobeDecrypted
     * @param iv
     * @param encKey
     * @return
     */
    public String decrypt(String tobeDecrypted, String iv, String encKey) {

        byte[] _iv = Stuff.getBytesFromInt(Stuff.getDataArray(iv));
        try {
            IvParameterSpec iv_ = new IvParameterSpec(_iv);
            SecretKeySpec skeySpec = new SecretKeySpec(
                    Stuff.getBytesFromInt(
                            Stuff.getDataArray(
                                    encKey
                            )), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv_);

            byte [] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(tobeDecrypted));
            int pad = 0;
            for ( int i = decryptedBytes.length ; i > 0 ; i-- ) {
                if ( decryptedBytes[i-1] == 0 ) pad++;
                else break;
            }

            byte [] unpadded = new byte[decryptedBytes.length-pad];
            for ( int i = 0 ; i < decryptedBytes.length-pad ; i++ ) {
                unpadded[i] = decryptedBytes[i];
            }

            // trim should be removed with the new method but I keep it for non regression
            // with the string already stored in database
            return new String (unpadded, StandardCharsets.UTF_8).trim();
        } catch (NoSuchPaddingException e) {
            log.error("Error initializing the AES decryption library 1");
        } catch (NoSuchAlgorithmException e) {
            log.error("Error initializing the AES decryption library 2");
        } catch (InvalidKeyException e) {
            log.error("Error initializing the AES decryption library 3");
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            log.error("Error initializing the AES decryption library 4");
        } catch (IllegalBlockSizeException e) {
            log.error(e.getLocalizedMessage());
        } catch (BadPaddingException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }


    /**
     * hash a string into a sha256 string equivalent
     * use in the backend to protect the end user email against propagation
     * @param tobeHashed
     * @return
     */
    public static String hashSha256(String tobeHashed ) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(tobeHashed.getBytes(StandardCharsets.UTF_8));
            return Stuff.bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException x) {
            System.err.println("Error - no such algorithm sha256");
        }
        return "unknown-sha256";
    }

}
