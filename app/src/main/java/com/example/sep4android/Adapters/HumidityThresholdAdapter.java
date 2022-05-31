package com.example.sep4android.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for creating Humidity Threshold Views in HumidityThresholdFragment
 */
public class HumidityThresholdAdapter extends RecyclerView.Adapter<HumidityThresholdAdapter.HumThresholdViewHolder> {
  private final String TAG = "HumidityThresholdAdapter";
  private List<HumidityThresholdObject> thresholdList;

  /**
   * Simple constructor initializing thresholdObjects as new ArrayList
   */
  public HumidityThresholdAdapter() {
    thresholdList = new ArrayList<>();
  }


  /**
   * Updates thresholdObjects with new data and notify change
   * @param list new list with Thresholds from repository
   */
  public void updateHumidityThresholdsAndNotify(List<HumidityThresholdObject> list) {
    Log.i(TAG,"Update Humidity Adapter with "+list.size()+" objects");
    thresholdList = list;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public HumThresholdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view;
    view = inflater.inflate(R.layout.fragment_humidity_threshold_item, parent, false);
    return new HumThresholdViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull HumThresholdViewHolder holder, int position) {
    Log.i(TAG,"Binding viewHolder number : "+position);
    holder.startValue.setText(String.valueOf(thresholdList.get(position).getMinValue()));
    holder.endValue.setText(String.valueOf(thresholdList.get(position).getMaxValue()));
    holder.startTime.setText(thresholdList.get(position).getStartTime());
    holder.endTime.setText(thresholdList.get(position).getEndTime());
  }

  @Override
  public int getItemCount() {
    return thresholdList.size();
  }

  public List<HumidityThresholdObject> getThresholds() {
    return thresholdList;
  }

  /**
   * View Holder for temperature threshold data
   */
  static class HumThresholdViewHolder extends RecyclerView.ViewHolder {

    TextView startValue;
    TextView endValue;
    TextView startTime;
    TextView endTime;


    HumThresholdViewHolder(View itemView) {
      super(itemView);
      startValue = itemView.findViewById(R.id.humidity_threshold_start_value_holder);
      endValue = itemView.findViewById(R.id.humidity_threshold_end_value_holder);
      startTime = itemView.findViewById(R.id.humidity_threshold_start_time_holder);
      endTime = itemView.findViewById(R.id.humidity_threshold_end_time_holder);
    }

  }
}
