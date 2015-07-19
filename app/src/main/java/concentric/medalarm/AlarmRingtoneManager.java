package concentric.medalarm;

import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 7/17/15.
 */
public class AlarmRingtoneManager {

    private static AlarmRingtoneManager ourInstance;
    private static List<String> toneNameList;
    private static List<Uri> URI_List;

    private AlarmRingtoneManager(Context context) {
        toneNameList = new ArrayList<>();
        URI_List = new ArrayList<>();

        RingtoneManager ringtoneManager = new RingtoneManager(context);
        ringtoneManager.setType(RingtoneManager.TYPE_ALL);
        Cursor cursor = ringtoneManager.getCursor();

        while (cursor.moveToNext()) {
            String toneTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            toneNameList.add(toneTitle);
            //URI_List.add(cursor.getString(RingtoneManager.URI_COLUMN_INDEX));
            Uri partUri = Uri.parse(cursor.getString(RingtoneManager.URI_COLUMN_INDEX));

            RingtoneManager rm = new RingtoneManager(context);
            Cursor c = rm.getCursor();
            c.moveToFirst();

            while (c.moveToNext()) {
                if (toneTitle.compareToIgnoreCase(c.getString(c.getColumnIndex(MediaStore.MediaColumns.TITLE))) == 0) {
                    int ringtoneID = c.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                    URI_List.add(Uri.withAppendedPath(partUri, "" + ringtoneID));
                    c.close();
                    break;
                }
                c.moveToNext();
            }

        }
    }

    public static List<String> getToneNameList() {
        return toneNameList;
    }

    public static List<Uri> getURI_List() {
        return URI_List;
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
