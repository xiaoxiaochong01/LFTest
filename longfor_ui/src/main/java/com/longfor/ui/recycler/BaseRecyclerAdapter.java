package com.longfor.ui.recycler;

import android.support.v7.widget.GridLayoutManager;

import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/8
 * @function:
 */
public abstract class BaseRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder> implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener{

    public BaseRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
        setSpanSizeLookup(this);
        openLoadAnimation();
        isFirstOnly(false);
    }

    @Override
    protected abstract void convert(MultipleViewHolder helper, MultipleItemEntity item);

    protected abstract void init();

    @Override
    public abstract int getSpanSize(GridLayoutManager gridLayoutManager, int position);

    @Override
    public abstract void onItemClick(int position);

    public void refresh(List<MultipleItemEntity> data) {
        getData().clear();
        setNewData(data);
        notifyDataSetChanged();
    }
}
