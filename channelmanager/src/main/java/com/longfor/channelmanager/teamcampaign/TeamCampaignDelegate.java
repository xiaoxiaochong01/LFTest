package com.longfor.channelmanager.teamcampaign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.baidu.mapapi.map.MapView;
import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.view.CommonHeadView;
import com.longfor.core.delegates.LongForDelegate;

import butterknife.BindView;

/**
 * @author: gaomei
 * @date: 2018/1/22
 * @function:
 */

public class TeamCampaignDelegate extends LongForDelegate {
    @BindView(R2.id.header_team_campaign)
    CommonHeadView mHeaderTeamCampaign;
    @BindView(R2.id.baiduMap)
    MapView mBaiduMap;

    public static TeamCampaignDelegate getInstance(String leftStr) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE_LEFT_TEXT, leftStr);
        TeamCampaignDelegate delegate = new TeamCampaignDelegate();
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_team_campaign;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mHeaderTeamCampaign.setLeftBackImageVisible(true);
        mHeaderTeamCampaign.setLeftMsg(getArguments().getString(Constant.TITLE_LEFT_TEXT));
        mHeaderTeamCampaign.setTitle(getString(R.string.channel_platform_team_campaign));
        mHeaderTeamCampaign.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().pop();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mBaiduMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBaiduMap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBaiduMap.onDestroy();
    }
}
