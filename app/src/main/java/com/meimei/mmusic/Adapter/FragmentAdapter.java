package com.meimei.mmusic.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 梅梅 on 2016/5/14.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    public static String[] Title = new String[]{"热门歌曲","欧美","内地","港台"};
    private List<Fragment> fragments;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return Title.length;
    }

    public CharSequence getPageTitle(int position){
        return Title[position];
    }
}