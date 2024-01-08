package fr.ingeniousthings.tools;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DateConverters {

    // from 2022-11-14T20:05:16+00:00 to timestamp
    public static long StringDateToMs(String d) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        ZonedDateTime dateTime = ZonedDateTime.parse(d, formatter);
        return dateTime.toInstant().toEpochMilli();
    }
}
