package com.qingfeng.mytest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qingfeng.mytest.jni.ActivityJniTest;
import com.qingfeng.mytest.panorama.ActivityPanorama;
import com.qingfeng.mytest.step.ActivityStep;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startActivity(new Intent(mContext, ScrollerActivity.class));
                break;
            case R.id.btn2:
                //全景图片
                startActivity(new Intent(mContext, ActivityPanorama.class));
                break;
            case R.id.btn3:
                //算法
                startAc(ActivityAlgorithm.class);
                break;
            case R.id.btn4:
                startAc(ActivityStep.class);
                break;
            case R.id.btn5:
                startAc(ActivityJniTest.class);
                break;
        }
    }

    private void startAc(Class<?> c) {
        startActivity(new Intent(mContext, c));
    }
}
