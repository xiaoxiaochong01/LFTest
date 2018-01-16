package com.longfor.channelmanager.statistics.checkinstatistics.adapter;

import android.support.v7.widget.GridLayoutManager;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.statistics.checkinstatistics.constant.CheckInStatisticsConstant;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;
import com.longfor.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/15
 * @function:
 */

public class CheckInStatisticsRvAdapter extends BaseRecyclerAdapter {
    private int mCheckInType;

    public CheckInStatisticsRvAdapter(List<MultipleItemEntity> data, int checkInType) {
        super(data);
        mCheckInType = checkInType;
    }

    public static CheckInStatisticsRvAdapter create(List<MultipleItemEntity> data, int checkInType) {
        return new CheckInStatisticsRvAdapter(data, checkInType);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        helper.setText(R.id.tv_check_in_name, ((String) item.getField(CheckInStatisticsConstant.NAME)));
        helper.setText(R.id.tv_today_check_in, String.valueOf(item.getField(CheckInStatisticsConstant.TODAY_CHECK_IN)));
        helper.setText(R.id.tv_month_avg_check_in, String.valueOf(item.getField(CheckInStatisticsConstant.MONTH_AVG_CHECK_IN)));
        if (((Integer) item.getField(MultipleFields.ITEM_TYPE))!=mCheckInType) {
            helper.setTextColor(R.id.tv_check_in_name, helper.itemView.getContext().getResources().getColor(R.color.orange_red));
            helper.setTextColor(R.id.tv_today_check_in, helper.itemView.getContext().getResources().getColor(R.color.orange_red));
            helper.setTextColor(R.id.tv_month_avg_check_in, helper.itemView.getContext().getResources().getColor(R.color.orange_red));
        } else {
            helper.setTextColor(R.id.tv_check_in_name, helper.itemView.getContext().getResources().getColor(R.color.gray_6));
            helper.setTextColor(R.id.tv_today_check_in, helper.itemView.getContext().getResources().getColor(R.color.gray_6));
            helper.setTextColor(R.id.tv_month_avg_check_in, helper.itemView.getContext().getResources().getColor(R.color.gray_6));
        }
    }

    @Override
    protected void init() {
        addItemType(CheckInStatisticsConstant.ITEM_TYPE_COMPANY, R.layout.item_check_in_statistics);
        addItemType(CheckInStatisticsConstant.ITEM_TYPE_PROJECT, R.layout.item_check_in_statistics);
        addItemType(CheckInStatisticsConstant.ITEM_TYPE_TEAM, R.layout.item_check_in_statistics);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}
