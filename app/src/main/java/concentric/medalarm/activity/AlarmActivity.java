package concentric.medalarm.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

import concentric.medalarm.AlarmBroadcastReceiver;
import concentric.medalarm.R;

public class AlarmActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static AlarmActivity inst;
    private TextView alarmTextView;

    public static AlarmActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        Button b = (Button) findViewById(R.id.actionButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: GET FIELD DATA FROM TIME PICKER HERE.
                alarmTimePicker = (TimePicker) findViewById(R.id.timePicker2);
                Log.d("AlarmActivity", "Alarm On");
                Calendar calendar = Calendar.getInstance();
                Log.d("AlarmActivity", "Create new calendar");
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                Log.d("AlarmActivity", "Set calendar HOURS");
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
                Log.d("AlarmActivity", "Set calendar MINUTES");
                Intent myIntent = new Intent(AlarmActivity.this, AlarmBroadcastReceiver.class);
                Log.d("AlarmActivity", "Sending myIntent");
                pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);
                Log.d("AlarmActivity", "Pending Intent");
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                Log.d("AlarmActivity", "Alarm Manager set");
            }
        });*/

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        alarmTextView = (TextView) findViewById(R.id.text);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }


    public void onToggleClicked(View view) {
        Log.d("AlarmActivity", "On Toggle Clicked");
        if (((ToggleButton) view).isChecked()) {
//            Log.d("AlarmActivity", "Alarm On");
            Calendar calendar = Calendar.getInstance();
//            Log.d("AlarmActivity", "Create new calendar");
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
//            Log.d("AlarmActivity", "Set calendar HOURS");
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
//            Log.d("AlarmActivity", "Set calendar MINUTES");
            Intent myIntent = new Intent(AlarmActivity.this, AlarmBroadcastReceiver.class);
//            Log.d("AlarmActivity", "Sending myIntent");
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);
//            Log.d("AlarmActivity", "Pending Intent");
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
//            Log.d("AlarmActivity", "Alarm Manager set");
        } else {
            Log.d("AlarmActivity", "Alarm Off");
            alarmManager.cancel(pendingIntent);
            Log.d("AlarmActivity", "Alarm cancel");
            //alarmTextView.setText("");
            Log.d("AlarmActivity", "Alarm Off");
        }
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("AlarmActivity", "onOptionsItemSelected nav up");
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
