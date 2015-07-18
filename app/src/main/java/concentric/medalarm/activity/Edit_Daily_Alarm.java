package concentric.medalarm.activity;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import concentric.medalarm.CustomListViewAdapter;
import concentric.medalarm.R;
import concentric.medalarm.models.Alarm;
import concentric.medalarm.models.AlarmDataSource;
import concentric.medalarm.models.AlarmGroup;
import concentric.medalarm.models.AlarmGroupDataSource;

public class Edit_Daily_Alarm extends AppCompatActivity {
    private AlarmGroup alarmGroup;
    private List<Alarm> alarms;
    private ListView listView;
    private CustomListViewAdapter adapter;
    private Calendar dateAndTime = Calendar.getInstance();

    private TimePickerDialog.OnTimeSetListener tp = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay,
                              int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
           // updateList();
        }
    };
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

        // Set alarm details
        EditText name = (EditText) findViewById(R.id.alarmName);
        name.setText(alarmGroup.getGroupName());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit__daily__alarm, menu);
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
