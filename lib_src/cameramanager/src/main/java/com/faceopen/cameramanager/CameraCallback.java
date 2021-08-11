package com.faceopen.cameramanager;


import android.graphics.Bitmap;

public interface CameraCallback {
    void frameReceived(Bitmap bitmap);
}
