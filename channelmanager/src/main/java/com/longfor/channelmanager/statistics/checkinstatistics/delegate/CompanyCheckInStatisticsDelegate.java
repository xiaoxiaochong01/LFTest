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
import com.longfor.channelmanager.statistics.checkinstatistics.adapter.CheckInStatisticsRvAdapter;
import com.longfor.channelmanager.statistics.checkinstatistics.constant.CheckInStatisticsConstant;
import com.longfor.channelmanager.statistics.checkinstatistics.handler.CheckInStatisticsHandler;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.ui.recycler.BaseDecoration;

import butterknife.BindView;

/**
 * @author: gaomei
 * @date: 2018/1/11
 * @function:地区级别上岗统计
 */

public class CompanyCheckInStatisticsDelegate extends LongForDelegate implements CheckInStatisticsRvAdapter.OnItemClickListener {
    @BindView(R2.id.header_company_check_in)
    CommonHeadView mHeaderCompanyCheckIn;
    @BindView(R2.id.tv_today_check_in)
    AppCompatTextView mTvTodayCheckIn;
    @BindView(R2.id.tv_month_avg_check_in)
    AppCompatTextView mTvMonthAvgCheckIn;
    @BindView(R2.id.rv_company_check_in)
    RecyclerView mRvCompanyCheckIn;
    @BindView(R2.id.srl_company_check_in)
    SwipeRefreshLayout mSrlCompanyCheckIn;
    private TextView mTvTitleRight;
    public String mRoleType=String.valueOf(0);

    public static CompanyCheckInStatisticsDelegate getInstance(String leftMsg) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE_LEFT_TEXT, leftMsg);
        CompanyCheckInStatisticsDelegate delegate = new CompanyCheckInStatisticsDelegate();
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_statistics_company_check_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initHeader();
        initRefreshLayout();
        initRecyclerView();
        CheckInStatisticsHandler handler=CheckInStatisticsHandler.create(mSrlCompanyCheckIn,
                mRvCompanyCheckIn, mRoleType, CheckInStatisticsConstant.ITEM_TYPE_COMPANY,
                null,this);
        handler.firstPage();
    }

    private void initHeader() {
        mHeaderCompanyCheckIn.setLeftMsg(getArguments().getString(Constant.TITLE_LEFT_TEXT));
        mHeaderCompanyCheckIn.setLeftBackImageVisible(true);
        mHeaderCompanyCheckIn.setTitle(getString(R.string.company_check_in_statistics));
        mHeaderCompanyCheckIn.setRightTextViewVisible(true);
        mHeaderCompanyCheckIn.setRightTextViewText(getString(R.string.total));
        mTvTitleRight = (TextView) mHeaderCompanyCheckIn.findViewById(R.id.tv_head_common_right_text);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_arrow_down_s);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mTvTitleRight.setCompoundDrawablePadding(5);
        mTvTitleRight.setCompoundDrawables(null, null, drawable, null);
        mHeaderCompanyCheckIn.setBottomLineVisible(true);
        mHeaderCompanyCheckIn.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().pop();
            }
        });
        mHeaderCompanyCheckIn.setRightLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initRefreshLayout() {
        mSrlCompanyCheckIn.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSrlCompanyCheckIn.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        final Context context = getContext();
        mRvCompanyCheckIn.setLayoutManager(manager);
        if (context != null) {
            mRvCompanyCheckIn.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(context, com.longfor.ec.R.color.app_background), 5));
        }
    }

    @Override
    public void onItemClick(String id) {
        ProjectCheckInStatisticsDelegate delegate = ProjectCheckInStatisticsDelegate.getInstance(mRoleType,id);
        getSupportDelegate().start(delegate);
    }
}
