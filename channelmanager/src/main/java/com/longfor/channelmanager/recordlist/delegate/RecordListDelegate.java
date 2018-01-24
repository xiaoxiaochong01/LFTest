package com.longfor.channelmanager.recordlist.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.ec.baseadapter.ViewPagerAdapter;
import com.longfor.channelmanager.common.view.CommonHeadView;
import com.longfor.channelmanager.recordlist.constant.RecordListConstant;
import com.longfor.core.delegates.LongForDelegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author: gaomei
 * @date: 2018/1/23
 * @function:
 */

public class RecordListDelegate extends LongForDelegate {

    @BindView(R.id.header_tab_viewpager)
    CommonHeadView mHeaderTabViewpager;
    @BindView(R.id.tl_tab_viewpager)
    TabLayout mTlTabViewpager;
    @BindView(R.id.vp_tab_viewpager)
    ViewPager mVpTabViewpager;
    public List<Fragment> mFragmentList;
    public ViewPagerAdapter mPagerAdapter;
    private int mClickGroupCount = 0;

    public static RecordListDelegate getInstance(String leftStr) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE_LEFT_TEXT, leftStr);
        RecordListDelegate delegate = new RecordListDelegate();
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_tab_viewpager;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initHeader();
        initTabLayout();
    }

    private void initHeader() {
        mHeaderTabViewpager.setLeftBackImageVisible(true);
        mHeaderTabViewpager.setLeftMsg(getArguments().getString(Constant.TITLE_LEFT_TEXT));
        mHeaderTabViewpager.setTitle(getString(R.string.channel_platform_record_list));
        mHeaderTabViewpager.setBottomLineVisible(true);
        mHeaderTabViewpager.setRightImageVisible(true);
        mHeaderTabViewpager.setRightImageSrc(R.mipmap.ic_group);
        mHeaderTabViewpager.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().pop();
            }
        });
        mHeaderTabViewpager.setRightLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickGroupCount++;
                boolean isGroup = mClickGroupCount % 2 == 1;
                mHeaderTabViewpager.setRightImageSrc(isGroup ? R.mipmap.ic_person : R.mipmap.ic_group);
                for (int i = 0; i < mFragmentList.size(); i++) {
                    ((RecordListSubDelegate) mFragmentList.get(i)).mIsGroup = mClickGroupCount % 2;
                    if (mFragmentList.get(i).isAdded()) {
                        ((RecordListSubDelegate) mFragmentList.get(i)).updateParams();
                    }
                }
            }
        });
    }

    private void initTabLayout() {
        mTlTabViewpager.setTabMode(TabLayout.MODE_FIXED);
        List<String> mTabTitles = Arrays.asList(new String[]{getString(R.string.first_level_client),
                getString(R.string.second_level_client), getString(R.string.subscribe_client),
                getString(R.string.client_conversion_rate)});
        List<String> mCategoryTypes = Arrays.asList(new String[]{RecordListConstant.FIRST_LEVEL_CLIENT,
                RecordListConstant.SECOND_LEVEL_CLIENT, RecordListConstant.SUBSCRIBE_CLIENT,
                RecordListConstant.CLIENT_CONVERSION_RATE});
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < mTabTitles.size(); i++) {
            mFragmentList.add(RecordListSubDelegate.getInstance(mClickGroupCount % 2, mCategoryTypes.get(i), this));
        }
        mPagerAdapter = new ViewPagerAdapter(getFragmentManager(), mTabTitles, mFragmentList);
        mVpTabViewpager.setAdapter(mPagerAdapter);
        mTlTabViewpager.setupWithViewPager(mVpTabViewpager);
    }
}
