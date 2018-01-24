package com.longfor.channelmanager.common.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.longfor.channelmanager.R;
import com.longfor.ui.recycler.BaseDecoration;

import java.util.List;

/**
 * @author: gaomei
 * @date: 2018/1/24
 * @function:
 */

public class BottomPopupWindow extends PopupWindow implements View.OnClickListener {
    private List<Data> mDataList;
    private OnItemClickListener mOnItemClickListener;

    public BottomPopupWindow(Context context, List<Data> items, OnItemClickListener onItemClickListener) {
        super(context);
        mDataList = items;
        mOnItemClickListener = onItemClickListener;
        View view = View.inflate(context, R.layout.popup_window_bottom, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(false);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(dw);
        initView(view);
    }

    private void initView(View view) {
        RecyclerView rvPopupWindow = (RecyclerView) view.findViewById(R.id.rv_popup_window);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 1);
        rvPopupWindow.setLayoutManager(layoutManager);
        if (view.getContext() != null) {
            rvPopupWindow.addItemDecoration(BaseDecoration.creat(ContextCompat.getColor(view.getContext(), com.longfor.ec.R.color.app_background), 1));
        }
        BottomPopupWindowRvAdapter adapter = new BottomPopupWindowRvAdapter();
        rvPopupWindow.setAdapter(adapter);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);
    }

    public void showPopupWindow(View parent) {
        if (isShowing()) {
            dismiss();
        } else {
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                if (isShowing()) {
                    dismiss();
                }
                break;
        }
    }

    public class BottomPopupWindowRvAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.item_simple_text, null);
            ViewHolder viewHolder = new ViewHolder(view);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            ((ViewHolder) holder).mTvSimpleText.setText(mDataList.get(position).getTitle());
            holder.mTvSimpleText.setTextColor(mDataList.get(position).isSelected() ?
                    ((ViewHolder) holder).itemView.getContext().getResources().getColor(R.color.orange) :
                    ((ViewHolder) holder).itemView.getContext().getResources().getColor(R.color.gray_9));
            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mDataList.size(); i++) {
                        mDataList.get(i).setSelected(position == i ? true : false);
                    }
                    notifyDataSetChanged();
                    BottomPopupWindow.this.dismiss();
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(mDataList.get(position));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvSimpleText;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvSimpleText = ((TextView) itemView.findViewById(R.id.tv_simple_text));
        }
    }

    public static class Data {
        private String id;
        private String title;
        private boolean selected;

        public Data(String id, String title) {
            this.id = id;
            this.title = title;
            this.selected = false;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Data data);
    }
}
