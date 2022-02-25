package com.neoqee.camera

interface OnPreviewCallback {

    fun onPreview(nv21Data: ByteArray, width: Int, height: Int, rotation: Int)

}