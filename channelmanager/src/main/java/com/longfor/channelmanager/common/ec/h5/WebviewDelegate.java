package com.longfor.channelmanager.common.ec.h5;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.ec.Constant;
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
        Bundle bundle = getArguments();
        if(bundle != null) {
            String leftText = bundle.getString(Constant.TITLE_LEFT_TEXT);
            if(!TextUtils.isEmpty(leftText)) {
                mHeadView.setLeftMsgVisiable(true);
                mHeadView.setLeftMsg(leftText);
            }
            String title = bundle.getString(Constant.WEB_TITLE);
            mHeadView.setTitle(title);
            mUrl = bundle.getString(Constant.WEB_URL);
            if(getResources().getString(R.string.feed_back_title).equals(title)) {
                mHeadView.setRightTextViewVisible(true);
                mHeadView.setRightTextViewText(getResources().getString(R.string.feed_back_list));
                mHeadView.setRightTextViewOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.TITLE_LEFT_TEXT, getResources().getString(R.string.feed_back_title));
                        bundle.putString(Constant.WEB_URL,"http://www.longfor.com");
                        bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.feed_back_list));
                        WebviewDelegate supportFragment = new WebviewDelegate();
                        supportFragment.setArguments(bundle);
                        getSupportDelegate().start(supportFragment);
                    }
                });
            }
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
