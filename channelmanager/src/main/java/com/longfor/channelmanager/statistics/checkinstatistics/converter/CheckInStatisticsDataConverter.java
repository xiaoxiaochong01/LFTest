package com.longfor.channelmanager.statistics.checkinstatistics.converter;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.statistics.checkinstatistics.bean.CompanyCheckInBean;
import com.longfor.channelmanager.statistics.checkinstatistics.bean.ProjectCheckInBean;
import com.longfor.channelmanager.statistics.checkinstatistics.bean.TeamCheckInBean;
import com.longfor.channelmanager.statistics.checkinstatistics.constant.CheckInStatisticsConstant;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/15
 * @function:上岗统计
 */

public class CheckInStatisticsDataConverter extends DataConverter {
    private int mItemType;

    public CheckInStatisticsDataConverter(int itemType) {
        mItemType = itemType;
    }

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        clearData();
        switch (mItemType) {
            case CheckInStatisticsConstant.ITEM_TYPE_COMPANY:
                parseCompanyCheckInData();
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_PROJECT:
                parseProjectCheckInData();
                break;
            case CheckInStatisticsConstant.ITEM_TYPE_TEAM:
                parseTeamCheckInData();
                break;
        }
        return ENTITIES;
    }

    private void parseCompanyCheckInData() {
        CompanyCheckInBean companyCheckInBean = JSON.parseObject(getJsonData(), CompanyCheckInBean.class);
        List<CompanyCheckInBean.DataBean> data = companyCheckInBean.getData();
        if (companyCheckInBean != null && data != null) {
            for (int i = 0; i < data.size(); i++) {
                MultipleItemEntity entity = addData(CheckInStatisticsConstant.ITEM_TYPE_COMPANY, data.get(i).getAreaId(),
                        data.get(i).getAreaName(), data.get(i).getCheckins(), data.get(i).getAvg());
                ENTITIES.add(entity);
            }
        }
    }

    private void parseProjectCheckInData() {
        ProjectCheckInBean projectCheckInBean = JSON.parseObject(getJsonData(), ProjectCheckInBean.class);
        if (projectCheckInBean != null) {
            CompanyCheckInBean.DataBean area = projectCheckInBean.getData().getArea();
            if (area != null) {
                MultipleItemEntity entity = addData(CheckInStatisticsConstant.ITEM_TYPE_COMPANY, area.getAreaId(), area.getAreaName(),
                        area.getCheckins(), area.getAvg());
                ENTITIES.add(0, entity);
            }
            List<ProjectCheckInBean.DataBean.ProjectsBean> projects = projectCheckInBean.getData().getProjects();
            if (projects != null) {
                for (int i = 0; i < projects.size(); i++) {
                    MultipleItemEntity entity = addData(CheckInStatisticsConstant.ITEM_TYPE_PROJECT, projects.get(i).getProjectId(),
                            projects.get(i).getProjectName(), projects.get(i).getCheckins(), projects.get(i).getAvg());
                    ENTITIES.add(entity);
                }
            }
        }
    }

    private void parseTeamCheckInData() {
        TeamCheckInBean teamCheckInBean = JSON.parseObject(getJsonData(), TeamCheckInBean.class);
        if (teamCheckInBean != null) {
            ProjectCheckInBean.DataBean.ProjectsBean project = teamCheckInBean.getData().getProject();
            if (project != null) {
                MultipleItemEntity entity = addData(CheckInStatisticsConstant.ITEM_TYPE_PROJECT, project.getProjectId(), project.getProjectName(),
                        project.getCheckins(), project.getAvg());
                ENTITIES.add(0, entity);
            }
            List<TeamCheckInBean.DataBean.TeamsBean> teams = teamCheckInBean.getData().getTeams();
            if (teams != null) {
                for (int i = 0; i < teams.size(); i++) {
                    MultipleItemEntity entity = addData(CheckInStatisticsConstant.ITEM_TYPE_TEAM, teams.get(i).getTeamId(), teams.get(i).getTeamName(),
                            teams.get(i).getCheckins(), teams.get(i).getAvg());
                    ENTITIES.add(entity);
                }
            }
        }
    }

    private MultipleItemEntity addData(int itemType, String id, String name, int todayCheckIn, double monthAvgCheckIn) {
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, itemType)
                .setField(CheckInStatisticsConstant.ID, id)
                .setField(CheckInStatisticsConstant.NAME, name)
                .setField(CheckInStatisticsConstant.TODAY_CHECK_IN, todayCheckIn)
                .setField(CheckInStatisticsConstant.MONTH_AVG_CHECK_IN, monthAvgCheckIn)
                .setField(MultipleFields.SPAN_SIZE, 1)
                .build();
        return entity;
    }
}
