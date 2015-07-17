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
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_REPEATABLE,
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_NUMBER_OF_REPEATS,
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_TIMES_REPEATED,
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_VIBRATES};


    public AlarmGroupDataSource() {
        dbHelper = DBHelper.getInstance();
    }

    /**
     * Database opening operation.
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Database closing operation.
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Constructor to be used by an activity. Used if the alarm is not repeatable.
     * Uses the complete constructor.
     *
     * @param groupName The name of the group.
     * @param ringTone  String file reference of the sound to be played upon alarm firing.
     * @param type      TODO: Firm up this description.
     * @param offset    Does this alarm group get offset functionality?
     * @param enabled   Is this alarm group active?
     * @return AlarmGroup object.
     */
    public AlarmGroup createAlarmGroup(String groupName, String ringTone, int type, boolean offset,
                                       boolean enabled) {
        return createAlarmGroup(groupName, ringTone, type, offset, enabled, false, 0, 0);
    }

    /**
     * The standard AlarmGroup constructor.
     *
     * @param groupName     Essentially a message, or name of this alarm group
     * @param ringTone      A string reference to the ringtone that will be played upon alarm firing.
     * @param type          TODO: Firm up this definition.
     * @param offset        Does this alarm group use the offset feature?
     * @param enabled       Is this alarm group active?
     * @param repeatable    TODO: Do we need this here or at the alarm level?
     * @param numRepeats    How many times should the alarms go off?
     * @param timesRepeated The number of how many times it has gone off.
     * @return Returns a newly created AlarmGroup object.
     */
    public AlarmGroup createAlarmGroup(String groupName, String ringTone, int type,
                                       boolean offset,
                                       boolean enabled, boolean repeatable, int numRepeats,
                                       int timesRepeated) {
        ContentValues values = new ContentValues();
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_NAME, groupName);
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_TYPE, type);
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_OFFSET, offset);
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_ENABLED, enabled);
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_RINGTONE, ringTone);
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_REPEATABLE, repeatable);
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_NUMBER_OF_REPEATS, numRepeats);
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_TIMES_REPEATED, timesRepeated);
        long insertId = database.insert(AlarmGroup.TABLE_NAME, null, values);
        Cursor cursor = database.query(AlarmGroup.TABLE_NAME, allColumns,
                AlarmGroup.COLUMN_NAME_ALARM_GROUP_ID +
                        " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        AlarmGroup group = cursorToGroup(cursor);
        Log.i(getClass().getName() + " createAlarmGroup", "Alarm group creation");
        return group;
    }

    /**
     * Allows you to delete an alarm group by its object.
     * TODO: When an alarm group is deleted, deleted associated alarms too.
     *
     * @param group an AlarmGroup object to be deleted from the DB.
     */
    public void deleteGroup(AlarmGroup group) {
        long id = group.getId();
        Log.w(getClass().getName() + " deleteGroup", "Deleting AlarmGroup ID" + id + ".");
        database.delete(AlarmGroup.TABLE_NAME, Alarm.COLUMN_NAME_ALARM_ID + " = " + id, null);
        Log.i(getClass().getName() + " deleteGroup", "Alarm group deletion");
    }

    /**
     * The main activity needs to display a list view of all alarm groups currently in the database.
     * This function returns all alarm groups.
     *
     * @return ArrayList containing AlarmGroup objects.
     */
    public List<AlarmGroup> getAllAlarmGroups() {
        List<AlarmGroup> alarmGroups = new ArrayList<>();
        Cursor cursor = database.query(AlarmGroup.TABLE_NAME, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AlarmGroup group = cursorToGroup(cursor);
            alarmGroups.add(group);
            cursor.moveToNext();
        }
        cursor.close();
        Log.i(getClass().getName() + " getAllAlarmGroups", "Obtained a list of all AlarmGroups "
                + "and found " + alarmGroups.size() + " entries.");
        return alarmGroups;
    }

    /**
     * Allows the user to get an AlarmGroup object from an ID.
     * @param id
     * @return
     */
    public AlarmGroup getAlarmGroup(long id) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + AlarmGroup.TABLE_NAME + " WHERE " +
                AlarmGroup.COLUMN_NAME_ALARM_GROUP_ID + " = " + id, null);
        AlarmGroup alarmGroup = cursorToGroup(cursor);
        return alarmGroup;
    }

    public long createAlarmGroup(AlarmGroup item) {
        ContentValues values = populateContent(item);
        return database.insert(Alarm.TABLE_NAME, null, values);
    }

    public long updateAlarmGroup(AlarmGroup item) {
        ContentValues values = populateContent(item);
        return database.update(AlarmGroup.TABLE_NAME, values, AlarmGroup.COLUMN_NAME_ALARM_GROUP_ID + "" +
                " =?", new
                String[]{String.valueOf(item.getId())});
    }

    public int deleteAlarm(long id) {
        return database.delete(AlarmGroup.TABLE_NAME, AlarmGroup.COLUMN_NAME_ALARM_GROUP_ID + " " +
                "=?", new String[] { String.valueOf(id)});
    }


    /**
     * TODO: Should this be private?
     * This function is used to work with data returned by the database. Converts a query into an
     * AlarmGroup object. This is a helper function.
     *
     * @param cursor See android documentation.
     * @return AlarmGroup object.
     */
    private AlarmGroup cursorToGroup(Cursor cursor) {
        AlarmGroup group = new AlarmGroup();
        group.setId(cursor.getLong(cursor.getColumnIndex(AlarmGroup.COLUMN_NAME_ALARM_GROUP_ID)));
        group.setGroupName(cursor.getString(cursor.getColumnIndex(AlarmGroup
                 .COLUMN_NAME_ALARM_GROUP_NAME)));
        group.setOffset(cursor.getInt(cursor.getColumnIndex(AlarmGroup
                 .COLUMN_NAME_ALARM_GROUP_OFFSET)) > 0);
        group.setEnabled(cursor.getInt(cursor.getColumnIndex(AlarmGroup
                 .COLUMN_NAME_ALARM_GROUP_ENABLED)) > 0);
        group.setAlarmType(cursor.getInt(cursor.getColumnIndex(AlarmGroup
                 .COLUMN_NAME_ALARM_GROUP_TYPE)));
        group.setRepeatable(cursor.getInt(cursor.getColumnIndex(AlarmGroup
                .COLUMN_NAME_ALARM_GROUP_REPEATABLE)) > 0);
        group.setRingTone(cursor.getString(cursor.getColumnIndex(AlarmGroup
                 .COLUMN_NAME_ALARM_GROUP_RINGTONE)));
        group.setNumOfRepeats(cursor.getInt(cursor.getColumnIndex(AlarmGroup
                 .COLUMN_NAME_ALARM_GROUP_NUMBER_OF_REPEATS)));
        group.setTimesRepeated(cursor.getInt(cursor.getColumnIndex(AlarmGroup
                 .COLUMN_NAME_ALARM_GROUP_TIMES_REPEATED)));
        group.setVibrate(cursor.getInt(cursor.getColumnIndex(AlarmGroup
                 .COLUMN_NAME_ALARM_GROUP_VIBRATES)) > 0);
        Log.i(getClass().getName() + " cursorToGroup", "Gets Something...");
        return group;
    }

    private ContentValues populateContent(AlarmGroup item) {
        ContentValues values = new ContentValues();
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_ID, item.getId());
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_NAME, item.getGroupName());
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_RINGTONE, item.getRingTone());
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_TYPE, item.getAlarmType());
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_OFFSET, item.isOffset());
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_ENABLED, item.isEnabled());
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_REPEATABLE, item.isRepeatable());
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_NUMBER_OF_REPEATS, item.getNumOfRepeats());
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_TIMES_REPEATED, item.getTimesRepeated());
        values.put(AlarmGroup.COLUMN_NAME_ALARM_GROUP_VIBRATES, item.isVibrate());
        return values;
    }
}
