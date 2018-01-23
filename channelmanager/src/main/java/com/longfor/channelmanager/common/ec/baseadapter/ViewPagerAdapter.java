package com.longfor.channelmanager.common.ec.baseadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/4
 * @function:考勤查询页面ViewPager的数据适配器
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mTabTitles;
    private List<Fragment> mFragmentList;

    public ViewPagerAdapter(FragmentManager fm, List<String> tabTitles, List<Fragment> fragmentList) {
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
