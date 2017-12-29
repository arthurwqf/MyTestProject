package com.qingfeng.mytest.shortvideo

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import com.qingfeng.mytest.R
import kotlinx.android.synthetic.main.activity_short_video.*

class ActivityShortVideo : AppCompatActivity() {

    private lateinit var mCamera:Camera

    private var mCallBack = object : SurfaceHolder.Callback{
        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun surfaceCreated(holder: SurfaceHolder?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

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
            initBase()
        }
    }

    private fun initBase() {
        surface_view.holder.setFixedSize(320,280)
        surface_view.holder.setKeepScreenOn(true)
        surface_view.holder.addCallback(mCallBack)
    }

    private fun initCamera(){
        if (mCamera!=null){
            mCamera.
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            var result = grantResults[0]
            if (result == PackageManager.PERMISSION_GRANTED) {
                initBase()
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

    private fun stopCamera(){
        if (mCamera!=null){
            mCamera
        }
    }
}
