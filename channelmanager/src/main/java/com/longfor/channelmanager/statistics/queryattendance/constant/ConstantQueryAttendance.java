package com.longfor.channelmanager.statistics.queryattendance.constant;

/**
 * @author: gaomei
 * @date: 2018/1/4
 * @function:考勤查询
 */

public interface ConstantQueryAttendance {

    String URL_GET_CHECK_IN_LIST="channelManager/getCheckInList";//考勤查询url
    String IMG_URL = "imgUrl";//跳转大图界面传递参数的键名

    String SHOW_GET_NUM = "1";//1.外展
    String EXPAND_GET_NUM = "2";//2.外拓
    String SHOW_AND_EXPAND_CALL_NUM = "3";//3.外展外拓追电
    String CALL_GET_NUM = "4";//4.外呼陌电
    String CALL_CALL_NUM = "5";//5.外呼追电
}
