package concentric.medalarm;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
}
