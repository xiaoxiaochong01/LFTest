package com.longfor.channelmanager.client.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.ec.baseadapter.BaseRefreshHandler;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.DataConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: tongzhenhua
 * @date: 2018/1/11
 * @function:
 */
public class TestBaseRefreshHandler extends BaseRefreshHandler {
    private final String INTENT_TYPE;//0：全部 1：一级 2：二级  3：认购

    private String mRoleType = "0";//1：置业顾问 2：实习生 3：客户
    private String mSearchContent = "";

    public TestBaseRefreshHandler(String INTENT_TYPE,SwipeRefreshLayout REFRESH_LAYOUT, RecyclerView RECYCLERVIEW, DataConverter CONVERTER) {
        super(REFRESH_LAYOUT, RECYCLERVIEW, CONVERTER);
        this.INTENT_TYPE = INTENT_TYPE;
    }

    public static TestBaseRefreshHandler create(String INTENT_TYPE,SwipeRefreshLayout REFRESH_LAYOUT, RecyclerView RECYCLERVIEW, DataConverter CONVERTER) {
        return new TestBaseRefreshHandler(INTENT_TYPE, REFRESH_LAYOUT, RECYCLERVIEW, CONVERTER);
    }

    @Override
    public String getUrl() {
        return ConstantClientList.URL_COMMISSION_QUERY_CUSTOMER_FOR_MANAGER;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMPLOYEE_ID, DatabaseManager.getEmployeeId());
        params.put(Constant.CURRENT_PAGE, getPageIndex());
        params.put(Constant.KEY_PAGE_SIZE, Constant.VALUE_PAGE_SIZE);
        params.put(Constant.ROLE_TYPE, mRoleType);
        params.put(Constant.PROJECT_ID, DatabaseManager.getProjectId());
        params.put(ConstantClientList.SEARCH_CONTENT, mSearchContent);
        params.put(ConstantClientList.INTENT_TYPE, INTENT_TYPE);

        return params;
    }

    @Override
    public int getPageSize() {
        return getDefaultPageSize();
    }

    @Override
    public int getTotals(String response) {
        ClientListDataBean dataBean = JSON.parseObject(response, ClientListDataBean.class);
        return dataBean.getData().getTotals();
    }

    @Override
    public BaseRecyclerAdapter getAdapter(DataConverter converter) {
        return ClientListRecyclerAdapter.create(converter);
    }

    @Override
    public BaseRefreshHandler updateParams(String... params) {
        if(params != null && params.length == 1) {
            mRoleType = params[0];
            mSearchContent = params[1];
        }
        return this;
    }
}
