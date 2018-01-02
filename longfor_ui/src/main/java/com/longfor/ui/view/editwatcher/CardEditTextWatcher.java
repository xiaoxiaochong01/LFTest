package com.longfor.ui.view.editwatcher;

import android.widget.EditText;

import com.longfor.ui.view.editwatcher.utils.TextWatcherFormatUtil;


/**
 * @author: tongzhenhua
 * @date: 2017/11/29
 * @function: 银行卡号输入控件监听器
 */
public class CardEditTextWatcher extends EditTextWatcher {
    private EditText editText;

    public CardEditTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        if (s == null) {
            return;
        }
        //判断是否是在中间输入，需要重新计算
        boolean isMiddle = (start + count) < (s.length());
        //在末尾输入时，是否需要加入空格
        boolean isNeedSpace = false;
        if (!isMiddle && s.length() > 0 && s.length() % 5 == 0) {
            isNeedSpace = true;
        }
        if (isMiddle || isNeedSpace) {
            String sb = TextWatcherFormatUtil.toCardFormat(s.toString());
            editText.removeTextChangedListener(this);
            editText.setText(sb);
            //如果是在末尾的话,或者加入的字符个数大于零的话（输入或者粘贴）
            if (!isMiddle || count > 1) {
                editText.setSelection(sb.length());
            } else if (isMiddle) {
                //如果是删除
                if (count == 0) {
                    //如果删除时，光标停留在空格的前面，光标则要往前移一位
                    if ((start - before + 1) % 5 == 0) {
                        editText.setSelection((start - before) > 0 ? start - before : 0);
                    } else {
                        editText.setSelection((start - before + 1) > sb.length() ? sb.length() : (start - before + 1));
                    }
                }
                //如果是增加
                else {
                    if ((start - before + count) % 5 == 0) {
                        editText.setSelection((start + count - before + 1) < sb.length() ? (start + count - before + 1) : sb.length());
                    } else {
                        editText.setSelection(start + count - before);
                    }
                }
            }
            editText.addTextChangedListener(this);
        }
    }
}
