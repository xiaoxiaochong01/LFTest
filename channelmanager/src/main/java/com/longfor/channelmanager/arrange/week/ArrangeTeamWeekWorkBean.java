package com.longfor.channelmanager.arrange.week;

import com.longfor.core.bean.BaseResponse;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/25
 * @function:
 */

public class ArrangeTeamWeekWorkBean extends BaseResponse{

    /**
     * data : {"teamId":"625","teamName":"外呼陌电-景粼原著-第四小组","weekDatas":[{"date":"2018-01-26","dayName":"周五","shifts":[{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下","scheduleId":"1516958573449"}],"name":"上午","personAmount":5,"shiftId":"1"},{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下","scheduleId":"1516958573449"}],"name":"下午","personAmount":5,"shiftId":"2"},{"arranged":[],"name":"晚上","personAmount":5,"shiftId":"3"}]},{"date":"2018-01-27","dayName":"周六","shifts":[{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99200","employeeName":"测试快速点击","freetimes":"下、上、晚","scheduleId":"1516958573766"},{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下、晚","scheduleId":"1516958573766"}],"name":"上午","personAmount":5,"shiftId":"1"},{"arranged":[],"name":"下午","personAmount":5,"shiftId":"2"},{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下、晚","scheduleId":"1516958573766"}],"name":"晚上","personAmount":5,"shiftId":"3"}]},{"date":"2018-01-28","dayName":"周日","shifts":[{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下、晚","scheduleId":"1516958574018"}],"name":"上午","personAmount":5,"shiftId":"1"},{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下、晚","scheduleId":"1516958574018"}],"name":"下午","personAmount":5,"shiftId":"2"},{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下、晚","scheduleId":"1516958574018"}],"name":"晚上","personAmount":5,"shiftId":"3"}]}]}
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
         * teamId : 625
         * teamName : 外呼陌电-景粼原著-第四小组
         * weekDatas : [{"date":"2018-01-26","dayName":"周五","shifts":[{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下","scheduleId":"1516958573449"}],"name":"上午","personAmount":5,"shiftId":"1"},{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下","scheduleId":"1516958573449"}],"name":"下午","personAmount":5,"shiftId":"2"},{"arranged":[],"name":"晚上","personAmount":5,"shiftId":"3"}]},{"date":"2018-01-27","dayName":"周六","shifts":[{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99200","employeeName":"测试快速点击","freetimes":"下、上、晚","scheduleId":"1516958573766"},{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下、晚","scheduleId":"1516958573766"}],"name":"上午","personAmount":5,"shiftId":"1"},{"arranged":[],"name":"下午","personAmount":5,"shiftId":"2"},{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下、晚","scheduleId":"1516958573766"}],"name":"晚上","personAmount":5,"shiftId":"3"}]},{"date":"2018-01-28","dayName":"周日","shifts":[{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下、晚","scheduleId":"1516958574018"}],"name":"上午","personAmount":5,"shiftId":"1"},{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下、晚","scheduleId":"1516958574018"}],"name":"下午","personAmount":5,"shiftId":"2"},{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下、晚","scheduleId":"1516958574018"}],"name":"晚上","personAmount":5,"shiftId":"3"}]}]
         */

        private String teamId;
        private String teamName;
        private List<WeekDatasBean> weekDatas;

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

        public List<WeekDatasBean> getWeekDatas() {
            return weekDatas;
        }

        public void setWeekDatas(List<WeekDatasBean> weekDatas) {
            this.weekDatas = weekDatas;
        }

        public static class WeekDatasBean {
            /**
             * date : 2018-01-26
             * dayName : 周五
             * shifts : [{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下","scheduleId":"1516958573449"}],"name":"上午","personAmount":5,"shiftId":"1"},{"arranged":[{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下","scheduleId":"1516958573449"}],"name":"下午","personAmount":5,"shiftId":"2"},{"arranged":[],"name":"晚上","personAmount":5,"shiftId":"3"}]
             */

            private String date;
            private String dayName;
            private List<ShiftsBean> shifts;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDayName() {
                return dayName;
            }

            public void setDayName(String dayName) {
                this.dayName = dayName;
            }

            public List<ShiftsBean> getShifts() {
                return shifts;
            }

            public void setShifts(List<ShiftsBean> shifts) {
                this.shifts = shifts;
            }

            public static class ShiftsBean {
                /**
                 * arranged : [{"avgWorks":"时产:0.0","employeeId":"99477","employeeName":"花色","freetimes":"上、下","scheduleId":"1516958573449"}]
                 * name : 上午
                 * personAmount : 5
                 * shiftId : 1
                 */
                private String name;
                private int personAmount;
                private String shiftId;
                private List<ArrangedBean> arranged;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getPersonAmount() {
                    return personAmount;
                }

                public void setPersonAmount(int personAmount) {
                    this.personAmount = personAmount;
                }

                public String getShiftId() {
                    return shiftId;
                }

                public void setShiftId(String shiftId) {
                    this.shiftId = shiftId;
                }

                public List<ArrangedBean> getArranged() {
                    return arranged;
                }

                public void setArranged(List<ArrangedBean> arranged) {
                    this.arranged = arranged;
                }

                public static class ArrangedBean {
                    /**
                     * avgWorks : 时产:0.0
                     * employeeId : 99477
                     * employeeName : 花色
                     * freetimes : 上、下
                     * scheduleId : 1516958573449
                     */

                    private String avgWorks;
                    private String employeeId;
                    private String employeeName;
                    private String freetimes;
                    private String scheduleId;
                    private boolean empty;

                    public ArrangedBean() {
                    }

                    public ArrangedBean(boolean empty) {
                        this.empty = empty;
                    }

                    public String getAvgWorks() {
                        return avgWorks;
                    }

                    public void setAvgWorks(String avgWorks) {
                        this.avgWorks = avgWorks;
                    }

                    public String getEmployeeId() {
                        return employeeId;
                    }

                    public void setEmployeeId(String employeeId) {
                        this.employeeId = employeeId;
                    }

                    public String getEmployeeName() {
                        return employeeName;
                    }

                    public void setEmployeeName(String employeeName) {
                        this.employeeName = employeeName;
                    }

                    public String getFreetimes() {
                        return freetimes;
                    }

                    public void setFreetimes(String freetimes) {
                        this.freetimes = freetimes;
                    }

                    public String getScheduleId() {
                        return scheduleId;
                    }

                    public void setScheduleId(String scheduleId) {
                        this.scheduleId = scheduleId;
                    }

                    public boolean isEmpty() {
                        return empty;
                    }

                    public void setEmpty(boolean empty) {
                        this.empty = empty;
                    }
                }
            }
        }
    }
}
