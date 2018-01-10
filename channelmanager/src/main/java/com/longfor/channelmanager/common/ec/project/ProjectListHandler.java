package com.longfor.channelmanager.common.ec.project;

import android.content.Context;

import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.IError;
import com.longfor.core.net.callback.ISuccess;
import com.longfor.core.utils.log.LogUtils;
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
    private final ISuccess IPROJECT;

    public ProjectListHandler(Context context, ISuccess iProjectList) {
        IPROJECT = iProjectList;
        CONTEXT = context;
    }

    public static ProjectListHandler create(Context context, ISuccess iProjectList) {
        return new ProjectListHandler(context, iProjectList);
    }

    public void requestProjects() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMPLOYEE_ID, DatabaseManager.getEmployeeId());
        RestClient.builder()
                .raw(params)
                .url(ConstantProject.URL_GET_PROJECTS)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LogUtils.e("project", "response="+response);
                        if(IPROJECT != null) {
                            IPROJECT.onSuccess(response);
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.showMessage(msg);
                    }
                })
                .build()
                .post();
    }
}
