package com.longfor.channelmanager.arrange.group;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.longfor.channelmanager.arrange.ArrangeConstant;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.ec.baseadapter.BaseRefreshHandler;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.ui.recycler.BaseRecyclerAdapter;
import com.longfor.ui.recycler.DataConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: tongzhenhua
 * @date: 2018/1/19
 * @function:
 */
public class ArrangeGroupRefreshHandler extends BaseRefreshHandler {
    private IArrangeGroup I_ARRANGE_GROUP = null;

    public ArrangeGroupRefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT, RecyclerView RECYCLERVIEW, DataConverter CONVERTER, IArrangeGroup I_ARRANGE_GROUP) {
        super(REFRESH_LAYOUT, RECYCLERVIEW, CONVERTER);
        this.I_ARRANGE_GROUP = I_ARRANGE_GROUP;
    }

    public static ArrangeGroupRefreshHandler create(SwipeRefreshLayout REFRESH_LAYOUT, RecyclerView RECYCLERVIEW, DataConverter CONVERTER, IArrangeGroup I_ARRANGE_GROUP) {
        return new ArrangeGroupRefreshHandler(REFRESH_LAYOUT, RECYCLERVIEW, CONVERTER, I_ARRANGE_GROUP);
    }
    @Override
    public String getUrl() {
        return ArrangeConstant.URL_GET_ARRANGE_GROUP;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMPLOYEE_ID, DatabaseManager.getEmployeeId());
        params.put(Constant.PROJECT_ID, DatabaseManager.getProjectId());
        return params;
    }

    @Override
    public int getPageSize() {
        return getDefaultPageSize();
    }

    @Override
    public int getTotals(String response) {
        return 0;
    }

    @Override
    public BaseRecyclerAdapter getAdapter(DataConverter converter) {
        return ArrangeGroupRecyclerAdapter.create(converter, I_ARRANGE_GROUP);
    }

    @Override
    public BaseRefreshHandler updateParams(String... params) {
        return this;
    }
}
