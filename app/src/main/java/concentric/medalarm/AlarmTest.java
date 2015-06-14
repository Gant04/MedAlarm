package concentric.medalarm;

import concentric.medalarm.models.Alarm;

/**
 * Created by Thom on 6/13/2015.
 */
public class AlarmTest {
    int hour = 24;
    int minute = 30;
    boolean daysToRepeat[] = new boolean[] {false, true, false, true, false, true, false};
    boolean repeatWeekly = false;

    public void runTest(){
        Alarm alarm = new Alarm(hour, minute, daysToRepeat, repeatWeekly);
        assert( alarm.getHour() >= 0 && alarm.getHour() < 24 );
        assert( alarm.getMinute() >= 0 && alarm.getMinute() < 60);
        assert(alarm.TABLE_NAME == "alarm");
        assert(alarm.COLUMN_NAME_ALARM_GROUP == "group");
        assert(alarm.COLUMN_NAME_ALARM_ID == "id");
        assert(alarm.COLUMN_NAME_ALARM_TIME_HOUR == "hour");
        assert(alarm.COLUMN_NAME_ALARM_TIME_MINUTE == "minute");
        assert(alarm.COLUMN_NAME_ALARM_REPEATS_DAYS == "repeatDays");
        assert(alarm.COLUMN_NAME_ALARM_REPEATS_WEEKLY == "repeatWeeks");
    }
}
