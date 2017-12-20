package com.qingfeng.mytest.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.qingfeng.mytest.BR;

/**
 * Created by WangQF on 2017/12/20 0020.
 */

public class ModelRating extends BaseObservable {
    int min;
    int max;
    float average;
    int stars;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Bindable
    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
        notifyPropertyChanged(BR.average);
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
