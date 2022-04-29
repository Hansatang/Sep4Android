package com.example.sep4android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class VPAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> stringArrayList = new ArrayList<>();

    public VPAdapter(@NonNull FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new NewRoom();
                case 1:
                    return new MyRooms();
                default:
                    return null;
            }
//        }
//        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentArrayList.add(fragment);
        System.out.println("Hello "+title);
        stringArrayList.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringArrayList.get(position);
    }


}
