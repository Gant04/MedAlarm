package concentric.medalarm;


import android.app.Activity;

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

    public void startAlarm() {
        //mediaPlayer.start();
        setIsPlaying(true);
    }

    public void stopAlarm() {
        if (isPlaying()) {
            //mediaPlayer.stop();
            setIsPlaying(false);
        }
    }

    public String getAlarmSound() {
        return this.tone;
    }

    public void setAlarmSound(String tone) {
        this.tone = tone;
    }
}
