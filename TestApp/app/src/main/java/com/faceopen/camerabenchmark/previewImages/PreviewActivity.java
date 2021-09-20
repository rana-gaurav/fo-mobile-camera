package com.faceopen.camerabenchmark.previewImages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.faceopen.camerabenchmark.R;
import com.faceopen.camerabenchmark.base.AppActivity;
import com.faceopen.camerabenchmark.adapter.ImagePreviewAdapter;
import com.faceopen.camerabenchmark.cameraoptions.CamOptionActivity;
import com.faceopen.camerabenchmark.data.BitmapDT;
import com.faceopen.camerabenchmark.data.Face;
import com.ryan.rv_gallery.AnimManager;
import com.ryan.rv_gallery.GalleryRecyclerView;

import java.util.ArrayList;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class PreviewActivity extends AppActivity {

    private final int TOTAL_IMAGES = 15;
    private final String selectionType = "";
    public Toolbar toolbar;
    public ImagePreviewAdapter adapter;
    public ArrayList<Face> completeList = new ArrayList<>();
    public ArrayList<Face> selectedList = new ArrayList<>();
    public LinearLayoutManager layoutManager;
    private GalleryRecyclerView listView;
    private TextView tvData;
    private ImageView tvBack;
    private TextView tvSave;
    private int deleteCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = findViewById(R.id.recyclerView);
        tvData = findViewById(R.id.tv_data1);
        tvBack = findViewById(R.id.tv_back1);
        tvSave = findViewById(R.id.tv_save1);
        tvBack.setOnClickListener(v -> clickBack());
        tvSave.setOnClickListener(v -> {
            saveImage();
        });
        Face face;
        for(int i = 0; i < BitmapDT.getInstance().getBitmaps().size(); i++){
            face = new Face();
            face.croppedBitmap = BitmapDT.getInstance().getBitmaps().get(i);
            completeList.add(i, face);
        }
        Log.d("CCC", ""+completeList.size());
        setListView();
    }

    private void setListView() {
        tvData.setText("" + (TOTAL_IMAGES) + " / " + TOTAL_IMAGES);
        adapter = new ImagePreviewAdapter(PreviewActivity.this, completeList, new OnImageClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onSaveChecked(int position, boolean state, Face face) {
                if (deleteCount > 0) {
                    deleteCount--;
                }
                tvData.setText("" + (TOTAL_IMAGES - deleteCount) + " / " + TOTAL_IMAGES);
            }

            @Override
            public void onDeleteChecked(int position, boolean state, Face face) {
                deleteCount++;
                tvData.setText("" + (TOTAL_IMAGES - deleteCount) + " / " + TOTAL_IMAGES);
            }
        });
        layoutManager = new LinearLayoutManager(PreviewActivity.this, LinearLayoutManager.HORIZONTAL, false);
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);
        listView.setAdapter(adapter);

        listView.initFlingSpeed(1000)
                .initPageParams(0, getResources().getInteger(R.integer.page_width))
                .setAnimFactor(0.2f)
                .setAnimType(AnimManager.ANIM_BOTTOM_TO_TOP)
                .autoPlay(false)
                .initPosition(0)
                .setUp();
    }

    private void clickBack() {
        Intent intent = new Intent(getApplicationContext(), CamOptionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void saveImage() {
        //selectionCallback.onComplete(selectionType);
        Log.d("RRR", "getDeletedIndexSize " + adapter.getDeletedIndex().size());
        for (int i = 0; i < adapter.getDeletedIndex().size(); i++){
            Log.d("RRR", "getDeletedIndex " + adapter.getDeletedIndex().get(i));
            completeList.remove(new Integer(adapter.getDeletedIndex().get(i)));
        }
        Log.d("RRR", "selectedList " + completeList.size());
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", selectionType);
        setResult(Activity.RESULT_OK, returnIntent);
        clearData();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearData();
    }

    private void clearData() {
        completeList.clear();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}