package concentric.medalarm;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import concentric.medalarm.activity.Edit_Daily_Alarm;
import concentric.medalarm.models.AlarmGroup;
import concentric.medalarm.models.DBHelper;

/**
 * Created by MatthewAry on 7/9/2015.
 */
public class AlarmGroupCardAdapter extends RecyclerView.Adapter<AlarmGroupCardAdapter.ViewHolder> {
    private List<AlarmGroup> alarmGroups;
    private Context parentContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AlarmGroupCardAdapter(Context context, List<AlarmGroup> dataSet) {
        alarmGroups = dataSet;
        parentContext = context;
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
        holder.groupID = item.getId();

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
            private TableRow line = (TableRow) v.findViewById(R.id.line);
            private TableRow controls = (TableRow) v.findViewById(R.id.controls);
            private View arrow = v.findViewById(R.id.expandCollapse);


            /**
             * Called when the table Row is clicked.
             *
             * See: http://goo.gl/gR5aZH
             * Here we want to lay the ground work for the animation.
             * // TODO: Make it so that there is a real animation for the controls and line.
             * for details on how this works.
             * @param caller the View of item that was touched.
             */
            public void toggleControls(final View caller) {
                RotateAnimation rotate;
                if (!rotated) {
                    rotate = new RotateAnimation(0.0f, 180f, Animation
                            .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    line.setVisibility(View.VISIBLE);
                    controls.setVisibility(View.VISIBLE);
                    rotated = true;
                } else {
                    rotate = new RotateAnimation(180f, 0.0f, Animation
                            .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    line.setVisibility(View.GONE);
                    controls.setVisibility(View.GONE);
                    rotated = false;
                }
                rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                rotate.setDuration(200);
                rotate.setFillAfter(true);

                arrow.startAnimation(rotate);
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
        protected ImageButton arrow;
        protected ImageButton edit;
        protected ImageButton delete;
        protected ViewHolderClicks mListener;
        protected long groupID;

        public ViewHolder(View v, ViewHolderClicks listener) {
            super(v);
            mListener = listener;
            groupName = (TextView) v.findViewById(R.id.groupName);
            groupType = (TextView) v.findViewById(R.id.groupType);
            itemDetails = (TableRow) v.findViewById(R.id.cardDetails);
            arrow = (ImageButton) v.findViewById(R.id.expandCollapse);
            edit = (ImageButton) v.findViewById(R.id.editAlarm);
            delete = (ImageButton) v.findViewById(R.id.deleteAlarm);

            itemDetails.setOnClickListener(this);
            arrow.setOnClickListener(this);
            edit.setOnClickListener(this);
            delete.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cardDetails || v.getId() == R.id.expandCollapse) { // Expand
                mListener.toggleControls(v);
            } else if (v.getId() == R.id.editAlarm) { // Edit
                Intent intent = new Intent(parentContext, Edit_Daily_Alarm.class);
                intent.putExtra("groupID", groupID);
                parentContext.startActivity(intent);
            } else if (v.getId() == R.id.deleteAlarm) { // Delete

            } else if (v.getId() == R.id.enabled) { // Disable

            }
        }
    }
    public interface ViewHolderClicks {
        void toggleControls(View caller);
    }
}
