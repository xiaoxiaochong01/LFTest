package com.longfor.channelmanager.login.contants;

/**
 * @author: tongzhenhua
 * @date: 2017/12/26
 * @function:
 */
public interface ConstantLogin {
    /**---------------------------------登录页常量 begin----------------------------------*/
    int CODE_0 = 0; // code返回值为0
    int CODE_2 = 2; // code返回值为2提示相应信息
    /**-----------------------------------登录页常量 end---------------------------------*/

    /**---------------------------------登录页网络请求相关begin----------------------------------*/
    String URL_LOGIN = "channelManager/getChannelLogin";////登录
    String ENTRY_CODE = "entryCode";
    String LOGIN_NAME = "loginName";
    String URL_SEND_CODE = "channelManager/getChannelSendCode";//渠道专员-获取验证码
    /**-----------------------------------登录页网络请求相关end---------------------------------*/
}
