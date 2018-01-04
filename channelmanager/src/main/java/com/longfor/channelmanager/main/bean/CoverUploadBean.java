package com.longfor.channelmanager.main.bean;

import com.longfor.core.bean.BaseResponse;

/**
 * @author: tongzhenhua
 * @date: 2018/1/3
 * @function:
 */
public class CoverUploadBean extends BaseResponse {

    /**
     * data : {"imageUrl":"http://192.168.36.101:8080//pic/2018-01-03/6753_1514976315444.jpg"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * imageUrl : http://192.168.36.101:8080//pic/2018-01-03/6753_1514976315444.jpg
         */

        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
