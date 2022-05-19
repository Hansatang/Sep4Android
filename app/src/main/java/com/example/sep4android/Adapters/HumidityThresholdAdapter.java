package com.example.sep4android.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.Objects.Room;
import com.example.sep4android.R;

import java.util.ArrayList;

public class HumidityThresholdAdapter extends RecyclerView.Adapter<HumidityThresholdAdapter.ViewHolder>{

    private ArrayList<HumidityThresholdObject> thresholdObjects;

    public HumidityThresholdAdapter(){
        thresholdObjects = new ArrayList<>();
    }


    public void update(ArrayList<HumidityThresholdObject> list) {
        System.out.println("Update call " + list.size());
        if (list != null) {
            thresholdObjects = list;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        view = inflater.inflate(R.layout.fragment_humidity_threshold_list, parent, false);
        return new HumidityThresholdAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder.room.getRoomId().equals(thresholdObjects.get(position).getRoomId()))
        {
            holder.startValue.setText(String.valueOf(thresholdObjects.get(position).getMinValue()));
            holder.endValue.setText(String.valueOf(thresholdObjects.get(position).getMaxValue()));
            holder.startTime.setText(thresholdObjects.get(position).getStartTime());
            holder.endTime.setText(thresholdObjects.get(position).getEndTime());
        }
    }

    @Override
    public int getItemCount() {
        return thresholdObjects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        EditText startValue;
        EditText endValue;
        EditText startTime;
        EditText endTime;
        Spinner spinner;
        Room room;


        ViewHolder(View itemView){
            super(itemView);
            startValue = itemView.findViewById(R.id.humidity_threshold_start_value_holder);
            endValue = itemView.findViewById(R.id.humidity_threshold_end_value_holder);
            startTime = itemView.findViewById(R.id.humidity_threshold_start_time_holder);
            endTime = itemView.findViewById(R.id.humidity_threshold_end_time_holder);
            spinner = itemView.findViewById(R.id.sp_humidity);
            room = (Room)spinner.getSelectedItem();
        }
    }
}
