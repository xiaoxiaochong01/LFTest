package com.longfor.channelmanager.statistics.queryattendance.bean;

import com.longfor.core.bean.BaseResponse;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/9
 * @function:考勤查询的bean
 */

public class CheckInListBean extends BaseResponse{

    /**
     * data : {"totals":2,"checkins":[{"employeeName":"高美","workAddresses":["北京市北京市朝阳区安定路11号(上午)","北京市北京市朝阳区安定路11号(下午)","北京市北京市朝阳区安定路11号(晚上)"],"innerCheckins":[{"arriveStatus":2,"leaveImageUrl":"","arriveImageUrl":"http://192.168.36.101:8080//pic/2018-01-10/98141_1515549461788.jpg","leaveStatus":3}]},{"employeeName":"测试高大美","workAddresses":["北京市北京市朝阳区安定路11号(下午)","北京市北京市朝阳区安定路11号(晚上)"],"innerCheckins":[{"arriveStatus":0,"leaveImageUrl":"","arriveImageUrl":"http://192.168.36.101:8080//pic/2018-01-10/98267_1515549706861.jpg","leaveStatus":3}]}]}
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
         * totals : 2
         * checkins : [{"employeeName":"高美","workAddresses":["北京市北京市朝阳区安定路11号(上午)","北京市北京市朝阳区安定路11号(下午)","北京市北京市朝阳区安定路11号(晚上)"],"innerCheckins":[{"arriveStatus":2,"leaveImageUrl":"","arriveImageUrl":"http://192.168.36.101:8080//pic/2018-01-10/98141_1515549461788.jpg","leaveStatus":3}]},{"employeeName":"测试高大美","workAddresses":["北京市北京市朝阳区安定路11号(下午)","北京市北京市朝阳区安定路11号(晚上)"],"innerCheckins":[{"arriveStatus":0,"leaveImageUrl":"","arriveImageUrl":"http://192.168.36.101:8080//pic/2018-01-10/98267_1515549706861.jpg","leaveStatus":3}]}]
         */

        private int totals;
        private List<CheckinsBean> checkins;

        public int getTotals() {
            return totals;
        }

        public void setTotals(int totals) {
            this.totals = totals;
        }

        public List<CheckinsBean> getCheckins() {
            return checkins;
        }

        public void setCheckins(List<CheckinsBean> checkins) {
            this.checkins = checkins;
        }

        public static class CheckinsBean {
            /**
             * employeeName : 高美
             * workAddresses : ["北京市北京市朝阳区安定路11号(上午)","北京市北京市朝阳区安定路11号(下午)","北京市北京市朝阳区安定路11号(晚上)"]
             * innerCheckins : [{"arriveStatus":2,"leaveImageUrl":"","arriveImageUrl":"http://192.168.36.101:8080//pic/2018-01-10/98141_1515549461788.jpg","leaveStatus":3}]
             */

            private String employeeName;
            private List<String> workAddresses;
            private List<InnerCheckinsBean> innerCheckins;

            public String getEmployeeName() {
                return employeeName;
            }

            public void setEmployeeName(String employeeName) {
                this.employeeName = employeeName;
            }

            public List<String> getWorkAddresses() {
                return workAddresses;
            }

            public void setWorkAddresses(List<String> workAddresses) {
                this.workAddresses = workAddresses;
            }

            public List<InnerCheckinsBean> getInnerCheckins() {
                return innerCheckins;
            }

            public void setInnerCheckins(List<InnerCheckinsBean> innerCheckins) {
                this.innerCheckins = innerCheckins;
            }

            public static class InnerCheckinsBean {
                /**
                 * arriveStatus : 2
                 * leaveImageUrl :
                 * arriveImageUrl : http://192.168.36.101:8080//pic/2018-01-10/98141_1515549461788.jpg
                 * leaveStatus : 3
                 */

                private int arriveStatus;
                private String leaveImageUrl;
                private String arriveImageUrl;
                private int leaveStatus;

                public int getArriveStatus() {
                    return arriveStatus;
                }

                public void setArriveStatus(int arriveStatus) {
                    this.arriveStatus = arriveStatus;
                }

                public String getLeaveImageUrl() {
                    return leaveImageUrl;
                }

                public void setLeaveImageUrl(String leaveImageUrl) {
                    this.leaveImageUrl = leaveImageUrl;
                }

                public String getArriveImageUrl() {
                    return arriveImageUrl;
                }

                public void setArriveImageUrl(String arriveImageUrl) {
                    this.arriveImageUrl = arriveImageUrl;
                }

                public int getLeaveStatus() {
                    return leaveStatus;
                }

                public void setLeaveStatus(int leaveStatus) {
                    this.leaveStatus = leaveStatus;
                }
            }
        }
    }
}
