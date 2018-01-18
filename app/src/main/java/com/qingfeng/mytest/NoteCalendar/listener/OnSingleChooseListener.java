package com.qingfeng.mytest.NoteCalendar.listener;

import android.view.View;

import com.qingfeng.mytest.NoteCalendar.bean.DateBean;

/**
 * 日期点击接口
 */
public interface OnSingleChooseListener {
    /**
     * @param view
     * @param date
     */
    void onSingleChoose(View view, DateBean date);
}
