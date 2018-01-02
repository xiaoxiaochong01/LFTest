package com.longfor.channelmanager.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: tongzhenhua
 * @date: 2017/12/27
 * @function:
 */
@Entity(nameInDb = "user_profile")
public class UserProfile {

    /**
     * mobilePhone : 18310770997
     * roleId : 4
     * subRoleId : 7
     * projectName : 蔚澜香醍
     * projectId : D50012F0-1DFD-4640-8C49-33A85833EDBD
     * employeeId : 6753
     */
    @Id
    private long userid;
    private String mobilePhone;
    private int roleId;
    private int subRoleId;
    private String projectName;
    private String projectId;
    private int employeeId;
    public int getEmployeeId() {
        return this.employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    public String getProjectId() {
        return this.projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public String getProjectName() {
        return this.projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public int getSubRoleId() {
        return this.subRoleId;
    }
    public void setSubRoleId(int subRoleId) {
        this.subRoleId = subRoleId;
    }
    public int getRoleId() {
        return this.roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public String getMobilePhone() {
        return this.mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public long getUserid() {
        return this.userid;
    }
    public void setUserid(long userid) {
        this.userid = userid;
    }
    @Generated(hash = 1434109476)
    public UserProfile(long userid, String mobilePhone, int roleId, int subRoleId,
            String projectName, String projectId, int employeeId) {
        this.userid = userid;
        this.mobilePhone = mobilePhone;
        this.roleId = roleId;
        this.subRoleId = subRoleId;
        this.projectName = projectName;
        this.projectId = projectId;
        this.employeeId = employeeId;
    }
    @Generated(hash = 968487393)
    public UserProfile() {
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userid=" + userid +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", roleId=" + roleId +
                ", subRoleId=" + subRoleId +
                ", projectName='" + projectName + '\'' +
                ", projectId='" + projectId + '\'' +
                ", employeeId=" + employeeId +
                '}';
    }
}
