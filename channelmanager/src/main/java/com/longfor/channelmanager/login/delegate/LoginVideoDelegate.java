package com.longfor.channelmanager.login.delegate;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.login.delegate.LoginDelegate;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.ui.login.view.ScreenMatchVideoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: tongzhenhua
 * @date: 2017/12/26
 * @function: 登录视频播放页
 */
public class LoginVideoDelegate extends LongForDelegate {
    @BindView(R2.id.vv_video)
    ScreenMatchVideoView videoView;
    private Uri videoUri; // 播放视频的地址

    @Override
    public Object setLayout() {
        return R.layout.delegate_login_video;
    }

    @OnClick(R2.id.btn_goto_login)
    void goToLogin() {
        getSupportDelegate().start(new LoginDelegate(), SINGLETASK);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        videoUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.video_login);
        videoView.playVideo(videoUri);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(videoView != null) {
            videoView.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(videoView != null && videoUri != null) {
            videoView.playVideo(videoUri);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(videoView != null) {
            videoView.stopPlayback();
        }
    }
}
