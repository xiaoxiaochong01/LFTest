package com.longfor.channelmanager.arrange.week;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author: gaomei
 * @date: 2018/1/29
 * @function:
 */

public class SectionArrangeWorkBackUp extends SectionEntity<ArrangeWorkBackUpBean.DataBean.MembersBean> {
    public SectionArrangeWorkBackUp(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionArrangeWorkBackUp(ArrangeWorkBackUpBean.DataBean.MembersBean membersBean) {
        super(membersBean);
    }
}
