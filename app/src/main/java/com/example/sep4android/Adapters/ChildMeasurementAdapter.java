package com.example.sep4android.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.R;
import com.example.sep4android.Util.DateFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for creating measurements Views in Nested Recycler View (Child) of ArchiveFragment
 */
public class ChildMeasurementAdapter extends RecyclerView.Adapter<ChildMeasurementAdapter.ChildViewHolder> {
  private final String TAG = "ChildMeasurementAdapter";
  private Context ctx;
  private List<MeasurementsObject> measurementsList;

  /**
   * Simple constructor initializing measurementsObjectList as new ArrayList
   */
  public ChildMeasurementAdapter() {
    measurementsList = new ArrayList<>();
  }

  /**
   * Updates measurementsObjectList with new data and notify change
   *
   * @param list new list with Measurements from repository
   */
  public void updateListAndNotify(List<MeasurementsObject> list) {
    Log.i(TAG, "Update Child Adapter with " + list.size() + " objects");
    if (!list.isEmpty()) {
      measurementsList = list;
    } else {
      measurementsList = new ArrayList<>();
    }
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_measurement_list_layout, parent, false);
    return new ChildViewHolder(view);
  }

  @Override
  public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    ctx = recyclerView.getContext();
  }

  @Override
  public int getItemViewType(int position) {
    return position; // Return any variable as long as it's not a constant value
  }

  @Override
  public void onBindViewHolder(ChildViewHolder holder, int position) {
    Log.i(TAG, "Binding viewHolder number : " + position);
    MeasurementsObject currentItem = measurementsList.get(position);
    holder.dateText.setText(DateFormatter.getFormattedDateForChildAdapter(currentItem.getDate()));
    holder.temperatureText.setText(ctx.getString(R.string.bind_holder_temp, currentItem.getTemperature()));
    if (currentItem.isTemperatureExceeded()) {
      holder.temperatureText.setTextColor(Color.RED);
    }
    holder.humidityText.setText(ctx.getString(R.string.bind_holder_hum, currentItem.getHumidity()));
    if (currentItem.isHumidityExceeded()) {
      holder.humidityText.setTextColor(Color.RED);
    }
    holder.co2Text.setText(ctx.getString(R.string.bind_holder_co2, currentItem.getCo2()));
    if (currentItem.isCo2Exceeded()) {
      holder.co2Text.setTextColor(Color.RED);
    }
  }

  @Override
  public int getItemCount() {
    return measurementsList.size();
  }

  /**
   * View Holder used to create Views in this adapter
   */
  static class ChildViewHolder extends RecyclerView.ViewHolder {
    TextView dateText;
    TextView temperatureText;
    TextView humidityText;
    TextView co2Text;

    ChildViewHolder(View itemView) {
      super(itemView);
      dateText = itemView.findViewById(R.id.dateText);
      temperatureText = itemView.findViewById(R.id.temperatureText);
      humidityText = itemView.findViewById(R.id.humidityText);
      co2Text = itemView.findViewById(R.id.co2Text);
    }
  }
}
