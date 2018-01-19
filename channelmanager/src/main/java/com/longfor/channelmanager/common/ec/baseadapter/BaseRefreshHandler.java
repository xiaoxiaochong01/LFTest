package com.longfor.channelmanager.common.ec.baseadapter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.net.BaseSuccessListener;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.IError;
import com.longfor.core.utils.toast.ToastUtils;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.refresh.PagingBean;

import java.util.Map;

/**
 * @author: tongzhenhua
 * @date: 2018/1/11
 * @function:
 */
public abstract class BaseRefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,IError {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private BaseRecyclerAdapter mAdapter;
    private final DataConverter CONVERTER;

    /**
     * 获取网络请求地址
     * @return
     */
    public abstract String getUrl();
    /**
     * 获取网络请求参数
     * @return
     */
    public abstract Map<String, String> getParams();

    /**
     * 获取每页加载数据条数
     * 默认为getDefaultPageSize()
     * @return
     */
    public abstract int getPageSize();

    /**
     * 获取总数据条数
     * @param response 网络请求数据
     * @return
     */
    public abstract int getTotals(String response);

    /**
     * 获取 adapter
     * @param converter
     * @return
     */
    public abstract BaseRecyclerAdapter getAdapter(DataConverter converter);
    /**
     * 更新网络请求参数
     * @param params
     * @return
     */
    public abstract BaseRefreshHandler updateParams(String ... params);

    /**
     * 获取当前请求页
     * @return
     */
    protected String getPageIndex() {
        return BEAN.getPageIndex()+"";
    }
    public BaseRefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT,
                                    RecyclerView RECYCLERVIEW,
                                    DataConverter CONVERTER) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        this.BEAN = new PagingBean();
        this.RECYCLERVIEW = RECYCLERVIEW;
        this.CONVERTER = CONVERTER;
        this.BEAN.setPageSize(getPageSize());
        REFRESH_LAYOUT.setOnRefreshListener(this);
//        //设置adapter
//        mAdapter = getAdapter(CONVERTER.setJsonData(""));
//        mAdapter.setOnLoadMoreListener(BaseRefreshHandler.this, RECYCLERVIEW);
//        RECYCLERVIEW.setAdapter(mAdapter);
    }


    @Override
    public void onRefresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        BEAN.resetPageIndex();
        RestClient.builder()
                .raw(getParams())
                .url(getUrl())
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        REFRESH_LAYOUT.setRefreshing(false);
                        mAdapter.refresh(CONVERTER.setJsonData(response));
                        BEAN.setCurrentCount(BEAN.getPageSize());
                    }
                })
                .error(this)
                .loader(RECYCLERVIEW.getContext())
                .build()
                .post();
    }

    public void firstPage() {
        RestClient.builder()
                .raw(getParams())
                .url(getUrl())
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        BEAN.setTotal(getTotals(response));
                        //设置adapter
                        mAdapter = getAdapter(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(BaseRefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
//                        mAdapter.refresh(CONVERTER.setJsonData(response));
                        BEAN.setCurrentCount(BEAN.getPageSize());
                    }
                })
                .error(this)
                .loader(RECYCLERVIEW.getContext())
                .build()
                .post();
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
                    .url(getUrl())
                    .success(new BaseSuccessListener() {
                        @Override
                        public void success(String response) {
                            mAdapter.addData(CONVERTER.setJsonData(response).convert());
                            BEAN.addCurrentCount();
                            mAdapter.loadMoreComplete();
                        }
                    })
                    .error(this)
                    .loader(RECYCLERVIEW.getContext())
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
        if(REFRESH_LAYOUT.isRefreshing()) {
            REFRESH_LAYOUT.setRefreshing(false);
        }
        else if(mAdapter != null) {
            mAdapter.loadMoreComplete();
        }
    }

    public int getDefaultPageSize() {
        return Integer.valueOf(Constant.VALUE_PAGE_SIZE);
    }
}