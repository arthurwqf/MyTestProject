package com.qingfeng.mytest.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.qingfeng.mytest.BR;

/**
 * Created by WangQF on 2017/12/21 0021.
 */

public class ModelImage extends BaseObservable {
    String small;
    String large;
    String medium;

    @Bindable
    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}
