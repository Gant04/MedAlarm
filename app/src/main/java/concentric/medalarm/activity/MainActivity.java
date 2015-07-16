package concentric.medalarm.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.sql.SQLException;
import java.util.List;

import concentric.medalarm.AlarmGroupCardAdapter;
import concentric.medalarm.MedAlarmManager;
import concentric.medalarm.R;
import concentric.medalarm.models.AlarmGroup;
import concentric.medalarm.models.AlarmGroupDataSource;
import concentric.medalarm.models.DBHelper;

//TODO We should go through and check to see which of the classes we actually need/use and remove those that aren't being used.
//TODO We should also remove any unused variables and methods if possible.

public class MainActivity extends AppCompatActivity {

    private final int createAlarmRequestCode = 1;
    // List of Alarm Groups
    List<AlarmGroup> alarmGroupList;
    // RecyclerView Implementation
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecycleAdapter;
    private RecyclerView.LayoutManager mRecycleLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DB Singleton
        DBHelper.getInstance(getApplicationContext());

        // Grab Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup Recycler View
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleAlarmList);
        mRecyclerView.setHasFixedSize(true);

        // Layout Managers
        mRecycleLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRecycleLayoutManager);


        // Adapter for Recycler View
        loadAlarmGroups();
        if (alarmGroupList.size() > 0) {
            mRecycleAdapter = new AlarmGroupCardAdapter(alarmGroupList);
            mRecyclerView.setAdapter(mRecycleAdapter);
            new MedAlarmManager(getApplicationContext()).setAllAlarms();
        }
    }

    private void loadAlarmGroups() {
        // Query the database for all existing alarm groups.
        AlarmGroupDataSource dataSource = new AlarmGroupDataSource();
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        alarmGroupList = dataSource.getAllAlarmGroups();
        dataSource.close();

        // TODO: Link Each AlarmGroup item in the list to AlarmGroup Edit activity.
        // TODO: Add a enabled/disabled toggle to each item on the list.
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

        createButton.setVisibility(View.VISIBLE);

        AnimatorSet editGroup = new AnimatorSet();
        editGroup.play(editAnimator1).with(editAnimator2);

        AnimatorSet buttonGroup = new AnimatorSet();
        buttonGroup.play(menuAnimator).before(createAnimator);
        buttonGroup.play(createAnimator).before(deleteAnimator);
        buttonGroup.play(deleteAnimator).before(editGroup);
        buttonGroup.start();
    }

    /**
     * This method is called when the create button is pressed.
     *
     * @param view The view that was clicked.
     */
    public void onClickActionCreateAlarm(View view) {
        Intent intent = new Intent(view.getContext(), AlarmGroupActivity.class);
        startActivityForResult(intent, createAlarmRequestCode);
        onClickActionMenu(view);
    }

    /**
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

                loadAlarmGroups();
                if (alarmGroupList.size() > 0) {
                    mRecycleAdapter = new AlarmGroupCardAdapter(alarmGroupList);
                    mRecyclerView.setAdapter(mRecycleAdapter);
                }
                mRecycleAdapter.notifyDataSetChanged();
                new MedAlarmManager(getApplicationContext()).setAllAlarms();
            }
            if (resultCode == RESULT_CANCELED) {

                }
            }
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
