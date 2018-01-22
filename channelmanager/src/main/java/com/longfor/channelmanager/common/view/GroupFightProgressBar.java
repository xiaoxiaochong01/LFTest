package com.longfor.channelmanager.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.longfor.channelmanager.R;


/**
 * @author: gaomei
 * @date: 2017/8/31
 * @function: 团队作战的progressBar，显示进度，左侧文本，右侧文本
 */

public class GroupFightProgressBar extends FrameLayout {
    public ProgressBar mProgressBar;
    public TextView mTvLeft;
    public TextView mTvRight;

    public GroupFightProgressBar(Context context) {
        this(context,null);
    }

    public GroupFightProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view=View.inflate(context, R.layout.progress_group_fight,null);
        mProgressBar = view.findViewById(R.id.progress);
        mTvLeft = view.findViewById(R.id.tv_left);
        mTvRight = view.findViewById(R.id.tv_right);
        addView(view);
    }

    public void setLeftText(String leftText){
        mTvLeft.setText(leftText);
    }

    public void setRightText(String rightText){
        mTvRight.setText(rightText);
    }

    public void setProgress(int progress){
        mProgressBar.setProgress(progress);
    }
}
