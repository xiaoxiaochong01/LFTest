package com.longfor.channelmanager.arrange.week;

import com.longfor.core.bean.BaseResponse;

/**
 * @author: gaomei
 * @date: 2018/1/29
 * @function:
 */

public class ArrangeWorkBean extends BaseResponse {

    /**
     * data : {"employeeName":"测试快速点击","avgWorks":"时产:0.0","freetimes":"上、下、晚","employeeId":"99200","scheduleId":"1516785271767"}
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
         * employeeName : 测试快速点击
         * avgWorks : 时产:0.0
         * freetimes : 上、下、晚
         * employeeId : 99200
         * scheduleId : 1516785271767
         */

        private String employeeName;
        private String avgWorks;
        private String freetimes;
        private String employeeId;
        private String scheduleId;

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

        public String getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(String scheduleId) {
            this.scheduleId = scheduleId;
        }
    }
}
