package com.longfor.channelmanager.statistics.queryattendance.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.statistics.queryattendance.adapter.IQueryAttendancexClickPhotoListener;
import com.longfor.channelmanager.statistics.queryattendance.adapter.QueryAttendanceConverter;
import com.longfor.channelmanager.statistics.queryattendance.adapter.QueryAttendanceRefreshHandler;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.ui.recycler.BaseDecoration;

import butterknife.BindView;

/**
 * @author: gaomei
 * @date: 2018/1/4
 * @function:考勤查询子页面-区分角色
 */

public class QueryAttendanceSubDelegate extends LongForDelegate implements IQueryAttendancexClickPhotoListener{
    @BindView(R2.id.srl_query_attendance)
    SwipeRefreshLayout mSrlQueryAttendance;
    @BindView(R2.id.rv_query_attendance)
    RecyclerView mRvQueryAttendance;
    public QueryAttendanceRefreshHandler mRefreshHandler;
    private String mProjectId;
    private String mRoleType;
    private LongForDelegate mParentDelegate;

    public static QueryAttendanceSubDelegate getInstance(String roleType, String projectId, LongForDelegate parentDelegate){
        Bundle bundle=new Bundle();
        bundle.putString(Constant.ROLE_TYPE,roleType);
        bundle.putString(Constant.PROJECT_ID,projectId);
        QueryAttendanceSubDelegate delegate=new QueryAttendanceSubDelegate();
        delegate.mParentDelegate=parentDelegate;
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_statistics_query_attendance_sub;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initData();
        mRefreshHandler = QueryAttendanceRefreshHandler.create(mSrlQueryAttendance, mRvQueryAttendance, new QueryAttendanceConverter(),this);
        initRefreshLayout();
        initRecyclerView();
    }

    private void initData() {
        mRoleType =getArguments().getString(Constant.ROLE_TYPE);
        mProjectId=getArguments().getString(Constant.PROJECT_ID);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshHandler.firstPage(mRoleType,mProjectId);
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        final Context context = getContext();
        mRvQueryAttendance.setLayoutManager(manager);
        if (context != null) {
            mRvQueryAttendance.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(context, com.longfor.ec.R.color.app_background), 5));
        }
    }

    private void initRefreshLayout() {
        mSrlQueryAttendance.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSrlQueryAttendance.setProgressViewOffset(true, 120, 300);
    }

    @Override
    public void onClickPhoto(String imageUrl) {
        mParentDelegate.start(LargePhotoDelegate.getInstance(imageUrl));
    }

    public void setProjectId(String projectId) {
        mProjectId = projectId;
    }
}
