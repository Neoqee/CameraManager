package com.neoqee.camera

import android.content.Context
import android.graphics.SurfaceTexture

internal class Camera1: CameraService {
    override fun setCameraConfig(config: CameraConfig) {
        TODO("Not yet implemented")
    }

    override fun getCameraConfig(): CameraConfig {
        TODO("Not yet implemented")
    }

    override fun openCamera(
        context: Context,
        surfaceTexture: SurfaceTexture,
        callback: OnPreviewCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun closeCamera() {
        TODO("Not yet implemented")
    }

    override fun switchCamera() {
        TODO("Not yet implemented")
    }

    override fun switchCamera(facing: Int) {
        TODO("Not yet implemented")
    }

    override fun release() {
        TODO("Not yet implemented")
    }
}