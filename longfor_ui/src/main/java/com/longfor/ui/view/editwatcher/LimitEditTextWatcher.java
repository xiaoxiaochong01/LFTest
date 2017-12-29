package com.longfor.ui.view.editwatcher;

import android.content.Context;
import android.text.Editable;
import android.widget.Toast;

/**
 * @author: tongzhenhua
 * @date: 2017/11/29
 * @function:
 */
public class LimitEditTextWatcher extends EditTextWatcher {
    private Context context;
    private int count; // 最大字数

    public LimitEditTextWatcher(Context context, int count) {
        this.context = context;
        this.count = count;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        super.afterTextChanged(editable);
        int size = editable.toString().trim().length();
        if(size >= count) {
            Toast.makeText(context, "最大字数限制是"+count,Toast.LENGTH_SHORT).show();
        }
    }
}
