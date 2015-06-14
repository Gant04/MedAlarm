package concentric.medalarm.models;

import junit.framework.TestCase;

/**
 * Created by MatthewAry on 6/13/2015.
 */
public class AlarmTest extends TestCase {

    int hour = 23;
    int minute = 90;
    boolean daysToRepeat[] = new boolean[]{false, true, false, true, false, true, false};
    boolean repeatWeekly = false;

    public void runTest() {
        Alarm alarm = new Alarm(hour, minute, daysToRepeat, repeatWeekly);
        assert (alarm.getHour() >= 0 && alarm.getHour() < 23);
        assert (alarm.getMinute() >= 0 && alarm.getMinute() < 60);
        assert (!alarm.isRepeatWeekly());
        assert (Alarm.TABLE_NAME.equals("alarm"));
        assert (Alarm.COLUMN_NAME_ALARM_GROUP.equals("group"));
        assert (Alarm.COLUMN_NAME_ALARM_ID.equals("id"));
        assert (Alarm.COLUMN_NAME_ALARM_TIME_HOUR.equals("hour"));
        assert (Alarm.COLUMN_NAME_ALARM_TIME_MINUTE.equals("minute"));
        assert (Alarm.COLUMN_NAME_ALARM_REPEATS_DAYS.equals("days"));
        assert (Alarm.COLUMN_NAME_ALARM_REPEATS_WEEKLY.equals("repeatWeeks"));
    }
}