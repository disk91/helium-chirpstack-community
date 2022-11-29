package fr.ingeniousthings.tools;

public class Tools {

    public static String EuiStringFromLong(long v) {
        String out = "";
        for ( int i =0; i < 8 ; i ++) {
            long k = v & 255;
            out = Stuff.intToHex((int)k)+out;
            v = v / 256;
        }
        return out;
    }

    public static long EuiStringToLong(String eui) {
        long out = 0;
        for ( int i = 0 ; i < eui.length() ; i+=2 ) {
            long k = Stuff.hexStrToInt(eui.substring(i,i+2));
            out *= 256;
            out = out + k;
        }
        return out;
    }

}
