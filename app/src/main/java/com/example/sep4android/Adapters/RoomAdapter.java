package com.example.sep4android.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.Room;
import com.example.sep4android.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

//Adapter for creating Current Measurements Card Views in MainFragment
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
  final private RoomAdapter.OnListItemClickListener clickListener;
  private List<Room> objects;

  public RoomAdapter(RoomAdapter.OnListItemClickListener listener) {
    objects = new ArrayList<>();
    clickListener = listener;
  }

  public void updateListAndNotify(List<Room> list) {
    System.out.println("Update call " + list.size());
    objects = list;
    notifyDataSetChanged();
  }


  @NonNull
  public RoomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view;
    view = inflater.inflate(R.layout.room_list_layout, parent, false);
    return new RoomAdapter.ViewHolder(view);
  }

  public void onBindViewHolder(RoomAdapter.ViewHolder viewHolder, int position) {
    System.out.println("Room: " + objects.get(position).getRoomId());
    viewHolder.name.setText("Room: " + objects.get(position).getName());

    List<MeasurementsObject> list = objects.get(position).getMeasurements();
    if (!list.isEmpty()) {
      viewHolder.temperature.setText(new StringBuilder().append(list.get(0).getTemperature()).append(" \u2103").toString());
      if (list.get(0).isTemperatureExceeded()) {
        viewHolder.temperature.setTextColor(Color.RED);
      } else {
        viewHolder.temperature.setTextColor(Color.BLACK);
      }
      else {
        viewHolder.temperature.setTextColor(Color.BLACK);
      }
      viewHolder.humidity.setText(list.get(0).getHumidity() + "");
      if (list.get(0).isHumidityExceeded()) {
        viewHolder.humidity.setTextColor(Color.RED);
      } else {
        viewHolder.humidity.setTextColor(Color.BLACK);
      }
      else {
        viewHolder.humidity.setTextColor(Color.BLACK);
      }
      viewHolder.co2.setText(list.get(0).getCo2() + "");
      if (list.get(0).isCo2Exceeded()) {
        viewHolder.co2.setTextColor(Color.RED);
      } else {
        viewHolder.co2.setTextColor(Color.BLACK);
      }
      else {
        viewHolder.co2.setTextColor(Color.BLACK);
      }
      viewHolder.date.setText(getFormattedDate(list));
    }
  }

  @Nullable
  private String getFormattedDate(List<MeasurementsObject> list) {
    String strDate = null;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

      Date date1 = dateFormat.parse(list.get(0).getDate());
      System.out.println(date1);
      DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd E hh:mm:ss");
      strDate = dateFormat2.format(date1);
      System.out.println(strDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return strDate;
  }


  public interface OnListItemClickListener {
    void onListItemClick(Room clickedItemIndex);
  }


  public int getItemCount() {
    return objects.size();
  }

  //View Holder for Current Measurements Cards
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
      clickListener.onListItemClick(objects.get(getBindingAdapterPosition()));
    }
  }
}
