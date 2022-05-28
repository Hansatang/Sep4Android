package com.example.sep4android.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.Log;
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
import com.example.sep4android.Util.DateFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for creating date Views in Nested Recycler View (Parent) of ArchiveFragment
 */
public class ParentMeasurementAdapter extends RecyclerView.Adapter<ParentMeasurementAdapter.ViewHolder> {
  private final String TAG = "ParentMeasurementAdapter";
  final private ParentMeasurementAdapter.OnListItemClickListener clickListener;
  private Context ctx;
  private ArrayList<LocalDateTime> dateTimeList;
  private int mExpandedPosition;
  private int previousExpandedPosition;


  /**
   * Constructor initializing dateTimeList as new ArrayList,
   * assigning listener from Archive fragment
   * and setting two recently interacted object indexes to -1
   *
   * @param listener
   */
  public ParentMeasurementAdapter(ParentMeasurementAdapter.OnListItemClickListener listener) {
    dateTimeList = new ArrayList<>();
    clickListener = listener;
    mExpandedPosition = -1;
    previousExpandedPosition = -1;
  }


  /**
   * Updates dateTimeList with new data , notifying change
   * and ressting two recently interacted object indexes to  -1
   *
   * @param list list with dates
   */
  public void updateListAndNotify(ArrayList<LocalDateTime> list) {
    Log.i(TAG, "Update Parent Adapter with " + list.size() + " objects");
    mExpandedPosition = -1;
    previousExpandedPosition = -1;
    dateTimeList = list;
    notifyDataSetChanged();
  }

  @Override
  public int getItemViewType(int position) {
    return position; // Return any variable as long as it's not a constant value
  }

  @NonNull
  public ParentMeasurementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

  @Override
  public void onBindViewHolder(ParentMeasurementAdapter.ViewHolder viewHolder, int position) {
    Log.i(TAG, "Binding viewHolder number : " + position);
    LocalDateTime currentItem = dateTimeList.get(position);
    viewHolder.dateId.setText(DateFormatter.getFormattedDateForParent(currentItem));
    setChildViewHolderAndAdapter(viewHolder);
    addExpandabilityToViewHolder(viewHolder);
  }

  @Override
  public void onBindViewHolder(ParentMeasurementAdapter.ViewHolder viewHolder, int position, List<Object> payloads) {
    Log.i(TAG, "Binding viewHolder number : " + position + " with payload");
    LocalDateTime currentItem = dateTimeList.get(position);
    viewHolder.dateId.setText(DateFormatter.getFormattedDateForParent(currentItem));

    setChildViewHolderAndAdapter(viewHolder);
    addExpandabilityToViewHolder(viewHolder);

  }

  @Override
  public void onViewAttachedToWindow(@NonNull ViewHolder viewHolder) {
    if (viewHolder.details.getVisibility() == View.VISIBLE) {
      viewHolder.details.setVisibility(View.GONE);
      ViewGroup.LayoutParams layoutParams = viewHolder.containerChild.getLayoutParams();
      layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
      layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
      viewHolder.containerChild.setLayoutParams(layoutParams);
      previousExpandedPosition = -1;
    }
    super.onViewAttachedToWindow(viewHolder);
  }

  /**
   * adds expandability to viewHolder by changing visibility of this viewHolder Linear Layout "details"
   * adds Click listener to expand/collapse, within this Listener logic for collapsing previously expanded viewHolder is provided,
   * also Click listener from Adapter is used to relay position and Child adapter to Fragment for populating child list purposes
   *
   * @param viewHolder to add functionality of expanding/collapsing
   */
  private void addExpandabilityToViewHolder(ViewHolder viewHolder) {
    final boolean isExpanded = viewHolder.getBindingAdapterPosition() == mExpandedPosition;
    viewHolder.details.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    viewHolder.itemView.setActivated(isExpanded);

    viewHolder.itemView.setOnClickListener(v -> {
          mExpandedPosition = isExpanded ? -1 : viewHolder.getBindingAdapterPosition();
          if (viewHolder.details.getVisibility() == View.GONE) {
            viewHolder.details.setVisibility(View.VISIBLE);
            notifyItemChanged(previousExpandedPosition, 0);
            previousExpandedPosition = viewHolder.getBindingAdapterPosition();
          } else {
            viewHolder.details.setVisibility(View.GONE);
            previousExpandedPosition = -1;
          }
          clickListener.onListItemClick(dateTimeList.get(viewHolder.getBindingAdapterPosition()), viewHolder.getInsideAdapter());
        }
    );
  }

  /**
   * adds ChildMeasurementAdapter to viewHolder by creating layoutManager and setting adapter
   * adds OnItemTouchListener to child reyclerView to ensure that both parent and child are able to being scrolled up and down
   *
   * @param viewHolder to add Child Adapter to
   */
  private void setChildViewHolderAndAdapter(ViewHolder viewHolder) {
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
    viewHolder.recyclerView.setLayoutManager(layoutManager);
    viewHolder.recyclerView.setHasFixedSize(true);
    ChildMeasurementAdapter childRecyclerViewAdapter = new ChildMeasurementAdapter();
    viewHolder.recyclerView.setAdapter(childRecyclerViewAdapter);
    viewHolder.recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
      float mLastY;

      @Override
      public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        switch (e.getAction()) {
          case MotionEvent.ACTION_DOWN:
            mLastY = e.getY();
            break;
          case MotionEvent.ACTION_CANCEL:
          case MotionEvent.ACTION_UP:
            break;
          case MotionEvent.ACTION_MOVE:
            float y = e.getY();
            if (mLastY > y) {
              rv.getParent().requestDisallowInterceptTouchEvent(rv.canScrollVertically(1));
            }
            if (mLastY < y) {
              rv.getParent().requestDisallowInterceptTouchEvent(rv.canScrollVertically(-1));
            }
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
    });
  }


  public interface OnListItemClickListener {
    void onListItemClick(LocalDateTime clickedItemIndex, ChildMeasurementAdapter viewHolder);
  }

  public int getItemCount() {
    return dateTimeList.size();
  }

  /**
   * View Holder for Views with nested Recycler View of measurements
   */
  public static class ViewHolder extends RecyclerView.ViewHolder {
    LinearLayout details;
    LinearLayout containerChild;
    TextView dateId;
    TextView temperatureId;
    TextView humidityId;
    TextView co2Id;
    RecyclerView recyclerView;

    ViewHolder(View itemView) {
      super(itemView);
      containerChild = itemView.findViewById(R.id.containerChild);
      recyclerView = itemView.findViewById(R.id.inside_rv);
      dateId = itemView.findViewById(R.id.dateId);
      temperatureId = itemView.findViewById(R.id.temperatureId);
      humidityId = itemView.findViewById(R.id.humidityId);
      co2Id = itemView.findViewById(R.id.co2Id);
      details = itemView.findViewById(R.id.details);
    }

    /**
     * @return ChildMeasurementAdapter to provided access to this dynamically created adapter
     */
    public ChildMeasurementAdapter getInsideAdapter() {
      return (ChildMeasurementAdapter) recyclerView.getAdapter();
    }
  }
}
