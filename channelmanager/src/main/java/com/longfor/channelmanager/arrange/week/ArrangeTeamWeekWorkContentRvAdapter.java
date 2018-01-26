package com.longfor.channelmanager.arrange.week;

import android.view.View;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longfor.channelmanager.R;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/25
 * @function:
 */

public class ArrangeTeamWeekWorkContentRvAdapter extends BaseSectionQuickAdapter<SectionArrangeWeek,ArrangeTeamWeekWorkContentRvAdapter.ViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public ArrangeTeamWeekWorkContentRvAdapter(int layoutResId, int sectionHeadResId, List<SectionArrangeWeek> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(ViewHolder helper, SectionArrangeWeek item) {
        helper.setText(R.id.tv_sticky_header,item.header);
    }

    @Override
    protected void convert(ViewHolder helper, SectionArrangeWeek item) {
        helper.setText(R.id.tv_name, item.t.isEmpty()?"":item.t.getEmployeeName());
        helper.setText(R.id.tv_avg_work,item.t.isEmpty()?"":item.t.getAvgWorks());
        if (item.t.isEmpty()){
            helper.setText(R.id.tv_free_times,helper.itemView.getContext().getString(R.string.please_select_trainee_to_work));
        }else {
            helper.setText(R.id.tv_free_times,item.t.getFreetimes());
        }
    }

    public class ViewHolder extends BaseViewHolder{
        public ViewHolder(View view) {
            super(view);
        }
    }
}
