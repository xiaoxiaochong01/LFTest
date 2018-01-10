package com.longfor.channelmanager.client.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.core.app.LongFor;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.ISuccess;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleRecyclerAdapter;
import com.longfor.ui.refresh.PagingBean;
import com.longfor.ui.refresh.RefreshHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: tongzhenhua
 * @date: 2018/1/10
 * @function: 客户列表加载数据控制类
 */
public class ClientListRefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private ClientListRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;

    private String mRoleType = "0";//1：置业顾问 2：实习生 3：客户
    private String mSearchContent = "";
    private int mIntentType;//0：全部 1：一级 2：二级  3：认购


    public ClientListRefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT,
                          PagingBean BEAN,
                          RecyclerView RECYCLERVIEW,
                          DataConverter CONVERTER) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        this.BEAN = BEAN;
        this.RECYCLERVIEW = RECYCLERVIEW;
        this.CONVERTER = CONVERTER;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static ClientListRefreshHandler creat(SwipeRefreshLayout swipeRefreshLayout,
                                       RecyclerView recyclerView,
                                       DataConverter converter) {
        return new ClientListRefreshHandler(swipeRefreshLayout, new PagingBean(), recyclerView, converter);
    }

    @Override
    public void onRefresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        LongFor.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);
    }

    public void firstPage() {
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url("")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total")).setPageSize(object.getInteger("page_size"));
                        //设置adapter
                        mAdapter = ClientListRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(ClientListRefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
//        params.put(Constant.EMPLOYEE_ID, employeeId);
//        params.put(ConstantClientList.CURRENT_PAGE, currentPage + "");
//        params.put(ConstantClientList.PAGE_SIZE, pageSize + "");
//        params.put(ConstantClientList.ROLE_TYPE, mRoleType);
//        params.put(ConstantClientList.SEARCH_CONTENT, searchContent);
//        params.put(ConstantClientList.INTENT_TYPE, intentType);
//        params.put(Constant.PROJECT_ID, projectId);
        return params;
    }
    private void paging(final String url) {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();
        if (mAdapter.getData().size() < pageSize || currentCount > total) {
            mAdapter.loadMoreEnd(true);
        } else {
            LongFor.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestClient
                            .builder()
                            .url(url + index)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    CONVERTER.clearData();
                                    mAdapter.addData(CONVERTER.setJsonData(response).convert());
                                    //累加数量
                                    BEAN.setCurrentCount(mAdapter.getData().size());
                                    mAdapter.loadMoreComplete();
                                    BEAN.addIndex();
                                }
                            })
                            .build()
                            .get();
                }
            }, 1000);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        paging("refresh.php?index=");
    }
}
