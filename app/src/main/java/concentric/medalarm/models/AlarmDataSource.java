package concentric.medalarm.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by MatthewAry on 6/22/2015.
 */
public class AlarmDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = {
            Alarm.COLUMN_NAME_ALARM_ID,
            Alarm.COLUMN_NAME_ALARM_GROUP,
            Alarm.COLUMN_NAME_ALARM_TIME_HOUR,
            Alarm.COLUMN_NAME_ALARM_TIME_MINUTE };

    public AlarmDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Alarm createAlarm(long groupID, int hour, int minute, boolean repeats) {
        ContentValues values = new ContentValues();
        //values.put();
        Alarm alarm = new Alarm();
        return alarm;
    }
}
