package concentric.medalarm.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import concentric.medalarm.R;
import concentric.medalarm.TimeConverter;
import concentric.medalarm.models.DBHelper;

//TODO We should go through and check to see which of the classes we actually need/use and remove those that aren't being used.
//TODO We should also remove any unused variables and methods if possible.

public class MainActivity extends AppCompatActivity {

    private final int createAlarmRequestCode = 1;
    AlarmManager alarmManager;
    private ListView alarmList;
    private List<String> medList;
    private ArrayAdapter<String> listAdapter;
    private boolean alarmSelected = false;
    private boolean menuClicked = false;
    private boolean soundLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper.getInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        medList = new ArrayList<>();
        alarmList = (ListView) findViewById(R.id.listView);
        alarmList.setSelector(R.color.colorPrimary);

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, medList);
        alarmList.setAdapter(listAdapter);

        alarmListOnClickListener();
        menuButtonOnLongClickListener();


/*        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("First Item"),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Second Item")
                )
                *//*
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                    }
                })*//*
                .build();

//use the result object to get different views of the drawer or modify it's data
//some sample calls
        result.setSelectionByIdentifier(1);
        result.openDrawer();
        result.closeDrawer();
        result.isDrawerOpen();*/
    }


    /**
     * Click listener for the AlarmList
     */
    private void alarmListOnClickListener() {
        // listens for when the list is clicked.
        alarmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO what happens when the list item is clicked?
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + id, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void menuButtonOnLongClickListener() {
        //Theres no where else for this to go. SINCE THERE IS NO onLongClick in xml.
        final View menuButton = findViewById(R.id.actionMenu);
        menuButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                alarmSelected = !alarmSelected;
                return true;
            }
        });
    }

    /**
     * This is the on click method for the floating action menu button
     *
     * @param view The view that was clicked.
     */
    public void onClickActionMenu(View view) {

        int translationA = -165;
        int translationB = -135;

        int rotationBegin = 0;
        int rotationEnd = 45;

        if (menuClicked) {
            translationA = 0;
            translationB = 0;
            int tmp = rotationBegin;
            rotationBegin = rotationEnd;
            rotationEnd = tmp;
        }

        final View createButton = findViewById(R.id.actionCreate);
        final View deleteButton = findViewById(R.id.actionDelete);
        final View editButton = findViewById(R.id.actionEdit);
        final View menuButton = findViewById(R.id.actionMenu);

        ObjectAnimator menuAnimator = ObjectAnimator.ofFloat(menuButton, "rotation", rotationBegin, rotationEnd);
        menuAnimator.setInterpolator(new DecelerateInterpolator());
        menuAnimator.setRepeatCount(0);
        menuAnimator.setDuration(200);

        ObjectAnimator createAnimator = ObjectAnimator.ofFloat(createButton, "translationY", translationA);
        createAnimator.setInterpolator(new DecelerateInterpolator());
        createAnimator.setRepeatCount(0);
        createAnimator.setDuration(200);

        ObjectAnimator deleteAnimator = ObjectAnimator.ofFloat(deleteButton, "translationX", translationA);
        deleteAnimator.setInterpolator(new DecelerateInterpolator());
        deleteAnimator.setRepeatCount(0);
        deleteAnimator.setDuration(200);

        ObjectAnimator editAnimator1 = ObjectAnimator.ofFloat(editButton, "translationX", translationB);
        editAnimator1.setInterpolator(new DecelerateInterpolator());
        editAnimator1.setRepeatCount(0);
        editAnimator1.setDuration(200);

        ObjectAnimator editAnimator2 = ObjectAnimator.ofFloat(editButton, "translationY", translationB);
        editAnimator2.setInterpolator(new DecelerateInterpolator());
        editAnimator2.setRepeatCount(0);
        editAnimator2.setDuration(200);

        if (alarmSelected) {
            createButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.VISIBLE);
        } else {
            createButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
            editButton.setVisibility(View.GONE);
        }

        AnimatorSet editGroup = new AnimatorSet();
        editGroup.play(editAnimator1).with(editAnimator2);

        AnimatorSet buttonGroup = new AnimatorSet();
        buttonGroup.play(menuAnimator).before(createAnimator);
        buttonGroup.play(createAnimator).before(deleteAnimator);
        buttonGroup.play(deleteAnimator).before(editGroup);
        buttonGroup.start();

        if (!alarmSelected) {
            deleteButton.setVisibility(View.GONE);
            editButton.setVisibility(View.GONE);
        }

        menuClicked = !menuClicked;

    }

    /**
     * This method is called when the delete button is pressed.
     *
     * @param view The view that was clicked.
     */
    public void onClickActionDeleteAlarm(View view) {
        //TODO Fix this.
    }

    /**
     * This method is called when the edit button is pressed.
     *
     * @param view The view that was clicked.
     */
    public void onClickActionEditAlarm(View view) {
        //TODO Fix this.
        Intent intent = new Intent(view.getContext(), AlarmActivity.class);
        startActivityForResult(intent, createAlarmRequestCode);
        onClickActionMenu(view);
    }

    /**
     * This method is called when the create button is pressed.
     *
     * @param view The view that was clicked.
     */
    public void onClickActionCreateAlarm(View view) {

        Intent intent = new Intent(view.getContext(), AlarmGroupActivity.class);
        startActivity(intent);
        //Intent intent = new Intent(view.getContext(), AlarmActivity.class);
        //startActivityForResult(intent, createAlarmRequestCode);
        onClickActionMenu(view);
    }

    /**
     *
     * @param menu The menu that was clicked.
     * @return returns a boolean set to true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /**
     * This is a method that is overridden so that we can intercept the activity result
     *
     * @param requestCode an int.
     * @param resultCode  the result code which is either RESULT_OK or RESULT_CANCELED
     * @param intent      the resulting intent that is passed from the completed activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == createAlarmRequestCode) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = intent.getExtras();
                buildStoreAndDisplayAlarm(bundle);

            }
            if (resultCode == RESULT_CANCELED) {

                if (alarmList.isSelected()) {
                    Toast.makeText(getApplicationContext(), "Edits to: " + alarmList.getSelectedItem().toString() + " were canceled.", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(MainActivity.this, "Alarm creation canceled.", Toast.LENGTH_SHORT).show();

                }
            }
        }

    }

    /**
     * The workhorse of the app, does most of the stuff for the actual building of alarms.
     * @param bundle takes a bundle from the onActivityResult, but can be used by a database as well.
     * TODO Move buildAndStoreAndDisplay into its own class. Somehow, eventually.
     */
    private void buildStoreAndDisplayAlarm(final Bundle bundle) {
        alarmList.clearChoices();
        final String med = bundle.getString("medicationName");
        Toast.makeText(getApplicationContext(), "Alarm created: " + med + ".", Toast.LENGTH_SHORT).show();
        medList.add(med);
        listAdapter.notifyDataSetChanged();

        //this is here for the time being.
        int hour = bundle.getInt("hour");
        int minute = bundle.getInt("minute");

        Long longHours = TimeConverter.hoursToMillis(hour);
        Long longMinutes = TimeConverter.minutesToMillis(minute);

        Long longTime = longHours + longMinutes;

        //Calculate difference between current time and future time AKA longtime

        long diffTime = System.currentTimeMillis() + longTime;
        Log.d("Time Difference: ", Long.toString(diffTime));

        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        final float volume = actVolume / maxVolume;

        /* TODO This should be placed in a different class - here because fast and quick*/
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Intent alarmIntent = new Intent();
                //alarmIntent.setClassName("concentric.medalarm.activity",FullScreenAlarm.class);
                //alarmIntent.putExtras(bundle);
                //alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(alarmIntent);
                //Toast.makeText(context, "Alarm: " + med + " is going off.", Toast.LENGTH_LONG).show();
                //TODO Replace the things here with an actual activity instead of this garbage.
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter("com.concentric.alarmIntent." + med));
        createAlarm(bundle);
    }

    /**
     * This method should only be called from the buildStoreAndDisplayMethod.
     * It builds the actual alarm.
     * TODO This should be moved into a different class with the build and store method from above.
     */
    private void createAlarm(Bundle bundle) {
        final String med = bundle.getString("medicationName");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        //this is here for the time being.
        int hour = bundle.getInt("hour");
        int minute = bundle.getInt("minute");

        Long longHours = TimeConverter.hoursToMillis(hour);
        Long longMinutes = TimeConverter.minutesToMillis(minute);

        Long longTime = longHours + longMinutes;

        //Calculate difference between current time and future time AKA difference
        // TODO: Time is depreciated. Change to something else.
        Time time = new Time();
        time.setToNow();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        final long difference = calendar.getTimeInMillis() - time.toMillis(true);
        Log.d("Time Difference:    ", Long.toString(difference));


        if (calendar.getTimeInMillis() < time.toMillis(true)) {
            calendar.set(Calendar.DAY_OF_YEAR, Calendar.DAY_OF_YEAR + 1);
        }


        /*
        new Intent("com.concentric.alarmIntent." + med)
        this allows to generate new intents on the fly.
        */

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("com.concentric.alarmIntent." + med), 0);
        alarmManager = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     *
     * @param item
     * @return true unless id != action_settings.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
