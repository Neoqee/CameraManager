package com.neoqee.camera

import android.content.Context
import android.graphics.SurfaceTexture

interface CameraService {

    fun setCameraConfig(config: CameraConfig)

    fun getCameraConfig(): CameraConfig

    fun openCamera(context: Context, surfaceTexture: SurfaceTexture, callback: OnPreviewCallback)

    fun closeCamera()

    fun switchCamera()

    fun switchCamera(facing: Int)

    fun release()

}