package com.qingfeng.mytest.shortvideo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.MediaRecorder
import android.os.*
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Size
import android.util.SparseIntArray
import android.view.*
import android.widget.Toast
import com.qingfeng.mytest.R
import kotlinx.android.synthetic.main.activity_short_video.*
import java.util.*
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class ActivityShortVideo : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ic_record_control -> {
                if (mIsRecordingVideo) {
                    stopRecordingVideo()
                } else {
                    startRecordingVideo()
                }
            }
            else -> {

            }
        }
    }

    companion object {
        private val DEFAULT_ORIENTATIONS = SparseIntArray()
        private val INVERSE_ORIENTATIONS = SparseIntArray()
        private val SENSOR_ORIENTATION_DEFAULT_DEGREES = 90
        private val SENSOR_ORIENTATION_INVERSE_DEGREES = 270
        private val VIDEO_PERMISSIONS = arrayOf(Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)

        init {
            DEFAULT_ORIENTATIONS.append(Surface.ROTATION_0, 90)
            DEFAULT_ORIENTATIONS.append(Surface.ROTATION_90, 0)
            DEFAULT_ORIENTATIONS.append(Surface.ROTATION_180, 270)
            DEFAULT_ORIENTATIONS.append(Surface.ROTATION_270, 180)

            INVERSE_ORIENTATIONS.append(Surface.ROTATION_0, 270)
            INVERSE_ORIENTATIONS.append(Surface.ROTATION_90, 190)
            INVERSE_ORIENTATIONS.append(Surface.ROTATION_180, 90)
            INVERSE_ORIENTATIONS.append(Surface.ROTATION_270, 0)
        }
    }

    private lateinit var mContext: Context
    private var mCameraDevice: CameraDevice? = null
    private var mPreviewSession: CameraCaptureSession? = null
    //预览的size
    private var mPreviewSize: Size? = null
    //视频的size
    private var mVideoSize: Size? = null
    private var mMediaRecorder: MediaRecorder? = null
    private var mIsRecordingVideo = false
    //执行任务的线程，防止ui线程阻塞
    private var mBackgroundThread: HandlerThread? = null
    private var mBackgroundHandler: Handler? = null
    //camera关闭前防止app关闭
    private var mCameraOpenCloseLock: Semaphore = Semaphore(1)

    private var mSensorOrientation = 0
    private var mNextVideoAbsolutePath: String? = null
    private var mPreviewBuilder: CaptureRequest.Builder? = null
    private var mRecorderSurface: Surface? = null

    /**
     * 监听TextureView生命周期
     */
    private var mSurfaceTextureListener = object : TextureView.SurfaceTextureListener {

        override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
            openCamera(width, height)
        }

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
            configureTransform(width, height)
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
            return true
        }

    }

    private var mStateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice?) {
            mCameraDevice = camera
            startPreview()
            mCameraOpenCloseLock.release()
            if (null != texture_view) {
                configureTransform(texture_view.width, texture_view.height)
            }
        }

        override fun onDisconnected(camera: CameraDevice?) {
            mCameraOpenCloseLock.release()
            camera?.close()
            mCameraDevice = null
        }

        override fun onError(camera: CameraDevice?, error: Int) {
            mCameraOpenCloseLock.release()
            camera?.close()
            mCameraDevice = null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_short_video)
        init()
    }

    override fun onResume() {
        super.onResume()
        startBackgroundThread()
        if (texture_view.isAvailable) {
            openCamera(texture_view.width, texture_view.height)
        } else {
            texture_view.surfaceTextureListener = mSurfaceTextureListener
        }
    }

    override fun onPause() {
        closeCamera()
        stopBackgroundThread()
        super.onPause()
    }

    private fun init() {
        ic_record_control.setOnClickListener(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 0)
            }
        } else {
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1) {
            var result = grantResults[0]
            if (result == PackageManager.PERMISSION_GRANTED) {

            } else {
                AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("需要请求相机权限！")
                        .setPositiveButton("确定", { _, _ ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(arrayOf(Manifest.permission.CAMERA), 0)
                            }
                        })
                        .setNegativeButton("取消", { _, _ ->
                            this@ActivityShortVideo.finish()
                        })
                        .create()
                        .show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    @SuppressLint("MissingPermission")
    private fun openCamera(width: Int, height: Int) {
        if (!hasPermissionsGranted(VIDEO_PERMISSIONS)) {
            requestVideoPermissions()
            return
        }
        val manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw RuntimeException("Time out waiting to lock camera opening.")
            }
            val cameraId = manager.cameraIdList[0]
            val characteristics = manager.getCameraCharacteristics(cameraId)
            val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)
            mVideoSize = chooseVideoSize(map.getOutputSizes(MediaRecorder::class.java))
            mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture::class.java), width, height, mVideoSize!!)

            val orientation = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                texture_view.setAspectRatio(mPreviewSize!!.width, mPreviewSize!!.height)
            } else {
                texture_view.setAspectRatio(mPreviewSize!!.height, mPreviewSize!!.width)
            }
            configureTransform(width, height)
            mMediaRecorder = MediaRecorder()
            manager.openCamera(cameraId, mStateCallback, null)
        } catch (e: Exception) {
        }
    }

    private fun closeCamera() {
        mCameraOpenCloseLock.acquire()
        closePreviewSession()
        mCameraDevice?.close()
        mCameraDevice = null
        mMediaRecorder?.release()
        mMediaRecorder = null
    }

    private fun configureTransform(viewWidth: Int, viewHeight: Int) {
        if (null == texture_view || null == mPreviewSize) {
            return
        }
        val rotation = windowManager.defaultDisplay.rotation
        val matrix = Matrix()
        val viewRect = RectF(0F, 0F, viewWidth.toFloat(), viewHeight.toFloat())
        val bufferRect = RectF(0F, 0F, mPreviewSize!!.height.toFloat(), mPreviewSize!!.width.toFloat())
        val centerX = viewRect.centerX()
        val centerY = viewRect.centerY()
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY())
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL)
            val scale = Math.max((viewHeight / mPreviewSize!!.height).toFloat(),
                    (viewWidth / mPreviewSize!!.width).toFloat())
            matrix.postScale(scale, scale, centerX, centerY)
            matrix.postRotate((90 * (rotation - 2)).toFloat(), centerX, centerY)
        }
        texture_view.setTransform(matrix)
    }

    private fun startBackgroundThread() {
        mBackgroundThread = HandlerThread("CameraBackground")
        mBackgroundThread?.start()
        mBackgroundHandler = Handler(mBackgroundThread?.looper)
    }

    private fun stopBackgroundThread() {
        mBackgroundThread?.quitSafely()
        mBackgroundThread?.join()
        mBackgroundThread = null
        mBackgroundHandler = null
    }

    private fun closePreviewSession() {
        mPreviewSession?.close()
        mPreviewSession = null
    }

    /**
     * 开始录像
     */
    private fun startRecordingVideo() {
        if (null == mCameraDevice || !texture_view.isAvailable || null == mPreviewSize) {
            return
        }
        closePreviewSession()
        setUpMediaRecorder()
        val texture = texture_view.surfaceTexture
        texture.setDefaultBufferSize(mPreviewSize!!.width, mPreviewSize!!.height)
        mPreviewBuilder = mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_RECORD)
        val surfaces = ArrayList<Surface>()

        //为camera preview设置surface
        val previewSurface = Surface(texture)
        surfaces.add(previewSurface)
        mPreviewBuilder?.addTarget(previewSurface)

        //为MediaRecorder 设置surface
        mRecorderSurface = mMediaRecorder?.surface
        surfaces.add(mRecorderSurface!!)
        mPreviewBuilder?.addTarget(mRecorderSurface)

        //开启一个capture session
        //session 一启动，更新ui并启动录像
        mCameraDevice?.createCaptureSession(surfaces, object : CameraCaptureSession.StateCallback() {
            override fun onConfigured(session: CameraCaptureSession?) {
                mPreviewSession = session
                updatePreview()
                (mContext as Activity).runOnUiThread({
                    mIsRecordingVideo = true
                    ic_record_control.setImageResource(android.R.drawable.ic_media_pause)
                    record_time.start()
                    //开始录像
                    mMediaRecorder?.start()
                })
            }

            override fun onConfigureFailed(session: CameraCaptureSession?) {
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show()
            }
        }, mBackgroundHandler)
    }

    /**
     * 停止录像
     */
    private fun stopRecordingVideo() {
        mIsRecordingVideo = false
        ic_record_control.setImageResource(android.R.drawable.ic_media_play)
        record_time.stop()
        record_time.base = SystemClock.elapsedRealtime()

        //必须将这一句放到MediaRecorder释放前
        //否则会造成，接收数据方（Encoder）已经停止了，而发送数据的session还在运行
        startPreview()

        //stop recording
        mMediaRecorder?.stop()
        mMediaRecorder?.reset()

        Toast.makeText(mContext, "视频保存到了" + mNextVideoAbsolutePath, Toast.LENGTH_SHORT).show()
        mNextVideoAbsolutePath = null
    }

    /**
     * 设置mediaRecorder
     */
    private fun setUpMediaRecorder() {
        mMediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mMediaRecorder?.setVideoSource(MediaRecorder.VideoSource.SURFACE)
        mMediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mNextVideoAbsolutePath = getVideoFilePath(this)
        mMediaRecorder?.setOutputFile(mNextVideoAbsolutePath)
        mMediaRecorder?.setVideoEncodingBitRate(10000000)
        mMediaRecorder?.setVideoFrameRate(30)
        mMediaRecorder?.setVideoSize(mVideoSize!!.width, mVideoSize!!.height)
        mMediaRecorder?.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
        mMediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        val rotation = windowManager.defaultDisplay.rotation
        when (mSensorOrientation) {
            SENSOR_ORIENTATION_DEFAULT_DEGREES -> {
                mMediaRecorder?.setOrientationHint(DEFAULT_ORIENTATIONS.get(rotation))
            }
            SENSOR_ORIENTATION_INVERSE_DEGREES -> {
                mMediaRecorder?.setOrientationHint(INVERSE_ORIENTATIONS.get(rotation))
            }
        }
        mMediaRecorder?.prepare()
    }

    /**
     * 开启camera预览
     */
    private fun startPreview() {
        if (null == mCameraDevice || !texture_view.isAvailable || null == mPreviewSize) {
            return
        }
        try {
            closePreviewSession()
            val texture = texture_view.surfaceTexture
            texture.setDefaultBufferSize(mPreviewSize!!.width, mPreviewSize!!.height)
            mPreviewBuilder = mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)

            val previewSurface = Surface(texture)
            mPreviewBuilder?.addTarget(previewSurface)

            mCameraDevice!!.createCaptureSession(Arrays.asList(previewSurface), object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession?) {
                    mPreviewSession = session
                    updatePreview()
                }

                override fun onConfigureFailed(session: CameraCaptureSession?) {
                    Toast.makeText(mContext, "onConfigureFailed", Toast.LENGTH_SHORT).show()
                }
            }, mBackgroundHandler)
        } catch (e: Exception) {
            Log.d("test", e.toString())
        }
    }

    /**
     * 更新camera preview ,需要先调用startPreview
     */
    private fun updatePreview() {
        if (null == mCameraDevice) {
            return
        }
        setUpCaptureRequestBuilder(mPreviewBuilder)
        val thread = HandlerThread("CameraPreview")
        thread.start()
        mPreviewSession?.setRepeatingRequest(mPreviewBuilder?.build(), null, mBackgroundHandler)
    }

    private fun setUpCaptureRequestBuilder(builder: CaptureRequest.Builder?) {
        builder?.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)

    }

    private fun getVideoFilePath(context: Context): String {
        return context.getExternalFilesDir(null).absolutePath + "/" + System.currentTimeMillis() + ".mp4"
    }

    /**
     *
     */
    private fun chooseVideoSize(choices: Array<Size>): Size {
        return choices.firstOrNull { it.width == it.height * 4 / 3 && it.width <= 1080 }
                ?: choices[choices.size - 1]
    }

    /**
     *
     */
    private fun chooseOptimalSize(choices: Array<Size>, width: Int, height: Int, aspectRatio: Size): Size {
        val bigEnough = ArrayList<Size>()
        val w = aspectRatio.width
        val h = aspectRatio.height
        choices.filterTo(bigEnough) { it.height == it.width * h / w && it.width >= width && it.height >= height }

        return if (bigEnough.size > 0)
            Collections.min(bigEnough, CompareSizesByArea())
        else {
            choices[0]
        }
    }

    private fun hasPermissionsGranted(permissions: Array<String>): Boolean {
        return permissions.none { ActivityCompat.checkSelfPermission(mContext, it) != PackageManager.PERMISSION_GRANTED }
    }

    private fun shouldShowRequestPermissionRationals(permissions: Array<String>): Boolean {
        return permissions.any { ActivityCompat.shouldShowRequestPermissionRationale(mContext as Activity, it) }
    }

    private fun requestVideoPermissions() {
        if (shouldShowRequestPermissionRationals(VIDEO_PERMISSIONS)) {
            ConfirmationDialog().show(fragmentManager!!, "dialog")
        } else {
            ActivityCompat.requestPermissions(this, VIDEO_PERMISSIONS, 1)
        }
    }

    class CompareSizesByArea : Comparator<Size> {
        override fun compare(o1: Size, o2: Size): Int {
            return java.lang.Long.signum((o1.width * o1.height).toLong() - (o2.width * o2.height).toLong())
        }

    }

    class ConfirmationDialog : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            var parent = parentFragment
            return AlertDialog.Builder(activity)
                    .setMessage("需要请求权限")
                    .setPositiveButton("确定") { _, _ ->
                        ActivityCompat.requestPermissions(parent.activity, VIDEO_PERMISSIONS, 1)
                    }
                    .setNegativeButton("取消") { _, _ ->
                        parent.activity.finish()
                    }
                    .create()

        }
    }


}
