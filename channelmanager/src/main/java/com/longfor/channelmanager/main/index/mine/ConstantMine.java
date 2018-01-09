package com.longfor.channelmanager.main.index.mine;

/**
 * @author: tongzhenhua
 * @date: 2018/1/2
 * @function:
 */
public interface ConstantMine {
    int TAKE_COVER_REQUEST_CODE = 0xffff; // 封面拍照上传
    int SELECT_COVER_REQUEST_CODE = 0xfffe; // 封面选择相册上传

    int TAKE_PORTRAIT_REQUEST_CODE = 0xfffd; // 头像拍照上传
    int SELECT_PORTRAIT_REQUEST_CODE = 0xfffc; // 头像选择相册上传

    // 跳转类
    enum JUMB_TO{
        ABOUT_US,
        FEED_BACK,
        RECOMMEND,
        SETTING
    }
    String RECOMMEND_CODE = "recommend_code"; //bundle传值推荐码
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
