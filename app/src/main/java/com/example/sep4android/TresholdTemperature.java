package com.example.sep4android;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

public class TresholdTemperature extends Fragment {

    View view;
    Button selectStartTimeTemp;
    int hour, minute;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("MainView");
        view = inflater.inflate(R.layout.treshold_temperature, container, false);
        selectStartTimeTemp = view.findViewById(R.id.selectStartTimeTemp);

        return view;
    }

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
            selectStartTimeTemp.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(selectStartTimeTemp.getContext(), style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

}
