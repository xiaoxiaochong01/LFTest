package com.longfor.channelmanager.main.delegate.index;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.constants.Constant;
import com.longfor.channelmanager.common.delegate.WebviewDelegate;
import com.longfor.channelmanager.common.dialog.DialogWithYesOrNo;
import com.longfor.channelmanager.common.dialog.listener.OnDialogConfimClickListener;
import com.longfor.channelmanager.common.net.BaseSuccessListener;
import com.longfor.channelmanager.common.view.popupwindow.ChooseImgPopWindow;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.channelmanager.main.bean.UserInfoBean;
import com.longfor.channelmanager.main.constants.ConstantMain;
import com.longfor.channelmanager.mine.delegate.AboutUsDelegate;
import com.longfor.channelmanager.mine.delegate.RecommendDelegate;
import com.longfor.channelmanager.mine.delegate.SettingDelegate;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.delegates.bottomreplace.BottomItemDelegate;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.IError;
import com.longfor.core.net.callback.ISuccess;
import com.longfor.core.net.request.RequestParams;
import com.longfor.core.utils.UI.UiUtil;
import com.longfor.core.utils.filecompress.PicCompressUtils;
import com.longfor.core.utils.log.LogUtils;
import com.longfor.core.utils.toast.ToastUtils;
import com.longfor.ui.view.circleimageview.CircleImageView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: tongzhenhua
 * @date: 2017/12/28
 * @function:
 */
public class IndexMineDelegate extends BottomItemDelegate {
    private final int TAKE_COVER_REQUEST_CODE = 1;
    private final int SELECT_COVER_REQUEST_CODE = 2;
    
    private final int TAKE_PORTRAIT_REQUEST_CODE = 3;
    private final int SELECT_PORTRAIT_REQUEST_CODE = 4;

    private final int REQUEST_WRITE_CODE = 110;
    private final int REQUEST_CAMERA_CODE = 111;


    
    @BindView(R2.id.img_title_background)
    AppCompatImageView imgTopBg;
    @BindView(R2.id.img_uer_head_portrait)
    CircleImageView imgHead;
    @BindView(R2.id.tv_user_name)
    AppCompatTextView tvUserName;
    @BindView(R2.id.rl_about_us)
    RelativeLayout rlAboutUs;
    @BindView(R2.id.rl_feed_back)
    RelativeLayout rlFeedBack;
    @BindView(R2.id.rl_recommend_code)
    RelativeLayout rlRecommend;
    @BindView(R2.id.rl_setting)
    RelativeLayout rlSetting;

    private ChooseImgPopWindow chooseImgPopWindow;
    private DialogWithYesOrNo mDialogHintPermission;
    private File mUploadFile = null;
    private boolean mIsUpdateCover;
    private String mEmployeeId;

    @Override
    public Object setLayout() {
        return R.layout.delegate_index_mine;
    }

    /**
     * 封面点击事件
     */
    @OnClick(R2.id.img_title_background)
    void onTopBackgroundClick() {
        mIsUpdateCover = true;
        checkPermission();
    }

    /**
     * 更改头像
     */
    @OnClick(R2.id.img_uer_head_portrait)
    void onUserHeadClick() {
        mIsUpdateCover = false;
//        requestWritePermissions()
        checkPermission();
    }
    @OnClick(R2.id.rl_about_us)
    void onAboutUsClick() {
       jumToDelegateFromParent(new AboutUsDelegate());
    }
    @OnClick(R2.id.rl_recommend_code)
    void onRecommendClick() {
        jumToDelegateFromParent(new RecommendDelegate());
    }
    @OnClick(R2.id.rl_feed_back)
    void onFeedBackClick() {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE_LEFT_TEXT, getResources().getString(R.string.mine_title));
        bundle.putString(Constant.WEB_URL,"http://www.longfor.com");
        bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.feed_back_title));
        WebviewDelegate supportFragment = new WebviewDelegate();
