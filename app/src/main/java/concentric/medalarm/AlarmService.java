package concentric.medalarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import concentric.medalarm.models.AlarmStateManager;

/**
 * Created by MatthewAry on 6/12/2015.
 */
public class AlarmService extends Service {
    public static String TAG = AlarmService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmStateManager.set
    }
}
