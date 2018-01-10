package com.longfor.channelmanager.statistics.queryattendance.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.utils.ImageLoader;
import com.longfor.channelmanager.statistics.queryattendance.constants.ConstantQueryAttendance;
import com.longfor.core.delegates.LongForDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: gaomei
 * @date: 2018/1/10
 * @function:大图
 */

public class LargePhotoDelegate extends LongForDelegate {
    @BindView(R2.id.iv_large_photo)
    AppCompatImageView mIvLargePhoto;

    public static LargePhotoDelegate getInstance(String imgUrl){
        LargePhotoDelegate delegate=new LargePhotoDelegate();
        Bundle bundle=new Bundle();
        bundle.putString(ConstantQueryAttendance.IMG_URL,imgUrl);
        delegate.setArguments(bundle);
        return delegate;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        getSupportDelegate().set
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_large_photo;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        String imgUrl = getArguments().getString(ConstantQueryAttendance.IMG_URL);
        ImageLoader.display(getContext(),mIvLargePhoto,imgUrl);
    }

    @OnClick(R.id.iv_large_photo)
    public void onViewClicked() {
        getSupportDelegate().pop();
    }
}
