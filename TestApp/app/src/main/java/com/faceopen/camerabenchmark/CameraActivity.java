package com.faceopen.camerabenchmark;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.faceopen.camerabenchmark.adapter.ListAdapter;
import com.faceopen.camerabenchmark.dialog.PickerDialog;
import com.faceopen.camerabenchmark.dialog.SimplePickerDialog;
import com.faceopen.camerabenchmark.options.AspectRatioItem;
import com.faceopen.camerabenchmark.options.Commons;
import com.faceopen.camerabenchmark.options.SizeItem;
import com.joanfuentes.hintcase.HintCase;
import com.joanfuentes.hintcase.RectangularShape;
import com.joanfuentes.hintcaseassets.contentholderanimators.FadeInContentHolderAnimator;
import com.joanfuentes.hintcaseassets.contentholderanimators.FadeOutContentHolderAnimator;
import com.joanfuentes.hintcaseassets.hintcontentholders.SimpleHintContentHolder;
import com.joanfuentes.hintcaseassets.shapeanimators.RevealCircleShapeAnimator;
import com.joanfuentes.hintcaseassets.shapeanimators.UnrevealCircleShapeAnimator;
import com.joanfuentes.hintcaseassets.shapes.CircularShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import top.defaults.camera.CameraCallback;
import top.defaults.camera.CameraView;
import top.defaults.camera.FaceOpenCameraManager;
import top.defaults.camera.ImageData;
import top.defaults.camera.Size;
import top.defaults.camera.Values;
import top.defaults.view.TextButton;

public class CameraActivity extends AppCompatActivity {

    @BindView(R.id.preview) CameraView preview;
    @BindView(R.id.status) TextView statusTextView;
    @BindView(R.id.chooseSize) TextButton chooseSizeButton;
    @BindView(R.id.flash) TextButton flashTextButton;
    @BindView(R.id.flash_torch) ImageButton flashTorch;
    @BindView(R.id.switch_mode) TextButton switchButton;
    @BindView(R.id.action) ImageButton actionButton;
    @BindView(R.id.flip) ImageButton flipButton;
    @BindView(R.id.zoomValue) TextView zoomValueTextView;
    @BindView(R.id.iv_preview) ImageView ivPreView;
    @BindView(R.id.iv_hint) ImageView ivHint;
    @BindView(R.id.iv_hint_action) ImageView ivHintAction;
    @BindView(R.id.rl_main) RelativeLayout mainLayout;
    @BindView(R.id.iv_hint_text) TextView ivHintText;
    @BindView(R.id.lv1) RecyclerView listView;
    @BindView(R.id.tv_save) TextView tvSave;
    @BindView(R.id.tv_data) TextView tvData;
    @BindView(R.id.tv_back) TextView tvBack;
    @BindView(R.id.iv_del) ImageView ivDel;
    @BindView(R.id.action_text) TextView actionText;
    @BindView(R.id.fl_list) FrameLayout flList;
    @BindView(R.id.ll_tint) LinearLayout llTint;
    @BindView(R.id.ll_preview) LinearLayout llPreview;



    private ArrayList<Bitmap> imageid = new ArrayList<Bitmap>() ;
    private ArrayList<Bitmap> completeList = new ArrayList<Bitmap>() ;
    private Handler picHandler = new Handler();
    private int finalI;
    private boolean isRecordingVideo;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private int currentFlash = Values.FLASH_AUTO;
    private String TAG = "CameraActivity";
    private long CAMERA_CAPTURE_TIME = 100;
    private String camAction = "straight";
    private String camText = "";
    private SelectionCallback selectionCallback;
    private String selectionType="";
    private boolean f_straight = false;
    private boolean f_left = false;
    private boolean f_right = false;
    private boolean f_up = false;
    private boolean f_down = false;
    private boolean isPreviewZoom = false;
    private SelectionCallback mSelectionCallback;
    private String mSelectionType = "";
    private ListAdapter adapter;
    private int shownPosition = 0;
    private int TOTAL_IMAGES = 15;
    private static final int[] FLASH_OPTIONS = {
            Values.FLASH_AUTO,
            Values.FLASH_OFF,
            Values.FLASH_ON,
    };

    private static final int[] FLASH_ICONS = {
            R.drawable.ic_flash_auto,
            R.drawable.ic_flash_off,
            R.drawable.ic_flash_on,
    };

