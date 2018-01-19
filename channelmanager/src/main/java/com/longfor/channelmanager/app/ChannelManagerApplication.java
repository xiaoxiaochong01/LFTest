package com.longfor.channelmanager.app;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.utils.PickerImageLoader;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.core.app.LongFor;
import com.longfor.core.net.interceptors.DebugInterceptor;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

/**
 * Created by lsb18 on 2017/9/19.
 */

public class ChannelManagerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
        initImagePicker();
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        LongFor.init(this)
                .withIcon(new FontAwesomeModule())
                .withLoaderDelayed(1000)//修改loading框在网络回调结果中1s后消失
                //https://mop.longfor.com:27221/v1/app/
                .withApiHost("http://192.168.48.9:82/v1/app/")
                .withWeChatAppId("你的微信AppKey")
                .withWeChatAppSecret("你的微信AppSecret")
                .withJavascriptInterface("latte")
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .configure();

        DatabaseManager.getInstance().init(this);

    }

    /**
     * 初始化相册ImagePicker
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PickerImageLoader());
        imagePicker.setShowCamera(false);
        imagePicker.setCrop(false);
        imagePicker.setSaveRectangle(true);
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        imagePicker.setFocusWidth(800);
        imagePicker.setFocusHeight(800);
        imagePicker.setOutPutX(1000);
        imagePicker.setOutPutY(1000);
        imagePicker.setMultiMode(false);
    }

}
