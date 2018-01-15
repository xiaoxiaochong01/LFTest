package com.longfor.channelmanager.statistics.checkinstatistics.converter;

import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * @author: gaomei
 * @date: 2018/1/15
 * @function:上岗统计
 */

public class CheckInStatisticsDataConverter extends DataConverter {
    private String mItemType;
    public CheckInStatisticsDataConverter(String itemType) {
        mItemType=itemType;
    }

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ArrayList<MultipleItemEntity> entities=new ArrayList<>();
        switch (mItemType) {

        }
        return entities;
    }
}
