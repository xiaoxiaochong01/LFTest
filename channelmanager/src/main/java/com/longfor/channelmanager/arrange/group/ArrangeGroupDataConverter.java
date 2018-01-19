package com.longfor.channelmanager.arrange.group;

import com.alibaba.fastjson.JSON;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/19
 * @function:
 */
public class ArrangeGroupDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ArrangeGroupBean arrangeGroupBean = JSON.parseObject(getJsonData(), ArrangeGroupBean.class);
        ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        if(arrangeGroupBean != null) {
            List<ArrangeGroupBean.DataBean> dataBeans = arrangeGroupBean.getData();
            if(dataBeans != null) {
                for (ArrangeGroupBean.DataBean dataBean : dataBeans) {
                    final MultipleItemEntity entity = MultipleItemEntity.builder()
                            .setField(MultipleFields.ITEM_TYPE, ItemTypeArrangeGroup.ARRANGE_ITEM)
                            .setField(MultipleFields.SPAN_SIZE, 1)
                            .setField(MultipleFields.OBJECT, dataBean)
                            .build();
                    entities.add(entity);
                }
            }
        }
        return entities;
    }
}
