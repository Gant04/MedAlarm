package concentric.medalarm;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

/**
 * Created by mike on 6/30/15.
 */
public class AlarmTimePickerDialogFragment extends DialogFragment {
    Handler handler;

    int aHour;
    int aMinute;

    public AlarmTimePickerDialogFragment() {

    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Bundle bundle = getArguments();
        aHour = bundle.getInt("set_hour");
        aMinute = bundle.getInt("set_minute");

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                aHour = hourOfDay;
                aMinute = minute;

                Bundle timeBundle = new Bundle();

                timeBundle.putInt("set_hour", aHour);
                timeBundle.putInt("set_minute", aMinute);
                timeBundle.putString("set_time", "Set Time: " + Integer.toString(aHour) + ":" +
                                                 Integer.toString(aMinute));

                Message message = new Message();

                message.setData(timeBundle);

                handler.sendMessage(message);
            }
        };

        return new TimePickerDialog(getActivity(),onTimeSetListener,aHour,aMinute,false);
    }

}
