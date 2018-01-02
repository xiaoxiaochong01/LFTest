package com.longfor.channelmanager.main.delegate.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.constants.Constant;
import com.longfor.channelmanager.common.dialog.DialogWithYesOrNo;
import com.longfor.channelmanager.main.delegate.child.AboutUsDelegate;
import com.longfor.channelmanager.main.delegate.child.RecommendDelegate;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.delegates.bottomreplace.BottomItemDelegate;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author: tongzhenhua
 * @date: 2017/12/28
 * @function:
 */
public class IndexMineDelegate extends BottomItemDelegate {
    @BindView(R2.id.img_title_background)
    AppCompatImageView imgTopBg;
    @BindView(R2.id.img_uer_head_portrait)
    AppCompatImageView imgHead;
    @BindView(R2.id.tv_user_name)
    AppCompatTextView tvUserName;
    @BindView(R2.id.rl_about_us)
    RelativeLayout rlAboutUs;
    @BindView(R2.id.rl_feed_back)
    RelativeLayout rlFeedBack;
    @BindView(R2.id.rl_recommend_code)
    RelativeLayout rlRecommend;
    @BindView(R2.id.rl_setting)
    RelativeLayout rlSetting;

    private DialogWithYesOrNo mDialogHintPermission;

    @Override
    public Object setLayout() {
        return R.layout.delegate_index_mine;
    }

    /**
     * 封面点击事件
     */
    @OnClick(R2.id.img_title_background)
    void onTopBackgroundClick() {

    }

    /**
     *
     */
    @OnClick(R2.id.img_uer_head_portrait)
    void onUserHeadClick() {

    }
    @OnClick(R2.id.rl_about_us)
    void onAboutUsClick() {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE_LEFT_TEXT, getResources().getString(R.string.mine_title));
        LongForDelegate supportFragment = new AboutUsDelegate();
        supportFragment.setArguments(bundle);
        getParentDelegate().start(supportFragment, SINGLETASK);
    }
    @OnClick(R2.id.rl_recommend_code)
    void onRecommendClick() {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE_LEFT_TEXT, getResources().getString(R.string.mine_title));
        RecommendDelegate supportFragment = new RecommendDelegate();
        supportFragment.onNewBundle(bundle);
        supportFragment.putNewBundle(bundle);
        supportFragment.setArguments(bundle);
//        supportFragment.put
        getSupportDelegate().start(supportFragment, SINGLETASK);

    }
    @OnClick(R2.id.rl_feed_back)
    void onFeedBackClick() {

    }
    @OnClick(R2.id.rl_setting)
    void onSettingClick() {

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    /**
     * 请求我的接口
     */
    private void requstMinePort() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