    private static final int[] FLASH_TITLES = {
            R.string.flash_auto,
            R.string.flash_off,
            R.string.flash_on,
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        getIntentData();
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

    @OnClick(R.id.chooseRatio)
    void chooseRatio() {
        List<AspectRatioItem> supportedAspectRatios = Commons.wrapItems(FaceOpenCameraManager.getInstance().getSupportedAspectRatios(), AspectRatioItem::new);
        if (supportedAspectRatios != null) {
            SimplePickerDialog<AspectRatioItem> dialog = SimplePickerDialog.create(new PickerDialog.ActionListener<AspectRatioItem>() {
                @Override
                public void onCancelClick(PickerDialog<AspectRatioItem> dialog) { }

                @Override
                public void onDoneClick(PickerDialog<AspectRatioItem> dialog) {
                    AspectRatioItem item = dialog.getSelectedItem(AspectRatioItem.class);
                    FaceOpenCameraManager.getInstance().setAspectRatio(item.get());
                }
            });
            dialog.setItems(supportedAspectRatios);
            dialog.setInitialItem(Commons.findEqual(supportedAspectRatios,FaceOpenCameraManager.getInstance().getAspectRatio()));
            dialog.show(getFragmentManager(), "aspectRatio");
        }
    }

    @OnClick(R.id.chooseSize)
    void chooseSize() {
        Size selectedSize = null;
        List<SizeItem> supportedSizes = null;
        int mode = FaceOpenCameraManager.getInstance().getMode();
        if (mode == Values.MODE_VIDEO) {
            Set<Size> videoSizes = FaceOpenCameraManager.getInstance().getSupportedVideoSizes();
            selectedSize = FaceOpenCameraManager.getInstance().getVideoSize();
            if (videoSizes != null && videoSizes.size() > 0) {
                supportedSizes = Commons.wrapItems(videoSizes, SizeItem::new);
            }
        } else if (mode == Values.MODE_IMAGE) {
            Set<Size> imageSizes = FaceOpenCameraManager.getInstance().getSupportedImageSizes();
            selectedSize = FaceOpenCameraManager.getInstance().getImageSize();
            if (imageSizes != null && imageSizes.size() > 0) {
                supportedSizes = Commons.wrapItems(imageSizes, SizeItem::new);
            }
        }

        if (supportedSizes != null) {
            SimplePickerDialog<SizeItem> dialog = SimplePickerDialog.create(new PickerDialog.ActionListener<SizeItem>() {
                @Override
                public void onCancelClick(PickerDialog<SizeItem> dialog) { }

                @Override
                public void onDoneClick(PickerDialog<SizeItem> dialog) {
                    SizeItem sizeItem = dialog.getSelectedItem(SizeItem.class);
                    if (mode == Values.MODE_VIDEO) {
                        FaceOpenCameraManager.getInstance().setVideoSize(sizeItem.get());
                    } else {
                        FaceOpenCameraManager.getInstance().setImageSize(sizeItem.get());
                    }
                }
            });
            dialog.setItems(supportedSizes);
            dialog.setInitialItem(Commons.findEqual(supportedSizes, selectedSize));
            dialog.show(getFragmentManager(), "cameraOutputSize");
        }
    }



    @OnCheckedChanged(R.id.fillSpace)
    void onFillSpaceChecked(boolean checked) {
        preview.setFillSpace(checked);
    }

    @OnCheckedChanged(R.id.enableZoom)
    void onEnableZoomChecked(boolean checked) {
        preview.setPinchToZoom(checked);
    }

    @OnClick(R.id.flash)
    void flash() {
        currentFlash = (currentFlash + 1) % FLASH_OPTIONS.length;
        flashTextButton.setText(FLASH_TITLES[currentFlash]);
        flashTextButton.setCompoundDrawablesWithIntrinsicBounds(FLASH_ICONS[currentFlash], 0, 0, 0);
        FaceOpenCameraManager.getInstance().setFlash(FLASH_OPTIONS[currentFlash]);
    }

    @OnClick(R.id.action)
    void action() {
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
            finalI = 0;
            imageid.clear();
            actionButton.setEnabled(false);
            actionButton.setVisibility(View.GONE);
            FaceOpenCameraManager.getInstance().takePicture();
        }
    }

    @OnClick(R.id.flash_torch)
    void toggleFlashTorch() {
        int flash = FaceOpenCameraManager.getInstance().getFlash();
        if (flash == Values.FLASH_TORCH) {
            FaceOpenCameraManager.getInstance().setFlash(currentFlash);
            flashTextButton.setEnabled(true);
            flashTorch.setImageResource(R.drawable.light_off);
        } else {
            FaceOpenCameraManager.getInstance().setFlash(Values.FLASH_TORCH);
            flashTextButton.setEnabled(false);
            flashTorch.setImageResource(R.drawable.light_on);
        }
    }

    @OnClick(R.id.switch_mode)
    void switchMode() {
        FaceOpenCameraManager.getInstance().switchMode();
    }

