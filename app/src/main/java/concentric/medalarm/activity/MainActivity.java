package concentric.medalarm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import concentric.medalarm.R;
import concentric.medalarm.models.DBHelper;


public class MainActivity extends AppCompatActivity {

    private boolean menuClicked = false;
    private boolean alarmSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper.getInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
     *
     * @param v the view.
     */
    public void onClickActionCreateAlarm(View v) {

        Intent intent = new Intent(v.getContext(), AlarmActivity.class);
        startActivity(intent);
    }

    public void onClickActionMenu(View view) {

        final View createButton = findViewById(R.id.actionCreate);
        final View deleteButton = findViewById(R.id.actionDelete);

        Thread createAlarmButton = new Thread( new Runnable() {
            @Override
            public void run() {
                createButton.setVisibility(View.VISIBLE);
                createButton.setAlpha((float) 0);
                createButton.setTranslationY(150);
                createButton.animate().translationY(0).alpha(1).setDuration(500).start();
                menuClicked = true;
            }
        });

        Thread createDeleteButton = new Thread(new Runnable() {
            @Override
            public void run() {
                deleteButton.setVisibility(View.VISIBLE);
                deleteButton.setAlpha(0);
                deleteButton.setTranslationY(300);
                deleteButton.animate().translationY(0).alpha(1).setDuration(750).start();
            }
        });

        if (!menuClicked) {
            runOnUiThread(createAlarmButton);
            try {
                createAlarmButton.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(alarmSelected){
                runOnUiThread(createDeleteButton);
                try {
                    createDeleteButton.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } else {
            deleteButton.setVisibility(View.GONE);
            createButton.setVisibility(View.GONE);
            menuClicked = false;
        }
    }

    public void onClickActionDeleteAlarm(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
