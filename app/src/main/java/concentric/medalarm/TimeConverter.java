package concentric.medalarm;

/**
 * Created by mike on 6/23/15.
 * Completed by Thom
 */
public class TimeConverter {
    private static long minMilli = 60000;   // 60,000 milliseconds in a minute

    /**
     * Converts minutes to milliseconds
     *
     * @param minute takes in the minutes
     * @return number of milliseconds
     */
    public static long minutesToMillis(int minute) {
        return (minute * minMilli);
    }

    /**
     * Converts hours to milliseconds (unimplemented)
     *
     * @param hours takes in the hours
     * @return number of milliseconds
     */
    public static long hoursToMillis(int hours) {
        return (hours * 60 * minMilli);
    }
}
