package concentric.medalarm;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by MatthewAry on 6/12/2015.
 */
public class test extends Activity {
    public void runTest() {
        CharSequence message = "Hello World!";
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
