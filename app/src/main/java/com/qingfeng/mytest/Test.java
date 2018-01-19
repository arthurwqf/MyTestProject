package com.qingfeng.mytest;

import android.Manifest;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by WangQF on 2017/12/28 0028.
 */

public class Test extends AppCompatActivity {
    private void test(){
        new Thread().start();
        Float.parseFloat("123");

        TextView textView = new TextView(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
