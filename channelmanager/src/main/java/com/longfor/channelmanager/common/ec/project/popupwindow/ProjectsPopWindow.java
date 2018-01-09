package com.longfor.channelmanager.common.ec.project.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.longfor.channelmanager.R;
import com.longfor.channelmanager.common.ec.project.IProjectChange;
import com.longfor.channelmanager.common.ec.project.ProjectListHandler;
import com.longfor.channelmanager.common.ec.project.ProjectsDataBean;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.core.net.callback.ISuccess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/8
 * @function:
 */
public class ProjectsPopWindow extends PopupWindow implements ISuccess{
    private Context mContext;
    private ExpandableListView mListView;
    private RelativeLayout mRootView;
    private ExpandProjectsAdapter mProjectsAdapter;
    private ProjectListHandler mHandler;
    private final List<ProjectsDataBean.DataBean> dataBeans = new ArrayList<>();
    private IProjectChange PROJECT_CHANGE; // 切换项目成功后回调

    public ProjectsPopWindow(Context context, IProjectChange iProjectChange) {
        super(context);
        mContext = context;
        PROJECT_CHANGE = iProjectChange;
        initView();
    }

    public static ProjectsPopWindow create(Context context, IProjectChange iProjectChange) {
        return new ProjectsPopWindow(context, iProjectChange);
    }
    public void showPopWindow(View view) {
        showAsDropDown(view);
//        showAtLocation(view, Gravity., 0, 0);
    }

    private void initView() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_projects, null);
        mListView = contentView.findViewById(R.id.elv_projects);
        mRootView = contentView.findViewById(R.id.rl_root_view);
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ProjectsDataBean.DataBean.ProjectsBean projectsBean = dataBeans.get(groupPosition).getProjects().get(childPosition);
                if(PROJECT_CHANGE != null) {
                    PROJECT_CHANGE.changeSucess(projectsBean);
                }
                mProjectsAdapter.setmCurrentProjectId(projectsBean.getProjectId());
                mProjectsAdapter.notifyDataSetChanged();
                dismiss();
                return true;
            }
        });
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mProjectsAdapter = new ExpandProjectsAdapter(mContext, dataBeans);
        mProjectsAdapter.setmCurrentProjectId(DatabaseManager.getProjectId());
        mListView.setAdapter(mProjectsAdapter);
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable());
        mHandler = ProjectListHandler.create(mContext, this);
        mHandler.requestProjects();
    }

    @Override
    public void onSuccess(String response) {
        ProjectsDataBean projectsDataBean = JSON.parseObject(response, ProjectsDataBean.class);
        if(projectsDataBean != null) {
            dataBeans.clear();
            if(projectsDataBean.getData() != null) {
                dataBeans.addAll(projectsDataBean.getData());
            }
            mProjectsAdapter.notifyDataSetChanged();
        }
    }
}
