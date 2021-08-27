package com.faceopen.camerabenchmark;

import android.app.Activity;
import android.content.Context;
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
import com.ryan.rv_gallery.AnimManager;
import com.ryan.rv_gallery.GalleryRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class PreviewActivity extends AppCompatActivity {

    private final int TOTAL_IMAGES = 15;
    private final String selectionType = "";
    public Toolbar toolbar;
    public ImagePreviewAdapter adapter;
    public ArrayList<Bitmap> completeList;
    public ArrayList<Bitmap> selectedData = new ArrayList<Bitmap>();
    public LinearLayoutManager layoutManager;
    private HashMap<Integer, String> mMap = new HashMap<Integer, String>();
    private GalleryRecyclerView listView;
    private RadioGroup rgGroup;
    private RadioButton rbSave;
    private RadioButton rbDel;
    private TextView tvData;
    private TextView tvBack;
    private TextView tvSave;
    private ImageView ivPreView;
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
        rbDel = findViewById(R.id.rb_del1);
        tvData = findViewById(R.id.tv_data1);
        rgGroup = findViewById(R.id.rg_group1);
        ivPreView = findViewById(R.id.iv_preview1);
        tvBack = findViewById(R.id.tv_back1);
        tvSave = findViewById(R.id.tv_save1);
        tvBack.setOnClickListener(v -> clickBack());
        tvSave.setOnClickListener(v -> {
            saveImage();
        });
        completeList = BitmapDT.getInstance().getBitmaps();
        Log.d("CCC", ""+completeList.size());
        setListView();
    }

    private void setListView() {
        //listView.initPageParams(0, 40).setUp();
        adapter = new ImagePreviewAdapter(PreviewActivity.this, completeList, new OnclickListener() {
            @Override
            public void onClick(int position) {
                ivPreView.setImageBitmap(completeList.get(position));
                listView.smoothScrollToPosition(position);
            }

            @Override
            public void onSaveChecked(int position, boolean state) {
                if (deleteCount > 0) {
                    deleteCount--;
                }
                mMap.put(shownPosition, "S");
                selectedData.add(shownPosition, completeList.get(shownPosition));
                Log.d("RRR", "" + selectedData.size());
                tvData.setText("" + (TOTAL_IMAGES - deleteCount) + " / " + TOTAL_IMAGES);
            }

            @Override
            public void onDeleteChecked(int position, boolean state) {
                deleteCount++;
                mMap.put(shownPosition, "D");
                //selectedData.remove(shownPosition);
                Log.d("RRR", "" + selectedData.size());
                tvData.setText("" + (TOTAL_IMAGES - deleteCount) + " / " + TOTAL_IMAGES);
            }
        });
        layoutManager = new LinearLayoutManager(PreviewActivity.this, LinearLayoutManager.HORIZONTAL, false);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);

        listView
                // 设置滑动速度（像素/s）
                .initFlingSpeed(9000)
                // 设置页边距和左右图片的可见宽度，单位dp
                .initPageParams(0, 120)
                // 设置切换动画的参数因子
                .setAnimFactor(0.1f)
                // 设置切换动画类型，目前有AnimManager.ANIM_BOTTOM_TO_TOP和目前有AnimManager.ANIM_TOP_TO_BOTTOM
                .setAnimType(AnimManager.ANIM_BOTTOM_TO_TOP)
                // 设置点击事件
                // 设置自动播放
                .autoPlay(false)
                // 设置自动播放间隔时间 ms
                .intervalTime(2000)
                // 设置初始化的位置
                .initPosition(1)
                // 在设置完成之后，必须调用setUp()方法
                .setUp();

        //rbSave.setChecked(true);
        selectedData.addAll(completeList);
        Log.d("CCC", ""+selectedData.size());
        tvData.setText("" + selectedData.size() + " / " + TOTAL_IMAGES);
        rgGroup.setOnCheckedChangeListener(null);
        ivPreView.setImageBitmap(completeList.get(0));

        RadioGroup.OnCheckedChangeListener radioListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        };

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
        Intent intent = new Intent(getApplicationContext(), CameraOpActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
        selectedData.clear();
        mMap.clear();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}