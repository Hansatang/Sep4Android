package com.example.sep4android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class InsideAdapter extends RecyclerView.Adapter<InsideAdapter.ViewHolder> {
  private Context ctx;
  public List<MeasurementsObject> objects;


  public InsideAdapter() {
    System.out.println("created Inside adapter");
    objects = new ArrayList<>();
  }

  public void update(List<MeasurementsObject> list) {
    System.out.println("Update call elo " + list.size());

    objects = list;
    System.out.println(objects.size());
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inside_measurement_list_layout, parent, false);
    return new ViewHolder(view);
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
    MeasurementsObject currentItem = objects.get(position);
    System.out.println("Room: " + currentItem.getRoomId());
    holder.dateId.setText(getFormattedDate(currentItem));

    holder.temperatureId.setText(ctx.getString(R.string.bind_holder_temp, currentItem.getTemperature()));
    holder.humidityId.setText(ctx.getString(R.string.bind_holder_hum, currentItem.getHumidity()));
    holder.co2Id.setText(ctx.getString(R.string.bind_holder_co2, currentItem.getCo2()));

  }

  @Nullable
  private String getFormattedDate(MeasurementsObject currentItem) {
    String strDate = null;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
      Date date1 = dateFormat.parse(currentItem.getDate());
      DateFormat dateFormat2 = new SimpleDateFormat("E hh:mm:ss");
      strDate = dateFormat2.format(date1);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return strDate;
  }

  @Override
  public int getItemCount() {
    return objects.size();
  }

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
