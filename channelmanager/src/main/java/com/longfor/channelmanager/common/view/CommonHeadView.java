package com.longfor.channelmanager.common.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.longfor.channelmanager.R;

/**
 * Created by zhanghaitao1 on 2017/8/18.
 */

public class CommonHeadView extends FrameLayout {
    private View layoutLeft;
    private View layoutRight;
    private TextView tvTitle;
    private TextView leftTitle;
    private TextView leftMsg;
    private TextView tvRightText;
    private ImageView imgBack;
    private ImageView imgMsg;
    private ImageView imgRight;
    private View leftTopRedDot;
    private View rightTopRedDot;
    private Context mContext;
    private View commonRelative;
    private View bottomLine;
    private ObjectAnimator inAnim;
    private ObjectAnimator outAnim;

    public CommonHeadView(@NonNull Context context) {
       this(context,null);
    }

    public CommonHeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.common_head_view, null);
        commonRelative = view.findViewById(R.id.id_common_relative_bg);
        layoutLeft = view.findViewById(R.id.rl_head_common_left_btn_set);
        layoutRight = view.findViewById(R.id.rl_head_common_right_btn_set);
        tvTitle = view.findViewById(R.id.tv_head_common_title);
        tvRightText = view.findViewById(R.id.tv_head_common_right_text);
        imgBack = view.findViewById(R.id.iv_head_common_back);
        imgMsg = view.findViewById(R.id.iv_head_common_msg);
        imgRight = view.findViewById(R.id.iv_head_common_right_icon);
        leftTopRedDot = view.findViewById(R.id.iv_head_common_unread_dot);
        rightTopRedDot = view.findViewById(R.id.iv_head_common_new_device_dot);
        leftTitle = view.findViewById(R.id.tv_head_common_left_title);
        bottomLine = view.findViewById(R.id.vi_head_common_bottom_line);
        leftMsg = view.findViewById(R.id.tv_head_common_left_text);
        addView(view);
    }
    public void setCommonHeadBg(int bgColor) {
        commonRelative.setBackgroundColor(bgColor);
    }

    public void setLeftTopRedDotVisible(boolean visible) {
        leftTopRedDot.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightTopRedDotVisible(boolean visible) {
        rightTopRedDot.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setTitleTextVisible(boolean visible) {
        tvTitle.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public int getTitleTextVisible() {
        return tvTitle.getVisibility();
    }

    public void setLeftLayoutVisible(boolean visible) {
        layoutLeft.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setLeftBackImageVisible(boolean visible) {
        imgBack.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightLayoutVisible(boolean visible) {
        layoutRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void enableRightButton() {
        layoutRight.setEnabled(true);
        tvRightText.setEnabled(true);
    }

    public void disableRightButton() {
        layoutRight.setEnabled(false);
        tvRightText.setEnabled(false);
    }

    public void showRightText(boolean enable) {
        tvRightText.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    public void enableRightText(boolean enable) {
        tvRightText.setEnabled(enable);
    }

    public void setRightLayoutEnable(boolean enable) {
        layoutRight.setEnabled(enable);
    }

    public void setRightTextViewText(String rightText) {
        tvRightText.setText(rightText);
    }

    public void setRightTextViewText(int resId) {
        tvRightText.setText(resId);
    }

    public void setRightTextViewClickable(boolean isClickable) {
        layoutRight.setEnabled(isClickable);
        tvRightText.setEnabled(isClickable);
    }

    public void setRightTextViewVisible(boolean visible) {
        tvRightText.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    public void setLeftLayoutOnClickListener(OnClickListener clickListener) {
        layoutLeft.setOnClickListener(clickListener);
    }

    public void setLeftMsgImageVisible(boolean visible) {
        imgMsg.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setLeftMsgImageOnClickListener(OnClickListener clickListener) {
        imgMsg.setOnClickListener(clickListener);
    }

    public void setRightTextViewOnClickListener(OnClickListener clickListener) {
        tvRightText.setOnClickListener(clickListener);
    }

    public TextView getRightTextView(){
        return tvRightText;
    }

    public void setRightLayoutOnClickListener(OnClickListener clickListener) {
        layoutRight.setOnClickListener(clickListener);
    }

    public void setRightImageVisible(boolean visible) {
        imgRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightImageOnClickListener(OnClickListener clickListener) {
        imgRight.setOnClickListener(clickListener);
    }

    public void setRightImageSrc(int resId) {
        imgRight.setImageResource(resId);
    }

    public void setTitleText(String string) {
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(string);
    }

    public void setTitleText(int resId) {
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(resId);
    }

    public void setTitleSizeSP(int spSize) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }

    public void setTitle(String string) {
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(string);
    }

    public void setTitle(int resId) {
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(resId);
    }

    public float getTitleAlpha() {
        return tvTitle.getAlpha();
    }

    public void setTitleInWithAnim(String string, int milSecond) {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(string);
        if (inAnim == null)
            inAnim = ObjectAnimator.ofFloat(tvTitle, "alpha", 0f, 1f);
        if (tvTitle.getAlpha() == 0 && !inAnim.isRunning()) {
            inAnim.setDuration(milSecond);
            inAnim.start();
        }
    }

    public void setTitleOutWithAnim(String string, int milSecond) {
        if (outAnim == null)
            outAnim = ObjectAnimator.ofFloat(tvTitle, "alpha", 1.0f, 0f);
        if (tvTitle.getAlpha() == 1 && !outAnim.isRunning()) {
            outAnim.setDuration(milSecond);
            outAnim.start();
        }
    }

    public void showBackBtn(boolean enable) {
        imgBack.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    public void hideLeftView() {
        layoutLeft.setVisibility(View.GONE);
    }


    public void setLeftTitleVisiable(boolean visiable) {
        leftTitle.setVisibility(visiable ? View.VISIBLE : View.GONE);
    }

    public void setLeftTitle(String leftTitleStr) {
        leftTitle.setVisibility(View.VISIBLE);
        leftTitle.setText(leftTitleStr);
    }

    public void setLeftMsgVisiable(boolean visiable) {
        leftMsg.setVisibility(visiable ? View.VISIBLE : View.GONE);
    }

    public void setLeftMsg(String leftMsgStr) {
        leftMsg.setVisibility(View.VISIBLE);
        leftMsg.setText(leftMsgStr);
    }

    public void setBottomLineVisible(boolean visible) {
        bottomLine.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
