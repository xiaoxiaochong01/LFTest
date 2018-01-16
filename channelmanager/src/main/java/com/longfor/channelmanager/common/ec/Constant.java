package com.longfor.channelmanager.common.ec;

/**
 * @author: tongzhenhua
 * @date: 2018/1/2
 * @function:
 */
public interface Constant {
    /**
     * 返回界面名
     */
    String TITLE_LEFT_TEXT = "title_left_text";
    String WEB_URL = "web_view_url";
    String WEB_TITLE = "web_view_title";

    String EMPLOYEE_ID = "employeeId";
    String PROJECT_ID = "projectId";
    String ID = "id";
    String CURRENT_PAGE = "currentPage";//请求-当前页键名
    String KEY_PAGE_SIZE = "pageSize";//请求-每页数量键名
    String VALUE_PAGE_SIZE = "20";//请求-每页数量值
    String ROLE_TYPE="roleType";//请求-用户角色的键名

    /**
     * 权限请求Code
     */
    int REQUEST_WRITE_CODE = 0xefff;
    int REQUEST_CAMERA_CODE = 0xeffe;
}
