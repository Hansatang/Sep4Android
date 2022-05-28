package com.example.sep4android.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.R;
import com.example.sep4android.Util.DateFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for creating Current Measurements Card Views in MainFragment
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
  private final String TAG = "RoomAdapter";
  final private RoomAdapter.OnListItemClickListener clickListener;
  private List<RoomObject> roomObjectList;
  private Context ctx;

  public RoomAdapter(RoomAdapter.OnListItemClickListener listener) {
    roomObjectList = new ArrayList<>();
    clickListener = listener;
  }

  public void updateListAndNotify(List<RoomObject> list) {
    Log.i(TAG, "Update Room Adapter with " + list.size() + " objects");
    roomObjectList = list;
    notifyDataSetChanged();
  }

  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    ctx = recyclerView.getContext();
  }

  public List<RoomObject> getRoomObjectList() {
    return roomObjectList;
  }

  @NonNull
  public RoomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view;
    view = inflater.inflate(R.layout.room_list_layout, parent, false);
    return new RoomAdapter.ViewHolder(view);
  }

  public void onBindViewHolder(RoomAdapter.ViewHolder viewHolder, int position) {
    Log.i(TAG, "Binding viewHolder number : " + position);
    viewHolder.name.setText(ctx.getString(R.string.bind_holder_name, roomObjectList.get(position).getName()));
    List<MeasurementsObject> list = roomObjectList.get(position).getMeasurements();
    if (list != null) {
      if (!list.isEmpty()) {
        MeasurementsObject measurementsObject = list.get(0);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = ctx.getTheme();
        theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true);
        @ColorInt int color = typedValue.data;
        viewHolder.temperature.setText(ctx.getString(R.string.bind_holder_temp, measurementsObject.getTemperature()));
        if (measurementsObject.isTemperatureExceeded()) {
          viewHolder.temperature.setTextColor(Color.RED);
        } else {
          viewHolder.temperature.setTextColor(color);
        }
        viewHolder.humidity.setText(ctx.getString(R.string.bind_holder_hum, measurementsObject.getHumidity()));
        if (measurementsObject.isHumidityExceeded()) {
          viewHolder.humidity.setTextColor(Color.RED);
        } else {
          viewHolder.humidity.setTextColor(color);
        }
        viewHolder.co2.setText(ctx.getString(R.string.bind_holder_co2, measurementsObject.getCo2()));
        if (measurementsObject.isCo2Exceeded()) {
          viewHolder.co2.setTextColor(Color.RED);
        } else {
          viewHolder.co2.setTextColor(color);
        }
        viewHolder.date.setText(DateFormatter.getFormattedDateForRoom(list.get(0).getDate()));
      }
    }
  }


  public interface OnListItemClickListener {
    void onListItemClick(RoomObject clickedItemIndex);
  }


  public int getItemCount() {
    return roomObjectList.size();
  }

  /**
   * View Holder for Current Measurements Cards
   */
  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView name;
    TextView date;
    TextView temperature;
    TextView humidity;
    TextView co2;

    ViewHolder(View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.RoomIdText);
      date = itemView.findViewById(R.id.DateText);
      temperature = itemView.findViewById(R.id.TemperatureText);
      humidity = itemView.findViewById(R.id.HumidityText);
      co2 = itemView.findViewById(R.id.CO2Text);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      clickListener.onListItemClick(roomObjectList.get(getBindingAdapterPosition()));
    }
  }
}
