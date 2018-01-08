package com.longfor.channelmanager.common.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.longfor.channelmanager.R;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * @author: tongzhenhua
 * @date: 2018/1/3
 * @function:
 */
public class PickerImageLoader implements ImageLoader {

    private static final RequestOptions GLIDE_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .placeholder(R.mipmap.default_image)
                    .error(R.mipmap.default_image);
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .load(Uri.fromFile(new File(path)))
                .apply(GLIDE_OPTIONS)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
