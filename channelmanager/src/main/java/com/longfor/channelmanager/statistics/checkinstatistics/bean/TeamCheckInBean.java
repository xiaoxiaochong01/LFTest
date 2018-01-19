package com.longfor.channelmanager.statistics.checkinstatistics.bean;

import com.longfor.core.bean.BaseResponse;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/15
 * @function:
 */

public class TeamCheckInBean extends BaseResponse {

    /**
     * data : {"teams":[{"teamName":"外呼陌电-景粼原著-第一小组","avg":1,"teamId":584,"checkins":0},{"teamName":"外呼陌电-景粼原著-第二小组","avg":1,"teamId":610,"checkins":0},{"teamName":"外呼陌电-景粼原著-第三小组","avg":0,"teamId":614,"checkins":0},{"teamName":"外呼追电-景粼原著-1组","avg":1,"teamId":618,"checkins":0},{"teamName":"外呼追电-景粼原著-2组","avg":0,"teamId":619,"checkins":0},{"teamName":"外呼追电-景粼原著-3组","avg":0,"teamId":622,"checkins":0},{"teamName":"外呼陌电-景粼原著-第四小组","avg":0,"teamId":625,"checkins":0},{"teamName":"景粼原著-外展组","avg":1,"teamId":1868,"checkins":0},{"teamName":"景粼原著-外拓组","avg":1,"teamId":1869,"checkins":0},{"teamName":"景粼原著-外展外拓追电","avg":1,"teamId":1870,"checkins":0}],"project":{"avg":2,"projectName":"景粼原著","projectId":"80B03281-B931-4838-AEAD-EE2BA11DF224","checkins":0}}
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
         * teams : [{"teamName":"外呼陌电-景粼原著-第一小组","avg":1,"teamId":584,"checkins":0},{"teamName":"外呼陌电-景粼原著-第二小组","avg":1,"teamId":610,"checkins":0},{"teamName":"外呼陌电-景粼原著-第三小组","avg":0,"teamId":614,"checkins":0},{"teamName":"外呼追电-景粼原著-1组","avg":1,"teamId":618,"checkins":0},{"teamName":"外呼追电-景粼原著-2组","avg":0,"teamId":619,"checkins":0},{"teamName":"外呼追电-景粼原著-3组","avg":0,"teamId":622,"checkins":0},{"teamName":"外呼陌电-景粼原著-第四小组","avg":0,"teamId":625,"checkins":0},{"teamName":"景粼原著-外展组","avg":1,"teamId":1868,"checkins":0},{"teamName":"景粼原著-外拓组","avg":1,"teamId":1869,"checkins":0},{"teamName":"景粼原著-外展外拓追电","avg":1,"teamId":1870,"checkins":0}]
         * project : {"avg":2,"projectName":"景粼原著","projectId":"80B03281-B931-4838-AEAD-EE2BA11DF224","checkins":0}
         */

        private ProjectCheckInBean.DataBean.ProjectsBean project;
        private List<TeamsBean> teams;

        public ProjectCheckInBean.DataBean.ProjectsBean getProject() {
            return project;
        }

        public void setProject(ProjectCheckInBean.DataBean.ProjectsBean project) {
            this.project = project;
        }

        public List<TeamsBean> getTeams() {
            return teams;
        }

        public void setTeams(List<TeamsBean> teams) {
            this.teams = teams;
        }

        public static class TeamsBean {
            /**
             * teamName : 外呼陌电-景粼原著-第一小组
             * avg : 1.0
             * teamId : 584
             * checkins : 0
             */

            private String teamName;
            private int avg;
            private String teamId;
            private int checkins;

            public String getTeamName() {
                return teamName;
            }

            public void setTeamName(String teamName) {
                this.teamName = teamName;
            }

            public int getAvg() {
                return avg;
            }

            public void setAvg(int avg) {
                this.avg = avg;
            }

            public String getTeamId() {
                return teamId;
            }

            public void setTeamId(String teamId) {
                this.teamId = teamId;
            }

            public int getCheckins() {
                return checkins;
            }

            public void setCheckins(int checkins) {
                this.checkins = checkins;
            }
        }
    }
}
