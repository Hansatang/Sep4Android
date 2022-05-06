package com.example.sep4android.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Objects.MeasurementObject;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.R;

import java.util.ArrayList;
import java.util.List;

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.ViewHolder> {
  final private MeasurementAdapter.OnListItemClickListener clickListener;
  private List<MeasurementObject> objects;

  public MeasurementAdapter(MeasurementAdapter.OnListItemClickListener listener) {
    objects = new ArrayList<>();
    clickListener = listener;
  }

  public void update(List<MeasurementObject> list) {
    System.out.println("Update call " + list.size());
    if (list != null) {
      objects = list;
      notifyDataSetChanged();
    }
  }


  public MeasurementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view;
    view = inflater.inflate(R.layout.room_list_layout, parent, false);
    return new MeasurementAdapter.ViewHolder(view);
  }

  public void onBindViewHolder(MeasurementAdapter.ViewHolder viewHolder, int position) {
    System.out.println("Room: " + objects.get(position).getRoomId());
    viewHolder.name.setText("Room: " + objects.get(position).getRoomId());
    viewHolder.temperature.setText((objects.get(position).getTemperature()+""));
    viewHolder.humidity.setText(objects.get(position).getHumidity()+"");
  }


  public interface OnListItemClickListener {
    void onListItemClick(MeasurementObject clickedItemIndex);
  }


  public int getItemCount() {
    return objects.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView name;
    TextView temperature;
    TextView humidity;

    ViewHolder(View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.RoomIdText);
      temperature = itemView.findViewById(R.id.TemperatureText);
      humidity = itemView.findViewById(R.id.HumidityText);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      clickListener.onListItemClick(objects.get(getBindingAdapterPosition()));
    }
  }
}
