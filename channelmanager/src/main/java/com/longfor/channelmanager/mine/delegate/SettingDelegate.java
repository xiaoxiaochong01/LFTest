package com.longfor.channelmanager.mine.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.constants.Constant;
import com.longfor.channelmanager.common.view.commonheadview.CommonHeadView;
import com.longfor.channelmanager.login.LoginHandler;
import com.longfor.channelmanager.login.delegate.LoginVideoDelegate;
import com.longfor.core.delegates.LongForDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: tongzhenhua
 * @date: 2018/1/2
 * @function: 设置界面
 */
public class SettingDelegate extends LongForDelegate {
    @BindView(R2.id.setting_head)
    CommonHeadView mHeadView;

    @OnClick(R2.id.tv_logout)
    void onLogoutClick() {
        LoginHandler.logout();
        getSupportDelegate().start(new LoginVideoDelegate(), SINGLETASK);
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_setting;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String leftText = bundle.getString(Constant.TITLE_LEFT_TEXT);
            if (!TextUtils.isEmpty(leftText)) {
                mHeadView.setLeftMsgVisiable(true);
                mHeadView.setLeftMsg(leftText);
            }
        }
        mHeadView.setLeftBackImageVisible(true);
        mHeadView.setTitle(getResources().getString(R.string.mine_setting));
        mHeadView.setBottomLineVisible(true);
        mHeadView.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportDelegate().pop();
            }
        });
    }
}
