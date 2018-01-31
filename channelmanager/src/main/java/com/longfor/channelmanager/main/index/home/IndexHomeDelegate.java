package com.longfor.channelmanager.main.index.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.ec.h5.WebviewDelegate;
import com.longfor.channelmanager.common.ec.project.IProjectChange;
import com.longfor.channelmanager.common.ec.project.ProjectsDataBean;
import com.longfor.channelmanager.common.ec.project.popupwindow.ProjectsPopWindow;
import com.longfor.channelmanager.common.ec.unreadmessage.IUnReadMessageCount;
import com.longfor.channelmanager.common.ec.unreadmessage.UnReadMessageCountHandler;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.channelmanager.platform.ChannelPlatformDelegate;
import com.longfor.core.delegates.bottomreplace.BottomItemDelegate;
import com.longfor.ui.recycler.BaseDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: tongzhenhua
 * @date: 2017/12/28
 * @function:
 */
public class IndexHomeDelegate extends BottomItemDelegate implements IUnReadMessageCount, IHomePage, IProjectChange {
    @BindView(R2.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R2.id.tv_project_list)
    AppCompatTextView tvProjectList;
    @BindView(R2.id.img_message)
    AppCompatImageView imgMessage;
    @BindView(R2.id.img_message_dot)
    AppCompatImageView imgMessageDot;
    @BindView(R2.id.rl_group_unread_meg)
    RelativeLayout rlGroupUnreadMeg;
    @BindView(R2.id.rv_home)
    RecyclerView rvHome;

    private HomePageHandler pageHandler;
    private UnReadMessageCountHandler messageCountHandler;
    private ProjectsPopWindow mProjectWindow;

    @Override
    public Object setLayout() {
        return R.layout.delegate_index_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRecyclerView();
        pageHandler = HomePageHandler.create(getContext(), rvHome, new HomeDataConverter(), this);
        messageCountHandler = UnReadMessageCountHandler.create(getContext(), this);

        pageHandler.requestHomePageData(DatabaseManager.getProjectId());
        messageCountHandler.requestUnReadMessageCount();
        mProjectWindow = new ProjectsPopWindow(getContext(), this);
        tvProjectList.setText(DatabaseManager.getUserProfile().getProjectName());
    }


    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        final Context context = getContext();
        rvHome.setLayoutManager(manager);
        if (context != null) {
            rvHome.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(context, R.color.layout_bg_gray_f4), 5));
        }
    }

    @OnClick({R2.id.tv_project_list, R2.id.rl_group_unread_meg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_project_list:
                mProjectWindow.showPopWindow(tvProjectList);
                break;
            case R.id.rl_group_unread_meg:
                messageCountHandler.requestUnReadMessageCount();
                break;
        }
    }

    @Override
    public void callBack(int count) {
        imgMessageDot.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void OnItemClickListener(boolean isPlatform, HomePageBean.DataBean.FeatureInfo featureInfo) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE_LEFT_TEXT, getResources().getString(R.string.mine_home));
        if (isPlatform) {
            ChannelPlatformDelegate delegate = new ChannelPlatformDelegate();
            delegate.setArguments(bundle);
            getParentDelegate().start(delegate);

        } else {
            bundle.putString(Constant.WEB_URL, featureInfo.getFeatureUrl());
            bundle.putString(Constant.WEB_TITLE, featureInfo.getFeatureUrl());
            WebviewDelegate delegate = new WebviewDelegate();
            delegate.setArguments(bundle);
            getParentDelegate().start(delegate);
        }
    }

    @Override
    public void OnItemClickListener(int position, HomePageBean.DataBean.BannersBean bannersBean) {
        if (TextUtils.isEmpty(bannersBean.getBannerUrl())) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.TITLE_LEFT_TEXT, getResources().getString(R.string.mine_home));
            bundle.putString(Constant.WEB_URL, bannersBean.getBannerUrl());
            bundle.putString(Constant.WEB_TITLE, bannersBean.getBannerTitle());
            WebviewDelegate delegate = new WebviewDelegate();
            delegate.setArguments(bundle);
            getParentDelegate().start(delegate);
        }
    }

    @Override
    public void changeSucess(ProjectsDataBean.DataBean.ProjectsBean projectsBean) {
        DatabaseManager.updateProject(projectsBean.getProjectId(), projectsBean.getProjectName());
        tvProjectList.setText(DatabaseManager.getUserProfile().getProjectName());
    }
}
