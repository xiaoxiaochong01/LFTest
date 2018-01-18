package com.longfor.channelmanager.client.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.net.BaseSuccessListener;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.core.app.LongFor;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.IError;
import com.longfor.core.net.callback.ISuccess;
import com.longfor.core.utils.toast.ToastUtils;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.refresh.PagingBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: tongzhenhua
 * @date: 2018/1/10
 * @function: 客户列表加载数据控制类
 */
public class ClientListRefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,IError {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private ClientListRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private final String INTENT_TYPE;//0：全部 1：一级 2：二级  3：认购

    private String mRoleType = "0";//1：置业顾问 2：实习生 3：客户
    private String mSearchContent = "";



    public ClientListRefreshHandler(String INTEN_TTYPE,SwipeRefreshLayout REFRESH_LAYOUT,
                          PagingBean BEAN,
                          RecyclerView RECYCLERVIEW,
                          DataConverter CONVERTER) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        this.BEAN = BEAN;
        this.RECYCLERVIEW = RECYCLERVIEW;
        this.CONVERTER = CONVERTER;
        this.INTENT_TYPE = INTEN_TTYPE;
        this.BEAN.setPageSize(Integer.valueOf(Constant.VALUE_PAGE_SIZE));
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static ClientListRefreshHandler create(String intent_type, SwipeRefreshLayout swipeRefreshLayout,
                                       RecyclerView recyclerView,
                                       DataConverter converter) {
        return new ClientListRefreshHandler(intent_type,swipeRefreshLayout, new PagingBean(), recyclerView, converter);
    }

    public ClientListRefreshHandler updataParams(String roleType, String searchContent) {
        mRoleType = roleType;
        mSearchContent = searchContent;
        return this;
    }

    @Override
    public void onRefresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        BEAN.resetPageIndex();
        RestClient.builder()
                .raw(getParams())
                .url(ConstantClientList.URL_COMMISSION_QUERY_CUSTOMER_FOR_MANAGER)
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        REFRESH_LAYOUT.setRefreshing(false);
                        mAdapter.refresh(CONVERTER.setJsonData(response));
                        BEAN.setCurrentCount(BEAN.getPageSize());
                    }
                })
                .error(this)
                .build()
                .post();
    }

    public void firstPage() {
        RestClient.builder()
                .raw(getParams())
                .url(ConstantClientList.URL_COMMISSION_QUERY_CUSTOMER_FOR_MANAGER)
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        final ClientListDataBean dataBean = JSON.parseObject(response, ClientListDataBean.class);
                        BEAN.setTotal(dataBean.getData().getTotals());
                        //设置adapter
                        mAdapter = ClientListRecyclerAdapter.create(CONVERTER.setJsonData(response),null);
                        mAdapter.setOnLoadMoreListener(ClientListRefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                    }
                })
                .error(this)
                .build()
                .post();
    }

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMPLOYEE_ID, DatabaseManager.getEmployeeId());
        params.put(Constant.CURRENT_PAGE, BEAN.getPageIndex()+"");
        params.put(Constant.KEY_PAGE_SIZE, Constant.VALUE_PAGE_SIZE);
        params.put(Constant.ROLE_TYPE, mRoleType);
        params.put(Constant.PROJECT_ID, DatabaseManager.getProjectId());
        params.put(ConstantClientList.SEARCH_CONTENT, mSearchContent);
        params.put(ConstantClientList.INTENT_TYPE, INTENT_TYPE);

        return params;
    }
    private void paging() {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        if (mAdapter.getData().size() < pageSize || currentCount >= total) {
            mAdapter.loadMoreEnd(true);
        } else {
            RestClient.builder()
                    .raw(getParams())
                    .url(ConstantClientList.URL_COMMISSION_QUERY_CUSTOMER_FOR_MANAGER)
                    .success(new BaseSuccessListener() {
                        @Override
                        public void success(String response) {
                            mAdapter.addData(CONVERTER.setJsonData(response).convert());
                            BEAN.addCurrentCount();
                            mAdapter.loadMoreComplete();
                        }
                    })
                    .error(this)
                    .build()
                    .post();

        }
    }

    @Override
    public void onLoadMoreRequested() {
        BEAN.addIndex();
        paging();
    }

    @Override
    public void onError(int code, String msg) {
        ToastUtils.showMessage(msg);
    }
}
