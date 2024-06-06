package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Clahe {

    public CallBack callBack;

    public interface CallBack<Bitmap> {
        void onComplete(Bitmap bitmap);
    }

    public static void getClaheImage(final Bitmap bitmap, final Clahe.CallBack<Bitmap> callBack) {

        Executor executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Mat srcArry = new Mat();
                Utils.bitmapToMat(bitmap, srcArry);

                if (srcArry.channels() >= 3) {
                    Mat labImage = new Mat();
                    Imgproc.cvtColor(srcArry, labImage, Imgproc.COLOR_BGR2Lab);
                    List<Mat> labPlanes = new ArrayList<>();
                    Core.split(labImage, Collections.singletonList((Mat) labPlanes));

                    // Apply CLAHE algorithm on the L channel
                    org.opencv.imgproc.CLAHE clahe = Imgproc.createCLAHE();
                    clahe.setClipLimit(1);
                    clahe.apply(labPlanes.get(0), labPlanes.get(0));

                    // Merge the channels back
                    Core.merge(labPlanes, labImage);
                    Imgproc.cvtColor(labImage, srcArry, Imgproc.COLOR_Lab2BGR);

                    // Release resources
                    labImage.release();
                    for (Mat mat : labPlanes) {
                        mat.release();
                    }
                }

                final Bitmap result = Bitmap.createBitmap(srcArry.cols(), srcArry.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(srcArry, result);

                // Release srcArry
                srcArry.release();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onComplete(result);
                    }
                });
            }
        });
    }
}
