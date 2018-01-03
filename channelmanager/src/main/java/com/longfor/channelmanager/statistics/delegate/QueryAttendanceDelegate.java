package com.longfor.channelmanager.statistics.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.view.commonheadview.CommonHeadView;
import com.longfor.core.delegates.LongForDelegate;

import butterknife.BindView;

/**
 * @author: gaomei
 * @date: 2018/1/3
 * @function:考勤查询
 */

public class QueryAttendanceDelegate extends LongForDelegate {
    @BindView(R2.id.header_query_attendance)
    CommonHeadView mHeaderQueryAttendance;

    @Override
    public Object setLayout() {
        return R.layout.delegate_statistics_query_attendance;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mHeaderQueryAttendance.setTitle(getString(R.string.query_attendance));
        mHeaderQueryAttendance.setBottomLineVisible(true);
    }
}
