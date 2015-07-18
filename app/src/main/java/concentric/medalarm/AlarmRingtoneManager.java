package concentric.medalarm;

import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mike on 7/17/15.
 */
public class AlarmRingtoneManager {

    private static AlarmRingtoneManager ourInstance;
    private static List<HashMap<String, String>> ringtoneList;

    private AlarmRingtoneManager(Context context) {
        ringtoneList = new ArrayList<>();

        RingtoneManager ringtoneManager = new RingtoneManager(context);
        ringtoneManager.setType(RingtoneManager.TYPE_ALL);
        Cursor cursor = ringtoneManager.getCursor();

        while (cursor.moveToNext()) {
            HashMap<String, String> tempRingtoneList = new HashMap<>();
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            Log.i("Ringtone:", notificationTitle);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
            Log.i("Ringtone URI:", notificationUri);
            tempRingtoneList.put(notificationTitle, notificationUri);

            ringtoneList.add(tempRingtoneList);
        }
    }

    public static List<HashMap<String, String>> getRingtoneList() {
        return ringtoneList;
    }

    public static AlarmRingtoneManager getInstance(Context someContext) {

        if (ourInstance == null){
            synchronized (AlarmRingtoneManager.class) {
                ourInstance = new AlarmRingtoneManager(someContext);
            }
        }
        return ourInstance;
    }
}
