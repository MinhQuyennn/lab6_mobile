package org.opencv.android;

import org.opencv.core.Core;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.text.DecimalFormat;

public class FpsMeter {
    private static final String TAG = "FpsMeter";
    private static final int DEFAULT_STEP = 20;
    private static final DecimalFormat FPS_FORMAT = new DecimalFormat("0.00");

    private int framesCounter = 0;
    private double frequency;
    private long prevFrameTime;
    private String fpsString;
    private Paint paint;
    private boolean isInitialized = false;
    private int width = 0;
    private int height = 0;
    private boolean loggingEnabled = true;

    public FpsMeter() {
        init();
    }

    public FpsMeter(boolean enableLogging) {
        this.loggingEnabled = enableLogging;
        init();
    }

    private void init() {
        frequency = Core.getTickFrequency();
        prevFrameTime = Core.getTickCount();
        fpsString = "";

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(20);
    }

    public void measure() {
        framesCounter++;
        if (framesCounter % DEFAULT_STEP == 0) {
            long time = Core.getTickCount();
            double fps = calculateFps(time);
            if (width != 0 && height != 0)
                fpsString = FPS_FORMAT.format(fps) + " FPS@" + width + "x" + height;
            else
                fpsString = FPS_FORMAT.format(fps) + " FPS";
            logFps(fpsString);
        }
    }

    private double calculateFps(long currentTime) {
        return DEFAULT_STEP * frequency / Math.max(currentTime - prevFrameTime, 1); // Prevent division by zero
    }

    private void logFps(String fps) {
        if (loggingEnabled) {
            Log.i(TAG, fps);
        }
    }

    public void setResolution(int width, int height) {
        this.width = width;
        this.height = height;
        logResolution(width, height);
    }

    private void logResolution(int width, int height) {
        if (loggingEnabled) {
            Log.d(TAG, "FpsMeter.setResolution " + width + "x" + height);
        }
    }

    public void draw(Canvas canvas, float offsetX, float offsetY) {
        canvas.drawText(fpsString, offsetX, offsetY, paint);
        logFps(fpsString);
    }
}
