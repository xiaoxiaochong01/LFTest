package com.longfor.channelmanager.arrange.group;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.arrange.ArrangeConstant;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;
import com.longfor.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/19
 * @function:
 */
public class ArrangeGroupRecyclerAdapter extends BaseRecyclerAdapter {
    private IArrangeGroup iArrangeGroup;

    public ArrangeGroupRecyclerAdapter(List<MultipleItemEntity> data, IArrangeGroup iArrangeGroup) {
        super(data);
        this.iArrangeGroup = iArrangeGroup;
    }

    public static ArrangeGroupRecyclerAdapter create(DataConverter data, IArrangeGroup iArrangeGroup) {
        return new ArrangeGroupRecyclerAdapter(data.convert(), iArrangeGroup);
    }

    @Override
    protected void convert(final MultipleViewHolder helper, final MultipleItemEntity item) {
        final ArrangeGroupBean.DataBean dataBean = item.getField(MultipleFields.OBJECT);
        switch (item.getItemType()) {
            case ItemTypeArrangeGroup.ARRANGE_ITEM:
                helper.setText(R.id.tv_team_name, dataBean.getTeamName());
                helper.setText(R.id.tv_title, dataBean.getTitle());
                helper.setText(R.id.tv_address, dataBean.getWorkAddress());
                helper.getView(R.id.iv_read_status).setVisibility(dataBean.getReadStatus() == ArrangeConstant.ARRANGE_UNREAD_STATUS ? View.VISIBLE : View.GONE);
                helper.getView(R.id.rl_group).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iArrangeGroup != null) {
                            iArrangeGroup.jumbDelegate(dataBean);
                            dataBean.setReadStatus(ArrangeConstant.ARRANGE_READ_STATUS);
                            notifyItemChanged(helper.getAdapterPosition());
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void init() {
        addItemType(ItemTypeArrangeGroup.ARRANGE_ITEM, R.layout.arrange_group_item);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}
