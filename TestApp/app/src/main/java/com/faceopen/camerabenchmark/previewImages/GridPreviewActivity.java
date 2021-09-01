package com.faceopen.camerabenchmark.previewImages;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.faceopen.camerabenchmark.R;
import com.faceopen.camerabenchmark.adapter.GridImageAdapter;
import com.faceopen.camerabenchmark.data.BitmapDT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GridPreviewActivity extends AppCompatActivity {

    GridView gridView;
    GridImageAdapter gridImageAdapter;
    TextView tvSave, tvBack, tvData;
    ArrayList<Bitmap> arrayList = new ArrayList<>();
    ImageView ivPreview;
    ImageView ivclose;
    private HashMap<String, Bitmap> saveMap=new HashMap<String, Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_preview);

        gridView = findViewById(R.id.gridViewImages);
        tvSave = findViewById(R.id.tv_save1);
        tvBack = findViewById(R.id.tv_back1);
        tvData = findViewById(R.id.tv_data1);
        ivclose = findViewById(R.id.iv_close);

        arrayList = BitmapDT.getInstance().getBitmaps();


        gridImageAdapter = new GridImageAdapter(this, BitmapDT.getInstance().getBitmaps());
        gridView.setAdapter(gridImageAdapter);

        for(int i = 0; i < BitmapDT.getInstance().getBitmaps().size(); i++){
            saveMap.put("s", BitmapDT.getInstance().getBitmaps().get(i));
        }

        // on Item Click Listener
        gridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        showImagePreview(position);
                    }
                }
        );

        tvSave.setOnClickListener(v -> {
            finish();
        });
        tvBack.setOnClickListener(v -> {
            finish();
        });


    }

    private void showImagePreview(int position) {
        Log.d("GGG", "Dialog open");
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.grid_imagepreview);
        dialog.setCancelable(true);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);

        ((ImageView) dialog.findViewById(R.id.iv_close)).setOnClickListener(v -> {
            Log.d("GGG", "Dialog closed");
            dialog.dismiss();
        });
        ((Button) dialog.findViewById(R.id.btnPreviewSave)).setOnClickListener(v -> {
            Log.d("GGG", "Image Saved");
            saveMap.put("s", BitmapDT.getInstance().getBitmaps().get(position));
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
        });
        ((Button) dialog.findViewById(R.id.btnPreviewDlt)).setOnClickListener(v ->{
                Log.d("GGG", "Image Dlt");
                saveMap.put("d", BitmapDT.getInstance().getBitmaps().get(position));
        });
        ivPreview = dialog.findViewById(R.id.iv_previewImage);
        ivPreview.setImageBitmap(arrayList.get(position));
    }

    private void getElements(){
        Iterator myVeryOwnIterator = saveMap.keySet().iterator();
        while(myVeryOwnIterator.hasNext()) {
            String key=(String)myVeryOwnIterator.next();
            Bitmap value=(Bitmap) saveMap.get(key);
            if(key.equals("s")){

            }else{

            }
        }
    }


}