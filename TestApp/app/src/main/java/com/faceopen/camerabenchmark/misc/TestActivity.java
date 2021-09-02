package com.faceopen.camerabenchmark.misc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.os.Bundle;
import android.widget.Button;

import com.faceopen.camerabenchmark.R;


public class TestActivity extends AppCompatActivity {
    Button btn;
    LinearLayoutCompat parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }
}