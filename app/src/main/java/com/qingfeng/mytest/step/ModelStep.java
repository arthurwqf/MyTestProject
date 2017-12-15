package com.qingfeng.mytest.step;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WangQF on 2017/12/6 0006.
 */

public class ModelStep implements Parcelable {
    int stepCount;
    int calorie;

    public ModelStep() {
    }

    protected ModelStep(Parcel in) {
        stepCount = in.readInt();
        calorie = in.readInt();
    }

    public static final Creator<ModelStep> CREATOR = new Creator<ModelStep>() {
        @Override
        public ModelStep createFromParcel(Parcel in) {
            return new ModelStep(in);
        }

        @Override
        public ModelStep[] newArray(int size) {
            return new ModelStep[size];
        }
    };

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stepCount);
        dest.writeInt(calorie);
    }

    public void readFromParcel(Parcel dest) {
        stepCount = dest.readInt();
        calorie = dest.readInt();
    }
}
