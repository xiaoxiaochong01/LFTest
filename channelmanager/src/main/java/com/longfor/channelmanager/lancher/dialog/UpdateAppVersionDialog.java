package com.longfor.channelmanager.lancher.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.lancher.dialog.listener.OnUpdataVersionClickListener;

/**
 * Created by lsb18 on 2017/9/20.
 */

public class UpdateAppVersionDialog extends Dialog implements View.OnClickListener{

    private OnUpdataVersionClickListener listener;
    private Button mBtnUpgrade;
    private LinearLayout mGroupClose;
    private ImageView mImgClose;
    private TextView mTvUpgradeInfo;

    private String mMessage;
    private int mUpgrade;

    private Handler mHandler;

    public UpdateAppVersionDialog(@NonNull Context context) {
        super(context);
    }

    public UpdateAppVersionDialog(@NonNull Context context, String msg, int upGrade, @StyleRes int themeResId, OnUpdataVersionClickListener listener) {
        super(context, themeResId);
        this.listener = listener;
        mMessage = msg;
        mUpgrade = upGrade;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_version);
        mBtnUpgrade = findViewById(R.id.btn_upgrade);
        mImgClose = findViewById(R.id.img_close);
        mGroupClose = findViewById(R.id.ll_group_close);
        mTvUpgradeInfo = findViewById(R.id.tv_update_info);

        mBtnUpgrade.setOnClickListener(this);
        mImgClose.setOnClickListener(this);
        setCanceledOnTouchOutside(false);

        initUI();
    }

    /**
     * 设置 升级对话框 显示信息

     */
    private void initUI() {
        mTvUpgradeInfo.setText(mMessage);
        if (mUpgrade == 1) {
            mGroupClose.setVisibility(View.VISIBLE);
        } else if (mUpgrade == 2) {
            mGroupClose.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upgrade:
                listener.updata();
                break;
            case R.id.img_close:
                listener.close();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        listener.cancle();
    }
}
