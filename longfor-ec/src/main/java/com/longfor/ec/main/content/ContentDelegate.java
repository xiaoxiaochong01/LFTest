package com.longfor.ec.main.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.ISuccess;
import com.longfor.ec.R;

import java.util.List;

/**
 * Created by 傅令杰
 */

public class ContentDelegate extends LongForDelegate {

    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId = -1;
    private List<SectionBean> mData = null;

    private RecyclerView mRecyclerView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    public static ContentDelegate newInstance(int contentId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
//        return R.layout.delegate_list_content;
        return null;
    }

    private void initData() {
        RestClient.builder()
                .url("sort_content_list.php?contentId=" + mContentId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mData = new SectionDataConverter().convert(response);
//                        final SectionAdapter sectionAdapter =
//                                new SectionAdapter(R.layout.item_section_content,
//                                        R.layout.item_section_header, mData);
//                        mRecyclerView.setAdapter(sectionAdapter);
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
//        mRecyclerView = $(R.id.rv_list_content);
//        final StaggeredGridLayoutManager manager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(manager);
//        initData();
    }
}
