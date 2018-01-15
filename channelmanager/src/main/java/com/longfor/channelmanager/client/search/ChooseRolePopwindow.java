package com.longfor.channelmanager.client.search;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;

import com.longfor.channelmanager.R;
import com.longfor.core.utils.UI.ScreenUtil;
import com.longfor.core.utils.UI.UiUtil;

import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/12
 * @function:
 */
public class ChooseRolePopwindow extends PopupWindow {
    private LayoutInflater inflater;
    private ListViewCompat mListView;
    private List<String> list;

    public ChooseRolePopwindow(Context context, List<String> list, AdapterView.OnItemClickListener clickListener) {
        super(context);
        inflater=LayoutInflater.from(context);
        this.list=list;
        init(context, clickListener);
    }

    private void init(Context context, AdapterView.OnItemClickListener clickListener){
        View view = inflater.inflate(R.layout.popwindow_client_choose_role, null);
        setContentView(view);
        setWidth(ScreenUtil.dip2px(context, 88));
        setHeight(ScreenUtil.dip2px(context, 130));
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = view.findViewById(R.id.lv_popWindow);
        mListView.setAdapter(new MyAdapter());
        mListView.setOnItemClickListener(clickListener);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                holder=new ViewHolder();
                convertView=inflater.inflate(R.layout.popwindow_client_choose_role_item, null);
                holder.tvName= convertView.findViewById(R.id.tv_item_show);
                convertView.setTag(holder);
            }else{
                holder=(ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(getItem(position).toString());
            return convertView;
        }
    }

    private class ViewHolder{
        private AppCompatTextView tvName;
    }
}
