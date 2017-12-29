package com.longfor.ui.view.counttimer;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;

import com.longfor.ui.R;

/**
 * @author: tongzhenhua
 * @date: 2017/12/26
 * @function:
 */
public class CommonCountDownTimer extends CountDownTimer {
    TextView mTextView;
    String strDownText;
    String strGetAgin;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CommonCountDownTimer(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        mTextView=textView;
    }

    /**
     * 设置倒计时显示字符
     * @param des 倒计时显示字符
     * @return
     */
    public CommonCountDownTimer setDownText(String des) {
        this.strDownText = des;
        return this;
    }

    public CommonCountDownTimer setGetAginText(String getAgin) {
        this.strGetAgin = getAgin;
        return this;
    }
    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false);
        if(TextUtils.isEmpty(strDownText)) {
            mTextView.setText("重新获取"+"("+millisUntilFinished/1000+")"+"秒");
        }
        else {
            mTextView.setText(strDownText);
        }

        mTextView.setTextColor(mTextView.getResources().getColor(R.color.code_enable));
    }

    @Override
    public void onFinish() {
        if(TextUtils.isEmpty(strGetAgin)) {
            mTextView.setText("重新获取");
        } else {
            mTextView.setText(strGetAgin);
        }
        mTextView.setClickable(true);
        mTextView.setTextColor(mTextView.getResources().getColor(R.color.code_enable));
    }
}
