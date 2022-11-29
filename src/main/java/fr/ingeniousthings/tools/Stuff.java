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

package fr.ingeniousthings.tools;

public class Stuff {

    public static int hexCharToInt(char c) {
        if ( c >= '0' && c <= '9') return c-'0';
        if ( c >= 'a' && c <= 'f') return 10 + c -'a';
        if ( c >= 'A' && c <= 'F') return 10 + c -'A';
        return 0;
    }

    public static int hexStrToInt(String s) {
        int v = 0;
        for ( int i = 0 ; i < s.length() ; i++ ) {
            v *=16;
            v += hexCharToInt(s.charAt(i));
        }
        return v;
        //return 16*hexCharToInt(s.charAt(0))+hexCharToInt(s.charAt(1));
    }



    public static int[] getDataArray(String s) {
        int sz = s.length()/2;
        int ret[] = new int[sz];

        for ( int i = 0 ; i < s.length() ; i+=2 ) {
            ret[i/2] = hexStrToInt(s.substring(i,i+2));
        }
        return ret;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte getByteFromInt(int v) {
        if ( v > 127 ) {
            v = v - 256;      // complement à deux for converting value > 127
        }
        return (byte)v;
    }

    public static byte[] getBytesFromInt(int [] src) {
        byte [] _r = new byte[src.length];
        for ( int i = 0 ; i < src.length ; i++ ) {
            _r[i] = getByteFromInt(src[i]);
        }
        return _r;
    }

    public static int[] getIntFromBytes(byte [] src) {
        int [] _r = new int[src.length];
        for ( int i = 0 ; i < src.length ; i++ ) {
            _r[i] = (int)src[i] & 0xFF; // get unsigned value
        }
        return _r;
    }

    public static String intToHex(int v) {
        char[] hexChars = new char[2];
        hexChars[0] = hexArray[(v >>> 4) & 0x0F];
        hexChars[1] = hexArray[v & 0x0F];
        return new String(hexChars);
    }

    public static int getIntFromByte(byte src) {
        return (int)src & 0xFF;
    }
}
