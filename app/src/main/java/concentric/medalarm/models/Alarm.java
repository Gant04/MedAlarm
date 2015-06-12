package concentric.medalarm.models;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public final class Alarm {


    // Public fields
    public long mId;
    public int mYear;
    public int mMonth;
    public int mDay;
    public int mHour;
    public int mMinute;
    public String mLabel;
    public boolean mVibrate;
    public Uri mRingtone;
    public Long mAlarmId;
    public int mAlarmState;

    public Alarm(Calendar calendar, Long alarmId) {

    }

    public Alarm(Calendar calendar) {

    }

    public Alarm(Cursor c) {

    }

    public String getLabelOrDefault(Context context) {
        return null;
    }

    public void setAlarmTime(Calendar calendar) {

    }
    /**
     * Return the time when a alarm should fire.
     *
     * @return the time
     */
    public Calendar getAlarmTime() {
        return null;
    }
    /**
     * Return the time when a low priority notification should be shown.
     *
     * @return the time
     */
    public Calendar getLowNotificationTime() {
        return null;
    }
    /**
     * Return the time when a high priority notification should be shown.
     *
     * @return the time
     */
    public Calendar getHighNotificationTime() {
        return null;
    }
    /**
     * Return the time when a missed notification should be removed.
     *
     * @return the time
     */
    public Calendar getMissedTimeToLive() {
        return null;
    }
    /**
     * Return the time when the alarm should stop firing and be marked as missed.
     *
     * @param context to figure out the timeout setting
     * @return the time when alarm should be silence, or null if never
     */
    public Calendar getTimeout(Context context) {
        return null;
    }
}
