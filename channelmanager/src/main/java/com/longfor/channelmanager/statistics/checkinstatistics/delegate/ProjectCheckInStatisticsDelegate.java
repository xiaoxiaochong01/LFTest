package com.longfor.channelmanager.statistics.checkinstatistics.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.core.delegates.LongForDelegate;

/**
 * @author: gaomei
 * @date: 2018/1/16
 * @function:项目级别上岗统计
 */

public class ProjectCheckInStatisticsDelegate extends LongForDelegate {
    private String mRoleType;
    private String mAreaId;

    public static ProjectCheckInStatisticsDelegate getInstance(String roleType,String areaId){
        ProjectCheckInStatisticsDelegate delegate=new ProjectCheckInStatisticsDelegate();
        delegate.mRoleType=roleType;
        delegate.mAreaId=areaId;
        return delegate;
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_statistics_company_check_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
