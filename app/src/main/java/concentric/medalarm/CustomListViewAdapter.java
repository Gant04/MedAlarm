package concentric.medalarm;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by mike on 7/16/15.
 */
public class CustomListViewAdapter extends BaseAdapter implements ListAdapter {
    Bundle theBundle = new Bundle();

    private Calendar dateAndTime = Calendar.getInstance();
    private List<String> list = new ArrayList<>();
    private List<Boolean> booleanList;
    private List<View> viewList;
    private Context context;
    private TimePickerDialog.OnTimeSetListener tp = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay,
                              int minute) {
            Log.d(getClass().getName() + " onTimeSet", "setting the time I think");
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            //theBundle.putInt("hour", dateAndTime.get(Calendar.HOUR_OF_DAY));
            //theBundle.putInt("minute", dateAndTime.get(Calendar.MINUTE));
            updateList();
        }
    };

    public CustomListViewAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        this.viewList = new ArrayList<>(list.size());
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *ma
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (list.size() > viewList.size()) {
            List<View> viewList = new ArrayList<>(list.size());
            for (View aView : this.viewList) {
                viewList.add(aView);
            }
            this.viewList = viewList;
        }

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list_view, null);
        }

        TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));
        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.delete_btn);
        ImageButton editBtn = (ImageButton) view.findViewById(R.id.edit_btn);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (View aView : viewList) {
                    if (!v.equals(aView)) {
                        buttonAnimOut(aView.findViewById(R.id.delete_btn), aView.findViewById(R.id.edit_btn));
                    }
                }
                buttonAnimIn(v.findViewById(R.id.delete_btn), v.findViewById(R.id.edit_btn));
            }
        });

        for (View aView : viewList) {
            if (!view.equals(aView)) {
                aView.findViewById(R.id.delete_btn).setVisibility(View.INVISIBLE);
                aView.findViewById(R.id.edit_btn).setVisibility(View.INVISIBLE);
            }
        }

        view.findViewById(R.id.delete_btn).setVisibility(View.VISIBLE);
        view.findViewById(R.id.edit_btn).setVisibility(View.VISIBLE);



        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                if (v.getVisibility() == View.VISIBLE) {
                    list.remove(position); //or some other task
                    notifyDataSetChanged();
                }
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getVisibility() == View.VISIBLE) {
                    timePickerClicker(v);
                    list.remove(position);
                    notifyDataSetChanged();
                }
            }
        });

        viewList.add(position, view);
        return view;
    }

    private void buttonAnimIn(final View delete, final View edit) {
        ObjectAnimator deleteButton = ObjectAnimator.ofFloat(delete, "translationX", 1000, 0);
        deleteButton.setInterpolator(new AccelerateInterpolator());
        deleteButton.setRepeatCount(0);
        deleteButton.setDuration(500);

        ObjectAnimator editButton = ObjectAnimator.ofFloat(edit, "translationX", 1000, 0);
        editButton.setInterpolator(new AccelerateInterpolator());
        editButton.setRepeatCount(0);
        editButton.setDuration(500);

        AnimatorSet buttonSet = new AnimatorSet();
        buttonSet.play(editButton).before(deleteButton);

        deleteButton.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                delete.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        editButton.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                edit.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        buttonSet.start();
    }

    private void buttonAnimOut(final View delete, final View edit) {
        ObjectAnimator deleteButton = ObjectAnimator.ofFloat(delete, "translationX", 0, 1000);
        deleteButton.setInterpolator(new AccelerateInterpolator());
        deleteButton.setRepeatCount(0);
        deleteButton.setDuration(500);

        ObjectAnimator editButton = ObjectAnimator.ofFloat(edit, "translationX", 0, 1000);
        editButton.setInterpolator(new AccelerateInterpolator());
        editButton.setRepeatCount(0);
        editButton.setDuration(500);

        AnimatorSet buttonSet = new AnimatorSet();
        buttonSet.play(deleteButton).before(editButton);

        deleteButton.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                delete.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        editButton.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                edit.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        buttonSet.start();
    }

    public void timePickerClicker(View view) { //lawl
        Log.d(getClass().getName() + " timePickerClicker", "Time floater happening");
        new TimePickerDialog(context,
                tp,
                dateAndTime.get(Calendar.HOUR_OF_DAY),//theBundle.getInt("hour"),
                dateAndTime.get(Calendar.MINUTE),//theBundle.getInt("minute"),
                false).show();
    }

    private void updateList() {
        String item = DateFormat.getTimeInstance(DateFormat.SHORT).format(dateAndTime
                .getTime());
        // TODO: Add Create a bundle and add it to instance
        Bundle bundle = new Bundle();
        Log.d(getClass().getName() + " updateList", "list updated");
//        theBundle.putInt("hour", dateAndTime.get(Calendar.HOUR_OF_DAY));
//        theBundle.putInt("minute", dateAndTime.get(Calendar.MINUTE));
        bundle.putInt("hour", dateAndTime.get(Calendar.HOUR_OF_DAY));
        bundle.putInt("minute", dateAndTime.get(Calendar.MINUTE));
        //aTimes.add(bundle);
        list.add(item);
        Collections.sort(list);
        //adapter.notifyDataSetChanged();
        notifyDataSetChanged();
    }
}
