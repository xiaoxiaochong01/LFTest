package com.longfor.channelmanager.main.index.mine;

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

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.ec.h5.WebviewDelegate;
import com.longfor.channelmanager.common.dialog.DialogWithYesOrNo;
import com.longfor.channelmanager.common.dialog.listener.OnDialogConfimClickListener;
import com.longfor.channelmanager.common.net.BaseSuccessListener;
import com.longfor.channelmanager.common.utils.ImageLoader;
import com.longfor.channelmanager.common.view.popupwindow.ChooseImgPopWindow;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.channelmanager.mine.aboutus.AboutUsDelegate;
import com.longfor.channelmanager.mine.recommend.RecommendDelegate;
import com.longfor.channelmanager.mine.setting.SettingDelegate;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.delegates.bottomreplace.BottomItemDelegate;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.IError;
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
 * @function: 我的界面
 */
public class IndexMineDelegate extends BottomItemDelegate {
    
    @BindView(R2.id.img_title_background)
    AppCompatImageView imgTopBg;
    @BindView(R2.id.img_uer_head_portrait)
    CircleImageView imgHead;
    @BindView(R2.id.tv_user_name)
    AppCompatTextView tvUserName;

    private ChooseImgPopWindow chooseImgPopWindow;
    private DialogWithYesOrNo mDialogHintPermission;
    private File mUploadFile = null;
    private boolean mIsUpdateCover;
    private String mEmployeeId;
    private String strRecommendCode = "";

    @Override
    public Object setLayout() {
        return R.layout.delegate_index_mine;
    }

    @OnClick(R2.id.img_title_background)
    void onTopBackgroundClick() {
        checkPermission(true);
    }
    @OnClick(R2.id.img_uer_head_portrait)
    void onUserHeadClick() {
        checkPermission(false);
    }
    @OnClick(R2.id.rl_about_us)
    void onAboutUsClick() {
       jumToDelegateFromParent(new AboutUsDelegate(), ConstantMine.JUMB_TO.ABOUT_US);
    }
    @OnClick(R2.id.rl_recommend_code)
    void onRecommendClick() {
        jumToDelegateFromParent(new RecommendDelegate(), ConstantMine.JUMB_TO.RECOMMEND);
    }
    @OnClick(R2.id.rl_feed_back)
    void onFeedBackClick() {
        jumToDelegateFromParent(new WebviewDelegate(), ConstantMine.JUMB_TO.FEED_BACK);
    }
    @OnClick(R2.id.rl_setting)
    void onSettingClick() {
        jumToDelegateFromParent(new SettingDelegate(), ConstantMine.JUMB_TO.SETTING);
    }

    private void jumToDelegateFromParent(LongForDelegate longForDelegate, ConstantMine.JUMB_TO type) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE_LEFT_TEXT, getResources().getString(R.string.mine_title));
        switch (type) {
            case FEED_BACK:
                bundle.putString(Constant.WEB_URL,"http://www.longfor.com");
                bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.feed_back_title));
                break;
            case RECOMMEND:
                bundle.putString(ConstantMine.RECOMMEND_CODE, strRecommendCode);
                break;
        }
        longForDelegate.setArguments(bundle);
        getParentDelegate().start(longForDelegate);
    }

    private void checkPermission(boolean mIsUpdateCover) {
        this.mIsUpdateCover = mIsUpdateCover;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            mDialogHintPermission.showDialog();
        } else {
            uploadPicture();
        }
    }
    private void requestWritePermissions() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQUEST_WRITE_CODE);
    }

    private void uploadPicture() {
        if (mIsUpdateCover) {
            showPopUpWindow(ConstantMine.TAKE_COVER_REQUEST_CODE, ConstantMine.SELECT_COVER_REQUEST_CODE);
        } else {
            showPopUpWindow(ConstantMine.TAKE_PORTRAIT_REQUEST_CODE, ConstantMine.SELECT_PORTRAIT_REQUEST_CODE);
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
                .url(ConstantMine.URL_TRAINEE_GET_PROFILE)
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        if(isAdded()) {
                            UserInfoBean userInfoBean = JSON.parseObject(response, UserInfoBean.class);
                            UserInfoBean.DataBean dataBean = userInfoBean.getData();
                            if(dataBean != null) {
                                tvUserName.setText(userInfoBean.getData().getEmployeeName());
                                ImageLoader.display(getContext(), imgHead, dataBean.getHeadPortraitUrl());
                                ImageLoader.display(getContext(), imgTopBg, dataBean.getCoverUrl());
                                strRecommendCode = dataBean.getRecommendationCode();
                            }

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
            case ConstantMine.TAKE_PORTRAIT_REQUEST_CODE:       //头像-拍照
            case ConstantMine.SELECT_PORTRAIT_REQUEST_CODE:       //头像-选择
                if (data != null) {
                    compressAndUpload(data, ConstantMine.URL_STUDENT_HEAD_IMG, false);
                }
                break;
            case ConstantMine.TAKE_COVER_REQUEST_CODE:         //封面-拍照
            case ConstantMine.SELECT_COVER_REQUEST_CODE:          //封面-选择
                if (data != null) {
                    compressAndUpload(data, ConstantMine.URL_STUDENT_COVER, true);
                }
                break;
        }
    }

    /**
     * 压缩上传图片
     */
    public void compressAndUpload(Intent data, final String requestUrl, final boolean isCover) {
        final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
        UiUtil.runInThread(new Runnable() {
            @Override
            public void run() {
                mUploadFile = PicCompressUtils.getSmallPicFile(getContext(), images.get(0).path);
                if (mUploadFile == null) {
                    ToastUtils.showMessage(getContext(), getString(R.string.picture_illegal));
                    return;
                }
                requstUploadPhoto(requestUrl,mUploadFile,isCover);
            }
        });
    }

    private void requstUploadPhoto(String url, File file, final boolean isCover) {
        RequestParams params = new RequestParams();
        params.put(ConstantMine.EMPLOYEE_ID, mEmployeeId);
        try {
            if(isCover) {
                params.put(ConstantMine.COVER, file);
            }
            else {
                params.put(ConstantMine.HEAD_PORTRAIT, file);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtils.e("上传图片", "错误了");
        }
        RestClient.builder()
                .raw(params)
                .url(url)
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        if(isCover) {
                            CoverUploadBean coverUploadBean = JSON.parseObject(response, CoverUploadBean.class);
                            CoverUploadBean.DataBean dataBean = coverUploadBean.getData();
                            if(dataBean != null) {
                                ImageLoader.display(getContext(), imgTopBg, dataBean.getImageUrl());
                            }
                            ToastUtils.showMessage(getContext(),coverUploadBean.getMessage());
                        }
                        else {
                            AvatarUploadBean avatarUploadBean = JSON.parseObject(response, AvatarUploadBean.class);
                            AvatarUploadBean.DataBean dataBean = avatarUploadBean.getData();
                            if(dataBean != null) {
                                ImageLoader.display(getContext(), imgHead, dataBean.getAvatarUrl());
                            }
                            ToastUtils.showMessage(getContext(),avatarUploadBean.getMessage());
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.REQUEST_WRITE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                uploadPicture();
            } else {
                ToastUtils.showMessage(getContext(), getString(R.string.no_permission_to_save_file));
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
