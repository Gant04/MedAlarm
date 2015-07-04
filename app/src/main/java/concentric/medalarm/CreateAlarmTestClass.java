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
        //setAlarm = (Button) findViewById(R.id.actionButton);
        //setAlarm.setOnClickListener(this);
        //cancelAlarm = (Button) findViewById(R.id.button);
        //cancelAlarm.setOnClickListener(this);
    }


    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.actionButton: { //SetAlarm

                //break;
            }

            //case R.id.button: { //CancelAlarm

                //break;
            //}

        }
    }


