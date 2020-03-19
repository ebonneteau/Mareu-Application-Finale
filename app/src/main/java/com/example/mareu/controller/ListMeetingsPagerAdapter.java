package com.example.mareu.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ListMeetingsPagerAdapter extends FragmentPagerAdapter {

    ListMeetingsPagerAdapter(FragmentManager fm) {
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