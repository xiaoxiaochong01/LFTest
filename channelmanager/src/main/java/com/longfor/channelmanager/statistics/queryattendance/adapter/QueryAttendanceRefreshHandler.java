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
        firstPage();
        return this;
    }
}
