package com.longfor.channelmanager.statistics.adapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.ItemType;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * @author: gaomei
 * @date: 2018/1/4
 * @function:考勤查询
 */

public class QueryAttendanceConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONObject("data").getJSONArray("checkins");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);

            final String employeeName = data.getString("employeeName");
            final JSONArray workAddresses= data.getJSONArray("workAddresses");
            JSONArray innerCheckins = data.getJSONArray("innerCheckins");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,ItemType.QUERY_ATTENDANCE)
                    .setField(MultipleFields.EMPLOYEE_NAME,employeeName)
                    .setField(MultipleFields.WORK_ADDRESSES,workAddresses)
                    .setField(MultipleFields.INNER_CHECK_INS,innerCheckins)
                    .build();

            ENTITIES.add(entity);

        }

        return ENTITIES;
    }
}
