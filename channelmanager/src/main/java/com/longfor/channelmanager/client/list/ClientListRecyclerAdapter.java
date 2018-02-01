package com.longfor.channelmanager.client.list;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.view.taglayout.FlowLayout;
import com.longfor.channelmanager.common.view.taglayout.TagAdapter;
import com.longfor.channelmanager.common.view.taglayout.TagFlowLayout;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;
import com.longfor.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/10
 * @function: 客户列表item适配器
 */
public class ClientListRecyclerAdapter extends BaseRecyclerAdapter {
    private IClientList clientList;

    public ClientListRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
    }

    public static ClientListRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new ClientListRecyclerAdapter(data);
    }

    public static ClientListRecyclerAdapter create(DataConverter converter, IClientList clientList) {
        ClientListRecyclerAdapter listRecyclerAdapter = new ClientListRecyclerAdapter(converter.convert());
        listRecyclerAdapter.clientList = clientList;
        return listRecyclerAdapter;
    }
    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        final ClientListDataBean.DataBean.CustomersBean customersBean = item.getField(MultipleFields.OBJECT);
        switch (item.getItemType()) {
            case ItemTypeClient.TEXTS:
                helper.setText(R.id.tv_intent, TextUtils.isEmpty(customersBean.getIntent()) ? "" : customersBean.getIntent());
                helper.setText(R.id.tv_customer_name, customersBean.getCustomerName());
                TagFlowLayout tagFlowLayout = helper.getView(R.id.flow_layout);
                List<String> tags = customersBean.getTags();
                if(tags != null && tags.size() > 0) {
                    tagFlowLayout.setVisibility(View.VISIBLE);
                    TagAdapter<String> tagAdapter = new TagAdapter<String>(tags) {

                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            AppCompatTextView tvTag = (AppCompatTextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client_list_flow_layout, null);
                            tvTag.setText(s);
                            return tvTag;
                        }
                    };
                    tagFlowLayout.setAdapter(tagAdapter);
                }
                else {
                    tagFlowLayout.setVisibility(View.GONE);
                }
                //实习生
                String nameTrainee = customersBean.getTraineeName();
                AppCompatTextView tvJobTitle = helper.getView(R.id.tv_job_title);
                AppCompatTextView tvJobName = helper.getView(R.id.tv_job_name);
                if (TextUtils.isEmpty(nameTrainee)) {
                    tvJobTitle.setVisibility(View.GONE);
                    tvJobName.setVisibility(View.GONE);
                } else {
                    tvJobTitle.setVisibility(View.VISIBLE);
                    tvJobName.setVisibility(View.VISIBLE);
                    tvJobName.setText(nameTrainee);
                }

                //置业顾问
                String nameConsultant = customersBean.getConsultantName();
                AppCompatTextView tvCounselorTitle = helper.getView(R.id.tv_counselor_title);
                AppCompatTextView tvCounselorName = helper.getView(R.id.tv_counselor_name);
                if (TextUtils.isEmpty(nameConsultant)) {
                    tvCounselorTitle.setVisibility(View.GONE);
                    tvCounselorName.setVisibility(View.GONE);
                } else {
                    tvCounselorTitle.setVisibility(View.VISIBLE);
                    tvCounselorName.setVisibility(View.VISIBLE);
                    tvCounselorName.setText(nameConsultant);
                }

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(clientList != null){
                            clientList.onClientListClick(customersBean.getCustomerId()+"");
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void init() {
        addItemType(ItemTypeClient.TEXTS, R.layout.client_list_item);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}
