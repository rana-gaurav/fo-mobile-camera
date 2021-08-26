package com.faceopen.camerabenchmark;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faceopen.camerabenchmark.adapter.ImagePreviewAdapter;
import com.faceopen.camerabenchmark.cameraoptions.CameraOpActivity2;
import com.faceopen.camerabenchmark.data.BitmapDT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;

public class PreviewActivity extends AppCompatActivity {

    private final int TOTAL_IMAGES = 15;
    private final String selectionType = "";
    public Toolbar toolbar;
    public ImagePreviewAdapter adapter;
    public ArrayList<Bitmap> completeList;
    public ArrayList<Bitmap> selectedData = new ArrayList<Bitmap>();
    public LinearLayoutManager layoutManager;
    HashMap<Integer, String> mMap = new HashMap<Integer, String>();
    RecyclerView listView;
    RadioGroup rgGroup;
    RadioButton rbSave;
    @BindView(R.id.rb_del1)
    RadioButton rbDel;
    TextView tvData;
    @BindView(R.id.tv_back1)
    TextView tvBack;
    TextView tvSave;
    ImageView ivPreView;
    private ArrayList<Bitmap> imageid = new ArrayList<Bitmap>();
    private int deleteCount = 0;
    private int shownPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = findViewById(R.id.recyclerView);
        rbSave = findViewById(R.id.rb_save1);
        tvData = findViewById(R.id.tv_data1);
        rgGroup = findViewById(R.id.rg_group1);
        ivPreView = findViewById(R.id.iv_preview1);
        tvBack = findViewById(R.id.tv_back1);
        tvSave = findViewById(R.id.tv_save1);
        listView.setHasFixedSize(false);
        tvBack.setOnClickListener(v -> clickBack());
        tvSave.setOnClickListener(v -> {
            saveImage();
        });
        completeList = BitmapDT.getInstance().getBitmaps();
        setListView();
    }

    private void setListView() {
        adapter = new ImagePreviewAdapter(PreviewActivity.this, completeList);
        layoutManager = new LinearLayoutManager(PreviewActivity.this, LinearLayoutManager.HORIZONTAL, false);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        rbSave.setChecked(true);
        selectedData.addAll(completeList);
        tvData.setText("" + selectedData.size() + " / " + TOTAL_IMAGES);
        rgGroup.setOnCheckedChangeListener(null);

        RadioGroup.OnCheckedChangeListener radioListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_save1) {
                    if (deleteCount > 0) {
                        deleteCount--;
                    }
                    mMap.put(shownPosition, "S");
                    selectedData.add(shownPosition, completeList.get(shownPosition));
                    Log.d("RRR", "" + selectedData.size());
                }
                if (checkedId == R.id.rb_del1) {
                    deleteCount++;
                    mMap.put(shownPosition, "D");
                    //selectedData.remove(shownPosition);
                    Log.d("RRR", "" + selectedData.size());
                }
                tvData.setText("" + (TOTAL_IMAGES - deleteCount) + " / " + TOTAL_IMAGES);
            }
        };

        ivPreView.setImageBitmap(completeList.get(0));



//        Glide.with(this)
//                .asBitmap()
//                .load(completeList.get(0))
//                .into(ivPreView);

        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    shownPosition = getCurrentItem();
                    Log.d("CCC", "" + shownPosition);
                    ivPreView.setImageBitmap(completeList.get(shownPosition));
                    Iterator myVeryOwnIterator = mMap.keySet().iterator();
                    rgGroup.setOnCheckedChangeListener(null);
                    rbSave.setChecked(true);
                    try {
                        while (myVeryOwnIterator.hasNext()) {
                            Integer key = (Integer) myVeryOwnIterator.next();
                            String value = (String) mMap.get(key);
                            if (key == shownPosition && value.equals("S")) {
                                rbSave.setChecked(true);
                            }
                            if (key == shownPosition && value.equals("D")) {
                                rbDel.setChecked(true);
                            }
                        }
                        rgGroup.setOnCheckedChangeListener(radioListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private int getCurrentItem() {
        return ((LinearLayoutManager) listView.getLayoutManager())
                .findFirstVisibleItemPosition() + ((LinearLayoutManager) listView.getLayoutManager()).findLastCompletelyVisibleItemPosition() / 2;
    }

    private void clickBack() {
        onBackPressed();
    }

    private void saveImage() {
        //selectionCallback.onComplete(selectionType);
        Iterator myVeryOwnIterator = mMap.keySet().iterator();
        while (myVeryOwnIterator.hasNext()) {
            Integer key = (Integer) myVeryOwnIterator.next();
            String value = mMap.get(key);
            if (value.equals("S")) {
                //selectedData.set(key, completeList.get(key));
            }
            if (value.equals("D")) {
                Log.d("RRR", "saveImageD " + key);
                selectedData.remove(key);
            }
        }
        Log.d("RRR", "saveImage " + selectedData.size());

        Intent returnIntent = new Intent(PreviewActivity.this, CameraOpActivity2.class);
        returnIntent.putExtra("result", selectionType);
        setResult(Activity.RESULT_OK, returnIntent);
        startActivity(returnIntent);
        completeList.clear();
        imageid.clear();
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
        selectedData.clear();
        imageid.clear();
        mMap.clear();
    }

}