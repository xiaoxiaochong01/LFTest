package com.longfor.ec.main.index;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.longfor.core.delegates.bottom.BottomItemDelegate;
import com.longfor.ec.R;
import com.longfor.ec.R2;
import com.longfor.ec.main.ECBottomDelegate;
import com.longfor.ui.recycler.BaseDecoration;
import com.longfor.ui.refresh.RefreshHandler;

import butterknife.BindView;

/**
 * Created by zhanghaitao1 on 2017/12/25.
 */

public class IndexDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconScan = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchView = null;

    private RefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRefreshHandler = RefreshHandler.creat(mRefreshLayout,mRecyclerView,new IndexDataConverter());
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("index.php");
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(),4);
        final Context context =getContext();
        mRecyclerView.setLayoutManager(manager);
        if(context!=null){
            mRecyclerView.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(context,R.color.layout_bg_gray_f4),5));
        }
//        final ECBottomDelegate ecBottomDelegate = getParentDelegate();
//        mRecyclerView.addOnItemTouchListener();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshLayout.setProgressViewOffset(true,120,300);
    }

    public void test(){
       getParentDelegate().start(new ECBottomDelegate());
    }
}
