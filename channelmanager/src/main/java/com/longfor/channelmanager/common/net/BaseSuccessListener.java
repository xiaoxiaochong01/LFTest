package com.longfor.channelmanager.common.net;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.R;
import com.longfor.core.app.LongFor;
import com.longfor.core.bean.BaseResponse;
import com.longfor.core.net.callback.ISuccess;
import com.longfor.core.utils.toast.ToastUtils;

/**
 * @author: tongzhenhua
 * @date: 2017/12/27
 * @function:
 */
public abstract class BaseSuccessListener implements ISuccess {
    @Override
    public void onSuccess(String response) {
        try {
            BaseResponse baseResponse = JSON.parseObject(response, BaseResponse.class);
            if(baseResponse == null) {
                ToastUtils.showMessage(R.string.data_parsing_error);
                return;
            }
            if(baseResponse.getCode() != 0) {
                ToastUtils.showMessage(baseResponse.getMessage());
                return;
            }
            success(response);
        }
        catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showMessage(R.string.data_parsing_error);
        }
    }

     public abstract void success(String response);
}
