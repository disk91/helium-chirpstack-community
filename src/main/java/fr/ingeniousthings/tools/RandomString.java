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

import java.util.Random;

public class RandomString {

    public static String getRandomString(int size) {
        Random r = new Random();
        String s = "";
        int a = "a".getBytes()[0];
        int zero = "0".getBytes()[0];

        for ( int i = 0 ; i < size ; i++ ) {

            int v = r.nextInt() % 36;
            if ( v < 0 ) v = -v;
            if (v < 26) {
                s += (char)(a+v);
            } else {
                s += (char)(zero+(v-26));
            }

        }
        return s;
    }

    public static String getRandomAZString(int size) {
        Random r = new Random();
        String s = "";
        int A = "A".getBytes()[0];

        for ( int i = 0 ; i < size ; i++ ) {

            int v = r.nextInt() % 26;
            if ( v < 0 ) v = -v;
            s += (char)(A+v);

        }
        return s;
    }

    public static String getRandomHexString(int size) {
        Random r = new Random();
        String all = "0123456789ABCDEF";
        String s = "";

        for ( int i = 0 ; i < size ; i++ ) {

            int v = r.nextInt() % 16;
            if ( v < 0 ) v = -v;
            s += all.charAt(v);

        }
        return s;
    }


    public static String getUUIDString() {
        long t = Now.NowUtcMs();
        String s = getRandomString(4);
        s+='-'+t+'-';
        s+= getRandomString(8);
        return s;
    }

}
