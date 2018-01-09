package com.longfor.channelmanager.common.ec.unreadmessage;

import com.longfor.core.bean.BaseResponse;

/**
 * @author: tongzhenhua
 * @date: 2018/1/5
 * @function:
 */
public class UnReadMsgBean extends BaseResponse{

    /**
     * data : {"msgCount":6}
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
         * msgCount : 6
         */

        private int msgCount;

        public int getMsgCount() {
            return msgCount;
        }

        public void setMsgCount(int msgCount) {
            this.msgCount = msgCount;
        }
    }
}
