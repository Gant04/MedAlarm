package concentric.medalarm.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import concentric.medalarm.AlarmBootReceiver;
import concentric.medalarm.AlarmRingtoneManager;
import concentric.medalarm.R;
import concentric.medalarm.activity.util.SystemUiHider;
import concentric.medalarm.models.DBHelper;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MedAlarm extends Activity {

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;
    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
    Boolean animationComplete = false;
    Boolean ringtonesLoaded = false;
    Boolean bootRegistered = false;
    Boolean dbLoaded = false;
    Handler mHideHandler = new Handler();
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * On Create
     *
     * @param savedInstanceState this is the saved Instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_med_alarm);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        registerBootReceiver();
        initRingtones();
        rotateAnimation();
        regesterDBhelper();

    }

    /**
     * This private method registers and enables the AlarmBootReceiver with the android system
     * it also prevents the receiver from being killed. This is all done through a seperate thread.
     */
    private void registerBootReceiver() {
        Thread bootReceiver = new Thread() {
            @Override
            public void run() {
                ComponentName receiver = new ComponentName(getApplicationContext(), AlarmBootReceiver.class);
                PackageManager pm = getApplicationContext().getPackageManager();

                pm.setComponentEnabledSetting(receiver,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP);
                bootRegistered = true;
            }
        };

        bootReceiver.start();
    }

    /**
     * This private method initalizes and starts up the database through a thread.
     */
    private void regesterDBhelper() {
        Thread dbHelper = new Thread() {
            @Override
            public void run() {
                DBHelper dbInstance = DBHelper.getInstance(getApplicationContext());
                dbLoaded = true;
            }
        };
        dbHelper.start();
    }

    /**
     * This private method calls and Initalizes the AlarmRingtoneManager Singleton
     * through a seperate thread.
     */
    private void initRingtones() {
        Thread ringtoneLoader = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                AlarmRingtoneManager.getInstance(getApplicationContext());
                ringtonesLoaded = true;
            }
        };

        ringtoneLoader.start();
    }

    /**
     * This private method completes the rotation animation for the application boot.
     * This is completed in a thread.
     */
    private void rotateAnimation() {
        Thread pillAnimationThread = new Thread() {
            @Override
            public void run() {

                final RotateAnimation rotateAnimation;
                rotateAnimation = new RotateAnimation(0.0f, -3f * 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(1000);
                rotateAnimation.setRepeatCount(0);

                final ImageView icon = (ImageView) findViewById(R.id.fullscreen_content);
                icon.setImageResource(R.drawable.web_hi_res_512_pill);

                final ImageView logo = (ImageView) findViewById(R.id.imageView);
                logo.setImageResource(R.drawable.medalarm_logo);

                rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        animationComplete = true;

                        if (bootRegistered && animationComplete && ringtonesLoaded && dbLoaded) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            icon.startAnimation(rotateAnimation);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                icon.startAnimation(rotateAnimation);

            }
        };
        pillAnimationThread.start();
    }

    /**
     * This runs after the onCreate
     *
     * @param savedInstanceState the saved instance state.
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        //delayedHide(100);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
