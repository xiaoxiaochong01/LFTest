package com.longfor.channelmanager.main.adapter;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * @author: tongzhenhua
 * @date: 2018/1/5
 * @function:
 */
public class BannerHolderCreator implements CBViewHolderCreator<BannerImageHolder> {
    @Override
    public BannerImageHolder createHolder() {
        return new BannerImageHolder();
    }
}
