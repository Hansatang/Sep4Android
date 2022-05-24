package com.example.sep4android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sep4android.Objects.Room;
import com.example.sep4android.R;

import java.util.ArrayList;

//Custom Adapter for creating and populating spinners
public class SpinnerAdapter extends ArrayAdapter<Room> {

  private Context context;
  ArrayList<Room> data = new ArrayList<>();

  public SpinnerAdapter(Context context, int textViewResourceId, ArrayList<Room> objects) {
    super(context, textViewResourceId, objects);
    this.context = context;
    data = objects;
  }

  @Override
  public int getCount() {
    return super.getCount();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    View v = convertView;
    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    v = inflater.inflate(R.layout.spin_item, null);
    TextView textView = v.findViewById(R.id.item_text);
    textView.setText(data.get(position).getName());
    return v;

  }

  @Override
  public View getDropDownView(int position, View convertView, ViewGroup parent)
  {   // This view starts when we click the spinner.
    return initView(position, convertView, parent);
  }

  private View initView(int position, View convertView, ViewGroup parent) {
    View row = convertView;
    if(row == null)
    {
      LayoutInflater inflater =  (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      row = inflater.inflate(R.layout.spin_item_dropdown, parent, false);
    }

    Room room = data.get(position);

    TextView roomName = row.findViewById(R.id.spinnerItemName);

    if(roomName != null){
      roomName.setText(room.getName());
    }
    return row;
  }
}
