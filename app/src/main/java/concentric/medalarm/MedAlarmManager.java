package concentric.medalarm;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by mike on 7/14/15.
 */
public class MedAlarmManager {

    Intent intent;
    PendingIntent pendingIntent;

    Bundle alarmBundle; //Alarm info.

    public MedAlarmManager(Bundle alarmBundle) {
        this.alarmBundle = alarmBundle;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    public Bundle getAlarmBundle() {
        return alarmBundle;
    }

    public void setAlarmBundle(Bundle alarmBundle) {
        this.alarmBundle = alarmBundle;
    }
}
