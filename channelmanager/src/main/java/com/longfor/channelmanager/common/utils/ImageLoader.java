package com.longfor.channelmanager.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author: tongzhenhua
 * @date: 2018/1/3
 * @function:
 */
public class ImageLoader {

    public static void display(Context context, ImageView imageView, String path) {
        Glide.with(context)
                .load(path)
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, String path, int loadResId, int errorResId) {
        Glide.with(context)
                .load(path)
                .placeholder(loadResId)
                .error(errorResId)
                .into(imageView);
    }

}
