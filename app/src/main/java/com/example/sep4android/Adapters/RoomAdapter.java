package com.example.sep4android.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.R;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    final private RoomAdapter.OnListItemClickListener clickListener;
    private List<RoomObject> objects;

    public RoomAdapter(RoomAdapter.OnListItemClickListener listener) {
        objects = new ArrayList<>();
        clickListener = listener;
    }

    public void update(List<RoomObject> list) {
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
        viewHolder.temperature.setText(objects.get(position).getUserId());
        viewHolder.humidity.setText(objects.get(position).getRegistrationDate().toString());
    }


    public interface OnListItemClickListener {
        void onListItemClick(RoomObject clickedItemIndex);
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
