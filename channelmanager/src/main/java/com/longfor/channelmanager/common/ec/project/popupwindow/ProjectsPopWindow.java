package com.longfor.channelmanager.common.ec.project.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;

import com.longfor.channelmanager.R;

/**
 * @author: tongzhenhua
 * @date: 2018/1/8
 * @function:
 */
public class ProjectsPopWindow extends PopupWindow {
    private Context mContext;
    private ExpandableListView mListView;
    private ExpandProjectsAdapter mProjectsAdapter;
//    private final

    public ProjectsPopWindow(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_projects, null);
        mListView = contentView.findViewById(R.id.elv_projects);
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
