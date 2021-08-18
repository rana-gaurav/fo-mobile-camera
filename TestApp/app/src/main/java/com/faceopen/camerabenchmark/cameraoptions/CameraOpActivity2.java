package com.faceopen.camerabenchmark.cameraoptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.faceopen.camerabenchmark.CameraActivity;
import com.faceopen.camerabenchmark.R;
import com.faceopen.camerabenchmark.SelectionCallback;
import com.faceopen.camerabenchmark.SharedPreferenceManager;
import com.faceopen.camerabenchmark.adapter.CustomAdapter1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CameraOpActivity2 extends AppCompatActivity {

    GridView gridView;
    CustomAdapter1 adapter1;
    int selectedPos = 0;
    Set<String> listSelect = new HashSet<String>();
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
        adapter1 = new CustomAdapter1(this, items, logo);
        gridView.setAdapter(adapter1);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) gridView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), itemValue, Toast.LENGTH_SHORT).show();
                selectedPos = position;
                Intent intent = new Intent(CameraOpActivity2.this, CameraActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", itemValue);
                intent.putExtras(bundle);
                startActivityForResult(intent, selectedPos);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == selectedPos) {
            if(resultCode == Activity.RESULT_OK) {
                Log.d("YYY", "onActivityResult " + selectedPos);
                if(selectedPos == 0)
                    SharedPreferenceManager.setCategory1(this, true);
                if(selectedPos == 1)
                    SharedPreferenceManager.setCategory2(this, true);
                if(selectedPos == 2)
                    SharedPreferenceManager.setCategory3(this, true);
                if(selectedPos == 3)
                    SharedPreferenceManager.setCategory4(this, true);
                if(selectedPos == 4)
                    SharedPreferenceManager.setCategory5(this, true);
                adapter1.refresh();
            }

        }
    }
}