package com.neoqee.camera

import android.content.Context
import android.graphics.SurfaceTexture
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.TextureView

class CameraManager(private val threadName: String = "camera_thread"): CameraService,
    TextureView.SurfaceTextureListener{

    private var type: CameraType = CameraType.CAMERA1
    private var camera: CameraService = Camera1()

    private var cameraThread: HandlerThread? = null
    private var handler: Handler? = null

    private var mContext: Context? = null
    private var textureView: TextureView? = null
    private var previewCallback: OnPreviewCallback? = null


    fun startPreview(context: Context, textureView: TextureView, callback: OnPreviewCallback) {
        mContext = context
        this.textureView = textureView
        previewCallback = callback

        if (textureView.isAvailable) {
            openCamera(context, textureView.surfaceTexture!!, callback)
        } else {
            textureView.surfaceTextureListener = this
        }
    }

    fun stopPreview() {
        release()
        textureView = null
        previewCallback = null
    }

    fun setCameraType(cameraType: CameraType) {
        if (type == cameraType) return
        type = cameraType
    }

    override fun setCameraConfig(config: CameraConfig) {
        camera.setCameraConfig(config)
    }

    override fun getCameraConfig(): CameraConfig {
        return camera.getCameraConfig()
    }

    override fun openCamera(
        context: Context,
        surfaceTexture: SurfaceTexture,
        callback: OnPreviewCallback
    ) {
        postTask {
            camera.openCamera(context, surfaceTexture, callback)
        }
    }

    override fun closeCamera() {
        postTask {
            camera.closeCamera()
        }
    }

    override fun switchCamera() {
        postTask { camera.switchCamera() }
    }

    override fun switchCamera(facing: Int) {
        postTask { camera.switchCamera(facing) }
    }

    override fun release() {
        postTask { camera.release() }
    }

    // =================================================================================================

    private fun getHandler(): Handler {
        if (handler == null || cameraThread == null) {
            startBackground()
        }
        return handler!!
    }

    private fun startBackground() {
        stopBackground()
        cameraThread = HandlerThread(threadName)
        cameraThread!!.start()
        handler = Handler(cameraThread!!.looper)
    }

    private fun stopBackground() {
        if (cameraThread != null) {
            cameraThread!!.quitSafely()
            cameraThread = null
        }
        if (handler != null) {
            handler = null
        }
    }

    private fun postTask(runnable: Runnable) {
        getHandler().post(runnable)
    }

    private fun postTask(block: () -> Unit) {
        postTask(Runnable(block))
    }

    // =================================================================================================

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, p1: Int, p2: Int) {
        Log.e("Neoqee", "onSurfaceTextureAvailable")
        openCamera(mContext!!, surface, previewCallback!!)
    }

    override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
    }

    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
        postTask { release() }
        return true
    }

    override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
    }


}