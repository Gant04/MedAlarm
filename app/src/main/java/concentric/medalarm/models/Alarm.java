package concentric.medalarm.models;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.StringTokenizer;

/*
* Alarm Class
* This class models the information required to create an alarm.
* An alarm requires that they are made through the use of the createAlarm function in the
* AlarmDataSource class. Alarms should not be created directly through this class.
*
* @author Matthew Ary
*
* */
public class Alarm {
    // DB names
    public static final String TABLE_NAME = "alarm";
    public static final String COLUMN_NAME_ALARM_GROUP = "group_id";
    public static final String COLUMN_NAME_ALARM_ID = "id";
    public static final String COLUMN_NAME_ALARM_TIME_HOUR = "hour";
    public static final String COLUMN_NAME_ALARM_TIME_MINUTE = "minute";
    public static final String COLUMN_NAME_ALARM_REPEATS_HOURS = "repeats_hours";
    public static final String COLUMN_NAME_ALARM_REPEATS_MINUTES = "repeats_minutes";

    private long id;
    private long groupID;
    private int hour;
    private int minute;
    private int rHour;
    private int rMinute;

    private String alarmTone;
    private String alarmMessage;
    private SQLiteDatabase database;
    private DBHelper dbHelper;


    /**
     * Non-Default Alarm Constructor
     * This constructor allows an alarm to be created.
     * Only the AlarmDataSource should use this constructor.
     *
     * @param id        Set the record id (this is for the database)
     * @param groupID   Set the alarm's group id. (this is for the database)
     * @param hour      Set the hour
     * @param minute    Set the minute
     * @param repeats   Boolean. Does this alarm repeat? Set true if it does.
     * @param rHour     Set the repeat interval in hours
     * @param rMinute   Set the repeat interval in minutes TODO: Investigate if this is necessary.
     */
    public Alarm(long id, long groupID, int hour, int minute, boolean repeats, int rHour,
                 int rMinute) {
        setId(id);
        setGroupID(groupID);
        setHour(hour);
        setMinute(minute);
    }

    /*
    * TODO: Remove this and the tests connected with it.
    * */
    public Alarm(long id, long groupID, int hour, int minute) {
        setId(id);
        setGroupID(groupID);
        setHour(hour);
        setMinute(minute);
    }

    public Alarm() {
        // Do nothing.
    }

    public int getHour() {
        return this.hour;
    }

    /**
     * Sets the hour and ensures its valid.
     *
     * @param hour
     */
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

    public int getMinute() {
        return this.minute;
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

    public String getAlarmTime() {
        return Integer.toString(this.getHour()) + ":" + Integer.toString(getMinute());
    }

    /**
     * Parses a string and translates that string into a valid time.
     * @param alarmTime
     */
    public void setAlarmTime(String alarmTime) {
        StringTokenizer stringTokenizer = new StringTokenizer(alarmTime, ":");
        this.setHour(Integer.parseInt(stringTokenizer.nextToken()));
        this.setMinute(Integer.parseInt(stringTokenizer.nextToken()));
    }

    public String getAlarmTone() {
        return this.alarmTone;
    }

    /**
     * Sets the file name of the ringtone that will be played upon alarm firing.
     * @param alarmTone
     */
    public void setAlarmTone(String alarmTone) {
        this.alarmTone = alarmTone;
    }

    public long getGroupID() {
        return this.groupID;
    }

    /**
     * All alarms belong to a group and should be created by the group class. Set the association
     * by group ID.
     * @param id
     */
    public void setGroupID(long id) {
        this.groupID = id;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * An Alarm has a message, E.G.: the type of medication to take.
     *
     * @param alarmMessage
     */
    public void setAlarmMessage(String alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    /**
     * TODO: Make this function do something.
     * This Function will disable the alarm.
     */
    public void disableAlarm() {
        // Disable an alarm from the system scheduler.
    }

}
