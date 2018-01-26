package com.qingfeng.mytest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_dp_px.*

class ActivityDpPx : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dp_px)
        btn1.setOnClickListener(View.OnClickListener {
            val dpStr1 = et_dp1.text.toString()
            if (!TextUtils.isEmpty(dpStr1)) {
                val dp1 = java.lang.Float.parseFloat(dpStr1)
                et_px1.setText(dp2px(dp1).toString())
            }
        })

        btn2.setOnClickListener(View.OnClickListener {
            val pxStr = et_px2.text.toString()
            if (!TextUtils.isEmpty(pxStr)) {
                val px = java.lang.Float.parseFloat(pxStr)
                et_dp2.setText(px2dp(px).toString())
            }
        })
    }

    private fun dp2px(dp: Float): Float {
        val scale = this.resources.displayMetrics.density
        return dp * scale + 0.5f
    }

    private fun px2dp(px: Float): Float {
        val scale = this.resources.displayMetrics.density
        return px / scale + 0.5f
    }
}
