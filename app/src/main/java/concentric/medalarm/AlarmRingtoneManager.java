package concentric.medalarm;

import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 7/17/15.
 */
public class AlarmRingtoneManager {

    private static AlarmRingtoneManager ourInstance;
    private static Context context;
    private static Map<String,String> ringtoneList;

    public static AlarmRingtoneManager getInstance(Context someContext) {

        if (ourInstance == null){
            synchronized (AlarmRingtoneManager.class) {
                ourInstance = new AlarmRingtoneManager(someContext);
            }
        }
        return ourInstance;
    }

    public static Map<String,String> getRingtoneList() {
        return ringtoneList;
    }

    private AlarmRingtoneManager(Context context) {

        this.context = context;

        ringtoneList = new HashMap<>();
        RingtoneManager ringtoneManager = new RingtoneManager(context);
        ringtoneManager.setType(RingtoneManager.TYPE_ALL);
        Cursor cursor = ringtoneManager.getCursor();

        while(cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            Log.i("Ringtone:",notificationTitle);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
            Log.i("Ringtone URI:",notificationUri);
            ringtoneList.put(notificationTitle, notificationUri);
        }
    }
}
