package com.longfor.channelmanager.client.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.client.list.ConstantClientList;
import com.longfor.channelmanager.common.view.taglayout.TagFlowLayout;
import com.longfor.core.delegates.LongForDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: tongzhenhua
 * @date: 2018/1/11
 * @function: 查询客户
 */
public class ClientSearchDelegate extends LongForDelegate {
    @BindView(R2.id.tv_spinner)
    AppCompatTextView tvSpinner;
    @BindView(R2.id.et_search)
    AppCompatEditText etSearch;
    @BindView(R2.id.tv_cancel)
    AppCompatTextView tvCancel;
    @BindView(R2.id.img_delete)
    AppCompatImageView imgDelete;
    @BindView(R2.id.tagFlowLayout)
    TagFlowLayout tagFlowLayout;

    private String mRoleType = ConstantClientList.ROLE_TYPE_DEFAULT;
    private String mSearchContent = ConstantClientList.SEARCH_CONTENT_DEFAULT;

    @Override
    public Object setLayout() {
        return R.layout.delegate_client_search;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @OnClick({R2.id.tv_spinner, R2.id.tv_cancel, R2.id.img_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_spinner:
                break;
            case R.id.tv_cancel:
                break;
            case R.id.img_delete:
                break;
        }
    }
}
