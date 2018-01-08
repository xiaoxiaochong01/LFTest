package com.longfor.ui.recycler;

import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by zhanghaitao1 on 2018/1/2.
 */

public class MultipleViewHolder extends BaseViewHolder{
    public MultipleViewHolder(View view) {
        super(view);
    }

    public static MultipleViewHolder creat(View view){
        return new MultipleViewHolder(view);
    }

    @Override
    public BaseViewHolder setOnItemSelectedClickListener(int viewId, AdapterView.OnItemSelectedListener listener) {
        return super.setOnItemSelectedClickListener(viewId, listener);
    }
}
