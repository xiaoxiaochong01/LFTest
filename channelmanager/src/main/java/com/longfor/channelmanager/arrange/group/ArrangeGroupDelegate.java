package com.longfor.channelmanager.arrange.group;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.arrange.week.ArrangeTeamWeekWorkDelegate;
import com.longfor.channelmanager.common.view.CommonHeadView;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.utils.log.LogUtils;

import butterknife.BindView;

/**
 * @author: tongzhenhua
 * @date: 2018/1/19
 * @function: 排班组列表
 */
public class ArrangeGroupDelegate extends LongForDelegate implements IArrangeGroup {
    @BindView(R2.id.rv_arrange_group)
    RecyclerView rvArrangeGroup;
    @BindView(R2.id.srl_arrange_group)
    SwipeRefreshLayout srlArrangeGroup;
    @BindView(R2.id.head_view_arrange_group)
    CommonHeadView headViewArrangeGroup;

    private String mTitleLeftText;
    private ArrangeGroupRefreshHandler mRefreshHandler;

    public static ArrangeGroupDelegate getInstance(String titleLeft) {
        ArrangeGroupDelegate delegate = new ArrangeGroupDelegate();
        delegate.mTitleLeftText = titleLeft;
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_arrange_group;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        if (!TextUtils.isEmpty(mTitleLeftText)) {
            headViewArrangeGroup.setLeftMsgVisiable(true);
            headViewArrangeGroup.setLeftMsg(mTitleLeftText);
        }
        headViewArrangeGroup.setTitle(R.string.arrange_title);
        headViewArrangeGroup.setLeftBackImageVisible(true);
        headViewArrangeGroup.setBottomLineVisible(true);
        headViewArrangeGroup.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportDelegate().pop();
            }
        });
        initRecyclerView();
        initRefreshLayout();
        mRefreshHandler = ArrangeGroupRefreshHandler.create(srlArrangeGroup, rvArrangeGroup, new ArrangeGroupDataConverter(), this);
        mRefreshHandler.firstPage();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.e("ArrangeGroupDelegate", "onHiddenChanged-" + hidden);
//        太浪费流量了，注释掉
//        if (!hidden) {
//            mRefreshHandler.firstPage();
//        }
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        rvArrangeGroup.setLayoutManager(manager);
    }

    private void initRefreshLayout() {
        srlArrangeGroup.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        srlArrangeGroup.setProgressViewOffset(true, 120, 300);
    }

    @Override
    public void jumbDelegate(ArrangeGroupBean.DataBean dataBean) {
        getSupportDelegate().start(ArrangeTeamWeekWorkDelegate.getInstance(getString(R.string.arrange_title), dataBean.getTeamName(), dataBean.getTeamId()));
    }
}
