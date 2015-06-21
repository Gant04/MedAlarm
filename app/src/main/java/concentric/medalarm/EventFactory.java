package concentric.medalarm;

/**
 * Created by mike on 6/20/15.
 */
public class EventFactory {
    public static EventHandler create(String eventType) {
        EventHandler eventHandler = null;
        switch (eventType) {
            case ("toast"):
                eventHandler = new ToastHandler();
                return eventHandler;
            case ("notification"):
                eventHandler = new NotificationHandler();
                return eventHandler;
            case ("sound"):
                break;
            default:
                break;
        }
        return eventHandler;
    }
}
