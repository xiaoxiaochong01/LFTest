package com.longfor.channelmanager.statistics.queryattendance.bean;

import com.longfor.core.bean.BaseResponse;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/9
 * @function:考勤查询的bean
 */

public class CheckInListBean extends BaseResponse{
    private CheckInListData data;

    public CheckInListData getData() {
        return data;
    }

    public void setData(CheckInListData data) {
        this.data = data;
    }

    public static class CheckInListData {
        /**
         * "totals":90,   //一共有多少条考勤记录，用于分页判断
         * "checkins": []  //分页每页请求多少条数据，有的话数组中就返回多少个元素
         */
        private int totals;
        private List<EmplyoeeCheckinsInfo> checkins;

        public int getTotals() {
            return totals;
        }

        public void setTotals(int totals) {
            this.totals = totals;
        }

        public List<EmplyoeeCheckinsInfo> getCheckins() {
            return checkins;
        }

        public void setCheckins(List<EmplyoeeCheckinsInfo> checkins) {
            this.checkins = checkins;
        }

        public static class EmplyoeeCheckinsInfo {
            /**
             * "employeeName":"张三", //员工姓名
             * "workAdresses": [] 工作地点 集合 //必须按照班次从早到晚来拼装
             * innerCheckins  头像集合  //必须按照班次从早到晚来拼装
             */
            private String employeeName;
            private List<String> workAddresses;
            private List<InnerCheckins> innerCheckins;

            public String getEmployeeName() {
                return employeeName;
            }

            public void setEmployeeName(String employeeName) {
                this.employeeName = employeeName;
            }

            public List<String> getWorkAdresses() {
                return workAddresses;
            }

            public void setWorkAdresses(List<String> workAdresses) {
                this.workAddresses = workAdresses;
            }

            public List<InnerCheckins> getInnerCheckins() {
                return innerCheckins;
            }

            public void setInnerCheckins(List<InnerCheckins> innerCheckins) {
                this.innerCheckins = innerCheckins;
            }

            public static class InnerCheckins {
                /**
                 * "arriveStatus":0,//打卡状态：0：打卡正常 1：迟到 2：早退 3：未打卡
                 * "leaveStatus":0,//打卡状态：0：打卡正常 1：迟到 2：早退 3：未打卡
                 * "arriveImageUrl":"",//上班打卡的照片
                 * "leaveImageUrl":"",//下班打卡的照片
                 */
                private int arriveStatus;
                private int leaveStatus;
                private String arriveImageUrl;
                private String leaveImageUrl;

                public int getArriveStatus() {
                    return arriveStatus;
                }

                public void setArriveStatus(int arriveStatus) {
                    this.arriveStatus = arriveStatus;
                }

                public int getLeaveStatus() {
                    return leaveStatus;
                }

                public void setLeaveStatus(int leaveStatus) {
                    this.leaveStatus = leaveStatus;
                }

                public String getArriveImageUrl() {
                    return arriveImageUrl;
                }

                public void setArriveImageUrl(String arriveImageUrl) {
                    this.arriveImageUrl = arriveImageUrl;
                }

                public String getLeaveImageUrl() {
                    return leaveImageUrl;
                }

                public void setLeaveImageUrl(String leaveImageUrl) {
                    this.leaveImageUrl = leaveImageUrl;
                }
            }
        }
    }
}
