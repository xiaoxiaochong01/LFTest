package com.longfor.channelmanager.recordlist.bean;

import com.longfor.core.bean.BaseResponse;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/23
 * @function:
 */

public class RecordListBean extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * recordId : 584
         * cover : http://192.168.36.101:8080//pic/
         * result : 6位
         * name : 外呼陌电-景粼原著-第一小组
         * avatar : http://192.168.36.101:8080//pic/
         * position : 1
         * schoolName :
         */

        private String recordId;
        private String cover;
        private String result;
        private String name;
        private String avatar;
        private int position;
        private String schoolName;

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }
    }
}
