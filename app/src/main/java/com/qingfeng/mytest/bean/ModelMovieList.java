package com.qingfeng.mytest.bean;

import java.util.List;

/**
 * Created by WangQF on 2017/12/21 0021.
 */

public class ModelMovieList {
    int count;
    int start;
    int total;
    List<ModelMovie> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ModelMovie> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<ModelMovie> subjects) {
        this.subjects = subjects;
    }
}
