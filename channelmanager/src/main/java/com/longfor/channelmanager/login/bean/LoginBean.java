package com.longfor.channelmanager.login.bean;

import com.longfor.core.bean.BaseResponse;

/**
 * @author: tongzhenhua
 * @date: 2017/12/27
 * @function:
 */
public class LoginBean extends BaseResponse {

    /**
     * data : {"mobilePhone":"18310770997","roleId":4,"subRoleId":7,"project":{"projectName":"蔚澜香醍","projectId":"D50012F0-1DFD-4640-8C49-33A85833EDBD"},"employeeId":6753}
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
         * mobilePhone : 18310770997
         * roleId : 4
         * subRoleId : 7
         * project : {"projectName":"蔚澜香醍","projectId":"D50012F0-1DFD-4640-8C49-33A85833EDBD"}
         * employeeId : 6753
         */

        private String mobilePhone;
        private int roleId;
        private int subRoleId;
        private ProjectBean project;
        private int employeeId;

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public int getSubRoleId() {
            return subRoleId;
        }

        public void setSubRoleId(int subRoleId) {
            this.subRoleId = subRoleId;
        }

        public ProjectBean getProject() {
            return project;
        }

        public void setProject(ProjectBean project) {
            this.project = project;
        }

        public int getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
        }

        public static class ProjectBean {
            /**
             * projectName : 蔚澜香醍
             * projectId : D50012F0-1DFD-4640-8C49-33A85833EDBD
             */

            private String projectName;
            private String projectId;

            public String getProjectName() {
                return projectName;
            }

            public void setProjectName(String projectName) {
                this.projectName = projectName;
            }

            public String getProjectId() {
                return projectId;
            }

            public void setProjectId(String projectId) {
                this.projectId = projectId;
            }
        }
    }
}
