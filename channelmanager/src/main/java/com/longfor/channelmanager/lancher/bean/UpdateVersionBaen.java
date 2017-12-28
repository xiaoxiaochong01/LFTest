package com.longfor.channelmanager.lancher.bean;

import com.longfor.core.bean.BaseResponse;

/**
 * @author: tongzhenhua
 * @date: 2017/12/25
 * @function:
 */
public class UpdateVersionBaen extends BaseResponse {
    /**
     "data": {
     "upgrade": 0,
     "downloadUrl": "",
     "message": "",
     "version": ""
     }
     */
    private DataBean data;

    public static class DataBean {
        private int upgrade;
        private String downloadUrl;
        private String message;
        private String version;

        public int getUpgrade() {
            return upgrade;
        }

        public void setUpgrade(int upgrade) {
            this.upgrade = upgrade;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}
