package concentric.medalarm.activity;


import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import concentric.medalarm.AlarmRingtoneManager;
import concentric.medalarm.CustomListViewAdapter;
import concentric.medalarm.MedAlarmManager;
import concentric.medalarm.R;
import concentric.medalarm.models.Alarm;
import concentric.medalarm.models.AlarmDataSource;
import concentric.medalarm.models.AlarmGroup;
import concentric.medalarm.models.AlarmGroupDataSource;

public class Edit_Daily_Alarm extends AppCompatActivity {
    private AlarmGroup alarmGroup;
    private List<Alarm> alarms;
    private List<String> list = new ArrayList<>();
    private List<Bundle> aTimes = new ArrayList<>();
    private ListView listView;
    private CustomListViewAdapter adapter;
    private Calendar dateAndTime = Calendar.getInstance();
    private Uri toneURI;
    private int type = 1;

    private TimePickerDialog.OnTimeSetListener tp = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay,
                              int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
           // updateList();
        }
    };

    /**
     * The onCreate - creates the activity
     * @param savedInstanceState takes a savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__daily__alarm);
        // Get Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Load in alarm details
        AlarmGroupDataSource db = new AlarmGroupDataSource();
        AlarmDataSource dba = new AlarmDataSource();
        try {
            db.open();
            Bundle extras = getIntent().getExtras();
            alarmGroup = db.getAlarmGroup(extras.getLong("groupID"));
            db.close();
            dba.open();
            alarms = dba.getGroupAlarms(alarmGroup.getId());
            dba.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setTitle(alarmGroup.getGroupName());
        // Set alarm details
        EditText name = (EditText) findViewById(R.id.alarmName);
        name.setText(alarmGroup.getGroupName());

        Iterator iterate = alarms.iterator();
        while(iterate.hasNext()) {
            Alarm item = (Alarm) iterate.next();
            list.add(item.getAlarmTime());
            Bundle bItem = new Bundle();
            bItem.putInt("hour", item.getHour());
            bItem.putInt("minute", item.getrMinute());
            aTimes.add(bItem);
        }
        listView = (ListView) findViewById(R.id.dailyAlarmList);
        adapter = new CustomListViewAdapter(list, this);
        listView.setAdapter(adapter);

        AlarmRingtoneManager.getInstance(getApplicationContext());

        final List<Uri> toneURI_List = AlarmRingtoneManager.getURI_List();
        final List<String> toneList = AlarmRingtoneManager.getToneNameList();

        final ArrayAdapter<String> toneAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, toneList);
        Spinner ringtoneSpinner = (Spinner) findViewById(R.id.ringtoneSpinner);
        ringtoneSpinner.setAdapter(toneAdapter);

        ringtoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toneURI = toneURI_List.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * The onCreateOptionsMenu
     * @param menu takes a menu
     * @return returns true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit__daily__alarm, menu);
        return true;
    }

    /**
     * The onOptionsItemSelected
     * @param item takes an item
     * @return a super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                TextView name = (TextView) findViewById(R.id.alarmName);
                if (!name.getText().toString().isEmpty()){
                    save();
                    setResult(RESULT_OK);
                    finish();// TODO: Confirm Save
                }
                else {
                    Toast.makeText(this,"Medication name cannot be empty.",Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Updates the list
     */
    private void updateList() {
        String item = DateFormat.getTimeInstance(DateFormat.SHORT).format(dateAndTime
                .getTime());
        // TODO: Add Create a bundle and add it to instance
        Bundle bundle = new Bundle();
        bundle.putInt("hour", dateAndTime.get(Calendar.HOUR_OF_DAY));
        bundle.putInt("minute", dateAndTime.get(Calendar.MINUTE));
        aTimes.add(bundle);
        list.add(item);
        Collections.sort(list);
        adapter.notifyDataSetChanged();
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    public void onNothingSelected(AdapterView<?> parent) {
        // required interface callback.
    }

    public void timePickerClicker(View view) { //lawl
        new TimePickerDialog(Edit_Daily_Alarm.this,
                tp,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE),
                false).show();
    }

    /**
     * Go back to previous screen
     */
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    /**
     * Changes dp to px for display
     * @param dp takes a dp
     * @return pixels
     */
    private int dp2px(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

    /**
     * When the editButton is clicked
     * @param position takes the position
     */
    public void editButtonClick(int position) {
        Calendar temp = Calendar.getInstance();
        Bundle bundleTime = aTimes.get(position);
        aTimes.remove(position);
        list.remove(position);
        temp.set(Calendar.HOUR_OF_DAY, bundleTime.getInt("hour"));
        temp.set(Calendar.MINUTE, bundleTime.getInt("minute"));
        new TimePickerDialog(Edit_Daily_Alarm.this,
                tp,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE),
                false).show();
    }

    /**
     * When the delete button is clicked
     * @param position takes a position
     */
    private void deleteButtonClick(int position) {
        aTimes.remove(position);
        list.remove(position);
    }

    /**
     * When the save icon is pressed, saves information to the DB
     */
    private void save() {
        AlarmGroupDataSource save = new AlarmGroupDataSource();
        TextView name = (TextView) findViewById(R.id.alarmName);
        try {
            save.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // TODO: Ringtone needs to be set!
        if (!name.getText().toString().isEmpty()){
            new MedAlarmManager(getApplicationContext()).cancelGroup(alarmGroup.getId());

            save.deleteGroup(alarmGroup.getId());

            AlarmGroup ag = save.createAlarmGroup(name.getText().toString(), toneURI.toString(), type, false, true);
            Iterator iTimes = aTimes.iterator();

            // TODO: Might want to use a better itterator pattern so that the world is not destroyed.
            while (iTimes.hasNext()) {
                Bundle aTime = (Bundle) iTimes.next();
                int hour = aTime.getInt("hour");
                int minute = aTime.getInt("minute");
                boolean repeats = false;
                int rHours = 0;
                int rMiutes = 0;

                ag.addAlarm(hour, minute, repeats, rHours, rMiutes);
            }
            save.close();
        }
        else {
            Toast.makeText(this, "Medication name cannot be empty.", Toast.LENGTH_LONG).show();
        }
    }
}
