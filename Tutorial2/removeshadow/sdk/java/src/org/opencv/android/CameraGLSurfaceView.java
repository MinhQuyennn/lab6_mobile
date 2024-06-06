package org.opencv.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

import org.opencv.R;

public class CameraGLSurfaceView extends GLSurfaceView {

    private static final String LOGTAG = "CameraGLSurfaceView";

    public interface CameraTextureListener {
        void onCameraViewStarted(int width, int height);

        void onCameraViewStopped();

        boolean onCameraTexture(int texIn, int texOut, int width, int height);
    }

    private CameraTextureListener cameraTextureListener;
    private CameraGLRendererBase renderer;

    public CameraGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.CameraBridgeViewBase);
        int cameraIndex = styledAttrs.getInt(R.styleable.CameraBridgeViewBase_camera_id, -1);
        styledAttrs.recycle();

        if (android.os.Build.VERSION.SDK_INT >= 21)
            renderer = new Camera2Renderer(this);
        else
            renderer = new CameraRenderer(this);

        setCameraIndex(cameraIndex);

        setEGLContextClientVersion(2);
        setRenderer(renderer);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    public void setCameraTextureListener(CameraTextureListener cameraTextureListener) {
        this.cameraTextureListener = cameraTextureListener;
    }

    public CameraTextureListener getCameraTextureListener() {
        return cameraTextureListener;
    }

    public void setCameraIndex(int cameraIndex) {
        renderer.setCameraIndex(cameraIndex);
    }

    public void setMaxCameraPreviewSize(int maxWidth, int maxHeight) {
        renderer.setMaxCameraPreviewSize(maxWidth, maxHeight);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        renderer.mHaveSurface = false;
        super.surfaceDestroyed(holder);
    }

    @Override
    public void onResume() {
        Log.i(LOGTAG, "onResume");
        super.onResume();
        renderer.onResume();
    }

    @Override
    public void onPause() {
        Log.i(LOGTAG, "onPause");
        renderer.onPause();
        super.onPause();
    }

    public void enableView() {
        renderer.enableView();
    }

    public void disableView() {
        renderer.disableView();
    }
}
