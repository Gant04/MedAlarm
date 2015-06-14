package concentric.medalarm.models;

import junit.framework.TestCase;

/**
 * Created by MatthewAry on 6/13/2015.
 */
public class AlarmTest extends TestCase {

    int hour = 5;
    int minute = 30;
    boolean daysToRepeat[] = new boolean[]{false, true, false, true, false, true, false};
    boolean repeatWeekly = false;

    public void testGetTableName() throws Exception {

    }

    public void testGetColumnNameAlarmName() throws Exception {

    }

    public void testSetColumnNameAlarmName() throws Exception {

    }

    public void testGetColumnNameAlarmTimeHour() throws Exception {

    }

    public void testSetColumnNameAlarmTimeHour() throws Exception {

    }

    public void testGetColumnNameAlarmTimeMinute() throws Exception {

    }

    public void testSetColumnNameAlarmTimeMinute() throws Exception {

    }

    public void testGetColumnNameAlarmRepeatDays() throws Exception {

    }

    public void testSetColumnNameAlarmRepeatDays() throws Exception {

    }

    public void testGetColumnNameAlarmRepeatWeekly() throws Exception {

    }

    public void testSetColumnNameAlarmRepeatWeekly() throws Exception {

    }

    public void testGetColumnNameAlarmTone() throws Exception {

    }

    public void testSetColumnNameAlarmTone() throws Exception {

    }

    public void testGetColumnNameAlarmEnabled() throws Exception {

    }

    public void testSetColumnNameAlarmEnabled() throws Exception {

    }

    public void runTest() {
        Alarm alarm = new Alarm(hour, minute, daysToRepeat, repeatWeekly);
        // Tests
        assert (hour >= 0 || hour < 24);
        assert (minute >= 0 || minute < 60);

        assert (Alarm.TABLE_NAME == "alarm");
        assert (Alarm.COLUMN_NAME_ALARM_GROUP == "group");
        assert (Alarm.COLUMN_NAME_ALARM_ID == "id");
        assert (Alarm.COLUMN_NAME_ALARM_TIME_HOUR == "hour");
        assert (Alarm.COLUMN_NAME_ALARM_TIME_MINUTE == "minute");
        assert (Alarm.COLUMN_NAME_ALARM_REPEATS_DAYS == "repeatDays");
        assert (Alarm.COLUMN_NAME_ALARM_REPEATS_WEEKLY == "repeatWeeks");
    }
    }