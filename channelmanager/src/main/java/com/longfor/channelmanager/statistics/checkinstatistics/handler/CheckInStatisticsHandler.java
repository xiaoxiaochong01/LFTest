package com.longfor.channelmanager.statistics.checkinstatistics.handler;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.ec.baseadapter.BaseRefreshHandler;
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
import java.util.Comparator;
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
    public Comparator<MultipleItemEntity> mMonthAvgCheckInComparator = new Comparator<MultipleItemEntity>() {
        @Override
        public int compare(MultipleItemEntity o1, MultipleItemEntity o2) {
            if (mIsAsc) {
                return ((Integer) o1.getField(CheckInStatisticsConstant.MONTH_AVG_CHECK_IN)) -
                        ((Integer) o2.getField(CheckInStatisticsConstant.MONTH_AVG_CHECK_IN));
            } else {
                return ((Integer) o2.getField(CheckInStatisticsConstant.MONTH_AVG_CHECK_IN)) -
                        ((Integer) o1.getField(CheckInStatisticsConstant.MONTH_AVG_CHECK_IN));
            }
        }
    };
    public Comparator<MultipleItemEntity> mTodayCheckInComparator = new Comparator<MultipleItemEntity>() {
        @Override
        public int compare(MultipleItemEntity o1, MultipleItemEntity o2) {
            if (mIsAsc) {
                return ((Integer) o1.getField(CheckInStatisticsConstant.TODAY_CHECK_IN)) -
                        ((Integer) o2.getField(CheckInStatisticsConstant.TODAY_CHECK_IN));
            } else {
                return ((Integer) o2.getField(CheckInStatisticsConstant.TODAY_CHECK_IN)) -
                        ((Integer) o1.getField(CheckInStatisticsConstant.TODAY_CHECK_IN));
            }
        }
    };
    private boolean mIsAsc = false;
    private CheckInStatisticsDelegate mCheckInStatisticsDelegate;

    public CheckInStatisticsHandler(SwipeRefreshLayout REFRESH_LAYOUT, RecyclerView RECYCLERVIEW,
                                    DataConverter CONVERTER, String roleType, int itemType,
                                    String id, CheckInStatisticsRvAdapter.OnItemClickListener onItemClickListener,
                                    CheckInStatisticsDelegate delegate) {
        super(REFRESH_LAYOUT, RECYCLERVIEW, CONVERTER);
        mRoleType = roleType;
        mItemType = itemType;
        mId = id;
        mOnItemClickListener = onItemClickListener;
        mCheckInStatisticsDelegate = delegate;
        delegate.setOnSortButtonClickListener(this);
    }

    public static CheckInStatisticsHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                                  RecyclerView recyclerView, String roleType,
                                                  int itemType, String id,
                                                  CheckInStatisticsRvAdapter.OnItemClickListener onItemClickListener,
                                                  CheckInStatisticsDelegate delegate,
                                                  CheckInStatisticsDataConverter.OnGetNetDataListener onGetNetDataListener) {
        return new CheckInStatisticsHandler(swipeRefreshLayout, recyclerView,
                new CheckInStatisticsDataConverter(itemType, onGetNetDataListener), roleType, itemType, id, onItemClickListener, delegate);
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
        mCheckInStatisticsRvAdapter = CheckInStatisticsRvAdapter.create(converter.convert(), mItemType, mOnItemClickListener);
        return mCheckInStatisticsRvAdapter;
    }

    @Override
    public BaseRefreshHandler updateParams(String... params) {
        if (params != null && params.length == 1) {
            mRoleType = params[0];
        }
        clearSortState();
        firstPage();
        return this;
    }

    @Override
    public void onTodayCheckInSort(int todayCheckInFlag) {
        List<MultipleItemEntity> tempList = new ArrayList<>();
        if (todayCheckInFlag == CheckInStatisticsConstant.SORT_ASC) {
            mIsAsc = true;
            Collections.sort(mSortList, mTodayCheckInComparator);
        } else if (todayCheckInFlag == CheckInStatisticsConstant.SORT_DESC) {
            mIsAsc = false;
            Collections.sort(mSortList, mTodayCheckInComparator);
        } else if (todayCheckInFlag == CheckInStatisticsConstant.SORT_DEF) {
            mSortList.clear();
            mSortList.addAll(mOriginList);
        }
        tempList.addAll(mSortList);
        mCheckInStatisticsRvAdapter.refresh(tempList);
    }

    @Override
    public void onMonthCheckInSort(int monthAvgCheckInFlag) {
        List<MultipleItemEntity> tempList = new ArrayList<>();
        if (monthAvgCheckInFlag == CheckInStatisticsConstant.SORT_ASC) {
            mIsAsc = true;
            Collections.sort(mSortList, mMonthAvgCheckInComparator);
        } else if (monthAvgCheckInFlag == CheckInStatisticsConstant.SORT_DESC) {
            mIsAsc = false;
            Collections.sort(mSortList, mMonthAvgCheckInComparator);
        } else if (monthAvgCheckInFlag == CheckInStatisticsConstant.SORT_DEF) {
            mSortList.clear();
            mSortList.addAll(mOriginList);
        }
        tempList.addAll(mSortList);
        mCheckInStatisticsRvAdapter.refresh(tempList);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        clearSortState();
    }

    private void clearSortState() {
        mIsAsc = false;
        mCheckInStatisticsDelegate.mTodayCheckInFlag = CheckInStatisticsConstant.SORT_DEF;
        mCheckInStatisticsDelegate.mMonthAvgCheckInFlag = CheckInStatisticsConstant.SORT_DEF;
        mCheckInStatisticsDelegate.drawRight(mCheckInStatisticsDelegate.mTvTodayCheckIn, CheckInStatisticsConstant.SORT_DEF);
        mCheckInStatisticsDelegate.drawRight(mCheckInStatisticsDelegate.mTvMonthAvgCheckIn, CheckInStatisticsConstant.SORT_DEF);
    }
}
