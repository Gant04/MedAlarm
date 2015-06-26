package concentric.medalarm.models;

import junit.framework.Assert;
import android.test.AndroidTestCase;

/**
 * Created by MatthewAry on 6/13/2015.
 */
public class AlarmTest extends AndroidTestCase {

    private int hour = 23;
    private int minute = 50;
    private long groupId = 5;
    private long id = 2;

    private AlarmDataSource aDataSource;

    public void testAlarm() throws Throwable{
        // Basic Tests
        Alarm alarm = new Alarm(id, groupId, hour, minute);
        Assert.assertTrue(alarm.getHour() >= 0);
        Assert.assertTrue(alarm.getHour() < 24);
        Assert.assertTrue(alarm.getMinute() >= 0);
        Assert.assertTrue(alarm.getMinute() < 60);
        alarm.setMinute(-1);
        Assert.assertFalse(alarm.getMinute() == -1);
        alarm.setMinute(60);
        Assert.assertFalse(alarm.getMinute() == 60);

        // Database Tests
        aDataSource = new AlarmDataSource(getContext());
        aDataSource.open();

        //Alarm alarm = aDataSource.createAlarm(5, 2, 0);
    }
}