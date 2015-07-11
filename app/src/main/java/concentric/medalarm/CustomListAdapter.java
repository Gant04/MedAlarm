package concentric.medalarm;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Thom on 7/9/2015.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    customButtonListener customButtonListener;
    private Context context;
    private ArrayList<String> data = new ArrayList<>();

    public ListAdapter(Context context, ArrayList<String> dataItem) {
        super(context, R.layout.child_listview, dataItem);
        this.data = dataItem;
        this.context = context;
    }

    public void setCustomButtonListener(customButtonListener listener) {
        this.customButtonListener = listener;
    }

    public interface customButtonListener {
        void onButtonClickListener(int position, String value);
    }

}
