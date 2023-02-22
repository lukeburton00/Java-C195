package util;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Time is a utility for converting LocalDateTime objects between timezones.
 */
public abstract class Time {

    /**
     * systemToUTC converts from system time to UTC
     * @param systemTime the time in System time to convert
     * @return the time in UTC
     */
    public static LocalDateTime systemToUTC(LocalDateTime systemTime)
    {
        return systemTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

    /**
     * systemToUTC converts from system time to UTC
     * @param utcTime the time in UTC to convert
     * @return the time in System time
     */
    public static LocalDateTime UTCToSystem(LocalDateTime utcTime)
    {
        return utcTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * UTCtoEST converts UTC to EST
     * @param utcTime the time in UTC to convert
     * @return the time in EST
     */
    public static LocalDateTime UTCtoEST(LocalDateTime utcTime)
    {
        return utcTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
    }

}
