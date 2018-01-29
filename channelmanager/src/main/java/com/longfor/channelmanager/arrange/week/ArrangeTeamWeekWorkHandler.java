package com.longfor.channelmanager.arrange.week;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            showWeekArrangeWorkContent(mTlArrangeTeamWeekWork.getSelectedTabPosition());
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
    public ArrangeTeamWeekWorkContentRvAdapter mArrangeWorkWeekContentRvAdapter;
    private BaseQuickAdapter.OnItemClickListener mOnArrangeWorkWeekContentItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            if (!mSectionWeekDatas.get(mTlArrangeTeamWeekWork.getSelectedTabPosition()).get(position).isHeader) {
                if (ifAfterToday(mWeekDatas.get(mTlArrangeTeamWeekWork.getSelectedTabPosition()).getDate())) {
                    mCurrentDaySelectedItem = position;
                    requestAlternativeTrainee();
                } else {
                    ToastUtils.showMessage(mDlArrangeTeamWeekWork.getContext().getString(R.string.exceed_arrange_time_limit));
                }
            }
        }
    };
    public Calendar mTodayLastMinuteCalendar;
    public SimpleDateFormat mYearMonthDaySDF;
    private int mCurrentDaySelectedItem;
    private List<SectionArrangeWorkBackUp> mSectionArrangeWorkBackUpList = new ArrayList<>();
    public ArrangeWorkBackUpRvAdapter mArrangeWorkBackUpRvAdapter;
    private BaseQuickAdapter.OnItemClickListener mOnArrangeWorkBackUpItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            if (!mSectionArrangeWorkBackUpList.get(position).isHeader) {
                requestArrangeWork(mSectionArrangeWorkBackUpList.get(position).t);
            }
        }
    };

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
        getTodayCalendar();
    }

    @NonNull
    private Calendar getTodayCalendar() {
        mYearMonthDaySDF = new SimpleDateFormat("yyyy-MM-dd");
        mTodayLastMinuteCalendar = Calendar.getInstance();
        mTodayLastMinuteCalendar.set(Calendar.HOUR_OF_DAY, 23);
        mTodayLastMinuteCalendar.set(Calendar.MINUTE, 59);
        mTodayLastMinuteCalendar.set(Calendar.SECOND, 59);
        return mTodayLastMinuteCalendar;
    }

    private boolean ifAfterToday(String str) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(mYearMonthDaySDF.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.after(mTodayLastMinuteCalendar);
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
                            showWeekArrangeWorkContent(mTlArrangeTeamWeekWork.getSelectedTabPosition());
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
                        ArrangeTeamWeekWorkBean.DataBean.WeekDatasBean.ShiftsBean.ArrangedBean arrangedBean = shiftsBean.getArranged().get(j);
                        arrangedBean.setShiftId(shifts.get(i).getShiftId());
                        sectionArrangeWholeDayList.add(new SectionArrangeWeek(arrangedBean));
                    }
