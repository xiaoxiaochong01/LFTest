package com.longfor.channelmanager.arrange.group;

import com.longfor.core.bean.BaseResponse;

import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/19
 * @function:
 */
public class ArrangeGroupBean extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * workId : 2018-01-19
         * title : 01.15-01.21排班
         * workAddress : 景粼原著-外展外拓追电
         * teamId : 1870
         * teamName : 景粼原著-外展外拓追电
         * date : 1516349419304
         * readStatus : 1
         */

        private String workId;
        private String title;
        private String workAddress;
        private String teamId;
        private String teamName;
        private String date;
        private int readStatus;

        public String getWorkId() {
            return workId;
        }

        public void setWorkId(String workId) {
            this.workId = workId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getWorkAddress() {
            return workAddress;
        }

        public void setWorkAddress(String workAddress) {
            this.workAddress = workAddress;
        }

        public String getTeamId() {
            return teamId;
        }

        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getReadStatus() {
            return readStatus;
        }

        public void setReadStatus(int readStatus) {
            this.readStatus = readStatus;
        }
    }
}
