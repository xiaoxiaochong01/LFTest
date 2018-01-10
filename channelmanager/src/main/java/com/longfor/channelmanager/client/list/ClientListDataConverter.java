package com.longfor.channelmanager.client.list;

import com.longfor.channelmanager.main.index.home.ItemTypeHome;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * @author: tongzhenhua
 * @date: 2018/1/10
 * @function:
 */
public class ClientListDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemTypeClient.TEXTS)
                .setField(MultipleFields.SPAN_SIZE, 1)
                .build();
        entities.add(entity);
        return entities;
    }
}
