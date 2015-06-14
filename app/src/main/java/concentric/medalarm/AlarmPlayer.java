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

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }
    //private MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.alarm1);

    public void startAlarmSound() {
        //mediaPlayer.start();
        setIsPlaying(true);
    }

    public void stopAlarmSound() {
        if (isPlaying()) {
            //mediaPlayer.stop();
            setIsPlaying(false);
        }
    }
}
