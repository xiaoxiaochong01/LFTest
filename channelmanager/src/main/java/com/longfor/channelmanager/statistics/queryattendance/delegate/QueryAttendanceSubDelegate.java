package com.longfor.channelmanager.statistics.queryattendance.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.statistics.queryattendance.adapter.QueryAttendanceRvAdapter;
import com.longfor.channelmanager.statistics.queryattendance.handler.QueryAttendanceRefreshHandler;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.utils.UI.ScreenUtil;
import com.longfor.core.utils.log.LogUtils;
import com.longfor.ui.recycler.BaseDecoration;

import butterknife.BindView;

/**
 * @author: gaomei
 * @date: 2018/1/4
 * @function:考勤查询子页面-区分角色
 */

public class QueryAttendanceSubDelegate extends LongForDelegate implements QueryAttendanceRvAdapter.IQueryAttendanceClickPhotoListener {
    @BindView(R2.id.srl_query_attendance)
    SwipeRefreshLayout mSrlQueryAttendance;
    @BindView(R2.id.rv_query_attendance)
    RecyclerView mRvQueryAttendance;
    public QueryAttendanceRefreshHandler mRefreshHandler;
    public String mProjectId;
    public String mRoleType;
    private LongForDelegate mParentDelegate;
    private String TAG = "QueryAttendanceSubDelegate";

    public static QueryAttendanceSubDelegate getInstance(String roleType, String projectId, LongForDelegate parentDelegate) {
        QueryAttendanceSubDelegate delegate = new QueryAttendanceSubDelegate();
        delegate.mParentDelegate = parentDelegate;
        delegate.mRoleType = roleType;
        delegate.mProjectId = projectId;
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_statistics_query_attendance_sub;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        LogUtils.e(TAG, "onCreateView" + mRoleType);
        mRefreshHandler = QueryAttendanceRefreshHandler.create(mSrlQueryAttendance, mRvQueryAttendance,  mRoleType, mProjectId, this);
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshHandler.firstPage();
    }

    private void initSwipeRefreshLayout() {
        mSrlQueryAttendance.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSrlQueryAttendance.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(getContext(),1);
        final Context context = getContext();
        mRvQueryAttendance.setLayoutManager(manager);
        if (context != null) {
            mRvQueryAttendance.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(context, R.color.layout_bg_gray_f4), ScreenUtil.dip2px(getContext(), (float) 0.5)));
        }
    }

    @Override
    public void onClickPhoto(String imageUrl) {
        mParentDelegate.start(LargePhotoDelegate.getInstance(imageUrl));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.e(TAG, "onAttach" + mRoleType);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e(TAG, "onCreate" + mRoleType);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.e(TAG, "onActivityCreated" + mRoleType);
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.e(TAG, "onStart" + mRoleType);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e(TAG, "onResume" + mRoleType);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.e(TAG, "onPause" + mRoleType);
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.e(TAG, "onStop" + mRoleType);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.e(TAG, "onDestroyView" + mRoleType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG, "onDestroy" + mRoleType);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.e(TAG, "onDetach" + mRoleType);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.e(TAG, "setUserVisibleHint" + mRoleType);
    }
}
