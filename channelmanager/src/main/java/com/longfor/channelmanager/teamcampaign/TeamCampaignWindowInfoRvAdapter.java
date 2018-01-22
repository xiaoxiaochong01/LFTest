package com.longfor.channelmanager.teamcampaign;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.view.GroupFightProgressBar;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2017/9/17
 * @function: 分组作战(作战地图)-数据适配器
 */

public class TeamCampaignWindowInfoRvAdapter extends RecyclerView.Adapter {
    List<TeamCampaignBean.DataBean.MissionsBean> mMissionsBeanList;

    public TeamCampaignWindowInfoRvAdapter(List<TeamCampaignBean.DataBean.MissionsBean> missionsBeanList) {
        mMissionsBeanList = missionsBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_team_campaign_window_info_layout, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        GroupAchievementRvViewHolder viewHolder = new GroupAchievementRvViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TeamCampaignBean.DataBean.MissionsBean missionsBean = mMissionsBeanList.get(position);
        int progress = 0;
        if (missionsBean.getTotals() != 0) {
            progress = missionsBean.getComplete() * 100 / missionsBean.getTotals() ;
        }
        ((GroupAchievementRvViewHolder) holder).mTvName.setText(missionsBean.getTitle());
        ((GroupAchievementRvViewHolder) holder).mProgressBar.setLeftText(String.valueOf(missionsBean.getComplete()));
        ((GroupAchievementRvViewHolder) holder).mProgressBar.setRightText(String.valueOf(missionsBean.getTotals()));
        ((GroupAchievementRvViewHolder) holder).mProgressBar.setProgress(progress);
    }

    @Override
    public int getItemCount() {
        return mMissionsBeanList == null ? 0 : mMissionsBeanList.size();
    }

    static class GroupAchievementRvViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        GroupFightProgressBar mProgressBar;

        GroupAchievementRvViewHolder(View view) {
            super(view);
            mTvName = view.findViewById(R.id.tv_name);
            mProgressBar = view.findViewById(R.id.progressBar);
        }
    }
}
