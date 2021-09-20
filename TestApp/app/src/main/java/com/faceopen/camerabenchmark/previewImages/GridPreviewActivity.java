package com.faceopen.camerabenchmark.previewImages;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.faceopen.camerabenchmark.base.AppActivity;
import com.faceopen.camerabenchmark.cameraoptions.CamOptionActivity;
import com.faceopen.camerabenchmark.data.AppConstants;
import com.faceopen.camerabenchmark.data.Face;
import com.faceopen.camerabenchmark.R;
import com.faceopen.camerabenchmark.adapter.GridImageAdapter;
import com.faceopen.camerabenchmark.data.BitmapDT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GridPreviewActivity extends AppActivity {

    private String TAG = this.getClass().getSimpleName();
    private final int TOTAL_IMAGES = AppConstants.TOTAL_IMAGE_COUNT;
    private GridView gridView;
    private GridImageAdapter gridImageAdapter;
    private TextView tvSave, tvData;
    private ImageView tvBack;
    private ArrayList<Face> completeList = new ArrayList<>();
    private ArrayList<Face> selectedList = new ArrayList<>();
    private final String selectionType = "";
    private ImageView ivPreview;
    private ImageView ivclose;

    private Button btnSave;
    private Button btnDelete;
    private Dialog dialog = null;
    private HashMap<Face, String> saveMap = new HashMap<Face, String>();
    private Face face;
    private int deleteCount = 0;
    private Handler handler = new Handler();
    private long previewShowTime = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_preview);
        getSupportActionBar().hide();
        initDialog();
        gridView = findViewById(R.id.gridViewImages);
        tvSave = findViewById(R.id.tv_save1);
        tvBack = findViewById(R.id.tv_back1);
        tvData = findViewById(R.id.tv_data1);
        ivclose = findViewById(R.id.iv_close);

        gridImageAdapter = new GridImageAdapter(this, completeList, new OnImageClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onSaveChecked(int position, boolean state, Face face) {
                saveMap.put(face, "s");
                incCount();
            }

            @Override
            public void onDeleteChecked(int position, boolean state, Face face) {
                saveMap.put(face, "d");
                decCount();
            }
        });

        for (int i = 0; i < BitmapDT.getInstance().getBitmaps().size(); i++) {
            face = new Face();
            face.croppedBitmap = BitmapDT.getInstance().getBitmaps().get(i);
            face.setId(i);
            completeList.add(i, face);
            saveMap.put(face, "s");
        }
        tvData.setText("" + (TOTAL_IMAGES) + " / " + TOTAL_IMAGES);
        gridView.setAdapter(gridImageAdapter);

        // on Item Click Listener
        gridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        showImagePreview(position);
                    }
                }
        );

        tvBack.setOnClickListener(v -> clickBack());
        tvSave.setOnClickListener(v -> {
            saveImage();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showImagePreview(int position) {
        Face face = completeList.get(position);
        Log.d(TAG, "Dialog open");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);

        btnSave.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnSave.setTextColor(getResources().getColor(R.color.white));


        btnDelete.setBackgroundColor(getResources().getColor(R.color.white));


        Iterator myVeryOwnIterator = saveMap.keySet().iterator();
        while (myVeryOwnIterator.hasNext()) {
            Face key = (Face) myVeryOwnIterator.next();
            String value = (String) saveMap.get(key);
            if (value.equals("s") && position == key.ID) {
                btnSave.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnSave.setTextColor(getResources().getColor(R.color.white));
                btnDelete.setBackgroundColor(android.R.drawable.btn_default);
                btnDelete.setTextColor(getResources().getColor(R.color.app_color_c51));
            }
            if (value.equals("d") && position == key.ID) {
                btnDelete.setBackgroundColor(getResources().getColor(R.color.app_color_c51));
                btnDelete.setTextColor(getResources().getColor(R.color.white));
                btnSave.setBackgroundColor(android.R.drawable.btn_default);
                btnSave.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }

        btnSave.setOnClickListener(v -> {
            saveMap.put(face, "s");
            face.setSaved(true);
            incCount();
            btnSave.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btnSave.setTextColor(getResources().getColor(R.color.white));

            btnDelete.setTextColor(getResources().getColor(R.color.app_color_c51));
            btnDelete.setBackgroundColor(getResources().getColor(R.color.white));

            btnDelete.setBackgroundColor(android.R.drawable.btn_default);
            gridImageAdapter.notifyDataSetChanged();
            handler.postDelayed(closeDialogRunnable, previewShowTime);

        });
        btnDelete.setOnClickListener(v -> {
            saveMap.put(face, "d");
            face.setSaved(false);
            decCount();
            btnSave.setBackgroundColor(getResources().getColor(R.color.white));
            btnSave.setTextColor(getResources().getColor(R.color.colorPrimary));

            btnDelete.setBackgroundColor(getResources().getColor(R.color.app_color_c51));
            btnDelete.setTextColor(getResources().getColor(R.color.white));
            btnSave.setBackgroundColor(android.R.drawable.btn_default);
            gridImageAdapter.notifyDataSetChanged();
            handler.postDelayed(closeDialogRunnable, previewShowTime);
        });


        ((ImageView) dialog.findViewById(R.id.iv_close)).setOnClickListener(v -> {
            dialog.dismiss();
        });

        ivPreview = dialog.findViewById(R.id.iv_previewImage);
        ivPreview.setImageBitmap(completeList.get(position).croppedBitmap);
    }

    private void initDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.grid_imagepreview);
        dialog.setCancelable(true);
        btnSave = ((Button) dialog.findViewById(R.id.btnPreviewSave));
        btnDelete = ((Button) dialog.findViewById(R.id.btnPreviewDlt));
    }

    private void saveImage() {
        //selectionCallback.onComplete(selectionType);
        Log.d(TAG, "hashmap " + saveMap.size());
        Iterator myVeryOwnIterator = saveMap.keySet().iterator();
        while (myVeryOwnIterator.hasNext()) {
            Face key = (Face) myVeryOwnIterator.next();
            String value = (String) saveMap.get(key);
            if (value.equals("s")) {
                selectedList.add(key);
            }
        }
        Log.d(TAG, "selectedList " + selectedList.size());
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", selectionType);
        setResult(Activity.RESULT_OK, returnIntent);
        clearData();
        finish();
    }

    private void clearData() {
        completeList.clear();
    }

    private void clickBack() {
        Intent intent = new Intent(getApplicationContext(), CamOptionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void decCount() {
        deleteCount++;
        tvData.setText("" + (TOTAL_IMAGES - deleteCount) + " / " + TOTAL_IMAGES);
    }

    private void incCount() {
        if (deleteCount > 0) {
            deleteCount--;
        }
        tvData.setText("" + (TOTAL_IMAGES - deleteCount) + " / " + TOTAL_IMAGES);
    }

    Runnable closeDialogRunnable = new Runnable() {
        @Override
        public void run() {
            dialog.dismiss();
        }
    };

    @Override
    public void onBackPressed() {
        clickBack();
    }
}