package com.longfor.channelmanager.client.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.client.list.ConstantClientList;
import com.longfor.channelmanager.common.view.taglayout.FlowLayout;
import com.longfor.channelmanager.common.view.taglayout.TagAdapter;
import com.longfor.channelmanager.common.view.taglayout.TagFlowLayout;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.utils.toast.ToastUtils;

import java.util.Arrays;
import java.util.List;

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

    private TagAdapter mTagAdapter;

    private String mRoleType = ConstantClientSearch.ROLE_TYPE_CONSULTANT;
    private String mSearchContent = ConstantClientList.SEARCH_CONTENT_DEFAULT;
    private ChooseRolePopwindow mPopwindow;
    private List<String> mPopwindowList = Arrays.asList(new String[] {ConstantClientSearch.ROLE_NAME_CLIENT, ConstantClientSearch.ROLE_NAME_TRAINEE, ConstantClientSearch.ROLE_NAME_CONSULTANT});

    @Override
    public Object setLayout() {
        return R.layout.delegate_client_search;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initPopwindow();
        initEdit();
        initHistoryLayout();
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopwindow() {
        mPopwindow = new ChooseRolePopwindow(getContext(), mPopwindowList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String roleName = mPopwindowList.get(position);
                switch (roleName) {
                    case ConstantClientSearch.ROLE_NAME_CLIENT:
                        mRoleType = ConstantClientSearch.ROLE_TYPE_CLIENT;
                        etSearch.setHint(R.string.input_search_client);
                        break;
                    case ConstantClientSearch.ROLE_NAME_TRAINEE:
                        mRoleType = ConstantClientSearch.ROLE_TYPE_TRAINEE;
                        etSearch.setHint(R.string.input_search_trainee);
                        break;
                    case ConstantClientSearch.ROLE_NAME_CONSULTANT:
                        mRoleType = ConstantClientSearch.ROLE_TYPE_CONSULTANT;
                        etSearch.setHint(R.string.input_search_counselor);
                        break;
                }
                tvSpinner.setText(roleName);
                mPopwindow.dismiss();
                initHistoryLayout();

            }
        });
    }

    @OnClick({R2.id.tv_spinner, R2.id.tv_cancel, R2.id.img_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_spinner:
                mPopwindow.showAsDropDown(tvSpinner);
                break;
            case R.id.tv_cancel:
                getSupportDelegate().pop();
                break;
            case R.id.img_delete:
                DatabaseManager.clearHistory(mRoleType);
                initHistoryLayout();
                break;
        }
    }

    private void initHistoryLayout(){
        final List<String> historys = DatabaseManager.getSearchHistorys(mRoleType);
        mTagAdapter = new TagAdapter<String>(historys) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                AppCompatTextView tvHistroy = (AppCompatTextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client_list_search_history,null);
                tvHistroy.setText(s);
                return tvHistroy;
            }
        };
        tagFlowLayout.setAdapter(mTagAdapter);
        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(int pos) {
                mSearchContent = historys.get(pos);
                etSearch.setText(mSearchContent);
                setResultSearchContent();
            }
        });
    }

    private void setResultSearchContent() {
        Bundle bundle = new Bundle();
        bundle.putString(ConstantClientList.ROLE_TYPE, mRoleType);
        bundle.putString(ConstantClientList.SEARCH_CONTENT, mSearchContent);
        setFragmentResult(RESULT_OK,bundle);

        getSupportDelegate().pop();
    }

    /**
     * 回车键改为搜索
     */
    private void initEdit() {
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        etSearch.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    String content = etSearch.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        ToastUtils.showMessage(getString(R.string.input_search_content));
                    } else {
                        mSearchContent = content;
                        DatabaseManager.updateHistory(mRoleType, mSearchContent);
                        setResultSearchContent();
                        return true;
                    }
                }
                return false;
            }
        });

    }

}
