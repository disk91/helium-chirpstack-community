/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2018.
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

public class HexaConverters {

    public static boolean isHexString(String s) {
        return s.toUpperCase().matches("^[A-F0-9]+$");
    }

    public static String byteToHexString(byte b[]) {
        String r = "";
        for ( int j = 0; j < b.length; j++ ) {
            r+=byteToHexString(b[j]);
        }
        return r;
    }

    /**
     * Get the hex string after extracting sz bytes from
     * @param b
     * @param from
     * @param sz
     * @return
     */
    public static String byteToHexString(byte b[],int from, int sz) {
        String r = "";
        for ( int j = from; j < b.length && j < from+sz ; j++ ) {
            r+=byteToHexString(b[j]);
        }
        return r;
    }


    public static String byteToHexStringWithSpace(byte b[]) {
        String r = "";
        for ( int j = 0; j < b.length; j++ ) {
            r+=byteToHexString(b[j]);
            if ( j < b.length - 1 ) r+=" ";
        }
        return r;
    }

    public static String byteToDecStringWithSpace(byte b[]) {
        String r = "";
        for ( int j = 0; j < b.length; j++ ) {
            if ( b[j] >= 0 ) {
                r += "" + b[j];
            } else {
                r  += "" + (256+((int)b[j]));
            }
            if ( j < b.length - 1 ) r+=" ";
        }
        return r;
    }


    public static String byteToHexString(byte b) {
        char[] hexChars = new char[2];
        int v = b & 0xFF;
        String s= "";
        if ( v < 16 ) s+="0";
        s+=Integer.toHexString(v).toUpperCase();
        return s;
    }

}
