package com.longfor.channelmanager.mine.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.constants.Constant;
import com.longfor.channelmanager.common.view.commonheadview.CommonHeadView;
import com.longfor.channelmanager.mine.constants.ConstantMine;
import com.longfor.core.delegates.LongForDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: tongzhenhua
 * @date: 2018/1/2
 * @function:
 */
public class RecommendDelegate extends LongForDelegate {
    @BindView(R2.id.recommend_head)
    CommonHeadView mHeadView;
    @BindView(R2.id.tv_recommend_code)
    TextView tvRecommendCode;



    @Override
    public Object setLayout() {
        return R.layout.delegate_recommend;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            String leftText = bundle.getString(Constant.TITLE_LEFT_TEXT);
            if (!TextUtils.isEmpty(leftText)) {
                mHeadView.setLeftMsgVisiable(true);
                mHeadView.setLeftMsg(leftText);
            }
            String recommendCode = bundle.getString(ConstantMine.RECOMMEND_CODE);
            if(!TextUtils.isEmpty(recommendCode)) {
                tvRecommendCode.setText(recommendCode);
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
