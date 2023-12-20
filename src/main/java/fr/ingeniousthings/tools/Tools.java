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
