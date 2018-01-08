package com.longfor.channelmanager.main.adapter;


import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.RadioGroup;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.longfor.channelmanager.R;
import com.longfor.channelmanager.main.bean.HomePageBean;

/**
 * @author: tongzhenhua
 * @date: 2018/1/5
 * @function:
 */
public class BannerImageHolder implements Holder<HomePageBean.DataBean.BannersBean> {
    private AppCompatImageView mImageView = null;
    private static final RequestOptions BANNER_OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.mipmap.default_image)
            .error(R.mipmap.default_image)
            .dontAnimate()
            .centerCrop();

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);

        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, HomePageBean.DataBean.BannersBean data) {
        Glide.with(context).load(data.getBannerImage()).apply(BANNER_OPTIONS).into(mImageView);
    }
}
