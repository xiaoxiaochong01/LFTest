package com.longfor.channelmanager.arrange.week;

import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longfor.channelmanager.R;
import com.longfor.channelmanager.arrange.ArrangeConstant;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.net.BaseSuccessListener;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.IError;
import com.longfor.core.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: gaomei
 * @date: 2018/1/25
 * @function:
 */

public class ArrangeTeamWeekWorkHandler {
    private final DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.SimpleDrawerListener() {
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            requestAlternativeTrainee();
        }
    };

    private DrawerLayout mDlArrangeTeamWeekWork;

    private TabLayout mTlArrangeTeamWeekWork;
    private SwipeRefreshLayout mSrlArrangeTeamWeekWork;
    private RecyclerView mRvArrangeTeamWeekWeekContent;
    private RecyclerView mRvArrangeTeamWeekWeekMenu;
    private String mEmployeeId;
    private String mTeamId;
    private final TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            showContent(mTlArrangeTeamWeekWork.getSelectedTabPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
    private final SwipeRefreshLayout.OnRefreshListener mOnSwipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            requestWeekArrangedWorkByTeam();
        }
    };
    public List<ArrangeTeamWeekWorkBean.DataBean.WeekDatasBean> mWeekDatas = new ArrayList<>();
    public List<List<SectionArrangeWeek>> mSectionWeekDatas = new ArrayList<>();
    public ArrangeTeamWeekWorkContentRvAdapter mContentRvAdapter;
    private BaseQuickAdapter.OnItemClickListener mOnContentItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            if (!mSectionWeekDatas.get(mTlArrangeTeamWeekWork.getSelectedTabPosition()).get(position).isHeader) {
                if (mTlArrangeTeamWeekWork.getSelectedTabPosition()>mCurrentDayOfWeek) {
                    mDlArrangeTeamWeekWork.openDrawer(Gravity.END);
                }else {
                    ToastUtils.showMessage(mDlArrangeTeamWeekWork.getContext().getString(R.string.exceed_arrange_time_limit));
                }
            }
        }
    };
    public int mCurrentDayOfWeek;
    public ArrangeTeamWeekWorkHandler(DrawerLayout dlArrangeTeamWeekWork, TabLayout tlArrangeTeamWeekWork,
                                      SwipeRefreshLayout srlArrangeTeamWeekWork,
                                      RecyclerView rvArrangeTeamWeekWeekContent,
                                      RecyclerView rvArrangeTeamWeekWeekMenu, String teamId) {
        mDlArrangeTeamWeekWork = dlArrangeTeamWeekWork;
        mTlArrangeTeamWeekWork = tlArrangeTeamWeekWork;
        mSrlArrangeTeamWeekWork = srlArrangeTeamWeekWork;
        mRvArrangeTeamWeekWeekContent = rvArrangeTeamWeekWeekContent;
        mRvArrangeTeamWeekWeekMenu = rvArrangeTeamWeekWeekMenu;
        mTeamId = teamId;
        mTlArrangeTeamWeekWork.addOnTabSelectedListener(mOnTabSelectedListener);
        mSrlArrangeTeamWeekWork.setOnRefreshListener(mOnSwipeRefreshListener);
        mDlArrangeTeamWeekWork.addDrawerListener(mDrawerListener);
        Calendar calendar=Calendar.getInstance();
//        int i = calendar.get(Calendar.DAY_OF_WEEK);
//        mCurrentDayOfWeek = (i +6)%7;
    }

    public static ArrangeTeamWeekWorkHandler create(DrawerLayout dlArrangeTeamWeekWork,
                                                    TabLayout tlArrangeTeamWeekWork,
                                                    SwipeRefreshLayout srlArrangeTeamWeekWork,
                                                    RecyclerView rvArrangeTeamWeekWeekContent,
                                                    RecyclerView rvArrangeTeamWeekWeekMenu, String teamId) {
        return new ArrangeTeamWeekWorkHandler(dlArrangeTeamWeekWork, tlArrangeTeamWeekWork,
                srlArrangeTeamWeekWork, rvArrangeTeamWeekWeekContent, rvArrangeTeamWeekWeekMenu, teamId);
    }

    public void requestWeekArrangedWorkByTeam() {
        mWeekDatas.clear();
        mSectionWeekDatas.clear();
        RestClient.builder()
                .raw(getWeekArrangedWorkByTeamParams())
                .url(getWeekArrangedWorkByTeamUrl())
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        ArrangeTeamWeekWorkBean arrangeTeamWeekWorkBean = JSON.parseObject(response, ArrangeTeamWeekWorkBean.class);
                        if (arrangeTeamWeekWorkBean != null) {
                            generateWholeWeekData(arrangeTeamWeekWorkBean);
                            showTab();
                            showContent(mTlArrangeTeamWeekWork.getSelectedTabPosition());
                        }
                        if (mSrlArrangeTeamWeekWork.isRefreshing()) {
                            mSrlArrangeTeamWeekWork.setRefreshing(false);
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.showMessage(msg);
                        if (mSrlArrangeTeamWeekWork.isRefreshing()) {
                            mSrlArrangeTeamWeekWork.setRefreshing(false);
                        }
                    }
                })
                .loader(mDlArrangeTeamWeekWork.getContext())
                .build()
                .post();
    }

    private void generateWholeWeekData(ArrangeTeamWeekWorkBean arrangeTeamWeekWorkBean) {
        if (arrangeTeamWeekWorkBean.getData().getWeekDatas() != null) {
            mWeekDatas.addAll(arrangeTeamWeekWorkBean.getData().getWeekDatas());
            for (int i = 0; i < mWeekDatas.size(); i++) {
                generateWholeDayData(i);
            }
        }
    }

    private void generateWholeDayData(int index) {
        List<ArrangeTeamWeekWorkBean.DataBean.WeekDatasBean.ShiftsBean> shifts = mWeekDatas.get(index).getShifts();
        if (shifts != null) {
            List<SectionArrangeWeek> sectionArrangeWholeDayList = new ArrayList<>(shifts.size());
            for (int i = 0; i < shifts.size(); i++) {
                ArrangeTeamWeekWorkBean.DataBean.WeekDatasBean.ShiftsBean shiftsBean = shifts.get(i);
//                add header
                sectionArrangeWholeDayList.add(new SectionArrangeWeek(true, shiftsBean.getName(),
                        shiftsBean.getPersonAmount(), shiftsBean.getShiftId()));
//                add content
                if (shiftsBean.getArranged() != null && shiftsBean.getArranged().size() <= shiftsBean.getPersonAmount()) {
//                    add arranged data
                    for (int j = 0; j < shiftsBean.getArranged().size(); j++) {
                        sectionArrangeWholeDayList.add(new SectionArrangeWeek(shiftsBean.getArranged().get(j)));
                    }
//                    add empty data
                    int leftSize = shiftsBean.getPersonAmount() - shiftsBean.getArranged().size();
                    for (int j = 0; j < leftSize; j++) {
                        sectionArrangeWholeDayList.add(new SectionArrangeWeek(new ArrangeTeamWeekWorkBean.DataBean.WeekDatasBean.ShiftsBean.ArrangedBean(true)));
                    }
                }
            }
            mSectionWeekDatas.add(sectionArrangeWholeDayList);
        }
    }

    private void showContent(int selectedTabPosition) {
        if (mContentRvAdapter == null) {
            mContentRvAdapter = new ArrangeTeamWeekWorkContentRvAdapter(
                    R.layout.item_sticky_child, R.layout.item_sticky_header, mSectionWeekDatas.get(selectedTabPosition));
            mRvArrangeTeamWeekWeekContent.setAdapter(mContentRvAdapter);
            mContentRvAdapter.setOnItemClickListener(mOnContentItemClickListener);
        } else {
            mContentRvAdapter.setNewData(mSectionWeekDatas.get(selectedTabPosition));
        }
    }

    private void showTab() {
        if (mWeekDatas != null && mWeekDatas.size() > 0) {
            for (int i = 0; i < mWeekDatas.size(); i++) {
                mTlArrangeTeamWeekWork.addTab(mTlArrangeTeamWeekWork.newTab().setText(mWeekDatas.get(i).getDayName()));
            }
            mOnTabSelectedListener.onTabSelected(mTlArrangeTeamWeekWork.getTabAt(0));
        }
    }

    private Map<String, String> getWeekArrangedWorkByTeamParams() {
        mEmployeeId = DatabaseManager.getEmployeeId();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMPLOYEE_ID, mEmployeeId);
        params.put(ArrangeConstant.TEAM_ID, mTeamId);
        return params;
    }

    private String getWeekArrangedWorkByTeamUrl() {
        return ArrangeConstant.URL_GET_WEEK_ARRANGE_WORK_BY_TEAM;
    }

    private void requestAlternativeTrainee() {

    }
}
