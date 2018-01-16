package com.longfor.channelmanager.common.view.checkincircleimageview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.utils.ImageLoader;
import com.longfor.ui.view.circleimageview.CircleImageView;


/**
 * @author: gaomei
 * @date: 2017/12/11
 * @function: 考勤
 */

public class CheckInPhotoView extends RelativeLayout {

    public CircleImageView mImgCheckInPhoto;
    public ImageView mImgException;
    public RelativeLayout mRlCheckIn;

    public CheckInPhotoView(Context context) {
        this(context, null);
    }

    public CheckInPhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckInPhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.check_in_photo_layout, null);
        initView(view);
        addView(view);
    }

    private void initView(View view) {
        mRlCheckIn = view.findViewById(R.id.rl_check_in);
        mImgCheckInPhoto = view.findViewById(R.id.img_check_in_photo);
        mImgException = view.findViewById(R.id.img_exception);
    }

    public void setPhoto(int status, final String url) {
        setClickable(true);
        mImgCheckInPhoto.setVisibility(VISIBLE);
        mImgException.setVisibility(VISIBLE);
        if (url != null && !TextUtils.isEmpty(url)) {
            ImageLoader.display(getContext(), mImgCheckInPhoto, url);
        }
        switch (status) {
            case 0://打卡正常
                mImgException.setVisibility(GONE);
                break;
            case 1://迟到
                break;
            case 2://早退
                break;
            default://未打卡
                setClickable(false);
                mImgCheckInPhoto.setVisibility(GONE);
                mImgException.setVisibility(GONE);
                break;
        }
    }

    public boolean isPhotoClickable(){
        return isClickable();
    }
}
