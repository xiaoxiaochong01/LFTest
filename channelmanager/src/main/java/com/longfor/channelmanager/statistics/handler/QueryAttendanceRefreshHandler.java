package com.longfor.channelmanager.statistics.handler;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.channelmanager.statistics.constants.ConstantQueryAttendance;
import com.longfor.core.app.LongFor;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.ISuccess;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleRecyclerAdapter;
import com.longfor.ui.refresh.PagingBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghaitao1 on 2017/12/25.
 */

public class QueryAttendanceRefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private String mEmployeeId;
    private String mProjectId;
    private String mRoleType;

    public QueryAttendanceRefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT,
                                         PagingBean BEAN,
                                         RecyclerView RECYCLERVIEW,
                                         DataConverter CONVERTER) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        this.BEAN = BEAN;
        this.RECYCLERVIEW = RECYCLERVIEW;
        this.CONVERTER = CONVERTER;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static QueryAttendanceRefreshHandler creat(SwipeRefreshLayout swipeRefreshLayout,
                                                      RecyclerView recyclerView,
                                                      DataConverter converter) {
        return new QueryAttendanceRefreshHandler(swipeRefreshLayout, new PagingBean(), recyclerView, converter);
    }

    @Override
    public void onRefresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        LongFor.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);
    }

    public void firstPage(String roleType) {
        BEAN.setDelayed(1000);
        mEmployeeId = DatabaseManager.getEmployeeId();
        mProjectId = DatabaseManager.getUserProfile().getProjectId();
        mRoleType = roleType;
        Map<String, String> map = new HashMap<>();
        map.put(ConstantQueryAttendance.EMPLOYEE_ID, mEmployeeId);
        map.put(ConstantQueryAttendance.PROJECT_ID, mProjectId);
        map.put(ConstantQueryAttendance.ROLE_TYPE, mRoleType);
        map.put(ConstantQueryAttendance.CURRENT_PAGE, String.valueOf(1));
        map.put(ConstantQueryAttendance.PAGE_SIZE, String.valueOf(20));
        RestClient.builder()
                .url(ConstantQueryAttendance.URL_GET_CHECK_IN_LIST)
                .raw(map)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        int totals = 0;
                        if (!TextUtils.isEmpty(object.getJSONObject("data").getString("totals"))) {
                            totals = Integer.valueOf(object.getJSONObject("data").getString("totals"));
                        }
                        BEAN.setTotal(totals).setPageSize(20);
                        //设置adapter
                        mAdapter = MultipleRecyclerAdapter.creat(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(QueryAttendanceRefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.setCurrentCount(mAdapter.getData().size());
                        BEAN.addIndex();
                    }
                })
                .build()
                .post();
    }

    private void paging(final String url) {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();
        if (mAdapter.getData().size() < pageSize || currentCount > total) {
            mAdapter.loadMoreEnd(true);
        } else {
            final Map<String, String> map = new HashMap<>();
            map.put(ConstantQueryAttendance.EMPLOYEE_ID, mEmployeeId);
            map.put(ConstantQueryAttendance.PROJECT_ID, mProjectId);
            map.put(ConstantQueryAttendance.ROLE_TYPE, mRoleType);
            map.put(ConstantQueryAttendance.CURRENT_PAGE, String.valueOf(index));
            map.put(ConstantQueryAttendance.PAGE_SIZE, String.valueOf(20));
            LongFor.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestClient.builder()
                            .url(ConstantQueryAttendance.URL_GET_CHECK_IN_LIST)
                            .raw(map)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    mAdapter.addData(CONVERTER.setJsonData(response).convert());
                                    //累加数量
                                    BEAN.setCurrentCount(mAdapter.getData().size());
                                    mAdapter.loadMoreComplete();
                                    BEAN.addIndex();
                                }
                            })
                            .build()
                            .post();
                }
            }, 1000);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        paging("refresh.php?index=");
    }
}
