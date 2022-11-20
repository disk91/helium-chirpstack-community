package fr.ingeniousthings.tools;

import java.util.Calendar;

public class DateConverters {

    // from 2022-11-14T20:05:16+00:00 to timestamp
    public static long StringDateToMs(String d) {
        Calendar calendar = javax.xml.bind.DatatypeConverter.parseDateTime(d);
        return calendar.getTimeInMillis();
    }
}
