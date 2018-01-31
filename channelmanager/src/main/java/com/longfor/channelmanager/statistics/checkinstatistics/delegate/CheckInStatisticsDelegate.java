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
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.client.search.ChooseRolePopwindow;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.view.CommonHeadView;
import com.longfor.channelmanager.main.ChannelMainDelegate;
import com.longfor.channelmanager.statistics.checkinstatistics.adapter.CheckInStatisticsRvAdapter;
import com.longfor.channelmanager.statistics.checkinstatistics.bean.ProjectCheckInBean;
import com.longfor.channelmanager.statistics.checkinstatistics.bean.TeamCheckInBean;
import com.longfor.channelmanager.statistics.checkinstatistics.constant.CheckInStatisticsConstant;
import com.longfor.channelmanager.statistics.checkinstatistics.converter.CheckInStatisticsDataConverter;
import com.longfor.channelmanager.statistics.checkinstatistics.handler.CheckInStatisticsHandler;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.utils.UI.ScreenUtil;
import com.longfor.ui.recycler.BaseDecoration;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: gaomei
 * @date: 2018/1/11
 * @function:地区级别上岗统计
 */

public class CheckInStatisticsDelegate extends LongForDelegate implements
        CheckInStatisticsRvAdapter.OnItemClickListener, CheckInStatisticsDataConverter.OnGetNetDataListener {
    @BindView(R2.id.header_check_in)
    CommonHeadView mHeaderCheckIn;
    @BindView(R2.id.tv_check_in_level)
    AppCompatTextView mTvCheckInLevel;
    @BindView(R2.id.tv_today_check_in)
    public AppCompatTextView mTvTodayCheckIn;
    @BindView(R2.id.tv_month_avg_check_in)
    public AppCompatTextView mTvMonthAvgCheckIn;
    @BindView(R2.id.rv_check_in)
    RecyclerView mRvCheckIn;
    @BindView(R2.id.srl_check_in)
    SwipeRefreshLayout mSrlCheckIn;
    @BindView(R.id.tv_total_check_in_name)
    AppCompatTextView mTvTotalCheckInName;
    @BindView(R.id.tv_total_today_check_in)
    AppCompatTextView mTvTotalTodayCheckIn;
    @BindView(R.id.tv_total_month_avg_check_in)
    AppCompatTextView mTvTotalMonthAvgCheckIn;
    @BindView(R.id.ll_total_check_in_statistics)
    LinearLayoutCompat mLlTotalCheckInStatistics;
    private TextView mTvTitleRight;

    private int mCheckInType;//上岗统计类型
    public String mRoleType;//用户角色
    private String mId;//mId,可能是areaId或projectId


    public int mTodayCheckInFlag = CheckInStatisticsConstant.SORT_DEF;//按当天签到排序
    public int mMonthAvgCheckInFlag = CheckInStatisticsConstant.SORT_DEF;//按滚动30天日均排序
    private OnSortButtonClickListener mOnSortButtonClickListener;
    private ChooseRolePopwindow mPopwindow;
    private List<String> mPopupWindowList ;
    public CheckInStatisticsHandler mHandler;

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
        initPopupWindow();
        mHandler = CheckInStatisticsHandler.create(mSrlCheckIn, mRvCheckIn, mRoleType, mCheckInType,
                mId, this, this, this);
        mHandler.firstPage();
    }

    private void getBasicData() {
        mCheckInType = getArguments().getInt(CheckInStatisticsConstant.CHECK_IN_TYPE);
        mRoleType = getArguments().getString(Constant.ROLE_TYPE);
        mId = getArguments().getString(Constant.ID);
        mPopupWindowList = Arrays.asList(new String[]{getString(R.string.trainee_role_show_get_num),
                getString(R.string.trainee_role_expand_get_num), getString(R.string.trainee_role_show_and_expand_call_num),
                getString(R.string.trainee_role_call_get_num), getString(R.string.trainee_role_call_call_num),
                getString(R.string.total)});
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
                mPopwindow.showAsDropDown(mTvTitleRight);
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
            mRvCheckIn.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(context, R.color.layout_bg_gray_f4), ScreenUtil.dip2px(getContext(), (float) 0.5)));
        }
        mRvCheckIn.setNestedScrollingEnabled(false);
    }

    private void initPopupWindow() {
        mPopwindow = new ChooseRolePopwindow(getContext(), mPopupWindowList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTvTitleRight.setText(mPopupWindowList.get(position));
                mRoleType = String.valueOf((position + 1) % mPopupWindowList.size());
                mHandler.updateParams(mRoleType);
                mPopwindow.dismiss();
            }
        });
        mPopwindow.setWidth(ScreenUtil.dip2px(getContext(), 110));
        mPopwindow.setHeight(ScreenUtil.dip2px(getContext(), 236));
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

    public void setOnSortButtonClickListener(OnSortButtonClickListener listener) {
        mOnSortButtonClickListener = listener;
    }

    @OnClick({R.id.tv_today_check_in, R.id.tv_month_avg_check_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_today_check_in:
                mTodayCheckInFlag = ++mTodayCheckInFlag % CheckInStatisticsConstant.SORT_OVER;
                if (mOnSortButtonClickListener != null) {
                    mOnSortButtonClickListener.onTodayCheckInSort(mTodayCheckInFlag);
                }
                if (mMonthAvgCheckInFlag != CheckInStatisticsConstant.SORT_DEF) {
                    mMonthAvgCheckInFlag = CheckInStatisticsConstant.SORT_DEF;
                    drawRight(mTvMonthAvgCheckIn, mMonthAvgCheckInFlag);
                }
                drawRight(mTvTodayCheckIn, mTodayCheckInFlag);
                break;
            case R.id.tv_month_avg_check_in:
                mMonthAvgCheckInFlag = ++mMonthAvgCheckInFlag % CheckInStatisticsConstant.SORT_OVER;
                if (mOnSortButtonClickListener != null) {
                    mOnSortButtonClickListener.onMonthCheckInSort(mMonthAvgCheckInFlag);
                }
                if (mTodayCheckInFlag != CheckInStatisticsConstant.SORT_DEF) {
                    mTodayCheckInFlag = CheckInStatisticsConstant.SORT_DEF;
                    drawRight(mTvTodayCheckIn, mTodayCheckInFlag);
                }
                drawRight(mTvMonthAvgCheckIn, mMonthAvgCheckInFlag);
                break;
        }
    }

    public void drawRight(TextView textView, int type) {
        Drawable drawable = null;
        if (type == CheckInStatisticsConstant.SORT_DEF) {
            drawable = getResources().getDrawable(R.mipmap.ic_check_in_sort_def);
        } else if (type == CheckInStatisticsConstant.SORT_ASC) {
            drawable = getResources().getDrawable(R.mipmap.ic_check_in_sort_asc);
        } else if (type == CheckInStatisticsConstant.SORT_DESC) {
            drawable = getResources().getDrawable(R.mipmap.ic_check_in_sort_desc);
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public void onGetNetData(String jsonData) {
        switch (mCheckInType) {
            case CheckInStatisticsConstant.ITEM_TYPE_COMPANY:
                mLlTotalCheckInStatistics.setVisibility(View.GONE);
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_PROJECT:
                mLlTotalCheckInStatistics.setVisibility(View.VISIBLE);
                ProjectCheckInBean projectCheckInBean = JSON.parseObject(jsonData, ProjectCheckInBean.class);
                if (projectCheckInBean != null && projectCheckInBean.getData() != null && projectCheckInBean.getData().getArea() != null) {
                    mTvTotalCheckInName.setText(projectCheckInBean.getData().getArea().getAreaName());
                    mTvTotalTodayCheckIn.setText(String.valueOf(projectCheckInBean.getData().getArea().getCheckins()));
                    mTvTotalMonthAvgCheckIn.setText(String.valueOf(projectCheckInBean.getData().getArea().getAvg()));
                }
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_TEAM:
                mLlTotalCheckInStatistics.setVisibility(View.VISIBLE);
                TeamCheckInBean teamCheckInBean = JSON.parseObject(jsonData, TeamCheckInBean.class);
                if (teamCheckInBean != null && teamCheckInBean.getData() != null && teamCheckInBean.getData().getProject() != null) {
                    mTvTotalCheckInName.setText(teamCheckInBean.getData().getProject().getProjectName());
                    mTvTotalTodayCheckIn.setText(String.valueOf(teamCheckInBean.getData().getProject().getCheckins()));
                    mTvTotalMonthAvgCheckIn.setText(String.valueOf(teamCheckInBean.getData().getProject().getAvg()));
                }
                break;
        }
    }

    public interface OnSortButtonClickListener {
        void onTodayCheckInSort(int todayCheckInFlag);

        void onMonthCheckInSort(int monthAvgCheckInFlag);
    }
}
