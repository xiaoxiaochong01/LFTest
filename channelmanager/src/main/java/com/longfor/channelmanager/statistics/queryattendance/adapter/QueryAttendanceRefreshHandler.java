package com.longfor.channelmanager.statistics.queryattendance.adapter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.channelmanager.statistics.queryattendance.constants.ConstantQueryAttendance;
import com.longfor.core.app.LongFor;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.ISuccess;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.refresh.PagingBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gaomei
 * @date: 2018/1/4
 * @function:考勤查询
 */

public class QueryAttendanceRefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private QueryAttendanceRvAdapter mAdapter = null;
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

    public static QueryAttendanceRefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
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

    public void firstPage(String roleType, String projectId) {
        BEAN.setDelayed(1000);
        mEmployeeId = DatabaseManager.getEmployeeId();
        mRoleType = roleType;
        mProjectId = projectId;
        Map<String, String> map = new HashMap<>();
        map.put(Constant.EMPLOYEE_ID, mEmployeeId);
        map.put(Constant.PROJECT_ID, mProjectId);
        map.put(Constant.ROLE_TYPE, mRoleType);
        map.put(Constant.CURRENT_PAGE, String.valueOf(1));
        map.put(Constant.PAGE_SIZE, String.valueOf(20));
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
                        mAdapter = QueryAttendanceRvAdapter.create(CONVERTER.setJsonData(response));
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
            map.put(Constant.EMPLOYEE_ID, mEmployeeId);
            map.put(Constant.PROJECT_ID, mProjectId);
            map.put(Constant.ROLE_TYPE, mRoleType);
            map.put(Constant.CURRENT_PAGE, String.valueOf(index));
            map.put(Constant.PAGE_SIZE, String.valueOf(20));
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
