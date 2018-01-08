package com.longfor.channelmanager.main.bean;

import com.longfor.core.bean.BaseResponse;

import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/5
 * @function:
 */
public class HomePageBean extends BaseResponse {

    /**
     * data : {"banners":[{"bannerImage":"http://192.168.36.101:8080//pic/QDZY.jpg","bannerTitle":"渠道专员软文","bannerUrl":""}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<BannersBean> banners;
        private List<FeatureInfo> features;//首页功能 列表没有则不返回

        public List<FeatureInfo> getFeatures() {
            return features;
        }

        public void setFeatures(List<FeatureInfo> features) {
            this.features = features;
        }

        public List<BannersBean> getBanners() {
            return banners;
        }

        public void setBanners(List<BannersBean> banners) {
            this.banners = banners;
        }

        public static class BannersBean {
            /**
             * bannerImage : http://192.168.36.101:8080//pic/QDZY.jpg
             * bannerTitle : 渠道专员软文
             * bannerUrl :
             */

            private String bannerImage;
            private String bannerTitle;
            private String bannerUrl;

            public String getBannerImage() {
                return bannerImage;
            }

            public void setBannerImage(String bannerImage) {
                this.bannerImage = bannerImage;
            }

            public String getBannerTitle() {
                return bannerTitle;
            }

            public void setBannerTitle(String bannerTitle) {
                this.bannerTitle = bannerTitle;
            }

            public String getBannerUrl() {
                return bannerUrl;
            }

            public void setBannerUrl(String bannerUrl) {
                this.bannerUrl = bannerUrl;
            }
        }

        public static class FeatureInfo {
            private String featureName;//功能名字
            private String featureType;//功能类型： 1 营销动作；2营销执行单
            private String featureUrl;//功能详情 H5 url

            public String getFeatureName() {
                return featureName;
            }

            public void setFeatureName(String featureName) {
                this.featureName = featureName;
            }

            public String getFeatureType() {
                return featureType;
            }

            public void setFeatureType(String featureType) {
                this.featureType = featureType;
            }

            public String getFeatureUrl() {
                return featureUrl;
            }

            public void setFeatureUrl(String featureUrl) {
                this.featureUrl = featureUrl;
            }

            @Override
            public String toString() {
                return "FeatureInfo{" +
                        "featureName='" + featureName + '\'' +
                        ", featureType=" + featureType +
                        ", featureUrl='" + featureUrl + '\'' +
                        '}';
            }
        }
    }
}
