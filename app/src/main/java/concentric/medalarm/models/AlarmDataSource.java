package concentric.medalarm.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
            Alarm.COLUMN_NAME_ALARM_TIME_MINUTE,
            Alarm.COLUMN_NAME_ALARM_REPEATS_HOURS,
            Alarm.COLUMN_NAME_ALARM_REPEATS_MINUTES};


    public AlarmDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Alarm createAlarm(long groupID, int hour, int minute, boolean repeats, int rHours,
                             int rMinutes) {
        ContentValues values = new ContentValues();
        values.put(Alarm.COLUMN_NAME_ALARM_GROUP, groupID);
        values.put(Alarm.COLUMN_NAME_ALARM_TIME_HOUR, hour);
        values.put(Alarm.COLUMN_NAME_ALARM_TIME_MINUTE, minute);
        values.put(Alarm.COLUMN_NAME_ALARM_REPEATS_HOURS, hour);
        if (repeats) {
            values.put(Alarm.COLUMN_NAME_ALARM_REPEATS_HOURS, rHours);
            values.put(Alarm.COLUMN_NAME_ALARM_REPEATS_MINUTES, rMinutes);
        }
        long insertId = database.insert(Alarm.TABLE_NAME, null, values);
        Cursor cursor = database.query(Alarm.TABLE_NAME, allColumns, Alarm.COLUMN_NAME_ALARM_ID +
                                                                     " = " + insertId, null, null,
                                       null, null);
        Alarm alarm = cursorToAlarm(cursor);
        return alarm;
    }

    public void deleteAlarm(Alarm alarm) {
        long dId = alarm.getId();
        Log.e(getClass().getName() + " deleteAlarm", "Deleting alarm with id of " + dId);
        database.delete(Alarm.TABLE_NAME, Alarm.COLUMN_NAME_ALARM_ID + " = " + dId, null);
    }

    private Alarm cursorToAlarm(Cursor cursor) {
        Alarm alarm = new Alarm();
        alarm.setId(cursor.getLong(cursor.getColumnIndex(Alarm.COLUMN_NAME_ALARM_ID)));
        alarm.setGroupID(cursor.getLong(cursor.getColumnIndex(Alarm.COLUMN_NAME_ALARM_GROUP)));
        alarm.setHour(cursor.getInt(cursor.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TIME_HOUR)));
        alarm.setMinute(cursor.getInt(cursor.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TIME_MINUTE)));
        return alarm;
    }
}
