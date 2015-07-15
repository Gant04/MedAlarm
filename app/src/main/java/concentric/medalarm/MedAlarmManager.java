package concentric.medalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import concentric.medalarm.models.Alarm;
import concentric.medalarm.models.AlarmDataSource;
import concentric.medalarm.models.AlarmGroup;
import concentric.medalarm.models.AlarmGroupDataSource;

/**
 * Created by mike on 7/14/15.
 */
public class MedAlarmManager {

    private Context context;

    public MedAlarmManager(Context context) {
        this.context = context;
    }

    public void setAlarmGroupAlarms(long groupID) {

        AlarmDataSource alarmDataSource = new AlarmDataSource();

        try {
            alarmDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Alarm> alarmList = alarmDataSource.getGroupAlarms(groupID);

        for (Alarm alarm : alarmList) {
            //AlarmGroupDataSource alarmGroupDataSource = new AlarmGroupDataSource();
            //AlarmGroup alarmGroup = alarmGroupDataSource.getAlarmGroup(groupID);


            long id = alarm.getId();

            //Checks to make sure the alarm isnt already set.
            boolean alarmCreatedAlready = (PendingIntent.getBroadcast(context,
                    0, new Intent("MedAlarm.Intent." + groupID + "." + id), PendingIntent.FLAG_NO_CREATE) != null);

            if (!alarmCreatedAlready) {

                Log.i(getClass().getName() + ":", "Creating intent with the name: " + "MedAlarm.Intent." + groupID + "." + id);
                Intent intent = new Intent("MedAlarm.Intent." + groupID + "." + id);

                intent.setClassName(context.getPackageName(), context.getPackageName() + ".FullScreenAlarm");

                Bundle alarmBundle = new Bundle();

                int hour = alarm.getHour();
                int minute = alarm.getMinute();
                int second = 0;
                int milli = 0;


                //String medicationName = alarmGroup.getGroupName();

                //Build the Bundle
                alarmBundle.putInt("hour", hour);                //add Hours
                alarmBundle.putInt("minute", minute);            //add Minutes
                alarmBundle.putInt("second", second);           //add Seconds
                alarmBundle.putInt("milli", second);            //add Millis

                alarmBundle.putLong("id", id);                  //add Alarm ID
                alarmBundle.putLong("groupID", groupID);        //add Group ID

                //alarmBundle.putString("med", medicationName);    //add Medication Name

                intent.putExtras(alarmBundle);

                AlarmBroadcastReceiver alarmBroadcastReceiver = new AlarmBroadcastReceiver();

                context.registerReceiver(alarmBroadcastReceiver, new IntentFilter("MedAlarm.Intent." + groupID + "." + id));

                createAlarm(intent);

            }
        }

        alarmDataSource.close();
    }

    public void setAllAlarms() {

        AlarmGroupDataSource alarmGroupDataSource = new AlarmGroupDataSource();

        try {
            alarmGroupDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<AlarmGroup> alarmGroupList = alarmGroupDataSource.getAllAlarmGroups();

        for (AlarmGroup alarmGroup : alarmGroupList) {
            if (alarmGroup.getEnabled()) {
                setAlarmGroupAlarms(alarmGroup.getId());
            }
        }

        alarmGroupDataSource.close();
    }

    public void cancelGroup(long groupID) {

        AlarmDataSource alarmDataSource = new AlarmDataSource();
        List<Alarm> alarmList = alarmDataSource.getGroupAlarms(groupID);

        for (Alarm alarm : alarmList) {
            long id = alarm.getId();

            //Checks to make sure the alarm isnt already set.
            boolean alarmCreated = (PendingIntent.getBroadcast(context,
                    0, new Intent("MedAlarm.Intent." + groupID + "." + id), PendingIntent.FLAG_NO_CREATE) != null);

            if (alarmCreated) {

            }

        }
    }

    private void createAlarm(final Intent alarmIntent) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int hour, minute, second, milli;

        Bundle alarmBundle = alarmIntent.getExtras();

        long id, groupID;

        String medicationName = "";

        hour = alarmBundle.getInt("hour");                //add Hours
        minute = alarmBundle.getInt("minute");            //add Minutes
        second = alarmBundle.getInt("second");           //add Seconds
        milli = alarmBundle.getInt("milli");            //add Millis

        id = alarmBundle.getLong("id");                  //add Alarm ID
        groupID = alarmBundle.getLong("groupID");        //add Group ID

        //alarmIntent.getStringExtra("med", medicationName);    //add Medication Name

        //Calculate difference between current time and future time AKA difference
        // TODO: Time is depreciated. Change to something else.
        Time time = new Time();
        time.setToNow();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, milli);

        Log.i("Time set to: ", Integer.toString(hour) + ":" + Integer.toString(minute));

        final long difference = calendar.getTimeInMillis() - time.toMillis(true);
        Log.d("Time Difference:    ", Long.toString(difference));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) (context.getSystemService(Context.ALARM_SERVICE));
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }
}
