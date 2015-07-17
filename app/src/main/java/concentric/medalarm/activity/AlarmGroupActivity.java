package concentric.medalarm.activity;


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
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import concentric.medalarm.CustomListViewAdapter;
import concentric.medalarm.R;
import concentric.medalarm.models.AlarmGroup;
import concentric.medalarm.models.AlarmGroupDataSource;
import concentric.medalarm.models.DBHelper;

public class AlarmGroupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private List<String> list = new ArrayList<>();
    private List<Bundle> aTimes = new ArrayList<>();
    private ListView listView;

    private CustomListViewAdapter adapter;
    private Calendar dateAndTime = Calendar.getInstance();
    private int type;

    private View view;

    private TimePickerDialog.OnTimeSetListener tp = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay,
                              int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            updateList();
        }
    };

    private int dp2px(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

    public void editButtonClick(int position) {
        Calendar temp = Calendar.getInstance();
        Bundle bundleTime = aTimes.get(position);
        aTimes.remove(position);
        list.remove(position);
        temp.set(Calendar.HOUR_OF_DAY, bundleTime.getInt("hour"));
        temp.set(Calendar.MINUTE, bundleTime.getInt("minute"));
        new TimePickerDialog(AlarmGroupActivity.this,
                tp,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE),
                false).show();
    }

    private void deleteButtonClick(int position) {
        aTimes.remove(position);
        list.remove(position);
    }

    public void expand(View view) {
        TranslateAnimation translateAnimation = null;
        translateAnimation = new TranslateAnimation(0, 0, -view.getHeight(), 0);
        view.setVisibility(View.VISIBLE);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        view.startAnimation(translateAnimation);
    }

    public void collapse(final View view) {

        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(250);

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -view.getHeight());
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new DecelerateInterpolator());

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        AnimationSet animationSet;

        animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);

        view.startAnimation(animationSet);
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

        //Stops the keyboard from popping up utill the user clicks on a text box.
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
                setResult(RESULT_OK);
                finish();// TODO: Confirm Save
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void save() {
        AlarmGroupDataSource save = new AlarmGroupDataSource();
        TextView name = (TextView) findViewById(R.id.alarmName);
        try {
            save.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // TODO: Ringtone needs to be set!
        AlarmGroup ag = save.createAlarmGroup(name.getText().toString(), "BLEH", type, false, true);
        Iterator iTimes = aTimes.iterator();

        // TODO: Might want to use a better itterator pattern so that the world is not destroyed.
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
                    adapter = new CustomListViewAdapter(list, this);
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
        new TimePickerDialog(AlarmGroupActivity.this,
                tp,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE),
                false).show();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
