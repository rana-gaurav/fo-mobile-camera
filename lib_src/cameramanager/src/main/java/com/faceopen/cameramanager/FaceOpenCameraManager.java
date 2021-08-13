package com.faceopen.cameramanager;

import android.content.Context;


public class FaceOpenCameraManager {
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

    public void initPreview(){

    }
}
