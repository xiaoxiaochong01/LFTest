package com.longfor.channelmanager.app;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.longfor.channelmanager.R;
import com.longfor.core.app.LongFor;
import com.longfor.core.net.interceptors.DebugInterceptor;

/**
 * Created by lsb18 on 2017/9/19.
 */

public class ChannelManagerApplication extends Application {

    public static ChannelManagerApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initConfig();
    }

    public static ChannelManagerApplication getInstance() {
        return mInstance;
    }
    private void initConfig() {
        LongFor.init(this)
                .withIcon(new FontAwesomeModule())
                .withLoaderDelayed(5000)
                //http://192.168.48.9:82/v1/app
                .withApiHost("https://mop.longfor.com:27221/v1/app/")
                .withWeChatAppId("你的微信AppKey")
                .withWeChatAppSecret("你的微信AppSecret")
                .withJavascriptInterface("latte")
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .configure();

    }

}
