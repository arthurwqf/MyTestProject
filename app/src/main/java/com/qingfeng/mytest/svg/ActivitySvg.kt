package com.qingfeng.mytest.svg

import android.graphics.drawable.Animatable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qingfeng.mytest.R
import kotlinx.android.synthetic.main.activity_svg.*

class ActivitySvg : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_svg)
        if (iv_eagle.drawable is Animatable) {
            (iv_eagle.drawable as Animatable).start();
        }
    }
}
