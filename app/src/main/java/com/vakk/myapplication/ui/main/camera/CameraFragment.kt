package com.vakk.myapplication.ui.main.camera

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.vakk.myapplication.R
import com.vakk.myapplication.ui.list.base.BaseFragment
import com.vakk.myapplication.utils.CameraDetector
import com.vakk.myapplication.utils.TAG
import kotlinx.android.synthetic.main.fragment_camera.*
import org.opencv.android.*
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference

class CameraFragment
    : BaseFragment<CameraViewModel>(CameraViewModel::class.java, R.layout.fragment_camera) {

    private var loaderCallback: LoaderCallback? = null

    var javaDetector: CascadeClassifier? = null
    var nativeDetector: CameraDetector? = null
    var detectorType: DetectorType? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        svCamera.visibility = CameraBridgeViewBase.VISIBLE
        svCamera.setCvCameraViewListener(CvCameraListener(this))
    }

    override fun onResume() {
        super.onResume()
        val loaderCallback = LoaderCallback(context!!, this)
        if (!OpenCVLoader.initDebug()) {
            Log.d(
                TAG,
                "Internal OpenCV library not found. Using OpenCV Manager for initialization"
            )
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, context, loaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!")
            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
        this.loaderCallback = loaderCallback
    }

    override fun onPause() {
        super.onPause()
        loaderCallback = null
        svCamera.disableView()
    }

}

private class CvCameraListener(cameraFragment: CameraFragment) :
    CameraBridgeViewBase.CvCameraViewListener2 {

    var faceRectColor = Scalar(0.0, 255.0, 0.0, 255.0)

    private val cameraFragmentWR = WeakReference(cameraFragment)

    private val cameraFragment: CameraFragment? get() = cameraFragmentWR.get()

    private var detectorType: DetectorType?
        get() = cameraFragment?.detectorType
        set(value) { cameraFragment?.detectorType = value }

    private var javaDetector: CascadeClassifier?
        get() = cameraFragment?.javaDetector
        set(value) {
            cameraFragment?.javaDetector = value
        }
    private var nativeDetector: CameraDetector?
        get() = cameraFragment?.nativeDetector
        set(value) {
            cameraFragment?.nativeDetector = value
        }

    private var gray: Mat? = null
    private var rgba: Mat? = null

    private var absoluteFaceSize = 0
    private var relativeFaceSize = 0

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat? {
        rgba = inputFrame.rgba()
        gray = inputFrame.gray()
        val gray = gray ?: return null
        val rgba = rgba ?: return null
        if (absoluteFaceSize == 0) {
            val height: Int = gray.rows()
            if (height * relativeFaceSize > 0) {
                absoluteFaceSize = height * relativeFaceSize
            }
            nativeDetector?.setMinFaceSize(absoluteFaceSize)
        }
        val faces = MatOfRect()
        if (detectorType == DetectorType.JAVA) {
            javaDetector?.detectMultiScale(
                gray, faces, 1.1, 2, 2,  // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                Size(absoluteFaceSize.toDouble(), absoluteFaceSize.toDouble()), Size()
            )
        } else if (detectorType == DetectorType.NATIVE) {
            nativeDetector?.detect(gray, faces)
        } else {
            Log.e(TAG, "Detection method is not selected!")
        }
        val facesArray: Array<Rect> = faces.toArray()
        for (i in facesArray.indices) Imgproc.rectangle(
            rgba,
            facesArray[i].tl(),
            facesArray[i].br(),
            faceRectColor,
            3
        )
        return rgba
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        gray = Mat()
        rgba = Mat()
    }

    override fun onCameraViewStopped() {
        gray?.release()
        rgba?.release()
    }
}

class LoaderCallback(context: Context, cameraFragment: CameraFragment) :
    BaseLoaderCallback(context) {

    private val cameraFragmentWR = WeakReference(cameraFragment)
    private val cameraFragment: CameraFragment? get() = cameraFragmentWR.get()

    private var detectorType: DetectorType?
        get() = cameraFragment?.detectorType
        set(value) {
            cameraFragment?.detectorType = detectorType
        }

    private var javaDetector: CascadeClassifier?
        get() = cameraFragment?.javaDetector
        set(value) {
            cameraFragment?.javaDetector = value
        }
    private var nativeDetector: CameraDetector?
        get() = cameraFragment?.nativeDetector
        set(value) {
            cameraFragment?.nativeDetector = value
        }

    private val cameraView: JavaCamera2View? get() = cameraFragment?.svCamera

    override fun onManagerConnected(status: Int) {
        when (status) {
            LoaderCallbackInterface.SUCCESS -> {
                Log.i(TAG, "OpenCV loaded successfully")

                // Load native library after(!) OpenCV initialization
                System.loadLibrary("detection_based_tracker")
                try {
                    // load cascade file from application resources
                    val `is`: InputStream =
                        mAppContext.resources.openRawResource(R.raw.lbpcascade_frontalface)
                    val cascadeDir = mAppContext.getDir("cascade", Context.MODE_PRIVATE)
                    val mCascadeFile = File(cascadeDir, "lbpcascade_frontalface.xml")
                    val os = FileOutputStream(mCascadeFile)
                    val buffer = ByteArray(4096)
                    var bytesRead: Int
                    while (`is`.read(buffer).also { bytesRead = it } != -1) {
                        os.write(buffer, 0, bytesRead)
                    }
                    `is`.close()
                    os.close()
                    javaDetector = CascadeClassifier(mCascadeFile.absolutePath)
                    val javaDetector = javaDetector ?: return
                    if (javaDetector.empty()) {
                        Log.e(
                            TAG,
                            "Failed to load cascade classifier"
                        )
                        this.javaDetector = null
                    } else Log.i(
                        TAG,
                        "Loaded cascade classifier from " + mCascadeFile.absolutePath
                    )
                    nativeDetector = CameraDetector(mCascadeFile.absolutePath, 0)
                    cascadeDir.delete()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.e(
                        TAG,
                        "Failed to load cascade. Exception thrown: $e"
                    )
                }
                cameraView?.enableView()
            }
            else -> super.onManagerConnected(status)
        }
    }
}

enum class DetectorType {
    JAVA,
    NATIVE
}