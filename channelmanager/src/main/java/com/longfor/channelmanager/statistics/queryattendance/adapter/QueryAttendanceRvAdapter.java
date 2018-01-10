package com.longfor.channelmanager.statistics.queryattendance.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.view.checkincircleimageview.CheckInPhotoView;
import com.longfor.channelmanager.statistics.queryattendance.bean.CheckInListBean;
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
    public QueryAttendanceRvAdapter(List<MultipleItemEntity> data) {
        super(data);
    }

    public static QueryAttendanceRvAdapter create(DataConverter dataConverter) {
        return new QueryAttendanceRvAdapter(dataConverter.convert());
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        switch (item.getItemType()) {
            case QueryAttendanceItemType.QUERY_ATTENDANCE:
                CheckInListBean.DataBean.CheckinsBean emplyoeeCheckinsInfo = (CheckInListBean.DataBean.CheckinsBean) item.getField(MultipleFields.OBJECT);
                if (!TextUtils.isEmpty(emplyoeeCheckinsInfo.getEmployeeName())){
                    helper.setText(R.id.tv_name,emplyoeeCheckinsInfo.getEmployeeName());
                }
                showWorkAddress(helper,emplyoeeCheckinsInfo.getWorkAddresses());
                showCheckInPhoto(helper,emplyoeeCheckinsInfo.getInnerCheckins());
                break;
        }
    }

    /**
     * 显示工作地点
     * @param helper
     * @param workAdresses
     */
    private void showWorkAddress(MultipleViewHolder helper, List<String> workAdresses) {
        helper.setVisible(R.id.tv_address_morning,false);
        helper.setVisible(R.id.tv_address_noon,false);
        helper.setVisible(R.id.tv_address_night,false);
        List<TextView> workAddressView=new ArrayList<>();
        workAddressView.add((TextView) helper.getView(R.id.tv_address_morning));
        workAddressView.add((TextView) helper.getView(R.id.tv_address_noon));
        workAddressView.add((TextView) helper.getView(R.id.tv_address_night));
        if (workAdresses!=null&&workAdresses.size()>0){
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
     * @param helper
     * @param innerCheckins
     */
    private void showCheckInPhoto(MultipleViewHolder helper, List<CheckInListBean.DataBean.CheckinsBean.InnerCheckinsBean> innerCheckins) {
        helper.setVisible(R.id.rl_first_img_group,false);
        helper.setVisible(R.id.rl_second_img_group,false);
        if (innerCheckins == null || innerCheckins.size() == 0) {
            return;
        }
        //添加非空判断
        for (int i = 0; i < innerCheckins.size(); i++) {
            CheckInListBean.DataBean.CheckinsBean.InnerCheckinsBean checkins = innerCheckins.get(i);
            if (checkins != null) {
                int arriveStatus = checkins.getArriveStatus();
                int leaveStatus = checkins.getLeaveStatus();
                String arriveImageUrl = checkins.getArriveImageUrl();
                String leaveImageUrl = checkins.getLeaveImageUrl();
                if (i == 0) {
                    helper.setVisible(R.id.rl_first_img_group,true);
                    ((CheckInPhotoView) helper.getView(R.id.img_on_duty_first)).setPhoto(arriveStatus, arriveImageUrl);
                    ((CheckInPhotoView) helper.getView(R.id.img_off_duty_first)).setPhoto(leaveStatus, leaveImageUrl);
                } else if (i == 1) {
                    helper.setVisible(R.id.rl_second_img_group,true);
                    ((CheckInPhotoView) helper.getView(R.id.img_on_duty_second)).setPhoto(arriveStatus,arriveImageUrl);
                    ((CheckInPhotoView) helper.getView(R.id.img_off_duty_second)).setPhoto(leaveStatus,leaveImageUrl);
                }
            }
        }
    }

    @Override
    protected void init() {
        addItemType(QueryAttendanceItemType.QUERY_ATTENDANCE, R.layout.item_query_attendance);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getItemType();
    }

    @Override
    public void onItemClick(int position) {

    }
}
