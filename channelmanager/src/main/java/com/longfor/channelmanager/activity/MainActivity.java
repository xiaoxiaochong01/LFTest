package com.longfor.channelmanager.activity;

import android.os.Bundle;

import com.longfor.channelmanager.lancher.delegate.SplashDelegate;
import com.longfor.core.activities.ProxyActivity;
import com.longfor.core.delegates.LongForDelegate;

public class MainActivity extends ProxyActivity {

    @Override
    public LongForDelegate setRootDelegate() {
        return new SplashDelegate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
    }

}
