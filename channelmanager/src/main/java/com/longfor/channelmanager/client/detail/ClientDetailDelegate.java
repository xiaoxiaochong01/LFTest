package com.longfor.channelmanager.client.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.view.CommonHeadView;
import com.longfor.channelmanager.common.view.taglayout.FlowLayout;
import com.longfor.channelmanager.common.view.taglayout.TagAdapter;
import com.longfor.channelmanager.common.view.taglayout.TagFlowLayout;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.ui.recycler.BaseDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: tongzhenhua
 * @date: 2018/1/16
 * @function:
 */
public class ClientDetailDelegate extends LongForDelegate implements IClientDetail{
    @BindView(R2.id.channel_platform_head)
    CommonHeadView channelPlatformHead;
    @BindView(R2.id.tv_grade_info)
    AppCompatTextView tvGradeInfo;
    @BindView(R2.id.tv_customer_name)
    AppCompatTextView tvCustomerName;
    @BindView(R2.id.img_associate)
    AppCompatImageView imgAssociate;
    @BindView(R2.id.flow_layout)
    TagFlowLayout flowLayout;
    @BindView(R2.id.tv_follow_name)
    AppCompatTextView tvFollowName;
    @BindView(R2.id.tv_bungalows)
    AppCompatTextView tvBungalows;
    @BindView(R2.id.tv_square_meter)
    AppCompatTextView tvSquareMeter;
    @BindView(R2.id.item_tv_subscirbe)
    AppCompatTextView itemTvSubscirbe;
    @BindView(R2.id.tv_follow)
    AppCompatTextView tvFollow;
    @BindView(R2.id.view_follow)
    AppCompatTextView viewFollow;
    @BindView(R2.id.linear_follow)
    LinearLayoutCompat linearFollow;
    @BindView(R2.id.tv_data)
    AppCompatTextView tvData;
    @BindView(R2.id.view_data)
    AppCompatTextView viewData;
    @BindView(R2.id.linear_data)
    LinearLayoutCompat linearData;
    @BindView(R2.id.linear_client_list_info)
    LinearLayoutCompat linearClientListInfo;
    @BindView(R2.id.recycler_client_follow)
    RecyclerView recyclerClientFollow;
    @BindView(R2.id.recycler_client_data)
    RecyclerView recyclerClientData;

    private TagAdapter<String> mTagAdapter;

    private String mTitleLeftText;
    private String mCustomerId;
    private ClientDetailHandlerHandler mHandler;

    public static ClientDetailDelegate getInstance(String titleLeftText, String mCustomerId) {
        ClientDetailDelegate detailDelegate = new ClientDetailDelegate();
        detailDelegate.mCustomerId = mCustomerId;
        detailDelegate.mTitleLeftText = titleLeftText;
        return detailDelegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_client_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        if (!TextUtils.isEmpty(mTitleLeftText)) {
            channelPlatformHead.setLeftMsgVisiable(true);
            channelPlatformHead.setLeftMsg(mTitleLeftText);
        }
        channelPlatformHead.setTitle(R.string.client_detail_title);
        channelPlatformHead.setLeftBackImageVisible(true);
        channelPlatformHead.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportDelegate().pop();
            }
        });

        initRecyclerView();
        mHandler = ClientDetailHandlerHandler.create(recyclerClientFollow,recyclerClientData,new ClientDetailDataConverter(this));
        mHandler.requestClientDetailData(mCustomerId);
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        final Context context = getContext();
        recyclerClientData.setLayoutManager(manager);
        if (context != null) {
            recyclerClientData.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(context, R.color.gray_f8), 3));
        }
        final GridLayoutManager manager1 = new GridLayoutManager(getContext(), 1);
        recyclerClientFollow.setLayoutManager(manager1);
        if (context != null) {
            recyclerClientFollow.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(context, R.color.gray_f8), 3));
        }
    }

    @OnClick({R2.id.linear_data, R2.id.linear_client_list_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_data:
                resetTabView(true);
                break;
            case R.id.linear_client_list_info:
                resetTabView(false);
                break;
        }
    }

    /**
     * @param isData 是否是客户资料页
     */
    private void resetTabView(boolean isData) {
        tvFollow.setTextColor(getResources().getColor(isData?R.color.gray_9 : R.color.gray_3));
        viewFollow.setBackgroundColor(getResources().getColor(isData ? R.color.white : R.color.orange));
        tvData.setTextColor(getResources().getColor(isData ? R.color.gray_3 : R.color.gray_9));
        viewData.setBackgroundColor(getResources().getColor(isData ? R.color.orange : R.color.white));

        recyclerClientData.setVisibility(isData ? View.VISIBLE : View.GONE);
        recyclerClientFollow.setVisibility(!isData ? View.VISIBLE : View.GONE);
    }

    @Override
    public void fillLayout(ClientDetailBean.DataBean dataBean) {
        if(dataBean != null) {
            ClientDetailBean.DataBean.CustomerBean customerBean =dataBean.getCustomer();
            if(customerBean != null) {
                imgAssociate.setVisibility(customerBean.getHasRelated() == ConstantClientDetail.HAS_RELATED ? View.VISIBLE : View.GONE);
                tvGradeInfo.setText(customerBean.getIntent());
                tvFollowName.setText(customerBean.getFollowName());
                tvCustomerName.setText(customerBean.getCustomerName());

                List<String> tags = customerBean.getTags();
                if(tags != null ) {
                    flowLayout.setVisibility(View.VISIBLE);
                    mTagAdapter = new TagAdapter<String>(tags) {
                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            AppCompatTextView tvTag = (AppCompatTextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client_list_flow_layout, null);
                            tvTag.setText(s);
                            return tvTag;
                        }
                    };
                    flowLayout.setAdapter(mTagAdapter);

                }
                else {
                    flowLayout.setVisibility(View.GONE);
                }
            }
        }
    }
}
