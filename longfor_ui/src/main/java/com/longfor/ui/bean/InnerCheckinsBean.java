package com.longfor.ui.bean;

/**
 * @author: gaomei
 * @date: 2018/1/4
 * @function:
 */

public class InnerCheckinsBean {
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
