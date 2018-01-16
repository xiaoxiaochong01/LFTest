package com.longfor.channelmanager.statistics.checkinstatistics.constant;

/**
 * @author: gaomei
 * @date: 2018/1/15
 * @function:
 */

public class CheckInStatisticsConstant {
    public static final String URL_GET_COMPANY_CHECK_IN = "channelManager/getCompanyCheckIns";
    public static final String URL_GET_PROJECT_CHECK_IN = "channelManager/getProjectCheckIns";
    public static final String URL_GET_TEAM_CHECK_IN = "channelManager/getTeamCheckIns";

    //    上岗统计的类型
    public static final int ITEM_TYPE_COMPANY = 0;
    public static final int ITEM_TYPE_PROJECT = 1;
    public static final int ITEM_TYPE_TEAM = 2;

    public static final String AREA_ID = "areaId";//请求-地区id
    public static final String PROJECT_ID = "projectId";//请求-项目id

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TODAY_CHECK_IN = "todayCheckIn";
    public static final String MONTH_AVG_CHECK_IN = "monthAvgCheckIn";
}
