package concentric.medalarm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by mike on 6/22/15.
 */
public class AlarmHandler extends Activity {

    private PendingIntent pendingIntent;
    private Intent alarmIntent;
    private AlarmManager alarmManager;
    private Context context;

    public AlarmHandler(AlarmHandler alarmHandler) {
        this.pendingIntent = alarmHandler.getPendingIntent();
        this.alarmIntent = alarmHandler.getAlarmIntent();
        this.alarmManager = alarmHandler.getAlarmManager();
        this.context = alarmHandler.getContext();
    }

    public AlarmHandler(Context context) {
        this.context = context;

        alarmIntent = new Intent(AlarmHandler.this, AlarmBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(AlarmHandler.this, 0, alarmIntent, 0);
    }

    public static void cancelAlarmStatic(AlarmHandler alarmHandler) {

        AlarmHandler tmp = new AlarmHandler(alarmHandler);
        tmp.getAlarmManager().cancel(tmp.getPendingIntent());
        Toast.makeText(tmp, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public Intent getAlarmIntent() {
        return alarmIntent;
    }

    public AlarmManager getAlarmManager() {
        return alarmManager;
    }

    public Context getContext() {
        return context;
    }

    public void cancelAlarm() {
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NewApi")
    public AlarmHandler createAlarm(int hour, int minute, int alarmRepeatTimeInMillis,
                                    Boolean repeating) {

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        if (repeating) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                      alarmRepeatTimeInMillis, pendingIntent);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager
                    .setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        return AlarmHandler.this;
    }

}
