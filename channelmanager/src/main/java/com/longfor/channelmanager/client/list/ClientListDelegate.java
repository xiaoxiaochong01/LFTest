package com.longfor.channelmanager.client.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.view.CommonHeadView;
import com.longfor.channelmanager.statistics.adapter.QueryAttendancePagerAdapter;
import com.longfor.channelmanager.statistics.delegate.QueryAttendanceSubDelegate;
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
        List<String> mTabTitles = Arrays.asList(new String[]{getString(R.string.all),
                getString(R.string.first_floor), getString(R.string.second_floor),
                getString(R.string.subscribe)});
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < mTabTitles.size(); i++) {
            fragmentList.add(new QueryAttendanceSubDelegate());
        }
        QueryAttendancePagerAdapter pagerAdapter=new QueryAttendancePagerAdapter(getFragmentManager(),mTabTitles,fragmentList);
        vpClientList.setAdapter(pagerAdapter);
        tlClientList.setupWithViewPager(vpClientList);
    }
}
