package com.longfor.channelmanager.arrange;

/**
 * @author: tongzhenhua
 * @date: 2018/1/19
 * @function:
 */
public interface ArrangeConstant {
    int ARRANGE_UNREAD_STATUS = 0; // 排班未读信息
    String URL_GET_ARRANGE_GROUP = "channelManager/getShifts";//35-渠道专员 - 获取排班列表信息
    String URL_GET_WEEK_ARRANGE_WORK_BY_TEAM = "channelManager/getWeekShiftsByTeam";//8-渠道专员 - 获取小组某周的排班情况接口
    String URL_GET_ARRANGE_WORK_BACK_UPS = "channelManager/getBackups";//9-渠道专员 - 获取小组排班备选人员的接口
    String URL_ARRANGE_WORK = "channelManager/arrangeWork";//10-渠道专员 - 排班接口
    String TEAM_ID = "teamId";
    String SHIFT_ID = "shiftId";
    String WORK_DATE = "workDate";
    String ORIGIN_USER_ID = "originUserId";
    String NEW_USER_ID = "newUserId";
    String AVG_WORKS = "avgWorks";
}
