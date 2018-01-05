package com.longfor.channelmanager.main.delegate.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.core.delegates.bottomreplace.BottomItemDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: tongzhenhua
 * @date: 2017/12/28
 * @function:
 */
public class IndexHomeDelegate extends BottomItemDelegate {

    @BindView(R2.id.img_message)
    ImageView imgMessage;
    @BindView(R2.id.img_message_dot)
    ImageView imgMessageDot;
    @BindView(R2.id.rl_group_unread_meg)
    RelativeLayout rlGroupUnreadMeg;
    @BindView(R2.id.rv_home)
    RecyclerView rvHome;
    @BindView(R2.id.srl_home)
    SwipeRefreshLayout srlHome;
    @BindView(R2.id.elv_project_home)
    ExpandableListView elvProjectHome;
    @BindView(R2.id.btn_out_project_item)
    Button btnOutProjectItem;
    @BindView(R2.id.ll_group_project)
    LinearLayout llGroupProject;


    @Override
    public Object setLayout() {
        return R.layout.delegate_index_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @OnClick({R2.id.tv_project_list, R2.id.rl_group_unread_meg, R2.id.btn_out_project_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_project_list:
                break;
            case R.id.rl_group_unread_meg:
                break;
            case R.id.btn_out_project_item:
                break;
        }
    }
}
