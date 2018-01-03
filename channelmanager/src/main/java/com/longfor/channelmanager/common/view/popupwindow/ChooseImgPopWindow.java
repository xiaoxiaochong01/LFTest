package com.longfor.channelmanager.common.view.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

import com.longfor.channelmanager.R;

/**
 * @author: tongzhenhua
 * @date: 2018/1/2
 * @function:
 */
public class ChooseImgPopWindow extends PopupWindow implements View.OnClickListener{
    private Context context;
    private Activity activity;
    private View mMenuView, topView;

    private View showParentView;

    private View cancle_layout;

    private AppCompatTextView mTv_select_photo;

    private AppCompatTextView mTv_take_photo;

    public ChooseImgPopWindow(Context context) {
        super(context);
        this.context = context;
        activity = (Activity) context;
        initPopWdSelectPhoneDialog(context);
    }

    /**
     * 初始化popUpwindow
     *
     * @param context
     */
    private void initPopWdSelectPhoneDialog(final Context context) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popup_widow_select_photo, null);
        topView = mMenuView.findViewById(R.id.pop_layout);
        cancle_layout = mMenuView.findViewById(R.id.cancle_layout);
        mTv_select_photo = mMenuView.findViewById(R.id.tv_select_photo);
        mTv_take_photo = mMenuView.findViewById(R.id.tv_take_photo);

        cancle_layout.setOnClickListener(this);
        mTv_select_photo.setOnClickListener(this);
        mTv_take_photo.setOnClickListener(this);

        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        this.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        showParentView = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    }

    //显示弹窗位置
    public void showPopWdByLocation() {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.push_bottom_in);
        topView.startAnimation(animation);
        this.showAtLocation(showParentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //����layout��PopupWindow����ʾ��λ��
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 取消
            case R.id.cancle_layout:
                dismiss();
                break;
            case R.id.tv_select_photo:
                // 从手机相册选择
                callBackImagePath.selectPhoto();
                dismiss();
                break;
            case R.id.tv_take_photo:
                //拍照
                callBackImagePath.takePhoto();
                dismiss();
                break;
        }
    }
    public interface OnCallBackImagePath{
        void takePhoto();
        void selectPhoto();
    }
    public OnCallBackImagePath callBackImagePath;

    public void setOnCallBackImagePath(OnCallBackImagePath onCallBackImagePath) {
        this.callBackImagePath = onCallBackImagePath;
    }
}
