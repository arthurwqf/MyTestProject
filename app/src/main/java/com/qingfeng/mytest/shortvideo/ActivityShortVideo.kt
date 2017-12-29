package com.qingfeng.mytest.shortvideo

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.qingfeng.mytest.R

class ActivityShortVideo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_short_video)
        init()
    }

    private fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 0)
            }
        } else {
            TODO("VERSION.SDK_INT < M")
            initCamera()
        }
    }

    private fun initCamera() {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            var result = grantResults[0]
            if (result == PackageManager.PERMISSION_GRANTED) {
                initCamera()
            } else {
                AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("需要请求相机权限！")
                        .setPositiveButton("确定", { dialog, which ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(arrayOf(Manifest.permission.CAMERA), 0)
                            }
                        })
                        .setNegativeButton("取消", {
                            dialog, which ->
                            this@ActivityShortVideo.finish()
                        })
                        .create()
                        .show()
            }
        }
    }
}
