package concentric.medalarm.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

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
    private final int editAlarmRequestCode = 2;
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
            mRecycleAdapter = new AlarmGroupCardAdapter(MainActivity.this, alarmGroupList);
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
    public void onClickActionCreateAlarmAnim(View view) {

        int center = 0;
        int right = -45;
        int left = 45;

        final View menuButton = findViewById(R.id.actionMenu);

        ObjectAnimator rotateRight = ObjectAnimator.ofFloat(menuButton, "rotation", center, right);
        rotateRight.setInterpolator(new AccelerateInterpolator());
        rotateRight.setRepeatCount(0);
        rotateRight.setDuration(100);

        ObjectAnimator rotateLeft = ObjectAnimator.ofFloat(menuButton, "rotation", right, left);
        rotateLeft.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateLeft.setRepeatCount(0);
        rotateLeft.setDuration(200);

        ObjectAnimator rotateCenter = ObjectAnimator.ofFloat(menuButton, "rotation", left, center);
        rotateCenter.setInterpolator(new DecelerateInterpolator());
        rotateCenter.setRepeatCount(0);
        rotateCenter.setDuration(100);


        AnimatorSet buttonGroup = new AnimatorSet();
        buttonGroup.play(rotateRight).before(rotateLeft);
        buttonGroup.play(rotateLeft).before(rotateCenter);

        final View tmpView = view;

        buttonGroup.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onClickActionCreateAlarm(tmpView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        buttonGroup.start();
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.bell);
        mediaPlayer.start();
    }

    /**
     * This method is called when the create button is pressed.
     *
     * @param view The view that was clicked.
     */
    public void onClickActionCreateAlarm(View view) {
        Intent intent = new Intent(view.getContext(), AlarmGroupActivity.class);
        startActivityForResult(intent, createAlarmRequestCode);
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
                    mRecycleAdapter = new AlarmGroupCardAdapter(MainActivity.this, alarmGroupList);
                    mRecyclerView.setAdapter(mRecycleAdapter);
                }
                mRecycleAdapter.notifyDataSetChanged();
                new MedAlarmManager(getApplicationContext()).setAllAlarms();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Alarm creation cancelled.", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == editAlarmRequestCode) {
            if (resultCode == RESULT_OK) {
                loadAlarmGroups();
                if (alarmGroupList.size() > 0) {
                    mRecycleAdapter = new AlarmGroupCardAdapter(MainActivity.this, alarmGroupList);
                    mRecyclerView.setAdapter(mRecycleAdapter);
                }
                mRecycleAdapter.notifyDataSetChanged();
                new MedAlarmManager(getApplicationContext()).setAllAlarms();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Alarm edit cancelled.", Toast.LENGTH_LONG).show();
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
