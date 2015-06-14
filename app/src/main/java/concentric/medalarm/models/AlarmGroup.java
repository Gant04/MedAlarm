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

    public void setRingTone(String tone) {
        ringTone = tone;
    }

}
