package concentric.medalarm;

import android.test.AndroidTestCase;

import junit.framework.Assert;

/**
 * Created by mike on 6/13/15.
 */
public class AlarmPlayerTest extends AndroidTestCase {
    public void testAlarmPlayer() throws Throwable {
        AlarmPlayer alarmPlayer = new AlarmPlayer();
        Assert.assertFalse(alarmPlayer.isPlaying());
        alarmPlayer.startAlarmSound();
        Assert.assertTrue(alarmPlayer.isPlaying());
        alarmPlayer.stopAlarmSound();
        Assert.assertFalse(alarmPlayer.isPlaying());
    }
}
