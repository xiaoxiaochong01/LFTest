package com.longfor.channelmanager.statistics.queryattendance.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/4
 * @function:考勤查询页面ViewPager的数据适配器
 */

public class QueryAttendancePagerAdapter extends FragmentPagerAdapter {
    private List<String> mTabTitles;
    private List<Fragment> mFragmentList;

    public QueryAttendancePagerAdapter(FragmentManager fm, List<String> tabTitles, List<Fragment> fragmentList) {
        super(fm);
        mTabTitles=tabTitles;
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles.get(position);
    }
}
