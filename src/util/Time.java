package util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public abstract class Time {
    public static LocalDateTime systemToUTC(LocalDateTime systemTime)
    {
        return systemTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

    public static LocalDateTime UTCToSystem(LocalDateTime utcTime)
    {
        return utcTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime systemToEST(LocalDateTime systemTime)
    {
        return systemTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
    }

    public static LocalDateTime ESTToSystem(LocalDateTime estTime)
    {
        return estTime.atZone(ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime UTCtoEST(LocalDateTime utcTime)
    {
        return utcTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
    }

    public static LocalDateTime ESTtoUTC(LocalDateTime estTime)
    {
        return estTime.atZone(ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

}
