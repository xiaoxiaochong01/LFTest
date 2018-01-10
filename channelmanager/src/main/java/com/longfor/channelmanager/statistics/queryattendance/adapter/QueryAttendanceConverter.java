package com.longfor.channelmanager.statistics.queryattendance.adapter;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.statistics.queryattendance.bean.CheckInListBean;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/4
 * @function:考勤查询
 */

public class QueryAttendanceConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ArrayList<MultipleItemEntity> entities=new ArrayList<>();
        List<CheckInListBean.DataBean.CheckinsBean> checkins = JSON.parseObject(getJsonData(), CheckInListBean.class).getData().getCheckins();
        if (checkins!=null){
            for (int i = 0; i < checkins.size(); i++) {
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE,QueryAttendanceItemType.QUERY_ATTENDANCE)
                        .setField(MultipleFields.SPAN_SIZE,1)
                        .setField(MultipleFields.OBJECT,checkins.get(i))
                        .build();

                entities.add(entity);
            }
        }
        return entities;
    }
}
