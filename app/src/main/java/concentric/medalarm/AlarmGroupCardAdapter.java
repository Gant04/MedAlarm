package concentric.medalarm;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import concentric.medalarm.activity.Edit_Daily_Alarm;
import concentric.medalarm.models.AlarmGroup;
import concentric.medalarm.models.AlarmGroupDataSource;
import concentric.medalarm.models.DBHelper;

/**
 * Created by MatthewAry on 7/9/2015.
 */
public class AlarmGroupCardAdapter extends RecyclerView.Adapter<AlarmGroupCardAdapter.ViewHolder> {
    private List<AlarmGroup> alarmGroups;
    private Context parentContext;
    private AlarmGroupCardAdapter adapter = this;

    /**
     * AlarmGroupCardAdapter constructor
     *
     * @param context takes a context
     * @param dataSet takes a dataSet
     */
    public AlarmGroupCardAdapter(Context context, List<AlarmGroup> dataSet) {
        alarmGroups = dataSet;
        parentContext = context;
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     *
     * @return the size of the alarmGroup
     */
    @Override
    public int getItemCount() {
        return alarmGroups.size();
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     *
     * @param holder takes a ViewHolder
     * @param position takes the position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        AlarmGroup item = alarmGroups.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.groupName.setText(item.getGroupName());
        holder.groupID = item.getId();
        holder.position = position;
        holder.aSwitch.setEnabled(item.getEnabled());

        String alarmType[] = DBHelper.getContext().getResources().getStringArray(R.array
                .alarm_types);

        //holder.groupType.setText(alarmType[item.getAlarmType()]);
        // TODO: Fix this hack
        holder.groupType.setText("Daily");
    }

    /**
     * Create new views (invoked by the layout manager)
     *
     * @param parent takes the ViewGroup
     * @param viewType takes the viewType
     * @return the ViewHolder
     */
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
            public void toggleControls(View caller) {
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

            /**
             * Sends user to the edit alarm activity.
             * @param groupID
             */
            public void editAlarm(long groupID) {
                Intent intent = new Intent(parentContext, Edit_Daily_Alarm.class);
                intent.putExtra("groupID", groupID);
                ((Activity) parentContext).startActivityForResult(intent, 2);
            }

            /**
             * Disables and deletes the alarm group.
             * @param groupID
             */
            public void deleteAlarm(long groupID, int position) {
                MedAlarmManager ma = new MedAlarmManager(parentContext.getApplicationContext());
                ma.cancelGroup(groupID);

                // delete from DB
                AlarmGroupDataSource group = new AlarmGroupDataSource();
                try {
                    group.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                group.deleteGroup(groupID);
                group.close();

                // updating UI
                adapter.notifyDataSetChanged();
                alarmGroups.remove(position);
                adapter.notifyItemRemoved(position);

                // collapse card drop down
                line.setVisibility(View.GONE);
                controls.setVisibility(View.GONE);
                rotated = false;

                // notify user
                Toast.makeText(parentContext, "Alarm deleted", Toast.LENGTH_SHORT).show();
            }

            /**
             * Disables the alarm group.
             * @param groupID
             */
            public void disableAlarm(long groupID) {
                MedAlarmManager ma = new MedAlarmManager(parentContext.getApplicationContext());
                ma.cancelGroup(groupID);
                AlarmGroupDataSource db = new AlarmGroupDataSource();
                try {
                    db.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                AlarmGroup item = db.getAlarmGroup(groupID);
                item.setEnabled(false);
                db.updateAlarmGroup(item);
            }
        });
        return vh;
    }

    /**
     * View holder clicks
     */
    public interface ViewHolderClicks {
        void toggleControls(View caller);

        void editAlarm(long groupID);

        void deleteAlarm(long groupID, int position);

        void disableAlarm(long groupID);
    }

    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView groupName;
        protected TextView groupType;
        protected TableRow itemDetails;
        protected ImageButton arrow;
        protected ImageButton edit;
        protected ImageButton delete;
        protected ViewHolderClicks mListener;
        protected Switch aSwitch;
        protected long groupID;
        protected int position;

        /**
         * The holder that has the view
         *
         * @param v        View
         * @param listener ViewHolderClicks
         */
        public ViewHolder(View v, ViewHolderClicks listener) {
            super(v);
            mListener = listener;
            groupName = (TextView) v.findViewById(R.id.groupName);
            groupType = (TextView) v.findViewById(R.id.groupType);
            itemDetails = (TableRow) v.findViewById(R.id.cardDetails);
            arrow = (ImageButton) v.findViewById(R.id.expandCollapse);
            edit = (ImageButton) v.findViewById(R.id.editAlarm);
            delete = (ImageButton) v.findViewById(R.id.deleteAlarm);
            aSwitch = (Switch) v.findViewById(R.id.enabled);

            itemDetails.setOnClickListener(this);
            arrow.setOnClickListener(this);
            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mListener.disableAlarm(groupID);
                    if (isChecked) {
                        Toast.makeText(parentContext, "Alarm Enabled", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(parentContext, "Alarm Disabled", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        /**
         * Registers the onClick for the main activity cards
         *
         * @param v takes a view
         */
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cardDetails || v.getId() == R.id.expandCollapse) { // Expand
                mListener.toggleControls(v);
            } else if (v.getId() == R.id.editAlarm) { // Edit
                mListener.editAlarm(groupID);
            } else if (v.getId() == R.id.deleteAlarm) { // Delete
                mListener.deleteAlarm(groupID, position);
            } else if (v.getId() == R.id.enabled) { // Disable
                mListener.disableAlarm(groupID);
            }
        }
    }
}
