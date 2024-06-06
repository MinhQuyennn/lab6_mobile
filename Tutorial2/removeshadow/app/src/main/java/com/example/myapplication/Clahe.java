package com.example.myapplication;

import android.graphics.Bitmap;

public class Clahe {

    public interface CallBack<T> {
        void onComplete(T result);
    }

    public static void getClaheImage(Bitmap bitmap, CallBack<Bitmap> callBack) {
        // Your implementation of CLAHE preprocessing on the bitmap
        // Call callBack.onComplete(processedBitmap) once done
    }
}
