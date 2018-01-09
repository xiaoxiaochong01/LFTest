package com.longfor.channelmanager.common.ec.project;

import android.content.Context;

import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.ec.unreadmessage.IUnReadMessageCount;
import com.longfor.channelmanager.common.ec.unreadmessage.UnReadMessageCountHandler;
import com.longfor.channelmanager.database.DatabaseManager;
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
public class ProjectListHandler {
    private final Context CONTEXT;
    private final IProjectList IPROJECT;

    public ProjectListHandler(Context context, IProjectList iProjectList) {
        IPROJECT = iProjectList;
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
                .url(ConstantProject.URL_GET_PROJECTS)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
//                        UnReadMsgBean unReadMsgBean = JSON.parseObject(response, UnReadMsgBean.class);
//                        Iproject.callBack(unReadMsgBean.getData().getMsgCount());

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
