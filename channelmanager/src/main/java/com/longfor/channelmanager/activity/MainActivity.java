package com.longfor.channelmanager.activity;

import android.os.Bundle;

import com.longfor.channelmanager.lancher.delegate.SplashDelegate;
import com.longfor.core.activities.ProxyActivity;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.delegates.bottomreplace.BackHandledInterface;
import com.longfor.core.delegates.bottomreplace.BottomItemDelegate;

public class MainActivity extends ProxyActivity implements BackHandledInterface{
    private BottomItemDelegate mPlaceholderFragment;
    @Override
    public LongForDelegate setRootDelegate() {
        return new SplashDelegate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
    }

    @Override
    public void setSelectedFragment(BottomItemDelegate selectedFragment) {
        mPlaceholderFragment = selectedFragment;
    }

    @Override
    public void onBackPressed() {
        if (mPlaceholderFragment != null&& mPlaceholderFragment.isVisible()) {
            //处理
            mPlaceholderFragment.onBackPressed();
        } else {
            //处理
            super.onBackPressed();
        }
    }
}
