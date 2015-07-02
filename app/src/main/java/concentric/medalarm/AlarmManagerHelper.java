package concentric.medalarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import concentric.medalarm.models.Alarm;
import concentric.medalarm.models.DBHelper;

/**
 * Created by MatthewAry on 7/2/2015.
 */
public class AlarmManagerHelper extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }

    public static void setAlarms(Context context) {

    }

    public static void cancelAlarms(Context context) {
        DBHelper instance = DBHelper.getInstance();
    }

    private static PendingIntent createPendingIntent(Context context, Alarm model) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(Alarm.COLUMN_NAME_ALARM_ID, model.getId());
        intent.putExtra(Alarm.COLUMN_NAME_ALARM_GROUP, model.getGroupID());
        intent.putExtra(Alarm.COLUMN_NAME_ALARM_TIME_HOUR, model.getHour());
        intent.putExtra(Alarm.COLUMN_NAME_ALARM_TIME_MINUTE, model.getMinute());

        return PendingIntent.getService(context, (int) model.getId(), intent, PendingIntent
                .FLAG_UPDATE_CURRENT);

    }
}
