package com.longfor.channelmanager.recordlist.converter;

import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.recordlist.bean.RecordListBean;
import com.longfor.channelmanager.recordlist.constant.RecordListConstant;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/23
 * @function:
 */

public class RecordListConverter extends DataConverter {

    private OnGetNetDataListener mOnGetNetDataListener;

    public RecordListConverter(OnGetNetDataListener onGetNetDataListener) {
        mOnGetNetDataListener = onGetNetDataListener;
    }

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        RecordListBean.DataBean selfDataBean = null;
        RecordListBean recordListBean = JSON.parseObject(getJsonData(), RecordListBean.class);
        if (recordListBean != null && recordListBean.getData() != null && recordListBean.getData().size() > 0) {
            List<RecordListBean.DataBean> dataBeanList = recordListBean.getData();
            selfDataBean = dataBeanList.get(0);
            for (int i = 0; i < dataBeanList.size(); i++) {
                MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(RecordListConstant.NAME, dataBeanList.get(i).getName())
                        .setField(RecordListConstant.PORTRAIT, dataBeanList.get(i).getAvatar())
                        .setField(RecordListConstant.RANK_POSITION, dataBeanList.get(i).getPosition())
                        .setField(RecordListConstant.RESULT, dataBeanList.get(i).getResult())
                        .setField(MultipleFields.SPAN_SIZE,1)
                        .setField(MultipleFields.ITEM_TYPE,RecordListConstant.ITEM_TYPE)
                        .build();
                ENTITIES.add(entity);
            }
        }
        if (mOnGetNetDataListener != null) {
            mOnGetNetDataListener.onGetNetData(selfDataBean);
        }
        return ENTITIES;
    }

    public interface OnGetNetDataListener {
        void onGetNetData(@Nullable RecordListBean.DataBean dataBean);
    }
}
