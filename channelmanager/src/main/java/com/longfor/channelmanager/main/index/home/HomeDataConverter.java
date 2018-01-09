package com.longfor.channelmanager.main.index.home;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.main.index.home.ItemTypeHome;
import com.longfor.channelmanager.main.index.home.IHomePage;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/4
 * @function:
 */
public class HomeDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        IHomePage.HomePageBean homePageBean = JSON.parseObject(getJsonData(), IHomePage.HomePageBean.class);
        List<IHomePage.HomePageBean.DataBean.BannersBean> bannersBeans = homePageBean.getData().getBanners();
        if(bannersBeans !=null) {
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemTypeHome.BANNER)
                    .setField(MultipleFields.SPAN_SIZE, 1)
                    .setField(MultipleFields.ID, 1)
                    .setField(MultipleFields.BANNERS, bannersBeans)
                    .build();
            entities.add(entity);
        }
        List<IHomePage.HomePageBean.DataBean.FeatureInfo> featureInfos = homePageBean.getData().getFeatures();
        if(featureInfos != null) {
            for(IHomePage.HomePageBean.DataBean.FeatureInfo featureInfo : featureInfos) {
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemTypeHome.TEXT_IMAGE)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField(MultipleFields.OBJECT, featureInfo)
                        .build();
                entities.add(entity);
            }
        }
        IHomePage.HomePageBean.DataBean.FeatureInfo feature = new IHomePage.HomePageBean.DataBean.FeatureInfo();
        feature.setFeatureType(ConstantHome.FEATURE_TYPE_PLATFORM);
        feature.setFeatureName("渠道平台");
        final MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemTypeHome.TEXT_IMAGE)
                .setField(MultipleFields.SPAN_SIZE, 1)
                .setField(MultipleFields.OBJECT, feature)
                .build();
        entities.add(entity);
        return entities;
    }
}
