package org.example.swappayment.TimeService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeService {

    public static String getUTC() {

        Instant now = Instant.now();
        return getUTC(now);
    }

    public static String getUTC(Instant instant) {
        ZonedDateTime utcDateTime = instant.atZone(ZoneId.of("UTC"));
        return formatTime(utcDateTime);
    }

    public static String getLocalTime() {
        Instant now = Instant.now();
        ZonedDateTime localDateTime = now.atZone(ZoneId.systemDefault());
        return formatTime(localDateTime);

    }

    public static String formatTime(ZonedDateTime zonedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS Z");
        return zonedDateTime.format(formatter);
    }
}
