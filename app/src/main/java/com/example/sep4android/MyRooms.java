package com.example.sep4android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MyRooms extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        System.out.println("My Rooms");
        return inflater.inflate(R.layout.fragment_my_rooms, container, false);
    }
}