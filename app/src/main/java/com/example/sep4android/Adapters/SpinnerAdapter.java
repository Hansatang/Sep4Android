package com.example.sep4android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.R;

import java.util.ArrayList;

/**
 * Custom Adapter for creating and populating spinners
 */

public class SpinnerAdapter extends ArrayAdapter<RoomObject> {
  private final String TAG = "SpinnerAdapter";
  private ArrayList<RoomObject> roomObjects;

  /**
   * Constructor initializing roomObjects as a new Array, the context and a textViewResource
   * @param context
   * @param textViewResourceId
   * @param roomObjects
   */
  public SpinnerAdapter(Context context, int textViewResourceId, ArrayList<RoomObject> roomObjects) {
    super(context, textViewResourceId, roomObjects);
    this.roomObjects = roomObjects;
  }

  @Override
  public int getCount() {
    return super.getCount();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    convertView = inflater.inflate(R.layout.spinner_layout, parent, false);
    TextView textView = convertView.findViewById(R.id.item_text);
    textView.setText(roomObjects.get(position).getName());
    return convertView;

  }

  @Override
  public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {   // This view starts when we click the spinner.
    return initView(position, convertView, parent);
  }

  private View initView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.spin_item_dropdown, parent, false);
    }
    RoomObject roomObject = roomObjects.get(position);
    TextView roomName = convertView.findViewById(R.id.spinnerItemName);
    if (roomName != null) {
      roomName.setText(roomObject.getName());
    }
    return convertView;
  }
}
