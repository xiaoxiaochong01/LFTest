package com.longfor.channelmanager.recordlist.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.utils.ImageLoader;
import com.longfor.channelmanager.common.view.popupwindow.BottomPopupWindow;
import com.longfor.channelmanager.recordlist.bean.RecordListBean;
import com.longfor.channelmanager.recordlist.constant.RecordListConstant;
import com.longfor.channelmanager.recordlist.converter.RecordListConverter;
import com.longfor.channelmanager.recordlist.handler.RecordListHandler;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.ui.recycler.BaseDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: gaomei
 * @date: 2018/1/23
 * @function:
 */

public class RecordListSubDelegate extends LongForDelegate implements RecordListConverter.OnGetNetDataListener {
    public int mIsGroup;
    @BindView(R.id.iv_cover)
    AppCompatImageView mIvCover;
    @BindView(R.id.tv_cover)
    AppCompatTextView mTvCover;
    @BindView(R.id.rv_record)
    RecyclerView mRvRecord;
    @BindView(R.id.iv_filter)
    AppCompatImageView mIvFilter;
    private String mCategory;
    private LongForDelegate mParentDelegate;
    private String mUserRole = Constant.SHOW_GET_NUM;
    public RecordListHandler mRecordListHandler;
    public BottomPopupWindow mPopupWindow;
    private BottomPopupWindow.OnItemClickListener mPopupWindowItemClickListener = new BottomPopupWindow.OnItemClickListener() {
        @Override
        public void onItemClick(BottomPopupWindow.Data data) {
            mUserRole = data.getId();
            mRecordListHandler.requestRecordList(mIsGroup, mUserRole);
        }
    };

    public static RecordListSubDelegate getInstance(int isGroup, String category, LongForDelegate parentDelegate) {
        RecordListSubDelegate delegate = new RecordListSubDelegate();
        delegate.mIsGroup = isGroup;
        delegate.mCategory = category;
        delegate.mParentDelegate = parentDelegate;
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sub_record_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initBottomPopupWindow();
        initRecyclerView();
        mRecordListHandler = RecordListHandler.create(mRvRecord, mIsGroup, mCategory, mUserRole, this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRecordListHandler.requestRecordList(mIsGroup, mUserRole);
    }

    private void initBottomPopupWindow() {
        String[] filterRoleStrs = null;
        String[] filterRoleIds = null;
        switch (mCategory) {
            case RecordListConstant.FIRST_LEVEL_CLIENT:
                filterRoleStrs = new String[]{getString(R.string.trainee_role_show_get_num),
                        getString(R.string.trainee_role_expand_get_num),
                        getString(R.string.trainee_role_call_get_num)};
                filterRoleIds = new String[]{Constant.SHOW_GET_NUM, Constant.EXPAND_GET_NUM,
                        Constant.CALL_GET_NUM};
                break;
            case RecordListConstant.SECOND_LEVEL_CLIENT:
            case RecordListConstant.SUBSCRIBE_CLIENT:
            case RecordListConstant.CLIENT_CONVERSION_RATE:
                filterRoleStrs = new String[]{getString(R.string.trainee_role_show_get_num),
                        getString(R.string.trainee_role_expand_get_num),
                        getString(R.string.trainee_role_show_and_expand_call_num),
                        getString(R.string.trainee_role_call_get_num),
                        getString(R.string.trainee_role_call_call_num)};
                filterRoleIds = new String[]{Constant.SHOW_GET_NUM, Constant.EXPAND_GET_NUM,
                        Constant.SHOW_AND_EXPAND_CALL_NUM, Constant.CALL_GET_NUM, Constant.CALL_CALL_NUM};
                break;
        }
        List<BottomPopupWindow.Data> dataList = new ArrayList<>();
        for (int i = 0; i < filterRoleStrs.length; i++) {
            dataList.add(new BottomPopupWindow.Data(filterRoleIds[i], filterRoleStrs[i]));
        }
        dataList.get(0).setSelected(true);
        mPopupWindow = new BottomPopupWindow(getContext(), dataList, mPopupWindowItemClickListener);
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        final Context context = getContext();
        mRvRecord.setLayoutManager(manager);
        if (context != null) {
            mRvRecord.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(context, com.longfor.ec.R.color.app_background), 1));
        }
        mRvRecord.setNestedScrollingEnabled(false);
    }

    @OnClick(R.id.iv_filter)
    public void onViewClicked() {
        mPopupWindow.showPopupWindow(mIvFilter);
    }

    @Override
    public void onGetNetData(@Nullable RecordListBean.DataBean dataBean) {
        if (dataBean != null) {
            mIvCover.setVisibility(View.VISIBLE);
            mTvCover.setVisibility(View.VISIBLE);
            ImageLoader.display(getContext(), mIvCover, dataBean.getCover(), R.mipmap.ic_default_banner, R.mipmap.ic_default_banner);
            mTvCover.setText(dataBean.getName() + getString(R.string.rank_first_text));
        } else {
            mIvCover.setVisibility(View.GONE);
            mTvCover.setVisibility(View.GONE);
        }
    }

    public void updateParams() {
        mRecordListHandler.requestRecordList(mIsGroup, mUserRole);
    }
}
