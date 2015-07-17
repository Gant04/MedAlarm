package concentric.medalarm.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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


    public AlarmDataSource() {
        dbHelper = DBHelper.getInstance();
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
        if (repeats) {
            values.put(Alarm.COLUMN_NAME_ALARM_REPEATS_HOURS, rHours);
            values.put(Alarm.COLUMN_NAME_ALARM_REPEATS_MINUTES, rMinutes);
        }
        long insertId = database.insert(Alarm.TABLE_NAME, null, values);
        Cursor cursor = database.query(Alarm.TABLE_NAME, allColumns, Alarm.COLUMN_NAME_ALARM_ID +
                        " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Alarm alarm = cursorToAlarm(cursor);
        cursor.close();
        return alarm;
    }

    public void deleteAlarm(Alarm alarm) {
        long dId = alarm.getId();
        Log.e(getClass().getName() + " deleteAlarm", "Deleting alarm with id of " + dId);
        database.delete(Alarm.TABLE_NAME, Alarm.COLUMN_NAME_ALARM_ID + " = " + dId, null);
    }


    /**
     * This method constructs an array list based on the arrayGroup.
     *
     * @param groupID the group id for the alarm group.
     * @return an ArrayList of alarms.
     */
    public List<Alarm> getGroupAlarms(long groupID) {
        List<Alarm> alarms = new ArrayList<>();
        Cursor cursor = database.query(Alarm.TABLE_NAME, allColumns, Alarm.COLUMN_NAME_ALARM_GROUP +
                        " = ?", new String[]{String.valueOf(groupID)}, null, null,
                null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Alarm alarm = cursorToAlarm(cursor);
                alarms.add(alarm);
                cursor.moveToNext();
            }
            cursor.close();
            Log.i(getClass().getName() + " getGroupAlarms",
                    "Obtained a list of all alarms belonging to AlarmGroup with ID: " +
                            Long.toString(groupID));
        } else {
            Log.e(getClass().getName() + " getGroupAlarms",
                    "There are no Alarms belonging to AlarmGroup with ID: " +
                            Long.toString(groupID));
        }
        return alarms;
    }

    private Alarm cursorToAlarm(Cursor cursor) {
        Alarm alarm = new Alarm();
        alarm.setId(cursor.getLong(cursor.getColumnIndex(Alarm.COLUMN_NAME_ALARM_ID)));
        alarm.setGroupID(cursor.getLong(cursor.getColumnIndex(Alarm.COLUMN_NAME_ALARM_GROUP)));
        alarm.setHour(cursor.getInt(cursor.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TIME_HOUR)));
        alarm.setMinute(cursor.getInt(cursor.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TIME_MINUTE)));
        alarm.setrHour(cursor.getInt(cursor.getColumnIndex(Alarm.COLUMN_NAME_ALARM_REPEATS_HOURS)));
        alarm.setrMinute(cursor.getInt(cursor.getColumnIndex(Alarm.COLUMN_NAME_ALARM_REPEATS_MINUTES)));
        return alarm;
    }

    private ContentValues populateContent(Alarm item) {
        ContentValues values = new ContentValues();
        values.put(Alarm.COLUMN_NAME_ALARM_ID, item.getId());
        values.put(Alarm.COLUMN_NAME_ALARM_GROUP, item.getGroupID());
        values.put(Alarm.COLUMN_NAME_ALARM_TIME_HOUR, item.getHour());
        values.put(Alarm.COLUMN_NAME_ALARM_TIME_MINUTE, item.getMinute());
        values.put(Alarm.COLUMN_NAME_ALARM_REPEATS_HOURS, item.getrHour());
        values.put(Alarm.COLUMN_NAME_ALARM_REPEATS_MINUTES, item.getrMinute());
        return values;
    }

    public long createAlarm(Alarm item) {
        ContentValues values = populateContent(item);
        return database.insert(Alarm.TABLE_NAME, null, values);
    }

    public long updateAlarm(Alarm alarm) {
        ContentValues values = populateContent(alarm);
        return database.update(Alarm.TABLE_NAME, values, Alarm.COLUMN_NAME_ALARM_ID + " =?", new
                String[] {String.valueOf(alarm.getId())});
    }

    public int deleteAlarm(long id) {
        return database.delete(Alarm.TABLE_NAME, Alarm.COLUMN_NAME_ALARM_ID + " =?", new String[] {
                String.valueOf(id)});
    }

}
