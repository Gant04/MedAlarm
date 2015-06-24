package concentric.medalarm.models;

import junit.framework.Assert;
import android.test.AndroidTestCase;
/**
 * Created by MatthewAry on 6/13/2015.
 */
public class AlarmGroupTest extends AndroidTestCase {
    public void testAlarmGroup() throws Throwable {
        AlarmGroup alarmGroup = new AlarmGroup();
        Assert.assertTrue(alarmGroup.setAlarmType("Day") == true);
        Assert.assertTrue(alarmGroup.setAlarmType("Week") == true);
        Assert.assertTrue(alarmGroup.setAlarmType("Interval") == true);
        Assert.assertTrue(alarmGroup.setAlarmType("Once") == true);
        Assert.assertTrue(alarmGroup.setAlarmType("FAIL") == false);
        alarmGroup.setEnabled(true);
        Assert.assertTrue(alarmGroup.getEnabled() == true);
        alarmGroup.setEnabled(true);
        Assert.assertTrue(alarmGroup.getEnabled() == true);
        alarmGroup.setEnabled(false);
        Assert.assertTrue(alarmGroup.getEnabled() == false);
        alarmGroup.setEnabled(false);
        Assert.assertFalse(alarmGroup.getEnabled() == true);
        alarmGroup.setVibrate(true);
        Assert.assertTrue(alarmGroup.getVibrate() == true);
        alarmGroup.setVibrate(true);
        Assert.assertTrue(alarmGroup.getVibrate() == true);
        alarmGroup.setVibrate(false);
        Assert.assertTrue(alarmGroup.getVibrate() == false);
        alarmGroup.setVibrate(false);
        Assert.assertFalse(alarmGroup.getVibrate() == true);
    }
}