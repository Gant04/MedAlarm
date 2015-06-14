package concentric.medalarm.models;

/**
 * Created by mike on 6/13/15.
 */
public class Alarm {

    public static final String TABLE_NAME = "alarm";
    public static final String COLUMN_NAME_ALARM_GROUP = "group";
    public static final String COLUMN_NAME_ALARM_TIME_HOUR = "hour";
    public static final String COLUMN_NAME_ALARM_TIME_MINUTE = "minute";
    public static final String COLUMN_NAME_ALARM_REPEATS_DAYS = "repeatDays";
    public static final String COLUMN_NAME_ALARM_REPEATS_WEEKLY = "repeatWeeks";

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColumnNameAlarmGroup() {
        return COLUMN_NAME_ALARM_GROUP;
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
}
