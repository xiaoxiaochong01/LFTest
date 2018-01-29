package com.longfor.channelmanager.arrange.week;

import android.view.View;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longfor.channelmanager.R;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/29
 * @function:
 */

public class ArrangeWorkBackUpRvAdapter extends BaseSectionQuickAdapter<SectionArrangeWorkBackUp,ArrangeWorkBackUpRvAdapter.ViewHolder>{
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public ArrangeWorkBackUpRvAdapter(int layoutResId, int sectionHeadResId, List<SectionArrangeWorkBackUp> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(ArrangeWorkBackUpRvAdapter.ViewHolder helper, SectionArrangeWorkBackUp item) {
        helper.setText(R.id.tv_sticky_header,item.header);
    }

    @Override
    protected void convert(ArrangeWorkBackUpRvAdapter.ViewHolder helper, SectionArrangeWorkBackUp item) {
        helper.setText(R.id.tv_name,item.t.getEmployeeName());
        helper.setText(R.id.tv_avg_work,item.t.getAvgWorks());
        helper.setText(R.id.tv_free_times,item.t.getFreetimes());
        helper.getView(R.id.iv_right).setVisibility(View.GONE);
    }

    public class ViewHolder extends BaseViewHolder{
        public ViewHolder(View view) {
            super(view);
        }
    }
}
