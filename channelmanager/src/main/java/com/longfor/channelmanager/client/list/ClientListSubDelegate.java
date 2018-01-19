package com.longfor.channelmanager.client.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.client.detail.ClientDetailDelegate;
import com.longfor.channelmanager.client.search.ConstantClientSearch;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.utils.log.LogUtils;
import com.longfor.core.utils.toast.ToastUtils;
import com.longfor.ui.recycler.BaseDecoration;

import butterknife.BindView;

/**
 * @author: tongzhenhua
 * @date: 2018/1/9
 * @function: 客户列表子界面
 */
public class ClientListSubDelegate extends LongForDelegate implements OnSearchConditionListener{
    @BindView(R2.id.rv_client_sub)
    RecyclerView rvClientSub;
    @BindView(R2.id.srl_client_list)
    SwipeRefreshLayout srlClientList;
    public TestBaseRefreshHandler mRefreshHandler;
    private String intentType;
    private boolean isNeedLazyLoad = false; // 可见状态下是否需要请求网络
    private boolean isNeedLazyLoading = false; // 是否正在请求网络isNeedLazyLoad为true的情况

    private OnRefreshSearchContentListener searchContentListener;
    private IClientList clientList;

    public static ClientListSubDelegate getInstance(String intentType, OnRefreshSearchContentListener searchContentListener, IClientList clientList) {

        ClientListSubDelegate delegate = new ClientListSubDelegate();
        delegate.intentType = intentType;
        delegate.searchContentListener = searchContentListener;
        delegate.clientList = clientList;
        return delegate;
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_client_list_sub;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isNeedLazyLoad) {
            isNeedLazyLoad = false;
            isNeedLazyLoading = true;
            mRefreshHandler.updateParams(ClientListDelegate.mRoleType, ClientListDelegate.mSearchContent);
            mRefreshHandler.firstPage();
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);

        if(requestCode == ConstantClientSearch.DELEGATE_RESULT_CODE_CLIENT_SEARCH&&resultCode == RESULT_OK&&data != null) {
            String mRoleType = data.getString(ConstantClientList.ROLE_TYPE, ConstantClientList.ROLE_TYPE_DEFAULT);
            String mSearchContent = data.getString(ConstantClientList.SEARCH_CONTENT, ConstantClientList.SEARCH_CONTENT_DEFAULT);
            if (searchContentListener != null) {
                searchContentListener.onRefresh(mRoleType, mSearchContent);
            }
        }
        else {
//            ToastUtils.showMessage("requestCode="+requestCode);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler = TestBaseRefreshHandler.create(intentType, srlClientList, rvClientSub, new ClientListDataConverter(), clientList);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if(isNeedLazyLoading) {
            isNeedLazyLoading = false;
        }
        else {
            mRefreshHandler.updateParams(ClientListDelegate.mRoleType, ClientListDelegate.mSearchContent);
            mRefreshHandler.firstPage();
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        final Context context = getContext();
        rvClientSub.setLayoutManager(manager);
        if (context != null) {
            rvClientSub.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(context, R.color.app_background), 5));
        }
    }

    private void initRefreshLayout() {
        srlClientList.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        srlClientList.setProgressViewOffset(true, 120, 300);
    }

    @Override
    public void update(String roleId, String searchContent) {
        if(getUserVisibleHint()&&isAdded()) {
            mRefreshHandler.updateParams(ClientListDelegate.mRoleType, ClientListDelegate.mSearchContent);
            mRefreshHandler.firstPage();
        }
        else if(!getUserVisibleHint()) {
            if(isAdded()) {
                isNeedLazyLoad = true;
            }
        }
    }

}
