package com.longfor.channelmanager.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.longfor.channelmanager.R;

/**
 * @author: tongzhenhua
 * @date: 2018/1/3
 * @function:
 */
public class ImageLoader {

    public static void display(Context context, ImageView imageView, String path) {
        Glide.with(context)
                .load(path)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        )
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, String path, int loadResId, int errorResId) {
        Glide.with(context)
                .load(path)
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .placeholder(loadResId)
                        .error(errorResId))
                .into(imageView);
    }

    public static void displayCircle(Context context, ImageView imageView, String path, int loadResId, int errorResId) {
        Glide.with(context)
                .load(path)
                .apply(new RequestOptions()
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .placeholder(loadResId)
                        .error(errorResId))
                .into(imageView);
    }

}
