package com.qingfeng.mytest;

import android.Manifest;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by WangQF on 2017/12/28 0028.
 */

public class Test extends AppCompatActivity{
    private void test(){
        Test.this.finish();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{ Manifest.permission.CAMERA }, 0);
        }
    }

}
