package com.faceopen.camerabenchmark;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.faceopen.cameramanager.FaceOpenCameraManager;

import top.defaults.camera.CameraView;
import top.defaults.camera.Photographer;
import top.defaults.camera.PhotographerFactory;
import top.defaults.camera.SimpleOnEventListener;

public class CameraActivity extends AppCompatActivity {

    private Photographer photographer;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    CameraView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        preview = findViewById(R.id.preview);
        if (checkCameraPermission()) {
            photographer = PhotographerFactory.createPhotographerWithCamera2(this, preview);
        } else {
            requestPermission();
        }

        photographer.setOnEventListener(new SimpleOnEventListener() {
            @Override
            public void onDeviceConfigured() {
            }

            @Override
            public void onPreviewStarted() {
            }

            @Override
            public void onZoomChanged(float zoom) {
            }

            @Override
            public void onPreviewStopped() {
            }

            @Override
            public void onStartRecording() {
            }

            @Override
            public void onFinishRecording(String filePath) {
            }

            @Override
            public void onShotFinished(String filePath) {
            }

        });


   }

    @Override
    protected void onResume() {
        super.onResume();
        photographer.startPreview();
    }

    @Override
    protected void onPause() {
        photographer.stopPreview();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    photographer = PhotographerFactory.createPhotographerWithCamera2(this, preview);
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(CameraActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    public void requestPermission () {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA,  Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_CAMERA_PERMISSION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FaceOpenCameraManager.getInstance().deinit();
    }

}