package com.longfor.channelmanager.statistics.checkinstatistics.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

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
    private OnItemClickListener mOnItemClickListener;

    public CheckInStatisticsRvAdapter(List<MultipleItemEntity> data, int checkInType, OnItemClickListener onItemClickListener) {
        super(data);
        mCheckInType = checkInType;
        mOnItemClickListener = onItemClickListener;
    }

    public static CheckInStatisticsRvAdapter create(List<MultipleItemEntity> data, int checkInType, OnItemClickListener onItemClickListener) {
        return new CheckInStatisticsRvAdapter(data, checkInType, onItemClickListener);
    }

    @Override
    protected void convert(MultipleViewHolder helper, final MultipleItemEntity item) {
        helper.setText(R.id.tv_check_in_name, ((String) item.getField(CheckInStatisticsConstant.NAME)));
        helper.setText(R.id.tv_today_check_in, String.valueOf(item.getField(CheckInStatisticsConstant.TODAY_CHECK_IN)));
        helper.setText(R.id.tv_month_avg_check_in, String.valueOf(item.getField(CheckInStatisticsConstant.MONTH_AVG_CHECK_IN)));
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem(item.getItemType(), (String) item.getField(CheckInStatisticsConstant.ID));
            }
        });
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

    private void clickItem(int itemType, String id) {
        if (mOnItemClickListener != null && itemType == mCheckInType) {
            switch (mCheckInType) {
                case CheckInStatisticsConstant.ITEM_TYPE_COMPANY:
                    mOnItemClickListener.onItemClick(id);
                    break;
                case CheckInStatisticsConstant.ITEM_TYPE_PROJECT:
                    mOnItemClickListener.onItemClick(id);
                    break;
                case CheckInStatisticsConstant.ITEM_TYPE_TEAM:
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String id);
    }
}
