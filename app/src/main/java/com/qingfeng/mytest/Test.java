package com.qingfeng.mytest;

import android.Manifest;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.Arrays;

/**
 * Created by WangQF on 2017/12/28 0028.
 */

public class Test extends AppCompatActivity {
    private final static String[] VIDEOPER = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };
}
