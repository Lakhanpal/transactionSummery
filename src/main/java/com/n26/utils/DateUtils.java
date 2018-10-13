package com.n26.utils;

import java.time.Clock;
import java.time.LocalDateTime;

public final class DateUtils {

    /**
     * enforcing the class to be used in static way.
     */

    private DateUtils() {

    }

    /**
     * provides epoc time from localdatetime.
     */
    public static Long getEpocTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(Clock.systemUTC().getZone()).toInstant().toEpochMilli();
    }

}
