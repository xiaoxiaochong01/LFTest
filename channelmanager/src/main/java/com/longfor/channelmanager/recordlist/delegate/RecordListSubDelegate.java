package com.longfor.channelmanager.recordlist.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.core.delegates.LongForDelegate;

/**
 * @author: gaomei
 * @date: 2018/1/23
 * @function:
 */

public class RecordListSubDelegate extends LongForDelegate {
    public int mIsGroup;
    private String mCategory;
    private LongForDelegate mParentDelegate;

    public static RecordListSubDelegate getInstance(int isGroup, String category, LongForDelegate parentDelegate) {
        RecordListSubDelegate delegate=new RecordListSubDelegate();
        delegate.mIsGroup=isGroup;
        delegate.mCategory=category;
        delegate.mParentDelegate=parentDelegate;
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sub_record_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
