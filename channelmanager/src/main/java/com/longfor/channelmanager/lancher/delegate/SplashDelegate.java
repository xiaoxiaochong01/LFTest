package com.longfor.channelmanager.lancher.delegate;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.lancher.bean.UpdateVersionBaen;
import com.longfor.channelmanager.lancher.constants.Constant;
import com.longfor.channelmanager.lancher.dialog.UpdateAppVersionDialog;
import com.longfor.channelmanager.lancher.dialog.listener.OnUpdataVersionClickListener;
import com.longfor.channelmanager.login.LoginVideoDelegate;
import com.longfor.core.app.LongFor;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.IError;
import com.longfor.core.net.callback.ISuccess;
import com.longfor.core.utils.log.LogUtils;
import com.longfor.core.utils.storage.LongForPreference;
import com.longfor.core.utils.toast.ToastUtils;
import com.longfor.ec.launcher.LauncherScrollDelegate;
import com.longfor.ui.launcher.ScrollLauncherTag;
import com.longfor.ui.login.LoginTag;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @author: tongzhenhua
 * @date: 2017/12/25
 * @function:
 */
public class SplashDelegate extends LongForDelegate implements ISuccess, IError{
    @BindView(R2.id.tv_version)
    TextView mTvAppVersion;
    private String mAppVersion;

    private UpdateAppVersionDialog dialog;
    private UpdateVersionBaen.DataBean versionInfo;
    private int appUpgrade = Constant.CONSTANT_NO_FORCE_UPDATE;


    @Override
    public Object setLayout() {
        return R.layout.delegate_splash;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mAppVersion = getAppVersion();
        mTvAppVersion.setText(getResources().getString(R.string.string_version) + mAppVersion);
        doBusiness();
    }

    protected void doBusiness() {
        Map<String, String> map = new HashMap<>();
        map.put(Constant.APP_ID, getResources().getString(R.string.string_version));
        map.put(Constant.VERSION, mAppVersion);
        map.put(Constant.CLIENT, "0"); //(0：android、1：ios)
        RestClient.builder()
                .url(Constant.URL_VERSION_CHECK)
                .raw(map)
                .success(this)
                .error(this)
                .loader(getContext())
                .build()
                .post();

    }

    /**
     *
     * @param delayTime 单位毫秒
     */
    private void delayLoad(int delayTime) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoNextPage();
            }
        }, delayTime);
    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    private String getAppVersion() {
        try {
            PackageManager manager = getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
            mAppVersion = info.versionName;
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return getResources().getString(R.string.string_can_not_find_version_name);
        }
    }

    /**
     * 打开下载链接
     */
    private void downloadNewApp(String downloadUrl) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(downloadUrl);
        intent.setData(uri);
        startActivity(intent);
    }

    /**
     * 跳转下个页面逻辑
     */
    private void gotoNextPage() {
        //2 表示版本为强更，点击返回键，退出app
        if (appUpgrade == Constant.CONSTANT_FORCE_UPDATE) {
            getActivity().finish();
            return;
        }

        if(LongForPreference.getAppFlag(LoginTag.IS_LOGIN.name())) {
//            getSupportDelegate().startWithPop();
            ToastUtils.showMessage(getContext(), "跳转首页");
        }
        else {
            if(LongForPreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
                // 跳转登录页
                getSupportDelegate().start(new LoginVideoDelegate(), SINGLETASK);
            }
            else {
                // 跳转引导页
                 getSupportDelegate().start(new GuideDelegate());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 网络请求成功回调
     * @param response
     */
    @Override
    public void onSuccess(String response) {
        LogUtils.e("版本信息接口", response);
        UpdateVersionBaen entity = JSON.parseObject(response, UpdateVersionBaen.class);
        if (entity != null && entity.getCode() == 0) {
            versionInfo = entity.getData();
            if (versionInfo != null && versionInfo.getUpgrade() != 0) {
                appUpgrade = versionInfo.getUpgrade();
                dialog = new UpdateAppVersionDialog(getContext(), versionInfo.getMessage(), versionInfo.getUpgrade(), R.style.custom_dialog_version, new OnUpdataVersionClickListener() {
                    @Override
                    public void updata() {
                        downloadNewApp(versionInfo.getDownloadUrl());
                    }

                    @Override
                    public void close() {
                        dialog.dismiss();
                        delayLoad(1000);
                    }

                    @Override
                    public void cancle() {
                        delayLoad(0);
                    }
                }) ;
                dialog.show();
            } else {
                delayLoad(2000);
            }
        } else {
            delayLoad(2000);
        }
    }

    /**
     * 网络请求失败回调
     * @param code
     * @param msg
     */
    @Override
    public void onError(int code, String msg) {
        ToastUtils.showMessage(getContext(), msg);
    }
}
