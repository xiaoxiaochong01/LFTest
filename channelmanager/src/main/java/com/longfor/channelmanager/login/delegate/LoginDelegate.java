package com.longfor.channelmanager.login.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.login.LoginHandler;
import com.longfor.channelmanager.login.bean.LoginBean;
import com.longfor.channelmanager.login.contants.ConstantLogin;
import com.longfor.channelmanager.main.ChannelMainDelegate;
import com.longfor.channelmanager.common.net.BaseSuccessListener;
import com.longfor.core.bean.BaseResponse;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.IError;
import com.longfor.core.net.callback.IFailure;
import com.longfor.core.utils.log.LogUtils;
import com.longfor.core.utils.toast.ToastUtils;
import com.longfor.ui.dialog.DialogHint;
import com.longfor.ui.view.counttimer.CommonCountDownTimer;
import com.longfor.ui.view.editwatcher.EditTextWatcher;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: tongzhenhua
 * @date: 2017/12/26
 * @function:
 */
public class LoginDelegate extends LongForDelegate{
    @BindView(R2.id.et_oa_value)
    AppCompatEditText etOA;
    @BindView(R2.id.et_oa_password_value)
    AppCompatEditText etCode;
    @BindView(R2.id.tv_get_verify)
    AppCompatTextView tvGetCode;
    @BindView(R2.id.btn_login_ok)
    AppCompatButton btnLogin;

    private String strOA;
    private String strCode;

    @Override
    public Object setLayout() {
        return R.layout.delegate_login;
    }

    @OnClick(R2.id.btn_login_ok)
    void onLoginClick() {
        if(checkOA_Code()) {
            Map<String, String> map = new HashMap<>();
            map.put(ConstantLogin.LOGIN_NAME, strOA);
            map.put(ConstantLogin.ENTRY_CODE, strCode);
            RestClient.builder().url(ConstantLogin.URL_LOGIN)
                    .raw(map)
                    .success(new BaseSuccessListener() {
                        @Override
                        public void success(String response) {
                            LoginBean loginBean = JSON.parseObject(response, LoginBean.class);
                            if(loginBean != null) {
                                if(loginBean.getData() != null) {
//                                    // 存储用户信息
                                    LoginHandler.loginSucess(loginBean.getData());
                                    getSupportDelegate().startWithPop(new ChannelMainDelegate());

                                }
                                else {
                                    ToastUtils.showMessage(R.string.login_toast_user_error);
                                }
                            }
                            else {
                                ToastUtils.showMessage(R.string.data_parsing_error);
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

    @OnClick(R2.id.tv_get_verify)
    void onGetCodeClick() {
        strOA = etOA.getText().toString().trim();
        if(TextUtils.isEmpty(strOA)) {
            ToastUtils.showMessage(getResources().getString(R.string.login_toast_oa_null));
            return;
        }
        startCountDownTimer();
        Map<String, String> map = new HashMap<>();
        map.put(ConstantLogin.LOGIN_NAME, strOA);
        RestClient.builder().url(ConstantLogin.URL_SEND_CODE)
                .raw(map)
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        BaseResponse result = JSON.parseObject(response, BaseResponse.class);
                        if(result != null) {
                            if(ConstantLogin.CODE_0 == result.getCode() || ConstantLogin.CODE_2 == result.getCode()){
                                DialogHint dialogHint = new DialogHint(getContext(), getString(R.string.string_hint), result.getMessage());
                                dialogHint.show();
                            }
                        }
                        else {
                            ToastUtils.showMessage(getResources().getString(R.string.data_parsing_error));
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        LogUtils.e("验证码", "code="+code+"msg="+msg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        ToastUtils.showMessage(R.string.net_connect_time_out);
                    }
                })
                .loader(getContext())
                .build()
                .post();
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        etOA.addTextChangedListener(mChangeLoginAndGetCodeState);
        etCode.addTextChangedListener(mChangeLoginAndGetCodeState);
    }

    private boolean checkOA_Code() {
        strOA = etOA.getText().toString().trim();
        strCode = etCode.getText().toString().trim();
        if(TextUtils.isEmpty(strOA)) {
            ToastUtils.showMessage(getResources().getString(R.string.login_toast_oa_null));
            return false;
        }
        if(TextUtils.isEmpty(strCode) || !(strCode.length() == 4)) {
            ToastUtils.showMessage(getResources().getString(R.string.login_toast_code_error));
            return false;
        }

        return true;
    }
    /**
     * 输入账号验证码框监听器
     */
    private EditTextWatcher mChangeLoginAndGetCodeState = new EditTextWatcher(){
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            super.onTextChanged(s, start, before, count);
            changeCodeAndEnsureButtonState();
        }
    };

    /**
     * 根据输入框内容改变按钮状态
     */
    private void changeCodeAndEnsureButtonState() {
        String account = etOA.getText().toString().trim();
        String password = etCode.getText().toString().trim();
        String verify = tvGetCode.getText().toString().trim();
        boolean isTicking = verify.contains(getContext().getResources().getString(R.string.login_string_second));

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)&& password.length() == 4) {
            btnLogin.setBackgroundResource(R.mipmap.bg_login_btn);
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setBackgroundResource(R.mipmap.login_btn_nomal);
            btnLogin.setEnabled(false);
        }

        if (TextUtils.isEmpty(account) || isTicking) {
            tvGetCode.setTextColor(getResources().getColor(R.color.gray_9));
            tvGetCode.setEnabled(false);
        } else {
            tvGetCode.setTextColor(getResources().getColor(R.color.gray_3));
            tvGetCode.setEnabled(true);
        }
    }

    /**
     * 开启验证码
     * 获取倒计时
     */
    private void startCountDownTimer() {
        CommonCountDownTimer countDownTimer = new CommonCountDownTimer(tvGetCode, 60000, 1000);
        countDownTimer.start();
    }

}
