package com.longfor.channelmanager.client.list;

/**
 * @author: tongzhenhua
 * @date: 2018/1/10
 * @function:
 */
public interface ConstantClientList {
    // 客户列表
    int CLIENT_ALL = 0; // 全部
    int CLIENT_FIRST = 1; // 一级
    int CLIENT_SECOND = 2; // 二级
    int CLIENT_SUBSCRIBE = 3; // 认购

    /**--------------------------客户列表数据请求相关 begin ----------------------*/
    String CURRENT_PAGE = "currentPage";
    String PAGE_SIZE = "pageSize";
    String SEARCH_CONTENT = "searchContent";
    String INTENT_TYPE ="intentType";
    String ROLE_TYPE = "roleType";
    String URL_COMMISSION_QUERY_CUSTOMER_FOR_MANAGER = "/customer/queryCustomersForChannelManager";  //渠道专员-查询客户列表
    /**--------------------------客户列表数据请求相关 end ------------------------*/
}
