package com.longfor.channelmanager.main.index.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.view.CommonHeadView;
import com.longfor.channelmanager.statistics.checkinstatistics.constant.CheckInStatisticsConstant;
import com.longfor.channelmanager.statistics.checkinstatistics.delegate.CheckInStatisticsDelegate;
import com.longfor.channelmanager.statistics.queryattendance.delegate.QueryAttendanceDelegate;
import com.longfor.core.delegates.bottomreplace.BottomItemDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: tongzhenhua
 * @date: 2017/12/28
 * @function:首页数+
 */
public class IndexStatisticsDelegate extends BottomItemDelegate {
    @BindView(R2.id.index_statistics_head)
    CommonHeadView mIndexStatisticsHead;
    @BindView(R2.id.iv_statistics_check_in)
    AppCompatImageView mIvStatisticsCheckIn;
    @BindView(R2.id.iv_statistics_query_attendance)
    AppCompatImageView mIvStatisticsQueryAttendance;

    @Override
    public Object setLayout() {
        return R.layout.delegate_index_statistics;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mIndexStatisticsHead.setTitle(getString(R.string.index_statistics_title));
        mIndexStatisticsHead.setBottomLineVisible(true);
    }

    @OnClick(R2.id.iv_statistics_check_in)
    void onCheckInClick() {
        CheckInStatisticsDelegate delegate = CheckInStatisticsDelegate.getInstance(
                CheckInStatisticsConstant.ITEM_TYPE_COMPANY, getString(R.string.index_statistics_title),
                String.valueOf(0),null);
        getParentDelegate().start(delegate);
    }

    @OnClick(R2.id.iv_statistics_query_attendance)
    void onQueryAttendanceClick() {
        QueryAttendanceDelegate delegate = QueryAttendanceDelegate.getInstance(getString(R.string.index_statistics_title));
        getParentDelegate().start(delegate);
    }
}
