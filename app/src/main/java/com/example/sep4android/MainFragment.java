package com.example.sep4android;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainFragment extends Fragment {
    UserViewModel viewModel;
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    VPAdapter vpAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("MainView");
        view = inflater.inflate(R.layout.main_layout, container, false);
        findViews(view);
        vpAdapter = new VPAdapter(getChildFragmentManager());
        vpAdapter.addFragment(new NewRoom(), "NewRoom");
        vpAdapter.addFragment(new MyRooms(), "MyRooms");
        viewPager.setAdapter(vpAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        //viewModel.addRoomToDatabase(123456789);
        //  viewModel.getUser().observe(getViewLifecycleOwner(), listObjects -> System.out.println("Wolo " + listObjects.get(0).getRoomId()));
        return view;
    }

    private void findViews(View view) {
        tabLayout = view.findViewById(R.id.tabLay);
        viewPager = view.findViewById(R.id.ViewPager);

    }


    @Override
    public void onPause() {
        super.onPause();
        viewPager.setCurrentItem(0);
    }
}
