package com.longfor.channelmanager.main.bean;

import com.longfor.core.bean.BaseResponse;

/**
 * @author: tongzhenhua
 * @date: 2018/1/3
 * @function:
 */
public class UserInfoBean extends BaseResponse {

    /**
     * data : {"college":"","employeeName":"张琳媛","userSex":"1","recommendationCode":"88247","bankCard":"","city":{"cityName":"","cityCode":"","citySpell":""},"roleId":4,"idCard":"","employeeId":6753,"bankName":"","coverUrl":"http://192.168.36.101:8080//pic/2017-12-25/6753_1514190906114.jpg","major":"","school":{"schoolSpell":"CSXX","schoolName":"测试学校","schoolCode":"99999"},"subRoleId":7,"qrCodeUrl":"","headPortraitUrl":"http://192.168.36.101:8080//pic/2018-01-03/6753_1514943832943.jpg","entranceDate":""}
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
         * college :
         * employeeName : 张琳媛
         * userSex : 1
         * recommendationCode : 88247
         * bankCard :
         * city : {"cityName":"","cityCode":"","citySpell":""}
         * roleId : 4
         * idCard :
         * employeeId : 6753
         * bankName :
         * coverUrl : http://192.168.36.101:8080//pic/2017-12-25/6753_1514190906114.jpg
         * major :
         * school : {"schoolSpell":"CSXX","schoolName":"测试学校","schoolCode":"99999"}
         * subRoleId : 7
         * qrCodeUrl :
         * headPortraitUrl : http://192.168.36.101:8080//pic/2018-01-03/6753_1514943832943.jpg
         * entranceDate :
         */

        private String college;
        private String employeeName;
        private String userSex;
        private String recommendationCode;
        private String bankCard;
        private CityBean city;
        private int roleId;
        private String idCard;
        private int employeeId;
        private String bankName;
        private String coverUrl;
        private String major;
        private SchoolBean school;
        private int subRoleId;
        private String qrCodeUrl;
        private String headPortraitUrl;
        private String entranceDate;

        public String getCollege() {
            return college;
        }

        public void setCollege(String college) {
            this.college = college;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public String getUserSex() {
            return userSex;
        }

        public void setUserSex(String userSex) {
            this.userSex = userSex;
        }

        public String getRecommendationCode() {
            return recommendationCode;
        }

        public void setRecommendationCode(String recommendationCode) {
            this.recommendationCode = recommendationCode;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }

        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public int getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public SchoolBean getSchool() {
            return school;
        }

        public void setSchool(SchoolBean school) {
            this.school = school;
        }

        public int getSubRoleId() {
            return subRoleId;
        }

        public void setSubRoleId(int subRoleId) {
            this.subRoleId = subRoleId;
        }

        public String getQrCodeUrl() {
            return qrCodeUrl;
        }

        public void setQrCodeUrl(String qrCodeUrl) {
            this.qrCodeUrl = qrCodeUrl;
        }

        public String getHeadPortraitUrl() {
            return headPortraitUrl;
        }

        public void setHeadPortraitUrl(String headPortraitUrl) {
            this.headPortraitUrl = headPortraitUrl;
        }

        public String getEntranceDate() {
            return entranceDate;
        }

        public void setEntranceDate(String entranceDate) {
            this.entranceDate = entranceDate;
        }

        public static class CityBean {
            /**
             * cityName :
             * cityCode :
             * citySpell :
             */

            private String cityName;
            private String cityCode;
            private String citySpell;

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
                this.cityCode = cityCode;
            }

            public String getCitySpell() {
                return citySpell;
            }

            public void setCitySpell(String citySpell) {
                this.citySpell = citySpell;
            }
        }

        public static class SchoolBean {
            /**
             * schoolSpell : CSXX
             * schoolName : 测试学校
             * schoolCode : 99999
             */

            private String schoolSpell;
            private String schoolName;
            private String schoolCode;

            public String getSchoolSpell() {
                return schoolSpell;
            }

            public void setSchoolSpell(String schoolSpell) {
                this.schoolSpell = schoolSpell;
            }

            public String getSchoolName() {
                return schoolName;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }

            public String getSchoolCode() {
                return schoolCode;
            }

            public void setSchoolCode(String schoolCode) {
                this.schoolCode = schoolCode;
            }
        }
    }
}
