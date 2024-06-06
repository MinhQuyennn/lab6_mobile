package com.example.opencv;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;

public class FaceDetector {

    private long mNativeObj = 0;
    private FaceDetectionListener mListener;

    public interface FaceDetectionListener {
        void onFaceDetected(MatOfRect faces);
    }

    public FaceDetector(String cascadeName, int minFaceSize) {
        mNativeObj = createNativeObject(cascadeName, minFaceSize);
    }

    public void setFaceDetectionListener(FaceDetectionListener listener) {
        mListener = listener;
    }

    public void startDetection() {
        startNativeDetection(mNativeObj);
    }

    public void stopDetection() {
        stopNativeDetection(mNativeObj);
    }

    public void setMinFaceSize(int size) {
        setNativeFaceSize(mNativeObj, size);
    }

    public void detectFaces(Mat grayscaleImage) {
        MatOfRect detectedFaces = new MatOfRect();
        detectNativeFaces(mNativeObj, grayscaleImage.getNativeObjAddr(), detectedFaces.getNativeObjAddr());
        if (mListener != null) {
            mListener.onFaceDetected(detectedFaces);
        }
    }

    public void releaseResources() {
        releaseNativeObject(mNativeObj);
        mNativeObj = 0;
    }

    // Native methods
    private static native long createNativeObject(String cascadeName, int minFaceSize);

    private static native void releaseNativeObject(long object);

    private static native void startNativeDetection(long object);

    private static native void stopNativeDetection(long object);

    private static native void setNativeFaceSize(long object, int size);

    private static native void detectNativeFaces(long object, long inputImage, long faces);

    // Load the native library
    static {
        System.loadLibrary("YourNativeLibraryName");
    }
}
