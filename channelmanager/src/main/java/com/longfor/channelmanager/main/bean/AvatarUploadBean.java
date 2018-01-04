package com.longfor.channelmanager.main.bean;

import com.longfor.core.bean.BaseResponse;

/**
 * @author: tongzhenhua
 * @date: 2018/1/3
 * @function:
 */
public class AvatarUploadBean extends BaseResponse {

    /**
     * data : {"avatarUrl":"http://192.168.36.101:8080//pic/2018-01-03/6753_1514976045864.jpg"}
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
         * avatarUrl : http://192.168.36.101:8080//pic/2018-01-03/6753_1514976045864.jpg
         */

        private String avatarUrl;

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
}
