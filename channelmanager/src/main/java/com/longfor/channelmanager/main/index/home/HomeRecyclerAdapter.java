package com.longfor.channelmanager.main.index.home;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.longfor.channelmanager.R;
import com.longfor.ui.banner.HolderCreator;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;
import com.longfor.ui.recycler.MultipleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/4
 * @function: 首页适配器
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
        final List<IHomePage.HomePageBean.DataBean.BannersBean> bannersBeans;
        final IHomePage.HomePageBean.DataBean.FeatureInfo featureInfo;

        switch (item.getItemType()) {
            case ItemTypeHome.BANNER:
                bannersBeans = item.getField(MultipleFields.BANNERS);
                if(bannersBeans != null) {
                    List<String> urls = new ArrayList<>();
                    for(IHomePage.HomePageBean.DataBean.BannersBean bannersBean : bannersBeans) {
                        urls.add(bannersBean.getBannerImage());
                    }
                    ConvenientBanner mBanner = helper.getView(R.id.banner_recycler_item);
                    mBanner.setPages(new HolderCreator(), urls)
                            .setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    if (IHOME_PAGE != null) {
                                        IHOME_PAGE.OnItemClickListener(position, bannersBeans.get(position));
                                    }
                                }
                            })
                            .setCanLoop(false);
                    mBanner.startTurning(2000);
                }
                break;
            case ItemTypeHome.TEXT_IMAGE:
                featureInfo = item.getField(MultipleFields.OBJECT);
                final boolean isPlatform;
                switch (featureInfo.getFeatureType()) {
                    case ConstantHome.FEATURE_TYPE_ACTION:
                        isPlatform = false;
                        helper.setImageResource(R.id.img_feature_home, R.mipmap.home_page_market_action);
                        break;
                    case ConstantHome.FEATURE_TYPE_EXECUTE:
                        isPlatform = false;
                        helper.setImageResource(R.id.img_feature_home, R.mipmap.home_page_market_execute);
                        break;
                    case ConstantHome.FEATURE_TYPE_PLATFORM:
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
