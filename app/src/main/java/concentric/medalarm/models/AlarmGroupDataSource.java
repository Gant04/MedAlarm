package concentric.medalarm.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MatthewAry on 6/22/2015.
 */
public class AlarmGroupDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = { AlarmGroup.COLUMN_NAME_ALARM_GROUP_ID,
                                    AlarmGroup.COLUMN_NAME_ALARM_GROUP_NAME,
                                    AlarmGroup.COLUMN_NAME_ALARM_GROUP_TYPE,
                                    AlarmGroup.COLUMN_NAME_ALARM_GROUP_RINGTONE,
                                    AlarmGroup.COLUMN_NAME_ALARM_GROUP_OFFSET,
                                    AlarmGroup.COLUMN_NAME_ALARM_GROUP_ENABLED };

    public AlarmGroupDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public AlarmGroup createGroup() {
        AlarmGroup group = new AlarmGroup();
        return group;
    }

    public void deleteGroup(AlarmGroup group) {

    }

    public List<AlarmGroup> getAllAlarmGroups() {
        List<AlarmGroup> alarmGroups = new ArrayList<AlarmGroup>();
        return alarmGroups;
    }

    public AlarmGroup cursorToGroup(Cursor cursor) {
        AlarmGroup group = new AlarmGroup();
        return group;
    }
}
