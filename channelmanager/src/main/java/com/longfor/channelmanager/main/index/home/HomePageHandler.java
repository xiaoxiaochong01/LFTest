package com.longfor.channelmanager.main.index.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.net.BaseSuccessListener;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.IError;
import com.longfor.core.utils.toast.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: tongzhenhua
 * @date: 2018/1/5
 * @function:
 */
public class HomePageHandler {
    private final Context CONTEXT;
    private final RecyclerView RECYCLERVIEW;
    private HomeRecyclerAdapter mAdapter = null;
    private final HomeDataConverter CONVERTER;
    private final IHomePage CALL_BACK;


    public HomePageHandler(Context context, RecyclerView recyclerView, HomeDataConverter converter, IHomePage homePage) {
        this.CONTEXT = context;
        RECYCLERVIEW = recyclerView;
        CONVERTER = converter;
        CALL_BACK = homePage;
    }

    public static HomePageHandler create(Context context, RecyclerView recyclerView, HomeDataConverter converter, IHomePage iHomePage) {
        return new HomePageHandler(context, recyclerView, converter,iHomePage);
    }

    public static HomePageHandler create(Context context, IHomePage homePage) {
        return new HomePageHandler(context, null, null, homePage);
    }

    /**
     * 请求首页数据
     * @param projectId
     */
    public void requestHomePageData(String projectId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMPLOYEE_ID, DatabaseManager.getEmployeeId());
        params.put(Constant.PROJECT_ID, projectId);
        RestClient.builder()
                .raw(params)
                .url(ConstantHome.URL_HOME_PAGE)
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        mAdapter = HomeRecyclerAdapter.create(CONVERTER.setJsonData(response), CALL_BACK);
                        RECYCLERVIEW.setAdapter(mAdapter);
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
