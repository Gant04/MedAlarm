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
    private final String SQL_CREATE_ALARM =
            "CREATE TABLE " + Alarm.TABLE_NAME +
            "(" + Alarm.COLUMN_NAME_ALARM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Alarm.COLUMN_NAME_ALARM_GROUP + " INTEGER NOT NULL, " +
            Alarm.COLUMN_NAME_ALARM_TIME_HOUR + " INTEGER NOT NULL, " +
            Alarm.COLUMN_NAME_ALARM_TIME_MINUTE + " INTEGER NOT NULL, " +
            Alarm.COLUMN_NAME_ALARM_REPEATS_HOURS + " INTEGER, " +
            Alarm.COLUMN_NAME_ALARM_REPEATS_MINUTES + " INTEGER, " +
            "FOREIGN KEY(" + Alarm.COLUMN_NAME_ALARM_GROUP + ") REFERENCES " +
            AlarmGroup.TABLE_NAME + "(id) " + ");";
    private final String SQL_CREATE_ALARM_GROUP =
            "CREATE TABLE " + AlarmGroup.TABLE_NAME +
            "(" + AlarmGroup.COLUMN_NAME_ALARM_GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_NAME + " TEXT," +
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_ENABLED + " BOOLEAN NOT NULL, " +
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_TYPE + " INTEGER NOT NULL, " +
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_RINGTONE + " TEXT NOT NULL, " +
            AlarmGroup.COLUMN_NAME_ALARM_GROUP_OFFSET + " BOOLEAN NOT NULL, " +
            AlarmGroup.COLUMN_NAME_ALARM_REPEATABLE + " BOOLEAN NOT NULL, " +
            AlarmGroup.COLUMN_NAME_ALARM_NUMBER_OF_REPEATS + " INTEGER, " +
            AlarmGroup.COLUMN_NAME_ALARM_TIMES_REPEATED + " INTEGER" + ");";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALARM_GROUP);
        db.execSQL(SQL_CREATE_ALARM);
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
