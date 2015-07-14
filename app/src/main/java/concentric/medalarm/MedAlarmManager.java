package concentric.medalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import concentric.medalarm.models.Alarm;
import concentric.medalarm.models.AlarmDataSource;
import concentric.medalarm.models.AlarmGroup;
import concentric.medalarm.models.AlarmGroupDataSource;

/**
 * Created by mike on 7/14/15.
 */
public class MedAlarmManager extends AppCompatActivity {

    private MedAlarmManager(long groupID) {

        AlarmDataSource alarmDataSource = new AlarmDataSource();
        List<Alarm> alarmList = alarmDataSource.getGroupAlarms(groupID);


        for (Alarm alarm : alarmList) {
            long id = alarm.getId();

            //Checks to make sure the alarm isnt already set.
            boolean alarmCreatedAlready = (PendingIntent.getBroadcast(getApplicationContext(),
                    0, new Intent("MedAlarm.Intent." + groupID + "." + id), PendingIntent.FLAG_NO_CREATE) != null);

            if (!alarmCreatedAlready) {

                Log.i(getClass().getName() + ":", "Creating intent with the name: " + "MedAlarm.Intent." + groupID + "." + id);
                Intent intent = new Intent("MedAlarm.Intent." + groupID + "." + id);

                PendingIntent pendingIntent;

                Bundle alarmBundle = new Bundle();

                int hour = alarm.getHour();
                int minute = alarm.getMinute();
                int second = 0;
                int milli = 0;


                String medicationName = null;

                //Build the Bundle
                alarmBundle.putInt("hour", hour);                //add Hours
                alarmBundle.putInt("minute", minute);            //add Minutes
                alarmBundle.putInt("second", second);           //add Seconds
                alarmBundle.putInt("milli", second);            //add Millis

                alarmBundle.putLong("id", id);                  //add Alarm ID
                alarmBundle.putLong("groupID", groupID);        //add Group ID

                alarmBundle.putString("med", medicationName);    //add Medication Name

                intent.putExtras(alarmBundle);

                createAlarm(intent);

            }
        }
    }

    public static void setAllAlarms() {
        AlarmGroupDataSource alarmGroupDataSource = new AlarmGroupDataSource();
        List<AlarmGroup> alarmGroupList = alarmGroupDataSource.getAllAlarmGroups();

        for (AlarmGroup alarmGroup : alarmGroupList) {
            if (alarmGroup.getEnabled()) {
                new MedAlarmManager(alarmGroup.getId());
            }
        }
    }

    public void cancelGroup(long groupID) {

        AlarmDataSource alarmDataSource = new AlarmDataSource();
        List<Alarm> alarmList = alarmDataSource.getGroupAlarms(groupID);

        for (Alarm alarm : alarmList) {
            long id = alarm.getId();

            //Checks to make sure the alarm isnt already set.
            boolean alarmCreated = (PendingIntent.getBroadcast(getApplicationContext(),
                    0, new Intent("MedAlarm.Intent." + groupID + "." + id), PendingIntent.FLAG_NO_CREATE) != null);

            if (alarmCreated) {

            }

        }
    }

    private void createAlarm(final Intent alarmIntent) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int hour, minute, second, milli;
        hour = minute = second = milli = 0;

        long id, groupID;
        id = groupID = 0;

        String medicationName = "";

        alarmIntent.getIntExtra("hour", hour);                //add Hours
        alarmIntent.getIntExtra("minute", minute);            //add Minutes
        alarmIntent.getIntExtra("second", second);           //add Seconds
        alarmIntent.getIntExtra("milli", milli);            //add Millis

        alarmIntent.getLongExtra("id", id);                  //add Alarm ID
        alarmIntent.getLongExtra("groupID", groupID);        //add Group ID

        //alarmIntent.getStringExtra("med", medicationName);    //add Medication Name

        Long longHours = TimeConverter.hoursToMillis(hour);
        Long longMinutes = TimeConverter.minutesToMillis(minute);

        Long longTime = longHours + longMinutes;

        //Calculate difference between current time and future time AKA difference
        // TODO: Time is depreciated. Change to something else.
        Time time = new Time();
        time.setToNow();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, milli);

        final long difference = calendar.getTimeInMillis() - time.toMillis(true);
        Log.d("Time Difference:    ", Long.toString(difference));


        if (calendar.getTimeInMillis() < time.toMillis(true)) {
            calendar.set(Calendar.DAY_OF_YEAR, Calendar.DAY_OF_YEAR + 1);
        }


        /*
        new Intent("com.concentric.alarmIntent." + med)
        this allows to generate new intents on the fly.
        */

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


    }
}
