package concentric.medalarm.models;

import junit.framework.Assert;
import android.test.AndroidTestCase;

/**
 * Created by MatthewAry on 6/13/2015.
 */
public class AlarmTest extends AndroidTestCase {

    public int hour = 23;
    public int minute = 50;
    public boolean daysToRepeat[] = new boolean[]{false, true, false, true, false, true, false};
    public boolean repeatWeekly = false;

    public void testAlarm() throws Throwable{
        Alarm alarm = new Alarm(hour, minute, daysToRepeat, repeatWeekly);
        Assert.assertTrue(alarm.getHour() >= 0);
        Assert.assertTrue(alarm.getHour() < 24);
        Assert.assertTrue(alarm.getMinute() >= 0);
        Assert.assertTrue(alarm.getMinute() < 60);
        Assert.assertTrue(!alarm.isRepeatWeekly());
        Assert.assertTrue(Alarm.TABLE_NAME.equals("alarm"));
        Assert.assertTrue(Alarm.COLUMN_NAME_ALARM_GROUP.equals("group"));
        Assert.assertTrue(Alarm.COLUMN_NAME_ALARM_ID.equals("id"));
        Assert.assertTrue(Alarm.COLUMN_NAME_ALARM_TIME_HOUR.equals("hour"));
        Assert.assertTrue(Alarm.COLUMN_NAME_ALARM_TIME_MINUTE.equals("minute"));
        Assert.assertTrue(Alarm.COLUMN_NAME_ALARM_REPEATS_DAYS.equals("repeatDays"));
        Assert.assertTrue(Alarm.COLUMN_NAME_ALARM_REPEATS_WEEKLY.equals("repeatWeeks"));
    }
}