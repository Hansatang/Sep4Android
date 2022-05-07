package com.example.sep4android.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.Room;
import com.example.sep4android.R;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
  final private RoomAdapter.OnListItemClickListener clickListener;
  private List<Room> objects;

  public RoomAdapter(RoomAdapter.OnListItemClickListener listener) {
    objects = new ArrayList<>();
    clickListener = listener;
  }

  public void update(List<Room> list) {
    System.out.println("Update call " + list.size());
    if (list != null) {
      objects = list;
      notifyDataSetChanged();
    }
  }


  public RoomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view;
    view = inflater.inflate(R.layout.room_list_layout, parent, false);
    return new RoomAdapter.ViewHolder(view);
  }

  public void onBindViewHolder(RoomAdapter.ViewHolder viewHolder, int position) {
    System.out.println("Room: " + objects.get(position).getRoomId());
    viewHolder.name.setText("Room: " + objects.get(position).getRoomId());
    List<MeasurementsObject> list = objects.get(position).getMeasurements();
    if (!list.isEmpty()) {
      viewHolder.temperature.setText(list.get(0).getTemperature() + "");
      viewHolder.humidity.setText(list.get(0).getHumidity() + "");
      viewHolder.co2.setText(list.get(0).getCo2() + "");
    }

  }


  public interface OnListItemClickListener {
    void onListItemClick(Room clickedItemIndex);
  }


  public int getItemCount() {
    return objects.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView name;
    TextView temperature;
    TextView humidity;
    TextView co2;

    ViewHolder(View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.RoomIdText);
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
