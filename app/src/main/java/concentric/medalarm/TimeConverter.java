package concentric.medalarm;

/**
 * Created by mike on 6/23/15.
 */
public class TimeConverter {
    private static long minMilli = 60000;   // 60,000 milliseconds in a minute

    public static long minutesToMillis(int minute) {
        return (minute * minMilli);
    }

    public static long hoursToMillis(int hours) {
        return (hours * 60 * minMilli);
    }
}
