package com.webgalaxie.blischke.bachelortakesix.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bexx on 10.01.18.
 */

public class TabPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();



    // constructor
    public TabPageAdapter(FragmentManager fm) {
        super(fm);
    }

    // return the pagetitle
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    // method to add the fragment and the titles to the lists
    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);


    }
}
