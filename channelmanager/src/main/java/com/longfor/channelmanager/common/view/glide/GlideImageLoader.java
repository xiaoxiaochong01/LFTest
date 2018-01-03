package com.longfor.channelmanager.common.view.glide;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.longfor.channelmanager.R;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * @author: tongzhenhua
 * @date: 2018/1/3
 * @function:
 */
public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .load(Uri.fromFile(new File(path)))
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .dontAnimate()
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
