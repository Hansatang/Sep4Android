package com.example.sep4android.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Objects.TemperatureThresholdObject;
import com.example.sep4android.R;

import java.util.ArrayList;
import java.util.List;

/**

 * Adapter for creating Temperature Threshold Views in HumidityThresholdFragment
 */
public class TemperatureThresholdAdapter extends RecyclerView.Adapter<TemperatureThresholdAdapter.TempThresholdViewHolder> {
  private final String TAG = "TemperatureThresholdAdapter";
  private List<TemperatureThresholdObject> thresholdObjects;

  /**
   * Simple constructor initializing thresholdObjectList as new ArrayList
   */
  public TemperatureThresholdAdapter() {
    thresholdObjects = new ArrayList<>();
  }

  /**
   * Updates thresholdObjects with new data and notify change
   * @param list new list with Thresholds from repository
   */
  public void updateHumidityThresholdAndNotify(List<TemperatureThresholdObject> list) {
    Log.i(TAG, "Update Temperature Adapter with " + list.size() + " objects");
    thresholdObjects = list;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public TempThresholdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view;
    view = inflater.inflate(R.layout.fragment_temperature_threshold_item, parent, false);
    return new TempThresholdViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull TempThresholdViewHolder holder, int position) {
    Log.i(TAG, "Binding viewHolder number : " + position);
    holder.startValue.setText(String.valueOf(thresholdObjects.get(position).getMinValue()));
    holder.endValue.setText(String.valueOf(thresholdObjects.get(position).getMaxValue()));
    holder.startTime.setText(thresholdObjects.get(position).getStartTime());
    holder.endTime.setText(thresholdObjects.get(position).getEndTime());
  }

  @Override
  public int getItemCount() {
    return thresholdObjects.size();
  }

  public List<TemperatureThresholdObject> getThresholds() {
    return thresholdObjects;
  }

  /**
   * View Holder for temperature threshold data
   */
  static class TempThresholdViewHolder extends RecyclerView.ViewHolder {

    TextView startValue;
    TextView endValue;
    TextView startTime;
    TextView endTime;

    TempThresholdViewHolder(View itemView) {
      super(itemView);
      startValue = itemView.findViewById(R.id.temperature_threshold_start_value_holder);
      endValue = itemView.findViewById(R.id.temperature_threshold_end_value_holder);
      startTime = itemView.findViewById(R.id.temperature_threshold_start_time_holder);
      endTime = itemView.findViewById(R.id.temperature_threshold_end_time_holder);
    }
  }
}
