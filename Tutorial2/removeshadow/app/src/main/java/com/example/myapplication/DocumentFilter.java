package com.example.myapplication;

import android.graphics.Bitmap;


import com.example.myapplication.Filters.ShadowRemovelFilter;

import org.opencv.android.OpenCVLoader;

public class DocumentFilter{

    public interface CallBack<Bitmap>{
        void onCompleted(Bitmap bitmap);
    }

    static {
        OpenCVLoader.initDebug();
    }


    public void getShadowRemoval(Bitmap bitmap, final CallBack<Bitmap> myCallBack) {
        if(bitmap == null) throw new NullPointerException("Bitmap is Null");
        ShadowRemovelFilter.getShadowFilteredImage(bitmap, new ShadowRemovelFilter.MyCallBack<Bitmap>() {
            @Override
            public void onComplete(Bitmap bitmap) {
                myCallBack.onCompleted(bitmap);
            }
        });
    }
}