package com.longfor.channelmanager.login;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.VideoView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.core.delegates.LongForDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: tongzhenhua
 * @date: 2017/12/26
 * @function: 登录视频播放页
 */
public class LoginVideoDelegate extends LongForDelegate {
    @BindView(R2.id.vv_video)
    VideoView videoView;
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
        if(videoUri == null) {
            throw new IllegalArgumentException("Uri can not be null");
        }
        // 设置视频播放路径
        videoView.setVideoURI(videoUri);
        videoView.start(); // 播放视频

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true); // 设置循环播放
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return true;
            }
        });
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
        if(videoView != null) {
            videoView.start();
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
