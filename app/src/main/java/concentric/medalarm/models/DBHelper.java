package concentric.medalarm.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MatthewAry on 6/13/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alarmclock.db";
    private static final String SQL_DELETE_ALARM =
            "DROP TABLE IF EXISTS " + Alarm.TABLE_NAME + ";";
    private static final String SQL_DELETE_ALARMGROUP =
            "DROP TABLE IF EXISTS " + AlarmGroup.TABLE_NAME + ";";
    private static final String SQL_CREATE =
            "CREATE TABLE " + Alarm.TABLE_NAME +
              " (" + Alarm.COLUMN_NAME_ALARM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                     Alarm.COLUMN_NAME_ALARM_GROUP + " INTEGER," +
                     Alarm.COLUMN_NAME_ALARM_TIME_HOUR + " INTEGER," +
                     Alarm.COLUMN_NAME_ALARM_TIME_MINUTE + " INTEGER," +
                     Alarm.COLUMN_NAME_ALARM_REPEATS_DAYS + " TEXT," +
                     Alarm.COLUMN_NAME_ALARM_REPEATS_WEEKLY + " BOOLEAN," + " ) " +
            "CREATE TABLE " + AlarmGroup.TABLE_NAME +
              " (" + AlarmGroup.COLUMN_NAME_ALARM_GROUP_NAME + " TEXT," +
                     AlarmGroup.COLUMN_NAME_ALARM_GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                     AlarmGroup.COLUMN_NAME_ALARM_GROUP_ENABLED + " BOOLEAN," +
                     AlarmGroup.COLUMN_NAME_ALARM_GROUP_TYPE + " INTEGER," +
                     AlarmGroup.COLUMN_NAME_ALARM_GROUP_RINGTONE + " TEXT," +
                     AlarmGroup.COLUMN_NAME_ALARM_GROUP_OFFSET + " BOOLEAN," + " )";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(), "Upgrading database from version " + oldVersion +
                        " to " + newVersion + " which will destroy all old data.");
        db.execSQL(SQL_DELETE_ALARM);
        db.execSQL(SQL_DELETE_ALARMGROUP);
        onCreate(db);
    }
}