    @OnClick(R.id.flip)
    void flip() {
        FaceOpenCameraManager.getInstance().flipCamera();
    }

    @OnClick(R.id.iv_hint)
    void hint() {
        if(!isPreviewZoom) {
            isPreviewZoom = true;
            FrameLayout.LayoutParams buttonLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            buttonLayoutParams.setMargins(0, 100, 0, 100);
            ivHint.setLayoutParams(buttonLayoutParams);
            ivHintText.setVisibility(View.VISIBLE);
            buttonLayoutParams.gravity = Gravity.CENTER;
            //buttonLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            llTint.setVisibility(View.VISIBLE);
            //mainLayout.setBackgroundColor(R.color.background);
            ivHint.getLayoutParams().height = 1200;
            ivHint.getLayoutParams().width = 1200;
        }else {
            isPreviewZoom = false;
            FrameLayout.LayoutParams buttonLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            buttonLayoutParams.setMargins(0, 0, 0, 0);
            //buttonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            //buttonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            ivHint.setLayoutParams(buttonLayoutParams);
            ivHintText.setVisibility(View.GONE);
            llTint.setVisibility(View.GONE);
            ivHint.getLayoutParams().height = 400;
            ivHint.getLayoutParams().width = 400;
        }
        ivHint.requestLayout();
    }

    @OnClick(R.id.iv_hint_action)
    void hintAction() {

    }

    @OnClick(R.id.tv_save)
    void saveImage() {
        //selectionCallback.onComplete(selectionType);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",selectionType);
        setResult(Activity.RESULT_OK,returnIntent);
        completeList.clear();
        imageid.clear();
        finish();
    }

    @OnClick(R.id.tv_back)
    void clickBack() {
        completeList.clear();
        imageid.clear();
        finish();
    }

    @OnClick(R.id.iv_del)
    void delImage(){
        if(completeList.size() > 0) {
            adapter.removeAt(shownPosition);
            tvData.setText("" + completeList.size() + " / " + TOTAL_IMAGES);
        }
    }


