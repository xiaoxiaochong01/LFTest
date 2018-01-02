package com.longfor.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.longfor.ui.R;

/**
 * @author: tongzhenhua
 * @date: 2017/12/27
 * @function:
 */
public class DialogHint {
    private AlertDialog mAlertDialog;

    public DialogHint(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.string_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancle();
            }
        });

        mAlertDialog = builder.create();
        mAlertDialog.setCancelable(false);
    }

    /**
     *
     */
    public void show() {
        if(mAlertDialog !=null) {
            mAlertDialog.show();
        }
    }

    public void cancle() {
        if(mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }
}
