package com.longfor.channelmanager.mine.delegate;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.constants.Constant;
import com.longfor.channelmanager.common.delegate.WebviewDelegate;
import com.longfor.channelmanager.common.view.commonheadview.CommonHeadView;
import com.longfor.core.delegates.LongForDelegate;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author: tongzhenhua
 * @date: 2018/1/2
 * @function:
 */

public class AboutUsDelegate extends LongForDelegate {
    @BindView(R2.id.about_us_head)
    CommonHeadView mHeadView;
    @BindView(R2.id.tv_version)
    AppCompatTextView tvVersion;

    @Override
    public Object setLayout() {
        return R.layout.delegate_about_us;
    }

    @OnClick(R2.id.tv_function_introduction)
    void onFunctionIntroClick() {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE_LEFT_TEXT, getResources().getString(R.string.mine_about_us));
        bundle.putString(Constant.WEB_URL,"http://www.longfor.com");
        bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.about_us_function_intro));
        WebviewDelegate supportFragment = new WebviewDelegate();
//        supportFragment.putNewBundle(bundle);
        supportFragment.setArguments(bundle);
        getSupportDelegate().start(supportFragment);
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
        mHeadView.setTitle(getResources().getString(R.string.mine_about_us));
        mHeadView.setBottomLineVisible(true);
        mHeadView.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportDelegate().pop();
            }
        });
        tvVersion.setText(getResources().getString(R.string.string_version)+getAppVersion());
    }

    /**
     * 获取版本信息
     * @return
     */
    private String getAppVersion() {

        PackageManager packageManager = getContext().getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(getContext().getPackageName(), 0);
            return  info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return getResources().getString(R.string.string_can_not_find_version_name);
        }
    }
}
