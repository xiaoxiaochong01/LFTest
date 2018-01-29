package com.longfor.channelmanager.arrange.week;

import com.longfor.core.bean.BaseResponse;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/29
 * @function:
 */

public class ArrangeWorkBackUpBean extends BaseResponse {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * members : [{"employeeName":"删除该人员","avgWorks":"","freetimes":"","employeeId":"0"}]
         * title : 本组人员
         */

        private String title;
        private List<MembersBean> members;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<MembersBean> getMembers() {
            return members;
        }

        public void setMembers(List<MembersBean> members) {
            this.members = members;
        }

        public static class MembersBean {
            /**
             * employeeName : 删除该人员
             * avgWorks :
             * freetimes :
             * employeeId : 0
             */

            private String employeeName;
            private String avgWorks;
            private String freetimes;
            private String employeeId;

            public String getEmployeeName() {
                return employeeName;
            }

            public void setEmployeeName(String employeeName) {
                this.employeeName = employeeName;
            }

            public String getAvgWorks() {
                return avgWorks;
            }

            public void setAvgWorks(String avgWorks) {
                this.avgWorks = avgWorks;
            }

            public String getFreetimes() {
                return freetimes;
            }

            public void setFreetimes(String freetimes) {
                this.freetimes = freetimes;
            }

            public String getEmployeeId() {
                return employeeId;
            }

            public void setEmployeeId(String employeeId) {
                this.employeeId = employeeId;
            }
        }
    }
}
