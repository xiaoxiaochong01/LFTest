package com.longfor.channelmanager.teamcampaign;


import java.util.Comparator;

/**
 * @author: gaomei
 * @date: 2017/11/2
 * @function: 团队作战按排名排序
 */

public class TeamCampaignComparator implements Comparator<TeamCampaignBean.DataBean> {
    @Override
    public int compare(TeamCampaignBean.DataBean dataBean, TeamCampaignBean.DataBean t1) {
        return t1.getPosition()-dataBean.getPosition();
    }
}
