package com.faceopen.camerabenchmark.cameraoptions;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.faceopen.camerabenchmark.R;
import com.faceopen.camerabenchmark.adapter.CustomAdapter;
import com.faceopen.camerabenchmark.model.ItemModel;

import java.util.ArrayList;

public class CameraOpActivity extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_op);

        gridView = findViewById(R.id.gridView);

        ArrayList<ItemModel> itemModelArrayList = new ArrayList<ItemModel>();
        itemModelArrayList.add(new ItemModel("Under Light", R.drawable.ic_android,1));
        itemModelArrayList.add(new ItemModel("Under SunLight", R.drawable.ic_android,2));
        itemModelArrayList.add(new ItemModel("Left Side Light", R.drawable.ic_android,3));
        itemModelArrayList.add(new ItemModel("Right side Light", R.drawable.ic_android,4));
        itemModelArrayList.add(new ItemModel("Light on Face", R.drawable.ic_android,5));

        CustomAdapter adapter = new CustomAdapter(this, itemModelArrayList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String itemValue= String.valueOf(gridView.getItemIdAtPosition(position));
                /*switch (itemValue) {
                    case R.drawable.ic_android:

                }*/

                Toast.makeText(getApplicationContext(), itemValue, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

