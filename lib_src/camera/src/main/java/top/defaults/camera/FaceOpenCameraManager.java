package top.defaults.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.Set;


public class FaceOpenCameraManager {
    private CameraStatusCode moduleStatus = CameraStatusCode.CAMERA_DEINIT_SUCCESS;
    private Context mContext;
    private CameraCallback cameraCallback;
    private Photographer photographer;
    private PhotographerHelper photographerHelper;
    private CameraView mCameraView;
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

    public CameraStatusCode initPreview(CameraView cameraView){
        if(mContext == null){
            return setModuleStatus(CameraStatusCode.CAMERA_INIT_ERROR);
        }
        if(cameraView == null){
            return setModuleStatus(CameraStatusCode.CAMERA_PREVIEW_NOT_INIT);
        }
        mCameraView = cameraView;
        mCameraView.setFocusIndicatorDrawer(new CanvasDrawer() {
            private static final int SIZE = 300;
            private static final int LINE_LENGTH = 50;

            @Override
            public Paint[] initPaints() {
                Paint focusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                focusPaint.setStyle(Paint.Style.STROKE);
                focusPaint.setStrokeWidth(2);
                focusPaint.setColor(Color.WHITE);
                return new Paint[]{ focusPaint };
            }

            @Override
            public void draw(Canvas canvas, Point point, Paint[] paints) {
                if (paints == null || paints.length == 0) return;

                int left = point.x - (SIZE / 2);
                int top = point.y - (SIZE / 2);
                int right = point.x + (SIZE / 2);
                int bottom = point.y + (SIZE / 2);

                Paint paint = paints[0];

                canvas.drawLine(left, top + LINE_LENGTH, left, top, paint);
                canvas.drawLine(left, top, left + LINE_LENGTH, top, paint);

                canvas.drawLine(right - LINE_LENGTH, top, right, top, paint);
                canvas.drawLine(right, top, right, top + LINE_LENGTH, paint);

                canvas.drawLine(right, bottom - LINE_LENGTH, right, bottom, paint);
                canvas.drawLine(right, bottom, right - LINE_LENGTH, bottom, paint);

                canvas.drawLine(left + LINE_LENGTH, bottom, left, bottom, paint);
                canvas.drawLine(left, bottom, left, bottom - LINE_LENGTH, paint);
            }
        });
        return setModuleStatus(CameraStatusCode.CAMERA_PREVIEW_INIT);
    }

    public Photographer initPhotographer(Activity activity,  CameraView cameraView){
        if(cameraView != null) {
            photographer = PhotographerFactory.createPhotographerWithCamera2(activity, cameraView, new Photographer.onDataListener() {
                @Override
                public void onImageDataReceived(ImageData imageData) {
                    if (imageData != null) {
                        cameraCallback.frameReceived(imageData);
                    }
                }
            });
        }
        return photographer;
    }

    public PhotographerHelper initPhotoHelper(){
        photographerHelper = new PhotographerHelper(photographer);
        return photographerHelper;
    }

    public void enterFullscreen(Window window) {
        View decorView = window.getDecorView();
        decorView.setBackgroundColor(Color.BLACK);
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void createNewFile(String filePath, Activity activity) {
        Toast.makeText(activity, "File: " + filePath, Toast.LENGTH_SHORT).show();
        Utils.addMediaToGallery(activity, filePath);
    }

    public void flipCamera(){
        if(photographerHelper != null) {
            photographerHelper.flip();
        }
    }

    public void fillSpace(boolean value){
        mCameraView.setFillSpace(value);
    }

    public void startPreView(){
        photographer.startPreview();
    }

    public void switchMode(){
        if(photographerHelper != null) {
            photographerHelper.switchMode();
        }
    }


    public void finishRecord(){
        if(photographer != null){
            photographer.finishRecording();
        }
    }

    public void setFlash(int flash){
        if(photographer != null){
            photographer.setFlash(flash);
        }
    }

    public int getFlash(){
        if(photographer != null){
            return photographer.getFlash();
        }
        return 0;
    }

    public void startRecording(){
        if(photographer != null){
            photographer.startRecording(null);
        }

    }

    public void takePicture(){
        if(photographer != null){
            photographer.takePicture();
        }
    }

    public void setVideoSize(Size videoSize){
        if(photographer != null){
            photographer.setVideoSize(videoSize);
        }
    }

    public void setImageSize(Size imageSize){
        if(photographer != null){
            photographer.setImageSize(imageSize);
        }
    }

    public int getMode(){
        if(photographer != null){
            return photographer.getMode();
        }
        return 0;
    }

    public Set<Size> getSupportedVideoSizes(){
        if(photographer != null){
            return photographer.getSupportedVideoSizes();
        }
        return null;
    }

    public Size getVideoSize(){
        if(photographer != null){
            return photographer.getVideoSize();
        }
        return null;
    }

    public Set<Size> getSupportedImageSizes(){
        if(photographer != null){
            return photographer.getSupportedImageSizes();
        }
        return null;
    }

    public Size getImageSize(){
        if(photographer != null){
            return photographer.getImageSize();
        }
        return null;
    }

    public AspectRatio getAspectRatio(){
        if(photographer != null){
            return photographer.getAspectRatio();
        }
       return null;
    }

    public Set<AspectRatio> getSupportedAspectRatios(){
        if(photographer != null){
            return photographer.getSupportedAspectRatios();
        }
       return null;
    }

    public void setAspectRatio(AspectRatio aspectRatio){
        if(photographer != null){
            photographer.setAspectRatio(aspectRatio);
        }
    }
}
