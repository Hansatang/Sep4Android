package com.example.sep4android.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Adapter for creating measurements Views in Nested Recycler View (Child) of ArchiveFragment
 */
public class ChildMeasurementAdapter extends RecyclerView.Adapter<ChildMeasurementAdapter.ViewHolder> {
  private final String TAG = "ChildMeasurementAdapter";
  private Context ctx;
  private List<MeasurementsObject> measurementsObjectList;


  /**
   * Simple constructor initializing measurementsObjectList as new ArrayList
   */
  public ChildMeasurementAdapter() {
    measurementsObjectList = new ArrayList<>();
  }

  /**
   * Updates measurementsObjectList with new data and notify change
   * @param list new list with Measurements from repository
   */
  public void updateListAndNotify(List<MeasurementsObject> list) {
    Log.i(TAG,"Update Child Adapter with "+list.size()+" objects");
    if (!list.isEmpty()) {
      measurementsObjectList = list;
    }
    else {
      measurementsObjectList = new ArrayList<>();
    }
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inside_measurement_list_layout, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
    super.onViewDetachedFromWindow(holder);
  }

  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    ctx = recyclerView.getContext();
  }

  @Override
  public int getItemViewType(int position) {
    return position; // Return any variable as long as it's not a constant value
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Log.i(TAG,"Binding viewHolder number : "+position);
    MeasurementsObject currentItem = measurementsObjectList.get(position);
    holder.dateId.setText(getFormattedDate(currentItem.getDate()));
    holder.temperatureId.setText(ctx.getString(R.string.bind_holder_temp, currentItem.getTemperature()));
    if (currentItem.isTemperatureExceeded()) {
      holder.temperatureId.setTextColor(Color.RED);
    }
    holder.humidityId.setText(ctx.getString(R.string.bind_holder_hum, currentItem.getHumidity()));
    if (currentItem.isHumidityExceeded()) {
      holder.humidityId.setTextColor(Color.RED);
    }
    holder.co2Id.setText(ctx.getString(R.string.bind_holder_co2, currentItem.getCo2()));
    if (currentItem.isCo2Exceeded()) {
      holder.co2Id.setTextColor(Color.RED);
    }
    holder.itemView.setOnClickListener(v -> {
      System.out.println(holder.dateId.getText().toString());
    });
  }

  /**
   * @param currentItem Date in String in "yyyy-MM-dd'T'HH:mm:ss" format
   * @return modified Date in String in "E hh:mm:ss" format
   */
  @Nullable
  private String getFormattedDate(String currentItem) {
    String strDate = null;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
      Date date1 = dateFormat.parse(currentItem);
      DateFormat dateFormat2 = new SimpleDateFormat("E hh:mm:ss");
      strDate = dateFormat2.format(date1);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return strDate;
  }

  @Override
  public int getItemCount() {
    return measurementsObjectList.size();
  }

  /**
   * View Holder used to create Views in this adapter
   */
  static class ViewHolder extends RecyclerView.ViewHolder {
    TextView dateId;
    TextView temperatureId;
    TextView humidityId;
    TextView co2Id;

    ViewHolder(View itemView) {
      super(itemView);
      dateId = itemView.findViewById(R.id.dateId);
      temperatureId = itemView.findViewById(R.id.temperatureId);
      humidityId = itemView.findViewById(R.id.humidityId);
      co2Id = itemView.findViewById(R.id.co2Id);
    }
  }
}
