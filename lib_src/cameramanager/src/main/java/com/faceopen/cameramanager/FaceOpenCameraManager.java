package com.faceopen.cameramanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.widget.FrameLayout;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FaceOpenCameraManager {
    private Bitmap bitmap;
    private Camera mCamera;
    private CameraPreview mPreview;
    Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean isImageShown = false;
    private long frameTime = 0L;
    private CameraStatusCode moduleStatus = CameraStatusCode.CAMERA_DEINIT_SUCCESS;

    private Context mContext;
    private CameraCallback cameraCallback;
    private static final String TAG = FaceOpenCameraManager.class.getSimpleName();
    private static FaceOpenCameraManager instance = null;

    public static FaceOpenCameraManager getInstance() {
        if (instance == null) {
            instance = new FaceOpenCameraManager();
        }
        return instance;
    }

    public CameraStatusCode init(Context context) {
        if(context != null){
            instance.mContext = context;
            return setModuleStatus(CameraStatusCode.CAMERA_INIT_SUCCESS);
        }
        return setModuleStatus(CameraStatusCode.CAMERA_INIT_ERROR);
    }


    public CameraStatusCode deinit() throws CameraException {
        return setModuleStatus(CameraStatusCode.CAMERA_DEINIT_SUCCESS);
    }

    private Context getContext() {
        return instance.mContext;
    }

    public CameraStatusCode getModuleStatus() {
        if (instance != null)
            return instance.moduleStatus;
        return null;
    }

    private CameraStatusCode setModuleStatus(CameraStatusCode status) {
        if (instance != null)
            return instance.moduleStatus = status;
        return status;
    }

    public CameraStatusCode registerCallback(CameraCallback callback) {
        if (callback != null) {
            instance.cameraCallback = callback;
            return setModuleStatus(CameraStatusCode.CAMERA_CALLBACK_REGISTRATION_SUCCESS);
        }
        return setModuleStatus(CameraStatusCode.CAMERA_CALLBACK_REGISTRATION_FAILED);
    }

    public void startPreView (FrameLayout camera_preview) throws CameraException {
        if (instance.mContext == null)
            throw new CameraException("Context is null");
        mCamera = getCameraInstance(instance.mContext);
        mCamera.setDisplayOrientation(90);
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(instance.mContext, mCamera);
        //FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        camera_preview.addView(mPreview);
    }

    public static Camera getCameraInstance (Context context){
        Camera c = null;
        try {
            c = Camera.open(1); // attempt to get a Camera instance
        } catch (Exception e) {
            Toast.makeText(context, "!" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
        return c; // returns null if camera is unavailable
    }


    public void captureFrames(){
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] bytes, Camera camera) {
                Camera.Parameters parameters = camera.getParameters();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                YuvImage yuvImage = new YuvImage(bytes, parameters.getPreviewFormat(), parameters.getPreviewSize().width, parameters.getPreviewSize().height, null);
                yuvImage.compressToJpeg(new Rect(0, 0, parameters.getPreviewSize().width, parameters.getPreviewSize().height), 90, out);
                byte[] imageBytes = out.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                try {
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!isImageShown) {
                    isImageShown = true;
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isImageShown =false;
                            if (instance.cameraCallback != null)
                                instance.cameraCallback.frameReceived(bitmap);
                        }
                    }, getFrameDelayTime());
                }
            }
        });
    }

    public void setFrameDelay(int newFrameTime){
        frameTime = newFrameTime;
    }

    public long getFrameDelayTime(){
        return frameTime;
    }

}
