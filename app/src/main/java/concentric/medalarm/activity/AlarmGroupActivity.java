package concentric.medalarm.activity;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import concentric.medalarm.AlarmTimePickerDialogFragment;
import concentric.medalarm.R;

public class AlarmGroupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_group);

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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_group, menu);
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

    public static void expand(final View v) {
/*        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;*/
        v.setVisibility(View.VISIBLE);
        /*Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics()
                .density) / 8);
        v.startAnimation(a);*/
    }

    public static void collapse(final View v) {
/*        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);*/
        v.setVisibility(View.GONE);
    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     *
     * Impelmenters can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * <p>Used in this case for when an item in the spinners have been selected</p>
     * @param parent The AdapterView where the selection happened
     * @param view The view within the AdapterView that was clicked
     * @param pos The position of the view in the adapter
     * @param id The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
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

    public void hideVisibleViews(List list) {
        Iterator iterator = list.iterator();
        while(iterator.hasNext()) {
            View view = (View) iterator.next();
            if (view.getVisibility() == View.VISIBLE) collapse(view);
        }
    }


    /**
     * Sets the alarm from the user input.
     * @param view takes a view.
     * @return Returns a Bundle
     * TODO: Break this out into a Utility
     */
    public Bundle onSetAlarmTime(View view) {
        Bundle bundle = new Bundle();


        AlarmTimePickerDialogFragment timePickerDialogFragment = new AlarmTimePickerDialogFragment();
        timePickerDialogFragment.setHandler(new Handler() {

        });
        timePickerDialogFragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(timePickerDialogFragment, "time_picker");
        fragmentTransaction.commit();

        //bundle.putInt("set_hour", hour);
        //bundle.putInt("set_minute", minute);
        return bundle;
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
