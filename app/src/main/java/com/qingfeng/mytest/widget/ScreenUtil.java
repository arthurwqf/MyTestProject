package com.qingfeng.mytest.widget;

import android.content.Context;

/**
 * Created by WangQF on 2017/11/20 0020.
 */

public class ScreenUtil {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
