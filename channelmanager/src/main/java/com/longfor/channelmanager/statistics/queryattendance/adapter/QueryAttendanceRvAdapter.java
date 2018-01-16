package com.longfor.channelmanager.statistics.queryattendance.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.view.checkincircleimageview.CheckInPhotoView;
import com.longfor.channelmanager.statistics.queryattendance.bean.CheckInListBean;
import com.longfor.channelmanager.statistics.queryattendance.constant.QueryAttendanceConstant;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;
import com.longfor.ui.recycler.MultipleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/9
 * @function:考勤查询的RecyclerView的数据适配器
 */

public class QueryAttendanceRvAdapter extends BaseRecyclerAdapter {
    private IQueryAttendanceClickPhotoListener mIQueryAttendanceClickPhotoListener;

    public QueryAttendanceRvAdapter(List<MultipleItemEntity> data, IQueryAttendanceClickPhotoListener listener) {
        super(data);
        mIQueryAttendanceClickPhotoListener = listener;
    }

    public static QueryAttendanceRvAdapter create(DataConverter dataConverter, IQueryAttendanceClickPhotoListener listener) {
        return new QueryAttendanceRvAdapter(dataConverter.convert(), listener);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        switch (item.getItemType()) {
            case QueryAttendanceConstant.QUERY_ATTENDANCE:
                CheckInListBean.DataBean.CheckinsBean emplyoeeCheckinsInfo = (CheckInListBean.DataBean.CheckinsBean) item.getField(MultipleFields.OBJECT);
                if (!TextUtils.isEmpty(emplyoeeCheckinsInfo.getEmployeeName())) {
                    helper.setText(R.id.tv_name, emplyoeeCheckinsInfo.getEmployeeName());
                }
                showWorkAddress(helper, emplyoeeCheckinsInfo.getWorkAddresses());
                showCheckInPhoto(helper, emplyoeeCheckinsInfo.getInnerCheckins());
                break;
        }
    }

    /**
     * 显示工作地点
     *
     * @param helper
     * @param workAdresses
     */
    private void showWorkAddress(MultipleViewHolder helper, List<String> workAdresses) {
        helper.setVisible(R.id.tv_address_morning, false);
        helper.setVisible(R.id.tv_address_noon, false);
        helper.setVisible(R.id.tv_address_night, false);
        List<TextView> workAddressView = new ArrayList<>();
        workAddressView.add((TextView) helper.getView(R.id.tv_address_morning));
        workAddressView.add((TextView) helper.getView(R.id.tv_address_noon));
        workAddressView.add((TextView) helper.getView(R.id.tv_address_night));
        if (workAdresses != null && workAdresses.size() > 0) {
            for (int i = 0; i < workAdresses.size(); i++) {
                if (!TextUtils.isEmpty(workAdresses.get(i))) {
                    workAddressView.get(i).setText(workAdresses.get(i));
                    workAddressView.get(i).setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 展示打卡照片
     *
     * @param helper
     * @param innerCheckins
     */
    private void showCheckInPhoto(MultipleViewHolder helper, List<CheckInListBean.DataBean.CheckinsBean.InnerCheckinsBean> innerCheckins) {
        helper.setVisible(R.id.rl_first_img_group, false);
        helper.setVisible(R.id.rl_second_img_group, false);
        if (innerCheckins == null || innerCheckins.size() == 0) {
            return;
        }
        //添加非空判断
        for (int i = 0; i < innerCheckins.size(); i++) {
            CheckInListBean.DataBean.CheckinsBean.InnerCheckinsBean checkins = innerCheckins.get(i);
            if (checkins != null) {
                int arriveStatus = checkins.getArriveStatus();
                int leaveStatus = checkins.getLeaveStatus();
                final String arriveImageUrl = checkins.getArriveImageUrl();
                String leaveImageUrl = checkins.getLeaveImageUrl();
                if (i == 0) {
                    helper.setVisible(R.id.rl_first_img_group, true);
                    initPhotoView((CheckInPhotoView) helper.getView(R.id.img_on_duty_first), arriveStatus, arriveImageUrl);
                    initPhotoView((CheckInPhotoView) helper.getView(R.id.img_off_duty_first), leaveStatus, leaveImageUrl);
                } else if (i == 1) {
                    helper.setVisible(R.id.rl_second_img_group, true);
                    initPhotoView((CheckInPhotoView) helper.getView(R.id.img_on_duty_second), arriveStatus, arriveImageUrl);
                    initPhotoView((CheckInPhotoView) helper.getView(R.id.img_off_duty_second), leaveStatus, leaveImageUrl);
                }
            }
        }
    }

    private void initPhotoView(final CheckInPhotoView photoView, int status, final String imageUrl) {
        photoView.setPhoto(status, imageUrl);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIQueryAttendanceClickPhotoListener != null && !TextUtils.isEmpty(imageUrl) && photoView.isPhotoClickable()) {
                    mIQueryAttendanceClickPhotoListener.onClickPhoto(imageUrl);
                }
            }
        });
    }

    @Override
    protected void init() {
        addItemType(QueryAttendanceConstant.QUERY_ATTENDANCE, R.layout.item_query_attendance);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }

    public interface IQueryAttendanceClickPhotoListener {
        void onClickPhoto(String imageUrl);
    }
}
