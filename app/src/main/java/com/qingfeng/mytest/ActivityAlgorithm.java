package com.qingfeng.mytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qingfeng.mytest.algorithm.ActivitySort;

public class ActivityAlgorithm extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startAc(ActivitySort.class);
                break;
        }
    }

    private void startAc(Class<?> c) {
        startActivity(new Intent(ActivityAlgorithm.this, c));
    }
}
