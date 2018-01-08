package com.longfor.channelmanager.main.constants;

/**
 * @author: tongzhenhua
 * @date: 2018/1/2
 * @function:
 */
public interface ConstantMain {
    int TAKE_COVER_REQUEST_CODE = 0xffff;
    int SELECT_COVER_REQUEST_CODE = 0xfffe;

    int TAKE_PORTRAIT_REQUEST_CODE = 0xfffd;
    int SELECT_PORTRAIT_REQUEST_CODE = 0xfffc;

    // 跳转类
    enum JUMB_TO{
        ABOUT_US,
        FEED_BACK,
        RECOMMEND,
        SETTING
    }

    /** ---------------------个人信息接口相关 begin-------------------------*/
    String URL_TRAINEE_GET_PROFILE = "employee/getProfile";    //渠道专员 - 获取个人信息
    String EMPLOYEE_ID = "employeeId";
    /** ---------------------个人信息接口相关 end-------------------------*/

    /** ---------------------上传封面图片相关 begin-------------------------*/
    String URL_STUDENT_COVER = "employee/updateCover";//大学生-上传封面
    String COVER = "cover";
    /** ---------------------上传封面图片相关 end-------------------------*/

    /** ---------------------上传头像图片相关 begin-------------------------*/
    String URL_STUDENT_HEAD_IMG = "student/updateAvatar";//大学生-个人头像上传
    String HEAD_PORTRAIT = "avatar";
    /** ---------------------上传头像图片相关 end-------------------------*/

    /** ---------------------Home页接口相关 begin-------------------------*/
    String URL_HOME_PAGE = "channelManager/homepage";//首页接口
    String URL_GET_PROJECTS = "channelManager/getProjects";//渠道专员 - 获取项目列表接口
    String URL_STUDENT_UNREAD_MESSAGE_COUNT = "employee/unReadMessageCount";//实习生、渠道专员-未读消息
    /** ---------------------Home页接口相关 end-------------------------*/
}
