package com.qingfeng.mytest.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.qingfeng.mytest.BR;

/**
 * Created by WangQF on 2017/12/20 0020.
 */

public class ModelMovie extends BaseObservable {
    int id;
    String title; //
    String original_title; //原名
    String aka; //别名
    ModelRating rating;
    ModelImage images;

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

    @Bindable
    public ModelRating getRating() {
        return rating;
    }

    public void setRating(ModelRating rating) {
        this.rating = rating;
        notifyPropertyChanged(BR.rating);
    }

    @Bindable
    public ModelImage getImages() {
        return images;
    }

    public void setImages(ModelImage images) {
        this.images = images;
    }

    @BindingAdapter("showImage")
    public static void showImage(final ImageView imageView, String img) {

        Glide.with(imageView.getContext())
                .load(img)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int viewW = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) viewW / (float) resource.getIntrinsicWidth();
                        int viewH = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = viewH + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        Log.d("test", "onResourceReady: " + params.height);
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .into(imageView);
    }
}
