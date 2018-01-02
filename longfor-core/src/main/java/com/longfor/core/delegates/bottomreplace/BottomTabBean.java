package com.longfor.core.delegates.bottomreplace;

/**
 * Created by zhanghaitao1 on 2017/12/22.
 */

public class BottomTabBean {
    private final int ICON_RES;
    private final CharSequence TITLE;

    public BottomTabBean(int ICON_RES, CharSequence TITLE) {
        this.ICON_RES = ICON_RES;
        this.TITLE = TITLE;
    }

    public int getIcon() {
        return ICON_RES;
    }

    public CharSequence getTitle() {
        return TITLE;
    }
}
