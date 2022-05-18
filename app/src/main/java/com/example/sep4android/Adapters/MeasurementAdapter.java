package com.example.sep4android.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
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
  private Context ctx;
  private List<MeasurementsObject> objects;
  private int mExpandedPosition;
  private int previousExpandedPosition;


  public MeasurementAdapter( MeasurementAdapter.OnListItemClickListener listener) {
    objects = new ArrayList<>();
    clickListener = listener;
    mExpandedPosition = -1;
    previousExpandedPosition = -1;

  }

  public void update(List<MeasurementsObject> list) {
    System.out.println("Update call " + list.size());
    objects = list;
    notifyDataSetChanged();
  }

  public List<MeasurementsObject> getMeasurements() {
    return objects;
  }


  @NonNull
  public MeasurementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ctx = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view;
    view = inflater.inflate(R.layout.measurements_list_layout, parent, false);
    return new ViewHolder(view);
  }

  @SuppressLint("SetTextI18n")
  public void onBindViewHolder(MeasurementAdapter.ViewHolder viewHolder, int position) {
    MeasurementsObject currentItem = objects.get(position);
    viewHolder.dateId.setText(getFormattedDate(position));
    viewHolder.temperatureId.setText(ctx.getString(R.string.bind_holder_temp, currentItem.getTemperature()));
    viewHolder.humidityId.setText(ctx.getString(R.string.bind_holder_hum, currentItem.getHumidity()));
    viewHolder.co2Id.setText(ctx.getString(R.string.bind_holder_co2, currentItem.getCo2()));

    addExpandabilityToViewHolder(viewHolder);
    setChildViewHolderAndAdapter(viewHolder);
  }

  private void addExpandabilityToViewHolder(ViewHolder viewHolder) {
    final boolean isExpanded = viewHolder.getBindingAdapterPosition() == mExpandedPosition;
    viewHolder.details.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    viewHolder.itemView.setActivated(isExpanded);

    if (isExpanded)
      previousExpandedPosition = viewHolder.getBindingAdapterPosition();

    viewHolder.itemView.setOnClickListener(v -> {
      mExpandedPosition = isExpanded ? -1 : viewHolder.getBindingAdapterPosition();
      notifyItemChanged(previousExpandedPosition);
      notifyItemChanged(viewHolder.getBindingAdapterPosition());
      System.out.println("1");
      clickListener.onListItemClick(objects.get(viewHolder.getBindingAdapterPosition()));
    });
  }

  private void setChildViewHolderAndAdapter(ViewHolder viewHolder) {
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
    viewHolder.recyclerView.setLayoutManager(layoutManager);
    viewHolder.recyclerView.setHasFixedSize(true);

    ArrayList<MeasurementsObject> arrayList = new ArrayList<>();
    arrayList.add(new MeasurementsObject("1234567", "12123", 12.00, 3, 20, 20));
    arrayList.add(new MeasurementsObject("1234567", "12123", 22.00, 23, 40, 440));
    InsideAdapter childRecyclerViewAdapter = new InsideAdapter(arrayList);
    viewHolder.recyclerView.setAdapter(childRecyclerViewAdapter);
  }

  @Nullable
  private String getFormattedDate(int position) {
    String strDate = null;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
      Date date1 = dateFormat.parse(objects.get(position).getDate());
      DateFormat dateFormat2 = new SimpleDateFormat("E hh:mm:ss");
      strDate = dateFormat2.format(date1);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return strDate;
  }

  public interface OnListItemClickListener {
    void onListItemClick(MeasurementsObject clickedItemIndex);
  }


  public int getItemCount() {
    return objects.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    LinearLayout details;
    TextView dateId;
    TextView temperatureId;
    TextView humidityId;
    TextView co2Id;
    RecyclerView recyclerView;

    ViewHolder(View itemView) {
      super(itemView);
      recyclerView = itemView.findViewById(R.id.inside_rv);
      dateId = itemView.findViewById(R.id.dateId);
      temperatureId = itemView.findViewById(R.id.temperatureId);
      humidityId = itemView.findViewById(R.id.humidityId);
      co2Id = itemView.findViewById(R.id.co2Id);
      details = itemView.findViewById(R.id.details);
    }
  }
}
