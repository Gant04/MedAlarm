package concentric.medalarm;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import concentric.medalarm.models.AlarmStateManager;

/**
 * Created by MatthewAry on 6/12/2015.
 */
public abstract class AlarmService extends Service {
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

    public AlarmService() {
        repeatingDays = new boolean[7];
    }

    public void setRepeatingDay(int dayOfWeek, boolean value) {
        repeatingDays[dayOfWeek] = value;
    }

    public boolean getRepeatingDay(int dayOfWeek) {
        return repeatingDays[dayOfWeek];
    }

}
