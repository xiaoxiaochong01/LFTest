package com.longfor.ui.view.editwatcher;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author: tongzhenhua
 * @date: 2017/11/28
 * @function:
 */
public class EditTextWatcher implements TextWatcher {
    /**
     *  该方法在文本改变之前调用，传入了四个参数：
     * @param s 文本改变之前的内容
     * @param start 文本开始改变时的起点位置，从0开始计算
     * @param count 要被改变的文本字数，即将要被替代的选中文本字数
     * @param after 改变后添加的文本字数，即替代选中文本后的文本字数
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * 该方法是在当文本改变时被调用，传入了四个参数：
     * @param s 文本改变之后的内容
     * @param start 文本开始改变时的起点位置，从0开始计算
     * @param before 要被改变的文本字数，即已经被替代的选中文本字数
     * @param count 改变后添加的文本字数，即替代选中文本后的文本字数
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * 该方法是在文本改变结束后调用，传入了一个参数：
     * @param s 改变后的最终文本
     */
    @Override
    public void afterTextChanged(Editable s) {

    }
}
