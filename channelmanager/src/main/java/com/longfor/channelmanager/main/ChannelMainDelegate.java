package com.longfor.channelmanager.main;

import com.longfor.channelmanager.R;
import com.longfor.channelmanager.main.index.home.IndexHomeDelegate;
import com.longfor.channelmanager.main.index.mine.IndexMineDelegate;
import com.longfor.channelmanager.main.index.statistics.IndexStatisticsDelegate;
import com.longfor.core.delegates.bottomreplace.BaseBottomDelegate;
import com.longfor.core.delegates.bottomreplace.BottomItemDelegate;
import com.longfor.core.delegates.bottomreplace.BottomTabBean;
import com.longfor.core.delegates.bottomreplace.ItemBuilder;

import java.util.LinkedHashMap;

/**
 * @author: tongzhenhua
 * @date: 2017/12/28
 * @function: 龙策主操作界面
 */
public class ChannelMainDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean(R.drawable.index_home_tab_selector,"首页"), new IndexHomeDelegate());
        items.put(new BottomTabBean(R.drawable.index_statistics_tab_selector,"数+"), new IndexStatisticsDelegate());
        items.put(new BottomTabBean(R.drawable.index_mine_tab_selector,"我的"), new IndexMineDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setIndexBottomBackground() {
        return R.mipmap.main_delegate_bottom_bg;
    }
}
