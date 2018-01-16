package com.longfor.channelmanager.statistics.checkinstatistics.handler;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.ec.baseadapter.BaseRefreshHandler;
import com.longfor.channelmanager.common.utils.SortList;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.channelmanager.statistics.checkinstatistics.adapter.CheckInStatisticsRvAdapter;
import com.longfor.channelmanager.statistics.checkinstatistics.constant.CheckInStatisticsConstant;
import com.longfor.channelmanager.statistics.checkinstatistics.converter.CheckInStatisticsDataConverter;
import com.longfor.channelmanager.statistics.checkinstatistics.delegate.CheckInStatisticsDelegate;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: gaomei
 * @date: 2018/1/15
 * @function:
 */

public class CheckInStatisticsHandler extends BaseRefreshHandler implements CheckInStatisticsDelegate.OnSortButtonClickListener {
    private String mRoleType;
    private String mEmployeeId;
    private int mItemType;
    private String mId;
    private CheckInStatisticsRvAdapter.OnItemClickListener mOnItemClickListener;
    private List<MultipleItemEntity> mSortList = new ArrayList<MultipleItemEntity>();
    private List<MultipleItemEntity> mOriginList = new ArrayList<MultipleItemEntity>();
    public CheckInStatisticsRvAdapter mCheckInStatisticsRvAdapter;

    public CheckInStatisticsHandler(SwipeRefreshLayout REFRESH_LAYOUT, RecyclerView RECYCLERVIEW,
                                    DataConverter CONVERTER, String roleType, int itemType,
                                    String id, CheckInStatisticsRvAdapter.OnItemClickListener onItemClickListener,
                                    CheckInStatisticsDelegate delegate) {
        super(REFRESH_LAYOUT, RECYCLERVIEW, CONVERTER);
        mRoleType = roleType;
        mItemType = itemType;
        mId = id;
        mOnItemClickListener = onItemClickListener;
        delegate.setOnSortButtonClickListener(this);
    }

    public static CheckInStatisticsHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                                  RecyclerView recyclerView, String roleType,
                                                  int itemType, String id,
                                                  CheckInStatisticsRvAdapter.OnItemClickListener onItemClickListener,
                                                  CheckInStatisticsDelegate delegate) {
        return new CheckInStatisticsHandler(swipeRefreshLayout, recyclerView,
                new CheckInStatisticsDataConverter(itemType), roleType, itemType, id, onItemClickListener, delegate);
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
        mSortList.addAll(converter.convert());
        mOriginList.addAll(converter.convert());
        switch (mItemType) {
            case CheckInStatisticsConstant.ITEM_TYPE_COMPANY:
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_PROJECT:
                mSortList.remove(0);
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_TEAM:
                mSortList.remove(0);
                break;
        }
        mCheckInStatisticsRvAdapter = CheckInStatisticsRvAdapter.create(converter.convert(), mItemType, mOnItemClickListener);
        return mCheckInStatisticsRvAdapter;
    }

    @Override
    public BaseRefreshHandler updateParams(String... params) {
        return null;
    }

    @Override
    public void onTodayCheckInSort(int todayCheckInFlag) {
        mCheckInStatisticsRvAdapter.getData().clear();
        if (todayCheckInFlag == CheckInStatisticsConstant.SORT_ASC) {
            Collections.sort(mSortList, new SortList<MultipleItemEntity>(CheckInStatisticsConstant.TODAY_CHECK_IN, true));
            if (mItemType==CheckInStatisticsConstant.ITEM_TYPE_PROJECT||mItemType==CheckInStatisticsConstant.ITEM_TYPE_TEAM) {
                mSortList.add(0, mOriginList.get(0));
            }
        } else if (todayCheckInFlag == CheckInStatisticsConstant.SORT_DESC) {
            Collections.sort(mSortList, new SortList<MultipleItemEntity>(CheckInStatisticsConstant.TODAY_CHECK_IN, false));
            if (mItemType==CheckInStatisticsConstant.ITEM_TYPE_PROJECT||mItemType==CheckInStatisticsConstant.ITEM_TYPE_TEAM) {
                mSortList.add(0, mOriginList.get(0));
            }
        } else if (todayCheckInFlag == CheckInStatisticsConstant.SORT_DEF) {
            mSortList.clear();
            mSortList.addAll(mOriginList);
        }
        mCheckInStatisticsRvAdapter.refresh(mSortList);
    }

    @Override
    public void onMonthCheckInSort(int monthAvgCheckInFlag) {
        mCheckInStatisticsRvAdapter.getData().clear();
        if (monthAvgCheckInFlag == CheckInStatisticsConstant.SORT_ASC) {
            Collections.sort(mSortList, new SortList<MultipleItemEntity>(CheckInStatisticsConstant.MONTH_AVG_CHECK_IN, true));
            if (mItemType==CheckInStatisticsConstant.ITEM_TYPE_PROJECT||mItemType==CheckInStatisticsConstant.ITEM_TYPE_TEAM) {
                mSortList.add(0, mOriginList.get(0));
            }
        } else if (monthAvgCheckInFlag == CheckInStatisticsConstant.SORT_DESC) {
            Collections.sort(mSortList, new SortList<MultipleItemEntity>(CheckInStatisticsConstant.MONTH_AVG_CHECK_IN, false));
            if (mItemType==CheckInStatisticsConstant.ITEM_TYPE_PROJECT||mItemType==CheckInStatisticsConstant.ITEM_TYPE_TEAM) {
                mSortList.add(0, mOriginList.get(0));
            }
        } else if (monthAvgCheckInFlag == CheckInStatisticsConstant.SORT_DEF) {
            mSortList.clear();
            mSortList.addAll(mOriginList);
        }
        mCheckInStatisticsRvAdapter.refresh(mSortList);
    }
}
