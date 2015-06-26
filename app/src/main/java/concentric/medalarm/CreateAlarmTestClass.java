package concentric.medalarm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by mike on 6/25/15.
 */
public class CreateAlarmTestClass extends Activity implements OnClickListener{

    Button setAlarm, cancelAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAlarm = (Button) findViewById(R.id.button);
        setAlarm.setOnClickListener(this);
        cancelAlarm = (Button) findViewById(R.id.button2);
        cancelAlarm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button: { //SetAlarm

                break;
            }

            case R.id.button2: { //CancelAlarm

                break;
            }

        }
    }
}


