package com.longfor.channelmanager.main.delegate.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.constants.Constant;
import com.longfor.channelmanager.common.view.commonheadview.CommonHeadView;
import com.longfor.core.delegates.LongForDelegate;

import butterknife.BindView;

/**
 * @author: tongzhenhua
 * @date: 2018/1/2
 * @function:
 */
public class RecommendDelegate extends LongForDelegate {
    @BindView(R2.id.recommend_head)
    CommonHeadView mHeadView;

    @Override
    public Object setLayout() {
        return R.layout.delegate_recommend;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        Bundle bundle = getArguments();
        if(bundle != null) {
            String leftText = bundle.getString(Constant.TITLE_LEFT_TEXT);
            if (!TextUtils.isEmpty(leftText)) {
                mHeadView.setLeftMsgVisiable(true);
                mHeadView.setLeftMsg(leftText);
            }
        }
        mHeadView.setLeftBackImageVisible(true);
        mHeadView.setTitle(getResources().getString(R.string.mine_recommend));
        mHeadView.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportDelegate().pop();
            }
        });
    }
}
