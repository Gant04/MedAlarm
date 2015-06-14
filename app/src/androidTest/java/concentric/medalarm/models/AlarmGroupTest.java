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
    }
}