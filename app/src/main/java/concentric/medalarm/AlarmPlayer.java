package concentric.medalarm;


import android.app.Activity;
import android.util.Log;


/**
 * Created by mike on 6/10/15.
 */
public class AlarmPlayer extends Activity {
    private boolean isPlaying = false;
    private String tone = "";

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    //private MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.alarm1);

    /**
     *
     */
    public void startAlarm() {
        //mediaPlayer.start();
        setIsPlaying(true);
        Log.i(getClass().getName() + " startAlarm", "Alarm is playing");
    }

    /**
     *
     */
    public void stopAlarm() {
        if (isPlaying()) {
            //mediaPlayer.stop();
            setIsPlaying(false);
            Log.i(getClass().getName() + " stopAlarm", "Alarm stopped");
        }
    }

    public String getAlarmSound() {
        return this.tone;
    }

    /**
     *
     * @param tone
     */
    public void setAlarmSound(String tone) {
        this.tone = tone;
    }
}
