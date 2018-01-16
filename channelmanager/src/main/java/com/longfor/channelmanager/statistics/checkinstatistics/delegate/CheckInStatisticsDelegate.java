package com.longfor.channelmanager.statistics.checkinstatistics.delegate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.view.CommonHeadView;
import com.longfor.channelmanager.main.ChannelMainDelegate;
import com.longfor.channelmanager.statistics.checkinstatistics.adapter.CheckInStatisticsRvAdapter;
import com.longfor.channelmanager.statistics.checkinstatistics.constant.CheckInStatisticsConstant;
import com.longfor.channelmanager.statistics.checkinstatistics.handler.CheckInStatisticsHandler;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.utils.log.LogUtils;
import com.longfor.ui.recycler.BaseDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: gaomei
 * @date: 2018/1/11
 * @function:地区级别上岗统计
 */

public class CheckInStatisticsDelegate extends LongForDelegate implements CheckInStatisticsRvAdapter.OnItemClickListener {
    private static final String TAG = "CheckInStatisticsDelegate";
    @BindView(R2.id.header_check_in)
    CommonHeadView mHeaderCheckIn;
    @BindView(R2.id.tv_check_in_level)
    AppCompatTextView mTvCheckInLevel;
    @BindView(R2.id.tv_today_check_in)
    AppCompatTextView mTvTodayCheckIn;
    @BindView(R2.id.tv_month_avg_check_in)
    AppCompatTextView mTvMonthAvgCheckIn;
    @BindView(R2.id.rv_check_in)
    RecyclerView mRvCheckIn;
    @BindView(R2.id.srl_check_in)
    SwipeRefreshLayout mSrlCheckIn;
    private TextView mTvTitleRight;

    private int mCheckInType;//上岗统计类型
    public String mRoleType;//用户角色
    private String mId;//mId,可能是areaId或projectId


    private int mTodayCheckInFlag = CheckInStatisticsConstant.SORT_DEF;//按当天签到排序
    private int mMonthAvgCheckInFlag = CheckInStatisticsConstant.SORT_DEF;//按滚动30天日均排序
    private OnSortButtonClickListener mOnSortButtonClickListener;

    public static CheckInStatisticsDelegate getInstance(int checkInType, String leftMsg, String roleType, String id) {
        Bundle bundle = new Bundle();
        bundle.putInt(CheckInStatisticsConstant.CHECK_IN_TYPE, checkInType);
        bundle.putString(Constant.TITLE_LEFT_TEXT, leftMsg);
        bundle.putString(Constant.ROLE_TYPE, roleType);
        bundle.putString(Constant.ID, id);
        CheckInStatisticsDelegate delegate = new CheckInStatisticsDelegate();
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_statistics_check_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        getBasicData();
        initHeader();
        initRefreshLayout();
        initRecyclerView();
        CheckInStatisticsHandler handler = CheckInStatisticsHandler.create(mSrlCheckIn,
                mRvCheckIn, mRoleType, mCheckInType, mId, this,this);

        handler.firstPage();
    }

    private void getBasicData() {
        mCheckInType = getArguments().getInt(CheckInStatisticsConstant.CHECK_IN_TYPE);
        mRoleType = getArguments().getString(Constant.ROLE_TYPE);
        mId = getArguments().getString(Constant.ID);
    }

