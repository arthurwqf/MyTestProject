package com.qingfeng.mytest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_dp_px.*

class ActivityDpPx : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dp_px)
        btn1.setOnClickListener({
            val dpStr1 = et_dp1.text.toString()
            if (!TextUtils.isEmpty(dpStr1)) {
                val dp1 = java.lang.Float.parseFloat(dpStr1)
                et_px1.setText(dp2px(dp1).toString())
            }
        })

        btn2.setOnClickListener({
            val pxStr = et_px2.text.toString()
            if (!TextUtils.isEmpty(pxStr)) {
                val px = java.lang.Float.parseFloat(pxStr)
                et_dp2.setText(px2dp(px).toString())
            }
        })
        btn3.setOnClickListener({
            val pxStr = et_px3.text.toString()
            if (!TextUtils.isEmpty(pxStr)) {
                val px = java.lang.Float.parseFloat(pxStr)
                et_sp.setText(px2sp(px).toString())
            }
        })
        btn_change.setOnClickListener({
            change16toInt()
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

    private fun px2sp(px: Float): Float {
        val scale = this.resources.displayMetrics.scaledDensity
        return px / scale
    }

    private fun change16toInt() {
        val rLong = java.lang.Long.parseLong(et16.text.toString(), 16)
        val rMove = java.lang.Long.parseLong(etRightMove.text.toString(), 16)
        val rTemp = rLong shr rMove.toInt()
        tv_result.text = rTemp.toString()
    }
}
