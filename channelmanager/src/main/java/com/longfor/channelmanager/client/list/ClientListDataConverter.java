package com.longfor.channelmanager.client.list;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.main.index.home.ItemTypeHome;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/10
 * @function:
 */
public class ClientListDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ClientListDataBean dataBean = JSON.parseObject(getJsonData(), ClientListDataBean.class);
        List<ClientListDataBean.DataBean.CustomersBean> customersBeans = dataBean.getData().getCustomers();
        ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        for(ClientListDataBean.DataBean.CustomersBean customersBean : customersBeans) {

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemTypeClient.TEXTS)
                    .setField(MultipleFields.SPAN_SIZE, 1)
                    .setField(MultipleFields.OBJECT, customersBean)
                    .build();
            entities.add(entity);
        }
        return entities;
    }
}
