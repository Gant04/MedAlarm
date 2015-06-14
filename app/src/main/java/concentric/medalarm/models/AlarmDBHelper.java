package concentric.medalarm.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MatthewAry on 6/13/2015.
 */
public class AlarmDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alarmclock.db";

    private static final String SQL_CREATE =
            "CREATE TABLE " + Alarm.TABLE_NAME +
              " (" + Alarm.COLUMN_NAME_ALARM_ID + " INTEGER PRIMARY KEY," +
                     Alarm.COLUMN_NAME_ALARM_GROUP + " INTEGER," +
                     Alarm.COLUMN_NAME_ALARM_TIME_HOUR + " INTEGER," +
                     Alarm.COLUMN_NAME_ALARM_TIME_MINUTE + " INTEGER," +
                     Alarm.COLUMN_NAME_ALARM_REPEATS_DAYS + " TEXT," +
                     Alarm.COLUMN_NAME_ALARM_REPEATS_WEEKLY + " BOOLEAN," + " ) " +
            "CREATE TABLE " + AlarmGroup.COLUMN_NAME_ALARM_GROUP_NAME + " TEXT," +
              " (" + AlarmGroup.COLUMN_NAME_ALARM_GROUP_ID + " INTEGER PRIMARY KEY," +
                     AlarmGroup.COLUMN_NAME_ALARM_GROUP_ENABLED + " BOOLEAN," +
                     AlarmGroup.COLUMN_NAME_ALARM_GROUP_TYPE + " INTEGER," +
                     AlarmGroup.COLUMN_NAME_ALARM_GROUP_RINGTONE + " TEXT," +
                     AlarmGroup.COLUMN_NAME_ALARM_GROUP_OFFSET + " BOOLEAN," + " )";

    private static final String SQL_DELETE_ALARM =
            "DROP TABLE IF EXISTS " + Alarm.TABLE_NAME;

    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ALARM);
        onCreate(db);
    }
}