    private void initHeader() {
        mHeaderCheckIn.setLeftMsg(getArguments().getString(Constant.TITLE_LEFT_TEXT));
        mHeaderCheckIn.setLeftBackImageVisible(true);
        mHeaderCheckIn.setRightTextViewVisible(true);
        switch (mCheckInType) {
            case CheckInStatisticsConstant.ITEM_TYPE_COMPANY:
                mHeaderCheckIn.setTitle(getString(R.string.company_check_in_statistics));
                mHeaderCheckIn.setRightTextViewText(getString(R.string.total));
                mTvTitleRight = (TextView) mHeaderCheckIn.findViewById(R.id.tv_head_common_right_text);
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_arrow_down_s);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTvTitleRight.setCompoundDrawablePadding(5);
                mTvTitleRight.setCompoundDrawables(null, null, drawable, null);
                mTvCheckInLevel.setText(getString(R.string.company));
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_PROJECT:
                mHeaderCheckIn.setTitle(getString(R.string.project_check_in_statistics));
                mHeaderCheckIn.setRightTextViewText(getString(R.string.close));
                mTvCheckInLevel.setText(getString(R.string.project));
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_TEAM:
                mHeaderCheckIn.setTitle(getString(R.string.team_check_in_statistics));
                mHeaderCheckIn.setRightTextViewText(getString(R.string.close));
                mTvCheckInLevel.setText(getString(R.string.checkInPosition));
                break;
        }
        mHeaderCheckIn.setBottomLineVisible(true);
        mHeaderCheckIn.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().pop();
            }
        });
        mHeaderCheckIn.setRightLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightClickHeader();
            }
        });
    }

    private void rightClickHeader() {
        switch (mCheckInType) {
            case CheckInStatisticsConstant.ITEM_TYPE_COMPANY:
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_PROJECT:
                getSupportDelegate().start(new ChannelMainDelegate(), SINGLETASK);
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_TEAM:
                getSupportDelegate().start(new ChannelMainDelegate(), SINGLETASK);
                break;
        }
    }

    private void initRefreshLayout() {
        mSrlCheckIn.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSrlCheckIn.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        final Context context = getContext();
        mRvCheckIn.setLayoutManager(manager);
        if (context != null) {
            mRvCheckIn.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(context, com.longfor.ec.R.color.app_background), 1));
        }
        mRvCheckIn.setNestedScrollingEnabled(false);
    }

    @Override
    public void onItemClick(String id) {
        switch (mCheckInType) {
            case CheckInStatisticsConstant.ITEM_TYPE_COMPANY:
                CheckInStatisticsDelegate projectCheckInStatisticsDelegate = CheckInStatisticsDelegate
                        .getInstance(CheckInStatisticsConstant.ITEM_TYPE_PROJECT,
                                getString(R.string.company_check_in_statistics), mRoleType, id);
                getSupportDelegate().start(projectCheckInStatisticsDelegate);
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_PROJECT:
                CheckInStatisticsDelegate teamCheckInStatisticsDelegate = CheckInStatisticsDelegate
                        .getInstance(CheckInStatisticsConstant.ITEM_TYPE_TEAM,
                                getString(R.string.project_check_in_statistics), mRoleType, id);
                getSupportDelegate().start(teamCheckInStatisticsDelegate);
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_TEAM:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG, "onDestroy" + mCheckInType);
    }

    public void setOnSortButtonClickListener(OnSortButtonClickListener listener) {
        mOnSortButtonClickListener = listener;
    }

    public interface OnSortButtonClickListener {
        void onTodayCheckInSort(int todayCheckInFlag);

        void onMonthCheckInSort(int monthAvgCheckInFlag);
    }

    @OnClick({R.id.tv_today_check_in, R.id.tv_month_avg_check_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_today_check_in:
                mTodayCheckInFlag = ++mTodayCheckInFlag % CheckInStatisticsConstant.SORT_OVER;
                if (mOnSortButtonClickListener!=null){
                    mOnSortButtonClickListener.onTodayCheckInSort(mTodayCheckInFlag);
                }
                if (mMonthAvgCheckInFlag != CheckInStatisticsConstant.SORT_DEF) {
                    mMonthAvgCheckInFlag = CheckInStatisticsConstant.SORT_DEF;
                    drawLeft(mTvMonthAvgCheckIn, mMonthAvgCheckInFlag);
                }
                drawLeft(mTvTodayCheckIn, mTodayCheckInFlag);
                break;
            case R.id.tv_month_avg_check_in:
                mMonthAvgCheckInFlag = ++mMonthAvgCheckInFlag % CheckInStatisticsConstant.SORT_OVER;
                if (mOnSortButtonClickListener!=null){
                    mOnSortButtonClickListener.onMonthCheckInSort(mMonthAvgCheckInFlag);
                }
                if (mTodayCheckInFlag != CheckInStatisticsConstant.SORT_DEF) {
                    mTodayCheckInFlag = CheckInStatisticsConstant.SORT_DEF;
                    drawLeft(mTvTodayCheckIn, mTodayCheckInFlag);
                }
                drawLeft(mTvMonthAvgCheckIn, mMonthAvgCheckInFlag);
                break;
        }
    }

    private void drawLeft(TextView textView, int type) {
        Drawable drawable = null;
        if (type == CheckInStatisticsConstant.SORT_DEF) {
            drawable = getResources().getDrawable(R.mipmap.icon_check_in_sort_def);
        } else if (type == CheckInStatisticsConstant.SORT_ASC) {
            drawable = getResources().getDrawable(R.mipmap.icon_check_in_sort_asc);
        } else if (type == CheckInStatisticsConstant.SORT_DESC) {
            drawable = getResources().getDrawable(R.mipmap.icon_check_in_sort_desc);
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }
}
