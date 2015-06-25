package concentric.medalarm.models;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.StringTokenizer;

/**
 * Created by mike on 6/13/15.
 */
public class Alarm {
    // DB names
    public static final String TABLE_NAME = "alarm";
    public static final String COLUMN_NAME_ALARM_GROUP = "group";
    public static final String COLUMN_NAME_ALARM_ID = "id";
    public static final String COLUMN_NAME_ALARM_TIME_HOUR = "hour";
    public static final String COLUMN_NAME_ALARM_TIME_MINUTE = "minute";
    public static final String COLUMN_NAME_ALARM_REPEATS_HOURS = "repeats_hours";
    public static final String COLUMN_NAME_ALARM_REPEATS_MINUTES = "repeats_minutes";

    private long id;
    private long groupID;
    private int hour;
    private int minute;
    private boolean repeatWeekly;

    private String alarmTone;
    private String alarmMessage;
    private SQLiteDatabase database;
    private DBHelper dbHelper;


    /**
     * Non-Default Alarm Constructor
     * This constructor allows an alarm to be created.
     *
     * @param hour   Set the hour
     * @param minute Set the minute
     */
    public Alarm(long id, long groupID, int hour, int minute,
                 boolean repeatWeekly) {
        setId(id);
        setGroupID(groupID);
        setHour(hour);
        setMinute(minute);
        setRepeatWeekly(repeatWeekly);
    }

    public Alarm() {
        // Do nothing.
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }


    public boolean isRepeatWeekly() {
        return this.repeatWeekly;
    }

    public String getAlarmTime() {
        return Integer.toString(this.getHour()) + ":" + Integer.toString(getMinute());
    }

    public String getAlarmTone() {
        return this.alarmTone;
    }

    public long getGroupID() {
        return this.groupID;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMinute(int minute) {
        if (minute > 59 || minute < 0) {
            Log.e(getClass().getName() + " setMinute",
                  "An attempt to set the minutes to an invalid" +
                  " value was made.\nMinutes should be between 0 and 59.\n\n" +
                  "This function was instructed to set the minutes to: " + minute + ".");
        } else {
            this.minute = minute;
        }
    }

    public void setHour(int hour) {
        if (hour > 23 || hour < 0) {
            Log.e(getClass().getName() + " setHour", "An attempt to set the hour to an invalid" +
                                                     " value was made.\nHour should be between 0 and 23.\n\n" +
                                                     "This function was instructed to set the hour to: " +
                                                     hour + ".");
        } else {
            this.hour = hour;
        }
    }


    public void setRepeatWeekly(boolean repeatWeekly) {
        this.repeatWeekly = repeatWeekly;
    }

    public void setAlarmTime(String alarmTime) {
        StringTokenizer stringTokenizer = new StringTokenizer(alarmTime, ":");
        this.setHour(Integer.parseInt(stringTokenizer.nextToken()));
        this.setMinute(Integer.parseInt(stringTokenizer.nextToken()));
    }

    public void setAlarmTone(String alarmTone) {
        this.alarmTone = alarmTone;
    }

    public void setAlarmMessage(String alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    public void setGroupID(long id) {
        this.groupID = id;
    }

    public void disableAlarm() {
        // Disable an alarm from the system scheduler.
    }

}
