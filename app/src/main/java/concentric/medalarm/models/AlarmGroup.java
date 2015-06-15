package concentric.medalarm.models;

public class AlarmGroup {
    public static final String TABLE_NAME = "alarmGroup";
    public static final String COLUMN_NAME_ALARM_GROUP_ID = "od";
    public static final String COLUMN_NAME_ALARM_GROUP_NAME = "name";
    public static final String COLUMN_NAME_ALARM_GROUP_RINGTONE = "ringtone";
    public static final String COLUMN_NAME_ALARM_GROUP_TYPE = "type";
    public static final String COLUMN_NAME_ALARM_GROUP_OFFSET = "offset";
    public static final String COLUMN_NAME_ALARM_GROUP_ENABLED = "enabled";

    private String groupName = "";
    private String ringTone = "";
    private int type = 0;
    private boolean offset = false;
    private boolean enabled = false;
    private boolean vibrate = false;

    public AlarmGroup() {
        // Do nothing.
    }

    /*
    * Create Alarm Group with Ringtone.
    */
    public AlarmGroup(String groupName, String ringTone, String type, boolean offset, boolean enabled) {
        setGroupName(groupName);
        setRingTone(ringTone);
        setAlarmType(type);
        setOffset(offset);
        setEnabled(enabled);
    }

    /*
    * Create Alarm Group with default Ringtone.
    */
    public AlarmGroup(String groupName, String type, boolean offset, boolean enabled) {

    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setRingTone(String ringTone) {
        // Check to see if the ringtone path is valid.. Maybe...
        this.ringTone = ringTone;
    }

    public String getRingTone() {
        return ringTone;
    }

    public void setOffset(boolean toggle) {
        offset = toggle;
    }

    public boolean getOffset(boolean toggle) {
        return offset;
    }

    public void setEnabled(boolean toggle) {
        enabled = toggle;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public boolean getVibrate() {
        return vibrate;
    }

    public boolean setAlarmType(String type) {
        boolean valid = false;
        switch (type) {
            case "Day" :
                valid = true;
                this.type = 1;
                break;
            case "Week" :
                valid = true;
                this.type = 2;
                break;
            case "Interval" :
                valid = true;
                this.type = 3;
                break;
            case "Once" :
                valid = true;
                this.type = 4;
                break;
            default:
                break;
        }
        return valid;
    }
}
