package com.longfor.channelmanager.statistics.checkinstatistics.bean;

import com.longfor.core.bean.BaseResponse;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/15
 * @function:
 */

public class CompanyCheckInBean extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * areaId : D5FA76DF-DCC4-4939-ACE3-FEF74D1B5E62
         * avg : 2.0
         * areaName : 北京龙湖
         * checkins : 0
         */

        private String areaId;
        private int avg;
        private String areaName;
        private int checkins;

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public int getAvg() {
            return avg;
        }

        public void setAvg(int avg) {
            this.avg = avg;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getCheckins() {
            return checkins;
        }

        public void setCheckins(int checkins) {
            this.checkins = checkins;
        }
    }
}
