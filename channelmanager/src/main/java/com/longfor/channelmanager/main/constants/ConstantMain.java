package com.longfor.channelmanager.main.constants;

/**
 * @author: tongzhenhua
 * @date: 2018/1/2
 * @function:
 */
public interface ConstantMain {
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
}
