package com.example.sep4android.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Objects.Room;
import com.example.sep4android.Objects.TemperatureThresholdObject;
import com.example.sep4android.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TemperatureThresholdAdapter extends RecyclerView.Adapter<TemperatureThresholdAdapter.ViewHolder>{


    private List<TemperatureThresholdObject> thresholdObjects;
    private List<Room> rooms;

    public TemperatureThresholdAdapter(){
        thresholdObjects = new ArrayList<>();
    }


    public void update(List<TemperatureThresholdObject> list){
        System.out.println("Update call "+ list.size());
        if (list != null){
            thresholdObjects = list;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        view = inflater.inflate(R.layout.fragment_temperature_threshold_item, parent, false);
        return new TemperatureThresholdAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.startValue.setText(String.valueOf(thresholdObjects.get(position).getMinValue()));
            holder.endValue.setText(String.valueOf(thresholdObjects.get(position).getMaxValue()));
        holder.startTime.setText(thresholdObjects.get(position).getStartTime());
        holder.endTime.setText(thresholdObjects.get(position).getEndTime());
    }

    @Override
    public int getItemCount() {
        return thresholdObjects.size();
    }

    public interface OnListItemClickListener {
        void onListItemClick(Room clickedItemIndex);
    }


    class ViewHolder extends RecyclerView.ViewHolder{


        EditText startValue;
        EditText endValue;
        EditText startTime;
        EditText endTime;

        ViewHolder(View itemView){
            super(itemView);
            startValue = itemView.findViewById(R.id.temperature_threshold_start_value_holder);
            endValue = itemView.findViewById(R.id.temperature_threshold_end_value_holder);
            startTime = itemView.findViewById(R.id.temperature_threshold_start_time_holder);
            endTime = itemView.findViewById(R.id.temperature_threshold_end_time_holder);
        }

        public void onClick(View view){
        }
    }
}
