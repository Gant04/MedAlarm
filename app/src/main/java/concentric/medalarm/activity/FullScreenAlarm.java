package concentric.medalarm.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import concentric.medalarm.R;
import concentric.medalarm.activity.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullScreenAlarm extends Activity {

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = false;
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
    ImageView icon;
    boolean cancel;
    MediaPlayer mediaPlayer;
    Handler mHideHandler = new Handler();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_screen_alarm);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        this.setTitle("MedAlarm");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        final TextView alarmText = (TextView) findViewById(R.id.textView);
        alarmText.setText(bundle.getString("medicationName"));

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        //final ImageView logo = (ImageView) findViewById(R.id.imageView);
        //logo.setImageResource(R.drawable.medalarm_logo);


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.stop_button).setOnTouchListener(mDelayHideTouchListener);
        findViewById(R.id.snooze_button).setOnTouchListener(mDelayHideTouchListener);

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm2);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);


        AnimatePill();


    }

    public void stopClickListener(View view) {
        //TODO FINISH THIS.
        cancel = true;
        mediaPlayer.stop();
        finish();
    }

    public void snoozeClickListener(View view) {
        //TODO FINISH THIS.
        cancel = true;
        mediaPlayer.stop();
        finish();
    }

    private void AnimatePill() {

        icon = (ImageView) findViewById(R.id.fullscreen_content);
        icon.setImageResource(R.drawable.web_hi_res_512_pill);

        ObjectAnimator rotateAnimationA = ObjectAnimator.ofFloat(icon, "translationY", 0, 180);
        rotateAnimationA.setDuration(1000);
        rotateAnimationA.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator rotateAnimationB = ObjectAnimator.ofFloat(icon, "translationY", 180, 0);
        rotateAnimationB.setDuration(1000);
        rotateAnimationB.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator slightTurnA = ObjectAnimator.ofFloat(icon, "rotation", 0, 30);
        slightTurnA.setDuration(1000);

        ObjectAnimator slightTurnB = ObjectAnimator.ofFloat(icon, "rotation", 30, 0);
        slightTurnB.setDuration(1000);


        final AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.play(rotateAnimationA).with(slightTurnA);
        animatorSet.play(rotateAnimationB).with(slightTurnB);
        animatorSet.play(rotateAnimationA).before(rotateAnimationB);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                cancel = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!cancel) {
                    animation.start();
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                cancel = false;
            }
        });
        animatorSet.start();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


    @Override
    public void onBackPressed() {
        snoozeClickListener(findViewById(R.id.snooze_button));
        super.onBackPressed();
    }

}
