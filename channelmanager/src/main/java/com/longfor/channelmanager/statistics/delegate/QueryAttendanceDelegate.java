package com.longfor.channelmanager.statistics.delegate;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.constants.Constant;
import com.longfor.channelmanager.common.view.commonheadview.CommonHeadView;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.utils.toast.ToastUtils;

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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        Bundle arguments = getArguments();
        mHeaderQueryAttendance.setLeftMsg(arguments.getString(Constant.TITLE_LEFT_TEXT));
        mHeaderQueryAttendance.setLeftBackImageVisible(true);
        mHeaderQueryAttendance.setTitle(getString(R.string.query_attendance));
        mHeaderQueryAttendance.setBottomLineVisible(true);
        mHeaderQueryAttendance.setRightTextViewVisible(true);
        mHeaderQueryAttendance.setRightTextViewText(DatabaseManager.getUserProfile().getProjectName());
        TextView tvRight = (TextView) mHeaderQueryAttendance.findViewById(R.id.tv_head_common_right_text);
        tvRight.setCompoundDrawablePadding(5);
        tvRight.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.mipmap.ic_arrow_down_s,0);
        mHeaderQueryAttendance.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().pop();
            }
        });
        mHeaderQueryAttendance.setRightLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showMessage(getContext(), getString(R.string.index_statistics_title));
            }
        });
    }
}
