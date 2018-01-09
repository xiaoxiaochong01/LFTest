package com.longfor.channelmanager.common.ec.project.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.ec.project.ProjectsDataBean;
import com.longfor.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/8
 * @function:
 */
public class ExpandProjectsAdapter extends BaseExpandableListAdapter {
    private List<ProjectsDataBean.DataBean> dataBeans;
    private Context mContext;
    private String mCurrentProjectId;

    public ExpandProjectsAdapter(Context context,List<ProjectsDataBean.DataBean> dataBeans) {
        this.dataBeans = dataBeans;
        mContext = context;
    }

    public void setmCurrentProjectId(String projectId) {
        mCurrentProjectId = projectId;
        notifyDataSetChanged();
    }
    @Override
    public int getGroupCount() {
        return dataBeans.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<ProjectsDataBean.DataBean.ProjectsBean> projectsBeans = dataBeans.get(groupPosition).getProjects();
        return projectsBeans == null ? 0 : projectsBeans.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataBeans.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataBeans.get(groupPosition).getProjects().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        MultipleViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_projects_group, null);
            viewHolder = MultipleViewHolder.creat(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (MultipleViewHolder) convertView.getTag();
        }
        viewHolder.setText(R.id.tv_city_name, dataBeans.get(groupPosition).getAreaName());
        viewHolder.setImageResource(R.id.img_arrow_right, isExpanded ? R.mipmap.elv_arrow_item_down : R.mipmap.mine_more_icon);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MultipleViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_projects_child, null);
            viewHolder = MultipleViewHolder.creat(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (MultipleViewHolder) convertView.getTag();
        }
        ProjectsDataBean.DataBean.ProjectsBean projectsBean = dataBeans.get(groupPosition).getProjects().get(childPosition);
        viewHolder.setText(R.id.tv_project_name, projectsBean.getProjectName());
        viewHolder.setBackgroundRes(R.id.tv_project_name, projectsBean.getProjectId().equals(mCurrentProjectId)?R.color.gray_e:R.color.gray_f8);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
