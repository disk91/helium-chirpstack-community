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

import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class Now {

    /**
     * Return current nanotime, expiration about 292y apparently
     * @return
     */
    public static long NanoTime() {
        return System.nanoTime();
    }

    public static long NowUtcMs() {
        return LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static long NowUtcS() {
        return LocalDateTime.now(ZoneId.of("UTC")).toEpochSecond(ZoneOffset.UTC);
    }

    public static long NowSystem() { return System.currentTimeMillis(); }

    public static long TodayMidnightUtc() {
        LocalTime midnight  = LocalTime.MIDNIGHT;
        LocalDate today     = LocalDate.now(ZoneId.of("UTC"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        return todayMidnight.toEpochSecond(ZoneOffset.UTC)*1000;
    }

    public static long ThisDayMidnightUtc(long timeMs) {
        LocalTime midnight  = LocalTime.MIDNIGHT;
        LocalDate day     = Instant.ofEpochMilli(timeMs).atZone(ZoneId.of("UTC")).toLocalDate();
        LocalDateTime dayMidnight = LocalDateTime.of(day, midnight);
        return dayMidnight.toEpochSecond(ZoneOffset.UTC)*1000;
    }

    public static long InOneYearUtcMs() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        Date nextYear = cal.getTime();
        LocalDateTime _nextYear = LocalDateTime.ofInstant(nextYear.toInstant(),ZoneId.of("UTC"));
        return _nextYear.toEpochSecond(ZoneOffset.UTC)*1000;
    }

    public static long FirstDayThisMonthMidnightUtc() {
        LocalTime midnight  = LocalTime.MIDNIGHT;
        LocalDate today     = LocalDate.now(ZoneId.of("UTC"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        todayMidnight = todayMidnight.withDayOfMonth(1);
        return todayMidnight.toEpochSecond(ZoneOffset.UTC)*1000;
    }

    public static long FirstDayMonthBeforeUtc(int months) {
        LocalTime midnight  = LocalTime.MIDNIGHT;
        LocalDate today     = LocalDate.now(ZoneId.of("UTC"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        todayMidnight = todayMidnight.withDayOfMonth(1);
        todayMidnight = todayMidnight.minusMonths(months);
        return todayMidnight.toEpochSecond(ZoneOffset.UTC)*1000;
    }

    public static long FirstDayMonthAfterUtc(int months) {
        LocalTime midnight  = LocalTime.MIDNIGHT;
        LocalDate today     = LocalDate.now(ZoneId.of("UTC"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        todayMidnight = todayMidnight.withDayOfMonth(1);
        todayMidnight = todayMidnight.plusMonths(months);
        return todayMidnight.toEpochSecond(ZoneOffset.UTC)*1000;
    }

    /**
     * 20 year in the future
     * @return
     */
    public static long reallyFarFromNow() {
        long v = 20*365*24;
        v *= 3_600_000L;
        return NowUtcMs()+v;
    }

    /**
     * 10 years in the future
     * @return
     */
    public static long farFromNow() {
        long v = 10*365*24;
        v *= 3_600_000L;
        return NowUtcMs()+v;
    }

    public static long ONE_FULL_DAY = 24*3600*1000;
    public static long ONE_HOUR = 1*3600*1000;

}
