package com.longfor.channelmanager.client.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.view.CommonHeadView;
import com.longfor.channelmanager.statistics.queryattendance.adapter.QueryAttendancePagerAdapter;
import com.longfor.channelmanager.statistics.queryattendance.delegate.QueryAttendanceSubDelegate;
import com.longfor.core.delegates.LongForDelegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author: tongzhenhua
 * @date: 2018/1/9
 * @function:
 */
public class ClientListDelegate extends LongForDelegate {
    @BindView(R2.id.header_client_list)
    CommonHeadView headerClientList;
    @BindView(R2.id.tl_client_list)
    TabLayout tlClientList;
    @BindView(R2.id.vp_client_list)
    ViewPager vpClientList;
    private final List<LongForDelegate> delegates = new ArrayList<>();
    private final List<String> mTabTitles = new ArrayList<>();
    private final List<String> intentTypes = new ArrayList<>();

    @Override
    public Object setLayout() {
        return R.layout.delegate_client_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initHeader();
        initTabLayout();
    }
    private void initHeader() {
        Bundle arguments = getArguments();
        headerClientList.setLeftMsg(arguments.getString(Constant.TITLE_LEFT_TEXT));
        headerClientList.setLeftBackImageVisible(true);
        headerClientList.setTitle(getString(R.string.channel_platform_client_list));
        headerClientList.setBottomLineVisible(true);
        headerClientList.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().pop();
            }
        });
//        headerClientList.setRightLayoutOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.showMessage(getContext(), getString(R.string.index_statistics_title));
//            }
//        });
    }

    private void initTabLayout() {
        delegates.clear();
        mTabTitles.clear();
        intentTypes.clear();
        mTabTitles.addAll(Arrays.asList(new String[]{getString(R.string.all),
                getString(R.string.first_floor), getString(R.string.second_floor),
                getString(R.string.subscribe)}));
        intentTypes.addAll(Arrays.asList(new String[]{ConstantClientList.CLIENT_ALL,
                ConstantClientList.CLIENT_FIRST,
                ConstantClientList.CLIENT_SECOND,
                ConstantClientList.CLIENT_SUBSCRIBE}
        ));
        for (String intentType : intentTypes) {
            ClientListSubDelegate delegate = ClientListSubDelegate.getInstance(intentType);
            delegates.add(delegate);
        }

        vpClientList.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return delegates.get(position);
            }

            @Override
            public int getCount() {
                return delegates.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitles.get(position);
            }
        });
        tlClientList.setupWithViewPager(vpClientList);
    }

//    private FragmentPagerAdapter pagerAdapter
}
