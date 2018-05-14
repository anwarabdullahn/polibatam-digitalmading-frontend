package com.anwarabdullahn.polibatamdigitalmading.Activity.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.anwarabdullahn.polibatamdigitalmading.Activity.Fragment.AnnouncementFragment;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Fragment.EventFragment;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Fragment.RecentFragment;

/**
 * Created by anwarabdullahn on 1/25/18.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 3;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return RecentFragment.newInstance();
            case 1:
                return AnnouncementFragment.newInstance();
            case 2:
                return EventFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return RecentFragment.TITLE;

            case 1:
                return AnnouncementFragment.TITLE;

            case 2:
                return EventFragment.TITLE;
        }
        return super.getPageTitle(position);
    }
}