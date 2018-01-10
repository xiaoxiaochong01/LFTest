package com.longfor.channelmanager.client.list;

import android.support.v7.widget.GridLayoutManager;

import com.longfor.channelmanager.R;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleItemEntity;
import com.longfor.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/10
 * @function:
 */
public class ClientListRecyclerAdapter extends BaseRecyclerAdapter {

    public ClientListRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
    }

    public static ClientListRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new ClientListRecyclerAdapter(data);
    }

    public static ClientListRecyclerAdapter create(DataConverter converter) {
        return new ClientListRecyclerAdapter(converter.convert());
    }
    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {

    }

    @Override
    protected void init() {
        addItemType(ItemTypeClient.TEXTS, R.layout.client_list_item);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return 0;
    }

    @Override
    public void onItemClick(int position) {

    }
}
