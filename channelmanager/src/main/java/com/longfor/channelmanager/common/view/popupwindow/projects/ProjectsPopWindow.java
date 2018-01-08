package com.longfor.channelmanager.common.view.popupwindow.projects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.view.popupwindow.projects.adpater.ExpandProjectsAdapter;

/**
 * @author: tongzhenhua
 * @date: 2018/1/8
 * @function:
 */
public class ProjectsPopWindow extends PopupWindow {
    private Context mContext;
    private ExpandableListView mListView;
    private ExpandProjectsAdapter mProjectsAdapter;

    public ProjectsPopWindow(Context context) {
        super(context);
        mContext = context;
    }

    private void initView() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_projects, null);
        mListView = contentView.findViewById(R.id.elv_projects);
    }
}
