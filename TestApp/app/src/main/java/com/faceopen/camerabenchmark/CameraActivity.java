package com.faceopen.camerabenchmark;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeImageTransform;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

import com.bumptech.glide.Glide;
import com.faceopen.AppActivity;
import com.faceopen.camerabenchmark.data.BitmapDT;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.jesualex.stooltip.Position;
import cl.jesualex.stooltip.Tooltip;
import top.defaults.camera.CameraCallback;
import top.defaults.camera.CameraView;
import top.defaults.camera.FaceOpenCameraManager;
import top.defaults.camera.ImageData;
import top.defaults.camera.Values;

public class CameraActivity extends AppActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    @BindView(R.id.preview)
    CameraView preview;
    @BindView(R.id.action)
    ImageView actionButton;
    @BindView(R.id.iv_hint)
    ImageView ivHint;
    @BindView(R.id.rl_main)
    ConstraintLayout mainLayout;
    @BindView(R.id.iv_hint_text)
    TextView ivHintText;
    @BindView(R.id.action_btn_text)
    TextView btnActionText;
    @BindView(R.id.action_text)
    TextView actionText;
    @BindView(R.id.ll_tint)
    LinearLayout llTint;
    @BindView(R.id.tv_middle)
    TextView tvMiddle;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.ll_footer)
    LinearLayout llFooter;
    @BindView(R.id.btn_hint)
    Button btnHint;
    Runnable picRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d("XXX", "takePicture ");
            FaceOpenCameraManager.getInstance().takePicture();
        }
    };
    private Tooltip tooltip;
    private ArrayList<Bitmap> imageid = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> completeList = new ArrayList<Bitmap>();
    private Handler picHandler = new Handler();
    private boolean isRecordingVideo;
    private String TAG = "CameraActivity";
    private long CAMERA_CAPTURE_TIME = 1000;
    private String camAction = "straight";
    private String camText = "";
    Runnable zoomRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d("XXX", "startCamera ");
            zoomIn(1000);
            showHintPreView(camAction);
        }
    };
    private boolean isPreviewZoom = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        if (checkCameraPermission()) {
            startCamera();
        } else {
            requestPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick(R.id.action)
    void action() {
        hideClickHint();
        int mode = FaceOpenCameraManager.getInstance().getMode();
        if (mode == Values.MODE_VIDEO) {
            if (isRecordingVideo) {
                //finishRecordingIfNeeded();
            } else {
                isRecordingVideo = true;
                FaceOpenCameraManager.getInstance().startRecording();
            }
        } else if (mode == Values.MODE_IMAGE) {
            actionButton.setEnabled(false);
            imageid.clear();
            actionButton.setVisibility(View.GONE);
            btnActionText.setVisibility(View.GONE);
            FaceOpenCameraManager.getInstance().takePicture();
        }
    }

    @OnClick(R.id.iv_hint)
    void hint() {
        // This preview is shown to user how to take picture in particular direction
        if (!isPreviewZoom) {
            isPreviewZoom = true;
            zoomIn(1000);

        } else {
            isPreviewZoom = false;
            zoomOut(1000);
        }
    }

    @OnClick(R.id.btn_hint)
    void hintClick() {
        isPreviewZoom = false;
        zoomOut(1000);
    }

    private void startCamera() {
        FaceOpenCameraManager.getInstance().init(this);
        FaceOpenCameraManager.getInstance().initPreview(preview);
        FaceOpenCameraManager.getInstance().initPhotographer(this, preview);
        FaceOpenCameraManager.getInstance().initPhotoHelper();
        FaceOpenCameraManager.getInstance().flipCamera();
        FaceOpenCameraManager.getInstance().enterFullscreen(getWindow());
        FaceOpenCameraManager.getInstance().fillSpace(true);
        FaceOpenCameraManager.getInstance().startPreView();
        configureMode();
        camText = getResources().getString(R.string.face_straight);
        mainLayout.setVisibility(View.VISIBLE);
        // crash handling
        picHandler.postDelayed(zoomRunnable, 500);
        FaceOpenCameraManager.getInstance().registerCallback(new CameraCallback() {
            @Override
            public void frameReceived(ImageData imageData) {
                if (imageData != null) {
                    Log.d(TAG, "" + imageData.getImageHeight());
                    Log.d(TAG, "" + imageData.getImageWidth());
                    Log.d(TAG, "" + imageData.getImageFormat());
                    //tvMiddle.setVisibility(View.VISIBLE);
                    //tvMiddle.setText("Please wait...");
                    imageid.add(imageData.getBitmap());
                    if (imageid.size() < 3) {
                        completeList.addAll(imageid);
                        Log.d("XXX", "frameReceived " + completeList.size());
                        picHandler.postDelayed(picRunnable, CAMERA_CAPTURE_TIME);
                        setTextToshow(camText);
                    } else {
                        //ivHintText.setText(R.string.face_straight);
                        actionButton.setEnabled(true);
                        actionButton.setVisibility(View.VISIBLE);
                        btnActionText.setVisibility(View.GONE);
                        //tvMiddle.setText("Please click again");
                        showHintPreView(camAction);
                        picHandler.removeCallbacks(picRunnable);
                        zoomIn(1000);

                    }
                    //ivPreView.setVisibility(View.VISIBLE);
                    //ivPreView.setImageBitmap(imageData.getBitmap());
                }
            }
        });
    }

    public boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                REQUEST_CAMERA_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    startCamera();
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

    private void configureMode() {
        if (FaceOpenCameraManager.getInstance().getMode() == Values.MODE_VIDEO) {
            actionButton.setImageResource(R.drawable.record);
        } else {
            actionButton.setImageResource(R.drawable.ic_camera);
        }
    }

    private void showHintPreView(String pose) {
        if (pose.contentEquals("straight")) {
            camAction = "left";
            camText = getResources().getString(R.string.face_straight);
            getGifLoadedUsingGlidePreView(R.raw.straight);
        }
        if (pose.contentEquals("left")) {
            camAction = "right";
            camText = getResources().getString(R.string.face_left);
            getGifLoadedUsingGlidePreView(R.raw.left);
        }
        if (pose.contentEquals("right")) {
            camAction = "up";
            camText = getResources().getString(R.string.face_right);
            getGifLoadedUsingGlidePreView(R.raw.right);
        }
        if (pose.contentEquals("up")) {
            camAction = "down";
            camText = getResources().getString(R.string.face_up);
            getGifLoadedUsingGlidePreView(R.raw.up);
        }
        if (pose.contentEquals("down")) {
            camAction = "";
            camText = getResources().getString(R.string.face_down);
            getGifLoadedUsingGlidePreView(R.raw.down);
        }
        if (pose.contentEquals("")) {
            camAction = "";
            camText = "";
            getGifLoadedUsingGlidePreView(null);
            Log.d("CCC", "start");
            BitmapDT.getInstance().setBitMaps(completeList);
            Intent intent = new Intent(CameraActivity.this, PreviewActivity.class);
            startActivityForResult(intent, 1);
            //overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);
            Log.d("CCC", "end");
        }
        actionText.setText(camText);
        ivHintText.setText(camText);
    }

    private ImageView getGifLoadedUsingGlidePreView(Integer resource) {
        ImageView animatedImageView = new ImageView(this);
        animatedImageView.setMaxHeight(900);
        Glide.with(this)
                .asGif()
                .load(resource)
                .into(ivHint);
        return animatedImageView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearData();
    }

    private void clearData() {
        completeList.clear();
        imageid.clear();
    }

    private void zoomIn(long duration) {
        ViewGroup.LayoutParams layoutParams = ivHint.getLayoutParams();
        int width = layoutParams.width;
        int height = layoutParams.height;
        layoutParams.width = getResources().getInteger(R.integer.hint_large_size);
        layoutParams.height = getResources().getInteger(R.integer.hint_large_size);
        Log.d("XXX", "zoomIn  " + layoutParams.width);
        Log.d("XXX", "zoomIn  " + layoutParams.height);
        ivHint.setLayoutParams(layoutParams);
        ivHint.setScaleType(ImageView.ScaleType.FIT_CENTER);

        TransitionSet transitionSet = new TransitionSet();
        Transition bound = new ChangeBounds();
        transitionSet.addTransition(bound);
        Transition changeImageTransform = new ChangeImageTransform();
        transitionSet.addTransition(changeImageTransform);
        transitionSet.setDuration(duration);
        TransitionManager.beginDelayedTransition(mainLayout, transitionSet);
        hideViews();
        hideClickHint();
    }

    private void zoomOut(long duration) {
        ViewGroup.LayoutParams layoutParams = ivHint.getLayoutParams();
        int width = layoutParams.width;
        int height = layoutParams.height;
        Log.d("XXX", "zoomOut  " + width);
        Log.d("XXX", "zoomOut  " + height);
        layoutParams.width = getResources().getInteger(R.integer.hint_small_size);
        layoutParams.height = getResources().getInteger(R.integer.hint_small_size);
        ivHint.setLayoutParams(layoutParams);
        ivHint.setScaleType(ImageView.ScaleType.FIT_CENTER);

        TransitionSet transitionSet = new TransitionSet();
        Transition bound = new ChangeBounds();
        transitionSet.addTransition(bound);
        Transition changeImageTransform = new ChangeImageTransform();
        transitionSet.addTransition(changeImageTransform);
        transitionSet.setDuration(duration);
        TransitionManager.beginDelayedTransition(mainLayout, transitionSet);
        showViews();
        showClickHint();
    }

    private void hideViews() {
        llHeader.setVisibility(View.GONE);
        llFooter.setVisibility(View.GONE);
        actionButton.setVisibility(View.GONE);
        if (!camText.equals("")) {
            btnHint.setVisibility(View.VISIBLE);
        }
        ivHintText.setVisibility(View.VISIBLE);
        //actionText.setVisibility(View.GONE);
        llTint.setVisibility(View.VISIBLE);
        mainLayout.setAlpha(100);
    }

    private void showViews() {
        llHeader.setVisibility(View.GONE);
        llFooter.setVisibility(View.VISIBLE);
        actionButton.setVisibility(View.VISIBLE);
        btnHint.setVisibility(View.GONE);

        ivHintText.setVisibility(View.GONE);
        //actionText.setVisibility(View.VISIBLE);
        llTint.setVisibility(View.GONE);
    }

    private void showClickHint() {
        hideClickHint();
        tooltip = Tooltip.on(actionButton)
                .text("\n   Please press here before starting   \n")
                .color(getResources().getColor(R.color.button_color))
                .border(Color.WHITE, 1f)
                .clickToHide(true)
                .corner(50)
                .borderMargin(20)
                .border(getResources().getColor(R.color.white), 5)
                .textColor(getResources().getColor(R.color.white))
                .textGravity(1)
                .arrowSize(30, 30)
                .position(Position.TOP)
                .textSize(20)
                .show();
    }

    private void hideClickHint() {
        if (tooltip != null) {
            tooltip.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", "");
            setResult(Activity.RESULT_OK, returnIntent);
            clearData();
            finish();
        }
    }

    private void setTextToshow(String pose) {
        btnActionText.setVisibility(View.VISIBLE);
        if (pose.contentEquals(getResources().getString(R.string.face_straight))) {
            btnActionText.setText("keep straight face");
        }
        if (pose.contentEquals(getResources().getString(R.string.face_left))) {
            btnActionText.setText("Keep face to left");
        }
        if (pose.contentEquals(getResources().getString(R.string.face_right))) {
            btnActionText.setText("Keep face to right");
        }
        if (pose.contentEquals(getResources().getString(R.string.face_up))) {
            btnActionText.setText("Keep face up");
        }
        if (pose.contentEquals(getResources().getString(R.string.face_down))) {
            btnActionText.setText("Keep face down");
        }
        if (pose.contentEquals("")) {
            btnActionText.setText("");
        }
    }


}
