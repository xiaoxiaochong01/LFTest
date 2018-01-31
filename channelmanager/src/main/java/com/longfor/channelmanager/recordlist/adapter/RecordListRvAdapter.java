package com.longfor.channelmanager.recordlist.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.utils.ImageLoader;
import com.longfor.channelmanager.recordlist.constant.RecordListConstant;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;
import com.longfor.ui.recycler.MultipleViewHolder;
import com.longfor.ui.view.circleimageview.CircleImageView;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/23
 * @function:
 */

public class RecordListRvAdapter extends BaseRecyclerAdapter {
    public RecordListRvAdapter(List<MultipleItemEntity> data) {
        super(data);
    }

    public static RecordListRvAdapter create(List<MultipleItemEntity> data){
        return new RecordListRvAdapter(data);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        helper.setText(R.id.tv_record_rank, String.valueOf(item.getField(RecordListConstant.RANK_POSITION)));
        ImageLoader.display(helper.itemView.getContext(),
                ((CircleImageView) helper.getView(R.id.civ_record_portrait)),
                ((String) item.getField(RecordListConstant.PORTRAIT)),R.mipmap.ic_default_portrait,R.mipmap.ic_default_portrait);
        helper.getView(R.id.iv_record_rank).setVisibility(View.VISIBLE);
        helper.setText(R.id.tv_record_name, ((String) item.getField(RecordListConstant.NAME)));
        helper.setText(R.id.tv_record_result, ((String) item.getField(RecordListConstant.RESULT)));
        switch (((Integer) item.getField(RecordListConstant.RANK_POSITION))) {
            case RecordListConstant.RANK_FIRST:
                ((ImageView) helper.getView(R.id.iv_record_rank)).setImageResource(R.mipmap.ic_rank_first);
                ((TextView) helper.getView(R.id.tv_record_result)).setTextColor(helper.itemView.getContext().getResources().getColor(R.color.rank_first));
                break;
            case RecordListConstant.RANK_SECOND:
                ((ImageView) helper.getView(R.id.iv_record_rank)).setImageResource(R.mipmap.ic_rank_second);
                ((TextView) helper.getView(R.id.tv_record_result)).setTextColor(helper.itemView.getContext().getResources().getColor(R.color.rank_second));
                break;
            case RecordListConstant.RANK_THIRD:
                ((ImageView) helper.getView(R.id.iv_record_rank)).setImageResource(R.mipmap.ic_rank_third);
                ((TextView) helper.getView(R.id.tv_record_result)).setTextColor(helper.itemView.getContext().getResources().getColor(R.color.rank_third));
                break;
            default:
                helper.getView(R.id.iv_record_rank).setVisibility(View.GONE);
                ((TextView) helper.getView(R.id.tv_record_result)).setTextColor(helper.itemView.getContext().getResources().getColor(R.color.rank_normal));
                break;
        }
    }

    @Override
    protected void init() {
        addItemType(RecordListConstant.ITEM_TYPE, R.layout.item_record_list);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}
