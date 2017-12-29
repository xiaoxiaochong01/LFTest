package com.longfor.ui.login.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

/**
 * @author: tongzhenhua
 * @date: 2017/12/26
 * @function:
 */
public class ScreenMatchVideoView extends VideoView {
    public ScreenMatchVideoView(Context context) {
        super(context);
    }

    public ScreenMatchVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScreenMatchVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScreenMatchVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec));
    }

    /**
     * 播放视频
     * @param uri
     */
    public void playVideo(Uri uri) {
        if(uri == null) {
            Log.e("ScreenMatchVideoView", "播放视频的Uri为null");
            return;
        }

        setVideoURI(uri);
        start();
        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true); // 设置循环播放
            }
        });
        setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return true;
            }
        });
    }
}
