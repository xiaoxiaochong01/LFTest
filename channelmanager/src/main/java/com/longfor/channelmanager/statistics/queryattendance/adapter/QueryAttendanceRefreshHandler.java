package com.longfor.channelmanager.statistics.queryattendance.adapter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.ec.baseadapter.BaseRefreshHandler;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.channelmanager.statistics.queryattendance.bean.CheckInListBean;
import com.longfor.channelmanager.statistics.queryattendance.constants.ConstantQueryAttendance;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.DataConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gaomei
 * @date: 2018/1/4
 * @function:考勤查询
 */

public class QueryAttendanceRefreshHandler extends BaseRefreshHandler {
    private String mEmployeeId;
    private String mProjectId;
    private String mRoleType;
    IQueryAttendanceClickPhotoListener mIQueryAttendancexClickPhotoListener;

    public QueryAttendanceRefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT, RecyclerView RECYCLERVIEW,
                                         DataConverter CONVERTER, String roleType, String projectId,
                                         IQueryAttendanceClickPhotoListener listener) {
        super(REFRESH_LAYOUT, RECYCLERVIEW, CONVERTER);
        mRoleType = roleType;
        mProjectId = projectId;
        mIQueryAttendancexClickPhotoListener = listener;
    }

    public static QueryAttendanceRefreshHandler create(SwipeRefreshLayout REFRESH_LAYOUT, RecyclerView RECYCLERVIEW,
                                                       DataConverter CONVERTER, String roleType, String projectId,
                                                       IQueryAttendanceClickPhotoListener listener) {
        return new QueryAttendanceRefreshHandler(REFRESH_LAYOUT, RECYCLERVIEW, CONVERTER, roleType, projectId, listener);
    }

    @Override
    public String getUrl() {
        return ConstantQueryAttendance.URL_GET_CHECK_IN_LIST;
    }

    @Override
    public Map<String, String> getParams() {
        mEmployeeId = DatabaseManager.getEmployeeId();
        Map<String, String> map = new HashMap<>();
        map.put(Constant.EMPLOYEE_ID, mEmployeeId);
        map.put(Constant.PROJECT_ID, mProjectId);
        map.put(Constant.ROLE_TYPE, mRoleType);
        map.put(Constant.CURRENT_PAGE, String.valueOf(1));
        map.put(Constant.KEY_PAGE_SIZE, Constant.VALUE_PAGE_SIZE);
        return map;
    }

    @Override
    public int getPageSize() {
        return getDefaultPageSize();
    }

    @Override
    public int getTotals(String response) {
        CheckInListBean checkInListBean = JSON.parseObject(response, CheckInListBean.class);
        return checkInListBean.getData().getTotals();
    }

    @Override
    public BaseRecyclerAdapter getAdapter(DataConverter converter) {
        return QueryAttendanceRvAdapter.create(converter, mIQueryAttendancexClickPhotoListener);
    }

    @Override
    public BaseRefreshHandler updateParams(String... params) {
        if (params != null && params.length == 1) {
            mProjectId = params[0];
        }
        return this;
    }

    /*private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private QueryAttendanceRvAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private String mEmployeeId;
    private String mProjectId;
    private String mRoleType;
    IQueryAttendanceClickPhotoListener mIQueryAttendancexClickPhotoListener;

    public QueryAttendanceRefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT,
                                         PagingBean BEAN,
                                         RecyclerView RECYCLERVIEW,
                                         DataConverter CONVERTER,
                                         IQueryAttendanceClickPhotoListener listener) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        this.BEAN = BEAN;
        this.RECYCLERVIEW = RECYCLERVIEW;
        this.CONVERTER = CONVERTER;
        mIQueryAttendancexClickPhotoListener=listener;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static QueryAttendanceRefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                                       RecyclerView recyclerView,
                                                       DataConverter converter,
                                                       IQueryAttendanceClickPhotoListener listener) {
        return new QueryAttendanceRefreshHandler(swipeRefreshLayout, new PagingBean(), recyclerView, converter,listener);
    }

    @Override
    public void onRefresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        LongFor.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                firstPage(mRoleType,mProjectId);
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
        map.put(Constant.KEY_PAGE_SIZE, Constant.VALUE_PAGE_SIZE);
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
                        mAdapter = QueryAttendanceRvAdapter.create(CONVERTER.setJsonData(response),mIQueryAttendancexClickPhotoListener);
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
            map.put(Constant.KEY_PAGE_SIZE, String.valueOf(20));
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
    }*/
}
