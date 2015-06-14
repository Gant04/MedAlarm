package concentric.medalarm;

import android.net.Uri;

/**
 * Created by Thom on 6/10/2015.
 */
public class AlarmAttributes {
    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRDAY = 5;
    public static final int SATURDAY = 6;

    public long id;
    public int timeHour;
    public int timeMinute;
    private boolean repeatingDays[];
    private boolean repeatWeekly;
    public Uri alarmTone;
    public String name;
    public boolean isEnabled;

    public AlarmAttributes() {
        repeatingDays = new boolean[7];
    }

    public void setRepeatingDay(int dayOfWeek, boolean value) {
        repeatingDays[dayOfWeek] = value;
    }

    public boolean getRepeatingDay(int dayOfWeek) {
        return repeatingDays[dayOfWeek];
    }

}
