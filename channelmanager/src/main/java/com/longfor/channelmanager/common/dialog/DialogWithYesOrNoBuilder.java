package com.longfor.channelmanager.common.dialog;

import android.content.Context;

import com.longfor.channelmanager.common.dialog.listener.OnDialogConfimClickListener;

/**
 * @author: tongzhenhua
 * @date: 2018/1/18
 * @function:
 */
public class DialogWithYesOrNoBuilder {
    private Context context = null;
    private String title = null; // 标题内容
    private String des = null; // 提示内容
    private String ensure = null; // 确定按钮
    private String cancel = null;// 取消按钮
    private OnDialogConfimClickListener listener = null; // 按钮监听事件

    public DialogWithYesOrNoBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public DialogWithYesOrNoBuilder title(String title) {
        this.title = title;
        return this;
    }

    public DialogWithYesOrNoBuilder des(String des) {
        this.des = des;
        return this;
    }

    public DialogWithYesOrNoBuilder ensure(String ensure) {
        this.ensure = ensure;
        return this;
    }

    public DialogWithYesOrNoBuilder cancel(String cancel) {
        this.cancel = cancel;
        return this;
    }

    public DialogWithYesOrNoBuilder listener(OnDialogConfimClickListener listener) {
        this.listener = listener;
        return this;
    }

    public final DialogWithYesOrNo build() {
        return new DialogWithYesOrNo(
                context,
                title,
                des,
                ensure,
                cancel,
                listener
                );
    }
}
