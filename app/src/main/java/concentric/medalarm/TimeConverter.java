package concentric.medalarm;

/**
 * Created by mike on 6/23/15.
 * Completed by Thom
 */
public class TimeConverter {
    private static long minMilli = 60000;   // 60,000 milliseconds in a minute

    /**
     * @param minute
     * @return
     */
    public static long minutesToMillis(int minute) {
        return (minute * minMilli);
    }

    /**
     * @param hours
     * @return
     */
    public static long hoursToMillis(int hours) {
        return (hours * 60 * minMilli);
    }
}
