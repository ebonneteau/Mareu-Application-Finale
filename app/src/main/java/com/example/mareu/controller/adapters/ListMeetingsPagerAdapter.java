package com.example.mareu.controller.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mareu.controller.fragments.MeetingsFragment;
import com.example.mareu.controller.fragments.PlacesFragment;


public class ListMeetingsPagerAdapter extends FragmentPagerAdapter {

    public ListMeetingsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * get the number of pages
     *
     * @return
     */
    @Override
    public int getCount() {
        return 2;
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return MeetingsFragment.newInstance(0);
        }
        if (position == 1){
            return PlacesFragment.newInstance(1);
        }
        else {
            return null;
        }


    }
}