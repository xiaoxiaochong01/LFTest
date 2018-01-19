package com.longfor.channelmanager.client.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.client.detail.ClientDetailDelegate;
import com.longfor.channelmanager.client.search.ClientSearchDelegate;
import com.longfor.channelmanager.client.search.ConstantClientSearch;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.ui.view.editwatcher.EditTextWatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: tongzhenhua
 * @date: 2018/1/9
 * @function:
 */
public class ClientListDelegate extends LongForDelegate implements OnRefreshSearchContentListener, IClientList{
    @BindView(R2.id.tl_client_list)
    TabLayout tlClientList;
    @BindView(R2.id.vp_client_list)
    ViewPager vpClientList;
    @BindView(R2.id.tv_back)
    AppCompatTextView tvBack;
    @BindView(R2.id.et_search)
    AppCompatEditText etSearch;
    @BindView(R2.id.iv_clear)
    AppCompatImageView imgClear;

    private final List<ClientListSubDelegate> delegates = new ArrayList<>();
    private final List<String> mTabTitles = new ArrayList<>();
    private final List<String> intentTypes = new ArrayList<>();
    public static String mRoleType = ConstantClientList.ROLE_TYPE_DEFAULT;
    public static String mSearchContent = ConstantClientList.SEARCH_CONTENT_DEFAULT;

    @Override
    public Object setLayout() {
        return R.layout.delegate_client_list;
    }

    @OnClick(R2.id.iv_clear)
    void onImgClearClick() {
        onRefresh(ConstantClientList.ROLE_TYPE_DEFAULT, ConstantClientList.SEARCH_CONTENT_DEFAULT);
    }

    @OnClick(R2.id.et_search)
    void onSearchClick() {
        getSupportDelegate().startForResult(new ClientSearchDelegate(), ConstantClientSearch.DELEGATE_RESULT_CODE_CLIENT_SEARCH);
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initHeader();
        initTabLayout();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void initHeader() {
        Bundle bundle = getArguments();
        if(bundle != null) {
            tvBack.setText(bundle.getString(Constant.TITLE_LEFT_TEXT));
        }
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().pop();
            }
        });
        etSearch.addTextChangedListener(etSearchListener);
        etSearch.setInputType(InputType.TYPE_NULL);
        etSearch.requestFocus();
    }

    private void initTabLayout() {
        delegates.clear();
        mTabTitles.clear();
        intentTypes.clear();
        mTabTitles.addAll(Arrays.asList(new String[]{getString(R.string.all),
                getString(R.string.first_floor), getString(R.string.second_floor),
                getString(R.string.subscribe)}));
        intentTypes.addAll(Arrays.asList(new String[]{ConstantClientList.CLIENT_ALL,
                ConstantClientList.CLIENT_FIRST,
                ConstantClientList.CLIENT_SECOND,
                ConstantClientList.CLIENT_SUBSCRIBE}
        ));
        for (String intentType : intentTypes) {
            ClientListSubDelegate delegate = ClientListSubDelegate.getInstance(intentType,this, this);
            delegates.add(delegate);
        }
        vpClientList.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return delegates.get(position);
            }

            @Override
            public int getCount() {
                return delegates.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitles.get(position);
            }
        });
        tlClientList.setupWithViewPager(vpClientList);
    }
    private EditTextWatcher etSearchListener = new EditTextWatcher() {
        @Override
        public void afterTextChanged(Editable editable) {
            String text = etSearch.getText().toString();
            imgClear.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        }
    };

    @Override
    public void onRefresh(String roleType, String searchContent) {
        etSearch.setText(searchContent);
        mRoleType = roleType;
        mSearchContent = searchContent;
        for(ClientListSubDelegate delegate : delegates) {
            delegate.update(roleType, searchContent);
        }
    }

    @Override
    public void onClientListClick(String mCustomerId) {
        ClientDetailDelegate detailDelegate = ClientDetailDelegate.getInstance(getString(R.string.client_list_title), mCustomerId);
        getSupportDelegate().start(detailDelegate);
    }

    @Override
    public void onDestroy() {
        mRoleType = ConstantClientList.ROLE_TYPE_DEFAULT;
        mSearchContent = ConstantClientList.SEARCH_CONTENT_DEFAULT;
        super.onDestroy();
    }
}
