package com.longfor.channelmanager.statistics.checkinstatistics.handler;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.ec.baseadapter.BaseRefreshHandler;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.channelmanager.statistics.checkinstatistics.constant.CheckInStatisticsConstant;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: gaomei
 * @date: 2018/1/15
 * @function:
 */

public class CheckInStatisticsHandler extends BaseRefreshHandler {
    private String mRoleType;
    private String mEmployeeId;
    private String mItemType;
    private String mId;

    public CheckInStatisticsHandler(SwipeRefreshLayout REFRESH_LAYOUT, RecyclerView RECYCLERVIEW,
                                    DataConverter CONVERTER, String roleType, String itemType,
                                    String id) {
        super(REFRESH_LAYOUT, RECYCLERVIEW, CONVERTER);
        mRoleType = roleType;
        mItemType = itemType;
        mId = id;
    }

    public static CheckInStatisticsHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                                  RecyclerView recyclerView, String roleType,
                                                  String itemType, String id) {
        return new CheckInStatisticsHandler(swipeRefreshLayout, recyclerView, new DataConverter() {
            @Override
            public ArrayList<MultipleItemEntity> convert() {
                return null;
            }
        }, roleType, itemType, id);
    }

    @Override
    public String getUrl() {
        String url = null;
        switch (mItemType) {
            case CheckInStatisticsConstant.ITEM_TYPE_COMPANY:
                url = CheckInStatisticsConstant.URL_GET_COMPANY_CHECK_IN;
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_PROJECT:
                url = CheckInStatisticsConstant.URL_GET_PROJECT_CHECK_IN;
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_TEAM:
                url = CheckInStatisticsConstant.URL_GET_TEAM_CHECK_IN;
                break;
        }
        return url;
    }

    @Override
    public Map<String, String> getParams() {
        mEmployeeId = DatabaseManager.getEmployeeId();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMPLOYEE_ID, mEmployeeId);
        params.put(Constant.ROLE_TYPE, mRoleType);
        switch (mItemType) {
            case CheckInStatisticsConstant.ITEM_TYPE_PROJECT:
                params.put(CheckInStatisticsConstant.AREA_ID, mId);
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_TEAM:
                params.put(CheckInStatisticsConstant.PROJECT_ID, mId);
                break;
        }
        return params;
    }

    @Override
    public int getPageSize() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getTotals(String response) {
        return 0;
    }

    @Override
    public BaseRecyclerAdapter getAdapter(DataConverter converter) {
        return null;
    }

    @Override
    public BaseRefreshHandler updateParams(String... params) {
        return null;
    }
}
