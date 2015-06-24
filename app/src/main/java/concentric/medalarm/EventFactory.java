package concentric.medalarm;

/**
 * Created by mike on 6/20/15.
 */
public class EventFactory {



    public static EventHandler create(String eventType) {
        EventHandler eventHandler = null;
        switch (eventType) {
            case ("toast"):
                String notificationText = "string here maybe";
                eventHandler = new ToastHandler(notificationText);
                return eventHandler;
            case ("notification"):
                eventHandler = new NotificationHandler();
                return eventHandler;
            default:
                break;
        }
        return eventHandler;
    }
}
