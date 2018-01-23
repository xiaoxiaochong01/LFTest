package com.longfor.channelmanager.platform;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.arrange.group.ArrangeGroupDelegate;
import com.longfor.channelmanager.client.list.ClientListDelegate;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.view.CommonHeadView;
import com.longfor.channelmanager.recordlist.delegate.RecordListDelegate;
import com.longfor.channelmanager.teamcampaign.TeamCampaignDelegate;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.utils.toast.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: tongzhenhua
 * @date: 2018/1/9
 * @function:
 */
public class ChannelPlatformDelegate extends LongForDelegate {
    @BindView(R2.id.channel_platform_head)
    CommonHeadView channelPlatformHead;


    @Override
    public Object setLayout() {
        return R.layout.delegate_channel_platform;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String leftText = bundle.getString(Constant.TITLE_LEFT_TEXT);
            if (!TextUtils.isEmpty(leftText)) {
                channelPlatformHead.setLeftMsgVisiable(true);
                channelPlatformHead.setLeftMsg(leftText);
            }
            channelPlatformHead.setTitle(R.string.channel_platform_title);
            channelPlatformHead.setLeftBackImageVisible(true);
            channelPlatformHead.setLeftLayoutOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSupportDelegate().pop();
                }
            });
        }
    }

    @OnClick({R2.id.rl_group_student_customer_list, R2.id.rl_group_broker_customer_list, R2.id.rl_team_campaign, R2.id.rl_performance, R2.id.rl_arrange_work})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_group_student_customer_list:
                Bundle bundle = new Bundle();
                bundle.putString(Constant.TITLE_LEFT_TEXT, getResources().getString(R.string.channel_platform_title));
                ClientListDelegate delegate = new ClientListDelegate();
                delegate.setArguments(bundle);
                getSupportDelegate().start(delegate, SINGLETASK);
                break;
            case R.id.rl_group_broker_customer_list:
                ToastUtils.showMessage(R.string.channel_platform_broker_list);
                break;
            case R.id.rl_team_campaign:
                getSupportDelegate().start(TeamCampaignDelegate.getInstance(getString(R.string.channel_platform_title)));
                break;
            case R.id.rl_performance:
                getSupportDelegate().start(RecordListDelegate.getInstance(getString(R.string.channel_platform_title)));
                break;
            case R.id.rl_arrange_work:
                getSupportDelegate().start(ArrangeGroupDelegate.getInstance(getResources().getString(R.string.channel_platform_title)));
                break;
        }
    }
}
