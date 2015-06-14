package concentric.medalarm;

import concentric.medalarm.models.Alarm;

/**
 * Created by Thom on 6/13/2015.
 */
public class AlarmTest {
    int hour = 5;
    int minute = 30;
    boolean daysToRepeat[] = new boolean[] {false, true, false, true, false, true, false};
    boolean repeatWeekly = false;

    public void runTest(){
        Alarm alarm = new Alarm(hour, minute, daysToRepeat, repeatWeekly);

    }
}
