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
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
  private final String TAG = "RoomAdapter";
  final private RoomAdapter.OnListItemClickListener clickListener;
  private List<RoomObject> roomList;
  private Context ctx;


  /**
   * Simple constructor initializing roomObjects as a new ArrayList and a click listener
   * @param listener to have on click listener
   */
  public RoomAdapter(RoomAdapter.OnListItemClickListener listener) {
    roomList = new ArrayList<>();
    clickListener = listener;
  }

  /**
   * Updates roomObjectList with new data and notify change
   * @param list new list with Room from repository
   */
  public void updateListAndNotify(List<RoomObject> list) {
    Log.i(TAG, "Update Room Adapter with " + list.size() + " objects");
    roomList = list;
    notifyDataSetChanged();
  }

  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    ctx = recyclerView.getContext();
  }

  public List<RoomObject> getRoomList() {
    return roomList;
  }

  @NonNull
  public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view;
    view = inflater.inflate(R.layout.room_list_layout, parent, false);
    return new RoomViewHolder(view);
  }

  public void onBindViewHolder(RoomViewHolder roomViewHolder, int position) {
    Log.i(TAG, "Binding viewHolder number : " + position);
    roomViewHolder.name.setText(ctx.getString(R.string.bind_holder_name, roomList.get(position).getName()));
    List<MeasurementsObject> list = roomList.get(position).getMeasurements();
    if (list != null) {
      if (!list.isEmpty()) {
        MeasurementsObject measurementsObject = list.get(0);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = ctx.getTheme();
        theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true);
        @ColorInt int color = typedValue.data;
        roomViewHolder.temperature.setText(ctx.getString(R.string.bind_holder_temp, measurementsObject.getTemperature()));
        if (measurementsObject.isTemperatureExceeded()) {
          roomViewHolder.temperature.setTextColor(Color.RED);
        } else {
          roomViewHolder.temperature.setTextColor(color);
        }
        roomViewHolder.humidity.setText(ctx.getString(R.string.bind_holder_hum, measurementsObject.getHumidity()));
        if (measurementsObject.isHumidityExceeded()) {
          roomViewHolder.humidity.setTextColor(Color.RED);
        } else {
          roomViewHolder.humidity.setTextColor(color);
        }
        roomViewHolder.co2.setText(ctx.getString(R.string.bind_holder_co2, measurementsObject.getCo2()));
        if (measurementsObject.isCo2Exceeded()) {
          roomViewHolder.co2.setTextColor(Color.RED);
        } else {
          roomViewHolder.co2.setTextColor(color);
        }
        roomViewHolder.date.setText(DateFormatter.getFormattedDateForRoom(list.get(0).getDate()));
      }
    }
  }


  public interface OnListItemClickListener {
    void onListItemClick(RoomObject clickedItemIndex);
  }


  public int getItemCount() {
    return roomList.size();
  }

  /**
   * View Holder for Current Measurements Cards
   */
  class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView name;
    TextView date;
    TextView temperature;
    TextView humidity;
    TextView co2;

    RoomViewHolder(View itemView) {
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
      clickListener.onListItemClick(roomList.get(getBindingAdapterPosition()));
    }
  }
}
