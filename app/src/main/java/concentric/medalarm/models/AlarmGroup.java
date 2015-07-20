package concentric.medalarm.models;

import android.util.Log;

import java.sql.SQLException;

public class AlarmGroup {
    public static final String TABLE_NAME = "alarmGroup";
    public static final String COLUMN_NAME_ALARM_GROUP_ID = "id";
    public static final String COLUMN_NAME_ALARM_GROUP_NAME = "name";
    public static final String COLUMN_NAME_ALARM_GROUP_RINGTONE = "ringtone";
    public static final String COLUMN_NAME_ALARM_GROUP_TYPE = "type";
    public static final String COLUMN_NAME_ALARM_GROUP_OFFSET = "offset";
    public static final String COLUMN_NAME_ALARM_GROUP_ENABLED = "enabled";
    public static final String COLUMN_NAME_ALARM_GROUP_REPEATABLE = "repeatable";
    public static final String COLUMN_NAME_ALARM_GROUP_NUMBER_OF_REPEATS = "numberOfRepeats";
    public static final String COLUMN_NAME_ALARM_GROUP_TIMES_REPEATED = "timesRepeated";
    public static final String COLUMN_NAME_ALARM_GROUP_VIBRATES = "vibrates";

    private String groupName;
    private String ringTone;
    private long id;
    private int type = 0;
    private int numOfRepeats;
    private int timesRepeated;
    private boolean repeatable = false;
    private boolean offset = false;
    private boolean enabled = false;
    private boolean vibrate = false;

    public AlarmGroup() {
        // Do nothing.
    }

    /**
     * Create Alarm Group with Custom Ringtone.
     *
     * @param id        takes an id
     * @param groupName takes a groupName
     * @param ringTone  takes a ringTone
     * @param type      takes a type
     * @param offset    takes an offset
     * @param enabled   takes an enabled
     */
    public AlarmGroup(long id, String groupName, String ringTone, int type, boolean offset,
                      boolean enabled) {
        setId(id);
        setGroupName(groupName);
        setRingTone(ringTone);
        setAlarmType(type);
        setOffset(offset);
        setEnabled(enabled);
    }

    /**
     * Create Alarm Group with default Ringtone.
     *
     * @param groupName     takes groupName
     * @param ringTone      takes ringTone
     * @param type          takes type
     * @param offset        takes offset
     * @param enabled       takes enabled
     * @param repeatable    takes repeatable
     * @param numRepeats    take numRepeates
     * @param timesRepeated take timesRepeated
     */
    public AlarmGroup(String groupName, String ringTone, int type, boolean offset, boolean
            enabled, boolean repeatable, int numRepeats, int timesRepeated) {
        setGroupName(groupName);
        setRingTone(ringTone);
        setAlarmType(type);
        setOffset(offset);
        setEnabled(enabled);
        setRepeatable(repeatable);
        setNumOfRepeats(numRepeats);
        setTimesRepeated(timesRepeated);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRingTone() {
        return ringTone;
    }

    public void setRingTone(String ringTone) {
        // TODO Check to see if the ringtone path is valid.. Maybe...
        this.ringTone = ringTone;
    }

    public boolean getOffset(boolean toggle) {
        return offset;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public boolean getVibrate() {
        return vibrate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAlarmType() {
        return type;
    }

    /**
     * Sets the alarm type
     *
     * @param type
     * @return
     */
    public void setAlarmType(int type) {
        boolean valid = false;
        this.type = type;
    }

    // After every alarm goes off we will need to perform operations on the alarm depending on the
    // Group type.
    public void checkAlarms() {
        // If the alarm only can go off a certain number of times, increment increment the times
        // repeated. If the times repeated = the repeat number, disable alarm.

        // If not disabled, after an alarm goes off, schedule the next alarm in the group.

    }

    public void disableAlarms() {
        // Disable all alarms in the group
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNumOfRepeats() {
        return numOfRepeats;
    }

    public void setNumOfRepeats(int numOfRepeats) {
        this.numOfRepeats = numOfRepeats;
    }

    public int getTimesRepeated() {
        return timesRepeated;
    }

    public void setTimesRepeated(int timesRepeated) {
        this.timesRepeated = timesRepeated;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public boolean isOffset() {
        return offset;
    }

    public void setOffset(boolean toggle) {
        if (this.offset && toggle) {
            Log.i(getClass().getName() + " setOffset", "Offset was already true!");
        } else if (!this.offset & !toggle) {
            Log.i(getClass().getName() + " setOffset", "Offset was already false!");
        } else {
            offset = toggle;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean toggle) {
        if (this.enabled && toggle) {
            Log.i(getClass().getName() + " setEnabled", "Enabled was already true!");
        } else if (!this.enabled & !toggle) {
            Log.i(getClass().getName() + " setEnabled", "Enabled was already false!");
        } else {
            enabled = toggle;
        }
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        if (this.vibrate && vibrate) {
            Log.i(getClass().getName() + " setVibrate", "Vibrate was already true!");
        } else if (!this.vibrate & !vibrate) {
            Log.i(getClass().getName() + " setVibrate", "Vibrate was already false!");
        } else {
            this.vibrate = vibrate;
        }
    }

    /**
     * addAlarm
     * This allows you to create an alarm from the AlarmGroup class.
     * TODO: Should this function do more like register an alarm with the system?
     *
     * @param hour     Hour it goes off.
     * @param minute   Minute it goes off.
     * @param repeats  If it repeats
     * @param rHours   Hour interval it repeats.
     * @param rMinutes Minute interval it repeats.
     */
    public void addAlarm(int hour, int minute, boolean repeats, int rHours, int rMinutes) {
        long id = getId();
        AlarmDataSource dataSource = new AlarmDataSource();

        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // TODO: Figure out where in the class stack Repeating alarms should be handled.
        dataSource.createAlarm(id, hour, minute, repeats, rHours, rMinutes);

        dataSource.close();
        Log.i(getClass().getName() + " addAlarm", "Alarm created");
    }


}
