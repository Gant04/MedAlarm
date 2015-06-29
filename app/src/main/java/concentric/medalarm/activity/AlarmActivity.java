package concentric.medalarm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import android.util.Log;
import android.view.View;
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
    private Toolbar toolbar;
    private ToggleButton alarmToggle;

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
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker2);
        alarmTextView = (TextView) findViewById(R.id.text);
        alarmToggle = (ToggleButton) findViewById(R.id.toggleButton);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
