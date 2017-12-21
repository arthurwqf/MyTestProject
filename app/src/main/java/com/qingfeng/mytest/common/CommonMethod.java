package com.qingfeng.mytest.common;

import android.content.Context;

/**
 * Created by WangQF on 2017/12/21 0021.
 */

public class CommonMethod {
    public static int getStatusBarHeight(Context context){
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
