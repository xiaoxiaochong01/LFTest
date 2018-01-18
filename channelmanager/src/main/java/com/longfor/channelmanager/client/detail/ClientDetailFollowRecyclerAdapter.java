package com.longfor.channelmanager.client.detail;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;
import com.longfor.ui.recycler.MultipleViewHolder;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/17
 * @function:
 */
public class ClientDetailFollowRecyclerAdapter extends BaseRecyclerAdapter {
    public ClientDetailFollowRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
    }
    public static ClientDetailFollowRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new ClientDetailFollowRecyclerAdapter(data);
    }
    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {

        switch (item.getItemType()) {
            case ItemTypeClientDetail.FOLLOW:
                ClientDetailBean.DataBean.FollowsBean.FollowDetailsBean followDetailsBean = item.getField(MultipleFields.OBJECT);
                helper.setText(R.id.tv_call_input, followDetailsBean.getName());
                String content = followDetailsBean.getFollowContent();
                AppCompatTextView tvClientInfo = helper.getView(R.id.tv_client_info);
                if(TextUtils.isEmpty(content)) {
                    tvClientInfo.setVisibility(View.GONE);
                }
                else {
                    tvClientInfo.setVisibility(View.VISIBLE);
                    tvClientInfo.setText(content);
                }
                String transTime = timeTransformat(followDetailsBean.getFollowTime());
                helper.setText(R.id.tv_date, transTime);
                break;
        }
    }

    @Override
    protected void init() {
        addItemType(ItemTypeClientDetail.FOLLOW, R.layout.client_detail_follow_child_item);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }

    /**
     * 时间戳转换格式
     *
     * @param time
     * @return
     */
    public static String timeTransformat(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd HH:mm");
        return sf.format(d);
    }
}
