package concentric.medalarm.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MatthewAry on 6/22/2015.
 */
public class AlarmGroupDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = {
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_ID,
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_NAME,
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_TYPE,
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_RINGTONE,
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_OFFSET,
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_ENABLED,
            AlarmGroup.COLUMN_NAME_ALARM_REPEATABLE,
            AlarmGroup.COLUMN_NAME_ALARM_NUMBER_OF_REPEATS,
            AlarmGroup.COLUMN_NAME_ALARM_TIMES_REPEATED };


    public AlarmGroupDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public AlarmGroup createAlarmGroup(String groupName, String type, boolean offset,
                                       boolean enabled) {
        ContentValues values = new ContentValues();
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_NAME, groupName);
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_TYPE, type);
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_OFFSET, offset);
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_ENABLED, enabled);
        long insertId = database.insert(AlarmGroup.TABLE_NAME, null, values);
        Cursor cursor = database.query(AlarmGroup.TABLE_NAME, allColumns,
                                       AlarmGroup.COLUMN_NAME_ALARM_GROUP_ID +
                                       " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        AlarmGroup group = cursorToGroup(cursor);
        Log.i(getClass().getName() + " createAlarmGroup", "Alarm group creation");
        return group;
    }

    public void deleteGroup(AlarmGroup group) {
        long id = group.getId();
        Log.w(AlarmGroupDataSource.class.getName(),
                "Deleting AlarmGroup ID" + id + ".");
        database.delete(AlarmGroup.TABLE_NAME, Alarm.COLUMN_NAME_ALARM_ID + " = " + id, null);
        Log.i(getClass().getName() + " deleteGroup", "Alarm group deletion");
    }

    public List<AlarmGroup> getAllAlarmGroups() {
        List<AlarmGroup> alarmGroups = new ArrayList<AlarmGroup>();
        Cursor cursor = database.query(AlarmGroup.TABLE_NAME, allColumns,
                                       null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            AlarmGroup group = cursorToGroup(cursor);
            alarmGroups.add(group);
            cursor.moveToNext();
        }
        cursor.close();
        Log.i(getClass().getName() + " getAllAlarmGroups", "Retrieve the list of alarms");
        return alarmGroups;
    }

    public AlarmGroup cursorToGroup(Cursor cursor) {
        AlarmGroup group = new AlarmGroup();
        Log.i(getClass().getName() + " cursorToGroup", "Gets Something...");
        return group;
    }
}
