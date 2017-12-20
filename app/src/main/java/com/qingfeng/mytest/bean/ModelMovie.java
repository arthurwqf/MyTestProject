package com.qingfeng.mytest.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qingfeng.mytest.BR;

/**
 * Created by WangQF on 2017/12/20 0020.
 */

public class ModelMovie extends BaseObservable {
    int id;
    String title; //英文名
    String original_title; //原名
    String aka; //别名
    ModelRating rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getAka() {
        return aka;
    }

    public void setAka(String aka) {
        this.aka = aka;
    }

    public ModelRating getRating() {
        return rating;
    }

    public void setRating(ModelRating rating) {
        this.rating = rating;
    }

    @BindingAdapter("showImage")
    public void showImage(ImageView imageView, String img) {
        Glide.with(imageView.getContext())
                .load(img)
                .into(imageView);
    }
}