//                    add empty data
                    int leftSize = shiftsBean.getPersonAmount() - shiftsBean.getArranged().size();
                    for (int j = 0; j < leftSize; j++) {
                        ArrangeTeamWeekWorkBean.DataBean.WeekDatasBean.ShiftsBean.ArrangedBean arrangedBean = new ArrangeTeamWeekWorkBean.DataBean.WeekDatasBean.ShiftsBean.ArrangedBean();
                        arrangedBean.setShiftId(shifts.get(i).getShiftId());
                        sectionArrangeWholeDayList.add(new SectionArrangeWeek(arrangedBean));
                    }
                }
            }
            mSectionWeekDatas.add(sectionArrangeWholeDayList);
        }
    }

    private void showWeekArrangeWorkContent(int selectedTabPosition) {
        mRvArrangeTeamWeekWeekContent.smoothScrollToPosition(0);
        if (mArrangeWorkWeekContentRvAdapter == null) {
            mArrangeWorkWeekContentRvAdapter = new ArrangeTeamWeekWorkContentRvAdapter(
                    R.layout.item_sticky_child, R.layout.item_sticky_header, mSectionWeekDatas.get(selectedTabPosition));
            mRvArrangeTeamWeekWeekContent.setAdapter(mArrangeWorkWeekContentRvAdapter);
            mArrangeWorkWeekContentRvAdapter.setOnItemClickListener(mOnArrangeWorkWeekContentItemClickListener);
        } else {
            mArrangeWorkWeekContentRvAdapter.setNewData(mSectionWeekDatas.get(selectedTabPosition));
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
        RestClient.builder()
                .raw(getArrangeWorkBackUpsParams())
                .url(getArrangeWorkBackUpsUrl())
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        ArrangeWorkBackUpBean arrangeWorkBackUpBean = JSON.parseObject(response, ArrangeWorkBackUpBean.class);
                        if (arrangeWorkBackUpBean != null) {
                            generateArrangeWorkBackupData(arrangeWorkBackUpBean);
                            showBackUpContent();
                            mDlArrangeTeamWeekWork.openDrawer(Gravity.END);
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.showMessage(msg);
                    }
                })
                .loader(mDlArrangeTeamWeekWork.getContext())
                .build()
                .post();
    }

    private Map<String, String> getArrangeWorkBackUpsParams() {
        mEmployeeId = DatabaseManager.getEmployeeId();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMPLOYEE_ID, mEmployeeId);
        params.put(ArrangeConstant.TEAM_ID, mTeamId);
        params.put(ArrangeConstant.SHIFT_ID,
                mSectionWeekDatas.get(mTlArrangeTeamWeekWork.getSelectedTabPosition()).get(mCurrentDaySelectedItem).t.getShiftId());
        params.put(ArrangeConstant.WORK_DATE, mWeekDatas.get(mTlArrangeTeamWeekWork.getSelectedTabPosition()).getDate());
        return params;
    }

    private String getArrangeWorkBackUpsUrl() {
        return ArrangeConstant.URL_GET_ARRANGE_WORK_BACK_UPS;
    }

    private void generateArrangeWorkBackupData(ArrangeWorkBackUpBean arrangeWorkBackUpBean) {
        mSectionArrangeWorkBackUpList.clear();
        List<ArrangeWorkBackUpBean.DataBean> dataBeanList = arrangeWorkBackUpBean.getData();
        for (int i = 0; i < dataBeanList.size(); i++) {
//            add header
            mSectionArrangeWorkBackUpList.add(new SectionArrangeWorkBackUp(true, dataBeanList.get(i).getTitle()));
//            add content
            for (int j = 0; j < dataBeanList.get(i).getMembers().size(); j++) {
                mSectionArrangeWorkBackUpList.add(new SectionArrangeWorkBackUp(dataBeanList.get(i).getMembers().get(j)));
            }
        }
    }

    private void showBackUpContent() {
        if (mArrangeWorkBackUpRvAdapter == null) {
            mArrangeWorkBackUpRvAdapter = new ArrangeWorkBackUpRvAdapter(
                    R.layout.item_sticky_child, R.layout.item_sticky_header, mSectionArrangeWorkBackUpList);
            mRvArrangeTeamWeekWeekMenu.setAdapter(mArrangeWorkBackUpRvAdapter);
            mArrangeWorkBackUpRvAdapter.setOnItemClickListener(mOnArrangeWorkBackUpItemClickListener);
        } else {
            mArrangeWorkBackUpRvAdapter.setNewData(mSectionArrangeWorkBackUpList);
        }
    }

    private void requestArrangeWork(final ArrangeWorkBackUpBean.DataBean.MembersBean t) {
        RestClient.builder()
                .raw(getArrangeWorkParams(t))
                .url(getArrangeWorkUrl())
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        ArrangeWorkBean arrangeWorkBean = JSON.parseObject(response, ArrangeWorkBean.class);
                        if (arrangeWorkBean!=null){
                            ArrangeTeamWeekWorkBean.DataBean.WeekDatasBean.ShiftsBean.ArrangedBean arrangedBean
                                    =new ArrangeTeamWeekWorkBean.DataBean.WeekDatasBean.ShiftsBean.ArrangedBean();
                            if (!TextUtils.equals(arrangeWorkBean.getData().getEmployeeId(),"-1")){
                                arrangedBean.setEmployeeId(arrangeWorkBean.getData().getEmployeeId());
                                arrangedBean.setEmployeeName(arrangeWorkBean.getData().getEmployeeName());
                                arrangedBean.setAvgWorks(arrangeWorkBean.getData().getAvgWorks());
                                arrangedBean.setFreetimes(arrangeWorkBean.getData().getFreetimes());
                                arrangedBean.setScheduleId(arrangeWorkBean.getData().getScheduleId());
                                arrangedBean.setShiftId(mSectionWeekDatas.get(mTlArrangeTeamWeekWork.getSelectedTabPosition()
                                ).get(mCurrentDaySelectedItem).t.getShiftId());
                            }
                            mSectionWeekDatas.get(mTlArrangeTeamWeekWork.getSelectedTabPosition()).get(mCurrentDaySelectedItem).t=arrangedBean;
                            mArrangeWorkWeekContentRvAdapter.notifyDataSetChanged();
                        }
                        mDlArrangeTeamWeekWork.closeDrawer(Gravity.END);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.showMessage(msg);
                        mDlArrangeTeamWeekWork.closeDrawer(Gravity.END);
                    }
                })
                .loader(mDlArrangeTeamWeekWork.getContext())
                .build()
                .post();
    }

    private Map<String, String> getArrangeWorkParams(ArrangeWorkBackUpBean.DataBean.MembersBean t) {
        mEmployeeId = DatabaseManager.getEmployeeId();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMPLOYEE_ID, mEmployeeId);
        params.put(ArrangeConstant.TEAM_ID, mTeamId);
        params.put(ArrangeConstant.SHIFT_ID,
                mSectionWeekDatas.get(mTlArrangeTeamWeekWork.getSelectedTabPosition()).get(mCurrentDaySelectedItem).t.getShiftId());
        params.put(ArrangeConstant.WORK_DATE, mWeekDatas.get(mTlArrangeTeamWeekWork.getSelectedTabPosition()).getDate());
        params.put(ArrangeConstant.ORIGIN_USER_ID, mSectionWeekDatas.get(mTlArrangeTeamWeekWork.getSelectedTabPosition()).get(mCurrentDaySelectedItem).t.getEmployeeId());
        params.put(ArrangeConstant.NEW_USER_ID, t.getEmployeeId());
        params.put(ArrangeConstant.AVG_WORKS, t.getAvgWorks());
        return params;
    }

    private String getArrangeWorkUrl() {
        return ArrangeConstant.URL_ARRANGE_WORK;
    }

    private void notifyArrangeWorkWeekContent() {

    }
}
