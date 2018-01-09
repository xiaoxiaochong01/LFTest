package com.longfor.channelmanager.statistics.queryattendance.adapter;

import android.support.v7.widget.GridLayoutManager;

import com.longfor.channelmanager.R;
import com.longfor.ui.bean.InnerCheckinsBean;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;
import com.longfor.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/9
 * @function:考勤查询的RecyclerView的数据适配器
 */

public class QueryAttendanceRvAdapter extends BaseRecyclerAdapter {
    public QueryAttendanceRvAdapter(List<MultipleItemEntity> data) {
        super(data);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        switch (item.getItemType()) {
            case QueryAttendanceItemType.QUERY_ATTENDANCE:
                String employName = (String) item.getField(MultipleFields.EMPLOYEE_NAME);
                List<String> workAddress = (List<String>) item.getField(MultipleFields.WORK_ADDRESSES);
                List<InnerCheckinsBean> innerCheckinsBeanList = (List<InnerCheckinsBean>) item.getField(MultipleFields.INNER_CHECK_INS);
                helper.setText(com.longfor.ui.R.id.tv_name,employName);
                break;
        }
    }

    @Override
    protected void init() {
        addItemType(QueryAttendanceItemType.QUERY_ATTENDANCE, R.layout.item_query_attendance);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getItemType();
    }

    @Override
    public void onItemClick(int position) {

    }
}
