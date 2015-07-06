package concentric.medalarm.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import concentric.medalarm.R;
import concentric.medalarm.models.DBHelper;


public class MainActivity extends AppCompatActivity {

    private final int createAlarmRequestCode = 1;
    private ListView alarmList;
    private List<String> medList;
    private ArrayAdapter<String> listAdapter;


    private boolean alarmSelected = true;
    private boolean menuClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper.getInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        medList = new ArrayList<>();
        alarmList = (ListView) findViewById(R.id.listView);

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

        ObjectAnimator menuAnimator = ObjectAnimator.ofFloat(menuButton, "rotation", rotationBegin, rotationEnd * 3);
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

        if (alarmSelected) {
            deleteButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.VISIBLE);
        }

        AnimatorSet editGroup = new AnimatorSet();
        editGroup.play(editAnimator1).with(editAnimator2);

        AnimatorSet buttonGroup = new AnimatorSet();
        buttonGroup.play(menuAnimator).before(createAnimator);
        buttonGroup.play(createAnimator).before(deleteAnimator);
        buttonGroup.play(deleteAnimator).before(editGroup);
        buttonGroup.start();

        menuClicked = !menuClicked;

    }

    public void onClickActionDeleteAlarm(View view) {

    }

    public void onClickActionEditAlarm(View view) {

    }

    public void onClickActionCreateAlarm(View v) {
        Intent intent = new Intent(v.getContext(), AlarmActivity.class);
        startActivityForResult(intent, createAlarmRequestCode);
        onClickActionMenu(v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == createAlarmRequestCode) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = intent.getExtras();
                buildStoreAndDisplayAlarm(bundle);

            }
            if (resultCode == RESULT_CANCELED) {

                if (alarmList.isSelected()) {
                    Toast.makeText(getApplicationContext(), "Edits to: " + alarmList.getSelectedItem().toString() + ".", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(MainActivity.this, "Alarm creation canceled.", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void buildStoreAndDisplayAlarm(Bundle bundle) {
        String med = bundle.getString("medicationName");
        Toast.makeText(getApplicationContext(), "Alarm created: " + med + ".", Toast.LENGTH_SHORT).show();
        medList.add(med);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, medList);
        alarmList.setAdapter(listAdapter);

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
