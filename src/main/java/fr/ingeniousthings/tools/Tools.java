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

package fr.ingeniousthings.tools;

public class Tools {


    public static boolean isAcceptedEmailSyntax(String email, String filters) {
        // filters is a list of regEx separated by , cooresponding to rejected patterns
        String [] _filters = filters.split(",");
        for (String filter : _filters) {
            if (email.matches(filter)) return false;
        }
        return true;
    }

    public static boolean isValidEmailSyntax(String email) {
        return email.matches("^(?=.{1,64}@)[A-Za-z0-9_+-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }

    public static String EuiStringFromLong(long v) {
        String out = "";
        for ( int i = 0; i < 8 ; i++) {
            long k = v & 0xFF;
            out = Stuff.intToHex((int)k)+out;
            v >>= 8;
        }
        return out;
    }

    public static long EuiStringToLong(String eui) {
        long out = 0;
        if ( eui == null )  return 0;
        for ( int i = 0 ; i < eui.length() ; i+=2 ) {
            long k = Stuff.hexStrToInt(eui.substring(i,i+2));
            out <<= 8;
            out = out + k;
        }
        return out;
    }

    public static byte getByteFromInt(int v) {
        if ( v > 127 ) {
            v = v - 256;      // complement à deux for converting value > 127
        }
        return (byte)v;
    }

    public static byte[] EuiStringToByteArray(String eui) {
        byte out[] = new byte[8];
        for ( int i = 0 ; i < 8 ; i++ ) out[i] = 0;
        if ( eui.length() != 16 ) return out;
        for ( int i = 0 ; i < eui.length() ; i+=2 ) {
            int k = Stuff.hexStrToInt(eui.substring(i,i+2));
            out[i/2] = getByteFromInt(k);
        }
        return out;
    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch ( InterruptedException x ) {};
    }

}
