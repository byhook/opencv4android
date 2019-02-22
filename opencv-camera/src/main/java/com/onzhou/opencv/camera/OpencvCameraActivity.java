package com.onzhou.opencv.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;

/**
 * @anchor: andy
 * @date: 2019-02-22
 * @description:
 */
public class OpencvCameraActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 100;

    private static final String TAG = "OpenCVCamera";

    private JavaCameraView mCameraView;

    private boolean mInitSuccess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_opencv_camera);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupOpenCV();
        applyPermission();
    }

    private void applyPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CODE);
        } else {
            setupView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE && grantResults != null && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupView();
            }
        }
    }

    private void setupOpenCV() {
        mInitSuccess = OpenCVLoader.initDebug();
        if (mInitSuccess) {
            Log.d(TAG, "OpenCV init success");
        } else {
            Log.e(TAG, "OpenCV init failed");
        }
    }

    private void setupView() {
        mCameraView = findViewById(R.id.camera_view);
        //0是后置摄像头 1是前置摄像头
        mCameraView.setCameraIndex(1);
        mCameraView.enableView();
    }

}
