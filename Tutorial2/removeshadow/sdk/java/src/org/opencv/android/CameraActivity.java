package org.opencv.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;

public class CameraActivity extends Activity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;

    protected List<? extends CameraBridgeViewBase> getCameraViews() {
        return new ArrayList<>();
    }

    protected void onCameraPermissionGranted() {
        List<? extends CameraBridgeViewBase> cameraViews = getCameraViews();
        if (cameraViews != null) {
            for (CameraBridgeViewBase cameraView : cameraViews) {
                if (cameraView != null) {
                    cameraView.setCameraPermissionGranted();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestCameraPermission();
        } else {
            onCameraPermissionGranted();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        if (checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            onCameraPermissionGranted();
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onCameraPermissionGranted();
        }
    }
}
