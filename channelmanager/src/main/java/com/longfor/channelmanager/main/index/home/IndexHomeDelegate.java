package com.longfor.channelmanager.main.index.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.ec.h5.WebviewDelegate;
import com.longfor.channelmanager.common.ec.unreadmessage.IUnReadMessageCount;
import com.longfor.channelmanager.common.ec.unreadmessage.UnReadMessageCountHandler;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.core.delegates.bottomreplace.BottomItemDelegate;
import com.longfor.core.utils.toast.ToastUtils;
import com.longfor.ui.recycler.BaseDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: tongzhenhua
 * @date: 2017/12/28
 * @function:
 */
public class IndexHomeDelegate extends BottomItemDelegate implements IUnReadMessageCount, IHomePage{

    @BindView(R2.id.img_message)
    ImageView imgMessage;
    @BindView(R2.id.img_message_dot)
    ImageView imgMessageDot;
    @BindView(R2.id.rl_group_unread_meg)
    RelativeLayout rlGroupUnreadMeg;
    @BindView(R2.id.rv_home)
    RecyclerView rvHome;
    @BindView(R2.id.elv_project_home)
    ExpandableListView elvProjectHome;
    @BindView(R2.id.btn_out_project_item)
    Button btnOutProjectItem;
    @BindView(R2.id.ll_group_project)
    LinearLayout llGroupProject;

    private HomePageHandler pageHandler;
    private UnReadMessageCountHandler messageCountHandler;
    private final List<HomePageBean.DataBean.FeatureInfo> featureInfos = new ArrayList<>();
    private final List<HomePageBean.DataBean.BannersBean> bannersBeans = new ArrayList<>();

    @Override
    public Object setLayout() {
        return R.layout.delegate_index_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRecyclerView();
        pageHandler = HomePageHandler.create(getContext(), rvHome, new HomeDataConverter(), this);
        messageCountHandler = UnReadMessageCountHandler.create(getContext(),this);

        pageHandler.requestHomePageData(DatabaseManager.getProjectId());
        messageCountHandler.requestUnReadMessageCount();
    }


    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(),1);
        final Context context =getContext();
        rvHome.setLayoutManager(manager);
        if(context!=null){
            rvHome.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(context, R.color.app_background),5));
        }
    }

    @OnClick({R2.id.tv_project_list, R2.id.rl_group_unread_meg, R2.id.btn_out_project_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_project_list:
                break;
            case R.id.rl_group_unread_meg:
                messageCountHandler.requestUnReadMessageCount();
                break;
            case R.id.btn_out_project_item:
                break;
        }
    }

    @Override
    public void callBack(int count) {
        imgMessageDot.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void OnItemClickListener(boolean isPlatform, HomePageBean.DataBean.FeatureInfo featureInfo) {
        if(isPlatform) {
            ToastUtils.showMessage(getContext(), "渠道平台");
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.TITLE_LEFT_TEXT, getResources().getString(R.string.mine_home));
            bundle.putString(Constant.WEB_URL,featureInfo.getFeatureUrl());
            bundle.putString(Constant.WEB_TITLE, featureInfo.getFeatureUrl());
            WebviewDelegate delegate = new WebviewDelegate();
            delegate.setArguments(bundle);
            getParentDelegate().start(delegate);
        }
    }

    @Override
    public void OnItemClickListener(int position, HomePageBean.DataBean.BannersBean bannersBean) {
        if(TextUtils.isEmpty(bannersBean.getBannerUrl())) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.TITLE_LEFT_TEXT, getResources().getString(R.string.mine_home));
            bundle.putString(Constant.WEB_URL, bannersBean.getBannerUrl());
            bundle.putString(Constant.WEB_TITLE, bannersBean.getBannerTitle());
            WebviewDelegate delegate = new WebviewDelegate();
            delegate.setArguments(bundle);
            getParentDelegate().start(delegate);
        }
    }
}
