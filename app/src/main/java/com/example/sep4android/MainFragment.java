package com.example.sep4android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainFragment extends Fragment {
    UserViewModel viewModel;
    View view;
    TabLayout tabLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("MainView");
        view = inflater.inflate(R.layout.main_layout, container, false);
        findViews(view);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                mSelectedPosition = tabLayout.getSelectedTabPosition();
//                lazyLoadAdapter.selectedTab(mSelectedPosition);
//            }
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });

        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        //viewModel.addRoomToDatabase(123456789);
        viewModel.getUser().observe(getViewLifecycleOwner(), listObjects -> System.out.println("Wolo " + listObjects.get(0).getRoomId()));
        return view;
    }

    private void findViews(View view) {
        tabLayout = view.findViewById(R.id.tabLay);
    }
}