    private void startCamera(){
        FaceOpenCameraManager.getInstance().init(this);
        FaceOpenCameraManager.getInstance().initPreview(preview);
        FaceOpenCameraManager.getInstance().initPhotographer(this,preview);
        FaceOpenCameraManager.getInstance().initPhotoHelper();
        FaceOpenCameraManager.getInstance().flipCamera();
        FaceOpenCameraManager.getInstance().enterFullscreen(getWindow());
        FaceOpenCameraManager.getInstance().fillSpace(true);
        FaceOpenCameraManager.getInstance().startPreView();
        configureMode();
        camText = getResources().getString(R.string.face_straight);
        mainLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showCameraHint();
            }
        },1000);
        FaceOpenCameraManager.getInstance().registerCallback(new CameraCallback() {
            @Override
            public void frameReceived(ImageData imageData) {
                if(imageData != null) {
                    Log.d(TAG, "" + imageData.getImageHeight());
                    Log.d(TAG, "" + imageData.getImageWidth());
                    Log.d(TAG, "" + imageData.getImageFormat());
                    imageid.add(imageData.getBitmap());
                    if(imageid.size() < 3) {
                        completeList.addAll(imageid);
                        Log.d("XXX", "frameReceived "+completeList.size());
                        picHandler.postDelayed(picRunnable, CAMERA_CAPTURE_TIME);
                    }else{
                        //ivHintText.setText(R.string.face_straight);
                        actionButton.setEnabled(true);
                        actionButton.setVisibility(View.VISIBLE);
                        showHintPreView(camAction);
                        picHandler.removeCallbacks(picRunnable);
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

    public void requestPermission () {
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

    private void configureMode(){
        if (FaceOpenCameraManager.getInstance().getMode() == Values.MODE_VIDEO) {
            actionButton.setImageResource(R.drawable.record);
            chooseSizeButton.setText(R.string.video_size);
            switchButton.setText(R.string.video_mode);
        } else {
            actionButton.setImageResource(R.drawable.ic_camera);
            chooseSizeButton.setText(R.string.image_size);
            switchButton.setText(R.string.image_mode);
        }
    }

    public void showCameraHint() {
            View parentView = getWindow().getDecorView();
            SimpleHintContentHolder blockInfo = new SimpleHintContentHolder.Builder(getApplicationContext())
                    .setContentTitle("Take picture")
                    .setContentText("Please click to take your face picture")
                    .setTitleStyle(R.style.title)
                    .setContentStyle(R.style.content)
                    .build();
            new HintCase(parentView)
                    .setTarget(findViewById(R.id.action), new CircularShape())
                    .setShapeAnimators(new RevealCircleShapeAnimator(),
                            new UnrevealCircleShapeAnimator())
                    .setHintBlock(blockInfo)
                    .setOnClosedListener(new HintCase.OnClosedListener() {
                        @Override
                        public void onClosed() {
                            showHintPreView(camAction);
                        }
                    })
                    .show();

    }

    public void showSaveHint() {
        View parentView = getWindow().getDecorView();
        SimpleHintContentHolder blockInfo = new SimpleHintContentHolder.Builder(getApplicationContext())
                .setContentTitle("Please save the data")
                .setContentText("Please review your pictures and click save")
                .setTitleStyle(R.style.title)
                .setContentStyle(R.style.content)
                .build();
        new HintCase(parentView)
                .setTarget(findViewById(R.id.tv_save), new RectangularShape())
                .setShapeAnimators(new RevealCircleShapeAnimator(),
                        new UnrevealCircleShapeAnimator())
                .setHintBlock(blockInfo)
                .setOnClosedListener(new HintCase.OnClosedListener() {
                    @Override
                    public void onClosed() {

                    }
                })
                .show();

    }

    private void showHintPreView(String pose){
        if(pose.contentEquals("straight")){
            camAction = "left";
            camText = getResources().getString(R.string.face_straight);
            getGifLoadedUsingGlidePreView(R.raw.straight);
        }
        if(pose.contentEquals("left")){
            camAction = "right";
            camText = getResources().getString(R.string.face_left);
           getGifLoadedUsingGlidePreView(R.raw.left);
        }
        if(pose.contentEquals("right")){
            camAction = "up";
            camText = getResources().getString(R.string.face_right);
            getGifLoadedUsingGlidePreView(R.raw.right);
        }
        if(pose.contentEquals("up")){
            camAction = "down";
            camText = getResources().getString(R.string.face_up);
            getGifLoadedUsingGlidePreView(R.raw.up);
        }
        if(pose.contentEquals("down")){
            camAction = "";
            camText = getResources().getString(R.string.face_down);
            getGifLoadedUsingGlidePreView(R.raw.down);
        }
        if(pose.contentEquals("")){
            camAction = "";
            camText = "";
            getGifLoadedUsingGlidePreView(null);
            setListView();
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

    private void setListView(){
        tvData.setVisibility(View.VISIBLE);
        tvSave.setVisibility(View.VISIBLE);
        tvBack.setVisibility(View.VISIBLE);
        flList.setVisibility(View.VISIBLE);
        actionButton.setVisibility(View.GONE);
        tvData.setText(""+ completeList.size() + " / " + TOTAL_IMAGES);
        adapter = new ListAdapter(CameraActivity.this, completeList);
        listView.setHasFixedSize(true);
        LinearLayoutManager layoutManager= new LinearLayoutManager(CameraActivity.this,LinearLayoutManager.HORIZONTAL, false);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    shownPosition = getCurrentItem();
                    Log.d("CCC", ""+shownPosition);
                    ivPreView.setVisibility(View.VISIBLE);
                    ivDel.setVisibility(View.VISIBLE);
                    //showImageDialog(completeList.get(shownPosition));
                    llPreview.setVisibility(View.VISIBLE);
                    ivPreView.setImageBitmap(completeList.get(shownPosition));
                }
            }
        });
        //showSaveHint();
    }

    private int getCurrentItem(){
        return ((LinearLayoutManager)listView.getLayoutManager())
                .findFirstVisibleItemPosition() + ((LinearLayoutManager)listView.getLayoutManager()).findLastCompletelyVisibleItemPosition()/2;
    }

    Runnable picRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d("XXX", "takePicture ");
            FaceOpenCameraManager.getInstance().takePicture();
        }
    };

    public void getIntentData(){
        Bundle b = getIntent().getExtras();
        Intent intent = getIntent();
        if(intent != null) {
            //selectionCallback = (SelectionCallback) intent.getSerializableExtra("listener");
        }
        if(b != null) {
            mSelectionType = b.getString("type");
        }
    }

    private void showImageDialog(Bitmap bitmap){
        ImageView ivImage;
        ImageView btDel;
        Dialog settingsDialog = new Dialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image_dialog, null));
        ivImage = settingsDialog.findViewById(R.id.iv_image);
        btDel = settingsDialog.findViewById(R.id.bt_del);
        ivImage.setImageBitmap(bitmap);
        btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(completeList.size() > 0) {
                    adapter.removeAt(shownPosition);
                    tvData.setText("" + completeList.size() + " / " + TOTAL_IMAGES);
                }
            }
        });
        settingsDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageid.clear();
        completeList.clear();
    }
}
