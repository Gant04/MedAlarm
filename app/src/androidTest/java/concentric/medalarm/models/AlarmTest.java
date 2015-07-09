package concentric.medalarm.models;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by MatthewAry on 6/13/2015.
 */
public class AlarmTest extends AndroidTestCase {

    private int hour = 23;
    private int minute = 50;
    private long groupId = 5;
    private long id = 2;

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
        DBHelper.getInstance(getContext());
        AlarmDataSource aDataSource = new AlarmDataSource();
        aDataSource.open();

        // Testing the creation of one row
        alarm = aDataSource.createAlarm(5, 2, 17, false, 0, 0);
        Assert.assertTrue(alarm.getGroupID() == 5);
        Assert.assertTrue(alarm.getHour() == 2);
        Assert.assertTrue(alarm.getMinute() == 17);

        // Testing the one to many query
        aDataSource.createAlarm(5, 6, 17, false, 0, 0);
        List<Alarm> aGroupList = aDataSource.getGroupAlarms(5);
        System.out.println("Size of aGroupList is: " + aGroupList.size());
        alarm = aGroupList.get(0);
        Assert.assertTrue(alarm.getGroupID() == 5);
        Assert.assertTrue(alarm.getHour() == 2);
        Assert.assertTrue(alarm.getMinute() == 17);
        aDataSource.close();

    }
}