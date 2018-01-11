package com.longfor.ui.refresh;

/**
 * Created by zhanghaitao1 on 2018/1/3.
 */

public final class PagingBean {
    //当前是第几页
    private int mPageIndex = 1;
    //总数据条数
    private int mTotal = 0;
    //一页一共显示几条数据
    private int mPageSize  = 0;
    //当前一共显示几条数据
    private int mCurrentCount = 0;
    //加载延迟
    private int mDelayed = 0;

    public int getPageIndex() {
        return mPageIndex;
    }

    public PagingBean setPageIndex(int mPageIndex) {
        this.mPageIndex = mPageIndex;
        return this;
    }

    public int getTotal() {
        return mTotal;
    }

    public PagingBean setTotal(int mTotal) {
        this.mTotal = mTotal;
        return this;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public PagingBean setPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
        return this;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public PagingBean setCurrentCount(int mCurrentCount) {
        this.mCurrentCount = mCurrentCount;
        return this;
    }
    public PagingBean addCurrentCount() {
        this.mCurrentCount += mPageSize;
        return this;
    }
    public int getmDelayed() {
        return mDelayed;
    }

    public PagingBean setDelayed(int mDelayed) {
        this.mDelayed = mDelayed;
        return this;
    }

    public PagingBean addIndex(){
        mPageIndex++;
        return this;
    }

    public PagingBean resetPageIndex() {
        mPageIndex=1;
        return this;
    }
}
