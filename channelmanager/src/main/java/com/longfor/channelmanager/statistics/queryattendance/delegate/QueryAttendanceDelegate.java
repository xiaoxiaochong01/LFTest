package com.longfor.channelmanager.statistics.queryattendance.delegate;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.ec.project.IProjectChange;
import com.longfor.channelmanager.common.ec.project.ProjectsDataBean;
import com.longfor.channelmanager.common.ec.project.popupwindow.ProjectsPopWindow;
import com.longfor.channelmanager.common.view.CommonHeadView;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.channelmanager.statistics.queryattendance.adapter.QueryAttendancePagerAdapter;
import com.longfor.channelmanager.statistics.queryattendance.constants.ConstantQueryAttendance;
import com.longfor.core.delegates.LongForDelegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author: gaomei
 * @date: 2018/1/3
 * @function:考勤查询
 */

public class QueryAttendanceDelegate extends LongForDelegate implements IProjectChange {
    @BindView(R2.id.header_query_attendance)
    CommonHeadView mHeaderQueryAttendance;
    @BindView(R.id.tl_query_attendance)
    TabLayout mTlQueryAttendance;
    @BindView(R.id.vp_query_attendance)
    ViewPager mVpQueryAttendance;
    public String mProjectId;
    private ProjectsPopWindow mProjectWindow;
    public TextView mTvTitleRight;
    public List<Fragment> mFragmentList;
    public QueryAttendancePagerAdapter mPagerAdapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_statistics_query_attendance;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initData();
        initHeader();
        initTabLayout();
    }

    private void initData() {
        mProjectId = DatabaseManager.getProjectId();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initHeader() {
        Bundle arguments = getArguments();
        mHeaderQueryAttendance.setLeftMsg(arguments.getString(Constant.TITLE_LEFT_TEXT));
        mHeaderQueryAttendance.setLeftBackImageVisible(true);
        mHeaderQueryAttendance.setTitle(getString(R.string.query_attendance));
        mHeaderQueryAttendance.setRightTextViewVisible(true);
        mHeaderQueryAttendance.setRightTextViewText(DatabaseManager.getUserProfile().getProjectName());
        mTvTitleRight = (TextView) mHeaderQueryAttendance.findViewById(R.id.tv_head_common_right_text);
        mTvTitleRight.setCompoundDrawablePadding(5);
        mTvTitleRight.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.ic_arrow_down_s, 0);
        mHeaderQueryAttendance.setBottomLineVisible(true);
        mProjectWindow = new ProjectsPopWindow(getContext(), this);
        mHeaderQueryAttendance.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().pop();
            }
        });
        mHeaderQueryAttendance.setRightLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProjectWindow.showPopWindow(mTvTitleRight);
            }
        });
    }

    private void initTabLayout() {
        List<String> mTabTitles = Arrays.asList(new String[]{getString(R.string.trainee_role_show_get_num),
                getString(R.string.trainee_role_expand_get_num), getString(R.string.trainee_role_show_and_expand_call_num),
                getString(R.string.trainee_role_call_get_num), getString(R.string.trainee_role_call_call_num)});
        List<String> mRoleTypes = Arrays.asList(new String[]{ConstantQueryAttendance.SHOW_GET_NUM,
                ConstantQueryAttendance.EXPAND_GET_NUM, ConstantQueryAttendance.SHOW_AND_EXPAND_CALL_NUM,
                ConstantQueryAttendance.CALL_GET_NUM, ConstantQueryAttendance.CALL_CALL_NUM});
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < mTabTitles.size(); i++) {
            mFragmentList.add(QueryAttendanceSubDelegate.getInstance(mRoleTypes.get(i), mProjectId, this));
        }
        mPagerAdapter = new QueryAttendancePagerAdapter(getFragmentManager(), mTabTitles, mFragmentList);
        mVpQueryAttendance.setAdapter(mPagerAdapter);
        mTlQueryAttendance.setupWithViewPager(mVpQueryAttendance);
    }

    @Override
    public void changeSucess(ProjectsDataBean.DataBean.ProjectsBean projectsBean) {
        mProjectId = projectsBean.getProjectId();
        mTvTitleRight.setText(projectsBean.getProjectName());
        for (int i = 0; i < mFragmentList.size(); i++) {
            ((QueryAttendanceSubDelegate) mFragmentList.get(i)).mProjectId = mProjectId;
            if (mFragmentList.get(i).isAdded()) {
                ((QueryAttendanceSubDelegate) mFragmentList.get(i)).mRefreshHandler.updateParams(mProjectId);
            }
        }
    }
}
