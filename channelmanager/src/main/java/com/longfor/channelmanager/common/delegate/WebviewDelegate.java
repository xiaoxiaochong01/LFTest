package com.longfor.channelmanager.common.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.constants.Constant;
import com.longfor.channelmanager.common.view.commonheadview.CommonHeadView;
import com.longfor.core.delegates.LongForDelegate;

import butterknife.BindView;

/**
 * @author: tongzhenhua
 * @date: 2018/1/2
 * @function:
 */
public class WebviewDelegate extends LongForDelegate {
    @BindView(R2.id.webview_head)
    CommonHeadView mHeadView;
    @BindView(R2.id.webview)
    WebView mWebView;

    private String mUrl;

    @Override
    public Object setLayout() {
        return R.layout.delegate_webview;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        if(savedInstanceState != null) {
            String leftText = savedInstanceState.getString(Constant.TITLE_LEFT_TEXT);
            if(!TextUtils.isEmpty(leftText)) {
                mHeadView.setLeftTitleVisiable(true);
                mHeadView.setLeftTitle(leftText);
            }
            String title = savedInstanceState.getString(Constant.WEB_TITLE);
            mHeadView.setTitle(title);
            mUrl = savedInstanceState.getString(Constant.WEB_URL);
        }
        configWebView();
        if(mUrl != null) {
            mWebView.loadUrl(mUrl);
        }
        mHeadView.setLeftBackImageVisible(true);
        mHeadView.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mWebView.canGoBack()) {
                    mWebView.goBack();
                }
                else {
                    getSupportDelegate().pop();
                }
            }
        });
    }

    private void configWebView() {

    }
}
