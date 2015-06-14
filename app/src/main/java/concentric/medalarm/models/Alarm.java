package concentric.medalarm.models;

/**
 * Created by mike on 6/13/15.
 */
public class Alarm {

    public static final String TABLE_NAME = "alarm";
    public static String COLUMN_NAME_ALARM_NAME = "";
    public static String COLUMN_NAME_ALARM_TIME_HOUR = "0";
    public static String COLUMN_NAME_ALARM_TIME_MINUTE = "0";
    public static String COLUMN_NAME_ALARM_REPEATS_DAYS = "0";
    public static String COLUMN_NAME_ALARM_REPEATS_WEEKLY = "0";
    public static String COLUMN_NAME_ALARM_TONE = "0";
    public static String COLUMN_NAME_ALARM_ENABLED = "0";

    /**
     * Non-Default Constructor for the Alarm Class
     * This constructor builds the data for the AlarmDB
     *
     * @param ALARM_NAME          The alarm name/medication name
     * @param ALARM_TIME_HOUR     The hour for dose
     * @param ALARM_TIME_MINUTE   The minute for dose
     * @param ALARM_REPEAT_DAYS   The days to repeat alarm
     * @param ALARM_REPEAT_WEEKLY The weeks to repeat.
     * @param ALARM_TONE          The alarm tone.
     * @param ALARM_ENABLED       Alarm enable
     */
    public Alarm(String ALARM_NAME, String ALARM_TIME_HOUR, String ALARM_TIME_MINUTE,
                 String ALARM_REPEAT_DAYS, String ALARM_REPEAT_WEEKLY,
                 String ALARM_TONE, String ALARM_ENABLED) {
        COLUMN_NAME_ALARM_NAME = ALARM_NAME;
        COLUMN_NAME_ALARM_TIME_HOUR = ALARM_TIME_HOUR;
        COLUMN_NAME_ALARM_TIME_MINUTE = ALARM_TIME_MINUTE;
        COLUMN_NAME_ALARM_REPEATS_DAYS = ALARM_REPEAT_DAYS;
        COLUMN_NAME_ALARM_REPEATS_WEEKLY = ALARM_REPEAT_WEEKLY;
        COLUMN_NAME_ALARM_TONE = ALARM_TONE;
        COLUMN_NAME_ALARM_ENABLED = ALARM_ENABLED;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColumnNameAlarmName() {
        return COLUMN_NAME_ALARM_NAME;
    }

    public static void setColumnNameAlarmName(String columnNameAlarmName) {
        COLUMN_NAME_ALARM_NAME = columnNameAlarmName;
    }

    public static String getColumnNameAlarmTimeHour() {
        return COLUMN_NAME_ALARM_TIME_HOUR;
    }

    public static void setColumnNameAlarmTimeHour(String columnNameAlarmTimeHour) {
        COLUMN_NAME_ALARM_TIME_HOUR = columnNameAlarmTimeHour;
    }

    public static String getColumnNameAlarmTimeMinute() {
        return COLUMN_NAME_ALARM_TIME_MINUTE;
    }

    public static void setColumnNameAlarmTimeMinute(String columnNameAlarmTimeMinute) {
        COLUMN_NAME_ALARM_TIME_MINUTE = columnNameAlarmTimeMinute;
    }

    public static String getColumnNameAlarmRepeatDays() {
        return COLUMN_NAME_ALARM_REPEATS_DAYS;
    }

    public static void setColumnNameAlarmRepeatDays(String columnNameAlarmRepeatDays) {
        COLUMN_NAME_ALARM_REPEATS_DAYS = columnNameAlarmRepeatDays;
    }

    public static String getColumnNameAlarmRepeatWeekly() {
        return COLUMN_NAME_ALARM_REPEATS_WEEKLY;
    }

    public static void setColumnNameAlarmRepeatWeekly(String columnNameAlarmRepeatWeekly) {
        COLUMN_NAME_ALARM_REPEATS_WEEKLY = columnNameAlarmRepeatWeekly;
    }

    public static String getColumnNameAlarmTone() {
        return COLUMN_NAME_ALARM_TONE;
    }

    public static void setColumnNameAlarmTone(String columnNameAlarmTone) {
        COLUMN_NAME_ALARM_TONE = columnNameAlarmTone;
    }

    public static String getColumnNameAlarmEnabled() {
        return COLUMN_NAME_ALARM_ENABLED;
    }

    public static void setColumnNameAlarmEnabled(String columnNameAlarmEnabled) {
        COLUMN_NAME_ALARM_ENABLED = columnNameAlarmEnabled;
    }
}
