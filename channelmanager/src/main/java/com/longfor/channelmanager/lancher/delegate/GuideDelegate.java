package com.longfor.channelmanager.lancher.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.longfor.channelmanager.R;
import com.longfor.channelmanager.login.delegate.LoginVideoDelegate;
import com.longfor.core.app.LongFor;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.utils.storage.LongForPreference;
import com.longfor.ui.launcher.LauncherHolderCreator;
import com.longfor.ui.launcher.ScrollLauncherTag;

import java.util.ArrayList;

/**
 * @author: tongzhenhua
 * @date: 2017/12/26
 * @function:
 */
public class GuideDelegate extends LongForDelegate implements OnItemClickListener{
    private ConvenientBanner<Integer> mBanner = null;
    private final ArrayList<Integer> INTEGERS = new ArrayList<>();

    @Override
    public Object setLayout() {
        mBanner = new ConvenientBanner<Integer>(getContext());
        return mBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initBanner();
    }

    /**
     * 初始化启动引导页轮滑控件
     */
    private void initBanner() {
        INTEGERS.add(R.mipmap.guide_one_bg);
        INTEGERS.add(R.mipmap.guide_two_bg);
        INTEGERS.add(R.mipmap.guide_three_bg);
        mBanner.setPages(new LauncherHolderCreator(), INTEGERS)
//                .setPageIndicator(new int[]{})
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public void onItemClick(int position) {
        if(position == INTEGERS.size() - 1) {
            LongForPreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            getSupportDelegate().startWithPop(new LoginVideoDelegate());
        }
    }
}
