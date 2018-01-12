package com.longfor.channelmanager.statistics.checkinstatistics.adapter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.longfor.channelmanager.common.ec.baseadapter.BaseRefreshHandler;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.DataConverter;

import java.util.Map;

/**
 * @author: gaomei
 * @date: 2018/1/12
 * @function:上岗统计
 */

public class CheckInStatisticsRefreshHandler extends BaseRefreshHandler {
    private String mItemType;
    private String mRoleType;
    private String mId;

    public CheckInStatisticsRefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT, RecyclerView RECYCLERVIEW, DataConverter CONVERTER, String itemType, String roleType, String id) {
        super(REFRESH_LAYOUT, RECYCLERVIEW, CONVERTER);
        mItemType = itemType;
        mRoleType = roleType;
        mId = id;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Map<String, String> getParams() {
        return null;
    }

    @Override
    public int getPageSize() {
        return 0;
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
