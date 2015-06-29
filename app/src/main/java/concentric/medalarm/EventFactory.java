package concentric.medalarm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mike on 6/20/15.
 */
public class EventFactory {

    public static EventHandler OnCreate(String eventType) {
        EventHandler eventHandler = null;
        switch (eventType) {
            case ("toast"):
                String notificationText = "string here maybe";
                eventHandler = new ToastHandler(notificationText);
                return eventHandler;
            case ("notification"):
                eventHandler = new NotificationHandler();
                return eventHandler;
            case ("alarm"):
                //eventHandler = new SoundPlayer();
                return eventHandler;
            default:
                break;
        }
        return eventHandler;
    }
}
