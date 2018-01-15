package com.longfor.core.utils.UI;

import android.content.Context;

/**
 * @author: tongzhenhua
 * @date: 2018/1/15
 * @function:
 */
public class ScreenUtil {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale);
    }

}
