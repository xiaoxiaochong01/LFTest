package com.longfor.channelmanager.common.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.dialog.listener.OnDialogConfimClickListener;

/**
 * @author: tongzhenhua
 * @date: 2017/12/28
 * @function:
 */
public class DialogWithYesOrNo {
    AlertDialog mAlertDialog;

//    public DialogWithYesOrNo(Context context, String title, String des, final @NonNull OnDialogConfimClickListener confimClickListener) {
//        String yes = context.getResources().getString(R.string.ensure);
//        String no = context.getResources().getString(R.string.cancel);
//        this(context, title, des, yes, no, confimClickListener);
//    }

    public DialogWithYesOrNo(Context context, String title, String des, String yes, String no, final @NonNull OnDialogConfimClickListener confimClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_with_yes_or_no, null);
        builder.setView(view);
        mAlertDialog = builder.create();
        WindowManager windowManager = mAlertDialog.getWindow().getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams p = mAlertDialog.getWindow().getAttributes();
        p.width = defaultDisplay.getWidth() - 100; //设置dialog的宽度为当前手机屏幕的宽度-100
        mAlertDialog.getWindow().setAttributes(p);
        mAlertDialog.setCanceledOnTouchOutside(false);
        TextView tvDialogTitle= view.findViewById(R.id.tv_dialog_title);
        TextView tvDialogDes= view.findViewById(R.id.tv_dialog_des);
        TextView tvDialogCancel= view.findViewById(R.id.tv_dialog_cancel);
        TextView tvDialogConfirm= view.findViewById(R.id.tv_dialog_confirm);
        tvDialogTitle.setText(title);
        if (TextUtils.isEmpty(des)){
            tvDialogDes.setVisibility(View.GONE);
        }else {
            tvDialogDes.setText(des);
        }
        tvDialogConfirm.setText(yes);
        tvDialogCancel.setText(no);
        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confimClickListener.onCancelClick();
                mAlertDialog.dismiss();
            }
        });
        tvDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confimClickListener.onEnsureClick();
                mAlertDialog.dismiss();
            }
        });
    }

    public void showDialog(){
        if (mAlertDialog!=null) {
            mAlertDialog.show();
        }
    }

    public void dismissDialog(){
        if (mAlertDialog!=null){
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }

    public boolean isShowing(){
        return mAlertDialog.isShowing();
    }
}
