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
     * @param hour
     * @param minute
     * @param daysToRepeat
     * @param repeatWeekly
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

    public static int getHour() {
        return hour;
    }

    public static void setHour(int hour) {
        Alarm.hour = hour;
    }

    public static int getMinute() {
        return minute;
    }

    public static void setMinute(int minute) {
        Alarm.minute = minute;
    }

    public static boolean[] getDaysToRepeat() {
        return daysToRepeat;
    }

    public static void setDaysToRepeat(boolean[] daysToRepeat) {
        Alarm.daysToRepeat = daysToRepeat;
    }

    public static boolean isRepeatWeekly() {
        return repeatWeekly;
    }

    public static void setRepeatWeekly(boolean repeatWeekly) {
        Alarm.repeatWeekly = repeatWeekly;
    }
}
