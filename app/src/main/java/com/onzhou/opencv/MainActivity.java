package com.onzhou.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageView mIvImage;

    private boolean mInitSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupView();
        setupOpenCV();
    }

    private void setupView() {
        mIvImage = findViewById(R.id.open_iv_main);
    }

    private void setupOpenCV() {
        mInitSuccess = OpenCVLoader.initDebug();
        if (mInitSuccess) {
            Log.d(TAG, "OpenCV init success");
        } else {
            Log.e(TAG, "OpenCV init failed");
        }
    }

    public void onOperateClick(View view) {
        if (mInitSuccess) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.main);
            Mat srcMat = new Mat();
            Mat dstMat = new Mat();
            Utils.bitmapToMat(bitmap, srcMat);
            Imgproc.cvtColor(srcMat, dstMat, Imgproc.COLOR_BGRA2GRAY);
            Utils.matToBitmap(dstMat, bitmap);
            mIvImage.setImageBitmap(bitmap);
            srcMat.release();
            dstMat.release();
        } else {
            Toast.makeText(this, "init failed", Toast.LENGTH_SHORT).show();
        }
    }
}
