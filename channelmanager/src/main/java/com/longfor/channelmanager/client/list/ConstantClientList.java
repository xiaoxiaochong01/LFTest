package com.longfor.channelmanager.client.list;

/**
 * @author: tongzhenhua
 * @date: 2018/1/10
 * @function:
 */
public interface ConstantClientList {
    // 客户列表
    String CLIENT_ALL = "0"; // 全部
    String CLIENT_FIRST = "1"; // 一级
    String CLIENT_SECOND = "2"; // 二级
    String CLIENT_SUBSCRIBE = "3"; // 认购

    String ROLE_TYPE_DEFAULT = "0";
    String SEARCH_CONTENT_DEFAULT = "";

    int DELEGATE_ON_RESULT_CODE = 0xcccc;

    /**--------------------------客户列表数据请求相关 begin ----------------------*/
    String CURRENT_PAGE = "currentPage";
    String PAGE_SIZE = "pageSize";
    String SEARCH_CONTENT = "searchContent";
    String INTENT_TYPE ="intentType";
    String ROLE_TYPE = "roleType";
    String URL_COMMISSION_QUERY_CUSTOMER_FOR_MANAGER = "customer/queryCustomersForChannelManager";  //渠道专员-查询客户列表
    /**--------------------------客户列表数据请求相关 end ------------------------*/
}
