package com.longfor.channelmanager.client.detail;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.longfor.channelmanager.R;
import com.longfor.ui.recycler.BaseDecoration;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;
import com.longfor.ui.recycler.MultipleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/17
 * @function:
 */
public class ClientDetailRecyclerAdapter extends BaseRecyclerAdapter {
    private IClientDetailHandler clientDetailHandler;

    public ClientDetailRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
    }

    public static ClientDetailRecyclerAdapter create(ClientDetailDataConverter data) {
        return  new ClientDetailRecyclerAdapter(data.convert());
    }
    public static ClientDetailRecyclerAdapter create(List<MultipleItemEntity> data) {
        return  new ClientDetailRecyclerAdapter(data);
    }
    public static ClientDetailRecyclerAdapter create(ArrayList<MultipleItemEntity> data, IClientDetailHandler iClientDetailHandler) {
        ClientDetailRecyclerAdapter clientDetailRecyclerAdapter = new ClientDetailRecyclerAdapter(data);
        clientDetailRecyclerAdapter.clientDetailHandler = iClientDetailHandler;
        return  clientDetailRecyclerAdapter;
    }
    public void refreshData(List<MultipleItemEntity> data) {
        getData().clear();
        getData().addAll(data);
        notifyDataSetChanged();
    }
    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {

        int itemType = item.getItemType();
        switch (itemType) {
            case ItemTypeClientDetail.FOLLOW:
                ClientDetailBean.DataBean.FollowsBean followsBean = item.getField(MultipleFields.OBJECT);
                int tag = item.getField(MultipleFields.TAG);
                Context context = helper.getView(R.id.tv_line_item).getContext();
                if(tag == ConstantClientDetail.FOLLOW_UP) {
                    helper.setImageResource(R.id.iv_dot, R.mipmap.follow_point_icon);
                    helper.setBackgroundColor(R.id.tv_line_item, context.getResources().getColor(R.color.orange));
                }
                else {
                    helper.setImageResource(R.id.iv_dot, R.mipmap.follow_history_icon);
                    helper.setBackgroundColor(R.id.tv_line_item, context.getResources().getColor(R.color.gray_d));
                }
                helper.setText(R.id.tv_identity, followsBean.getRoleName());
                helper.setText(R.id.tv_client_name, followsBean.getEmployeeName());
                RecyclerView recyclerView = helper.getView(R.id.recycler_details);
                initRecyclerView(recyclerView, followsBean);
                break;
            case ItemTypeClientDetail.SPACE:
                break;
            case ItemTypeClientDetail.TEXT_NORMAL:
                final ClientDateItemBean itemBean = item.getField(MultipleFields.OBJECT);
                helper.setText(R.id.tv_title, itemBean.getTitle());
                helper.setText(R.id.tv_content, itemBean.getContent());
                if(itemBean.getTitle().equals(ConstantClientDetail.PHONE_NUM)) {
                    RelativeLayout rl = helper.getView(R.id.rl_client_detail_item);
                    rl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(clientDetailHandler != null) {
                                clientDetailHandler.showCallPhoneDialog(itemBean.getContent());
                            }
                        }
                    });
                }

                break;
            case ItemTypeClientDetail.TEXT_CONTENT:
                String content = item.getField(MultipleFields.TEXT);
                helper.setText(R.id.tv_require, content);
                break;
            case ItemTypeClientDetail.TEXT_SHOW:
                String show = item.getField(MultipleFields.TEXT);
                AppCompatTextView tvShow = helper.getView(R.id.tv_msg);
                tvShow.setText(show);
                tvShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(clientDetailHandler != null) {
                            clientDetailHandler.show();
                        }
                    }
                });
                break;
            case ItemTypeClientDetail.TEXT_HIDEN:
                String hide = item.getField(MultipleFields.TEXT);
                AppCompatTextView tvHide = helper.getView(R.id.tv_msg);
                tvHide.setText(hide);
                tvHide.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(clientDetailHandler != null) {
                            clientDetailHandler.hiden();
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void init() {
        addItemType(ItemTypeClientDetail.FOLLOW, R.layout.client_detail_item_follow);
        addItemType(ItemTypeClientDetail.TEXT_NORMAL, R.layout.client_detail_data_item_normal);
        addItemType(ItemTypeClientDetail.TEXT_CONTENT, R.layout.client_detail_data_item_require);
        addItemType(ItemTypeClientDetail.SPACE, R.layout.client_detail_data_item_space);
        addItemType(ItemTypeClientDetail.TEXT_SHOW, R.layout.client_detail_data_item_text);
        addItemType(ItemTypeClientDetail.TEXT_HIDEN, R.layout.client_detail_data_item_text);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }

    private void initRecyclerView(RecyclerView recyclerView, ClientDetailBean.DataBean.FollowsBean followsBean) {
        final Context context = recyclerView.getContext();
        final GridLayoutManager manager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(manager);
        if (context != null) {
            recyclerView.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(context, R.color.app_background), 5));
        }
        ClientDetailFollowRecyclerAdapter adapter = ClientDetailFollowRecyclerAdapter.create(getFollowDetailBeans(followsBean));
        recyclerView.setAdapter(adapter);
    }

    private List<MultipleItemEntity> getFollowDetailBeans(ClientDetailBean.DataBean.FollowsBean followsBean) {
        List<ClientDetailBean.DataBean.FollowsBean.FollowDetailsBean> followDetailsBeans = followsBean.getFollowDetails();
        List<MultipleItemEntity> entities = new ArrayList<>();
        if(followDetailsBeans != null && followDetailsBeans.size() > 0) {
            for (ClientDetailBean.DataBean.FollowsBean.FollowDetailsBean followDetailsBean : followDetailsBeans) {
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setItemType(ItemTypeClientDetail.FOLLOW)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField(MultipleFields.OBJECT, followDetailsBean)
                        .build();
                entities.add(entity);

            }
        }
        return entities;
    }
}
