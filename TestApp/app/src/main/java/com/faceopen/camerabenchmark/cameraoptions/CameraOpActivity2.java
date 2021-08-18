package com.faceopen.camerabenchmark.cameraoptions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.faceopen.camerabenchmark.CameraActivity;
import com.faceopen.camerabenchmark.R;
import com.faceopen.camerabenchmark.adapter.CustomAdapter1;

public class CameraOpActivity2 extends AppCompatActivity {

    GridView gridView;
    String[] items = {"Under Light", "Under SunLight", "Left Side Light", "Right Side", "Light on Straight Face"};
    Integer[] logo = {R.drawable.ic_android,
            R.drawable.ic_android,
            R.drawable.ic_android,
            R.drawable.ic_android,
            R.drawable.ic_android};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_op2);

        gridView = findViewById(R.id.gridView);


        CustomAdapter1 adapter1 = new CustomAdapter1(this, items, logo);
        gridView.setAdapter(adapter1);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String itemValue = (String) gridView.getItemAtPosition(position);
                Toast.makeText(CameraOpActivity2.this, itemValue, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CameraOpActivity2.this, CameraActivity.class);
                startActivity(intent);


            }
        });


    }
}