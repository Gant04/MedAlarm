package concentric.medalarm;

import android.util.Log;

/**
 * Created by mike on 6/20/15.
 */
public class ToastHandler implements EventHandler {

    public ToastHandler(String notificationText) {

    }

    private static String LOG_TOAST_HANDLER = "ToastHandler: ";

    @Override
    public String getNotificationText() {
        return null;
    }

    @Override
    public void setNotificationText(String notificationText) {

    }

    @Override
    public void displayNotification() {
        Log.d(LOG_TOAST_HANDLER, "Creating a Toast with the following text: ");
    }
}
