package concentric.medalarm.models;

/**
 * Created by mike on 6/13/15.
 */
public class Alarm {

    public static final String TABLE_NAME = "alarm";
    public static String COLUMN_NAME_ALARM_NAME = "";
    public static String COLUMN_NAME_ALARM_TIME_HOUR = "0";
    public static String COLUMN_NAME_ALARM_TIME_MINUTE = "0";
    public static String COLUMN_NAME_ALARM_REPEAT_DAYS = "0";
    public static String COLUMN_NAME_ALARM_REPEAT_WEEKLY = "0";
    public static String COLUMN_NAME_ALARM_TONE = "0";
    public static String COLUMN_NAME_ALARM_ENABLED = "0";

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
        return COLUMN_NAME_ALARM_REPEAT_DAYS;
    }

    public static void setColumnNameAlarmRepeatDays(String columnNameAlarmRepeatDays) {
        COLUMN_NAME_ALARM_REPEAT_DAYS = columnNameAlarmRepeatDays;
    }

    public static String getColumnNameAlarmRepeatWeekly() {
        return COLUMN_NAME_ALARM_REPEAT_WEEKLY;
    }

    public static void setColumnNameAlarmRepeatWeekly(String columnNameAlarmRepeatWeekly) {
        COLUMN_NAME_ALARM_REPEAT_WEEKLY = columnNameAlarmRepeatWeekly;
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
