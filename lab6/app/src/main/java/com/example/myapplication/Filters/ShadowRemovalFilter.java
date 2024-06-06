package com.example.myapplication.Filters;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import com.example.myapplication.Clahe;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShadowRemovalFilter {

    public MyCallBack callBack;

    public interface MyCallBack<Bitmap>{
        void onComplete(Bitmap bitmap);
    }

    public static void getShadowFilteredImage(Bitmap bit_map, final MyCallBack<Bitmap> callBack){

        Clahe.getClaheImage(bit_map, new Clahe.CallBack<Bitmap>() {
            @Override
            public void onComplete(final Bitmap pre_processed) {
                Executor executor = Executors.newSingleThreadExecutor();
                final Handler handler = new Handler(Looper.getMainLooper());
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Mat srcArry = new Mat();
                        Utils.bitmapToMat(pre_processed, srcArry);

                        Imgproc.cvtColor(srcArry, srcArry, Imgproc.COLOR_BGR2HSV);

                        List<Mat> hsvPlanes = new ArrayList<>();
                        Core.split(srcArry, hsvPlanes);
                        Mat vChannel = hsvPlanes.get(2);

                        Mat dilatedImg = new Mat();
                        Mat kernel = Mat.ones(7, 7, CvType.CV_32F);
                        Imgproc.dilate(vChannel, dilatedImg, kernel);
                        Imgproc.medianBlur(dilatedImg, dilatedImg, 21);

                        Mat diff = new Mat();
                        Core.absdiff(vChannel, dilatedImg, diff);
                        Core.bitwise_not(diff, diff);

                        Mat norm = new Mat();
                        Core.normalize(diff, norm, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC1);
                        hsvPlanes.set(2, norm);

                        Mat resultNorm = new Mat();
                        Core.merge(hsvPlanes, resultNorm);

                        Imgproc.cvtColor(resultNorm, resultNorm, Imgproc.COLOR_HSV2BGR);

                        final Bitmap resUlt = Bitmap.createBitmap(resultNorm.cols(), resultNorm.rows(), Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(resultNorm, resUlt);

                        // Release the Mats to free memory
                        srcArry.release();
                        vChannel.release();
                        dilatedImg.release();
                        diff.release();
                        norm.release();
                        resultNorm.release();
                        for (Mat mat : hsvPlanes) {
                            mat.release();
                        }
                        kernel.release();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onComplete(resUlt);
                            }
                        });
                    }
                });
            }
        });
    }
}