//        supportFragment.putNewBundle(bundle);
        supportFragment.setArguments(bundle);
        getParentDelegate().start(supportFragment);
    }
    @OnClick(R2.id.rl_setting)
    void onSettingClick() {
        jumToDelegateFromParent(new SettingDelegate());
    }

    private void jumToDelegateFromParent(LongForDelegate longForDelegate) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE_LEFT_TEXT, getResources().getString(R.string.mine_title));
        longForDelegate.setArguments(bundle);
        getParentDelegate().start(longForDelegate);
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            mDialogHintPermission.showDialog();
        } else {
            uploadPicture();
        }
    }
    private void requestWritePermissions() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_CODE);
    }

    private void uploadPicture() {
        if (mIsUpdateCover) {
            showPopUpWindow(TAKE_COVER_REQUEST_CODE, SELECT_COVER_REQUEST_CODE);
        } else {
            showPopUpWindow(TAKE_PORTRAIT_REQUEST_CODE, SELECT_PORTRAIT_REQUEST_CODE);
        }
    }
    //弹窗选择拍照、相册选择
    public void showPopUpWindow(final int takePhoto, final int selectPhoto) {
        chooseImgPopWindow = new ChooseImgPopWindow(getActivity());
        chooseImgPopWindow.showPopWdByLocation();
        chooseImgPopWindow.setOnCallBackImagePath(new ChooseImgPopWindow.OnCallBackImagePath() {
            @Override
            public void takePhoto() {
                Intent mIntent = new Intent(getActivity(), ImageGridActivity.class);
                mIntent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true);
                startActivityForResult(mIntent, takePhoto);
            }

            @Override
            public void selectPhoto() {
                Intent mIntent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(mIntent, selectPhoto);
            }

        });
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mEmployeeId = DatabaseManager.getEmployeeId();
        mDialogHintPermission = new DialogWithYesOrNo(getContext(), getString(R.string.hint_write_external_storage),
                null, getString(R.string.setting), getString(R.string.cancel), new OnDialogConfimClickListener() {
            @Override
            public void onEnsureClick() {
                requestWritePermissions();
            }

            @Override
            public void onCancelClick() {

            }
        });
        requstMinePort();
    }

    /**
     * 请求我的接口
     */
    private void requstMinePort() {

        Map<String, String> map = new HashMap<>();
        map.put(Constant.EMPLOYEE_ID, mEmployeeId);
        RestClient.builder().raw(map)
                .url(ConstantMain.URL_TRAINEE_GET_PROFILE)
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        if(isAdded()) {
                            UserInfoBean userInfoBean = JSON.parseObject(response, UserInfoBean.class);
                            UserInfoBean.DataBean dataBean = userInfoBean.getData();
                            tvUserName.setText(userInfoBean.getData().getEmployeeName());
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.showMessage(getContext(), msg);
                    }
                })
                .build()
                .post();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PORTRAIT_REQUEST_CODE:       //头像-拍照
                if (data != null) {
                    showImg(data, ConstantMain.URL_STUDENT_HEAD_IMG, imgHead);
                }
                break;
            case SELECT_PORTRAIT_REQUEST_CODE:       //头像-选择
                if (data != null) {
                    showImg(data, ConstantMain.URL_STUDENT_HEAD_IMG, imgHead);
                }
                break;
            case TAKE_COVER_REQUEST_CODE:         //封面-拍照
                if (data != null) {
                    showImg(data, ConstantMain.URL_STUDENT_COVER, imgTopBg);
                }
                break;
            case SELECT_COVER_REQUEST_CODE:          //封面-选择
                if (data != null) {
                    showImg(data, ConstantMain.URL_STUDENT_COVER, imgTopBg);
                }
                break;
        }
    }

    /**
     * 上传封面照片
     */
    public void showImg(Intent data, final String requestUrl, final ImageView img) {
        final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
        UiUtil.runInThread(new Runnable() {
            @Override
            public void run() {
                mUploadFile = PicCompressUtils.getSmallPicFile(getContext(), images.get(0).path);
                if (mUploadFile == null) {
                    ToastUtils.showMessage(getContext(), getString(R.string.picture_illegal));
                    return;
                }
//                requstUploagPhotoProt(mEmployeeId, mUploadFile, requestUrl);
                if (requestUrl.contains("updateCover")) {  //上传封面
                    requstUploadCoverPhoto(mEmployeeId, mUploadFile);
                } else if (requestUrl.contains("updateAvatar")) {   //上传头像
                    requstUploadHeadPhoto(mEmployeeId, mUploadFile);
                }
            }
        });
    }

    private void requstUploadCoverPhoto(String employeeId, File cover) {
        RequestParams params = new RequestParams();
        params.put(ConstantMain.EMPLOYEE_ID, mEmployeeId);
        try {
            params.put(ConstantMain.COVER, cover);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtils.e("上传封面", "错误了");
        }
        RestClient.builder()
                .raw(params)
                .url(ConstantMain.URL_STUDENT_COVER)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LogUtils.e("上传封面", "response="+response);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.showMessage(getContext(), msg);
                    }
                })
                .build()
                .post();
    }

    private void requstUploadHeadPhoto(String employeeId, File cover) {
        RequestParams params = new RequestParams();
        params.put(ConstantMain.EMPLOYEE_ID, mEmployeeId);
        try {
            params.put(ConstantMain.HEAD_PORTRAIT, cover);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtils.e("上传头像", "错误了");
        }
        RestClient.builder()
                .raw(params)
                .url(ConstantMain.URL_STUDENT_HEAD_IMG)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LogUtils.e("上传头像", "response="+response);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.showMessage(getContext(), msg);
                    }
                })
                .build()
                .post();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                uploadPicture();
            } else {
                ToastUtils.showMessage(getContext(), getString(R.string.no_permission_to_save_file));
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
