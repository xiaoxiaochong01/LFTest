package com.longfor.channelmanager.main.delegate.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.core.delegates.bottomreplace.BottomItemDelegate;

/**
 * @author: tongzhenhua
 * @date: 2017/12/28
 * @function:
 */
public class IndexStatisticsDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_index_statistics;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
