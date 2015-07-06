package concentric.medalarm.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import concentric.medalarm.AlarmBroadcastReceiver;
import concentric.medalarm.AlarmTimePickerDialogFragment;
import concentric.medalarm.R;

public class AlarmActivity extends AppCompatActivity {

    private static AlarmActivity inst;
    AlarmManager alarmManager;
    boolean menuClicked = false;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private TextView alarmMedicationName;
    private int hour;
    private int minute;

    private Spinner repeatSelection;
    private Spinner weeklySelection;
    private boolean biWeekly = false;

    private Handler timePickerHandler = new Handler() {
        @Override
        public void handleMessage(Message m) {
            Bundle bundle = m.getData();
            hour = bundle.getInt("set_hour");
            minute = bundle.getInt("set_minute");

            EditText editText = (EditText) findViewById(R.id.timeText);

            if (hour >= 12) {
                editText.setText((hour - 12) + ":" + String.format("%02d", minute) + " PM");
            } else if (hour == 0) {
                editText.setText(12 + ":" + String.format("%02d", minute) + " AM");
            } else {
                editText.setText(hour + ":" + String.format("%02d", minute) + " AM");
            }
        }
    };

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
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        alarmMedicationName = (TextView) findViewById(R.id.medicationNameText);

        addItemsToRepeatSpinner();
        addListenerToRepeatSpinner();
        addDaysToBiWeeklySpinner();
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
        alarmMedicationName.setText(alarmText);
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


    public void onSetAlarmTime(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("set_hour", hour);
        bundle.putInt("set_minute", minute);

        AlarmTimePickerDialogFragment timePickerDialogFragment = new AlarmTimePickerDialogFragment();
        timePickerDialogFragment.setHandler(timePickerHandler);
        timePickerDialogFragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(timePickerDialogFragment, "time_picker");
        fragmentTransaction.commit();
    }

    public void onClickActionMenu(View view) {

        int translation = -165;
        int rotationBegin = 0;
        int rotationEnd = 45;

        if (menuClicked) {
            translation = 0;
            int tmp = rotationBegin;
            rotationBegin = rotationEnd;
            rotationEnd = tmp;
        }

        final View createButton = findViewById(R.id.actionConfirm);
        final View cancelButton = findViewById(R.id.actionCancel);

        final View menuButton = findViewById(R.id.actionMenu);

        ObjectAnimator menuAnimator = ObjectAnimator.ofFloat(menuButton, "rotation", rotationBegin, rotationEnd * 3);
        menuAnimator.setInterpolator(new DecelerateInterpolator());
        menuAnimator.setRepeatCount(0);
        menuAnimator.setDuration(200);

        ObjectAnimator createAnimator;
        createAnimator = ObjectAnimator.ofFloat(createButton, "translationY", translation);
        createAnimator.setInterpolator(new DecelerateInterpolator());
        createAnimator.setRepeatCount(0);
        createAnimator.setDuration(500);

        final ObjectAnimator cancelAnimator = ObjectAnimator.ofFloat(cancelButton, "translationX", translation);
        cancelAnimator.setInterpolator(new DecelerateInterpolator());
        cancelAnimator.setRepeatCount(0);
        cancelAnimator.setDuration(500);

        createButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);

        AnimatorSet buttonGroup = new AnimatorSet();
        buttonGroup.play(menuAnimator).before(createAnimator);
        buttonGroup.play(createAnimator).before(cancelAnimator);
        buttonGroup.start();

        menuClicked = !menuClicked;
    }

    private void addItemsToRepeatSpinner() {
        repeatSelection = (Spinner) findViewById(R.id.RepeatSpinner);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("Daily");
        list.add("Once Bi-Weekly");
        list.add("Once a Week");
        list.add("Once a Month");

        repeatSelection.setPrompt("Select Alarm Repeat Type");

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatSelection.setAdapter(listAdapter);
    }

    private void addDaysToBiWeeklySpinner() {
        weeklySelection = (Spinner) findViewById(R.id.weeklySpinner);

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("Sunday");
        list.add("Monday");
        list.add("Tuesday");
        list.add("Wednesday");
        list.add("Thursday");
        list.add("Friday");
        list.add("Saturday");

        weeklySelection.setPrompt("Select Day to Repeat");

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weeklySelection.setAdapter(listAdapter);
    }

    private void addListenerToRepeatSpinner() {
        repeatSelection = (Spinner) findViewById(R.id.RepeatSpinner);
        repeatSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                View dayTable = findViewById(R.id.dayTable);
                View weeklySelectorLayout = findViewById(R.id.weeklySelectorLayout);
                weeklySelectorLayout.setVisibility(View.GONE);
                dayTable.setVisibility(View.GONE);
                biWeekly = false;
                //Toast.makeText(AlarmActivity.this, "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();

                String selection = parent.getItemAtPosition(position).toString();

//                list.add("Repeat Unselected");
//                list.add("Repeat Daily");
//                list.add("Repeat Bi-Weekly");
//                list.add("Repeat Weekly");
//                list.add("Repeat Once Monthly");

                switch (selection) {
                    case "":
                        break;
                    case "Daily": {
                        dayTable.setVisibility(View.VISIBLE);
                        break;
                    }
                    case "Bi-Weekly": {
                        weeklySelectorLayout.setVisibility(View.VISIBLE);
                        biWeekly = true;
                        break;
                    }
                    case "Weekly": {
                        weeklySelectorLayout.setVisibility(View.VISIBLE);
                    }

                    case "Once Monthly": {

                    }


                    default:
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onClickCreateAlarm(View view) {
        Bundle alarmData = new Bundle();
        alarmData.putInt("hour", hour);
        alarmData.putInt("minute", minute);
        alarmData.putString("medicationName", alarmMedicationName.getText().toString());

        Intent returnIntent = new Intent();
        returnIntent.putExtras(alarmData);

        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void onClickCancelChanges(View view) {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

}
