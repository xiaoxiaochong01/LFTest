package com.longfor.channelmanager.recordlist.handler;

import android.support.v7.widget.RecyclerView;

import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.net.BaseSuccessListener;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.channelmanager.recordlist.adapter.RecordListRvAdapter;
import com.longfor.channelmanager.recordlist.constant.RecordListConstant;
import com.longfor.channelmanager.recordlist.converter.RecordListConverter;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.IError;
import com.longfor.core.utils.toast.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gaomei
 * @date: 2018/1/23
 * @function:
 */

public class RecordListHandler implements IError {
    private RecyclerView mRecyclerView;
    private RecordListConverter mRecordListConverter;
    private RecordListRvAdapter mAdapter;
    private int mIsGroup;
    private String mCategory;
    private String mUserRole;
    private String mEmployeeId;
    private String mProjectId;

    public RecordListHandler(RecyclerView recyclerView, int isGroup, String category, String userRole,
                             RecordListConverter.OnGetNetDataListener onGetNetDataListener) {
        mRecyclerView = recyclerView;
        mIsGroup = isGroup;
        mCategory = category;
        mUserRole = userRole;
        mRecordListConverter = new RecordListConverter(onGetNetDataListener);
    }

    public static RecordListHandler create(RecyclerView recyclerView, int isGroup, String category, String userRole, RecordListConverter.OnGetNetDataListener onGetNetDataListener) {
        return new RecordListHandler(recyclerView, isGroup, category, userRole, onGetNetDataListener);
    }

    public void requestRecordList(int isGroup, String userRole) {
        mRecordListConverter.clearData();
        mIsGroup = isGroup;
        mUserRole = userRole;
        RestClient.builder()
                .raw(getParams())
                .url(getUrl())
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        if (mAdapter==null){
                            mAdapter = RecordListRvAdapter.create(mRecordListConverter.setJsonData(response).convert());
                            mRecyclerView.setAdapter(mAdapter);
                        }else {
                            mAdapter.refresh(mRecordListConverter.setJsonData(response));
                        }
                    }
                })
                .error(this)
                .loader(mRecyclerView.getContext())
                .build()
                .post();
    }

    private String getUrl() {
        return RecordListConstant.URL_RECORD_LIST;
    }

    private Map<String, String> getParams() {
        mEmployeeId = DatabaseManager.getEmployeeId();
        mProjectId = DatabaseManager.getProjectId();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMPLOYEE_ID, mEmployeeId);
        params.put(Constant.PROJECT_ID, mProjectId);
        params.put(RecordListConstant.IS_TEAM, String.valueOf(mIsGroup));
        params.put(RecordListConstant.KEY_STAT_PERIOD, RecordListConstant.VALUE_STAT_PERIOD);
        params.put(RecordListConstant.CATEGORY, mCategory);
        params.put(RecordListConstant.APPLY_ROLE, mUserRole);
        return params;
    }

    @Override
    public void onError(int code, String msg) {
        ToastUtils.showMessage(msg);
    }
}
