package com.example.sep4android.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.R;

import java.util.ArrayList;
import java.util.List;

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.ViewHolder> {
  final private MeasurementAdapter.OnListItemClickListener clickListener;
  private Context ctx;
  private ArrayList<String> daysList;
  private int mExpandedPosition;
  private int previousExpandedPosition;


  public MeasurementAdapter(MeasurementAdapter.OnListItemClickListener listener) {
    daysList = new ArrayList<>();
    clickListener = listener;
    mExpandedPosition = -1;
    previousExpandedPosition = -1;
  }

  public void update(ArrayList<String> list) {
    System.out.println("Update call " + list.size());
    daysList = list;
    notifyDataSetChanged();
  }


  public ArrayList<String> getMeasurements() {
    return daysList;
  }

  @Override
  public int getItemViewType(int position) {
    return position; // Return any variable as long as it's not a constant value
  }

  @NonNull
  public MeasurementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    System.out.println("View holder creation");
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view;
    view = inflater.inflate(R.layout.measurements_list_layout, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    ctx = recyclerView.getContext();
  }

  public void onBindViewHolder(MeasurementAdapter.ViewHolder viewHolder, int position) {
    System.out.println("NormalOnBind");
    String currentItem = daysList.get(position);
    viewHolder.dateId.setText(currentItem);
    viewHolder.dateId.setTextColor(Color.RED);
    setChildViewHolderAndAdapter(viewHolder);
    addExpandabilityToViewHolder(viewHolder);
  }

  public void onBindViewHolder(MeasurementAdapter.ViewHolder viewHolder, int position, List<Object> payloads) {
    System.out.println("PayloadOnBind");
    String currentItem = daysList.get(position);
    if (payloads != null) {
      viewHolder.details.setVisibility(View.GONE);
    }
    viewHolder.dateId.setText(currentItem);

    viewHolder.dateId.setTextColor(Color.RED);

    setChildViewHolderAndAdapter(viewHolder);

    addExpandabilityToViewHolder(viewHolder);

  }

  private void addExpandabilityToViewHolder(ViewHolder viewHolder) {
    final boolean isExpanded = viewHolder.getBindingAdapterPosition() == mExpandedPosition;
    viewHolder.details.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    viewHolder.itemView.setActivated(isExpanded);

    viewHolder.itemView.setOnClickListener(v -> {
          mExpandedPosition = isExpanded ? -1 : viewHolder.getBindingAdapterPosition();

          if (viewHolder.details.getVisibility() == View.GONE) {
            viewHolder.details.setVisibility(View.VISIBLE);
            notifyItemChanged(previousExpandedPosition, 2);
            previousExpandedPosition = viewHolder.getBindingAdapterPosition();
          } else {
            viewHolder.details.setVisibility(View.GONE);
            previousExpandedPosition = -1;
          }
          clickListener.onListItemClick(daysList.get(viewHolder.getBindingAdapterPosition()), viewHolder.getInsideAdapter());
        }
    );
  }

  private void setChildViewHolderAndAdapter(ViewHolder viewHolder) {
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
    viewHolder.recyclerView.setLayoutManager(layoutManager);
    viewHolder.recyclerView.setHasFixedSize(true);
    InsideAdapter childRecyclerViewAdapter = new InsideAdapter();

    viewHolder.recyclerView.setAdapter(childRecyclerViewAdapter);
    RecyclerView.OnItemTouchListener mScrollTouchListener = new RecyclerView.OnItemTouchListener() {
      @Override
      public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        int action = e.getAction();
        switch (action) {
          case MotionEvent.ACTION_MOVE:
            rv.getParent().requestDisallowInterceptTouchEvent(true);
            break;
        }
        return false;
      }

      @Override
      public void onTouchEvent(RecyclerView rv, MotionEvent e) {

      }

      @Override
      public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

      }
    };

    viewHolder.recyclerView.addOnItemTouchListener(mScrollTouchListener);
  }


  public interface OnListItemClickListener {
    void onListItemClick(String clickedItemIndex, InsideAdapter viewHolder);
  }


  public int getItemCount() {
    return daysList.size();
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

    public InsideAdapter getInsideAdapter() {

      return (InsideAdapter) recyclerView.getAdapter();
    }
  }
}
