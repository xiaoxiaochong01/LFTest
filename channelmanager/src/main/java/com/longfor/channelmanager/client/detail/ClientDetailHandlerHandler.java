package com.longfor.channelmanager.client.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.dialog.DialogWithYesOrNo;
import com.longfor.channelmanager.common.dialog.listener.OnDialogConfimClickListener;
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
 * @date: 2018/1/17
 * @function:
 */
public class ClientDetailHandlerHandler implements IClientDetailHandler {
    private final RecyclerView RECYCLER_DATA;
    private final RecyclerView RECYCLER_FOLLOW;
    private final ClientDetailDataConverter CONVERTER;
    private ClientDetailRecyclerAdapter mDataAdpater;
    private ClientDetailRecyclerAdapter mFollowAdpater;

    public ClientDetailHandlerHandler(RecyclerView recycler_follow, RecyclerView recycler_data, ClientDetailDataConverter converter) {
        RECYCLER_FOLLOW = recycler_follow;
        RECYCLER_DATA = recycler_data;
        CONVERTER = converter;
    }

    public static ClientDetailHandlerHandler create(RecyclerView recycler_follow, RecyclerView recycler_data, ClientDetailDataConverter converter) {
        return new ClientDetailHandlerHandler(recycler_follow, recycler_data, converter);
    }

    /**
     * 请求客户详情数据
     * @param mCustomerId 客户ID
     */
    public void requestClientDetailData(String mCustomerId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMPLOYEE_ID, DatabaseManager.getEmployeeId());
        params.put(Constant.PROJECT_ID, DatabaseManager.getProjectId());
        params.put(ConstantClientDetail.CUSTOMER_ID, mCustomerId);
        RestClient.builder()
                .raw(params)
                .url(ConstantClientDetail.URL_TRAINEE_GETCUSTOMER_DETAILS)
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        CONVERTER.setJsonData(response);
                        CONVERTER.convert();
                        mDataAdpater = ClientDetailRecyclerAdapter.create(CONVERTER.getDataEntitysHide(), ClientDetailHandlerHandler.this);
                        mFollowAdpater = ClientDetailRecyclerAdapter.create(CONVERTER.getFollowEntitys());
                        RECYCLER_FOLLOW.setAdapter(mFollowAdpater);
                        RECYCLER_DATA.setAdapter(mDataAdpater);
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
    @Override
    public void show() {
        mDataAdpater.refreshData(CONVERTER.getDataEntitysShow());
    }

    @Override
    public void hiden() {
        mDataAdpater.refreshData(CONVERTER.getDataEntitysHide());
    }

    @Override
    public void showCallPhoneDialog(final String phone) {
        final Context context = RECYCLER_FOLLOW.getContext();

        DialogWithYesOrNo.builder()
                .context(context)
                .title(phone)
                .ensure(context.getResources().getString(R.string.call))
                .cancel(context.getResources().getString(R.string.cancel))
                .listener(new OnDialogConfimClickListener() {
                    @Override
                    public void onEnsureClick() {
                        Intent intent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));//跳转到拨号界面，同时传递电话号码
                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelClick() {

                    }
                })
                .build()
                .showDialog();
    }

}
