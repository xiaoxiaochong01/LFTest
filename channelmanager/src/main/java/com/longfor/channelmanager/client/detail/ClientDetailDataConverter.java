package com.longfor.channelmanager.client.detail;

import com.alibaba.fastjson.JSON;
import com.longfor.ui.recycler.DataConverter;
import com.longfor.ui.recycler.MultipleFields;
import com.longfor.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/16
 * @function:
 */
public class ClientDetailDataConverter extends DataConverter {
    private boolean isData = false;
    private boolean isShow = false;
    private IClientDetail CLIENT_DETAIL;
    private boolean isFirstConvert = true;

    public ClientDetailDataConverter(IClientDetail iClientDetail) {
        super();
        CLIENT_DETAIL = iClientDetail;
    }

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ClientDetailBean detailBean = JSON.parseObject(getJsonData(), ClientDetailBean.class);
        if(isFirstConvert) {
            isFirstConvert = false;
            if(CLIENT_DETAIL != null) {
                CLIENT_DETAIL.fillLayout(detailBean.getData());
            }
        }
        ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        if(isData) {
            ClientDetailBean.DataBean.CustomerBean customerBean = detailBean.getData().getCustomer();
            if (customerBean != null) {
                entities.add(getItemEntitySpace());
                entities.add(getItemEntityNormal(ConstantClientDetail.PHONE_NUM, customerBean.getPhoneNum()));
                String gener = customerBean.getUserSex().equals(ConstantClientDetail.GENDER_MALE) ? ConstantClientDetail.MALE : ConstantClientDetail.LADY;
                entities.add(getItemEntityNormal(ConstantClientDetail.USER_SEX, gener));
                entities.add(getItemEntityNormal(ConstantClientDetail.AGE_STAGE, customerBean.getAgeStage()));
                entities.add(getItemEntitySpace());
                entities.add(getItemEntityText(customerBean.getRequirements(), ItemTypeClientDetail.TEXT_CONTENT));
                entities.add(getItemEntitySpace());
                if(!isShow) {
                    entities.add(getItemEntityText(ConstantClientDetail.SHOW, ItemTypeClientDetail.TEXT_SHOW));
                    return entities;
                }
                entities.add(getItemEntityNormal(ConstantClientDetail.HOUSE_PURPOSE, customerBean.getHousePurpose()));
                entities.add(getItemEntityNormal(ConstantClientDetail.INTENT_TRADE, customerBean.getIntentTrade()));
                entities.add(getItemEntityNormal(ConstantClientDetail.INTENT_AREA, customerBean.getArea()));
                entities.add(getItemEntityNormal(ConstantClientDetail.ATTENTION_POINT, customerBean.getAttentionPoint()));
                entities.add(getItemEntityNormal(ConstantClientDetail.MAIN_RESISTANCE, customerBean.getMainResistance()));
                entities.add(getItemEntityNormal(ConstantClientDetail.SCHOOL_REQUIREMENT, customerBean.getSchoolRequirement()));
                entities.add(getItemEntityNormal(ConstantClientDetail.COMPETITIVE_BUILDING, customerBean.getCompetitiveBuilding()));
                entities.add(getItemEntitySpace());
                entities.add(getItemEntityNormal(ConstantClientDetail.FAMILY_STRUCTURE, customerBean.getFamilyStructure()));
                entities.add(getItemEntityNormal(ConstantClientDetail.MARRIAGE_STATUS, customerBean.getMarriageStatus()));
                entities.add(getItemEntityNormal(ConstantClientDetail.CHILD_STATUS, customerBean.getChildStatus()));
                entities.add(getItemEntityNormal(ConstantClientDetail.WORK_INDUSTRY, customerBean.getWorkIndustry()));
                entities.add(getItemEntityNormal(ConstantClientDetail.WORK_POSITION, customerBean.getWorkPosition()));
                entities.add(getItemEntitySpace());
                entities.add(getItemEntityNormal(ConstantClientDetail.CREATE_DATE, customerBean.getCreateDate()));
                entities.add(getItemEntityNormal(ConstantClientDetail.FIRST_VISIT_DATE, customerBean.getFirstVisitDate()));
                entities.add(getItemEntityNormal(ConstantClientDetail.LAST_FOLLOW_TIME, customerBean.getLastFollowTime()));
                entities.add(getItemEntityNormal(ConstantClientDetail.LAST_FOLLOW_TYPE, customerBean.getLastFollowType()));
                entities.add(getItemEntityNormal(ConstantClientDetail.LAST_FOLLOW_PERSON, customerBean.getLastFollowPerson()));
                entities.add(getItemEntityNormal(ConstantClientDetail.VISITING_COUNT, customerBean.getVisitingCount()));
                entities.add(getItemEntitySpace());
                entities.add(getItemEntityNormal(ConstantClientDetail.IS_LONGFOR_CUSTOMER, customerBean.getIsLongforCustomer()));
                entities.add(getItemEntityNormal(ConstantClientDetail.IS_STRANGER, customerBean.getIsStranger()));
                entities.add(getItemEntityNormal(ConstantClientDetail.NEVER_CONTACT, customerBean.getNeverContact()));
                entities.add(getItemEntitySpace());
                entities.add(getItemEntityText(ConstantClientDetail.HIDEN, ItemTypeClientDetail.TEXT_HIDEN));
                return entities;
            }
            else {
                return entities;
            }
        }
        else {
            List<ClientDetailBean.DataBean.FollowsBean> followsBeans = detailBean.getData().getFollows();
            if (followsBeans != null && followsBeans.size() > 0) {
                int tag = 1;
                for (ClientDetailBean.DataBean.FollowsBean followsBean : followsBeans) {
                    final MultipleItemEntity entity = MultipleItemEntity.builder()
                            .setField(MultipleFields.ITEM_TYPE, ItemTypeClientDetail.FOLLOW)
                            .setField(MultipleFields.SPAN_SIZE, 1)
                            .setField(MultipleFields.OBJECT, followsBean)
                            .setField(MultipleFields.TAG, tag)
                            .build();
                    entities.add(entity);
                    tag = 0;
                }
            }
            return entities;
        }
    }

    public ArrayList<MultipleItemEntity> getFollowEntitys() {
        isData = false;
        return convert();
    }

    public ArrayList<MultipleItemEntity> getDataEntitysShow() {
        isData = true;
        isShow = true;
        return convert();
    }

    public ArrayList<MultipleItemEntity> getDataEntitysHide() {
        isData = true;
        isShow = false;
        return convert();
    }

    private MultipleItemEntity getItemEntityNormal(String title, String content) {
        ClientDateItemBean itemBean = new ClientDateItemBean();
        itemBean.setTitle(title);
        itemBean.setContent(content);
        final MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemTypeClientDetail.TEXT_NORMAL)
                .setField(MultipleFields.SPAN_SIZE, 1)
                .setField(MultipleFields.OBJECT, itemBean)
                .build();
        return entity;
    }

    private MultipleItemEntity getItemEntityText(String content, int itemType) {
        final MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, itemType)
                .setField(MultipleFields.SPAN_SIZE, 1)
                .setField(MultipleFields.TEXT, content)
                .build();
        return entity;
    }

    private MultipleItemEntity getItemEntitySpace() {
        final MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, ItemTypeClientDetail.SPACE)
                .setField(MultipleFields.SPAN_SIZE, 1)
                .build();
        return entity;
    }
}
