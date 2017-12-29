package com.qingfeng.mytest.svg

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.qingfeng.mytest.R
import kotlinx.android.synthetic.main.activity_svg.*
import java.lang.reflect.Field

class ActivitySvg : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_svg)
        if (iv_eagle.drawable is AnimatedVectorDrawable) {
            (iv_eagle.drawable as AnimatedVectorDrawable).start()
        }
        var dra = VectorDrawable()
        var f:Field
        f= dra.javaClass.getDeclaredField("mVectorState")
    }
}
