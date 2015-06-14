package concentric.medalarm.models;

/**
 * Created by mike on 6/13/15.
 */
public class Alarm {

    public static final String TABLE_NAME = "alarm";
    public static final String COLUMN_NAME_ALARM_GROUP = "group";
    public static final String COLUMN_NAME_ALARM_ID = "id";
    public static final String COLUMN_NAME_ALARM_TIME_HOUR = "hour";
    public static final String COLUMN_NAME_ALARM_TIME_MINUTE = "minute";
    public static final String COLUMN_NAME_ALARM_REPEATS_DAYS = "repeatDays";
    public static final String COLUMN_NAME_ALARM_REPEATS_WEEKLY = "repeatWeeks";

    private int hour;
    private int minute;
    private boolean[] daysToRepeat = new boolean[7];
    private boolean repeatWeekly;

    /**
     * Non-Default Alarm Constructor
     * This constructor allows an alarm to be created.
     *
     * @param hour Set the hour
     * @param minute Set the minute
     * @param daysToRepeat A boolean array to set the days to repeat.
     * @param repeatWeekly A boolean to set if repeat weekly.
     */
    public Alarm(int hour, int minute, boolean[] daysToRepeat, boolean repeatWeekly) {
        this.hour = hour;
        this.minute = minute;
        this.daysToRepeat = daysToRepeat;
        this.repeatWeekly = repeatWeekly;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColumnNameAlarmGroup() {
        return COLUMN_NAME_ALARM_GROUP;
    }

    public static String getColumnNameAlarmId() {
        return COLUMN_NAME_ALARM_ID;
    }

    public static String getColumnNameAlarmTimeHour() {
        return COLUMN_NAME_ALARM_TIME_HOUR;
    }

    public static String getColumnNameAlarmTimeMinute() {
        return COLUMN_NAME_ALARM_TIME_MINUTE;
    }

    public static String getColumnNameAlarmRepeatsDays() {
        return COLUMN_NAME_ALARM_REPEATS_DAYS;
    }

    public static String getColumnNameAlarmRepeatsWeekly() {
        return COLUMN_NAME_ALARM_REPEATS_WEEKLY;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean[] getDaysToRepeat() {
        return this.daysToRepeat;
    }

    public void setDaysToRepeat(boolean[] daysToRepeat) {
        this.daysToRepeat = daysToRepeat;
    }

    public boolean isRepeatWeekly() {
        return this.repeatWeekly;
    }

    public void setRepeatWeekly(boolean repeatWeekly) {
        this.repeatWeekly = repeatWeekly;
    }
}
