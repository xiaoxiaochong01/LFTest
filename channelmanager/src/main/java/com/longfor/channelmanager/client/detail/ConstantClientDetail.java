package com.longfor.channelmanager.client.detail;

/**
 * @author: tongzhenhua
 * @date: 2018/1/16
 * @function:
 */
public interface ConstantClientDetail {
    String PHONE_NUM = "手机号码";
    String USER_SEX = "性别";
    String AGE_STAGE = "年龄阶段";

    String REQUIREMENTS = "客户需求";

    String HOUSE_PURPOSE = "置业目的";
    String INTENT_TRADE = "意向业态";
    String INTENT_AREA = "意向面积";
    String ATTENTION_POINT = "关注要点";
    String MAIN_RESISTANCE = "主要抗性";
    String SCHOOL_REQUIREMENT = "学区需求";
    String COMPETITIVE_BUILDING = "竞品楼盘";

    String FAMILY_STRUCTURE = "家庭架构";
    String MARRIAGE_STATUS = "婚姻状态";
    String CHILD_STATUS = "小孩情况";
    String WORK_INDUSTRY = "工作行业";
    String WORK_POSITION = "工作职务";

    String CREATE_DATE = "客户创建时间";
    String FIRST_VISIT_DATE = "客户首访时间";
    String LAST_FOLLOW_TIME = "最后一次跟进日期";
    String LAST_FOLLOW_TYPE = "最后一次跟进方式";
    String LAST_FOLLOW_PERSON = "最后一次跟进人";
    String VISITING_COUNT = "到访次数";

    String IS_LONGFOR_CUSTOMER = "龙湖业主";
    String IS_STRANGER = "是否异客";
    String NEVER_CONTACT = "永不接触";

    String SHOW = "展开";
    String HIDEN = "收起";

    int FOLLOW_UP = 1;

    String CUSTOMER_ID = "customerId";
    String URL_TRAINEE_GETCUSTOMER_DETAILS = "customer/getCustomerDetails";    //客户详情查询接口
    String GENDER_MALE = "0";
    String GENDER_LADY = "1";
    String MALE = "男";
    String LADY = "女";
    int HAS_RELATED = 1; // 有关联

}
