package com.qingfeng.mytest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qingfeng.mytest.widget.MyLinearSnapHelper;
import com.qingfeng.mytest.widget.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by WangQF on 2017/11/16 0016.
 */

public class ScrollerActivity extends AppCompatActivity {
    private Context mContext;
    RecyclerView mRecyclerView;
    ViewPager mViewPager;
    MyLinearSnapHelper mSnapHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        mContext = ScrollerActivity.this;
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mViewPager = (ViewPager) findViewById(R.id.vp);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mSnapHelper = new MyLinearSnapHelper();
        mSnapHelper.setItemWidth(ScreenUtil.dip2px(mContext, 90));
        mSnapHelper.setCurrentItemPos(0);
        mSnapHelper.attachToRecyclerView(mRecyclerView);
        mSnapHelper.setViewPager(mViewPager);

        ArrayList<Integer> imgs = new ArrayList<>();
        imgs.add(R.mipmap.p1);
        imgs.add(R.mipmap.p2);
        imgs.add(R.mipmap.p3);
        imgs.add(R.mipmap.p1);
        imgs.add(R.mipmap.p2);
        imgs.add(R.mipmap.p3);
        imgs.add(R.mipmap.p1);
        imgs.add(R.mipmap.p2);
        imgs.add(R.mipmap.p3);
        imgs.add(R.mipmap.p1);
        imgs.add(R.mipmap.p2);
        imgs.add(R.mipmap.p3);
        imgs.add(R.mipmap.p1);
        imgs.add(R.mipmap.p2);
        imgs.add(R.mipmap.p3);
        AdapterRecyclerViewImg adapterRecyclerView = new AdapterRecyclerViewImg(mContext, imgs);
        adapterRecyclerView.setOnItemClickListener(new AdapterRecyclerViewImg.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                mSnapHelper.setMoveStatus(1);
                mRecyclerView.smoothScrollToPosition(position);
            }
        });
        mRecyclerView.setAdapter(adapterRecyclerView);

        ArrayList<View> vpViews = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_img, null);
            ImageView imageView = view.findViewById(R.id.image);
            Glide.with(this).load(imgs.get(i))
                    .into(imageView);
            vpViews.add(view);
        }
        AdapterViewPager adapterViewPager = new AdapterViewPager(mContext, vpViews);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setOffscreenPageLimit(vpViews.size());
        mViewPager.setPageTransformer(true, new ScalePageTransformer());
    }


}
