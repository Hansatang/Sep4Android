package com.example.sep4android.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.ViewHolder> {
  final private MeasurementAdapter.OnListItemClickListener clickListener;
  private List<MeasurementsObject> objects;

  public MeasurementAdapter(MeasurementAdapter.OnListItemClickListener listener) {
    objects = new ArrayList<>();
    clickListener = listener;
  }

  public void update(List<MeasurementsObject> list) {
    System.out.println("Update call " + list.size());
    objects = list;
    notifyDataSetChanged();
  }


  public MeasurementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view;
    view = inflater.inflate(R.layout.measurements_list_layout, parent, false);
    return new MeasurementAdapter.ViewHolder(view);
  }

  public void onBindViewHolder(MeasurementAdapter.ViewHolder viewHolder, int position) {
    System.out.println("Room: " + objects.get(position).getRoomId());
    String strDate = null;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
      Date date1 = dateFormat.parse(objects.get(position).getDate());
      DateFormat dateFormat2 = new SimpleDateFormat("E hh:mm:ss");
      strDate = dateFormat2.format(date1);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    viewHolder.dateId.setText(strDate);

    viewHolder.temperatureId.setText(new StringBuilder().append(objects.get(position).getTemperature()).append(" \u2103").toString());
    viewHolder.humidityId.setText(objects.get(position).getHumidity() + "");
    viewHolder.co2Id.setText(objects.get(position).getCo2() + "");
  }


  public interface OnListItemClickListener {
    void onListItemClick(MeasurementsObject clickedItemIndex);
  }


  public int getItemCount() {
    return objects.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      clickListener.onListItemClick(objects.get(getBindingAdapterPosition()));
    }
  }
}
