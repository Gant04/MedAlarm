package concentric.medalarm.activity;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import concentric.medalarm.R;
import concentric.medalarm.models.AlarmGroup;
import concentric.medalarm.models.AlarmGroupDataSource;
import concentric.medalarm.models.DBHelper;

public class AlarmGroupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private List<String> list = new ArrayList<>();
    private List<Bundle> aTimes = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Calendar dateAndTime = Calendar.getInstance();
    private int type;
    private TimePickerDialog.OnTimeSetListener tp = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay,
                              int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            updateList();
        }
    };

    public static void expand(View view) {

        int duration = 500; //is in ms

        view.setVisibility(View.VISIBLE);
        ObjectAnimator slide = ObjectAnimator.ofFloat(view, "translationY", -800, 0);
        slide.setInterpolator(new DecelerateInterpolator());
        slide.setDuration(duration);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0, 1);
        alpha.setDuration(duration);

        AnimatorSet set = new AnimatorSet();
        set.play(alpha).with(slide);
        set.start();
    }

    public static void collapse(final View view) {

        int duration = 500; //is in ms

        ObjectAnimator slide = ObjectAnimator.ofFloat(view, "translationY", 0, -800);
        slide.setInterpolator(new DecelerateInterpolator());
        slide.setDuration(duration);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 1, 0);
        alpha.setDuration(duration);

        AnimatorSet set = new AnimatorSet();
        set.play(alpha).with(slide);

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        set.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_group);

        // Activate Singleton
        DBHelper.getInstance(getApplicationContext());

        // Get Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.AlarmGroupToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Populate Spinner
        AppCompatSpinner spinner = (AppCompatSpinner) findViewById(R.id.AlarmType);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array
                .alarm_types, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Time Picker Listeners
        android.support.design.widget.FloatingActionButton dSetTime = (android.support.design
                .widget.FloatingActionButton) findViewById(R.id.addTime);
        dSetTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new TimePickerDialog(AlarmGroupActivity.this,
                        tp,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE),
                        false).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflated = getMenuInflater();
        inflated.inflate(R.menu.menu_alarm_group, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This hook is called whenever an item in your options menu is selected. The default
     * implementation simply returns false to have the normal processing happen (calling the
     * item's Runnable or sending a message to its Handler as appropriate). You can use this
     * method for any items for which you would like to do processing without those other
     * facilities.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                save();
                // TODO: Confirm Save
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO: PERFORM ALARM SAVE OPERATION HERE
    private void save() {
        AlarmGroupDataSource save = new AlarmGroupDataSource();
        TextView name = (TextView) findViewById(R.id.alarmName);
        try {
            save.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AlarmGroup ag = save.createAlarmGroup(name.getText().toString(), "BLEH", type, false, true);
        // TODO: Use a loop to insert alarms into the ag object.
        Iterator iTimes = aTimes.iterator();
        while (iTimes.hasNext()) {
            Bundle aTime = (Bundle) iTimes.next();
            int hour = aTime.getInt("hour");
            int minute = aTime.getInt("minute");
            boolean repeats = false;
            int rHours = 0;
            int rMiutes = 0;
            if (type == 1) {
                repeats = true;
                rHours = 24;
                rMiutes = 0;
            }
            ag.addAlarm(hour, minute, repeats, rHours, rMiutes);
        }
        save.close();
    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p/>
     * Impelmenters can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     * <p/>
     * <p>Used in this case for when an item in the spinners have been selected</p>
     *
     * @param parent The AdapterView where the selection happened
     * @param view   The view within the AdapterView that was clicked
     * @param pos    The position of the view in the adapter
     * @param id     The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        type = pos;
        String[] types = getResources().getStringArray(R.array.alarm_types);
        // Test which spinner has been toggled.
        if (types[pos].equals(parent.getItemAtPosition(pos))) { // Type Spinner
            Log.i(getClass().getName() + " onItemSelected", types[pos] + " was " +
                    "selected.");
            List<View> views = new ArrayList<>();
            views.add(findViewById(R.id.DailyAlarms));
            // views.add();
            switch (pos) {
                case 1:
                    // TODO: Handle the selection of Daily
                    hideVisibleViews(views);
                    expand(views.get(0));
                    listView = (ListView) findViewById(R.id.dailyAlarmList);
                    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                            list);
                    listView.setAdapter(adapter);
                    break;
                case 2:
                    // TODO: Handle the selection of Weekly
                    hideVisibleViews(views);
                    break;
                case 3:
                    // TODO: Handle the selection of Intervals
                    hideVisibleViews(views);
                    break;
                case 4:
                    // TODO: Handle the selection of Only Once
                    hideVisibleViews(views);
                    break;
                default:
                    hideVisibleViews(views);

            }
        }
    }

    private void hideVisibleViews(List list) {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            View view = (View) iterator.next();
            if (view.getVisibility() == View.VISIBLE) collapse(view);
        }
    }

    private void updateList() {
        String item = DateFormat.getTimeInstance(DateFormat.SHORT).format(dateAndTime
                .getTime());
        // TODO: Add Create a bundle and add it to instance
        Bundle bundle = new Bundle();
        bundle.putInt("hour", dateAndTime.get(Calendar.HOUR_OF_DAY));
        bundle.putInt("minute", dateAndTime.get(Calendar.MINUTE));
        aTimes.add(bundle);
        list.add(item);
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
}
