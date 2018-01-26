package com.longfor.channelmanager.arrange.week;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.arrange.ArrangeConstant;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.view.CommonHeadView;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.ui.recycler.BaseDecoration;

import butterknife.BindView;

/**
 * @author: gaomei
 * @date: 2018/1/25
 * @function:
 */

public class ArrangeTeamWeekWorkDelegate extends LongForDelegate {
    @BindView(R.id.header_arrange_team_week_work)
    CommonHeadView mHeaderArrangeTeamWeekWork;
    @BindView(R.id.tl_arrange_team_week_work)
    TabLayout mTlArrangeTeamWeekWork;
    @BindView(R.id.rv_arrange_team_week_week_content)
    RecyclerView mRvArrangeTeamWeekWeekContent;
    @BindView(R.id.rv_arrange_team_week_week_menu)
    RecyclerView mRvArrangeTeamWeekWeekMenu;
    @BindView(R.id.srl_arrange_team_week_work)
    SwipeRefreshLayout mSrlArrangeTeamWeekWork;
    @BindView(R.id.dl_arrange_team_week_work)
    DrawerLayout mDlArrangeTeamWeekWork;
    private String mTeamId;
    public ArrangeTeamWeekWorkHandler mArrangeTeamWeekWorkHandler;

    public static LongForDelegate getInstance(String leftStr, String title, String teamId) {
        ArrangeTeamWeekWorkDelegate delegate = new ArrangeTeamWeekWorkDelegate();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE_LEFT_TEXT, leftStr);
        bundle.putString(Constant.TITLE_CENTER_TEXT, title);
        bundle.putString(ArrangeConstant.TEAM_ID, teamId);
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_arrange_team_week_work;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        getBasicData();
        initHeader();
        mDlArrangeTeamWeekWork.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        initSwipeRefreshLayout();
        initRecyclerView();
        mArrangeTeamWeekWorkHandler = ArrangeTeamWeekWorkHandler.create(mDlArrangeTeamWeekWork, mTlArrangeTeamWeekWork,
                mSrlArrangeTeamWeekWork, mRvArrangeTeamWeekWeekContent,
                mRvArrangeTeamWeekWeekMenu, mTeamId);
        mArrangeTeamWeekWorkHandler.requestWeekArrangedWorkByTeam();
    }

    private void getBasicData() {
        mTeamId = getArguments().getString(ArrangeConstant.TEAM_ID);
    }

    private void initHeader() {
        mHeaderArrangeTeamWeekWork.setLeftBackImageVisible(true);
        mHeaderArrangeTeamWeekWork.setLeftMsg(getArguments().getString(Constant.TITLE_LEFT_TEXT));
        mHeaderArrangeTeamWeekWork.setTitle(getArguments().getString(Constant.TITLE_CENTER_TEXT));
        mHeaderArrangeTeamWeekWork.setBottomLineVisible(true);
        mHeaderArrangeTeamWeekWork.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().pop();
            }
        });
    }

    private void initSwipeRefreshLayout() {
        mSrlArrangeTeamWeekWork.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSrlArrangeTeamWeekWork.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        GridLayoutManager contentManager = new GridLayoutManager(getContext(), 1);
        GridLayoutManager menuManager = new GridLayoutManager(getContext(), 1);
        mRvArrangeTeamWeekWeekContent.setLayoutManager(contentManager);
        mRvArrangeTeamWeekWeekMenu.setLayoutManager(menuManager);
        if (getContext() != null) {
            mRvArrangeTeamWeekWeekContent.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(getContext(), com.longfor.ec.R.color.app_background), 1));
            mRvArrangeTeamWeekWeekMenu.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(getContext(), com.longfor.ec.R.color.app_background), 1));
        }
    }
}
