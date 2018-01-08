package com.longfor.channelmanager.main.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.longfor.channelmanager.R;
import com.longfor.channelmanager.main.bean.HomePageBean;
import com.longfor.channelmanager.main.bean.ItemTypeHome;
import com.longfor.channelmanager.main.handler.IHomePage;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;
import com.longfor.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/4
 * @function:
 */
public class HomeRecyclerAdapter extends BaseRecyclerAdapter {
    IHomePage IHOME_PAGE;
    public HomeRecyclerAdapter(List<MultipleItemEntity> data, IHomePage iHomePage) {
        super(data);
        IHOME_PAGE = iHomePage;
    }

    public static HomeRecyclerAdapter create(List<MultipleItemEntity> data, IHomePage iHomePage) {
        return new HomeRecyclerAdapter(data, iHomePage);
    }

    public static HomeRecyclerAdapter create(DataConverter data, IHomePage iHomePage) {
        return new HomeRecyclerAdapter(data.convert(), iHomePage);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        final List<HomePageBean.DataBean.BannersBean> bannersBeans;
        final HomePageBean.DataBean.FeatureInfo featureInfo;

        switch (item.getItemType()) {
            case ItemTypeHome.BANNER:
                bannersBeans = item.getField(MultipleFields.BANNERS);
                ConvenientBanner mBanner = helper.getView(R.id.banner_recycler_item);
                mBanner.setPages(new BannerHolderCreator(), bannersBeans)
                        .setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                if(IHOME_PAGE!= null) {
                                    IHOME_PAGE.OnItemClickListener(position, bannersBeans.get(position));
                                }
                            }
                        })
                        .setCanLoop(false);
                mBanner.startTurning(2000);
                break;
            case ItemTypeHome.TEXT_IMAGE:
                featureInfo = item.getField(MultipleFields.OBJECT);
                final boolean isPlatform;
                switch (featureInfo.getFeatureType()) {
                    case "1":
                        isPlatform = false;
                        helper.setImageResource(R.id.img_feature_home, R.mipmap.home_page_market_action);
                        break;
                    case "2":
                        isPlatform = false;
                        helper.setImageResource(R.id.img_feature_home, R.mipmap.home_page_market_execute);
                        break;
                    case "3":
                        isPlatform = true;
                        helper.setImageResource(R.id.img_feature_home, R.mipmap.home_page_channel_platform);
                        break;
                    default:
                        isPlatform = false;
                }
                helper.setText(R.id.tv_feature_name, featureInfo.getFeatureName());
                helper.getView(R.id.rl_group_market_action).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(IHOME_PAGE != null) {
                            IHOME_PAGE.OnItemClickListener(isPlatform, featureInfo);
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void init() {
        addItemType(ItemTypeHome.BANNER, R.layout.banner_home_layout);
        addItemType(ItemTypeHome.TEXT_IMAGE, R.layout.item_home_page);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}
