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
import com.longfor.channelmanager.statistics.queryattendance.adapter.QueryAttendanceConverter;
import com.longfor.channelmanager.statistics.queryattendance.adapter.QueryAttendanceRefreshHandler;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.ui.recycler.BaseDecoration;

import butterknife.BindView;

/**
 * @author: tongzhenhua
 * @date: 2018/1/9
 * @function:
 */
public class ClientListSubDelegate extends LongForDelegate {
    @BindView(R2.id.rv_client_sub)
    RecyclerView rvClientSub;
    @BindView(R2.id.srl_client_list)
    SwipeRefreshLayout srlClientList;
    public QueryAttendanceRefreshHandler mRefreshHandler;
    private String employeeId;
    private String projectId;
    private String roleType;
    @Override
    public Object setLayout() {
        return R.layout.delegate_client_list_sub;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRefreshHandler = QueryAttendanceRefreshHandler.creat(srlClientList, rvClientSub, new QueryAttendanceConverter());
        initRefreshLayout();
        initRecyclerView();
        roleType="1";
        mRefreshHandler.firstPage(roleType);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
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
}
