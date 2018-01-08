package com.longfor.channelmanager.main.handler;

import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.longfor.channelmanager.main.bean.HomePageBean;

/**
 * @author: tongzhenhua
 * @date: 2018/1/5
 * @function:
 */
public interface IHomePage{
//    void callBack(HomePageBean homePageBean);
    void OnItemClickListener(boolean isPlatform, HomePageBean.DataBean.FeatureInfo featureInfo);
    void OnItemClickListener(int position, HomePageBean.DataBean.BannersBean bannersBean);
}
