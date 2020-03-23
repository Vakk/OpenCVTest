package com.vakk.myapplication.utils

import org.opencv.core.Mat
import org.opencv.core.MatOfRect


class CameraDetector(cascadeName: String, minFaceSize: Int) {
    private var mNativeObj: Long = 0

    init {
        mNativeObj = nativeCreateObject(cascadeName, minFaceSize)
    }

    fun start() {
        nativeStart(mNativeObj)
    }

    fun stop() {
        nativeStop(mNativeObj)
    }

    fun setMinFaceSize(size: Int) {
        nativeSetFaceSize(mNativeObj, size)
    }

    fun detect(imageGray: Mat, faces: MatOfRect) {
        nativeDetect(
            mNativeObj,
            imageGray.getNativeObjAddr(),
            faces.getNativeObjAddr()
        )
    }

    fun release() {
        nativeDestroyObject(mNativeObj)
        mNativeObj = 0
    }


    companion object {
        private external fun nativeCreateObject(cascadeName: String, minFaceSize: Int): Long
        private external fun nativeDestroyObject(thiz: Long)
        private external fun nativeStart(thiz: Long)
        private external fun nativeStop(thiz: Long)
        private external fun nativeSetFaceSize(thiz: Long, size: Int)
        private external fun nativeDetect(thiz: Long, inputImage: Long, faces: Long)
    }

}