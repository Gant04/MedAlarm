package concentric.medalarm;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import concentric.medalarm.models.AlarmGroup;
import concentric.medalarm.models.DBHelper;

/**
 * Created by MatthewAry on 7/9/2015.
 */
public class AlarmGroupCardAdapter extends RecyclerView.Adapter<AlarmGroupCardAdapter.ViewHolder> {
    private List<AlarmGroup> alarmGroups;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AlarmGroupCardAdapter(List<AlarmGroup> dataSet) {
        alarmGroups = dataSet;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return alarmGroups.size();
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AlarmGroup item = alarmGroups.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.groupName.setText(item.getGroupName());

        String alarmType[] = DBHelper.getContext().getResources().getStringArray(R.array
                .alarm_types);

        holder.groupType.setText(alarmType[item.getAlarmType()]);

    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlarmGroupCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_group_card, parent, false);
        // Optionally set the view's size, margins, paddings and layout parameters below

        AlarmGroupCardAdapter.ViewHolder vh = new ViewHolder(v, new AlarmGroupCardAdapter
                .ViewHolderClicks() {
            private boolean rotated = false;
            private View line = v.findViewById(R.id.line);
            private View controls = v.findViewById(R.id.controls);
            private View arrow = v.findViewById(R.id.expandCollapse);

            /**
             * Called when the table Row is clicked.
             *
             * See: http://goo.gl/gR5aZH
             * See:
             * for details on how this works.
             * @param caller the View of item that was touched.
             */
            public void toggleControls(View caller) {
                ObjectAnimator cheveron = null;
                ObjectAnimator aniControls = null;
                ObjectAnimator aniLine = null;
                float lineHight = (float) line.getHeight();
                float controlHeight = (float) controls.getHeight();

                if (!rotated) {
                    cheveron = ObjectAnimator.ofFloat(arrow, "rotation", 0.0f, 180f);
                    aniControls = ObjectAnimator.ofFloat(controls, "translationY", controlHeight, 0.0f);
                    aniLine = ObjectAnimator.ofFloat(line, "translationY", lineHight, 0.0f);
                    rotated = true;
                } else {
                    cheveron = ObjectAnimator.ofFloat(arrow, "rotation", 180f, 0.0f);
                    aniControls = ObjectAnimator.ofFloat(controls, "translationY", 0.0f, controlHeight);
                    aniLine = ObjectAnimator.ofFloat(line, "translationY", 0.0f, lineHight);
                    rotated = false;
                }

                aniControls.setDuration(300);
                aniControls.setInterpolator(new AccelerateDecelerateInterpolator());
                aniLine.setDuration(100);
                aniLine.setInterpolator(new LinearInterpolator());
                cheveron.setDuration(200);
                cheveron.setInterpolator(new AccelerateDecelerateInterpolator());

                ImageButton button = (ImageButton) caller.findViewById(R.id.expandCollapse);

                AnimatorSet s = new AnimatorSet();
                cheveron.setRepeatCount(0);
                aniControls.setRepeatCount(0);
                aniLine.setRepeatCount(0);

                s.play(cheveron).with(aniLine).with(aniControls);
                s.start();


                Log.d(getClass().getName() + " toggleControls", "Should have animated.");
            }
        });
        return vh;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView groupName;
        protected TextView groupType;
        protected TableRow itemDetails;
        protected ViewHolderClicks mListener;

        public ViewHolder(View v, ViewHolderClicks listener) {
            super(v);
            mListener = listener;
            groupName = (TextView) v.findViewById(R.id.groupName);
            groupType = (TextView) v.findViewById(R.id.groupType);
            itemDetails = (TableRow) v.findViewById(R.id.cardDetails);

            itemDetails.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cardDetails) {
                mListener.toggleControls(v);
            }
        }
    }
    public interface ViewHolderClicks {
        void toggleControls(View caller);
    }
}
