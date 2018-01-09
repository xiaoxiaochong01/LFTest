package com.longfor.channelmanager.common.ec.unreadmessage;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.channelmanager.main.bean.UnReadMsgBean;
import com.longfor.channelmanager.main.index.mine.ConstantMine;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.IError;
import com.longfor.core.net.callback.ISuccess;
import com.longfor.core.utils.toast.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: tongzhenhua
 * @date: 2018/1/5
 * @function:
 */
public class UnReadMessageCountHandler {
    private final Context CONTEXT;
    private final IUnReadMessageCount MESSAGE_COUNT;
    public UnReadMessageCountHandler(Context context, IUnReadMessageCount iUnReadMessageCount) {
        MESSAGE_COUNT = iUnReadMessageCount;
        CONTEXT = context;
    }

    public static UnReadMessageCountHandler create(Context context, IUnReadMessageCount iUnReadMessageCount) {
        return new UnReadMessageCountHandler(context, iUnReadMessageCount);
    }

    public void requestUnReadMessageCount() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMPLOYEE_ID, DatabaseManager.getEmployeeId());
        RestClient.builder()
                    .raw(params)
                    .url(ConstantUnreadMsg.URL_STUDENT_UNREAD_MESSAGE_COUNT)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            UnReadMsgBean unReadMsgBean = JSON.parseObject(response, UnReadMsgBean.class);
                            MESSAGE_COUNT.callBack(unReadMsgBean.getData().getMsgCount());

                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            ToastUtils.showMessage(CONTEXT, msg);
                        }
                    })
                    .build()
                    .post();
    }
}
