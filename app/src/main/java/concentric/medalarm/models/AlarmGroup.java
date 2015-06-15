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

    public AlarmGroup(String groupName, String ringTone, int type, boolean offset, boolean enabled) {

    }

    /*
    * Create Alarm Group with Ringtone.
    */
    public AlarmGroup(String groupName, int type, boolean offset, boolean enabled) {

    }

    /*
    * Create Alarm Group with default Ringtone.
    */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public boolean setRingTone(String ringTone) {
        // Check to see if the ringtone path is valid
        if (true) {
            this.ringTone = ringTone;
            return true;
        } else {
            return false;
        }
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
