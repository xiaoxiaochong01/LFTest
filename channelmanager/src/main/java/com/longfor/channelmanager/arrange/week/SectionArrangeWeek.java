package com.longfor.channelmanager.arrange.week;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author: gaomei
 * @date: 2018/1/26
 * @function:
 */

public class SectionArrangeWeek extends SectionEntity<ArrangeTeamWeekWorkBean.DataBean.WeekDatasBean.ShiftsBean.ArrangedBean>{
    private int mPersonAmount;
    private String mShiftId;

    public SectionArrangeWeek(boolean isHeader, String header,int personAmount,String shiftId) {
        super(isHeader, header);
        mPersonAmount=personAmount;
        mShiftId=shiftId;
    }

    public SectionArrangeWeek(ArrangeTeamWeekWorkBean.DataBean.WeekDatasBean.ShiftsBean.ArrangedBean arrangedBean) {
        super(arrangedBean);
    }

    public int getPersonAmount() {
        return mPersonAmount;
    }

    public void setPersonAmount(int personAmount) {
        mPersonAmount = personAmount;
    }

    public String getShiftId() {
        return mShiftId;
    }

    public void setShiftId(String shiftId) {
        mShiftId = shiftId;
    }
}
