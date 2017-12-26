package com.longfor.channelmanager.lancher.constants;

/**
 * @author: tongzhenhua
 * @date: 2017/12/25
 * @function:
 */
public interface Constant {
    /**-----------------------------启动页常量begin--------------------------------*/
    int CONSTANT_FORCE_UPDATE = 2; // 强制版本更新
    int CONSTANT_NO_FORCE_UPDATE = 0; // 非强制版本更新
    /**-----------------------------启动页常量end--------------------------------*/

    /**---------------------------------启动页网络请求相关begin----------------------------------*/
    String URL_VERSION_CHECK = "common/versionCheck";//版本升级检测接口
    String APP_ID = "appId";
    String VERSION = "version";
    String CLIENT = "client";
    /**-----------------------------------启动页网络请求相关end---------------------------------*/
}
