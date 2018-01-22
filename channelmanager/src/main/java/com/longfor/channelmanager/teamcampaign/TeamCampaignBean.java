package com.longfor.channelmanager.teamcampaign;

import com.longfor.core.bean.BaseResponse;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/22
 * @function:
 */

public class TeamCampaignBean extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * teamName : 景粼原著-外展组
         * missions : [{"totals":0,"title":"一级客储","type":1,"complete":0},{"totals":0,"title":"二级客储","type":2,"complete":0},{"totals":0,"title":"认购","type":3,"complete":0},{"totals":0,"title":"上岗人数","type":4,"complete":0}]
         * teamId : 1868
         * latitude : 39.985869
         * teamLeader :
         * position : 1
         * longitude : 116.415534
         */

        private String teamName;
        private String teamId;
        private String latitude;
        private String teamLeader;
        private int position;
        private String longitude;
        private List<MissionsBean> missions;

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getTeamId() {
            return teamId;
        }

        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getTeamLeader() {
            return teamLeader;
        }

        public void setTeamLeader(String teamLeader) {
            this.teamLeader = teamLeader;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public List<MissionsBean> getMissions() {
            return missions;
        }

        public void setMissions(List<MissionsBean> missions) {
            this.missions = missions;
        }

        public static class MissionsBean {
            /**
             * totals : 0
             * title : 一级客储
             * type : 1
             * complete : 0
             */

            private int totals;
            private String title;
            private int type;
            private int complete;

            public int getTotals() {
                return totals;
            }

            public void setTotals(int totals) {
                this.totals = totals;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getComplete() {
                return complete;
            }

            public void setComplete(int complete) {
                this.complete = complete;
            }
        }
    }
}
