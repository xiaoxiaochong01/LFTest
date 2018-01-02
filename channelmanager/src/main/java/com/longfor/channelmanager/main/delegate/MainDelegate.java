package com.longfor.channelmanager.main.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.channelmanager.login.LoginHandler;
import com.longfor.channelmanager.login.bean.LoginBean;
import com.longfor.channelmanager.login.delegate.LoginVideoDelegate;
import com.longfor.core.delegates.LongForDelegate;

import butterknife.OnClick;

/**
 * @author: tongzhenhua
 * @date: 2017/12/27
 * @function:
 */
public class MainDelegate extends LongForDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_main;
    }

    @OnClick(R2.id.bt_logout)
    void onLogoutClick() {
        LoginHandler.logout();
        getSupportDelegate().startWithPop(new LoginVideoDelegate());
    }
    @OnClick(R2.id.bt_test_toast1)
    void onToastAClick() {
        Toast.makeText(getContext(), "这是ToastA", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R2.id.bt_test_toast2)
    void onToastBClick() {
        Toast.makeText(getContext(), "这是ToastB", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
