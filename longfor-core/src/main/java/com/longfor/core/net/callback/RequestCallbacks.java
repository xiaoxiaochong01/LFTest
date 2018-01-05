package com.longfor.core.net.callback;


import android.os.Handler;

import com.longfor.core.app.ConfigKeys;
import com.longfor.core.app.LongFor;
import com.longfor.core.net.RestCreator;
import com.longfor.core.ui.loader.LoaderStyle;
import com.longfor.core.ui.loader.LongforLoader;
import com.longfor.core.utils.log.LogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhanghaitao1 on 2017/12/5.
 */

public final class RequestCallbacks implements Callback<String> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADERSTYLE;
    private static final Handler HANDLER = LongFor.getHandler();

    public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error, LoaderStyle style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADERSTYLE = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.body()!= null) {
            LogUtils.e("ResponseBody", response.body().toString());
        }
        if(response.isSuccessful()){
            if(call.isExecuted()){
                if(SUCCESS!=null){
                    SUCCESS.onSuccess(response.body());
                }
            }
        }else{
            if(ERROR!=null){
                ERROR.onError(response.code(),response.message());
            }
        }
        onRequestFinish();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(FAILURE!=null){
            FAILURE.onFailure();
        }
        if(REQUEST!=null){
            REQUEST.onRequestEnd();
        }
        onRequestFinish();
    }

    private void onRequestFinish(){
        final long delayed = LongFor.getConfiguration(ConfigKeys.LOADER_DELAYED);
        if(LOADERSTYLE!=null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestCreator.getParams().clear();
                    LongforLoader.stopLoading();
                }
            },delayed);
        }

    }
}
